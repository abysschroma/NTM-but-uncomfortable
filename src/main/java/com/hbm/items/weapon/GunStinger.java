package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityRocketHoming;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import java.util.List;

public class GunStinger extends Item {

	public GunStinger(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
        if(this == Armory.gun_stinger)
        	this.setMaxDamage(500);
        if(this == Armory.gun_skystinger)
        	this.setMaxDamage(1000);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer)entityLiving;
		if(player.getHeldItemMainhand() == stack && (player.getHeldItemOffhand().getItem() == Armory.gun_stinger || player.getHeldItemOffhand().getItem() == Armory.gun_skystinger)){
			player.getHeldItemOffhand().onPlayerStoppedUsing(worldIn, entityLiving, timeLeft);
		}
		int j = this.getMaxItemUseDuration(stack) - timeLeft;

		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, j, Library.hasInventoryItem(player.inventory, Armory.gun_stinger_ammo));
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		j = event.getCharge();

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		if (flag || Library.hasInventoryItem(player.inventory, Armory.gun_stinger_ammo)) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (j < 25.0D) {
				return;
			}

			if (j > 25.0F) {
				f = 25.0F;
			}

			stack.damageItem(1, player);
			
			if(this == Armory.gun_stinger)
				worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundEvents.rpgShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);
			if(this == Armory.gun_skystinger)
				worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundEvents.rpgShoot, SoundCategory.PLAYERS, 1.0F, 0.5F);

			Library.consumeInventoryItem(player.inventory, Armory.gun_stinger_ammo);

			if (!worldIn.isRemote) {
				EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
				if(this == Armory.gun_stinger) {
					EntityRocketHoming entityarrow = new EntityRocketHoming(worldIn, player, 1.0F, hand);
					if(player.isSneaking())
						entityarrow.homingRadius = 0;
					worldIn.spawnEntity(entityarrow);
				}
				
				if(this == Armory.gun_skystinger) {
					
					if(player.isSneaking()) {
						EntityRocketHoming entityarrow = new EntityRocketHoming(worldIn, player, 1.5F, hand);
						EntityRocketHoming entityarrow1 = new EntityRocketHoming(worldIn, player, 1.5F, hand);
						entityarrow.homingMod = 12;
						entityarrow1.homingMod = 12;
						entityarrow.motionX += worldIn.rand.nextGaussian() * 0.2;
						entityarrow.motionY += worldIn.rand.nextGaussian() * 0.2;
						entityarrow.motionZ += worldIn.rand.nextGaussian() * 0.2;
						entityarrow1.motionX += worldIn.rand.nextGaussian() * 0.2;
						entityarrow1.motionY += worldIn.rand.nextGaussian() * 0.2;
						entityarrow1.motionZ += worldIn.rand.nextGaussian() * 0.2;
						entityarrow.setIsCritical(true);
						entityarrow1.setIsCritical(true);
						worldIn.spawnEntity(entityarrow);
						worldIn.spawnEntity(entityarrow1);
					} else {
						EntityRocketHoming entityarrow = new EntityRocketHoming(worldIn, player, 2.0F, hand);
						entityarrow.homingMod = 8;
						entityarrow.homingRadius *= 50;
						entityarrow.setIsCritical(true);
						worldIn.spawnEntity(entityarrow);
					}
				}
			}
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(this == Armory.gun_stinger) {
        	list.add("Woosh, beep-beep-beep!");
			list.add("");
			list.add("Ammo: §eStinger Rockets");
			list.add(" Projectiles target entities.");
			list.add(" Projectiles explode on impact.");
			list.add(" Alt-fire disables homing effect.");
        }
        if(this == Armory.gun_skystinger) {
        	list.add("Oh, I get it, because of the...nyeees!");
        	list.add("It all makes sense now!");
			list.add("");
			list.add("Ammo: §eStinger Rockets");
			list.add(" Projectiles target entities.");
			list.add(" Projectiles explode on impact.");
			list.add(" Alt-fire fires a second rocket for free.");
			list.add("");
			list.add(I18nUtil.resolveKey("trait.legendaryweap"));
        }
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ArrowNockEvent event = new ArrowNockEvent(playerIn, playerIn.getHeldItem(handIn), handIn, worldIn, Library.hasInventoryItem(playerIn.inventory, Armory.gun_stinger_ammo));
		MinecraftForge.EVENT_BUS.post(event);
		playerIn.setActiveHand(handIn);
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public int getItemEnchantability() {
		return 1;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4, 0));
		}
		return map;
	}
}
