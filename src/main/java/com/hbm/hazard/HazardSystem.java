package com.hbm.hazard;

import com.hbm.hazard.transformer.HazardTransformerBase;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Untested
public class HazardSystem {

	/*
	 * Map for OreDict entries, always evaluated first. Avoid registering HazardData with 'doesOverride', as internal order is based on the item's ore dict keys.
	 */
	public static final HashMap<String, HazardData> oreMap = new HashMap<>();
	/*
	 * Map for items, either with wildcard meta or stuff that's expected to have a variety of damage values, like tools.
	 */
	public static final HashMap<Item, HazardData> itemMap = new HashMap<>();
	/*
	 * Very specific stacks with item and meta matching. ComparableStack does not support NBT matching, to scale hazards with NBT please use HazardModifiers.
	 */
	public static final HashMap<ComparableStack, HazardData> stackMap = new HashMap<>();
	/*
	 * For items that should, for whichever reason, be completely exempt from the hazard system.
	 */
	public static final HashSet<ComparableStack> blacklist = new HashSet<>();
	/*
	 * List of hazard transformers, called in order before and after unrolling all the HazardEntries.
	 */
	public static final List<HazardTransformerBase> trafos = new ArrayList<>();
	
	/**
	 * Automatically casts the first parameter and registers it to the HazSys
	 * @param o
	 * @param data
	 */
	public static void register(Object o, HazardData data) {

		if(o instanceof String)
			oreMap.put((String)o, data);
		if(o instanceof Item)
			itemMap.put((Item)o, data);
		if(o instanceof Block)
			itemMap.put(Item.getItemFromBlock((Block)o), data);
		if(o instanceof ItemStack)
			stackMap.put(new ComparableStack((ItemStack)o), data);
		if(o instanceof ComparableStack)
			stackMap.put((ComparableStack)o, data);
	}
	
	/**
	 * Prevents the stack from returning any HazardData
	 * @param stack
	 */
	public static void blacklist(ItemStack stack) {
		blacklist.add(new ComparableStack(stack).makeSingular());
	}
	
	public static boolean isItemBlacklisted(ItemStack stack) {
		return blacklist.contains(new ComparableStack(stack).makeSingular());
	}
	
	/**
	 * Will return a full list of applicable HazardEntries for this stack.
	 * <br><br>ORDER:
	 * <ol>
	 * <li>ore dict (if multiple keys, in order of the ore dict keys for this stack)
	 * <li>item
	 * <li>item stack
	 * </ol>
	 * 
	 * "Applicable" means that entries that are overridden or excluded via mutex are not in this list.
	 * Entries that are marked as "overriding" will delete all fetched entries that came before it.
	 * Entries that use mutex will prevent subsequent entries from being considered, shall they collide. The mutex system already assumes that
	 * two keys are the same in priority, so the flipped order doesn't matter.
	 * @param stack
	 * @return
	 */
	public static List<HazardEntry> getHazardsFromStack(ItemStack stack) {
		
		if(isItemBlacklisted(stack)) {
			return new ArrayList<>();
		}
		
		List<HazardData> chronological = new ArrayList<>();
		
		/// ORE DICT ///
		int[] ids = OreDictionary.getOreIDs(stack);
		for(int id : ids) {
			String name = OreDictionary.getOreName(id);
			
			if(oreMap.containsKey(name))
				chronological.add(oreMap.get(name));
		}
		
		/// ITEM ///
		if(itemMap.containsKey(stack.getItem()))
			chronological.add(itemMap.get(stack.getItem()));
		
		/// STACK ///
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if(stackMap.containsKey(comp))
			chronological.add(stackMap.get(comp));
		
		List<HazardEntry> entries = new ArrayList<>();
		
		for(HazardTransformerBase trafo : trafos) {
			trafo.transformPre(stack, entries);
		}
		
		int mutex = 0;
		
		for(HazardData data : chronological) {
			//if the current data is marked as an override, purge all previous data
			if(data.doesOverride)
				entries.clear();
			
			if((data.getMutex() & mutex) == 0) {
				entries.addAll(data.entries);
				mutex = mutex | data.getMutex();
			}
		}
		
		for(HazardTransformerBase trafo : trafos) {
			trafo.transformPost(stack, entries);
		}
		
		return entries;
	}
	
	/**
	 * Will grab and iterate through all assigned hazards of the given stack and apply their effects to the holder.
	 * @param stack
	 * @param entity
	 */
	public static void applyHazards(ItemStack stack, EntityLivingBase entity) {
		List<HazardEntry> hazards = getHazardsFromStack(stack);
		
		for(HazardEntry hazard : hazards) {
			hazard.applyHazard(stack, entity);
		}
	}
	
	/**
	 * Will apply the effects of all carried items, including the armor inventory.
	 * @param player
	 */
	public static void updatePlayerInventory(EntityPlayer player) {

		for(int i = 0; i < player.inventory.mainInventory.size(); i++) {
			
			ItemStack stack = player.inventory.mainInventory.get(i);
			if(stack != null) {
				applyHazards(stack, player);
				
				if(stack.isEmpty()) {
					player.inventory.mainInventory.set(i, ItemStack.EMPTY);
				}
			}
		}
		
		for(ItemStack stack : player.inventory.armorInventory) {
			if(stack != null) {
				applyHazards(stack, player);
			}
		}
	}

	public static void updateLivingInventory(EntityLivingBase entity) {
		
		for(int i = 0; i < 6; i++) {
			ItemStack stack = entity.getItemStackFromSlot(EntityEquipmentSlot.values()[i]);

			if(stack != null) {
				applyHazards(stack, entity);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void addFullTooltip(ItemStack stack, EntityPlayer player, List<String> list) {
		
		List<HazardEntry> hazards = getHazardsFromStack(stack);
		
		for(HazardEntry hazard : hazards) {
			hazard.type.addHazardInformation(player, list, hazard.baseLevel, stack, hazard.mods);
		}
	}
}