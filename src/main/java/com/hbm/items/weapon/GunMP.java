package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Armory;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GunMP extends Item {
	
	Random rand = new Random();
	
	public GunMP(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player1, int count) {
		if(!(player1 instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) player1;
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == Armory.gun_mp){
			player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
		}
		World world = player.world;

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, Armory.ammo_566_gold)) && count % 3 == 0) {
			EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 100, 150, false, false, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
			entityarrow.setDamage(100 + rand.nextInt(50));

			// world.playSoundAtEntity(player, "random.explode", 1.0F, 1.5F +
			// (rand.nextFloat() / 4));
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundEvents.rifleShoot, SoundCategory.PLAYERS, 1.0F, 0.8F + (rand.nextFloat() * 0.4F));

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				Library.consumeInventoryItem(player.inventory, Armory.ammo_566_gold);
			}

			if (!world.isRemote) {
				world.spawnEntity(entityarrow);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Isn't that name a little contrary,");
		list.add("you can't be a pacifist AND");
		list.add("shoot people. Logic errors aside,");
		list.add("whose blood is that? The former");
		list.add("user's? The victim's? Both?");
		list.add("");
		list.add("Ammo: §eSmall Propellantless Machine Gun Round");
		list.add("Damage: 100 - 150");
		list.add("");
		list.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 5, 0));
		}
		return map;
	}

}
