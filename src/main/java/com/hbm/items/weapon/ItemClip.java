package com.hbm.items.weapon;

import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Armory;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.items.special.ItemSimpleConsumable;
import com.hbm.lib.Library;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemClip extends Item {

	public ItemClip(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setMaxDamage(1);
		this.setMaxStackSize(32);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		stack.shrink(1);;
		if(stack.getCount() <= 0)
			stack.damageItem(5, player);
		
		//REVOLVERS
		if(this == Armory.clip_revolver_iron)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_iron_ammo, 24));
		}
		
		if(this == Armory.clip_revolver)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_ammo, 12));
		}
		
		if(this == Armory.clip_revolver_gold)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_gold_ammo, 12));
		}

		if(this == Armory.clip_revolver_lead)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_lead_ammo, 12));
		}
		
		if(this == Armory.clip_revolver_schrabidium)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_schrabidium_ammo, 12));
		}

		if(this == Armory.clip_revolver_cursed)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_cursed_ammo, 17));
		}

		if(this == Armory.clip_revolver_nightmare)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_nightmare_ammo, 6));
		}
		
		if(this == Armory.clip_revolver_nightmare2)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_nightmare2_ammo, 6));
		}

		if(this == Armory.clip_revolver_pip)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_44_pip, 6));
		}
		
		if(this == Armory.clip_revolver_nopip)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_44, 12));
		}


		//EXPLOSIVES
		if(this == Armory.clip_rpg)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_rocket, 6));
		}

		if(this == Armory.clip_stinger)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_stinger_ammo, 6));
		}

		if(this == Armory.clip_fatman)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_nuke, 6));
		}

		if(this == Armory.clip_mirv)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_mirv, 3));
		}

		if(this == Armory.clip_bf)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_bf_ammo, 2));
		}


		//MAGAZINES
		if(this == Armory.clip_mp40)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_9mm, 32));
		}

		if(this == Armory.clip_uzi)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_22lr, 32));
		}

		if(this == Armory.clip_uboinik)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_12gauge, 24));
		}
		
		if(this == Armory.clip_lever_action)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_20gauge, 24));
		}
		
		if(this == Armory.clip_bolt_action)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_20gauge_slug, 24));
		}


		
		if(this == Armory.clip_osipr)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_osipr_ammo, 30));
		}

		if(this == Armory.clip_immolator)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_immolator_ammo, 64));
		}
		
		if(this == Armory.clip_cryolator)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_cryolator_ammo, 64));
		}

		if(this == Armory.clip_mp)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_566_gold, 40));
		}
		
		if(this == Armory.clip_xvl1456)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_xvl1456_ammo, 64));
		}
		
		if(this == Armory.clip_emp)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_emp_ammo, 6));
		}
		
		if(this == Armory.clip_jack)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_jack_ammo, 12));
		}
		
		if(this == Armory.clip_spark)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_spark_ammo, 4));
		}
		
		if(this == Armory.clip_hp)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_hp_ammo, 12));
		}
		
		if(this == Armory.clip_euthanasia)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_euthanasia_ammo, 16));
		}
		
		if(this == Armory.clip_defabricator)
		{
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_defabricator_ammo, 16));
		}
		
		if(this == Armory.ammo_container)
		{
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_iron))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_iron_ammo, 24));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_ammo, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_gold))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_gold_ammo, 4));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_lead))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_lead_ammo, 6));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_schrabidium))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_schrabidium_ammo, 2));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_cursed))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_cursed_ammo, 8));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_nightmare))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_nightmare_ammo, 6));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_nightmare2))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_nightmare2_ammo, 3));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_pip))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_44_pip, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_nopip))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_44, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_blackjack))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_44_bj, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_red))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_44, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_calamity))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_50bmg, 16));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_calamity_dual))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_50bmg, 32));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_minigun)) {
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
			}
			if(Library.hasInventoryItem(player.inventory, Armory.gun_avenger)) {
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
			}
			if(Library.hasInventoryItem(player.inventory, Armory.gun_lacunae)) {
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_5mm, 64));
			}
			if(Library.hasInventoryItem(player.inventory, Armory.gun_rpg))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_rocket, 3));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_stinger))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_stinger_ammo, 2));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_skystinger))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_stinger_ammo, 2));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_fatman))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_nuke, 2));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_proto))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_nuke, 8));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_mirv))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_mirv, 1));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_bf))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_bf_ammo, 1));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_mp40))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_9mm, 32));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_uzi))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_22lr, 32));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_uzi_silencer))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_22lr, 32));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_uzi_saturnite))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_22lr, 32));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_uzi_saturnite_silencer))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_22lr, 32));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_uboinik))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_12gauge, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_lever_action))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_20gauge, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_lever_action_dark))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_20gauge, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_lever_action_sonata))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_20gauge, 1));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_bolt_action))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_20gauge_flechette, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_bolt_action_green))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_20gauge_flechette, 12));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_xvl1456))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_xvl1456_ammo, 40));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_osipr)) {
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_osipr_ammo, 30));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_osipr_ammo2, 1));
			}
			if(Library.hasInventoryItem(player.inventory, Armory.gun_immolator))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_immolator_ammo, 40));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_cryolator))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_cryolator_ammo, 40));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_mp))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.ammo_566_gold, 34));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_zomg))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Nuggies.nugget_euphemium, 1));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_emp))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_emp_ammo, 8));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_inverted))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_ammo, 1));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_revolver_inverted))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_revolver_ammo, 1));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_jack))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_jack_ammo, 3));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_spark))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_spark_ammo, 2));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_hp))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_hp_ammo, 6));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_euthanasia))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_euthanasia_ammo, 8));
			if(Library.hasInventoryItem(player.inventory, Armory.gun_defabricator))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(Armory.gun_defabricator_ammo, 6));
		}
		return super.onItemRightClick(worldIn, player, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == Armory.ammo_container)
		{
			tooltip.add("Gives ammo for all held weapons.");
		}
	}
}
