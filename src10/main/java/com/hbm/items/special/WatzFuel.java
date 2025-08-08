package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class WatzFuel extends ItemBase {

	public int lifeTime;
	public int power;
	public float powerMultiplier;
	public int heat;
	public float heatMultiplier;
	public float decayMultiplier;

	/**
	 * Constructor for a new Watz fuel pellet
	 * @param lifeTime
	 * @param power
	 * @param powerMultiplier
	 * @param heat
	 * @param heatMultiplier
	 * @param decayMultiplier
	 */
	public WatzFuel(int lifeTime, int power, float powerMultiplier, int heat, float heatMultiplier, float decayMultiplier, String s) {
		super(s);
		this.lifeTime = lifeTime * 100;
		this.power = power/10;
		this.powerMultiplier = powerMultiplier;
		this.heat = heat;
		this.heatMultiplier = heatMultiplier;
		this.decayMultiplier = decayMultiplier;
		this.setMaxDamage(100);
		this.canRepair = false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add("Max age:          " + this.lifeTime/100 + " ticks");
		list.add("Power per tick:  " + (power) + "HE");
		list.add("Power multiplier: " + (powerMultiplier >= 1 ? "+" : "") + (Math.round(powerMultiplier * 1000) * .10 - 100) + "%");
		list.add("Heat provided:   " + heat + " heat");
		list.add("Heat multiplier:   " + (heatMultiplier >= 1 ? "+" : "") + (Math.round(heatMultiplier * 1000) * .10 - 100) + "%");
		list.add("Decay multiplier: " + (decayMultiplier >= 1 ? "+" : "") + (Math.round(decayMultiplier * 1000) * .10 - 100) + "%");
	}
	
	public static void setLifeTime(ItemStack stack, int time) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.getTagCompound().setInteger("life", time);
	}
	
	public static void updateDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.setItemDamage((int)((double)getLifeTime(stack) / (double)((WatzFuel)stack.getItem()).lifeTime * 100D));
	}
	
	public static int getLifeTime(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			return 0;
		}
		
		return stack.getTagCompound().getInteger("life");
	}
}
