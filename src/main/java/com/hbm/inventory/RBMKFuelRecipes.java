package com.hbm.inventory;

import java.util.LinkedHashMap;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.items.ModItems.RBMKFuel;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class RBMKFuelRecipes {
	public static Random rand = new Random();
	public static LinkedHashMap<ItemStack, ItemStack> recipes = new LinkedHashMap<>();
	
	public static void registerRecipes() {
		addRod(RBMKFuel.rbmk_fuel_ueu);
		addRod(RBMKFuel.rbmk_fuel_meu);
		addRod(RBMKFuel.rbmk_fuel_heu233);
		addRod(RBMKFuel.rbmk_fuel_heu235);
		addRod(RBMKFuel.rbmk_fuel_thmeu);
		addRod(RBMKFuel.rbmk_fuel_lep);
		addRod(RBMKFuel.rbmk_fuel_mep);
		addRod(RBMKFuel.rbmk_fuel_hep239);
		addRod(RBMKFuel.rbmk_fuel_hep241);
		addRod(RBMKFuel.rbmk_fuel_lea);
		addRod(RBMKFuel.rbmk_fuel_mea);
		addRod(RBMKFuel.rbmk_fuel_hea241);
		addRod(RBMKFuel.rbmk_fuel_hea242);
		addRod(RBMKFuel.rbmk_fuel_men);
		addRod(RBMKFuel.rbmk_fuel_hen);
		addRod(RBMKFuel.rbmk_fuel_mox);
		addRod(RBMKFuel.rbmk_fuel_les);
		addRod(RBMKFuel.rbmk_fuel_mes);
		addRod(RBMKFuel.rbmk_fuel_hes);
		addRod(RBMKFuel.rbmk_fuel_leaus);
		addRod(RBMKFuel.rbmk_fuel_heaus);
		addRod(RBMKFuel.rbmk_fuel_unobtainium);
		addRod(RBMKFuel.rbmk_fuel_ra226be);
		addRod(RBMKFuel.rbmk_fuel_po210be);
		addRod(RBMKFuel.rbmk_fuel_pu238be);
		addRod(RBMKFuel.rbmk_fuel_balefire_gold);
		addRod(RBMKFuel.rbmk_fuel_flashlead);
		addRod(RBMKFuel.rbmk_fuel_zfb_bismuth);
		addRod(RBMKFuel.rbmk_fuel_zfb_pu241);
		addRod(RBMKFuel.rbmk_fuel_zfb_am_mix);
		addRod(RBMKFuel.rbmk_fuel_balefire);
		addRod(RBMKFuel.rbmk_fuel_drx);
	}

	public static void addRod(ItemRBMKRod rod){
		for(int e = 0; e<5; e++){
			addRecipe(makeRBMKRod(rod, e, false), makeRBMKPellet(makeRBMKRod(rod, e, false)));
		}
		for(int e = 0; e<5; e++){
			addRecipe(makeRBMKRod(rod, e, true), makeRBMKPellet(makeRBMKRod(rod, e, true)));
		}
	}

	public static void addRecipe(ItemStack input, ItemStack output){
		recipes.put(input, output);
	}

	public static ItemStack makeRBMKRod(ItemRBMKRod rod, int enrichment, boolean xenon){
		ItemStack fuelRod = new ItemStack(rod);
		ItemRBMKRod.setCoreHeat(fuelRod, 20D+rand.nextDouble()*29.9D);
		ItemRBMKRod.setHullHeat(fuelRod, 20D+rand.nextDouble()*29.9D);
		ItemRBMKRod.setPoison(fuelRod, xenon ? 50D+rand.nextDouble()*50D : rand.nextDouble()*49.9D);
		ItemRBMKRod.setYield(fuelRod, rod.yield * Math.min(0.99D, ((-enrichment-1)*0.2D + 1D + rand.nextDouble()*0.199D)));
		return fuelRod;
	}

	public static ItemStack makeRBMKPellet(ItemStack rod){
		ItemStack result = new ItemStack(((ItemRBMKRod)rod.getItem()).pellet, 8);
		int enrichment = 4 - MathHelper.clamp((int)Math.ceil(ItemRBMKRod.getEnrichment(rod) * 5 - 1), 0, 4);
		int meta = enrichment + (ItemRBMKRod.getPoisonLevel(rod) >= 0.5D ? 5 : 0);
		result.setItemDamage(meta);
		return result;
	}
}
