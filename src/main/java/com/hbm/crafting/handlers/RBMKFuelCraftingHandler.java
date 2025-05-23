package com.hbm.crafting.handlers;

import com.hbm.items.machine.ItemRBMKRod;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class RBMKFuelCraftingHandler extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	/**
	 * The only rules for matching is that the item is fuel (meta and NBT don't matter) and that it's the only stack in the grid
	 */
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		if(!hasExactlyOneStack(inventory))
			return false;
		
		ItemStack stack = getFirstStack(inventory);
		
		return stack.getItem() instanceof ItemRBMKRod && ((ItemRBMKRod)stack.getItem()).pellet != null &&
				ItemRBMKRod.getHullHeat(stack) < 50 && ItemRBMKRod.getCoreHeat(stack) < 50;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		if(!hasExactlyOneStack(inventory))
			return ItemStack.EMPTY;
		
		ItemStack stack = getFirstStack(inventory);
		
		if(stack.getItem() instanceof ItemRBMKRod) {
			
			ItemRBMKRod rod = (ItemRBMKRod)stack.getItem();
			
			if(rod.pellet == null)
				return ItemStack.EMPTY;
			
			if(ItemRBMKRod.getEnrichment(stack) > 0.99D)
				return ItemStack.EMPTY;
			
			if(ItemRBMKRod.getHullHeat(stack) < 50 && ItemRBMKRod.getCoreHeat(stack) < 50) {
				ItemStack result = new ItemStack(rod.pellet, 8);
				int enrichment = 4 - MathHelper.clamp((int)Math.ceil(ItemRBMKRod.getEnrichment(stack) * 5 - 1), 0, 4);
				int meta = enrichment + (ItemRBMKRod.getPoisonLevel(stack) >= 0.5D ? 5 : 0);
				result.setItemDamage(meta);
				return result;
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
	private boolean hasExactlyOneStack(InventoryCrafting inventory) {
		
		boolean hasOne = false;

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				
				ItemStack stack = inventory.getStackInRowAndColumn(j, i);
				
				if(!stack.isEmpty()) {
					
					if(!hasOne)
						hasOne = true;
					else
						return false;
				}
			}
		}
		
		return hasOne;
	}
	
	private ItemStack getFirstStack(InventoryCrafting inventory) {

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				
				ItemStack stack = inventory.getStackInRowAndColumn(j, i);
				
				if(stack != null && !stack.isEmpty()) {
					return stack;
				}
			}
		}
		
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isDynamic(){
		return true;
	}
	
	@Override
	public boolean canFit(int width, int height){
		return width >= 1 && height >= 1;
	}

}