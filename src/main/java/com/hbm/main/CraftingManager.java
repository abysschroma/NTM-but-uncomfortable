package com.hbm.main;

import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.crafting.handlers.MKUCraftingHandler;
import com.hbm.crafting.handlers.RBMKFuelCraftingHandler;
import com.hbm.crafting.handlers.SmallReactorFuelCraftingHandler;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.BedrockOreRegistry;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.*;
import com.hbm.items.ModItems.Materials.*;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.machine.ItemFFFluidDuct;
import com.hbm.items.machine.ItemFluidTank;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.items.machine.ItemFuelRod;
import com.leafia.contents.control.fuel.nuclearfuel.ItemLeafiaRod;
import com.hbm.items.special.ItemCell;
import com.hbm.items.special.ItemHot;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.items.special.ItemBedrockOre;
import com.hbm.items.tool.ItemBombCaller;
import com.hbm.items.tool.ItemBombCaller.EnumCallerType;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.weapon.GunB92Cell;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnchantmentUtil;

import com.leafia.contents.gear.utility.ItemFuzzyIdentifier;
import com.leafia.contents.machines.reactors.pwr.debris.CraftingPWRDebris;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingManager {

	public static RegistryEvent.Register<IRecipe> hack;
	public static boolean registeringFluids = false;

	public static void init(){
		if(!GeneralConfig.recipes) {
			return;
		}
		addCrafting();
		addSmelting();

		hack.getRegistry().register(new SmallReactorFuelCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "reactor_fuel_crafting_handler")));
		hack.getRegistry().register(new RBMKFuelCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "rbmk_fuel_crafting_handler")));
		hack.getRegistry().register(new MKUCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "mku_crafting_handler")));
		hack.getRegistry().register(new CraftingPWRDebris().setRegistryName(new ResourceLocation(RefStrings.MODID, "pwr_debris_crafting_handler")));
	}

	public static void addCrafting(){

		addRecipeAuto(new ItemStack(ModItems.redstone_sword, 1), new Object[] { "R", "R", "S", 'R', Blocks.REDSTONE_BLOCK, 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ModItems.big_sword, 1), new Object[] { "QIQ", "QIQ", "GSG", 'G', Items.GOLD_INGOT, 'S', Items.STICK, 'I', Items.IRON_INGOT, 'Q', Items.QUARTZ });

		addRecipeAuto(new ItemStack(ModItems.egg_balefire_shard, 1), new Object[] { "##", "##", '#', Powders.powder_balefire });
		addRecipeAuto(new ItemStack(ModItems.board_copper, 1), new Object[] { "TTT", "TTT", 'T', CU.plate() });
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_red, 1), new Object[] { "C", "R", "C", 'C', ModItems.hazmat_cloth, 'R', BAKELITE.dust() });
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_grey, 1), new Object[] { "DPD", "ICI", "DLD", 'C', ModItems.hazmat_cloth_red, 'P', IRON.plate(), 'L', PB.plate(), 'I', ANY_RUBBER.ingot(), 'D', I.dustTiny() });
		addRecipeAuto(new ItemStack(ModItems.asbestos_cloth, 8), new Object[] { "SCS", "CPC", "SCS", 'S', Items.STRING, 'P', BR.dust(), 'C', Blocks.WOOL });
		addRecipeAuto(new ItemStack(ModItems.bolt_dura_steel, 4), new Object[] { "D", "D", 'D', DURA.ingot() });
		addRecipeAuto(new ItemStack(ModItems.pipes_steel, 1), new Object[] { "B", "B", "B", 'B', STEEL.block() });
		addRecipeAuto(new ItemStack(ModItems.bolt_tungsten, 4), new Object[] { "D", "D", 'D', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.bolt_compound, 1), new Object[] { "PDP", "PTP", "PDP", 'D', ModItems.bolt_dura_steel, 'T', ModItems.bolt_tungsten, 'P', TI.plate() });
		addRecipeAuto(new ItemStack(ModItems.pellet_coal, 1), new Object[] { "PFP", "FOF", "PFP", 'P', COAL.dust(), 'F', Items.FLINT, 'O', ModBlocks.gravel_obsidian });

		addRecipeAuto(new ItemStack(ModBlocks.marker_structure, 1), new Object[] { "L", "G", "R", 'L', LAPIS.dust(), 'G', Items.GLOWSTONE_DUST, 'R', Blocks.REDSTONE_TORCH });

		addRecipeAuto(new ItemStack(ModBlocks.conveyor, 16), new Object[] { "LLL", "I I", "LLL", 'L', Items.LEATHER, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.conveyor, 64), new Object[] { "LLL", "I I", "LLL", 'L', RUBBER.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_express, 8), new Object[] { "CCC", "CLC", "CCC", 'C', ModBlocks.conveyor, 'L', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.lubricant)) });

		Object[] craneCasing = new Object[] {
				Blocks.STONEBRICK, 1,
				IRON.ingot(), 2,
				STEEL.ingot(), 4
		};

		for(int i = 0; i < craneCasing.length / 2; i++) {
			Object casing = craneCasing[i * 2];
			int amount = (int) craneCasing[i * 2 + 1];
			addRecipeAuto(new ItemStack(ModBlocks.crane_inserter, amount), new Object[] { "CCC", "C C", "CBC", 'C', casing, 'B', ModBlocks.conveyor });
			addRecipeAuto(new ItemStack(ModBlocks.crane_ejector, amount), new Object[] { "CCC", "CPC", "CBC", 'C', casing, 'B', ModBlocks.conveyor, 'P', ModItems.piston_pneumatic });
		}
		
		addRecipeAuto(new ItemStack(ModItems.circuit_raw, 1), new Object[] { "A", "R", "S", 'S', STEEL.plate(), 'R', REDSTONE.dust(), 'A', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier1, 1), new Object[] { "CPC", 'C', ModItems.circuit_aluminium, 'P', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier2, 1), new Object[] { "CPC", 'C', ModItems.circuit_copper, 'P', NETHERQUARTZ.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier3, 1), new Object[] { "CPC", 'C', ModItems.circuit_red_copper, 'P', GOLD.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier4, 1), new Object[] { "CPC", 'C', ModItems.circuit_gold, 'P', LAPIS.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier5, 1), new Object[] { "CPC", 'C', ModItems.circuit_schrabidium, 'P', DIAMOND.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier6, 1), new Object[] { "P", "D", "C", 'C', ModItems.circuit_targeting_tier5, 'D', Batteries.battery_potatos, 'P', Powders.powder_spark_mix });
		addShapelessAuto(new ItemStack(ModItems.circuit_aluminium, 2), new Object[] { ModItems.circuit_targeting_tier1 });
		addShapelessAuto(new ItemStack(ModItems.circuit_copper, 2), new Object[] { ModItems.circuit_targeting_tier2 });
		addShapelessAuto(new ItemStack(ModItems.circuit_red_copper, 2), new Object[] { ModItems.circuit_targeting_tier3 });
		addShapelessAuto(new ItemStack(ModItems.circuit_gold, 2), new Object[] { ModItems.circuit_targeting_tier4 });
		addShapelessAuto(new ItemStack(ModItems.circuit_schrabidium, 2), new Object[] { ModItems.circuit_targeting_tier5 });
		addShapelessAuto(new ItemStack(ModItems.bio_wafer, 1), new Object[] { ModItems.biomass_compressed, ModItems.biomass_compressed});

		addShapelessAuto(new ItemStack(Items.GUNPOWDER, 3), new Object[] { S.dust(), KNO.dust(), Items.COAL });
		addShapelessAuto(new ItemStack(Items.GUNPOWDER, 3), new Object[] { S.dust(), KNO.dust(), new ItemStack(Items.COAL, 1, 1) });

		addRecipeAuto(new ItemStack(ModItems.cell, 6), new Object[] { "SSS", "G G", "SSS", 'S', STEEL.plate(), 'G', KEY_ANYPANE });
		addRecipeAuto(ItemCell.getFullCell(ModForgeFluids.deuterium, 8), new Object[] { "DDD", "DTD", "DDD", 'D', new IngredientNBT2(new ItemStack(ModItems.cell)), 'T', ModItems.mike_deut });

		addRecipeAuto(new ItemStack(ModItems.canister_generic, 2), new Object[] { "S ", "AA", "AA", 'S', STEEL.plate(), 'A', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.yellow_barrel, 1), new Object[] { " D ", "LTL", 'D', ModBlocks.block_waste, 'T', ModItems.tank_steel, 'L', PB.plate() });
		addShapelessAuto(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { ModBlocks.yellow_barrel });
		addRecipeAuto(new ItemStack(ModItems.gas_canister, 2), new Object[] { "S ", "AA", "AA", 'A', STEEL.plate(), 'S', CU.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.vitrified_barrel, 1), new Object[] { "LSL", "PWP", "LSL", 'L', PB.plate(), 'S', Blocks.SAND, 'P', ANY_PLASTIC.ingot(), 'W', ModBlocks.block_waste });
		addShapelessAuto(new ItemStack(ModBlocks.block_waste_painted, 1), new Object[] { "dyeYellow", ModBlocks.block_waste });
		addRecipeAuto(new ItemStack(ModBlocks.field_disturber), new Object[] { "ABA", "CDC", "ABA", 'A', STAR.ingot(), 'B', ModItems.circuit_bismuth, 'C', ModBlocks.hadron_coil_magtung, 'D', ModItems.pellet_rtg_gold});
		
		addRecipeAuto(new ItemStack(Blocks.TNT, 4), new Object[] { " S ", "S#S", " S ", '#', ModItems.ball_tnt, 'S', "sand" });
		
		addRecipeAuto(new ItemStack(ModBlocks.block_aluminium, 1), new Object[] { "###", "###", "###", '#', AL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_copper, 1), new Object[] { "###", "###", "###", '#', CU.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_fluorite, 1), new Object[] { "###", "###", "###", '#', F.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.block_niter, 1), new Object[] { "###", "###", "###", '#', KNO.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.block_red_copper, 1), new Object[] { "###", "###", "###", '#', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_steel, 1), new Object[] { "###", "###", "###", '#', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_sulfur, 1), new Object[] { "###", "###", "###", '#', S.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.block_titanium, 1), new Object[] { "###", "###", "###", '#', TI.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_tungsten, 1), new Object[] { "###", "###", "###", '#', W.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_uranium, 1), new Object[] { "###", "###", "###", '#', U.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_thorium, 1), new Object[] { "###", "###", "###", '#', TH232.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_lead, 1), new Object[] { "###", "###", "###", '#', PB.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_trinitite, 1), new Object[] { "###", "###", "###", '#', ModItems.trinitite });
		addRecipeAuto(new ItemStack(ModBlocks.block_waste, 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste });
		addRecipeAuto(new ItemStack(ModBlocks.block_scrap, 1), new Object[] { "##", "##", '#', ModItems.scrap });
		addRecipeAuto(new ItemStack(ModBlocks.block_scrap, 1), new Object[] { "###", "###", "###", '#', ModItems.dust });
		addRecipeAuto(new ItemStack(ModBlocks.block_beryllium, 1), new Object[] { "###", "###", "###", '#', BE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_schrabidium, 1), new Object[] { "###", "###", "###", '#', SA326.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_schrabidium_cluster, 1), new Object[] { "#S#", "SAS", "#S#", '#', SA326.ingot(), 'A', SBD.ingot(), 'S', STAR.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_euphemium, 1), new Object[] { "###", "###", "###", '#', EUPH.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_advanced_alloy, 1), new Object[] { "###", "###", "###", '#', ALLOY.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_magnetized_tungsten, 1), new Object[] { "###", "###", "###", '#', MAGTUNG.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_combine_steel, 1), new Object[] { "###", "###", "###", '#', CMB.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_australium, 1), new Object[] { "###", "###", "###", '#', AUSTRALIUM.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_weidanium, 1), new Object[] { "###", "###", "###", '#', WEIDANIUM.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_reiium, 1), new Object[] { "###", "###", "###", '#', REIIUM.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_unobtainium, 1), new Object[] { "###", "###", "###", '#', UNOBTAINIUM.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_daffergon, 1), new Object[] { "###", "###", "###", '#', DAFFERGON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_verticium, 1), new Object[] { "###", "###", "###", '#', VERTICIUM.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_desh, 1), new Object[] { "###", "###", "###", '#', DESH.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_dura_steel, 1), new Object[] { "###", "###", "###", '#', DURA.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_saturnite, 1), new Object[] { "###", "###", "###", '#', BIGMT.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_ra226, 1), new Object[] { "###", "###", "###", '#', RA226.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_radspice, 1), new Object[] { "###", "###", "###", '#', Ingots.ingot_radspice });
		addRecipeAuto(new ItemStack(ModBlocks.block_polymer, 1), new Object[] { "###", "###", "###", '#', POLYMER.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_bakelite, 1), new Object[] { "###", "###", "###", '#', BAKELITE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_rubber, 1), new Object[] { "###", "###", "###", '#', RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_meteor_cobble, 1), new Object[] { "##", "##", '#', ModItems.fragment_meteorite });
		addRecipeAuto(new ItemStack(ModBlocks.block_meteor_broken, 1), new Object[] { "###", "###", "###", '#', ModItems.fragment_meteorite });
		addRecipeAuto(new ItemStack(ModBlocks.block_yellowcake, 1), new Object[] { "###", "###", "###", '#', Powders.powder_yellowcake });
		addRecipeAuto(new ItemStack(ModBlocks.block_au198, 1), new Object[] { "###", "###", "###", '#', AU198.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_starmetal, 1), new Object[] { "###", "###", "###", '#', STAR.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_niter_reinforced, 1), new Object[] { "TCT", "CNC", "TCT", 'T', TCALLOY.ingot(), 'C', ModBlocks.concrete, 'N', KNO.block() });

		addShapelessAuto(new ItemStack(ModBlocks.machine_acidizer, 1), new Object[] { ModBlocks.machine_crystallizer });
		addShapelessAuto(new ItemStack(ModBlocks.machine_crystallizer, 1), new Object[] { ModBlocks.machine_acidizer });

		addRecipeAuto(new ItemStack(ModBlocks.hazmat, 8), new Object[] { "###", "# #", "###", '#', ModItems.hazmat_cloth });
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth, 1), new Object[] { "#", '#', ModBlocks.hazmat });

		addShapelessAuto(new ItemStack(Ingots.ingot_uranium_fuel, 1), new Object[] { U235.nugget(), U235.nugget(), U235.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget() });
		addShapelessAuto(new ItemStack(Ingots.ingot_uranium_fuel, 1), new Object[] { U233.nugget(), U233.nugget(), U233.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget() });
		addShapelessAuto(new ItemStack(Ingots.ingot_thorium_fuel, 1), new Object[] { U233.nugget(), U233.nugget(), U233.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget() });
		addShapelessAuto(new ItemStack(Ingots.ingot_plutonium_fuel, 1), new Object[] { PU238.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU240.nugget(), PU240.nugget(), PU240.nugget() });
		addShapelessAuto(new ItemStack(Ingots.ingot_mox_fuel, 1), new Object[] { U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget() });
		addShapelessAuto(new ItemStack(Ingots.ingot_mox_fuel, 1), new Object[] { U235.nugget(), U235.nugget(), U235.nugget(), U238.nugget(), U238.nugget(), PU238.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget() });
		addShapelessAuto(new ItemStack(Ingots.ingot_schrabidium_fuel, 1), new Object[] { SA326.nugget(), SA326.nugget(), SA326.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), BE.nugget(), BE.nugget(), BE.nugget() });
		addShapelessAuto(new ItemStack(Ingots.ingot_hes, 1), new Object[] { SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget(), NP237.nugget(), NP237.nugget(), BE.nugget(), BE.nugget() });
		addShapelessAuto(new ItemStack(Ingots.ingot_les, 1), new Object[] { SA326.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), BE.nugget(), BE.nugget(), BE.nugget(), BE.nugget() });

		//AMMO CLIP UNCRAFTING
		addShapelessAuto(new ItemStack(Armory.gun_revolver_iron_ammo, 24), new Object[] { new ItemStack(Armory.clip_revolver_iron) });
		addShapelessAuto(new ItemStack(Armory.gun_revolver_ammo, 12), new Object[] { new ItemStack(Armory.clip_revolver) });
		addShapelessAuto(new ItemStack(Armory.gun_revolver_gold_ammo, 12), new Object[] { new ItemStack(Armory.clip_revolver_gold) });
		addShapelessAuto(new ItemStack(Armory.gun_revolver_lead_ammo, 12), new Object[] { new ItemStack(Armory.clip_revolver_lead) });
		addShapelessAuto(new ItemStack(Armory.gun_revolver_schrabidium_ammo, 12), new Object[] { new ItemStack(Armory.clip_revolver_schrabidium) });
		addShapelessAuto(new ItemStack(Armory.gun_revolver_cursed_ammo, 17), new Object[] { new ItemStack(Armory.clip_revolver_cursed) });
		addShapelessAuto(new ItemStack(Armory.gun_revolver_nightmare_ammo, 6), new Object[] { new ItemStack(Armory.clip_revolver_nightmare) });
		addShapelessAuto(new ItemStack(Armory.gun_revolver_nightmare2_ammo, 6), new Object[] { new ItemStack(Armory.clip_revolver_nightmare2) });
		addShapelessAuto(new ItemStack(Armory.ammo_44_pip, 6), new Object[] { new ItemStack(Armory.clip_revolver_pip) });
		addShapelessAuto(new ItemStack(Armory.ammo_44, 12), new Object[] { new ItemStack(Armory.clip_revolver_nopip) });
		addShapelessAuto(new ItemStack(Armory.ammo_rocket, 6), new Object[] { new ItemStack(Armory.clip_rpg) });
		addShapelessAuto(new ItemStack(Armory.gun_stinger_ammo, 6), new Object[] { new ItemStack(Armory.clip_stinger) });
		addShapelessAuto(new ItemStack(Armory.ammo_nuke, 6), new Object[] { new ItemStack(Armory.clip_fatman) });
		addShapelessAuto(new ItemStack(Armory.ammo_mirv, 3), new Object[] { new ItemStack(Armory.clip_mirv) });
		addShapelessAuto(new ItemStack(Armory.gun_bf_ammo, 2), new Object[] { new ItemStack(Armory.clip_bf) });
		addShapelessAuto(new ItemStack(Armory.ammo_9mm, 32), new Object[] { new ItemStack(Armory.clip_mp40) });
		addShapelessAuto(new ItemStack(Armory.ammo_22lr, 32), new Object[] { new ItemStack(Armory.clip_uzi) });
		addShapelessAuto(new ItemStack(Armory.ammo_12gauge, 24), new Object[] { new ItemStack(Armory.clip_uboinik) });
		addShapelessAuto(new ItemStack(Armory.ammo_20gauge, 24), new Object[] { new ItemStack(Armory.clip_lever_action) });
		addShapelessAuto(new ItemStack(Armory.ammo_20gauge_slug, 24), new Object[] { new ItemStack(Armory.clip_bolt_action) });
		addShapelessAuto(new ItemStack(Armory.gun_osipr_ammo, 30), new Object[] { new ItemStack(Armory.clip_osipr) });
		addShapelessAuto(new ItemStack(Armory.gun_immolator_ammo, 64), new Object[] { new ItemStack(Armory.clip_immolator) });
		addShapelessAuto(new ItemStack(Armory.gun_cryolator_ammo, 64), new Object[] { new ItemStack(Armory.clip_cryolator) });
		addShapelessAuto(new ItemStack(Armory.ammo_566_gold, 40), new Object[] { new ItemStack(Armory.clip_mp) });
		addShapelessAuto(new ItemStack(Armory.gun_xvl1456_ammo, 64), new Object[] { new ItemStack(Armory.clip_xvl1456) });
		addShapelessAuto(new ItemStack(Armory.gun_emp_ammo, 6), new Object[] { new ItemStack(Armory.clip_emp) });
		addShapelessAuto(new ItemStack(Armory.gun_jack_ammo, 12), new Object[] { new ItemStack(Armory.clip_jack) });
		addShapelessAuto(new ItemStack(Armory.gun_spark_ammo, 4), new Object[] { new ItemStack(Armory.clip_spark) });
		addShapelessAuto(new ItemStack(Armory.gun_hp_ammo, 12), new Object[] { new ItemStack(Armory.clip_hp) });
		addShapelessAuto(new ItemStack(Armory.gun_euthanasia_ammo, 16), new Object[] { new ItemStack(Armory.clip_euthanasia) });
		addShapelessAuto(new ItemStack(Armory.gun_defabricator_ammo, 16), new Object[] { new ItemStack(Armory.clip_defabricator) });


		addRecipeAuto(new ItemStack(Ingots.ingot_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_copper) });
		addRecipeAuto(new ItemStack(ModItems.fluorite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_fluorite) });
		addRecipeAuto(new ItemStack(ModItems.niter, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_niter) });
		addRecipeAuto(new ItemStack(Ingots.ingot_red_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_red_copper) });
		addRecipeAuto(new ItemStack(Ingots.ingot_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_steel) });
		addRecipeAuto(new ItemStack(ModItems.sulfur, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_sulfur) });
		addRecipeAuto(new ItemStack(Ingots.ingot_titanium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_titanium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_tungsten) });
		addRecipeAuto(new ItemStack(Ingots.ingot_uranium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_uranium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_th232, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_thorium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_lead, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_lead) });
		addRecipeAuto(new ItemStack(ModItems.trinitite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_trinitite) });
		addRecipeAuto(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste) });
		addRecipeAuto(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste_painted) });
		addRecipeAuto(new ItemStack(Ingots.ingot_beryllium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_beryllium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_schrabidium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_euphemium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_euphemium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_advanced_alloy, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_advanced_alloy) });
		addRecipeAuto(new ItemStack(Ingots.ingot_magnetized_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten) });
		addRecipeAuto(new ItemStack(Ingots.ingot_combine_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_combine_steel) });
		addRecipeAuto(new ItemStack(Ingots.ingot_australium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_australium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_weidanium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_weidanium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_reiium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_reiium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_unobtainium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_unobtainium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_daffergon, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_daffergon) });
		addRecipeAuto(new ItemStack(Ingots.ingot_verticium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_verticium) });
		addRecipeAuto(new ItemStack(Ingots.ingot_desh, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_desh) });
		addRecipeAuto(new ItemStack(Ingots.ingot_dura_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_dura_steel) });
		addRecipeAuto(new ItemStack(Ingots.ingot_saturnite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_saturnite) });
		addRecipeAuto(new ItemStack(Ingots.ingot_ra226, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_ra226) });
		addRecipeAuto(new ItemStack(Ingots.ingot_radspice, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_radspice) });
		addRecipeAuto(new ItemStack(Ingots.ingot_polymer, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_polymer) });
		addRecipeAuto(new ItemStack(Ingots.ingot_bakelite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_bakelite) });
		addRecipeAuto(new ItemStack(Ingots.ingot_rubber, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_rubber) });
		addRecipeAuto(new ItemStack(Powders.powder_yellowcake, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_yellowcake) });
		addRecipeAuto(new ItemStack(Ingots.ingot_au198, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_au198) });
		addRecipeAuto(new ItemStack(Ingots.ingot_starmetal, 9), new Object[] { "#", '#', ModBlocks.block_starmetal });

		addRecipeAuto(new ItemStack(Ingots.ingot_plutonium, 1), new Object[] { "###", "###", "###", '#', PU.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_plutonium, 9), new Object[] { "#", '#', PU.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_pu238, 1), new Object[] { "###", "###", "###", '#', PU238.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_pu238, 9), new Object[] { "#", '#', PU238.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_pu239, 1), new Object[] { "###", "###", "###", '#', PU239.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_pu239, 9), new Object[] { "#", '#', PU239.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_pu240, 1), new Object[] { "###", "###", "###", '#', PU240.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_pu240, 9), new Object[] { "#", '#', PU240.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_th232, 1), new Object[] { "###", "###", "###", '#', TH232.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_th232, 9), new Object[] { "#", '#', TH232.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_uranium, 1), new Object[] { "###", "###", "###", '#', U.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_uranium, 9), new Object[] { "#", '#', U.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_u233, 1), new Object[] { "###", "###", "###", '#',U233.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_u233, 9), new Object[] { "#", '#',U233.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_u235, 1), new Object[] { "###", "###", "###", '#', U235.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_u235, 9), new Object[] { "#", '#', U235.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_u238, 1), new Object[] { "###", "###", "###", '#', U238.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_u238, 9), new Object[] { "#", '#', U238.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_neptunium, 1), new Object[] { "###", "###", "###", '#', NP237.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_neptunium, 9), new Object[] { "#", '#', NP237.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_polonium, 1), new Object[] { "###", "###", "###", '#', PO210.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_polonium, 9), new Object[] { "#", '#', PO210.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_lead, 1), new Object[] { "###", "###", "###", '#', PB.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_lead, 9), new Object[] { "#", '#', PB.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_beryllium, 1), new Object[] { "###", "###", "###", '#', BE.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_beryllium, 9), new Object[] { "#", '#', BE.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', SA326.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_schrabidium, 9), new Object[] { "#", '#', SA326.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', Nuggies.nugget_uranium_fuel });
		addRecipeAuto(new ItemStack(Nuggies.nugget_uranium_fuel, 9), new Object[] { "#", '#', Ingots.ingot_uranium_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_thorium_fuel, 1), new Object[] { "###", "###", "###", '#', Nuggies.nugget_thorium_fuel });
		addRecipeAuto(new ItemStack(Nuggies.nugget_thorium_fuel, 9), new Object[] { "#", '#', Ingots.ingot_thorium_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_plutonium_fuel, 1), new Object[] { "###", "###", "###", '#', Nuggies.nugget_plutonium_fuel });
		addRecipeAuto(new ItemStack(Nuggies.nugget_plutonium_fuel, 9), new Object[] { "#", '#', Ingots.ingot_plutonium_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_mox_fuel, 1), new Object[] { "###", "###", "###", '#', Nuggies.nugget_mox_fuel });
		addRecipeAuto(new ItemStack(Nuggies.nugget_mox_fuel, 9), new Object[] { "#", '#', Ingots.ingot_mox_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_schrabidium_fuel, 1), new Object[] { "###", "###", "###", '#', Nuggies.nugget_schrabidium_fuel });
		addRecipeAuto(new ItemStack(Nuggies.nugget_schrabidium_fuel, 9), new Object[] { "#", '#', Ingots.ingot_schrabidium_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_hes, 1), new Object[] { "###", "###", "###", '#', Nuggies.nugget_hes });
		addRecipeAuto(new ItemStack(Nuggies.nugget_hes, 9), new Object[] { "#", '#', Ingots.ingot_hes });
		addRecipeAuto(new ItemStack(Ingots.ingot_les, 1), new Object[] { "###", "###", "###", '#', Nuggies.nugget_les });
		addRecipeAuto(new ItemStack(Nuggies.nugget_les, 9), new Object[] { "#", '#', Ingots.ingot_les });
		addRecipeAuto(new ItemStack(Ingots.ingot_australium, 1), new Object[] { "###", "###", "###", '#', AUSTRALIUM.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_australium, 9), new Object[] { "#", '#', AUSTRALIUM.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_weidanium, 1), new Object[] { "###", "###", "###", '#', WEIDANIUM.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_weidanium, 9), new Object[] { "#", '#', WEIDANIUM.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_reiium, 1), new Object[] { "###", "###", "###", '#', REIIUM.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_reiium, 9), new Object[] { "#", '#', REIIUM.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_unobtainium, 1), new Object[] { "###", "###", "###", '#', UNOBTAINIUM.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_unobtainium, 9), new Object[] { "#", '#', UNOBTAINIUM.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_daffergon, 1), new Object[] { "###", "###", "###", '#', DAFFERGON.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_daffergon, 9), new Object[] { "#", '#', DAFFERGON.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_verticium, 1), new Object[] { "###", "###", "###", '#', VERTICIUM.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_verticium, 9), new Object[] { "#", '#', VERTICIUM.ingot() });

		addRecipeAuto(new ItemStack(Ingots.ingot_ac227, 1), new Object[] { "###", "###", "###", '#', AC227.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_ac227, 9), new Object[] { "#", '#', AC227.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_actinium, 1), new Object[] { "###", "###", "###", '#', AC.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_actinium, 9), new Object[] { "#", '#', AC.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_cobalt, 1), new Object[] { "###", "###", "###", '#', CO.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_cobalt, 9), new Object[] { "#", '#', CO.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_sr90, 1), new Object[] { "###", "###", "###", '#', SR90.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_sr90, 9), new Object[] { "#", '#', SR90.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_strontium, 1), new Object[] { "###", "###", "###", '#', SR.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_strontium, 9), new Object[] { "#", '#', SR.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_pb209, 1), new Object[] { "###", "###", "###", '#', PB209.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_pb209, 9), new Object[] { "#", '#', PB209.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_gh336, 1), new Object[] { "###", "###", "###", '#', GH336.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_gh336, 9), new Object[] { "#", '#', GH336.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_u238m2, 1), new Object[] { "###", "###", "###", '#',Nuggies.nugget_u238m2 });
		addRecipeAuto(new ItemStack(Nuggies.nugget_u238m2, 9), new Object[] { "#", '#', Ingots.ingot_u238m2 });
		addRecipeAuto(new ItemStack(Ingots.ingot_ra226, 1), new Object[] { "###", "###", "###", '#', RA226.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_ra226, 9), new Object[] { "#", '#', RA226.ingot() });
		addRecipeAuto(new ItemStack(Ingots.ingot_desh, 1), new Object[] { "###", "###", "###", '#', DESH.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_desh, 9), new Object[] { "#", '#', DESH.ingot() });

		addShapelessAuto(new ItemStack(Powders.powder_radspice, 1), new Object[] { CO60.dust(), SR90.dust(), I131.dust(), CS137.dust(), XE135.dust(), AU198.dust(), PB209.dust(), AT209.dust(), AC227.dust() });
		addShapelessAuto(new ItemStack(Powders.powder_radspice_tiny, 1), new Object[] { CO60.dustTiny(), SR90.dustTiny(), I131.dustTiny(), CS137.dustTiny(), XE135.dustTiny(), AU198.dustTiny(), PB209.dustTiny(), AT209.dustTiny(), AC227.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_steel, 1), new Object[] { "###", "###", "###", '#', STEEL.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_steel_tiny, 9), new Object[] { "#", '#', STEEL.dust() });
		addRecipeAuto(new ItemStack(Powders.powder_lithium, 1), new Object[] { "###", "###", "###", '#', LI.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_lithium_tiny, 9), new Object[] { "#", '#', LI.dust() });
		addRecipeAuto(new ItemStack(Powders.powder_cobalt, 1), new Object[] { "###", "###", "###", '#', CO.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_cobalt_tiny, 9), new Object[] { "#", '#', CO.dust() });
		addRecipeAuto(new ItemStack(Powders.powder_neodymium, 1), new Object[] { "###", "###", "###", '#', ND.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_neodymium_tiny, 9), new Object[] { "#", '#', ND.dust() });
		addRecipeAuto(new ItemStack(Powders.powder_niobium, 1), new Object[] { "###", "###", "###", '#', NB.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_niobium_tiny, 9), new Object[] { "#", '#', NB.dust() });
		addRecipeAuto(new ItemStack(Powders.powder_cerium, 1), new Object[] { "###", "###", "###", '#', CE.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_cerium_tiny, 9), new Object[] { "#", '#', CE.dust() });
		addRecipeAuto(new ItemStack(Powders.powder_lanthanium, 1), new Object[] { "###", "###", "###", '#', LA.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_lanthanium_tiny, 9), new Object[] { "#", '#', LA.dust() });
		addRecipeAuto(new ItemStack(Powders.powder_actinium, 1), new Object[] { "###", "###", "###", '#', AC.dustTiny() });
		addRecipeAuto(new ItemStack(Powders.powder_actinium_tiny, 9), new Object[] { "#", '#', AC.dust() });
		addRecipeAuto(new ItemStack(Powders.powder_meteorite, 1), new Object[] { "###", "###", "###", '#', Powders.powder_meteorite_tiny });
		addRecipeAuto(new ItemStack(Powders.powder_meteorite_tiny, 9), new Object[] { "#", '#', Powders.powder_meteorite });
		addRecipeAuto(new ItemStack(Ingots.ingot_solinium, 1), new Object[] { "###", "###", "###", '#', SA327.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_solinium, 9), new Object[] { "#", '#', SA327.ingot() });
		addRecipeAuto(new ItemStack(ModItems.nuclear_waste, 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste_tiny });
		addRecipeAuto(new ItemStack(ModItems.nuclear_waste_tiny, 9), new Object[] { "#", '#', ModItems.nuclear_waste });

		addRecipeAuto(new ItemStack(RetroRods.rod_empty, 16), new Object[] { "SSS", "L L", "SSS", 'S', STEEL.plate(), 'L', PB.plate() });
		addShapelessAuto(new ItemStack(RetroRods.rod_th232, 1), new Object[] { RetroRods.rod_empty, TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_uranium, 1), new Object[] { RetroRods.rod_empty, U.nugget(), U.nugget(), U.nugget(), U.nugget(), U.nugget(), U.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_u233, 1), new Object[] { RetroRods.rod_empty,U233.nugget(),U233.nugget(),U233.nugget(),U233.nugget(),U233.nugget(),U233.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_u235, 1), new Object[] { RetroRods.rod_empty, U235.nugget(), U235.nugget(), U235.nugget(), U235.nugget(), U235.nugget(), U235.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_u238, 1), new Object[] { RetroRods.rod_empty, U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_plutonium, 1), new Object[] { RetroRods.rod_empty, PU.nugget(), PU.nugget(), PU.nugget(), PU.nugget(), PU.nugget(), PU.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_pu238, 1), new Object[] { RetroRods.rod_empty, PU238.nugget(), PU238.nugget(), PU238.nugget(), PU238.nugget(), PU238.nugget(), PU238.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_pu239, 1), new Object[] { RetroRods.rod_empty, PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_pu240, 1), new Object[] { RetroRods.rod_empty, PU240.nugget(), PU240.nugget(), PU240.nugget(), PU240.nugget(), PU240.nugget(), PU240.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_neptunium, 1), new Object[] { RetroRods.rod_empty, NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_polonium, 1), new Object[] { RetroRods.rod_empty, PO210.nugget(), PO210.nugget(), PO210.nugget(), PO210.nugget(), PO210.nugget(), PO210.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_lead, 1), new Object[] { RetroRods.rod_empty, PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_schrabidium, 1), new Object[] { RetroRods.rod_empty, SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_solinium, 1), new Object[] { RetroRods.rod_empty, SA327.nugget(), SA327.nugget(), SA327.nugget(), SA327.nugget(), SA327.nugget(), SA327.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_uranium_fuel, 1), new Object[] { RetroRods.rod_empty, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_thorium_fuel, 1), new Object[] { RetroRods.rod_empty, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_plutonium_fuel, 1), new Object[] { RetroRods.rod_empty, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_mox_fuel, 1), new Object[] { RetroRods.rod_empty, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_schrabidium_fuel, 1), new Object[] { RetroRods.rod_empty, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_euphemium, 1), new Object[] { RetroRods.rod_empty, EUPH.nugget(), EUPH.nugget(), EUPH.nugget(), EUPH.nugget(), EUPH.nugget(), EUPH.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_australium, 1), new Object[] { RetroRods.rod_empty, AUSTRALIUM.nugget(), AUSTRALIUM.nugget(), AUSTRALIUM.nugget(), AUSTRALIUM.nugget(), AUSTRALIUM.nugget(), AUSTRALIUM.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_weidanium, 1), new Object[] { RetroRods.rod_empty, WEIDANIUM.nugget(), WEIDANIUM.nugget(), WEIDANIUM.nugget(), WEIDANIUM.nugget(), WEIDANIUM.nugget(), WEIDANIUM.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_reiium, 1), new Object[] { RetroRods.rod_empty, REIIUM.nugget(), REIIUM.nugget(), REIIUM.nugget(), REIIUM.nugget(), REIIUM.nugget(), REIIUM.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_unobtainium, 1), new Object[] { RetroRods.rod_empty, UNOBTAINIUM.nugget(), UNOBTAINIUM.nugget(), UNOBTAINIUM.nugget(), UNOBTAINIUM.nugget(), UNOBTAINIUM.nugget(), UNOBTAINIUM.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_daffergon, 1), new Object[] { RetroRods.rod_empty, DAFFERGON.nugget(), DAFFERGON.nugget(), DAFFERGON.nugget(), DAFFERGON.nugget(), DAFFERGON.nugget(), DAFFERGON.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_verticium, 1), new Object[] { RetroRods.rod_empty, VERTICIUM.nugget(), VERTICIUM.nugget(), VERTICIUM.nugget(), VERTICIUM.nugget(), VERTICIUM.nugget(), VERTICIUM.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_balefire, 1), new Object[] { RetroRods.rod_empty, ModItems.egg_balefire_shard });

		addShapelessAuto(new ItemStack(RetroRods.rod_ac227, 1), new Object[] { RetroRods.rod_empty, AC227.nugget(), AC227.nugget(), AC227.nugget(), AC227.nugget(), AC227.nugget(), AC227.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_cobalt, 1), new Object[] { RetroRods.rod_empty, CO.nugget(), CO.nugget(), CO.nugget(), CO.nugget(), CO.nugget(), CO.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_co60, 1), new Object[] { RetroRods.rod_empty, CO60.nugget(), CO60.nugget(), CO60.nugget(), CO60.nugget(), CO60.nugget(), CO60.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_ra226, 1), new Object[] { RetroRods.rod_empty, RA226.nugget(), RA226.nugget(), RA226.nugget(), RA226.nugget(), RA226.nugget(), RA226.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_rgp, 1), new Object[] { RetroRods.rod_empty, PURG.nugget(), PURG.nugget(), PURG.nugget(), PURG.nugget(), PURG.nugget(), PURG.nugget() });


		addRodBilletUnload(Billets.billet_uranium, RetroRods.rod_uranium);
		addRodBilletUnload(Billets.billet_u233, RetroRods.rod_u233);
		addRodBilletUnload(Billets.billet_u235, RetroRods.rod_u235);
		addRodBilletUnload(Billets.billet_u238, RetroRods.rod_u238);
		addRodBilletUnload(Billets.billet_th232, RetroRods.rod_th232);
		addRodBilletUnload(Billets.billet_plutonium, RetroRods.rod_plutonium);
		addRodBilletUnload(Billets.billet_pu238, RetroRods.rod_pu238);
		addRodBilletUnload(Billets.billet_pu239, RetroRods.rod_pu239);
		addRodBilletUnload(Billets.billet_pu240, RetroRods.rod_pu240);
		addRodBilletUnload(Billets.billet_neptunium, RetroRods.rod_neptunium);
		addRodBilletUnload(Billets.billet_polonium, RetroRods.rod_polonium);
		addRodBilletUnload(Billets.billet_schrabidium, RetroRods.rod_schrabidium);
		addRodBilletUnload(Billets.billet_solinium, RetroRods.rod_solinium);
		addRodBillet(Billets.billet_uranium_fuel, RetroRods.rod_uranium_fuel);
		addRodBillet(Billets.billet_thorium_fuel, RetroRods.rod_thorium_fuel);
		addRodBillet(Billets.billet_plutonium_fuel, RetroRods.rod_plutonium_fuel);
		addRodBillet(Billets.billet_mox_fuel, RetroRods.rod_mox_fuel);
		addRodBillet(Billets.billet_schrabidium_fuel, RetroRods.rod_schrabidium_fuel);
		addRodBilletUnload(Billets.billet_nuclear_waste, RetroRods.rod_waste );


		addRodBilletUnload(Billets.billet_ac227, RetroRods.rod_ac227);
		addRodBilletUnload(Billets.billet_ra226, RetroRods.rod_ra226);
		addRodBilletUnload(Billets.billet_pu_mix, RetroRods.rod_rgp);
		addRodBilletUnload(Billets.billet_co60, RetroRods.rod_co60);

		addShapelessAuto(new ItemStack(RetroRods.rod_empty, 2), new Object[] { RetroRods.rod_dual_empty });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_empty, 1), new Object[] { RetroRods.rod_empty, RetroRods.rod_empty });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_th232, 1), new Object[] { RetroRods.rod_dual_empty, TH232.ingot(), TH232.nugget(), TH232.nugget(), TH232.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_uranium, 1), new Object[] { RetroRods.rod_dual_empty, U.ingot(), U.nugget(), U.nugget(), U.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_u233, 1), new Object[] { RetroRods.rod_dual_empty,U233.ingot(),U233.nugget(),U233.nugget(),U233.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_u235, 1), new Object[] { RetroRods.rod_dual_empty, U235.ingot(), U235.nugget(), U235.nugget(), U235.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_u238, 1), new Object[] { RetroRods.rod_dual_empty, U238.ingot(), U238.nugget(), U238.nugget(), U238.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_plutonium, 1), new Object[] { RetroRods.rod_dual_empty, PU.ingot(), PU.nugget(), PU.nugget(), PU.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_pu238, 1), new Object[] { RetroRods.rod_dual_empty, PU238.ingot(), PU238.nugget(), PU238.nugget(), PU238.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_pu239, 1), new Object[] { RetroRods.rod_dual_empty, PU239.ingot(), PU239.nugget(), PU239.nugget(), PU239.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_pu240, 1), new Object[] { RetroRods.rod_dual_empty, PU240.ingot(), PU240.nugget(), PU240.nugget(), PU240.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_neptunium, 1), new Object[] { RetroRods.rod_dual_empty, NP237.ingot(), NP237.nugget(), NP237.nugget(), NP237.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_polonium, 1), new Object[] { RetroRods.rod_dual_empty, PO210.ingot(), PO210.nugget(), PO210.nugget(), PO210.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_lead, 1), new Object[] { RetroRods.rod_dual_empty, PB.ingot(), PB.nugget(), PB.nugget(), PB.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_schrabidium, 1), new Object[] { RetroRods.rod_dual_empty, SA326.ingot(), SA326.nugget(), SA326.nugget(), SA326.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_solinium, 1), new Object[] { RetroRods.rod_dual_empty, SA327.ingot(), SA327.nugget(), SA327.nugget(), SA327.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_uranium_fuel, 1), new Object[] { RetroRods.rod_dual_empty, Ingots.ingot_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_thorium_fuel, 1), new Object[] { RetroRods.rod_dual_empty, Ingots.ingot_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_plutonium_fuel, 1), new Object[] { RetroRods.rod_dual_empty, Ingots.ingot_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_mox_fuel, 1), new Object[] { RetroRods.rod_dual_empty, Ingots.ingot_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_schrabidium_fuel, 1), new Object[] { RetroRods.rod_dual_empty, Ingots.ingot_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_balefire, 1), new Object[] { RetroRods.rod_dual_empty, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_euphemium, 1), new Object[] { RetroRods.rod_quad_empty, EUPH.nugget() });

		addShapelessAuto(new ItemStack(RetroRods.rod_dual_ac227, 1), new Object[] { RetroRods.rod_dual_empty, AC227.ingot(), AC227.nugget(), AC227.nugget(), AC227.nugget()});
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_cobalt, 1), new Object[] { RetroRods.rod_dual_empty, CO.ingot(), CO.nugget(), CO.nugget(), CO.nugget()});
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_co60, 1), new Object[] { RetroRods.rod_dual_empty, CO60.ingot(), CO60.nugget(), CO60.nugget(), CO60.nugget()});
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_ra226, 1), new Object[] { RetroRods.rod_dual_empty, RA226.ingot(), RA226.nugget(), RA226.nugget(), RA226.nugget()});
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_rgp, 1), new Object[] { RetroRods.rod_dual_empty, PURG.ingot(), PURG.nugget(), PURG.nugget(), PURG.nugget()});


		addDualRodBilletUnload(Billets.billet_uranium, RetroRods.rod_dual_uranium);
		addDualRodBilletUnload(Billets.billet_u233, RetroRods.rod_dual_u233);
		addDualRodBilletUnload(Billets.billet_u235, RetroRods.rod_dual_u235);
		addDualRodBilletUnload(Billets.billet_u238, RetroRods.rod_dual_u238);
		addDualRodBilletUnload(Billets.billet_th232, RetroRods.rod_dual_th232);
		addDualRodBilletUnload(Billets.billet_plutonium, RetroRods.rod_dual_plutonium);
		addDualRodBilletUnload(Billets.billet_pu238, RetroRods.rod_dual_pu238);
		addDualRodBilletUnload(Billets.billet_pu239, RetroRods.rod_dual_pu239);
		addDualRodBilletUnload(Billets.billet_pu240, RetroRods.rod_dual_pu240);
		addDualRodBilletUnload(Billets.billet_neptunium, RetroRods.rod_dual_neptunium);
		addDualRodBilletUnload(Billets.billet_polonium, RetroRods.rod_dual_polonium);
		addDualRodBilletUnload(Billets.billet_schrabidium, RetroRods.rod_dual_schrabidium);
		addDualRodBilletUnload(Billets.billet_solinium, RetroRods.rod_dual_solinium);
		addDualRodBillet(Billets.billet_uranium_fuel, RetroRods.rod_dual_uranium_fuel);
		addDualRodBillet(Billets.billet_thorium_fuel, RetroRods.rod_dual_thorium_fuel);
		addDualRodBillet(Billets.billet_plutonium_fuel, RetroRods.rod_dual_plutonium_fuel);
		addDualRodBillet(Billets.billet_mox_fuel, RetroRods.rod_dual_mox_fuel);
		addDualRodBillet(Billets.billet_schrabidium_fuel, RetroRods.rod_dual_schrabidium_fuel);
		addDualRodBilletUnload(Billets.billet_nuclear_waste, RetroRods.rod_dual_waste);

		addDualRodBilletUnload(Billets.billet_ac227, RetroRods.rod_dual_ac227);
		addDualRodBilletUnload(Billets.billet_ra226, RetroRods.rod_dual_ra226);
		addDualRodBilletUnload(Billets.billet_pu_mix, RetroRods.rod_dual_rgp);
		addDualRodBilletUnload(Billets.billet_co60, RetroRods.rod_dual_co60);


		addShapelessAuto(new ItemStack(RetroRods.rod_lithium, 1), new Object[] { RetroRods.rod_empty, LI.ingot() });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_lithium, 1), new Object[] { RetroRods.rod_dual_empty, LI.ingot(), LI.ingot() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_lithium, 1), new Object[] { RetroRods.rod_quad_empty, LI.ingot(), LI.ingot(), LI.ingot(), LI.ingot() });
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.tritium, 1), new Object[] { RetroRods.rod_tritium, new IngredientNBT2(new ItemStack(ModItems.cell)) });
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.tritium, 2), new Object[] { RetroRods.rod_dual_tritium, new IngredientNBT2(new ItemStack(ModItems.cell)), new IngredientNBT2(new ItemStack(ModItems.cell)) });
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.tritium, 4), new Object[] { RetroRods.rod_quad_tritium, new IngredientNBT2(new ItemStack(ModItems.cell)), new IngredientNBT2(new ItemStack(ModItems.cell)), new IngredientNBT2(new ItemStack(ModItems.cell)), new IngredientNBT2(new ItemStack(ModItems.cell)) });

		addShapelessAuto(new ItemStack(RetroRods.rod_empty, 4), new Object[] { RetroRods.rod_quad_empty });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_empty, 1), new Object[] { RetroRods.rod_empty, RetroRods.rod_empty, RetroRods.rod_empty, RetroRods.rod_empty });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_empty, 1), new Object[] { RetroRods.rod_dual_empty, RetroRods.rod_dual_empty });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_th232, 1), new Object[] { RetroRods.rod_quad_empty, TH232.ingot(), TH232.ingot(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_uranium, 1), new Object[] { RetroRods.rod_quad_empty, U.ingot(), U.ingot(), U.nugget(), U.nugget(), U.nugget(), U.nugget(), U.nugget(), U.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_u233, 1), new Object[] { RetroRods.rod_quad_empty,U233.ingot(),U233.ingot(),U233.nugget(),U233.nugget(),U233.nugget(),U233.nugget(),U233.nugget(),U233.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_u235, 1), new Object[] { RetroRods.rod_quad_empty, U235.ingot(), U235.ingot(), U235.nugget(), U235.nugget(), U235.nugget(), U235.nugget(), U235.nugget(), U235.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_u238, 1), new Object[] { RetroRods.rod_quad_empty, U238.ingot(), U238.ingot(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_plutonium, 1), new Object[] { RetroRods.rod_quad_empty, PU.ingot(), PU.ingot(), PU.nugget(), PU.nugget(), PU.nugget(), PU.nugget(), PU.nugget(), PU.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_pu238, 1), new Object[] { RetroRods.rod_quad_empty, PU238.ingot(), PU238.ingot(), PU238.nugget(), PU238.nugget(), PU238.nugget(), PU238.nugget(), PU238.nugget(), PU238.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_pu239, 1), new Object[] { RetroRods.rod_quad_empty, PU239.ingot(), PU239.ingot(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_pu240, 1), new Object[] { RetroRods.rod_quad_empty, PU240.ingot(), PU240.ingot(), PU240.nugget(), PU240.nugget(), PU240.nugget(), PU240.nugget(), PU240.nugget(), PU240.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_neptunium, 1), new Object[] { RetroRods.rod_quad_empty, NP237.ingot(), NP237.ingot(), NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_polonium, 1), new Object[] { RetroRods.rod_quad_empty, PO210.ingot(), PO210.ingot(), PO210.nugget(), PO210.nugget(), PO210.nugget(), PO210.nugget(), PO210.nugget(), PO210.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_lead, 1), new Object[] { RetroRods.rod_quad_empty, PB.ingot(), PB.ingot(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_schrabidium, 1), new Object[] { RetroRods.rod_quad_empty, SA326.ingot(), SA326.ingot(), SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_solinium, 1), new Object[] { RetroRods.rod_quad_empty, SA327.ingot(), SA327.ingot(), SA327.nugget(), SA327.nugget(), SA327.nugget(), SA327.nugget(), SA327.nugget(), SA327.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_uranium_fuel, 1), new Object[] { RetroRods.rod_quad_empty, Ingots.ingot_uranium_fuel, Ingots.ingot_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel, Nuggies.nugget_uranium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_thorium_fuel, 1), new Object[] { RetroRods.rod_quad_empty, Ingots.ingot_thorium_fuel, Ingots.ingot_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel, Nuggies.nugget_thorium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_plutonium_fuel, 1), new Object[] { RetroRods.rod_quad_empty, Ingots.ingot_plutonium_fuel, Ingots.ingot_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel, Nuggies.nugget_plutonium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_mox_fuel, 1), new Object[] { RetroRods.rod_quad_empty, Ingots.ingot_mox_fuel, Ingots.ingot_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel, Nuggies.nugget_mox_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_schrabidium_fuel, 1), new Object[] { RetroRods.rod_quad_empty, Ingots.ingot_schrabidium_fuel, Ingots.ingot_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_balefire, 1), new Object[] { RetroRods.rod_quad_empty, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard });

		addShapelessAuto(new ItemStack(RetroRods.rod_quad_ac227, 1), new Object[] { RetroRods.rod_quad_empty, AC227.ingot(), AC227.ingot(), AC227.nugget(), AC227.nugget(), AC227.nugget(), AC227.nugget(), AC227.nugget(), AC227.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_cobalt, 1), new Object[] { RetroRods.rod_quad_empty, CO.ingot(), CO.ingot(), CO.nugget(), CO.nugget(), CO.nugget(), CO.nugget(), CO.nugget(), CO.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_co60, 1), new Object[] { RetroRods.rod_quad_empty, CO60.ingot(), CO60.ingot(), CO60.nugget(), CO60.nugget(), CO60.nugget(), CO60.nugget(), CO60.nugget(), CO60.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_ra226, 1), new Object[] { RetroRods.rod_quad_empty, RA226.ingot(), RA226.ingot(), RA226.nugget(), RA226.nugget(), RA226.nugget(), RA226.nugget(), RA226.nugget(), RA226.nugget() });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_rgp, 1), new Object[] { RetroRods.rod_quad_empty, PURG.ingot(), PURG.ingot(), PURG.nugget(), PURG.nugget(), PURG.nugget(), PURG.nugget(), PURG.nugget(), PURG.nugget() });




		addQuadRodBilletUnload(Billets.billet_uranium, RetroRods.rod_quad_uranium);
		addQuadRodBilletUnload(Billets.billet_u233, RetroRods.rod_quad_u233);
		addQuadRodBilletUnload(Billets.billet_u235, RetroRods.rod_quad_u235);
		addQuadRodBilletUnload(Billets.billet_u238, RetroRods.rod_quad_u238);
		addQuadRodBilletUnload(Billets.billet_th232, RetroRods.rod_quad_th232);
		addQuadRodBilletUnload(Billets.billet_plutonium, RetroRods.rod_quad_plutonium);
		addQuadRodBilletUnload(Billets.billet_pu238, RetroRods.rod_quad_pu238);
		addQuadRodBilletUnload(Billets.billet_pu239, RetroRods.rod_quad_pu239);
		addQuadRodBilletUnload(Billets.billet_pu240, RetroRods.rod_quad_pu240);
		addQuadRodBilletUnload(Billets.billet_neptunium, RetroRods.rod_quad_neptunium);
		addQuadRodBilletUnload(Billets.billet_polonium, RetroRods.rod_quad_polonium);
		addQuadRodBilletUnload(Billets.billet_schrabidium, RetroRods.rod_quad_schrabidium);
		addQuadRodBilletUnload(Billets.billet_solinium, RetroRods.rod_quad_solinium);
		addQuadRodBillet(Billets.billet_uranium_fuel, RetroRods.rod_quad_uranium_fuel);
		addQuadRodBillet(Billets.billet_thorium_fuel, RetroRods.rod_quad_thorium_fuel);
		addQuadRodBillet(Billets.billet_plutonium_fuel, RetroRods.rod_quad_plutonium_fuel);
		addQuadRodBillet(Billets.billet_mox_fuel, RetroRods.rod_quad_mox_fuel);
		addQuadRodBillet(Billets.billet_schrabidium_fuel, RetroRods.rod_quad_schrabidium_fuel);
		addQuadRodBilletUnload(Billets.billet_nuclear_waste, RetroRods.rod_quad_waste);

		addQuadRodBilletUnload(Billets.billet_ac227, RetroRods.rod_quad_ac227);
		addQuadRodBilletUnload(Billets.billet_ra226, RetroRods.rod_quad_ra226);
		addQuadRodBilletUnload(Billets.billet_pu_mix, RetroRods.rod_quad_rgp);
		addQuadRodBilletUnload(Billets.billet_co60, RetroRods.rod_quad_co60);

		addShapelessAuto(new ItemStack(Billets.billet_zfb_bismuth), new Object[] { ZR.nugget(), ZR.nugget(), ZR.nugget(),  U.nugget(), PU241.nugget(), ANY_BISMOID.nugget() });
		addShapelessAuto(new ItemStack(Billets.billet_zfb_pu241), new Object[] { ZR.nugget(), ZR.nugget(), ZR.nugget(),  U235.nugget(), PU240.nugget(), PU241.nugget() });
		addShapelessAuto(new ItemStack(Billets.billet_zfb_am_mix), new Object[] { ZR.nugget(), ZR.nugget(), ZR.nugget(),  PU241.nugget(), PU241.nugget(), AMRG.nugget() });

		addShapelessAuto(new ItemStack(Billets.billet_zfb_bismuth, 6), new Object[] { ZR.billet(), ZR.billet(), ZR.billet(), U.billet(), PU241.billet(), ANY_BISMOID.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_zfb_pu241, 6), new Object[] { ZR.billet(), ZR.billet(), ZR.billet(), U235.billet(), PU240.billet(), PU241.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_zfb_am_mix, 6), new Object[] { ZR.billet(), ZR.billet(), ZR.billet(), PU241.billet(), PU241.billet(), AMRG.billet() });

		addShapelessAuto(new ItemStack(Billets.billet_unobtainium), new Object[] { Nuggies.nugget_radspice, AMRG.nugget(), Nuggies.nugget_unobtainium_lesser, Nuggies.nugget_unobtainium_greater, Nuggies.nugget_unobtainium_greater, Nuggies.nugget_unobtainium_greater });

		addShapelessAuto(new ItemStack(RetroRods.rod_water, 1), new Object[] { RetroRods.rod_empty, Items.WATER_BUCKET });
		addShapelessAuto(new ItemStack(RetroRods.rod_dual_water, 1), new Object[] { RetroRods.rod_dual_empty, Items.WATER_BUCKET, Items.WATER_BUCKET });
		addShapelessAuto(new ItemStack(RetroRods.rod_quad_water, 1), new Object[] { RetroRods.rod_quad_empty, Items.WATER_BUCKET, Items.WATER_BUCKET, Items.WATER_BUCKET, Items.WATER_BUCKET });

		addShapelessAuto(new ItemStack(Nuggies.nugget_lead, 6), new Object[] { RetroRods.rod_lead });
		addShapelessAuto(new ItemStack(ModItems.lithium, 1), new Object[] { RetroRods.rod_lithium });
		addShapelessAuto(new ItemStack(Nuggies.nugget_cobalt, 6), new Object[] { RetroRods.rod_cobalt });
		addShapelessAuto(new ItemStack(Nuggies.nugget_australium, 6), new Object[] { RetroRods.rod_australium });
		addShapelessAuto(new ItemStack(Nuggies.nugget_weidanium, 6), new Object[] { RetroRods.rod_weidanium });
		addShapelessAuto(new ItemStack(Nuggies.nugget_reiium, 6), new Object[] { RetroRods.rod_reiium });
		addShapelessAuto(new ItemStack(Nuggies.nugget_unobtainium, 6), new Object[] { RetroRods.rod_unobtainium });
		addShapelessAuto(new ItemStack(Nuggies.nugget_daffergon, 6), new Object[] { RetroRods.rod_daffergon });
		addShapelessAuto(new ItemStack(Nuggies.nugget_verticium, 6), new Object[] { RetroRods.rod_verticium });
		addShapelessAuto(new ItemStack(Nuggies.nugget_euphemium, 6), new Object[] { RetroRods.rod_euphemium });
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 1), new Object[] { RetroRods.rod_balefire });
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 1), new Object[] { RetroRods.rod_balefire_blazing });

		addShapelessAuto(new ItemStack(Nuggies.nugget_lead, 12), new Object[] { RetroRods.rod_dual_lead });
		addShapelessAuto(new ItemStack(ModItems.lithium, 2), new Object[] { RetroRods.rod_dual_lithium });
		addShapelessAuto(new ItemStack(Nuggies.nugget_cobalt, 12), new Object[] { RetroRods.rod_dual_cobalt });
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 2), new Object[] { RetroRods.rod_dual_balefire });
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 2), new Object[] { RetroRods.rod_dual_balefire_blazing });

		addShapelessAuto(new ItemStack(Nuggies.nugget_lead, 24), new Object[] { RetroRods.rod_quad_lead });
		addShapelessAuto(new ItemStack(ModItems.lithium, 4), new Object[] { RetroRods.rod_quad_lithium });
		addShapelessAuto(new ItemStack(Nuggies.nugget_cobalt, 24), new Object[] { RetroRods.rod_quad_cobalt });
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 4), new Object[] { RetroRods.rod_quad_balefire });
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 4), new Object[] { RetroRods.rod_quad_balefire_blazing });

		addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 1), new Object[] { RetroRods.rod_uranium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 2), new Object[] { RetroRods.rod_dual_uranium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 4), new Object[] { RetroRods.rod_quad_uranium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 1), new Object[] { RetroRods.rod_thorium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 2), new Object[] { RetroRods.rod_dual_thorium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 4), new Object[] { RetroRods.rod_quad_thorium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 1), new Object[] { RetroRods.rod_plutonium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 2), new Object[] { RetroRods.rod_dual_plutonium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 4), new Object[] { RetroRods.rod_quad_plutonium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 1), new Object[] { RetroRods.rod_mox_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 2), new Object[] { RetroRods.rod_dual_mox_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 4), new Object[] { RetroRods.rod_quad_mox_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 1), new Object[] { RetroRods.rod_schrabidium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 2), new Object[] { RetroRods.rod_dual_schrabidium_fuel_depleted });
		addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 4), new Object[] { RetroRods.rod_quad_schrabidium_fuel_depleted });

		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.BEETROOT, Items.BEETROOT, Items.BEETROOT, Items.BEETROOT, Items.BEETROOT, Items.BEETROOT, Items.BEETROOT });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling" });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { "treeLeaves", "treeLeaves", "treeLeaves", "treeLeaves", "treeLeaves" });
		addShapelessAuto(new ItemStack(ModItems.biomass, 8), new Object[] { Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN });
		addShapelessAuto(new ItemStack(ModItems.biomass, 6), new Object[] { KEY_LOG, KEY_LOG, KEY_LOG });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS });
		addShapelessAuto(new ItemStack(ModItems.biomass, 8), new Object[] { Blocks.HAY_BLOCK, Blocks.HAY_BLOCK });
		addShapelessAuto(new ItemStack(ModItems.biomass, 1), new Object[] { Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS });
		addShapelessAuto(new ItemStack(ModItems.biomass, 2), new Object[] { Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS });
		addShapelessAuto(new ItemStack(ModItems.biomass, 2), new Object[] { Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS });
		addShapelessAuto(new ItemStack(ModItems.biomass, 2), new Object[] { Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS });

		addShapelessAuto(new ItemStack(ModBlocks.deco_aluminium), new Object[] { AL.ingot(), ModBlocks.steel_scaffold });
		addShapelessAuto(new ItemStack(ModBlocks.deco_beryllium), new Object[] { BE.ingot(), ModBlocks.steel_scaffold });
		addShapelessAuto(new ItemStack(ModBlocks.deco_lead), new Object[] { PB.ingot(), ModBlocks.steel_scaffold });
		addShapelessAuto(new ItemStack(ModBlocks.deco_red_copper), new Object[] { MINGRADE.ingot(), ModBlocks.steel_scaffold });
		addShapelessAuto(new ItemStack(ModBlocks.deco_steel), new Object[] { STEEL.ingot(), ModBlocks.steel_scaffold });
		addShapelessAuto(new ItemStack(ModBlocks.deco_titanium), new Object[] { TI.ingot(), ModBlocks.steel_scaffold });
		addShapelessAuto(new ItemStack(ModBlocks.deco_tungsten), new Object[] { W.ingot(), ModBlocks.steel_scaffold });

		addRecipeAuto(new ItemStack(Nuggies.nugget_euphemium, 1), new Object[] { "#", '#', RetroRods.rod_quad_euphemium });
		//Drillgon200: Gone, reduced.
		//addRecipeAuto(new ItemStack(ModItems.ingot_euphemium, 1), new Object[] { "###", "###", "###", '#', ModItems.rod_quad_euphemium });
		addRecipeAuto(new ItemStack(Ingots.ingot_euphemium, 1), new Object[] { "###", "###", "###", '#', EUPH.nugget() });
		addRecipeAuto(new ItemStack(Nuggies.nugget_euphemium, 9), new Object[] { "#", '#', EUPH.ingot() });

		addRecipeAuto(new ItemStack(ModItems.coil_copper, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_red_copper, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_alloy, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_advanced_alloy, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_gold, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_gold, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_advanced_alloy });
		addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_gold });
		addRecipeAuto(new ItemStack(ModItems.coil_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_tungsten, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_magnetized_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.tank_steel, 2), new Object[] { "STS", "S S", "STS", 'S', STEEL.plate(), 'T', TI.plate() });
		addRecipeAuto(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", "ITI", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', IRON.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", " T ", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', STEEL.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.motor_desh, 1), new Object[] { "PCP", "DMD", "PCP", 'P', ANY_PLASTIC.ingot(), 'C', ModItems.coil_gold_torus, 'D', DESH.ingot(), 'M', ModItems.motor });
		addRecipeAuto(new ItemStack(ModItems.deuterium_filter, 1), new Object[] { "TST", "SCS", "TST", 'T', ANY_RESISTANTALLOY.ingot(), 'S', S.dust(), 'C', ModItems.catalyst_clay });
		addRecipeAuto(new ItemStack(ModItems.thermo_unit_endo, 1), new Object[] { "EEE", "ETE", "EEE", 'E', Item.getItemFromBlock(Blocks.ICE), 'T', ModItems.thermo_unit_empty });
		addRecipeAuto(new ItemStack(ModItems.thermo_unit_exo, 1), new Object[] { "LLL", "LTL", "LLL", 'L', Items.LAVA_BUCKET, 'T', ModItems.thermo_unit_empty });

		addRecipeAuto(new ItemStack(ModItems.cap_aluminium, 1), new Object[] { "PIP", 'P', AL.plate(), 'I', AL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_small_steel, 2), new Object[] { "PPP", "   ", "PPP", 'P', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModItems.hull_small_aluminium, 2), new Object[] { "PPP", "   ", "PPP", 'P', AL.plate() });
		addRecipeAuto(new ItemStack(ModItems.hull_big_steel, 1), new Object[] { "III", "   ", "III", 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_big_aluminium, 1), new Object[] { "III", "   ", "III", 'I', AL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_big_titanium, 1), new Object[] { "III", "   ", "III", 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_tube_steel, 4), new Object[] { "PPP", "PPP", 'P', STEEL.plate() });

		addRecipeAuto(new ItemStack(ModItems.fins_flat, 1), new Object[] { "IP", "PP", "IP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_small_steel, 1), new Object[] { " PP", "PII", " PP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_big_steel, 1), new Object[] { " PI", "III", " PI", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_tri_steel, 1), new Object[] { " PI", "IIB", " PI", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'B', STEEL.block() });
		addRecipeAuto(new ItemStack(ModItems.fins_quad_titanium, 1), new Object[] { " PP", "III", " PP", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.sphere_steel, 1), new Object[] { "PIP", "I I", "PIP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.pedestal_steel, 1), new Object[] { "P P", "P P", "III", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Foods.lemon, 1), new Object[] { " D ", "DSD", " D ", 'D', new ItemStack(Items.DYE, 1, 11), 'S', "stone" });
		addRecipeAuto(new ItemStack(ModItems.blade_titanium, 2), new Object[] { "TP", "TP", "TT", 'P', TI.plate(), 'T', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.turbine_titanium, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_titanium, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.rotor_steel, 3), new Object[] { "CCC", "SSS", "CCC", 'C', ModItems.coil_gold, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.generator_steel, 1), new Object[] { "RRR", "CCC", "SSS", 'C', ModItems.coil_gold_torus, 'S', STEEL.ingot(), 'R', ModItems.rotor_steel });
		addRecipeAuto(new ItemStack(ModItems.shimmer_head, 1), new Object[] { "SSS", "DTD", "SSS", 'S', STEEL.ingot(), 'D', DESH.block(), 'T', W.block() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_axe_head, 1), new Object[] { "PII", "PBB", "PII", 'P', STEEL.plate(), 'B', DESH.block(), 'I', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_handle, 1), new Object[] { "GP", "GP", "GP", 'G', GOLD.plate(), 'P', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_sledge, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_head });
		addRecipeAuto(new ItemStack(ModItems.shimmer_axe, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_axe_head });
		addRecipeAuto(new ItemStack(Foods.definitelyfood, 1), new Object[] { "DDD", "SDS", "DDD", 'D', Blocks.DIRT, 'S', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModItems.blade_tungsten, 2), new Object[] { "IP", "TP", "TI", 'P', TI.plate(), 'T', TI.ingot(), 'I', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.turbine_tungsten, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_tungsten, 'S', DURA.ingot() });
		addRecipeAuto(new ItemStack(ModItems.ring_starmetal, 1), new Object[] { " S ", "S S", " S ", 'S', STAR.ingot() });

		addRecipeAuto(new ItemStack(ModItems.wrench, 1), new Object[] { " S ", " IS", "I  ", 'S', STEEL.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.wrench_flipped, 1), new Object[] { "S", "D", "W", 'S', Items.IRON_SWORD, 'D', ModItems.ducttape, 'W', ModItems.wrench });
		addRecipeAuto(new ItemStack(ModItems.memespoon, 1), new Object[] { "CGC", "PSP", "IAI", 'C', Powders.powder_cloud, 'G', ModBlocks.block_thorium, 'P', ModItems.photo_panel, 'S', ToolSets.steel_shovel, 'I', ModItems.plate_polymer, 'A', AUSTRALIUM.ingot() });
		addShapelessAuto(new ItemStack(ModItems.cbt_device, 1), new Object[] { ModItems.bolt_tungsten, ModItems.wrench });

		addShapelessAuto(new ItemStack(ModItems.toothpicks, 3), new Object[] { Items.STICK, Items.STICK, Items.STICK });
		addRecipeAuto(new ItemStack(ModItems.ducttape, 6), new Object[] { "FSF", "SPS", "FSF", 'F', Items.STRING, 'S', Items.SLIME_BALL, 'P', Items.PAPER });
		addRecipeAuto(new ItemStack(ModItems.ball_resin), new Object[] { "DD", "DD", 'D', Blocks.YELLOW_FLOWER });
		
		addShapelessAuto(new ItemStack(ModItems.missile_taint, 1), new Object[] { ModItems.missile_assembly, new IngredientContainsTag(FluidUtil.getFilledBucket(new FluidStack(ModForgeFluids.mud_fluid, 1000))), Powders.powder_spark_mix, Powders.powder_magic });
		addShapelessAuto(new ItemStack(ModItems.missile_micro, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, Armory.ammo_nuke });
		addShapelessAuto(new ItemStack(ModItems.missile_bhole, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, Armory.grenade_black_hole });
		addShapelessAuto(new ItemStack(ModItems.missile_schrabidium, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, Armory.grenade_aschrab });
		addShapelessAuto(new ItemStack(ModItems.missile_schrabidium, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.sas3)), ModItems.circuit_targeting_tier4 });
		addShapelessAuto(new ItemStack(ModItems.missile_emp, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModBlocks.emp_bomb, ModItems.circuit_targeting_tier3 });
		addShapelessAuto(new ItemStack(ModItems.missile_anti_ballistic, 1), new Object[] { ModItems.missile_generic, ModItems.circuit_targeting_tier3 });

		if (ModBlocks.oc_cable != null) {
			addRecipeAuto(new ItemStack(ModBlocks.oc_cable, 4), new Object[] { "ICI", "CRC", "ICI", 'I', ModItems.plate_polymer, 'R', REDSTONE.dust(), 'C',ForgeRegistries.ITEMS.getValue(new ResourceLocation("opencomputers","cable"))});
		}
		if (ModBlocks.audio_cable != null) {
			addRecipeAuto(new ItemStack(ModBlocks.audio_cable, 4), new Object[] { "SCS", "CIC", "SCS", 'S', ModBlocks.concrete_smooth, 'I', IRON.ingot(), 'C',ForgeRegistries.ITEMS.getValue(new ResourceLocation("computronics","audio_cable"))});
		}

		addRecipeAuto(new ItemStack(ModBlocks.machine_difurnace_ext, 1), new Object[] { " C ", "BGB", "BGB", 'C', CU.plate(), 'B', Ingots.ingot_firebrick, 'G', ModBlocks.steel_grate });
		addRecipeAuto(new ItemStack(ModBlocks.machine_uf6_tank, 1), new Object[] { "WTW", "WTW", "SRS", 'S', IRON.plate(), 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten, 'R', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_puf6_tank, 1), new Object[] { "WTW", "WTW", "SRS", 'S', STEEL.plate(), 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten, 'R', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_nuke_furnace_off, 1), new Object[] { "SSS", "LFL", "CCC", 'S', STEEL.plate(), 'C', ModItems.board_copper, 'L', PB.plate(), 'F', Item.getItemFromBlock(Blocks.FURNACE) });
		addRecipeAuto(new ItemStack(ModBlocks.machine_electric_furnace_off, 1), new Object[] { "BBB", "WFW", "RRR", 'B', BE.ingot(), 'R', ModItems.coil_tungsten, 'W', ModItems.board_copper, 'F', Item.getItemFromBlock(Blocks.FURNACE) });
		addRecipeAuto(new ItemStack(ModBlocks.machine_arc_furnace_off, 1), new Object[] { "ITI", "PFP", "ITI", 'I', W.ingot(), 'T', ModBlocks.machine_transformer, 'P', ModItems.board_copper, 'F', Blocks.FURNACE });
		addRecipeAuto(new ItemStack(ModBlocks.red_wire_coated, 16), new Object[] { "WRW", "RIR", "WRW", 'W', ModItems.plate_polymer, 'I', MINGRADE.ingot(), 'R', ModItems.wire_red_copper });
		addShapelessAuto(new ItemStack(ModBlocks.red_wire_sealed, 1), new Object[] { ModBlocks.red_wire_coated, ModBlocks.brick_compound });
		addRecipeAuto(new ItemStack(ModBlocks.cable_switch, 1), new Object[] { "S", "W", 'S', Blocks.LEVER, 'W', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.cable_detector, 1), new Object[] { "S", "W", 'S', REDSTONE.dust(), 'W', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.cable_diode, 1), new Object[] { " Q ", "CAC", " Q ", 'Q', NETHERQUARTZ.gem(), 'C', ModBlocks.red_cable, 'A', AL.ingot() });
		addShapelessAuto(new ItemStack(ModBlocks.red_cable_gauge), new Object[] { ModBlocks.red_cable, ModBlocks.concrete, ModItems.circuit_aluminium });
		addRecipeAuto(new ItemStack(ModBlocks.radio_torch_sender, 8), new Object[] { "PMP", "RTR", "PMP", 'P', TI.plate(), 'M', ModItems.magnetron, 'R', Items.COMPARATOR, 'T', ModItems.pellet_rtg });
		addRecipeAuto(new ItemStack(ModBlocks.radio_torch_receiver, 8), new Object[] { "PMP", "RTR", "PMP", 'P', STEEL.plate(), 'M', ModItems.magnetron, 'R', Items.REPEATER, 'T', ModItems.pellet_rtg });
		addRecipeAuto(new ItemStack(ModBlocks.red_cable, 16), new Object[] { " W ", "RRR", " W ", 'W', ModItems.plate_polymer, 'R', ModItems.wire_red_copper });
		addRecipeAuto(new ItemStack(ModBlocks.red_pylon, 4), new Object[] { "CWC", "PWP", " T ", 'C', ModItems.coil_copper_torus, 'W', KEY_PLANKS, 'P', ModItems.plate_polymer, 'T', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.machine_battery_potato, 1), new Object[] { "PCP", "WRW", "PCP", 'P', ItemBattery.getEmptyBattery(Batteries.battery_potato), 'C', CU.ingot(), 'R', Blocks.REDSTONE_BLOCK, 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.machine_coal_off, 1), new Object[] { "STS", "SCS", "SFS", 'S', STEEL.ingot(), 'T', ModItems.tank_steel, 'C', MINGRADE.ingot(), 'F', Blocks.FURNACE });
		addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', STEEL.ingot(), 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', Blocks.FURNACE });
		addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_electric_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', DESH.ingot(), 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', ModBlocks.machine_electric_furnace_off });
		addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_rtg_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', STAR.ingot(), 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', ModBlocks.machine_rtg_furnace_off });
		addRecipeAuto(new ItemStack(ModBlocks.machine_turbine, 1), new Object[] { "PTP", "BMB", "PTP", 'P', TI.plate(), 'T', ModItems.turbine_titanium, 'B', ModItems.tank_steel, 'M', ModItems.motor });
		addRecipeAuto(new ItemStack(ModBlocks.machine_converter_he_rf, 1), new Object[] { "SSS", "CRB", "SSS", 'S', STEEL.ingot(), 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', REDSTONE.block() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_converter_rf_he, 1), new Object[] { "SSS", "BRC", "SSS", 'S', BE.ingot(), 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', REDSTONE.block() });
		addRecipeAuto(new ItemStack(ModBlocks.crate_iron, 1), new Object[] { "PPP", "I I", "III", 'P', IRON.plate(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.crate_steel, 1), new Object[] { "PPP", "I I", "III", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.crate_desh, 1), new Object[] { " P ", "PAP", " P ", 'P', ModItems.plate_desh, 'A', ModBlocks.crate_steel });
		addRecipeAuto(new ItemStack(ModBlocks.crate_tungsten, 1), new Object[] { "BPB", "PCP", "BPB", 'B', W.block(), 'P', ModItems.board_copper, 'C', ModBlocks.crate_steel });
		addRecipeAuto(new ItemStack(ModBlocks.safe, 1), new Object[] { "LAL", "ACA", "LAL", 'L', PB.plate(), 'A', ALLOY.plate(), 'C', ModBlocks.crate_steel });
		addRecipeAuto(new ItemStack(ModBlocks.machine_waste_drum, 1), new Object[] { "LRL", "BRB", "LRL", 'L', PB.ingot(), 'B', Blocks.IRON_BARS, 'R', RetroRods.rod_quad_empty });
		addRecipeAuto(new ItemStack(ModBlocks.machine_press, 1), new Object[] { "IRI", "IPI", "IBI", 'I', IRON.ingot(), 'R', Blocks.FURNACE, 'B', IRON.block(), 'P', Blocks.PISTON });
		addRecipeAuto(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "SIS", "ICI", "SRS", 'S', STEEL.plate(), 'I', ANY_RUBBER.ingot(), 'C', ModItems.circuit_copper, 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_microwave, 1), new Object[] { "III", "SGM", "IDI", 'I', ModItems.plate_polymer, 'S', STEEL.plate(), 'G', KEY_ANYPANE, 'M', ModItems.magnetron, 'D', ModItems.motor });

		addRecipeAuto(new ItemStack(ModBlocks.machine_acidizer, 1), new Object[] { "III", "SGM", "IDI", 'I', ModItems.plate_polymer, 'S', STEEL.plate(), 'G', KEY_ANYPANE, 'M', ModItems.magnetron, 'D', ModItems.motor });

		addRecipeAuto(new ItemStack(ModBlocks.factory_titanium_hull, 1), new Object[] { "PIP", "I I", "PIP", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.factory_titanium_furnace, 1), new Object[] { "HMH", "MFM", "HMH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'M', ModItems.motor, 'F', Item.getItemFromBlock(Blocks.FURNACE) });
		addRecipeAuto(new ItemStack(ModBlocks.factory_titanium_conductor, 1), new Object[] { "SWS", "FFF", "SWS", 'S', TI.ingot(), 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'F', ModItems.fuse });
		addRecipeAuto(new ItemStack(ModBlocks.factory_titanium_core, 1), new Object[] { "HPH", "PCP", "HPH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'C', ModItems.circuit_aluminium, 'P', Item.getItemFromBlock(Blocks.PISTON) });
		addRecipeAuto(new ItemStack(ModItems.factory_core_titanium, 1), new Object[] { "BRB", "RHR", "BRB", 'B', ItemBattery.getEmptyBattery(Batteries.battery_generic), 'R', Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull) });
		addRecipeAuto(new ItemStack(ModItems.factory_core_advanced, 1), new Object[] { "BLB", "SHS", "BLB", 'B', ItemBattery.getEmptyBattery(Batteries.battery_advanced), 'S', S.block(), 'L', PB.block(), 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) });
		addRecipeAuto(new ItemStack(ModItems.factory_core_advanced, 1), new Object[] { "BSB", "LHL", "BSB", 'B', ItemBattery.getEmptyBattery(Batteries.battery_advanced), 'S', S.block(), 'L', PB.block(), 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) });

		addRecipeAuto(new ItemStack(ModItems.arc_electrode, 1), new Object[] { "C", "T", "C", 'C', COAL.dust(), 'T', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.arc_electrode_desh, 1), new Object[] { "C", "T", "C", 'C', DESH.dust(), 'T', ModItems.arc_electrode });
		addRecipeAuto(new ItemStack(ModItems.detonator, 1), new Object[] { " W", "SC", "CE", 'S', STEEL.plate(), 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'E', STEEL.ingot() });
		addShapelessAuto(new ItemStack(ModItems.detonator_multi, 1), new Object[] { ModItems.detonator, ModItems.circuit_targeting_tier3 });
		addRecipeAuto(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', Items.REDSTONE, 'C', ModItems.circuit_targeting_tier3, 'D', DIAMOND.gem(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', Items.REDSTONE, 'C', ModItems.circuit_targeting_tier3, 'D', EMERALD.gem(), 'I', STEEL.ingot() });
		addShapelessAuto(new ItemStack(ModItems.detonator_deadman, 1), new Object[] { ModItems.detonator, ModItems.defuser, ModItems.ducttape });
		addRecipeAuto(new ItemStack(ModItems.detonator_de, 1), new Object[] { "T", "D", "T", 'T', Blocks.TNT, 'D', ModItems.detonator_deadman });

		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.CARPET), new Object[] { "TTT", "TRT", "TTT", 'T', Blocks.TNT, 'R', ModItems.detonator_laser });
		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.NAPALM), new Object[] { "TTT", "TRT", "TTT", 'T', Armory.grenade_gascan, 'R', ModItems.detonator_laser });
		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.POISON), new Object[] { "TTT", "TRT", "TTT", 'T', ModItems.pellet_gas, 'R', ModItems.detonator_laser });
		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.ORANGE), new Object[] { "TRT", 'T', Armory.grenade_cloud, 'R', ModItems.detonator_laser });
		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.ATOMIC), new Object[] { "TRT", 'T', Armory.ammo_nuke, 'R', ModItems.detonator_laser });

		addRecipeAuto(new ItemStack(ModItems.crystal_xen, 1), new Object[] { "EEE", "EIE", "EEE", 'E', Powders.powder_power, 'I', EUPH.ingot() });

		addRecipeAuto(new ItemStack(ModItems.screwdriver, 1), new Object[] { "  I", " I ", "S  ", 'S', STEEL.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.screwdriver_desh, 1), new Object[] { "  I", " I ", "S  ", 'S', ANY_PLASTIC.ingot(), 'I', DESH.ingot() });
		addShapelessAuto(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.screwdriver, NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), ModItems.board_copper, ModItems.singularity_spark, CS.dust() });
		addShapelessAuto(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.screwdriver, SR.dust(), BR.dust(), CO.dust(), TS.dust(), NB.dust(), ModItems.board_copper, ModItems.singularity_spark, CE.dust() });
		addRecipeAuto(new ItemStack(ModItems.crystal_energy, 1), new Object[] { "EEE", "EGE", "EEE", 'E', Powders.powder_power, 'G', Items.GLOWSTONE_DUST });
		addRecipeAuto(new ItemStack(ModItems.pellet_coolant, 1), new Object[] { "CRC", "RBR", "CRC", 'C', Powders.powder_ice, 'R', RetroRods.rod_quad_coolant, 'B', KNO.block() });

		addRecipeAuto(new ItemStack(ModItems.blades_aluminum, 1), new Object[] { " P ", "PIP", " P ", 'P', AL.plate(), 'I', AL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_gold, 1), new Object[] { " P ", "PIP", " P ", 'P', GOLD.plate(), 'I', GOLD.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_iron, 1), new Object[] { " P ", "PIP", " P ", 'P', IRON.plate(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), new Object[] { " P ", "PIP", " P ", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { " P ", "PIP", " P ", 'P', ALLOY.plate(), 'I', ALLOY.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', CMB.plate(), 'I', CMB.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { " P ", "PIP", " P ", 'P', SA326.plate(), 'I', SA326.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_desh, 1), new Object[] { "NPN", "PBP", "NPN", 'N', SA326.nugget(), 'P', ModItems.plate_desh, 'B', ModItems.blades_combine_steel });

		addRecipeAuto(new ItemStack(ModItems.blades_aluminum, 1), new Object[] { "PIP", 'P', AL.plate(), 'I', new ItemStack(ModItems.blades_aluminum, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_gold, 1), new Object[] { "PIP", 'P', GOLD.plate(), 'I', new ItemStack(ModItems.blades_gold, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_iron, 1), new Object[] { "PIP", 'P', IRON.plate(), 'I', new ItemStack(ModItems.blades_iron, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), new Object[] { "PIP", 'P', STEEL.plate(), 'I', new ItemStack(ModItems.blades_steel, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), new Object[] { "PIP", 'P', TI.plate(), 'I', new ItemStack(ModItems.blades_titanium, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { "PIP", 'P', ALLOY.plate(), 'I', new ItemStack(ModItems.blades_advanced_alloy, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { "PIP", 'P', CMB.plate(), 'I', new ItemStack(ModItems.blades_combine_steel, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { "PIP", 'P', SA326.plate(), 'I', new ItemStack(ModItems.blades_schrabidium, 1, OreDictionary.WILDCARD_VALUE) });

		addRecipeAuto(new ItemStack(ModItems.laser_crystal_nano, 1), new Object[] { "QPQ", "ACA", "QPQ", 'Q', ModBlocks.glass_quartz, 'P', GRAPHITE.ingot(), 'A', ANY_PLASTIC.dust(), 'C', ModItems.filter_coal });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_pentacene, 1), new Object[] { "QSQ", "AEA", "QSQ", 'Q', ModBlocks.glass_quartz, 'A', ALLOY.dust(), 'S', CE.ingot(), 'E', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.biogas)) });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_co2, 1), new Object[] { "QDQ", "NCN", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', DESH.ingot(), 'N', NB.ingot(), 'C', ModItems.part_carbon });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_bismuth, 1), new Object[] {"QUQ", "BCB", "QTQ", 'Q', ModBlocks.glass_quartz, 'U', U.ingot(), 'T', TH232.ingot(), 'B', ANY_BISMOID.nugget(), 'C', Crystals.crystal_rare });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_cmb, 1), new Object[] {"QBQ", "CSC", "QBQ", 'Q', ModBlocks.glass_quartz, 'B', STAR.ingot(), 'C', PB209.nugget(), 'S', XE135.dust() });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_dem, 1), new Object[] {"QDQ", "SBS", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', CMB.ingot(), 'B', ModItems.demon_core_open, 'S', GH336.nugget()});
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_bale, 1), new Object[] {"QDQ", "SBS", "QDQ", 'Q', ModBlocks.glass_trinitite, 'D', Ingots.ingot_verticium, 'B', RBMKFuel.rbmk_pellet_balefire, 'S', Nuggies.nugget_radspice });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_digamma, 1), new Object[] {"QUQ", "UEU", "QUQ", 'Q', ModBlocks.glass_ash, 'U', ModItems.undefined, 'E', Ingots.ingot_electronium } );

		addRecipeAuto(new ItemStack(ModItems.stamp_stone_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', "stone" });
		addRecipeAuto(new ItemStack(ModItems.stamp_iron_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.stamp_steel_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.stamp_titanium_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.stamp_obsidian_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', Blocks.OBSIDIAN });
		addRecipeAuto(new ItemStack(ModItems.stamp_schrabidium_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', SA326.ingot() });
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_flat, 1), new Object[] { "RIR", "ISI", "RIR", 'S', ModItems.stamp_schrabidium_flat, 'I', ModItems.plate_desh, 'R', ModBlocks.cmb_brick_reinforced });


		addRecipeAuto(new ItemStack(Armory.mechanism_revolver_1, 1), new Object[] { " II", "ICA", "IKW", 'I', IRON.plate(), 'C', CU.ingot(), 'A', AL.ingot(), 'K', ModItems.wire_copper, 'W', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(Armory.mechanism_revolver_2, 1), new Object[] { " II", "ICA", "IKW", 'I', ALLOY.plate(), 'C', DURA.ingot(), 'A', W.ingot(), 'K', ModItems.bolt_dura_steel, 'W', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(Armory.mechanism_rifle_1, 1), new Object[] { "ICI", "CMA", "IAM", 'I', IRON.plate(), 'C', CU.ingot(), 'A', AL.ingot(), 'M', Armory.mechanism_revolver_1 });
		addRecipeAuto(new ItemStack(Armory.mechanism_rifle_2, 1), new Object[] { "ICI", "CMA", "IAM", 'I', ALLOY.plate(), 'C', DURA.ingot(), 'A', W.ingot(), 'M', Armory.mechanism_revolver_2 });
		addRecipeAuto(new ItemStack(Armory.mechanism_launcher_1, 1), new Object[] { "TTT", "SSS", "BBI", 'T', TI.plate(), 'S', STEEL.ingot(), 'B', ModItems.bolt_tungsten, 'I', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(Armory.mechanism_launcher_2, 1), new Object[] { "TTT", "SSS", "BBI", 'T', ALLOY.plate(), 'S', ANY_PLASTIC.ingot(), 'B', ModItems.bolt_dura_steel, 'I', DESH.ingot() });

		addRecipeAuto(new ItemStack(ModBlocks.fwatz_cooler, 1), new Object[] { "IPI", "IPI", "IPI", 'I', SBD.ingot(), 'P', ModItems.thermo_unit_endo });
		addRecipeAuto(new ItemStack(ModBlocks.fwatz_tank, 1), new Object[] { "CGC", "GGG", "CGC", 'C', CMB.plate(), 'G', KEY_ANYPANE });
		addRecipeAuto(new ItemStack(ModBlocks.fwatz_scaffold, 1), new Object[] { "IPI", "P P", "IPI", 'I', W.ingot(), 'P', OreDictManager.getReflector() });
		addRecipeAuto(new ItemStack(ModBlocks.fwatz_conductor, 1), new Object[] { "IPI", "PFP", "IPI", 'I', TI.plate(), 'P', ModItems.coil_magnetized_tungsten, 'F', ModBlocks.hadron_coil_neodymium });

		addRecipeAuto(new ItemStack(ModBlocks.reinforced_stone, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.COBBLESTONE, 'B', Blocks.STONE });
		addRecipeAuto(new ItemStack(ModBlocks.brick_light, 4), new Object[] { "FBF", "BFB", "FBF", 'F', "fenceWood", 'B', Blocks.BRICK_BLOCK });
		addRecipeAuto(new ItemStack(ModBlocks.brick_asbestos, 2), new Object[] { " A ", "ABA", " A ", 'B', ModBlocks.brick_light, 'A', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.concrete, 4), new Object[] { "CC", "CC", 'C', ModBlocks.concrete_smooth });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_white, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeWhite"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_white });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_orange, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeOrange"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_orange });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_black, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeBlack"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_black });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_blue, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeBlue"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_blue });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_brown, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeBrown"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_brown });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_cyan, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeCyan"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_cyan });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_gray, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeGray"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_gray });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_green, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeGreen"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_green });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_light_blue, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeLightBlue"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_light_blue });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_lime, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeLime"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_lime });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_magenta, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeMagenta"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_magenta });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_pink, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyePink"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_pink });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_purple, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyePurple"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_purple });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_silver, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeLightGray"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_silver });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_red, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeRed"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_red });

		addShapelessAuto(new ItemStack(ModBlocks.concrete_yellow, 8), new Object[] {ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeYellow"});
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), new Object[] { ModBlocks.concrete_yellow });

		addRecipeAuto(new ItemStack(ModBlocks.concrete_hazard, 6), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeYellow", '2', "dyeBlack" });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_hazard, 6), new Object[] { "CCC", "2 1", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeYellow", '2', "dyeBlack" });

		addRecipeAuto(new ItemStack(ModBlocks.concrete__ext_machine, 6), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeBrown", '2', "dyeGray" });
		addRecipeAuto(new ItemStack(ModBlocks.concrete__ext_machine_stripe, 6), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeBrown", '2', "dyeBlack" });
		addRecipeAuto(new ItemStack(ModBlocks.concrete__ext_indigo, 6), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeBlue", '2', "dyePurple" });
		addRecipeAuto(new ItemStack(ModBlocks.concrete__ext_pink, 6), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyePink", '2', "dyeRed" });
		addRecipeAuto(new ItemStack(ModBlocks.concrete__ext_sand, 6), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeYellow", '2', "dyeGray" });
		addRecipeAuto(new ItemStack(ModBlocks.concrete__ext_bronze, 6), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeOrange", '2', "dyeBrown" });
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth, 6), new Object[] { ModBlocks.concrete_hazard, ModBlocks.concrete_hazard, ModBlocks.concrete_hazard, ModBlocks.concrete_hazard, ModBlocks.concrete_hazard, ModBlocks.concrete_hazard });

		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_solid_tinge, 6), new Object[] { "DDD", "1 2", "DDD", 'D', ModBlocks.fluid_duct_solid, '1', "dyeBrown", '2', "dyeGray" });
		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_solid_stripe, 6), new Object[] { "DDD", "1 2", "DDD", 'D', ModBlocks.fluid_duct_solid, '1', "dyeBrown", '2', "dyeBlack" });

		addRecipeAuto(new ItemStack(ModBlocks.concrete_pillar, 8), new Object[] { "CBC", "CBC", "CBC", 'C', ModBlocks.concrete_smooth, 'B', Blocks.IRON_BARS });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete_smooth, 'B', Items.CLAY_BALL });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete, 'B', Items.CLAY_BALL });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.brick_concrete, 'V', Blocks.VINE });
		addShapelessAuto(new ItemStack(ModBlocks.brick_concrete_cracked, 6), new Object[] { ModBlocks.brick_concrete, ModBlocks.brick_concrete, ModBlocks.brick_concrete, ModBlocks.brick_concrete });
		addShapelessAuto(new ItemStack(ModBlocks.brick_concrete_broken, 6), new Object[] { ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked });
		addShapelessAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked });
		addShapelessAuto(new ItemStack(ModBlocks.brick_concrete_cracked, 4), new Object[] { ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken });
		addRecipeAuto(new ItemStack(ModBlocks.brick_obsidian, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', Blocks.OBSIDIAN });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.block_meteor_broken });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_pillar, 2), new Object[] { "C", "C", 'C', ModBlocks.meteor_polished });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.meteor_polished });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.meteor_brick, 'V', Blocks.VINE });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_cracked, 6), new Object[] { " C ", "C C", " C ", 'C', ModBlocks.meteor_brick });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_battery, 1), new Object[] { "MSM", "MWM", "MSM", 'M', ModBlocks.meteor_polished, 'S', STAR.block(), 'W', ModItems.wire_schrabidium });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab, 4), new Object[] { "CBC", "CBC", "CBC", 'C', Items.BRICK, 'B', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab_cracked, 6), new Object[] { " C ", "C C", " C ", 'C', ModBlocks.tile_lab });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab_broken, 6), new Object[] { " C ", "C C", " C ", 'C', ModBlocks.tile_lab_cracked });

		addRecipeAuto(new ItemStack(ModBlocks.ducrete_smooth, 4), new Object[] { "DD", "DD", 'D', ModBlocks.ducrete });
		addRecipeAuto(new ItemStack(ModBlocks.ducrete_brick, 4), new Object[] {"CDC", "DLD", "CDC", 'D', ModBlocks.ducrete_smooth, 'C', Items.CLAY_BALL, 'L', PB.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.ducrete_brick, 4), new Object[] {"CDC", "DLD", "CDC", 'D', ModBlocks.ducrete, 'C', Items.CLAY_BALL, 'L', PB.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.ducrete_reinforced, 4), new Object[] {"DSD", "SUS", "DSD", 'D', ModBlocks.ducrete_brick, 'S', STEEL.plate(), 'U', U238.billet() });

		addSlabStair(ModBlocks.reinforced_brick_slab, ModBlocks.reinforced_brick_stairs, ModBlocks.reinforced_brick);
		addSlabStair(ModBlocks.reinforced_sand_slab, ModBlocks.reinforced_sand_stairs, ModBlocks.reinforced_sand);
		addSlabStair(ModBlocks.reinforced_stone_slab, ModBlocks.reinforced_stone_stairs, ModBlocks.reinforced_stone);
		addSlabStair(ModBlocks.brick_concrete_slab, ModBlocks.brick_concrete_stairs, ModBlocks.brick_concrete);
		addSlabStair(ModBlocks.brick_concrete_mossy_slab, ModBlocks.brick_concrete_mossy_stairs, ModBlocks.brick_concrete_mossy);
		addSlabStair(ModBlocks.brick_concrete_cracked_slab, ModBlocks.brick_concrete_cracked_stairs, ModBlocks.brick_concrete_cracked);
		addSlabStair(ModBlocks.brick_concrete_broken_slab, ModBlocks.brick_concrete_broken_stairs, ModBlocks.brick_concrete_broken);
		addSlabStair(ModBlocks.brick_compound_slab, ModBlocks.brick_compound_stairs, ModBlocks.brick_compound);
		addSlabStair(ModBlocks.brick_asbestos_slab, ModBlocks.brick_asbestos_stairs, ModBlocks.brick_asbestos);
		addSlabStair(ModBlocks.brick_obsidian_slab, ModBlocks.brick_obsidian_stairs, ModBlocks.brick_obsidian);
		addSlabStair(ModBlocks.cmb_brick_reinforced_slab, ModBlocks.cmb_brick_reinforced_stairs, ModBlocks.cmb_brick_reinforced);
		addSlabStair(ModBlocks.concrete_slab, ModBlocks.concrete_stairs, ModBlocks.concrete);
		addSlabStair(ModBlocks.concrete_smooth_slab, ModBlocks.concrete_smooth_stairs, ModBlocks.concrete_smooth);
		addSlabStair(ModBlocks.concrete_white_slab, ModBlocks.concrete_white_stairs, ModBlocks.concrete_white);
		addSlabStair(ModBlocks.concrete_orange_slab, ModBlocks.concrete_orange_stairs, ModBlocks.concrete_orange);
		addSlabStair(ModBlocks.concrete_magenta_slab, ModBlocks.concrete_magenta_stairs, ModBlocks.concrete_magenta);
		addSlabStair(ModBlocks.concrete_light_blue_slab, ModBlocks.concrete_light_blue_stairs, ModBlocks.concrete_light_blue);
		addSlabStair(ModBlocks.concrete_yellow_slab, ModBlocks.concrete_yellow_stairs, ModBlocks.concrete_yellow);
		addSlabStair(ModBlocks.concrete_lime_slab, ModBlocks.concrete_lime_stairs, ModBlocks.concrete_lime);
		addSlabStair(ModBlocks.concrete_pink_slab, ModBlocks.concrete_pink_stairs, ModBlocks.concrete_pink);
		addSlabStair(ModBlocks.concrete_gray_slab, ModBlocks.concrete_gray_stairs, ModBlocks.concrete_gray);
		addSlabStair(ModBlocks.concrete_silver_slab, ModBlocks.concrete_silver_stairs, ModBlocks.concrete_silver);
		addSlabStair(ModBlocks.concrete_cyan_slab, ModBlocks.concrete_cyan_stairs, ModBlocks.concrete_cyan);
		addSlabStair(ModBlocks.concrete_purple_slab, ModBlocks.concrete_purple_stairs, ModBlocks.concrete_purple);
		addSlabStair(ModBlocks.concrete_blue_slab, ModBlocks.concrete_blue_stairs, ModBlocks.concrete_blue);
		addSlabStair(ModBlocks.concrete_brown_slab, ModBlocks.concrete_brown_stairs, ModBlocks.concrete_brown);
		addSlabStair(ModBlocks.concrete_green_slab, ModBlocks.concrete_green_stairs, ModBlocks.concrete_green);
		addSlabStair(ModBlocks.concrete_red_slab, ModBlocks.concrete_red_stairs, ModBlocks.concrete_red);
		addSlabStair(ModBlocks.concrete_black_slab, ModBlocks.concrete_black_stairs, ModBlocks.concrete_black);
		addSlabStair(ModBlocks.concrete_asbestos_slab, ModBlocks.concrete_asbestos_stairs, ModBlocks.concrete_asbestos);
		addSlabStair(ModBlocks.ducrete_smooth_slab, ModBlocks.ducrete_smooth_stairs, ModBlocks.ducrete_smooth);
		addSlabStair(ModBlocks.ducrete_slab, ModBlocks.ducrete_stairs, ModBlocks.ducrete);
		addSlabStair(ModBlocks.ducrete_brick_slab, ModBlocks.ducrete_brick_stairs, ModBlocks.ducrete_brick);
		addSlabStair(ModBlocks.ducrete_reinforced_slab, ModBlocks.ducrete_reinforced_stairs, ModBlocks.ducrete_reinforced);
		addSlabStair(ModBlocks.tile_lab_slab, ModBlocks.tile_lab_stairs, ModBlocks.tile_lab);
		addSlabStair(ModBlocks.tile_lab_cracked_slab, ModBlocks.tile_lab_cracked_stairs, ModBlocks.tile_lab_cracked);
		addSlabStair(ModBlocks.tile_lab_broken_slab, ModBlocks.tile_lab_broken_stairs, ModBlocks.tile_lab_broken);

		addSlabStair(ModBlocks.pink_slab, ModBlocks.pink_stairs, ModBlocks.pink_planks);
		

		addRecipeAuto(new ItemStack(ModBlocks.reinforced_brick, 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', ModBlocks.brick_concrete });
		addRecipeAuto(new ItemStack(ModBlocks.brick_compound, 8), new Object[] { "FBF", "BFB", "FBF", 'F', ModItems.bolt_tungsten, 'B', ModBlocks.reinforced_brick });
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_glass, 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', "blockGlassColorless" });
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_light, 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.IRON_BARS, 'B', "glowstone" });
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_lamp_off, 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.IRON_BARS, 'B', Blocks.REDSTONE_LAMP });
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_sand, 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', Blocks.SANDSTONE });

		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire, 16), new Object[] { "AIA", "I I", "AIA", 'A', ModItems.wire_aluminium, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_fire, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I',P_RED.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_poison, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', Powders.powder_poison });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_acid, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.acid)) });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_wither, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(Items.SKULL, 1, 1) });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_ultradeath, 4), new Object[] { "BCB", "CIC", "BCB", 'B', ModBlocks.barbed_wire, 'C', Powders.powder_cloud, 'I', ModItems.nuclear_waste });

		addRecipeAuto(new ItemStack(ModBlocks.tape_recorder, 4), new Object[] { "TST", "SSS", 'T', W.ingot(), 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.steel_poles, 16), new Object[] { "S S", "SSS", "S S", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.pole_top, 1), new Object[] { "T T", "TRT", "BBB", 'T', W.ingot(), 'B', BE.ingot(), 'R', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.pole_satellite_receiver, 1), new Object[] { "SS ", "SCR", "SS ", 'S', STEEL.ingot(), 'C', ModItems.circuit_red_copper, 'R', ModItems.wire_red_copper });
		addRecipeAuto(new ItemStack(ModBlocks.steel_beam, 8), new Object[] { "S", "S", "S", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.steel_wall, 4), new Object[] { "SSS", "SSS", 'S', STEEL.ingot() });
		addShapelessAuto(new ItemStack(ModBlocks.steel_corner), new Object[] { Item.getItemFromBlock(ModBlocks.steel_wall), Item.getItemFromBlock(ModBlocks.steel_wall) });
		addRecipeAuto(new ItemStack(ModBlocks.steel_roof, 2), new Object[] { "SSS", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8), new Object[] { "SSS", " S ", "SSS", 'S', STEEL.ingot() });

		addRecipeAuto(new ItemStack(Armory.gun_rpg, 1), new Object[] { "SSW", " MW", 'S', ModItems.hull_small_steel, 'W', IRON.plate(), 'M', Armory.mechanism_launcher_1 });
		addRecipeAuto(new ItemStack(Armory.gun_panzerschreck, 1), new Object[] { "SSS", " MW", 'S', ModItems.hull_small_steel, 'W', CU.plate(), 'M', Armory.mechanism_launcher_1 });
		addRecipeAuto(new ItemStack(Armory.gun_karl, 1), new Object[] { "SSW", " MW", 'S', ModItems.hull_small_steel, 'W', ALLOY.plate(), 'M', Armory.mechanism_launcher_2 });
		addRecipeAuto(new ItemStack(Armory.gun_quadro, 1), new Object[] { "SSS", "SSS", "CM ", 'S', ModItems.hull_small_steel, 'C', ModItems.circuit_targeting_tier3, 'M', Armory.mechanism_launcher_2 });
		addRecipeAuto(new ItemStack(Armory.gun_hk69, 1), new Object[] { "SSI", " MB", 'S', ModItems.hull_small_steel, 'I', IRON.ingot(), 'M', Armory.mechanism_launcher_1, 'B', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(Armory.gun_stinger, 1), new Object[] { "SSW", "CMW", 'S', STEEL.plate(), 'W', TI.plate(), 'C', ModItems.circuit_red_copper, 'M', Armory.mechanism_launcher_2 });
		addRecipeAuto(new ItemStack(Armory.gun_stinger_ammo, 4), new Object[] { "SS ", "STI", " IR", 'S', STEEL.plate(), 'T', Item.getItemFromBlock(Blocks.TNT), 'I', AL.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(Armory.gun_revolver, 1), new Object[] { "SSM", " RW", 'S', STEEL.plate(), 'W', KEY_PLANKS, 'R', ModItems.wire_aluminium, 'M', Armory.mechanism_revolver_1 });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_saturnite, 1), new Object[] { "SSM", " RW", 'S', BIGMT.plate(), 'W', KEY_PLANKS, 'R', ModItems.wire_tungsten, 'M', Armory.mechanism_revolver_2 });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_iron, 1), new Object[] { "SSM", " RW", 'S', IRON.plate(), 'W', KEY_PLANKS, 'R', ModItems.wire_aluminium, 'M', Armory.mechanism_revolver_1 });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_gold, 1), new Object[] { "SSM", " RW", 'S', GOLD.plate(), 'W', GOLD.ingot(), 'R', ModItems.wire_gold, 'M', Armory.mechanism_revolver_1 });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_lead, 1), new Object[] { "SSM", " RW", 'S', PB.plate(), 'W', W.ingot(), 'R', ModItems.wire_tungsten, 'M', Armory.mechanism_revolver_2 });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_schrabidium, 1), new Object[] { "SSM", " RW", 'S', SA326.block(), 'W', W.ingot(), 'R', ModItems.wire_schrabidium, 'M', Armory.mechanism_special });
		addRecipeAuto(new ItemStack(Armory.gun_deagle, 1), new Object[] { "PPM", " BI", 'P', STEEL.plate(), 'B', ModItems.bolt_tungsten, 'I', ANY_PLASTIC.ingot(), 'M', Armory.mechanism_rifle_1 });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_cursed, 1), new Object[] { "TTM", "SRI", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'R', ModItems.wire_red_copper, 'T', TI.plate(), 'M', Armory.mechanism_revolver_2 });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_nightmare, 1), new Object[] { "SEM", " RW", 'S', STEEL.plate(), 'W', KEY_PLANKS, 'R', ModItems.wire_aluminium, 'E', Powders.powder_power, 'M', Armory.mechanism_revolver_2 });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_nightmare2, 1), new Object[] { "SSM", "RRW", 'S', OreDictManager.getReflector(), 'W', W.ingot(), 'R', ModItems.wire_gold, 'M', Armory.mechanism_special });
		addRecipeAuto(new ItemStack(Armory.gun_fatman, 1), new Object[] { "SSI", "IIM", "WPH", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'W', ModItems.wire_aluminium, 'H', ModItems.hull_small_steel, 'P', Item.getItemFromBlock(Blocks.PISTON), 'M', Armory.mechanism_launcher_2 });

		addRecipeAuto(new ItemStack(Armory.gun_mirv, 1), new Object[] { "LLL", "WFW", "SSS", 'S', STEEL.plate(), 'L', PB.plate(), 'W', ModItems.wire_gold, 'F', Armory.gun_fatman });
		addRecipeAuto(new ItemStack(Armory.gun_proto, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ANY_RUBBER.ingot(), 'L', ModItems.plate_desh, 'W', ModItems.wire_tungsten, 'F', Armory.gun_fatman });
		//addRecipeAuto(new ItemStack(ModItems.gun_bf, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ModItems.plate_paa, 'L', OreDictManager.getReflector(), 'W', ModItems.wire_advanced_alloy, 'F', ModItems.gun_mirv });
		addRecipeAuto(new ItemStack(Armory.gun_bf_ammo, 1), new Object[] { " S ", "EBE", " S ", 'S', ModItems.hull_small_steel, 'E', Powders.powder_power, 'B', ModItems.egg_balefire_shard });
		addRecipeAuto(new ItemStack(Armory.gun_mp40, 1), new Object[] { "IIM", " SW", " S ", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'W', KEY_PLANKS, 'M', Armory.mechanism_rifle_2 });
		addRecipeAuto(new ItemStack(Armory.gun_thompson, 1), new Object[] { "IIM", " SW", " S ", 'S', IRON.plate(), 'I', STEEL.plate(), 'W', KEY_PLANKS, 'M', Armory.mechanism_rifle_2 });
		addRecipeAuto(new ItemStack(Armory.gun_flechette, 1), new Object[] { "PPM", "TIS", "G  ", 'P', STEEL.plate(), 'M', Armory.mechanism_rifle_2, 'T', ModItems.hull_small_steel, 'I', STEEL.ingot(), 'S', ANY_PLASTIC.ingot(), 'G', Armory.mechanism_launcher_1 });
		addRecipeAuto(new ItemStack(Armory.gun_uboinik, 1), new Object[] { "IIM", "SPW", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'W', KEY_PLANKS, 'S', Items.STICK, 'M', Armory.mechanism_revolver_2 });
		addRecipeAuto(new ItemStack(Armory.gun_xvl1456, 1), new Object[] { "PBB", "ACC", "PRY", 'P', STEEL.plate(), 'R', ModItems.redcoil_capacitor, 'A', ModItems.coil_advanced_alloy, 'B', Batteries.battery_generic, 'C', ModItems.coil_advanced_torus, 'Y', Armory.mechanism_special });
		addRecipeAuto(new ItemStack(Armory.gun_xvl1456_ammo, 64), new Object[] { "SSS", "SRS", "SSS", 'S', STEEL.plate(), 'R', RetroRods.rod_quad_uranium_fuel_depleted });
		addRecipeAuto(new ItemStack(Armory.gun_xvl1456_ammo, 32), new Object[] { " S ", "SRS", " S ", 'S', STEEL.plate(), 'R', RetroRods.rod_dual_uranium_fuel_depleted });
		addRecipeAuto(new ItemStack(Armory.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', STEEL.plate(), 'R', RetroRods.rod_uranium_fuel_depleted });
		addRecipeAuto(new ItemStack(Armory.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', STEEL.plate(), 'R', RetroRods.rod_uranium_fuel_depleted });
		addRecipeAuto(new ItemStack(Armory.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', STEEL.plate(), 'R', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', STEEL.plate(), 'R', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.gun_osipr, 1), new Object[] { "CCT", "WWI", "MCC", 'C', CMB.plate(), 'T', W.ingot(), 'W', ModItems.wire_magnetized_tungsten, 'I', Armory.mechanism_rifle_2, 'M', ModItems.coil_magnetized_tungsten });
		addRecipeAuto(new ItemStack(Armory.gun_immolator, 1), new Object[] { "WCC", "PMT", "WAA", 'W', ModItems.wire_gold, 'C', CU.plate(), 'P', ALLOY.plate(), 'M', Armory.mechanism_launcher_1, 'T', ModItems.tank_steel, 'A', STEEL.plate() });
		addRecipeAuto(new ItemStack(Armory.gun_immolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', STEEL.plate(), 'C', COAL.dust(), 'P',P_RED.dust() });
		addRecipeAuto(new ItemStack(Armory.gun_immolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)) });
		addRecipeAuto(new ItemStack(Armory.gun_immolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.kerosene)) });
		addRecipeAuto(new ItemStack(Armory.gun_immolator_ammo, 24), new Object[] { " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ModItems.canister_napalm });
		addRecipeAuto(new ItemStack(Armory.gun_immolator_ammo, 32), new Object[] { " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.nitan)) });
		addRecipeAuto(new ItemStack(Armory.gun_cryolator, 1), new Object[] { "SSS", "IWL", "LMI", 'S', STEEL.plate(), 'I', IRON.plate(), 'L', Items.LEATHER, 'M', Armory.mechanism_launcher_1, 'W', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(Armory.gun_cryolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', STEEL.plate(), 'C', KNO.dust(), 'P', Items.SNOWBALL });
		addRecipeAuto(new ItemStack(Armory.gun_cryolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', Powders.powder_ice });
		addRecipeAuto(new ItemStack(Armory.gun_mp, 1), new Object[] { "EEE", "SSM", "III", 'E', EUPH.ingot(), 'S', STEEL.plate(), 'I', STEEL.ingot(), 'M', Armory.mechanism_rifle_2 });
		addRecipeAuto(new ItemStack(Armory.gun_emp, 1), new Object[] { "CPG", "CMF", "CPI", 'C', ModItems.coil_copper, 'P', PB.plate(), 'G', ModItems.circuit_gold, 'M', ModItems.magnetron, 'I', W.ingot(), 'F', Armory.mechanism_special });
		addRecipeAuto(new ItemStack(Armory.gun_emp_ammo, 8), new Object[] { "IGI", "IPI", "IPI", 'G', GOLD.plate(), 'I', IRON.plate(), 'P', Powders.powder_power });
		addRecipeAuto(new ItemStack(Armory.gun_jack, 1), new Object[] { "WW ", "TSD", " TT", 'W', WEIDANIUM.ingot(), 'T', ModItems.toothpicks, 'S', Armory.gun_uboinik, 'D', ModItems.ducttape });
		addShapelessAuto(new ItemStack(Armory.gun_jack_ammo, 1), new Object[] { Armory.ammo_12gauge, Armory.ammo_12gauge, Armory.ammo_12gauge, Armory.ammo_12gauge });
		addRecipeAuto(new ItemStack(Armory.gun_euthanasia, 1), new Object[] { "TDT", "AAS", " T ", 'A', AUSTRALIUM.ingot(), 'T', ModItems.toothpicks, 'S', Armory.gun_mp40, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Armory.gun_euthanasia_ammo, 12), new Object[] { "P", "S", "N", 'P', Powders.powder_poison, 'N', KNO.dust(), 'S', ModItems.syringe_metal_empty });
		addRecipeAuto(new ItemStack(Armory.gun_spark, 1), new Object[] { "TTD", "AAS", "  T", 'A', DAFFERGON.ingot(), 'T', ModItems.toothpicks, 'S', Armory.gun_rpg, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Armory.gun_spark_ammo, 4), new Object[] { "PCP", "DDD", "PCP", 'P', PB.plate(), 'C', ModItems.coil_gold, 'D', Powders.powder_power });
		addRecipeAuto(new ItemStack(Armory.gun_skystinger, 1), new Object[] { "TTT", "AAS", " D ", 'A', UNOBTAINIUM.ingot(), 'T', ModItems.toothpicks, 'S', Armory.gun_stinger, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Armory.gun_hp, 1), new Object[] { "TDT", "ASA", " T ", 'A', REIIUM.ingot(), 'T', ModItems.toothpicks, 'S', Armory.gun_xvl1456, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Armory.gun_hp_ammo, 8), new Object[] { " R ", "BSK", " Y ", 'S', STEEL.plate(), 'K', new ItemStack(Items.DYE, 1, 0), 'R', new ItemStack(Items.DYE, 1, 1), 'B', new ItemStack(Items.DYE, 1, 4), 'Y', new ItemStack(Items.DYE, 1, 11) });
		addRecipeAuto(new ItemStack(Armory.gun_defabricator_ammo, 16), new Object[] { "PCP", "DDD", "PCP", 'P', STEEL.plate(), 'C', ModItems.coil_copper, 'D', LI.dust() });
		addRecipeAuto(new ItemStack(Armory.gun_lever_action, 1), new Object[] { "PPI", "SWD", 'P', IRON.plate(), 'I', Armory.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(Armory.gun_lever_action_dark, 1), new Object[] { "PPI", "SWD", 'P', STEEL.plate(), 'I', Armory.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(Armory.gun_bolt_action, 1), new Object[] { "PPI", "SWD", 'P', STEEL.plate(), 'I', Armory.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_copper });
		addRecipeAuto(new ItemStack(Armory.gun_bolt_action_green, 1), new Object[] { "PPI", "SWD", 'P', IRON.plate(), 'I', Armory.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_copper });
		addRecipeAuto(new ItemStack(Armory.gun_bolt_action_saturnite, 1), new Object[] { "PPI", "SWD", 'P', BIGMT.plate(), 'I', Armory.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_tungsten });
		addRecipeAuto(new ItemStack(Armory.gun_b92, 1), new Object[] { "DDD", "SSC", "  R", 'D', ModItems.plate_dineutronium, 'S', STAR.ingot(), 'C', ModItems.circuit_targeting_tier6, 'R', Armory.gun_revolver_schrabidium });
		addRecipeAuto(new ItemStack(Armory.gun_b92_ammo, 1), new Object[] { "PSP", "ESE", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'E', Powders.powder_spark_mix });
		addShapelessAuto(new ItemStack(Armory.weaponized_starblaster_cell, 1), new Object[] { new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.acid)), new IngredientContainsTag(GunB92Cell.getFullCell()), ModItems.wire_copper });
		addRecipeAuto(new ItemStack(Armory.gun_uzi, 1), new Object[] { "SMS", " PB", " P ", 'S', STEEL.ingot(), 'M', Armory.mechanism_rifle_2, 'P', STEEL.plate(), 'B', ModItems.bolt_dura_steel });
		addRecipeAuto(new ItemStack(Armory.gun_uzi_silencer, 1), new Object[] { "P  ", " P ", "  U", 'P', ANY_PLASTIC.ingot(), 'U', Armory.gun_uzi });
		addRecipeAuto(new ItemStack(Armory.gun_uzi_saturnite, 1), new Object[] { "SMS", " PB", " P ", 'S', BIGMT.ingot(), 'M', Armory.mechanism_rifle_2, 'P', BIGMT.plate(), 'B', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(Armory.gun_uzi_saturnite_silencer, 1), new Object[] { "P  ", " P ", "  U", 'P', ANY_PLASTIC.ingot(), 'U', Armory.gun_uzi_saturnite });
		addRecipeAuto(new ItemStack(Armory.gun_bolter, 1), new Object[] { "SSM", "PIP", " I ", 'S', BIGMT.plate(), 'I', BIGMT.ingot(), 'M', Armory.mechanism_special, 'P', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(Armory.gun_vortex, 1), new Object[] { "AS ", "SIP", " SC", 'S', ModItems.plate_armor_lunar, 'I', Armory.gun_xvl1456, 'A', ModItems.levitation_unit, 'P', ModItems.circuit_tantalium, 'C', Crystals.crystal_trixite });

		addShapelessAuto(new ItemStack(Armory.ammo_44, 1), new Object[] { Armory.gun_revolver_nopip_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_44_pip, 1), new Object[] { Armory.gun_revolver_pip_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_50bmg, 1), new Object[] { Armory.gun_calamity_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_5mm, 1), new Object[] { Armory.gun_lacunae_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_rocket, 1), new Object[] { Armory.gun_rpg_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_9mm, 1), new Object[] { Armory.gun_mp40_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_22lr, 1), new Object[] { Armory.gun_uzi_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_12gauge, 1), new Object[] { Armory.gun_uboinik_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_20gauge, 1), new Object[] { Armory.gun_lever_action_ammo });
		addShapelessAuto(new ItemStack(Armory.ammo_20gauge_slug, 1), new Object[] { Armory.gun_bolt_action_ammo });

		reg2();

	}

	public static void reg2(){

		addRecipeAuto(new ItemStack(ModItems.pellet_flechette, 1), new Object[] { " L ", " L ", "LLL", 'L', PB.nugget() });
		addRecipeAuto(new ItemStack(Armory.assembly_iron, 24), new Object[] { " I", "GC", " P", 'I', IRON.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_357, 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_iron, 24), new Object[] { " I", "GC", " P", 'I', IRON.ingot(), 'G', ModItems.ballistite, 'C', Armory.casing_357, 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_steel, 24), new Object[] { " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_357, 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_steel, 24), new Object[] { " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.ballistite, 'C', Armory.casing_357, 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', U235.ingot(), 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', PU239.ingot(), 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.trinitite, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.nuclear_waste_tiny, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_gold, 24), new Object[] { " I", "GC", " P", 'I', GOLD.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_357, 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_schrabidium, 6), new Object[] { " I ", "GCN", " P ", 'I', SA326.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_357, 'P', Armory.primer_357, 'N', Items.NETHER_STAR });
		addRecipeAuto(new ItemStack(Armory.assembly_nightmare, 24), new Object[] { " I", "GC", " P", 'I', W.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_357, 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_desh, 24), new Object[] { " I", "GC", " P", 'I', DESH.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_357, 'P', Armory.primer_357 });
		addRecipeAuto(new ItemStack(Armory.assembly_smg, 32), new Object[] { " I", "GC", " P", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_9, 'P', Armory.primer_9 });
		addRecipeAuto(new ItemStack(Armory.assembly_556, 32), new Object[] { " I", "GC", " P", 'I', STEEL.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_9, 'P', Armory.primer_9 });
		addRecipeAuto(new ItemStack(Armory.ammo_556_k, 32), new Object[] { "G", "C", "P", 'G', ANY_GUNPOWDER.dust(), 'C', Armory.casing_9, 'P', Armory.primer_9 });
		addRecipeAuto(new ItemStack(Armory.assembly_uzi, 32), new Object[] { " I", "GC", " P", 'I', IRON.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_9, 'P', Armory.primer_9 });
		addRecipeAuto(new ItemStack(Armory.assembly_lacunae, 32), new Object[] { " I", "GC", " P", 'I', CU.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_9, 'P', Armory.primer_9 });
		addRecipeAuto(new ItemStack(Armory.assembly_nopip, 24), new Object[] { " I", "GC", " P", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_44, 'P', Armory.primer_44 });
		addRecipeAuto(new ItemStack(Armory.ammo_12gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_buckshot, 'P', Armory.primer_buckshot, 'L', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_buckshot, 'P', Armory.primer_buckshot, 'L', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_buckshot, 'P', Armory.primer_buckshot, 'L', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_explosive, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_cluster, 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_buckshot, 'P', Armory.primer_buckshot, 'L', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_buckshot, 'P', Armory.primer_buckshot, 'L', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_nightmare2_ammo, 12), new Object[] { "I", "C", "P", 'I', Powders.powder_power, 'C', Armory.casing_buckshot, 'P', Armory.primer_buckshot });
		addRecipeAuto(new ItemStack(Armory.assembly_calamity, 12), new Object[] { " I ", "GCG", " P ", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_50, 'P', Armory.primer_50 });
		addRecipeAuto(new ItemStack(Armory.assembly_actionexpress, 12), new Object[] { " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', Armory.casing_50, 'P', Armory.primer_50 });
		addRecipeAuto(new ItemStack(Armory.ammo_dart, 16), new Object[] { "IPI", "ICI", "IPI", 'I', ModItems.plate_polymer, 'P', IRON.plate(), 'C', new IngredientContainsTag(ItemFluidTank.getFullBarrel(ModForgeFluids.watz)) });

		addRecipeAuto(new ItemStack(Armory.ammo_12gauge_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_12gauge, 'A',P_RED.dust() });
		addRecipeAuto(new ItemStack(Armory.ammo_12gauge_shrapnel, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_12gauge, 'A', ModBlocks.gravel_obsidian });
		addRecipeAuto(new ItemStack(Armory.ammo_12gauge_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_12gauge, 'A', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_12gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_12gauge, 'A', ModItems.coin_maskman });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_20gauge, 'A',P_RED.dust() });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_shrapnel, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_20gauge, 'A', ModBlocks.gravel_obsidian });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_caustic, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_20gauge, 'A', Powders.powder_poison });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_shock, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_20gauge, 'A', DIAMOND.dust() });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_wither, 4), new Object[] { "BCB", "CAC", "BCB", 'B', Armory.ammo_20gauge, 'A', Blocks.SOUL_SAND, 'C', COAL.dust() });
		addRecipeAuto(new ItemStack(Armory.ammo_20gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_20gauge, 'A', ModItems.coin_maskman });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_4gauge, 'A', ModItems.coin_maskman });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_flechette_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_4gauge_flechette, 'A', P_WHITE.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_balefire, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_4gauge_explosive, 'A', ModItems.egg_balefire_shard });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_kampf, 2), new Object[] { "G", "R", 'G', Armory.ammo_rocket, 'R', Armory.ammo_4gauge_explosive });
		addRecipeAuto(new ItemStack(Armory.ammo_44_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_44, 'A', DURA.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_44_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_44, 'A', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_44_star, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_44_du, 'A', STAR.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_5mm_explosive, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_5mm, 'A', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_5mm_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_5mm, 'A', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_5mm_star, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_5mm_du, 'A', STAR.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_9mm_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_9mm, 'A', DURA.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_9mm_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_9mm, 'A', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_22lr_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_22lr, 'A', DURA.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_50bmg_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50bmg, 'A',P_RED.dust() });
		addRecipeAuto(new ItemStack(Armory.ammo_50bmg_explosive, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50bmg, 'A', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_50bmg_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50bmg, 'A', DURA.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_50bmg_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50bmg, 'A', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_50bmg_star, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_50bmg_du, 'A', STAR.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_50bmg_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50bmg, 'A', ModItems.coin_maskman });
		addRecipeAuto(new ItemStack(Armory.ammo_50ae_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50ae, 'A', DURA.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_50ae_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50ae, 'A', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_50ae_star, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_50ae_du, 'A', STAR.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_556_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556, 'A', P_WHITE.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_556_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556, 'A', DURA.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_556_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556, 'A', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_556_star, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_556_du, 'A', STAR.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_556_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556, 'A', ModItems.coin_maskman });
		addRecipeAuto(new ItemStack(Armory.ammo_556_tracer, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556, 'A', Items.REDSTONE });
		addRecipeAuto(new ItemStack(Armory.ammo_556_flechette, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_556, 'A', ModItems.pellet_flechette });
		addRecipeAuto(new ItemStack(Armory.ammo_556_flechette_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556_flechette, 'A',P_RED.dust() });
		addRecipeAuto(new ItemStack(Armory.ammo_556_flechette_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556_flechette, 'A', P_WHITE.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_556_flechette_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556_flechette, 'A', U238.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_556_flechette_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556_flechette, 'A', ModItems.coin_maskman });

		addRecipeAuto(new ItemStack(Armory.folly_bullet, 1), new Object[] { " S ", "STS", "SMS", 'S', STAR.ingot(), 'T', Powders.powder_magic, 'M', ModBlocks.block_meteor });
		addRecipeAuto(new ItemStack(Armory.folly_bullet_nuclear, 1), new Object[] { " N ", "UTU", "UTU", 'N', Armory.ammo_nuke, 'U', IRON.ingot(), 'T', W.block() });
		addRecipeAuto(new ItemStack(Armory.folly_bullet_du, 1), new Object[] { " U ", "UDU", "UTU", 'U', U238.block(), 'D', DESH.block(), 'T', W.block() });
		addRecipeAuto(new ItemStack(Armory.folly_shell, 1), new Object[] { "IPI", "IPI", "IMI", 'I', IRON.ingot(), 'P', IRON.plate(), 'M', Armory.primer_50 });
		addRecipeAuto(new ItemStack(Armory.ammo_folly, 1), new Object[] { " B ", "MEM", " S ", 'B', Armory.folly_bullet, 'M', Powders.powder_magic, 'E', Powders.powder_power, 'S', Armory.folly_shell });
		addRecipeAuto(new ItemStack(Armory.ammo_folly_nuclear, 1), new Object[] { " B ", "EEE", " S ", 'B', Armory.folly_bullet_nuclear, 'E', ModBlocks.det_charge, 'S', Armory.folly_shell });
		addRecipeAuto(new ItemStack(Armory.ammo_folly_du, 1), new Object[] { " B ", "EEE", " S ", 'B', Armory.folly_bullet_du, 'E', ModBlocks.det_charge, 'S', Armory.folly_shell });

		addRecipeAuto(new ItemStack(Armory.ammo_rocket, 1), new Object[] { " T ", "GCG", " P ", 'T', Blocks.TNT, 'G', ModItems.rocket_fuel, 'C', Armory.casing_50, 'P', Armory.primer_50 });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket, 2), new Object[] { " T ", "GCG", " P ", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.rocket_fuel, 'C', Armory.casing_50, 'P', Armory.primer_50 });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_he, 1), new Object[] { "G", "R", 'G', ANY_PLASTICEXPLOSIVE.ingot(), 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_incendiary, 1), new Object[] { "G", "R", 'G',P_RED.dust(), 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_emp, 1), new Object[] { "G", "R", 'G', DIAMOND.dust(), 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_shrapnel, 1), new Object[] { "G", "R", 'G', ModItems.pellet_buckshot, 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_glare, 1), new Object[] { "GGG", "GRG", "GGG", 'G', Items.REDSTONE, 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_toxic, 1), new Object[] { "G", "R", 'G', ModItems.pellet_gas, 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_nuclear, 1), new Object[] { " P ", "NRN", " P ", 'P', PU239.nugget(), 'N', OreDictManager.getReflector(), 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', STEEL.plate(), 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', STEEL.plate(), 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.petroil)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', STEEL.plate(), 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.biofuel)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_sleek, 64), new Object[] { "GGG", "GRG", "GGG", 'G', Armory.ammo_rocket, 'R', ModItems.coin_maskman });

		addRecipeAuto(new ItemStack(Armory.ammo_grenade, 2), new Object[] { " T ", "GCI", 'T', ANY_HIGHEXPLOSIVE.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_50, 'I', IRON.plate() });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_tracer, 2), new Object[] { " T ", "GCI", " P ", 'T', LAPIS.dust(), 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_50, 'P', Armory.primer_50, 'I', IRON.plate() });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_he, 2), new Object[] { "GIG", 'G', Armory.ammo_grenade, 'I', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_incendiary, 2), new Object[] { "GIG", 'G', Armory.ammo_grenade, 'I',P_RED.dust() });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_toxic, 2), new Object[] { "GIG", 'G', Armory.ammo_grenade, 'I', Powders.powder_poison });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_concussion, 2), new Object[] { "GIG", 'G', Armory.ammo_grenade, 'I', Items.GLOWSTONE_DUST });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_nuclear, 2), new Object[] { " P ", "GIG", " P ", 'G', Armory.ammo_grenade, 'I', ModItems.neutron_reflector, 'P', PU239.nugget() });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_finned, 1), new Object[] { "G", "R", 'G', Items.FEATHER, 'R', Armory.ammo_grenade });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_kampf, 2), new Object[] { "G", "R", 'G', Armory.ammo_rocket, 'R', Armory.ammo_grenade });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_sleek, 64), new Object[] { "GGG", "GRG", "GGG", 'G', Armory.ammo_grenade, 'R', ModItems.coin_maskman });

		addRecipeAuto(new ItemStack(ModItems.stamp_357, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', Armory.casing_357 });
		addRecipeAuto(new ItemStack(ModItems.stamp_44, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', Armory.casing_44 });
		addRecipeAuto(new ItemStack(ModItems.stamp_9, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', Armory.casing_9 });
		addRecipeAuto(new ItemStack(ModItems.stamp_50, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', Armory.casing_50 });

		addRecipeAuto(new ItemStack(ModItems.stamp_desh_357, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', Armory.casing_357 });
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_44, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', Armory.casing_44 });
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_9, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', Armory.casing_9 });
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_50, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', Armory.casing_50 });

		addRecipeAuto(new ItemStack(Armory.casing_357, 1), new Object[] { " P ", "   ", "P P", 'P', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_44, 1), new Object[] { "P", " ", "P", 'P', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_9, 1), new Object[] { "P", "P", 'P', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_50, 1), new Object[] { " P ", " P ", "PPP", 'P', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_buckshot, 1), new Object[] { "P P", "PPP", 'P', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_357, 1), new Object[] { " P ", "   ", "P P", 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_44, 1), new Object[] { "P", " ", "P", 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_9, 1), new Object[] { "P", "P", 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_50, 1), new Object[] { " P ", " P ", "PPP", 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(Armory.casing_buckshot, 1), new Object[] { "P P", "PPP", 'P', IRON.plate() });

		addRecipeAuto(new ItemStack(Armory.primer_357, 1), new Object[] { "R", "P", 'P', IRON.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(Armory.primer_44, 1), new Object[] { "P", "R", 'P', IRON.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(Armory.primer_9, 1), new Object[] { "R", "P", 'P', AL.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(Armory.primer_50, 1), new Object[] { "P", "R", 'P', AL.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(Armory.primer_buckshot, 1), new Object[] { "R", "P", 'P', CU.plate(), 'R', REDSTONE.dust() });

		addRecipeAuto(new ItemStack(Armory.turret_light_ammo, 1), new Object[] { " L ", "IGI", "ICI", 'L', PB.plate(), 'I', IRON.plate(), 'C', CU.plate(), 'G', Items.GUNPOWDER });
		addRecipeAuto(new ItemStack(Armory.turret_heavy_ammo, 1), new Object[] { "LGC", "LGC", "LGC", 'L', PB.plate(), 'C', CU.plate(), 'G', Items.GUNPOWDER });
		addRecipeAuto(new ItemStack(Armory.turret_rocket_ammo, 1), new Object[] { "TS ", "SGS", " SR", 'T', Blocks.TNT, 'S', STEEL.plate(), 'G', Items.GUNPOWDER, 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(Armory.turret_flamer_ammo, 1), new Object[] { "FSF", "FPF", "FPF", 'F', Armory.gun_immolator_ammo, 'S', ModItems.pipes_steel, 'P', CU.plate() });
		addRecipeAuto(new ItemStack(Armory.turret_tau_ammo, 1), new Object[] { "AAA", "AUA", "AAA", 'A', Armory.gun_xvl1456_ammo, 'U', U238.block() });
		addRecipeAuto(new ItemStack(Armory.turret_spitfire_ammo, 1), new Object[] { "CP ", "PTP", " PR", 'P', STEEL.plate(), 'C', ModItems.circuit_copper, 'T', Blocks.TNT, 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(Armory.turret_cwis_ammo, 1), new Object[] { "LLL", "GGG", "IGI", 'L', PB.plate(), 'I', IRON.plate(), 'G', Items.GUNPOWDER });
		addRecipeAuto(new ItemStack(Armory.turret_cheapo_ammo, 1), new Object[] { "ILI", "IGI", "ICI", 'L', PB.plate(), 'I', STEEL.plate(), 'C', CU.plate(), 'G', Items.GUNPOWDER });

		addRecipeAuto(new ItemStack(Armory.grenade_generic, 4), new Object[] { "RS ", "ITI", " I ", 'I', IRON.plate(), 'R', ModItems.wire_red_copper, 'S', STEEL.plate(), 'T', Item.getItemFromBlock(Blocks.TNT) });
		addRecipeAuto(new ItemStack(Armory.grenade_strong, 2), new Object[] { " G ", "SGS", " S ", 'G', Armory.grenade_generic, 'S', Items.GUNPOWDER });
		addRecipeAuto(new ItemStack(Armory.grenade_frag, 2), new Object[] { " G ", "WGW", " K ", 'G', Armory.grenade_generic, 'W', Item.getItemFromBlock(Blocks.PLANKS), 'K', Item.getItemFromBlock(Blocks.GRAVEL) });
		addRecipeAuto(new ItemStack(Armory.grenade_poison, 2), new Object[] { " G ", "PGP", " P ", 'G', Armory.grenade_generic, 'P', Powders.powder_poison });
		addRecipeAuto(new ItemStack(Armory.grenade_gas, 2), new Object[] { " G ", "CGC", " C ", 'G', Armory.grenade_generic, 'C', ModItems.pellet_gas });
		addRecipeAuto(new ItemStack(Armory.grenade_aschrab, 1), new Object[] { "RS ", "ITI", " S ", 'I', "paneGlassColorless", 'R', ModItems.wire_red_copper, 'S', STEEL.plate(), 'T', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.aschrab)) });
		addRecipeAuto(new ItemStack(Armory.grenade_mk2, 2), new Object[] { " G ", "SGS", " S ", 'G', Armory.grenade_strong, 'S', Items.GUNPOWDER });
		addShapelessAuto(new ItemStack(Armory.grenade_gascan, 1), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)), Items.FLINT });
		addShapelessAuto(new ItemStack(Armory.grenade_gascan, 1), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.biofuel)), Items.FLINT });
		addShapelessAuto(new ItemStack(Armory.grenade_gascan, 1), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.petroil)), Items.FLINT });
		addShapelessAuto(new ItemStack(Armory.grenade_gascan, 1), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.kerosene)), Items.FLINT });
		addShapelessAuto(new ItemStack(Armory.grenade_lemon, 1), new Object[] { Foods.lemon, Armory.grenade_strong });
		addShapelessAuto(new ItemStack(Armory.gun_moist_nugget, 12), new Object[] { Items.BREAD, Items.WHEAT, Items.COOKED_CHICKEN, Items.EGG });
		addRecipeAuto(new ItemStack(Armory.grenade_smart, 4), new Object[] { " A ", "ACA", " A ", 'A', Armory.grenade_strong, 'C', ModItems.circuit_aluminium });
		addRecipeAuto(new ItemStack(Armory.grenade_mirv, 1), new Object[] { "GGG", "GCG", "GGG", 'G', Armory.grenade_smart, 'C', Armory.grenade_generic });
		addRecipeAuto(new ItemStack(Armory.grenade_breach, 1), new Object[] { "G", "G", "P", 'G', Armory.grenade_smart, 'P', BIGMT.plate() });
		addRecipeAuto(new ItemStack(Armory.grenade_burst, 1), new Object[] { "GGG", "GCG", "GGG", 'G', Armory.grenade_breach, 'C', Armory.grenade_generic });

		addRecipeAuto(new ItemStack(Armory.grenade_if_generic, 1), new Object[] { " C ", "PTP", " P ", 'C', ModItems.coil_tungsten, 'P', STEEL.plate(), 'T', Blocks.TNT });
		addRecipeAuto(new ItemStack(Armory.grenade_if_he, 1), new Object[] { "A", "G", "A", 'G', Armory.grenade_if_generic, 'A', Items.GUNPOWDER });
		addRecipeAuto(new ItemStack(Armory.grenade_if_bouncy, 1), new Object[] { "G", "A", 'G', Armory.grenade_if_generic, 'A', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(Armory.grenade_if_sticky, 1), new Object[] { "G", "A", 'G', Armory.grenade_if_generic, 'A', Items.SLIME_BALL });
		addRecipeAuto(new ItemStack(Armory.grenade_if_impact, 1), new Object[] { "G", "A", 'G', Armory.grenade_if_generic, 'A', Items.REDSTONE });
		addRecipeAuto(new ItemStack(Armory.grenade_if_concussion, 1), new Object[] { "G", "A", 'G', Armory.grenade_if_generic, 'A', Items.GLOWSTONE_DUST });
		addRecipeAuto(new ItemStack(Armory.grenade_if_toxic, 1), new Object[] { "G", "A", 'G', Armory.grenade_if_generic, 'A', Powders.powder_poison });
		addRecipeAuto(new ItemStack(Armory.grenade_if_incendiary, 1), new Object[] { "G", "A", 'G', Armory.grenade_if_generic, 'A',P_RED.dust() });
		addRecipeAuto(new ItemStack(Armory.grenade_if_brimstone, 1), new Object[] { "R", "G", "A", 'G', Armory.grenade_if_generic, 'R', REDSTONE.dust(), 'A', Powders.powder_power });
		addRecipeAuto(new ItemStack(Armory.grenade_if_mystery, 1), new Object[] { " A ", "BGB", " A ", 'G', Armory.grenade_if_generic, 'A', Powders.powder_magic, 'B', Powders.powder_cloud });
		addShapelessAuto(new ItemStack(Armory.grenade_if_hopwire, 1), new Object[] { Armory.grenade_if_generic, ModItems.singularity_counter_resonant });
		addShapelessAuto(new ItemStack(Armory.grenade_if_spark, 1), new Object[] { Armory.grenade_if_generic, ModItems.singularity_spark });
		addShapelessAuto(new ItemStack(Armory.grenade_if_null, 1), new Object[] { Armory.grenade_if_generic, ModItems.undefined });

		addRecipeAuto(new ItemStack(Foods.bomb_waffle, 1), new Object[] { "WEW", "MPM", "WEW", 'W', Items.WHEAT, 'E', Items.EGG, 'M', Items.MILK_BUCKET, 'P', ModItems.demon_core_open });
		addRecipeAuto(new ItemStack(Foods.schnitzel_vegan, 3), new Object[] { "RWR", "WPW", "RWR", 'W', ModItems.nuclear_waste, 'R', Items.REEDS, 'P', Items.PUMPKIN_SEEDS });
		addRecipeAuto(new ItemStack(Foods.cotton_candy, 2), new Object[] { " S ", "SPS", " H ", 'P', PU239.nugget(), 'S', Items.SUGAR, 'H', Items.STICK });
		addRecipeAuto(new ItemStack(Foods.apple_schrabidium, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', SA326.nugget(), 'A', Items.APPLE });
		addRecipeAuto(new ItemStack(Foods.apple_schrabidium1, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', SA326.ingot(), 'A', Items.APPLE });
		addRecipeAuto(new ItemStack(Foods.apple_schrabidium2, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', SA326.block(), 'A', Items.APPLE });
		addRecipeAuto(new ItemStack(Foods.apple_lead, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', PB.nugget(), 'A', Items.APPLE });
		addRecipeAuto(new ItemStack(Foods.apple_lead1, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', PB.ingot(), 'A', Items.APPLE });
		addRecipeAuto(new ItemStack(Foods.apple_lead2, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', PB.block(), 'A', Items.APPLE });
		addShapelessAuto(new ItemStack(Foods.tem_flakes, 1, 0), new Object[] { GOLD.nugget(), Items.PAPER });
		addShapelessAuto(new ItemStack(Foods.tem_flakes1, 1, 0), new Object[] { GOLD.nugget(), GOLD.nugget(), GOLD.nugget(), Items.PAPER });
		addShapelessAuto(new ItemStack(Foods.tem_flakes2, 1, 0), new Object[] { Items.GOLD_INGOT, Items.GOLD_INGOT, GOLD.nugget(), GOLD.nugget(), Items.PAPER });
		addShapelessAuto(new ItemStack(Foods.glowing_stew, 1), new Object[] { Items.BOWL, Item.getItemFromBlock(ModBlocks.mush), Item.getItemFromBlock(ModBlocks.mush) });
		addShapelessAuto(new ItemStack(Foods.balefire_scrambled, 1), new Object[] { Items.BOWL, ModItems.egg_balefire, Powders.powder_radspice });
		addShapelessAuto(new ItemStack(Foods.balefire_and_ham, 1), new Object[] { Foods.balefire_scrambled, Items.COOKED_BEEF, Powders.powder_radspice });
		addShapelessAuto(new ItemStack(Foods.med_ipecac, 1), new Object[] { Items.GLASS_BOTTLE, Items.NETHER_WART });
		addShapelessAuto(new ItemStack(Foods.med_ptsd, 1), new Object[] { Foods.med_ipecac });
		addShapelessAuto(new ItemStack(Foods.pancake, 1), new Object[] { REDSTONE.dust(), DIAMOND.dust(), Items.WHEAT, ModItems.bolt_tungsten, ModItems.wire_copper, STEEL.plate() });
		addShapelessAuto(new ItemStack(Foods.pancake, 1), new Object[] { REDSTONE.dust(), EMERALD.dust(), Items.WHEAT, ModItems.bolt_tungsten, ModItems.wire_copper, STEEL.plate() });

		addRecipeAuto(new ItemStack(Foods.can_empty, 1), new Object[] { "P", "P", 'P', AL.plate() });
		addShapelessAuto(new ItemStack(Foods.can_smart, 1), new Object[] { Foods.can_empty, Items.POTIONITEM, Items.SUGAR, KNO.dust() });
		addShapelessAuto(new ItemStack(Foods.can_creature, 1), new Object[] { Foods.can_empty, Items.POTIONITEM, Items.SUGAR, new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)) });
		addShapelessAuto(new ItemStack(Foods.can_redbomb, 1), new Object[] { Foods.can_empty, Items.POTIONITEM, Items.SUGAR, ModItems.pellet_cluster });
		addShapelessAuto(new ItemStack(Foods.can_mrsugar, 1), new Object[] { Foods.can_empty, Items.POTIONITEM, Items.SUGAR, F.dust() });
		addShapelessAuto(new ItemStack(Foods.can_overcharge, 1), new Object[] { Foods.can_empty, Items.POTIONITEM, Items.SUGAR, S.dust() });
		addShapelessAuto(new ItemStack(Foods.can_luna, 1), new Object[] { Foods.can_empty, Items.POTIONITEM, Items.SUGAR, Powders.powder_meteorite_tiny });

		addRecipeAuto(new ItemStack(Foods.canteen_13, 1), new Object[] { "O", "P", 'O', Items.POTIONITEM, 'P', STEEL.plate() });
		addRecipeAuto(new ItemStack(Foods.canteen_vodka, 1), new Object[] { "O", "P", 'O', Items.POTATO, 'P', STEEL.plate() });

		addRecipeAuto(new ItemStack(Foods.bottle_empty, 6), new Object[] { " G ", "G G", "GGG", 'G', KEY_ANYPANE });
		addShapelessAuto(new ItemStack(Foods.bottle_nuka, 1), new Object[] { Foods.bottle_empty, Items.POTIONITEM, Items.SUGAR, COAL.dust() });
		addShapelessAuto(new ItemStack(Foods.bottle_cherry, 1), new Object[] { Foods.bottle_empty, Items.POTIONITEM, Items.SUGAR, Items.REDSTONE });
		addShapelessAuto(new ItemStack(Foods.bottle_quantum, 1), new Object[] { Foods.bottle_empty, Items.POTIONITEM, Items.SUGAR, ModItems.trinitite });
		addRecipeAuto(new ItemStack(Foods.bottle2_empty, 6), new Object[] { " G ", "G G", "G G", 'G', KEY_ANYPANE });
		addShapelessAuto(new ItemStack(Foods.bottle2_korl, 1), new Object[] { Foods.bottle2_empty, Items.POTIONITEM, Items.SUGAR, CU.dust() });
		addShapelessAuto(new ItemStack(Foods.bottle2_fritz, 1), new Object[] { Foods.bottle2_empty, Items.POTIONITEM, Items.SUGAR, W.dust() });
		addShapelessAuto(new ItemStack(Foods.bottle2_korl_special, 1), new Object[] { Foods.bottle2_empty, Items.POTIONITEM, Items.SUGAR, CU.dust(), SR.dust() });
		addShapelessAuto(new ItemStack(Foods.bottle2_fritz_special, 1), new Object[] { Foods.bottle2_empty, Items.POTIONITEM, Items.SUGAR, W.dust(), TH232.dust() });
		addShapelessAuto(new ItemStack(Foods.bottle2_sunset, 1), new Object[] { Foods.bottle2_empty, Items.POTIONITEM, Items.SUGAR, GOLD.dust() });

		addRecipeAuto(new ItemStack(ModItems.syringe_empty, 6), new Object[] { "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.IRON_BARS), 'C', new IngredientNBT2(new ItemStack(ModItems.cell)), 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.MILK_BUCKET });
		addRecipeAuto(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.MILK_BUCKET });
		addRecipeAuto(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.REEDS });
		addRecipeAuto(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.REEDS });
		addRecipeAuto(new ItemStack(ModItems.syringe_poison, 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.SPIDER_EYE, 'L', PB.dust() });
		addRecipeAuto(new ItemStack(ModItems.syringe_poison, 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.SPIDER_EYE, 'L', Powders.powder_poison });
		addRecipeAuto(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SPS", "NCN", "SPS", 'C', ModItems.syringe_empty, 'S', S.dust(), 'P', PU239.nugget(), 'N', PU238.nugget() });
		addRecipeAuto(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SNS", "PCP", "SNS", 'C', ModItems.syringe_empty, 'S', S.dust(), 'P', PU239.nugget(), 'N', PU238.nugget() });
		addRecipeAuto(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SPS", "NCN", "SPS", 'C', ModItems.syringe_empty, 'S', S.dust(), 'P', PU239.nugget(), 'N', PU238.nugget() });
		addRecipeAuto(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SNS", "PCP", "SNS", 'C', ModItems.syringe_empty, 'S', S.dust(), 'P', PU239.nugget(), 'N', PU238.nugget() });
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_empty, 6), new Object[] { "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.IRON_BARS), 'C', RetroRods.rod_empty, 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_stimpak, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.NETHER_WART, 'S', ModItems.syringe_metal_empty });
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_medx, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.QUARTZ, 'S', ModItems.syringe_metal_empty });
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_psycho, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.GLOWSTONE_DUST, 'S', ModItems.syringe_metal_empty });
		addRecipeAuto(new ItemStack(ModItems.pill_iodine, 8), new Object[] { "IF", 'I', I.dust(), 'F', F.dust() });
		addRecipeAuto(new ItemStack(ModItems.plan_c, 1), new Object[] { "PFP", 'P', Powders.powder_poison, 'F', F.dust() });
		addShapelessAuto(new ItemStack(ModItems.radx, 1), new Object[] { COAL.dust(), COAL.dust(), F.dust() });
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.syringe_antidote });
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.pill_iodine });
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), new Object[] { "LL", "SI", "LL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_super, 'I', ModItems.radaway });
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', ANY_RUBBER.ingot(), 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.syringe_antidote });
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', ANY_RUBBER.ingot(), 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.pill_iodine });
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), new Object[] { "LL", "SI", "LL", 'L', ANY_RUBBER.ingot(), 'S', ModItems.syringe_metal_super, 'I', ModItems.radaway });
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', Foods.bottle_nuka, 'P', STEEL.plate(), 'S', ModItems.syringe_metal_stimpak, 'L', Items.LEATHER });
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', Foods.bottle_nuka, 'P', STEEL.plate(), 'S', ModItems.syringe_metal_stimpak, 'L', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', Foods.bottle_cherry, 'P', STEEL.plate(), 'S', ModItems.syringe_metal_stimpak, 'L', Items.LEATHER });
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', Foods.bottle_cherry, 'P', STEEL.plate(), 'S', ModItems.syringe_metal_stimpak, 'L', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModItems.iv_empty, 4), new Object[] { "A", "B", "A", 'A', ANY_RUBBER.ingot(), 'B', IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.iv_xp_empty, 1), new Object[] { ModItems.iv_empty, Powders.powder_magic });
		addShapelessAuto(new ItemStack(ModItems.radaway, 1), new Object[] { ModItems.iv_blood, COAL.dust(), Items.PUMPKIN_SEEDS});
		addShapelessAuto(new ItemStack(ModItems.radaway_strong, 1), new Object[] { ModItems.radaway, ModBlocks.mush });
		addShapelessAuto(new ItemStack(ModItems.radaway_flush, 1), new Object[] { ModItems.radaway_strong, I.dust() });

		addRecipeAuto(new ItemStack(ModItems.stealth_boy, 1), new Object[] { " B", "LI", "LC", 'B', Item.getItemFromBlock(Blocks.STONE_BUTTON), 'L', Items.LEATHER, 'I', STEEL.ingot(), 'C', ModItems.circuit_red_copper });

		addRecipeAuto(new ItemStack(ModBlocks.sat_dock, 1), new Object[] { "SSS", "PCP", 'S', STEEL.ingot(), 'P', ANY_PLASTIC.ingot(), 'C', ModBlocks.crate_iron });
		addRecipeAuto(new ItemStack(ModBlocks.book_guide, 1), new Object[] { "IBI", "LBL", "IBI", 'B', Items.BOOK, 'I', new ItemStack(Items.DYE, 1, 0), 'L', new ItemStack(Items.DYE, 1, 4) });

		addRecipeAuto(new ItemStack(ModItems.book_guide, 1, 1), new Object[] { "G", "B", "C", 'B', Items.BOOK, 'G', ModItems.rbmk_lid_glass, 'C', ModItems.rbmk_lid });
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, 2), new Object[] { Items.BOOK, Powders.powder_meteorite });
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, 3), new Object[] { Items.BOOK, ModItems.fuse });

		addRecipeAuto(new ItemStack(ModBlocks.rail_highspeed, 16), new Object[] { "S S", "SIS", "S S", 'S', STEEL.ingot(), 'I', IRON.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.rail_booster, 6), new Object[] { "S S", "CIC", "SRS", 'S', STEEL.ingot(), 'I', IRON.plate(), 'R', MINGRADE.ingot(), 'C', ModItems.coil_copper });

		addRecipeAuto(new ItemStack(ModBlocks.bomb_multi, 1), new Object[] { "AAD", "CHF", "AAD", 'A', ModItems.wire_aluminium, 'C', ModItems.circuit_aluminium, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', new ItemStack(Items.DYE, 1, 15) });
		addShapelessAuto(new ItemStack(Powders.powder_ice, 4), new Object[] { Items.SNOWBALL, KNO.dust(), REDSTONE.dust() });
		addShapelessAuto(new ItemStack(Powders.powder_poison, 4), new Object[] { Items.SPIDER_EYE, REDSTONE.dust(), "gemQuartz" });
		addShapelessAuto(new ItemStack(ModItems.pill_herbal), new Object[] { COAL.dust(), Items.POISONOUS_POTATO, Items.NETHER_WART, Items.BEETROOT });
		addShapelessAuto(new ItemStack(ModItems.pellet_gas, 2), new Object[] { Items.WATER_BUCKET, "dustGlowstone", STEEL.plate() , Ingots.ingot_iodine});

		addRecipeAuto(new ItemStack(ModItems.flame_pony, 1), new Object[] { " O ", "DPD", " O ", 'D', new ItemStack(Items.DYE, 1, 11), 'O', new ItemStack(Items.DYE, 1, 9), 'P', Items.PAPER });
		addRecipeAuto(new ItemStack(ModItems.flame_conspiracy, 1), new Object[] { " S ", "STS", " S ", 'S', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)), 'T', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.flame_politics, 1), new Object[] { " I ", "IPI", " I ", 'P', Items.PAPER, 'I', new ItemStack(Items.DYE, 1, 0) });
		addRecipeAuto(new ItemStack(ModItems.flame_opinion, 1), new Object[] { " R ", "RPR", " R ", 'P', Items.PAPER, 'R', new ItemStack(Items.DYE, 1, 1) });

		addRecipeAuto(new ItemStack(ModItems.solid_fuel_presto, 1), new Object[] { " P ", "SRS", " P ", 'P', Items.PAPER, 'S', ModItems.solid_fuel, 'R', REDSTONE.dust() });
		addShapelessAuto(new ItemStack(ModItems.solid_fuel_presto_triplet, 1), new Object[] { ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.ball_dynamite });
		
		addRecipeAuto(new ItemStack(ModBlocks.flame_war, 1), new Object[] { "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.PLANKS), 'T', Item.getItemFromBlock(Blocks.TNT), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion });
		addRecipeAuto(new ItemStack(ModBlocks.emp_bomb, 1), new Object[] { "LML", "LCL", "LML", 'L', PB.plate(), 'M', ModItems.magnetron, 'C', ModItems.circuit_gold });

		addRecipeAuto(new ItemStack(ModItems.gadget_explosive8, 1), new Object[] { "EEE", "EPE", "EEE", 'E', ModItems.gadget_explosive, 'P', AL.plate() });

		addRecipeAuto(new ItemStack(ModItems.man_explosive8, 1), new Object[] { "EEE", "ESE", "EEE", 'E', ModItems.man_explosive, 'S', STEEL.plate() });

		addRecipeAuto(new ItemStack(ModItems.n2_charge, 1), new Object[] { " D ", "ERE", " D ", 'D', ModItems.ducttape, 'E', ModBlocks.det_charge, 'R', REDSTONE.block() });

		addRecipeAuto(new ItemStack(ModItems.custom_tnt, 1), new Object[] { " C ", "TIT", "TIT", 'C', CU.plate(), 'I', IRON.plate(), 'T', ANY_HIGHEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ModItems.custom_nuke, 1), new Object[] { " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', U235.ingot() });
		addRecipeAuto(new ItemStack(ModItems.custom_hydro, 1), new Object[] { " C ", "LTL", "LIL", 'C', CU.plate(), 'L', PB.plate(), 'I', IRON.plate(), 'T', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)) });
		addRecipeAuto(new ItemStack(ModItems.custom_amat, 1), new Object[] { " C ", "MMM", "AAA", 'C', CU.plate(), 'A', AL.plate(), 'M', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)) });
		addRecipeAuto(new ItemStack(ModItems.custom_dirty, 1), new Object[] { " C ", "WLW", "WLW", 'C', CU.plate(), 'L', PB.plate(), 'W', ModItems.nuclear_waste });
		addRecipeAuto(new ItemStack(ModItems.custom_schrab, 1), new Object[] { " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', SA326.ingot() });
		addRecipeAuto(new ItemStack(ModItems.custom_sol, 1), new Object[] { " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', SA327.ingot() });
		addRecipeAuto(new ItemStack(ModItems.custom_euph, 1), new Object[] { " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', EUPH.ingot() });

		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_generic), new Object[] { " A ", "PRP", "PRP", 'A', ModItems.wire_aluminium, 'P', AL.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', CU.plate(), 'S', S.dust(), 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', CU.plate(), 'S', S.dust(), 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_lithium), new Object[] { "A A", "PSP", "PLP", 'A', ModItems.wire_gold, 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_lithium), new Object[] { "A A", "PLP", "PSP", 'A', ModItems.wire_gold, 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_schrabidium), new Object[] { " A ", "PNP", "PSP", 'A', ModItems.wire_schrabidium, 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_schrabidium), new Object[] { " A ", "PSP", "PNP", 'A', ModItems.wire_schrabidium, 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_trixite), new Object[] { " A ", "PSP", "PTP", 'A', ModItems.wire_aluminium, 'P', AL.plate(), 'S', Powders.powder_power, 'T', Crystals.crystal_trixite });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_trixite), new Object[] { " A ", "PTP", "PSP", 'A', ModItems.wire_aluminium, 'P', AL.plate(), 'S', Powders.powder_power, 'T', Crystals.crystal_trixite });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TRD", "PCW", 'P', ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), 'D', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.deuterium)), 'T', W.ingot() });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TDR", "PCW", 'P', ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), 'D', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.deuterium)), 'T', W.ingot() });

		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_red_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_aluminium, 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_generic) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_advanced_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_red_copper, 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_advanced) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_lithium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_gold, 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_lithium) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_schrabidium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_schrabidium, 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_schrabidium) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_red_cell_6), new Object[] { "BBB", "WPW", "BBB", 'W', ModItems.wire_aluminium, 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_red_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_advanced_cell_4), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_red_copper, 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_advanced_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_lithium_cell_3), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_gold, 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_lithium_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_schrabidium_cell_2), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_schrabidium_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_red_cell_24), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_aluminium, 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_red_cell_6) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_advanced_cell_12), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_red_copper, 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_advanced_cell_4) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_lithium_cell_6), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_gold, 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_lithium_cell_3) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_schrabidium_cell_4), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(Batteries.battery_schrabidium_cell_2) });
		
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_spark), new Object[] { "P", "S", "S", 'P', ModItems.plate_dineutronium, 'S', Powders.powder_spark_mix });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_6), new Object[] { "BW", "PW", "BW", 'W', ModItems.wire_magnetized_tungsten, 'P', Powders.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(Batteries.battery_spark) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_25), new Object[] { "W W", "SCS", "PSP", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'S', Powders.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_6) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_100), new Object[] { "W W", "BPB", "SSS", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'S', Powders.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_25) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_1000), new Object[] { "PCP", "CSC", "PCP", 'S', ModItems.singularity_spark, 'P', Powders.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_100) });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_2500), new Object[] { "SCS", "CVC", "SCS", 'C', ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_100), 'V', ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_1000), 'S', Powders.powder_spark_mix });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_10000), new Object[] { "OPO", "VSV", "OPO", 'S', ModItems.singularity_spark, 'V', ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_2500), 'O', Ingots.ingot_osmiridium, 'P', ModItems.plate_dineutronium });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_power), new Object[] { "YPY", "CSC", "YPY", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(Batteries.battery_spark_cell_10000), 'Y', Billets.billet_yharonite, 'P', ModItems.plate_dineutronium });

		addRecipeAuto(ItemBattery.getFullBattery(Batteries.battery_su), new Object[] { "P", "R", "C", 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(Batteries.battery_su), new Object[] { "P", "C", "R", 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(Batteries.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_aluminium, 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(Batteries.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_aluminium, 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(Batteries.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_copper, 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(Batteries.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_copper, 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addShapelessAuto(ItemBattery.getFullBattery(Batteries.battery_potato), new Object[] { Items.POTATO, ModItems.wire_aluminium, ModItems.wire_copper });
		addShapelessAuto(ItemBattery.getFullBattery(Batteries.battery_potatos), new Object[] { ItemBattery.getFullBattery(Batteries.battery_potato), Armory.turret_chip, REDSTONE.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_steam), new Object[] { "PMP", "ISI", "PCP", 'P', CU.plate(), 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new IngredientContainsTag(ItemFluidTank.getFullTank(FluidRegistry.WATER)), 'I', ANY_RUBBER.ingot() });
		addRecipeAuto(ItemBattery.getEmptyBattery(Batteries.battery_steam_large), new Object[] { "MPM", "ISI", "CPC", 'P', ModItems.board_copper, 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new IngredientContainsTag(ItemFluidTank.getFullBarrel(FluidRegistry.WATER)), 'I', ANY_PLASTIC.ingot() });

		if(GeneralConfig.enableBabyMode) {
			addRecipeAuto(new ItemStack(ArmorSets.starmetal_helmet, 1), new Object[] { "EEE", "E E", 'E', STAR.ingot() });
			addRecipeAuto(new ItemStack(ArmorSets.starmetal_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', STAR.ingot() });
			addRecipeAuto(new ItemStack(ArmorSets.starmetal_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', STAR.ingot() });
			addRecipeAuto(new ItemStack(ArmorSets.starmetal_boots, 1), new Object[] { "E E", "E E", 'E', STAR.ingot() });
			addRecipeAuto(new ItemStack(ArmorSets.schrabidium_helmet, 1), new Object[] { "EEE", "E E", 'E', SA326.ingot() });
			addRecipeAuto(new ItemStack(ArmorSets.schrabidium_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', SA326.ingot() });
			addRecipeAuto(new ItemStack(ArmorSets.schrabidium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', SA326.ingot() });
			addRecipeAuto(new ItemStack(ArmorSets.schrabidium_boots, 1), new Object[] { "E E", "E E", 'E', SA326.ingot() });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_sword, 1), new Object[] { "I", "I", "S", 'I', SA326.ingot(), 'S', Items.STICK });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', SA326.ingot(), 'S', Items.STICK });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_axe, 1), new Object[] { "II", "IS", " S", 'I', SA326.ingot(), 'S', Items.STICK });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_shovel, 1), new Object[] { "I", "S", "S", 'I', SA326.ingot(), 'S', Items.STICK });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_hoe, 1), new Object[] { "II", " S", " S", 'I', SA326.ingot(), 'S', Items.STICK });
		} else {
			addRecipeAuto(new ItemStack(ArmorSets.starmetal_helmet, 1), new Object[] { "EEE", "ECE", 'E', STAR.ingot(), 'C', ArmorSets.cobalt_helmet });
			addRecipeAuto(new ItemStack(ArmorSets.starmetal_plate, 1), new Object[] { "ECE", "EEE", "EEE", 'E', STAR.ingot(), 'C', ArmorSets.cobalt_plate });
			addRecipeAuto(new ItemStack(ArmorSets.starmetal_legs, 1), new Object[] { "EEE", "ECE", "E E", 'E', STAR.ingot(), 'C', ArmorSets.cobalt_legs });
			addRecipeAuto(new ItemStack(ArmorSets.starmetal_boots, 1), new Object[] { "E E", "ECE", 'E', STAR.ingot(), 'C', ArmorSets.cobalt_boots });
			addRecipeAuto(new ItemStack(ArmorSets.schrabidium_helmet, 1), new Object[] { "EEE", "ESE", " P ", 'E', SA326.ingot(), 'S', ArmorSets.starmetal_helmet, 'P', ModItems.pellet_charged });
			addRecipeAuto(new ItemStack(ArmorSets.schrabidium_plate, 1), new Object[] { "ESE", "EPE", "EEE", 'E', SA326.ingot(), 'S', ArmorSets.starmetal_plate, 'P', ModItems.pellet_charged });
			addRecipeAuto(new ItemStack(ArmorSets.schrabidium_legs, 1), new Object[] { "EEE", "ESE", "EPE", 'E', SA326.ingot(), 'S', ArmorSets.starmetal_legs, 'P', ModItems.pellet_charged });
			addRecipeAuto(new ItemStack(ArmorSets.schrabidium_boots, 1), new Object[] { "EPE", "ESE", 'E', SA326.ingot(), 'S', ArmorSets.starmetal_boots, 'P', ModItems.pellet_charged });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_sword, 1), new Object[] { "I", "W", "S", 'I', SA326.block(), 'W', ToolSets.desh_sword, 'S', POLYMER.ingot() });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_pickaxe, 1), new Object[] { "SWS", " P ", " P ", 'S', ModItems.blades_schrabidium, 'W', ToolSets.desh_pickaxe, 'P', POLYMER.ingot() });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_axe, 1), new Object[] { "SW", "SP", " P", 'S', ModItems.blades_schrabidium, 'W', ToolSets.desh_axe, 'P', POLYMER.ingot() });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_shovel, 1), new Object[] { "S", "W", "P", 'S', ModItems.blades_schrabidium, 'W', ToolSets.desh_shovel, 'P', POLYMER.ingot() });
			addRecipeAuto(new ItemStack(ToolSets.schrabidium_hoe, 1), new Object[] { "IW", " S", " S", 'I', SA326.ingot(), 'W', ToolSets.desh_hoe, 'S', POLYMER.ingot() });
		}

		addRecipeAuto(new ItemStack(ArmorSets.steel_helmet, 1), new Object[] { "EEE", "E E", 'E', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.steel_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.steel_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.steel_boots, 1), new Object[] { "E E", "E E", 'E', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ToolSets.steel_sword, 1), new Object[] { "I", "I", "S", 'I', STEEL.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.steel_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', STEEL.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.steel_axe, 1), new Object[] { "II", "IS", " S", 'I', STEEL.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.steel_shovel, 1), new Object[] { "I", "S", "S", 'I', STEEL.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.steel_hoe, 1), new Object[] { "II", " S", " S", 'I', STEEL.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ArmorSets.titanium_helmet, 1), new Object[] { "EEE", "E E", 'E', TI.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.titanium_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', TI.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.titanium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', TI.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.titanium_boots, 1), new Object[] { "E E", "E E", 'E', TI.ingot() });
		addRecipeAuto(new ItemStack(ToolSets.titanium_sword, 1), new Object[] { "I", "I", "S", 'I', TI.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.titanium_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', TI.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.titanium_axe, 1), new Object[] { "II", "IS", " S", 'I', TI.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.titanium_shovel, 1), new Object[] { "I", "S", "S", 'I', TI.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.titanium_hoe, 1), new Object[] { "II", " S", " S", 'I', TI.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_sword, 1), new Object[] { "I", "I", "S", 'I', CO.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', CO.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_axe, 1), new Object[] { "II", "IS", " S", 'I', CO.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_shovel, 1), new Object[] { "I", "S", "S", 'I', CO.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_hoe, 1), new Object[] { "II", " S", " S", 'I', CO.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ArmorSets.alloy_helmet, 1), new Object[] { "EEE", "E E", 'E', ALLOY.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.alloy_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ALLOY.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.alloy_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ALLOY.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.alloy_boots, 1), new Object[] { "E E", "E E", 'E', ALLOY.ingot() });
		addRecipeAuto(new ItemStack(ToolSets.alloy_sword, 1), new Object[] { "I", "I", "S", 'I', ALLOY.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.alloy_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ALLOY.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.alloy_axe, 1), new Object[] { "II", "IS", " S", 'I', ALLOY.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.alloy_shovel, 1), new Object[] { "I", "S", "S", 'I', ALLOY.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.alloy_hoe, 1), new Object[] { "II", " S", " S", 'I', ALLOY.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ArmorSets.cmb_helmet, 1), new Object[] { "EEE", "E E", 'E', CMB.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.cmb_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', CMB.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.cmb_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', CMB.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.cmb_boots, 1), new Object[] { "E E", "E E", 'E', CMB.ingot() });
		addRecipeAuto(new ItemStack(ToolSets.cmb_sword, 1), new Object[] { "I", "I", "S", 'I', CMB.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cmb_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', CMB.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cmb_axe, 1), new Object[] { "II", "IS", " S", 'I', CMB.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cmb_shovel, 1), new Object[] { "I", "S", "S", 'I', CMB.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.cmb_hoe, 1), new Object[] { "II", " S", " S", 'I', CMB.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.elec_sword, 1), new Object[] { "RPR", "RPR", " B ", 'P', POLYMER.ingot(), 'R', ModItems.bolt_dura_steel, 'B', Batteries.battery_lithium });
		addRecipeAuto(new ItemStack(ToolSets.elec_pickaxe, 1), new Object[] { "RDM", " PB", " P ", 'P', POLYMER.ingot(), 'D', DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', Batteries.battery_lithium });
		addRecipeAuto(new ItemStack(ToolSets.elec_axe, 1), new Object[] { " DP", "RRM", " PB", 'P', POLYMER.ingot(), 'D', DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', Batteries.battery_lithium });
		addRecipeAuto(new ItemStack(ToolSets.elec_shovel, 1), new Object[] { "  P", "RRM", "  B", 'P', POLYMER.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', Batteries.battery_lithium });
		addShapelessAuto(new ItemStack(ModItems.centri_stick, 1), new Object[] { ModItems.centrifuge_element, ModItems.energy_core, Items.STICK });
		addRecipeAuto(new ItemStack(ModItems.smashing_hammer, 1), new Object[] { "STS", "SPS", " P ", 'S', STEEL.block(), 'T', W.block(), 'P', POLYMER.ingot() });
		addRecipeAuto(new ItemStack(ToolSets.desh_sword, 1), new Object[] { "I", "I", "S", 'I', DESH.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.desh_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', DESH.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.desh_axe, 1), new Object[] { "II", "IS", " S", 'I', DESH.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.desh_shovel, 1), new Object[] { "I", "S", "S", 'I', DESH.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ToolSets.desh_hoe, 1), new Object[] { "II", " S", " S", 'I', DESH.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ArmorSets.security_helmet, 1), new Object[] { "SSS", "IGI", 'S', STEEL.plate(), 'I', ModItems.plate_kevlar, 'G', KEY_ANYPANE });
		addRecipeAuto(new ItemStack(ArmorSets.security_plate, 1), new Object[] { "KWK", "IKI", "WKW", 'K', ModItems.plate_kevlar, 'I', POLYMER.ingot(), 'W', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ArmorSets.security_legs, 1), new Object[] { "IWI", "K K", "W W", 'K', ModItems.plate_kevlar, 'I', POLYMER.ingot(), 'W', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ArmorSets.security_boots, 1), new Object[] { "P P", "I I", 'P', STEEL.plate(), 'I', ModItems.plate_kevlar });

		addRecipeAuto(new ItemStack(ModItems.jetpack_boost, 1), new Object[] { "PTP", "SLS", "W W", 'P', STEEL.plate(), 'T', ModItems.tank_steel, 'S', ModItems.pipes_steel, 'L', Items.LEATHER, 'W', ModItems.thruster_small });
		addRecipeAuto(new ItemStack(ModItems.jetpack_fly, 1), new Object[] { "PTP", "SLS", "W W", 'P', STEEL.plate(), 'T', ModItems.cap_aluminium, 'S', ModItems.pipes_steel, 'L', ModItems.jetpack_boost, 'W', ModItems.thruster_small });
		addRecipeAuto(new ItemStack(ModItems.jetpack_break, 1), new Object[] { "PTP", "SLS", "P P", 'P', STEEL.plate(), 'T', ModItems.cap_aluminium, 'S', ModItems.coil_tungsten, 'L', ModItems.jetpack_boost });
		addRecipeAuto(new ItemStack(ModItems.jetpack_vector, 1), new Object[] { "PTP", "SLS", "W W", 'P', TI.plate(), 'T', ModItems.circuit_copper, 'S', ModItems.motor, 'L', ModItems.jetpack_fly, 'W', ModItems.thruster_small });

		addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)) });
		addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.petroil)) });
		addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.biofuel)) });

		addRecipeAuto(new ItemStack(ModItems.wiring_red_copper, 1), new Object[] { "PPP", "PIP", "PPP", 'P', STEEL.plate(), 'I', STEEL.ingot() });

		addShapelessAuto(new ItemStack(ModItems.gadget_kit, 1), new Object[] { ModBlocks.nuke_gadget, ModItems.gadget_explosive8, ModItems.gadget_explosive8, ModItems.gadget_explosive8, ModItems.gadget_explosive8, ModItems.gadget_wireing, ModItems.gadget_core, ModItems.hazmat_kit });
		addShapelessAuto(new ItemStack(ModItems.boy_kit, 1), new Object[] { ModBlocks.nuke_boy, ModItems.boy_shielding, ModItems.boy_target, ModItems.boy_bullet, ModItems.boy_propellant, ModItems.boy_igniter, ModItems.hazmat_kit });
		addShapelessAuto(new ItemStack(ModItems.man_kit, 1), new Object[] { ModBlocks.nuke_man, ModBlocks.det_nuke, ModItems.man_igniter, ModItems.hazmat_kit });
		addShapelessAuto(new ItemStack(ModItems.mike_kit, 1), new Object[] { ModBlocks.nuke_mike, ModBlocks.det_nuke, ModItems.mike_core, ModItems.mike_deut, ModItems.mike_cooling_unit, ModItems.hazmat_red_kit });
		addShapelessAuto(new ItemStack(ModItems.tsar_kit, 1), new Object[] { ModBlocks.nuke_tsar, ModBlocks.det_nuke, ModItems.mike_core, ModItems.mike_deut, ModItems.mike_core, ModItems.mike_deut, ModItems.hazmat_grey_kit });
		addShapelessAuto(new ItemStack(ModItems.boy_target, 1), new Object[] { ModItems.boy_target_invalid });

		addRecipeAuto(new ItemStack(ArmorSets.hazmat_helmet, 1), new Object[] { "EEE", "EIE", "FPF", 'E', ModItems.hazmat_cloth, 'I', KEY_ANYPANE, 'P', STEEL.plate(), 'F', ModItems.filter_coal });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth });
		addShapelessAuto(new ItemStack(ModItems.hazmat_kit, 1), new Object[] { new ItemStack(ModItems.gas_mask_filter, 1, 0), new ItemStack(ArmorSets.hazmat_helmet, 1, 0), new ItemStack(ArmorSets.hazmat_plate, 1, 0), new ItemStack(ArmorSets.hazmat_legs, 1, 0), new ItemStack(ArmorSets.hazmat_boots, 1, 0) });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_helmet_red, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_red, 'I', KEY_ANYPANE, 'F', ModItems.gas_mask_filter });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_plate_red, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_red });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_legs_red, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_boots_red, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		addShapelessAuto(new ItemStack(ModItems.hazmat_red_kit, 1), new Object[] { new ItemStack(ModItems.gas_mask_filter, 1, 0), new ItemStack(ArmorSets.hazmat_helmet_red, 1, 0), new ItemStack(ArmorSets.hazmat_plate_red, 1, 0), new ItemStack(ArmorSets.hazmat_legs_red, 1, 0), new ItemStack(ArmorSets.hazmat_boots_red, 1, 0) });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_helmet_grey, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_grey, 'I', KEY_ANYPANE, 'F', ModItems.gas_mask_filter });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_plate_grey, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_grey });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_legs_grey, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_boots_grey, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		addShapelessAuto(new ItemStack(ModItems.hazmat_grey_kit, 1), new Object[] { new ItemStack(ModItems.gas_mask_filter_combo, 1, 0), new ItemStack(ArmorSets.hazmat_helmet_grey, 1, 0), new ItemStack(ArmorSets.hazmat_plate_grey, 1, 0), new ItemStack(ArmorSets.hazmat_legs_grey, 1, 0), new ItemStack(ArmorSets.hazmat_boots_grey, 1, 0) });
		addRecipeAuto(new ItemStack(ArmorSets.asbestos_helmet, 1), new Object[] { "EEE", "EIE", 'E', ModItems.asbestos_cloth, 'I', GOLD.plate() });
		addRecipeAuto(new ItemStack(ArmorSets.asbestos_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.asbestos_cloth });
		addRecipeAuto(new ItemStack(ArmorSets.asbestos_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.asbestos_cloth });
		addRecipeAuto(new ItemStack(ArmorSets.asbestos_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.asbestos_cloth });
		
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_paa_helmet, 1), new Object[] { "EEE", "EIE", "FPF", 'E', ModItems.plate_paa, 'I', ArmorSets.liquidator_helmet, 'P', STEEL.plate(), 'F', ModItems.filter_coal });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_paa_plate, 1), new Object[] { "E E", "EIE", "EEE", 'E', ModItems.plate_paa, 'I', ArmorSets.liquidator_plate });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_paa_legs, 1), new Object[] { "EEE", "EIE", "E E", 'E', ModItems.plate_paa, 'I', ArmorSets.liquidator_legs });
		addRecipeAuto(new ItemStack(ArmorSets.hazmat_paa_boots, 1), new Object[] { "E E", "EIE", 'E', ModItems.plate_paa, 'I', ArmorSets.liquidator_boots });
		
		addRecipeAuto(new ItemStack(ArmorSets.paa_helmet, 1), new Object[] { "XGX", "NEN", 'E', ArmorSets.hazmat_paa_helmet, 'N', OreDictManager.getReflector(), 'G', Billets.billet_gh336, 'X', Powders.powder_verticium });
		addRecipeAuto(new ItemStack(ArmorSets.paa_plate, 1), new Object[] { "XGX", "NEN", 'E', ArmorSets.hazmat_paa_plate, 'N', OreDictManager.getReflector(), 'G', Billets.billet_gh336, 'X', Powders.powder_verticium });
		addRecipeAuto(new ItemStack(ArmorSets.paa_legs, 1), new Object[] { "XGX", "NEN", 'E', ArmorSets.hazmat_paa_legs, 'N', OreDictManager.getReflector(), 'G', Billets.billet_gh336, 'X', Powders.powder_verticium });
		addRecipeAuto(new ItemStack(ArmorSets.paa_boots, 1), new Object[] { "XGX", "NEN", 'E', ArmorSets.hazmat_paa_boots, 'N', OreDictManager.getReflector(), 'G', Billets.billet_gh336, 'X', Powders.powder_verticium });

		addRecipeAuto(new ItemStack(ModItems.goggles, 1), new Object[] { "P P", "GPG", 'G', KEY_ANYPANE, 'P', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModItems.gas_mask, 1), new Object[] { "PPP", "GPG", " F ", 'G', KEY_ANYPANE, 'P', STEEL.plate(), 'F', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.gas_mask_m65, 1), new Object[] { "PPP", "GPG", " F ", 'G', KEY_ANYPANE, 'P', ANY_RUBBER.ingot(), 'F', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.gas_mask_mono, 1), new Object[] { " P ", "PPP", " F ", 'P', ANY_RUBBER.ingot(), 'F', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.mask_damp, 1), new Object[] { "RRR", 'R', ModItems.rag_damp });
		addRecipeAuto(new ItemStack(ModItems.mask_piss, 1), new Object[] { "RRR", 'R', ModItems.rag_piss });
		addRecipeAuto(new ItemStack(ModItems.mask_rag, 1), new Object[] { "RRR", 'R', ModItems.rag });
		addRecipeAuto(new ItemStack(ModItems.jetpack_tank, 1), new Object[] { " S ", "BKB", " S ", 'S', STEEL.plate(), 'B', ModItems.bolt_tungsten, 'K', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.kerosene)) });
		addRecipeAuto(new ItemStack(ModItems.gun_kit_1, 4), new Object[] { "I ", "LB", "P ", 'I', ANY_RUBBER.ingot(), 'L', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.lubricant)), 'B', ModItems.bolt_tungsten, 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.gun_kit_2, 1), new Object[] { "III", "GLG", "PPP", 'I', ANY_RUBBER.ingot(), 'L', ModItems.ducttape, 'G', ModItems.gun_kit_1, 'P', IRON.plate() });

		addRecipeAuto(new ItemStack(ModItems.igniter, 1), new Object[] { " W", "SC", "CE", 'S', STEEL.plate(), 'W', ModItems.wire_schrabidium, 'C', ModItems.circuit_schrabidium, 'E', EUPH.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.euphemium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.plate_euphemium });
		addRecipeAuto(new ItemStack(ArmorSets.euphemium_plate, 1), new Object[] { "EWE", "EEE", "EEE", 'E', ModItems.plate_euphemium, 'W', ModItems.watch });
		addRecipeAuto(new ItemStack(ArmorSets.euphemium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.plate_euphemium });
		addRecipeAuto(new ItemStack(ArmorSets.euphemium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.plate_euphemium });
		addRecipeAuto(new ItemStack(ModItems.watch, 1), new Object[] { "LEL", "EWE", "LEL", 'E', EUPH.ingot(), 'L', new ItemStack(Items.DYE, 1, 4), 'W', Items.CLOCK });
		addRecipeAuto(new ItemStack(ModItems.apple_euphemium, 1), new Object[] { "EEE", "EAE", "EEE", 'E', EUPH.nugget(), 'A', Items.APPLE });
		
		addRecipeAuto(new ItemStack(ModItems.mask_of_infamy, 1), new Object[] { "III", "III", " I ", 'I', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.designator, 1), new Object[] { "  A", "#B#", "#B#", '#', IRON.plate(), 'A', STEEL.plate(), 'B', ModItems.circuit_red_copper });
		addRecipeAuto(new ItemStack(ModItems.designator_range, 1), new Object[] { "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', Items.REDSTONE, 'C', ModItems.circuit_gold, 'D', ModItems.designator, 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.designator_manual, 1), new Object[] { "  A", "#C#", "#B#", '#', ANY_PLASTIC.ingot(), 'A', PB.plate(), 'B', ModItems.circuit_gold, 'C', ModItems.designator });
		addRecipeAuto(new ItemStack(ModItems.linker, 1), new Object[] { "I I", "ICI", "GGG", 'I', IRON.plate(), 'G', GOLD.plate(), 'C', ModItems.circuit_gold });
		addRecipeAuto(new ItemStack(ModItems.oil_detector, 1), new Object[] { "W I", "WCI", "PPP", 'W', ModItems.wire_gold, 'I', CU.ingot(), 'C', ModItems.circuit_red_copper, 'P', STEEL.plate() });
		addRecipeAuto(new ItemStack(Armory.turret_chip, 1), new Object[] { "WWW", "CPC", "WWW", 'W', ModItems.wire_gold, 'P', ANY_PLASTIC.ingot(), 'C', ModItems.circuit_gold, });
		addRecipeAuto(new ItemStack(Armory.turret_biometry, 1), new Object[] { "CC ", "GGS", "SSS", 'C', ModItems.circuit_copper, 'S', STEEL.plate(), 'G', GOLD.plate() });
		addRecipeAuto(new ItemStack(ModItems.survey_scanner, 1), new Object[] { "SWS", " G ", "PCP", 'W', ModItems.wire_gold, 'P', ANY_PLASTIC.ingot(), 'C', ModItems.circuit_gold, 'S', STEEL.plate(), 'G', GOLD.ingot() });
		addRecipeAuto(new ItemStack(ModItems.geiger_counter, 1), new Object[] { "GPP", "WCS", "WBB", 'W', ModItems.wire_gold, 'P', ANY_PLASTIC.ingot(), 'C', ModItems.circuit_copper, 'G', GOLD.ingot(), 'S', STEEL.plate(), 'B', BE.ingot() });
		addRecipeAuto(new ItemStack(ModItems.dosimeter, 1), new Object[] { "WGW", "WCW", "WBW", 'W', KEY_PLANKS, 'G', KEY_ANYPANE, 'C', ModItems.circuit_aluminium, 'B', BE.ingot() });
		addRecipeAuto(new ItemStack(ModItems.digamma_diagnostic), new Object[] { "GPP", "WCS", "WBB", 'W', ModItems.wire_tungsten, 'P', REIIUM.ingot(), 'C', ModItems.circuit_schrabidium, 'G', CS.ingot(), 'S', ModItems.plate_desh, 'B', I131.ingot() });
		addRecipeAuto(new ItemStack(ModItems.lung_diagnostic, 1), new Object[] { "WGW", "WCW", "WBW", 'W', AL.ingot(), 'G', ModItems.gas_mask_filter, 'C', ASBESTOS.ingot(), 'B', ModItems.circuit_red_copper });
		addRecipeAuto(new ItemStack(ModItems.fuzzy_identifier, 1), new Object[] { "=  ", "@CS", "@MP", '@', ModItems.wire_gold, 'P', ANY_PLASTIC.ingot(), '=', ModItems.circuit_targeting_tier4, 'M', ModItems.motor_desh, 'C', ModItems.coil_gold, 'S', ModItems.screwdriver_desh });

		addRecipeAuto(new ItemStack(CircuitV2.tube, 1), new Object[] { "G", "W", "I", 'G', KEY_ANYPANE, 'W', ModItems.wire_tungsten, 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(CircuitV2.tube, 1), new Object[] { "G", "W", "I", 'G', KEY_ANYPANE, 'W', ModItems.wire_carbon, 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(CircuitV2.capacitor, 2), new Object[] { "IAI", "W W", 'A', AL.dust(), 'W', ModItems.wire_aluminium, 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(CircuitV2.capacitor, 2), new Object[] { "IAI", "W W", 'A', AL.dust(), 'W', ModItems.wire_copper, 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(CircuitV2.capacitor, 1), new Object[] { "I", "N", "W", 'N', NB.nugget(), 'W', ModItems.wire_aluminium, 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(CircuitV2.capacitor, 1), new Object[] { "I", "N", "W", 'N', NB.nugget(), 'W', ModItems.wire_copper, 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(CircuitV2.capacitor_tantalium, 1), new Object[] { "I", "T", "W", 'I', ModItems.plate_polymer, 'T', TA.nugget(), 'W', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(CircuitV2.capacitor_tantalium, 1), new Object[] { "I", "T", "W", 'I', ModItems.plate_polymer, 'T', TA.nugget(), 'W', ModItems.wire_copper });

		addRecipeAuto(new ItemStack(Inserts.gasSensor, 1), new Object[] { "G", "T", "I", 'G', GOLD.plate(), 'T', CircuitV2.tube, 'I', IRON.plate() });


		addRecipeAuto(new ItemStack(ModItems.key, 1), new Object[] { "  B", " B ", "P  ", 'P', STEEL.plate(), 'B', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.key_kit, 1), new Object[] { "PKP", "DTD", "PKP", 'P', GOLD.plate(), 'K', ModItems.key, 'D', DIAMOND.dust(), 'T', ModItems.screwdriver });
		addRecipeAuto(new ItemStack(ModItems.key_red, 1), new Object[] { "DSC", "SMS", "KSD", 'C', ModItems.circuit_targeting_tier4, 'M', Items.NETHER_STAR, 'K', ModItems.key, 'D', DESH.dust(), 'S', BIGMT.plate() });
		addRecipeAuto(new ItemStack(ModItems.pin, 1), new Object[] { "W ", " W", " W", 'W', ModItems.wire_copper });
		addRecipeAuto(new ItemStack(ModItems.padlock_rusty, 1), new Object[] { "I", "B", "I", 'I', IRON.ingot(), 'B', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.padlock, 1), new Object[] { " P ", "PBP", "PPP", 'P', STEEL.plate(), 'B', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.padlock_reinforced, 1), new Object[] { " P ", "PBP", "PDP", 'P', ALLOY.plate(), 'D',ModItems.plate_desh, 'B', ModItems.bolt_dura_steel });
		addRecipeAuto(new ItemStack(ModItems.padlock_unbreakable, 1), new Object[] { " P ", "PBP", "PDP", 'P', BIGMT.plate(), 'D', DIAMOND.gem(), 'B', ModItems.bolt_dura_steel });

		addRecipeAuto(new ItemStack(ModItems.euphemium_stopper, 1), new Object[] { "I", "S", "S", 'I', EUPH.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', S.dust(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', S.dust(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ModItems.matchstick, 24), new Object[] { "I", "S", 'I',P_RED.dust(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ModItems.crowbar, 1), new Object[] { "II", " I", " I", 'I', STEEL.ingot() });

		addShapelessAuto(new ItemStack(Powders.powder_power, 5), new Object[] { REDSTONE.dust(), "dustGlowstone", DIAMOND.dust(), NP237.dust(),  MAGTUNG.dust() });
		addShapelessAuto(new ItemStack(ModItems.ballistite, 3), new Object[] { Items.GUNPOWDER, KNO.dust(), Items.SUGAR });

		addShapelessAuto(new ItemStack(Powders.powder_nitan_mix, 6), new Object[] { NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CS.dust() });
		addShapelessAuto(new ItemStack(Powders.powder_nitan_mix, 6), new Object[] { SR.dust(), CO.dust(), BR.dust(), TS.dust(), NB.dust(), CE.dust() });
		addShapelessAuto(new ItemStack(Powders.powder_spark_mix, 5), new Object[] { DESH.dust(), EUPH.dust(), Powders.powder_meteorite, Powders.powder_power, Powders.powder_nitan_mix });
		addShapelessAuto(new ItemStack(Powders.powder_meteorite, 5), new Object[] { IRON.dust(), CU.dust(), LI.dust(), W.dust(), U.dust() });
		addShapelessAuto(new ItemStack(Powders.powder_thermite, 4), new Object[] { IRON.dust(), IRON.dust(), IRON.dust(), AL.dust() });
		addShapelessAuto(new ItemStack(Powders.powder_semtex_mix, 3), new Object[] { ModItems.solid_fuel, ModItems.cordite, KNO.dust() });

		addRecipeAuto(ItemFluidCanister.getFullCanister(ModForgeFluids.petroil, 9), new Object[] { "RRR", "RLR", "RRR", 'R', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.reclaimed)), 'L', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.lubricant)) });

		addRecipeAuto(new ItemStack(ModItems.record_lc, 1), new Object[] { " S ", "SDS", " S ", 'S', POLYMER.ingot(), 'D', LAPIS.dust() });
		addRecipeAuto(new ItemStack(ModItems.record_ss, 1), new Object[] { " S ", "SDS", " S ", 'S', POLYMER.ingot(), 'D', ALLOY.dust() });
		addRecipeAuto(new ItemStack(ModItems.record_vc, 1), new Object[] { " S ", "SDS", " S ", 'S', POLYMER.ingot(), 'D', CMB.dust() });

		addShapelessAuto(new ItemStack(Powders.powder_red_copper, 2), new Object[] { REDSTONE.dust(), CU.dust() });
		addShapelessAuto(new ItemStack(Powders.powder_steel, 2), new Object[] { IRON.dust(), COAL.dust() });

		addRecipeAuto(new ItemStack(ModItems.saw, 1), new Object[] { "IIL", "PP ", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'L', Items.LEATHER });
		addRecipeAuto(new ItemStack(ModItems.bat, 1), new Object[] { "P", "P", "S", 'S', STEEL.plate(), 'P', KEY_PLANKS });
		addShapelessAuto(new ItemStack(ModItems.bat_nail, 1), new Object[] { ModItems.bat, STEEL.plate() });
		addRecipeAuto(new ItemStack(ModItems.golf_club, 1), new Object[] { "IP", " P", " P", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.pipe_rusty, 1), new Object[] { "II", " I", " I", 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.pipe_lead, 1), new Object[] { "II", " I", " I", 'I', PB.ingot() });

		addRecipeAuto(new ItemStack(Foods.bottle_opener, 1), new Object[] { "S", "P", 'S', STEEL.plate(), 'P', KEY_PLANKS });

		addRecipeAuto(new ItemStack(ModItems.polaroid, 1), new Object[] { " C ", "RPY", " B ", 'B', LAPIS.dust(), 'C', COAL.dust(), 'R', ALLOY.dust(), 'Y', GOLD.dust(), 'P', Items.PAPER });

		addRecipeAuto(new ItemStack(ModItems.ullapool_caber, 1), new Object[] { "ITI", " S ", " S ", 'I', IRON.plate(), 'T', Blocks.TNT, 'S', Items.STICK });
		addShapelessAuto(new ItemStack(Foods.chocolate_milk, 1), new Object[] { KEY_ANYPANE, new ItemStack(Items.DYE, 1, 3), Items.MILK_BUCKET, KNO.block(), S.dust(), S.dust(), S.dust(),P_RED.dust() });

		addShapelessAuto(new ItemStack(ModItems.crystal_horn, 1), new Object[] { NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CS.dust(), ModBlocks.block_meteor, ModBlocks.gravel_obsidian, Items.WATER_BUCKET });
		addShapelessAuto(new ItemStack(ModItems.crystal_charred, 1), new Object[] { SR.dust(), CO.dust(), BR.dust(), NB.dust(), TS.dust(), CE.dust(), ModBlocks.block_meteor, AL.block(), Items.WATER_BUCKET });
		addRecipeAuto(new ItemStack(ModBlocks.crystal_virus, 1), new Object[] { "STS", "THT", "STS", 'S', ModItems.particle_strange, 'T', W.dust(), 'H', ModItems.crystal_horn });
		addRecipeAuto(new ItemStack(ModBlocks.crystal_pulsar, 32), new Object[] { "STS", "THT", "STS", 'S', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.uf6)), 'T', AL.dust(), 'H', ModItems.crystal_charred });

		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_mk2, 8), new Object[] { "SAS", "   ", "SAS", 'S', STEEL.plate(), 'A', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_solid, 8), new Object[] { "SAS", "ADA", "SAS", 'S', STEEL.ingot(), 'A', AL.plate(), 'D', ModItems.ducttape });
		addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_solid_sealed, 1), new Object[] { ModBlocks.fluid_duct_solid, ModBlocks.brick_compound });
		addRecipeAuto(new ItemStack(ModBlocks.machine_assembler, 1), new Object[] { "WWW", "MCM", "ISI", 'W', KEY_ANYPANE, 'M', ModItems.motor, 'C', ModItems.circuit_aluminium, 'I', CU.block(), 'S', STEEL.block() });
		addRecipeAuto(new ItemStack(ModItems.template_folder, 1), new Object[] { "LPL", "BPB", "LPL", 'P', Items.PAPER, 'L', "dyeBlue", 'B', "dyeWhite" });
		addRecipeAuto(new ItemStack(Armory.turret_control, 1), new Object[] { "R12", "PPI", "  I", 'R', Items.REDSTONE, '1', ModItems.circuit_aluminium, '2', ModItems.circuit_red_copper, 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.pellet_antimatter, 1), new Object[] { "###", "###", "###", '#', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)) });
		addRecipeAuto(new ItemStack(ModItems.fluid_tank_full, 8), new Object[] { "121", "1 1", "121", '1', AL.plate(), '2', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.fluid_barrel_full, 2), new Object[] { "121", "1 1", "121", '1', STEEL.plate(), '2', AL.plate() });
		addRecipeAuto(new ItemStack(ModItems.inf_water, 1), new Object[] { "222", "131", "222", '1', Items.WATER_BUCKET, '2', AL.plate(), '3', Items.DIAMOND });
		addShapelessAuto(new ItemStack(Powders.powder_desh_mix, 1), new Object[] { B.dustTiny(), B.dustTiny(), AC.dustTiny(), LA.dustTiny(), CE.dustTiny(), CO.dustTiny(), LI.dustTiny(), ND.dustTiny(), NB.dustTiny() });
		addShapelessAuto(new ItemStack(Powders.powder_desh_mix, 9), new Object[] { B.dust(), B.dust(), AC.dust(), LA.dust(), CE.dust(), CO.dust(), LI.dust(), ND.dust(), NB.dust() });
		addShapelessAuto(new ItemStack(Powders.powder_desh_ready, 1), new Object[] { Powders.powder_desh_mix, Nuggies.nugget_mercury, Nuggies.nugget_mercury, COAL.dust() });

		//not so Temporary Crappy Recipes
		addRecipeAuto(new ItemStack(Armory.gun_revolver_pip, 1), new Object[] { " G ", "SSP", " TI", 'G', KEY_ANYPANE, 'S', STEEL.plate(), 'P', Armory.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_nopip, 1), new Object[] { "SSP", " TI", 'S', STEEL.plate(), 'P', Armory.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_silver, 1), new Object[] { "SSP", " TI", 'S', AL.plate(), 'P', Armory.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', KEY_PLANKS });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_blackjack, 1), new Object[] { "SSP", " TI", 'S', STEEL.plate(), 'P', Armory.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', KEY_PLANKS });
		addRecipeAuto(new ItemStack(Armory.gun_revolver_red, 1), new Object[] { "R ", " B", 'R', ModItems.key_red, 'B', Armory.gun_revolver_blackjack });
		addRecipeAuto(new ItemStack(ModItems.plate_dineutronium, 4), new Object[] { "PIP", "IDI", "PIP", 'P', Powders.powder_spark_mix, 'I', DNT.ingot(), 'D', DESH.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_desh, 4), new Object[] { "PIP", "IDI", "PIP", 'P', ANY_PLASTIC.dust(), 'I', DESH.ingot(), 'D', DURA.ingot() });
		addRecipeAuto(new ItemStack(ModItems.piston_selenium, 1), new Object[] { "SSS", "STS", " D ", 'S', STEEL.plate(), 'T', W.ingot(), 'D', ModItems.bolt_dura_steel });
		addShapelessAuto(new ItemStack(ModItems.catalyst_clay), new Object[] { IRON.dust(), Items.CLAY_BALL });
		addRecipeAuto(new ItemStack(ModItems.ams_core_sing, 1), new Object[] { "EAE", "ASA", "EAE", 'E', ModItems.plate_euphemium, 'A', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.aschrab)), 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.ams_core_wormhole, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.plate_dineutronium, 'P', Powders.powder_spark_mix, 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.ams_core_eyeofharmony, 1), new Object[] { "ALA", "LSL", "ALA", 'A', ModItems.plate_dalekanium, 'L', new IngredientContainsTag(ItemFluidTank.getFullBarrel(FluidRegistry.LAVA)), 'S', ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.ams_core_thingy), new Object[] { "GGG", "N N", " S ", 'N', GOLD.nugget(), 'G', GOLD.ingot(), 'S', Batteries.battery_spark_cell_10000 });
		addRecipeAuto(new ItemStack(ModItems.photo_panel), new Object[] { " G ", "IPI", " C ", 'G', KEY_ANYPANE, 'I', ModItems.plate_polymer, 'P', NETHERQUARTZ.dust(), 'C', ModItems.circuit_aluminium });
		addRecipeAuto(new ItemStack(ModBlocks.machine_satlinker), new Object[] { "PSP", "SCS", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'C', ModItems.sat_chip });
		addRecipeAuto(new ItemStack(ModBlocks.machine_telelinker), new Object[] { "PSP", "SCS", "PSP", 'P', STEEL.plate(), 'S', ALLOY.ingot(), 'C', Armory.turret_biometry });
		addRecipeAuto(new ItemStack(ModBlocks.machine_keyforge), new Object[] { "PCP", "WSW", "WSW", 'P', STEEL.plate(), 'S', W.ingot(), 'C', ModItems.padlock, 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModItems.sat_chip), new Object[] { "WWW", "CIC", "WWW", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'I', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.sat_mapper), new Object[] { "H", "B", 'H', ModItems.sat_head_mapper, 'B', ModItems.sat_base });
		addRecipeAuto(new ItemStack(ModItems.sat_scanner), new Object[] { "H", "B", 'H', ModItems.sat_head_scanner, 'B', ModItems.sat_base });
		addRecipeAuto(new ItemStack(ModItems.sat_radar), new Object[] { "H", "B", 'H', ModItems.sat_head_radar, 'B', ModItems.sat_base });
		addRecipeAuto(new ItemStack(ModItems.sat_laser), new Object[] { "H", "B", 'H', ModItems.sat_head_laser, 'B', ModItems.sat_base });
		addRecipeAuto(new ItemStack(ModItems.sat_resonator), new Object[] { "H", "B", 'H', ModItems.sat_head_resonator, 'B', ModItems.sat_base });
		addShapelessAuto(new ItemStack(ModItems.sat_mapper), new Object[] { ModBlocks.sat_mapper });
		addShapelessAuto(new ItemStack(ModItems.sat_scanner), new Object[] { ModBlocks.sat_scanner });
		addShapelessAuto(new ItemStack(ModItems.sat_radar), new Object[] { ModBlocks.sat_radar });
		addShapelessAuto(new ItemStack(ModItems.sat_laser), new Object[] { ModBlocks.sat_laser });
		addShapelessAuto(new ItemStack(ModItems.sat_resonator), new Object[] { ModBlocks.sat_resonator });
		addShapelessAuto(new ItemStack(ModItems.sat_foeq), new Object[] { ModBlocks.sat_foeq });
		addShapelessAuto(new ItemStack(ModItems.geiger_counter), new Object[] { ModBlocks.geiger });
		addShapelessAuto(new ItemStack(ModBlocks.sat_mapper), new Object[] { ModItems.sat_mapper });
		addShapelessAuto(new ItemStack(ModBlocks.sat_scanner), new Object[] { ModItems.sat_scanner });
		addShapelessAuto(new ItemStack(ModBlocks.sat_radar), new Object[] { ModItems.sat_radar });
		addShapelessAuto(new ItemStack(ModBlocks.sat_laser), new Object[] { ModItems.sat_laser });
		addShapelessAuto(new ItemStack(ModBlocks.sat_resonator), new Object[] { ModItems.sat_resonator });
		addShapelessAuto(new ItemStack(ModBlocks.sat_foeq), new Object[] { ModItems.sat_foeq });
		addShapelessAuto(new ItemStack(ModBlocks.geiger), new Object[] { ModItems.geiger_counter });
		addRecipeAuto(new ItemStack(ModItems.sat_interface), new Object[] { "ISI", "PCP", "PAP", 'I', STEEL.ingot(), 'S', STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_gold });
		addRecipeAuto(new ItemStack(ModItems.sat_coord), new Object[] { "SII", "SCA", "SPP", 'I', STEEL.ingot(), 'S', STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_red_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { "MDM", "LCL", "LWL", 'M', MAGTUNG.ingot(), 'D',ModItems.plate_desh, 'L', PB.plate(), 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten });
		addRecipeAuto(new ItemStack(ModBlocks.machine_spp_top), new Object[] { "LWL", "LCL", "MDM", 'M', MAGTUNG.ingot(), 'D',ModItems.plate_desh, 'L', PB.plate(), 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten });
		addShapelessAuto(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { ModBlocks.machine_spp_top });
		addShapelessAuto(new ItemStack(ModBlocks.machine_spp_top), new Object[] { ModBlocks.machine_spp_bottom });
		addRecipeAuto(new ItemStack(Armory.gun_b93), new Object[] { "PCE", "SEB", "PCE", 'P', ModItems.plate_dineutronium, 'C', Armory.weaponized_starblaster_cell, 'E', ModItems.component_emitter, 'B', Armory.gun_b92, 'S', ModItems.singularity_spark });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer), new Object[] { "SDS", "MCM", "MCM", 'S', IRON.ingot(), 'D', MINGRADE.ingot(), 'M', ModItems.coil_advanced_alloy, 'C', ModItems.circuit_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_20), new Object[] { "SDS", "MCM", "MCM", 'S', IRON.ingot(), 'D', MINGRADE.ingot(), 'M', ModItems.coil_copper, 'C', ModItems.circuit_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt), new Object[] { "SDS", "MCM", "MCM", 'S', STAR.ingot(), 'D', DESH.ingot(), 'M', ModBlocks.fwatz_conductor, 'C', ModItems.circuit_targeting_tier6 });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt_20), new Object[] { "SDS", "MCM", "MCM", 'S', STAR.ingot(), 'D', DESH.ingot(), 'M', ModBlocks.fusion_conductor, 'C', ModItems.circuit_targeting_tier6 });
		addShapelessAuto(new ItemStack(Foods.bottle_sparkle), new Object[] { Foods.bottle_nuka, Items.CARROT, GOLD.nugget() });
		addShapelessAuto(new ItemStack(Foods.bottle_rad), new Object[] { Foods.bottle_quantum, Items.CARROT, GOLD.nugget(), Powders.powder_radspice});
		addRecipeAuto(new ItemStack(Armory.grenade_nuke), new Object[] { "CGC", "CGC", "PAP", 'C', ModBlocks.det_charge, 'G', Armory.grenade_mk2, 'P', ALLOY.plate(), 'A', Blocks.ANVIL });
		addRecipeAuto(new ItemStack(ModBlocks.radiobox), new Object[] { "PLP", "PSP", "PLP", 'P', STEEL.plate(), 'S', ModItems.ring_starmetal, 'L', OreDictManager.getReflector() });
		addRecipeAuto(new ItemStack(ModBlocks.radiorec), new Object[] { "  W", "PCP", "PIP", 'W', ModItems.wire_copper, 'P', STEEL.plate(), 'C', ModItems.circuit_red_copper, 'I', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.jackt), new Object[] { "S S", "LIL", "LIL", 'S', STEEL.plate(), 'L', Items.LEATHER, 'I', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.jackt2), new Object[] { "S S", "LIL", "III", 'S', STEEL.plate(), 'L', Items.LEATHER, 'I', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(Armory.grenade_cloud), new Object[] { "SPS", "CAC", "SPS", 'S', S.dust(), 'P', Powders.powder_poison, 'C', CU.dust(), 'A', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.acid)) });
		addRecipeAuto(new ItemStack(Armory.grenade_pink_cloud), new Object[] { " S ", "ECE", " E ", 'S', Powders.powder_spark_mix, 'E', Powders.powder_magic, 'C', Armory.grenade_cloud });
		addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.IRON_BARS, 'C', ModItems.pellet_gas, 'D', Blocks.DISPENSER });
		addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine_seal), new Object[] { "ISI", "SCS", "ISI", 'I', BIGMT.ingot(), 'S', STAR.ingot(), 'C', ModItems.chlorine_pinwheel });
		addRecipeAuto(new ItemStack(ModBlocks.vent_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.IRON_BARS, 'C', Armory.grenade_cloud, 'D', Blocks.DISPENSER });
		addRecipeAuto(new ItemStack(ModBlocks.vent_pink_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.IRON_BARS, 'C', Armory.grenade_pink_cloud, 'D', Blocks.DISPENSER });
		addRecipeAuto(new ItemStack(ModBlocks.spikes, 4), new Object[] { "FFF", "BBB", "TTT", 'F', Items.FLINT, 'B', ModItems.bolt_tungsten, 'T', W.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.mine_ap, 4), new Object[] { "C", "P", "T", 'C', ModItems.circuit_targeting_tier2, 'P', IRON.plate(), 'T', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.mine_he, 1), new Object[] { " C ", "PTP", 'C', ModItems.circuit_targeting_tier2, 'P', STEEL.plate(), 'T', ANY_HIGHEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.mine_shrap, 2), new Object[] { "LLL", " C ", "PTP", 'C', ModItems.circuit_targeting_tier2, 'P', STEEL.plate(), 'T', ModBlocks.det_cord, 'L', ModItems.pellet_buckshot });
		addRecipeAuto(new ItemStack(ModBlocks.mine_fat, 1), new Object[] { "CDN", 'C', ModItems.circuit_targeting_tier2, 'D', ModItems.ducttape, 'N', Armory.ammo_nuke });
		addRecipeAuto(new ItemStack(ModItems.defuser, 1), new Object[] { " PS", "P P", " P ", 'P', ANY_PLASTIC.ingot(), 'S', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModItems.defuser_desh), new Object[] { " SD", "S S", " S ", 'D', DESH.ingot(), 'S', POLYMER.ingot() });
		addShapelessAuto(new ItemStack(ModItems.syringe_taint), new Object[] { Foods.bottle2_empty, ModItems.syringe_metal_empty, ModItems.ducttape, Powders.powder_magic, SA326.nugget(), Items.POTIONITEM });
		addShapelessAuto(new ItemStack(Foods.loops), new Object[] { ModItems.flame_pony, Items.WHEAT, Items.SUGAR });
		addShapelessAuto(new ItemStack(Foods.loop_stew), new Object[] { Foods.loops, Foods.can_smart, Items.BOWL });
		addRecipeAuto(new ItemStack(Armory.gun_calamity, 1), new Object[] { " PI", "BBM", " PI", 'P', IRON.plate(), 'B', ModItems.pipes_steel, 'M', Armory.mechanism_rifle_1, 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Armory.gun_calamity_dual, 1), new Object[] { "BBM", " PI", "BBM", 'P', IRON.plate(), 'B', ModItems.pipes_steel, 'M', Armory.mechanism_rifle_1, 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.custom_fall, 1), new Object[] { "IIP", "CHW", "IIP", 'I', ANY_RUBBER.ingot(), 'P', BIGMT.plate(), 'C', ModItems.circuit_red_copper, 'H', ModItems.hull_small_steel, 'W', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_controller, 1), new Object[] { "PGP", "ICI", "PRP", 'P',ModItems.plate_desh, 'G', KEY_ANYPANE, 'I', ANY_PLASTIC.ingot(), 'R', REDSTONE.block(), 'C', ModItems.circuit_targeting_tier4 });
		addRecipeAuto(new ItemStack(Armory.gun_minigun, 1), new Object[] { "PIB", "PCM", "PIB", 'P', ModItems.pipes_steel, 'B', STEEL.block(), 'I', ANY_PLASTIC.ingot(), 'C', Armory.mechanism_rifle_2, 'M', ModItems.motor });
		addRecipeAuto(new ItemStack(Armory.gun_avenger, 1), new Object[] { "PIB", "PCM", "PIB", 'P', ModItems.pipes_steel, 'B', BE.block(), 'I', DESH.ingot(), 'C', Armory.mechanism_rifle_2, 'M', ModItems.motor });
		addRecipeAuto(new ItemStack(Armory.gun_lacunae, 1), new Object[] { "TIT", "ILI", "PRP", 'T', ModItems.syringe_taint, 'I', STAR.ingot(), 'L', Armory.gun_minigun, 'P', ModItems.pellet_rtg, 'R', ModBlocks.machine_rtg_grey });
		addRecipeAuto(new ItemStack(ModItems.containment_box, 1), new Object[] { "LLL", "LCL", "LLL", 'L', PB.plate(), 'C', Blocks.CHEST });

		addRecipeAuto(new ItemStack(ModBlocks.absorber, 1), new Object[] { "ICI", "CPC", "ICI", 'I', CU.ingot(), 'C', COAL.dust(), 'P', PB.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_red, 1), new Object[] { "ICI", "CPC", "ICI", 'I', TI.ingot(), 'C', COAL.dust(), 'P', ModBlocks.absorber });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_green, 1), new Object[] { "ICI", "CPC", "ICI", 'I', ANY_PLASTIC.ingot(), 'C', Powders.powder_desh_mix, 'P', ModBlocks.absorber_red });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_pink, 1), new Object[] { "ICI", "CPC", "ICI", 'I', BIGMT.ingot(), 'C', Powders.powder_nitan_mix, 'P', ModBlocks.absorber_green });
		addRecipeAuto(new ItemStack(ModBlocks.decon, 1), new Object[] { "BGB", "SAS", "BSB", 'B', BE.ingot(), 'G', Blocks.IRON_BARS, 'S', STEEL.ingot(), 'A', ModBlocks.absorber });
		addRecipeAuto(new ItemStack(ModBlocks.decon_digamma, 1), new Object[] { "BGB", "SAS", "BTB", 'S', Billets.billet_flashlead, 'G', ModItems.fmn, 'B',ModItems.plate_desh, 'A', ModBlocks.decon , 'T', ModItems.xanax});
		addRecipeAuto(new ItemStack(ModBlocks.radsensor, 1), new Object[] { "IGI", "LCL", "IRI", 'I', CE.ingot(), 'L', PB.plate(), 'G', ModItems.geiger_counter , 'C', Items.COMPARATOR, 'R', Items.REDSTONE});
		addRecipeAuto(new ItemStack(ModBlocks.machine_geo, 1), new Object[] { "ITI", "PCP", "ITI", 'I', DURA.ingot(), 'T', ModItems.thermo_element, 'P', ModItems.board_copper, 'C', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.machine_amgen, 1), new Object[] { "ITI", "TAT", "ITI", 'I', ALLOY.ingot(), 'T', ModItems.thermo_element, 'A', ModBlocks.absorber });
		addRecipeAuto(new ItemStack(ModBlocks.machine_minirtg, 1), new Object[] { "LLL", "PPP", "TRT", 'L', PB.plate(), 'P', PU238.billet(), 'T', ModItems.thermo_element, 'R', ModItems.rtg_unit });
		addRecipeAuto(new ItemStack(ModBlocks.machine_powerrtg, 1), new Object[] { "SRS", "PTP", "SRS", 'S', STAR.ingot(), 'R', ModItems.rtg_unit, 'P', PO210.ingot(), 'T', TS.dust() });

		addShapelessAuto(new ItemStack(ModItems.pellet_rtg), new Object[] { PU238.billet(), PU238.billet(), PU238.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_radium), new Object[] { RA226.billet(), RA226.billet(), RA226.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_weak), new Object[] { U238.billet(), U238.billet(), PU238.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_strontium), new Object[] { SR90.billet(), SR90.billet(), SR90.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_cobalt), new Object[] { CO60.billet(), CO60.billet(), CO60.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_actinium), new Object[] { AC227.billet(), AC227.billet(), AC227.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_polonium), new Object[] { PO210.billet(), PO210.billet(), PO210.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_lead), new Object[] { PB209.billet(), PB209.billet(), PB209.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_gold), new Object[] { AU198.billet(), AU198.billet(), AU198.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_americium), new Object[] { AM241.billet(), AM241.billet(), AM241.billet(), IRON.plate() });
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_balefire), new Object[] { ModItems.egg_balefire, ModItems.egg_balefire, ModItems.egg_balefire, IRON.plate() });

		addShapelessAuto(new ItemStack(Billets.billet_bismuth, 3), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted_bismuth) });
		addShapelessAuto(new ItemStack(Ingots.ingot_lead, 2), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted_lead) });
		addShapelessAuto(new ItemStack(Nuggies.nugget_mercury, 12), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted_mercury) });
		addShapelessAuto(new ItemStack(Billets.billet_neptunium, 3), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted_neptunium) });
		addShapelessAuto(new ItemStack(Billets.billet_zirconium, 3), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted_zirconium) });

		addRecipeAuto(new ItemStack(ModBlocks.pink_planks, 4), new Object[] { "W", 'W', ModBlocks.pink_log });
		addRecipeAuto(new ItemStack(BlockItems.door_fuckoff, 3), new Object[] { "WW","WW","WW", 'W', ModBlocks.pink_planks });
		addRecipeAuto(new ItemStack(Inserts.decontamination_module, 1), new Object[] { "GAG", "WTW", "GAG", 'W', AC.ingot(), 'T', ModBlocks.decon, 'G', RA226.nugget(), 'A', TCALLOY.ingot() });

		addRecipeAuto(new ItemStack(BlockItems.door_metal, 3), new Object[] { "II", "SS", "II", 'I', IRON.plate(), 'S', STEEL.plate() });
		addRecipeAuto(new ItemStack(BlockItems.door_office, 3), new Object[] { "II", "SS", "II", 'I', KEY_PLANKS, 'S', IRON.plate() });
		addRecipeAuto(new ItemStack(BlockItems.door_bunker, 3), new Object[] { "II", "SS", "II", 'I', STEEL.plate(), 'S', PB.plate() });

		addShapelessAuto(new ItemStack(Items.PAPER, 1), new Object[] { new ItemStack(ModItems.assembly_template, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Items.PAPER, 1), new Object[] { new ItemStack(ModItems.chemistry_template, 1, OreDictionary.WILDCARD_VALUE) });

		for(Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			Fluid fluid = entry.getValue();
			addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid), new Object[] { new ItemStack(ModBlocks.fluid_duct_mk2, 1), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });

			addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid, 8), new Object[] { new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });

			addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid), new Object[] { new ItemStack(ModBlocks.fluid_duct_mk2, 1), new IngredientContainsTag(ItemFuzzyIdentifier.getStackFromFluid(entry.getValue())) });

			addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid, 8), new Object[] { new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new IngredientContainsTag(ItemFuzzyIdentifier.getStackFromFluid(entry.getValue())) });

			// fuck off addShapelessAuto(ItemFuzzyIdentifier.getStackFromFluid(fluid), new Object[] { new ItemStack(ModItems.fuzzy_identifier, 1, OreDictionary.WILDCARD_VALUE), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });
		// No more old pipe crafting
		// 	addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid), new Object[] { new ItemStack(ModItems.ff_fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });
		// 	addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid, 8), new Object[] { new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });
		}

		addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_mk2, 1), new Object[] { new ItemStack(ModItems.ff_fluid_duct, 1, OreDictionary.WILDCARD_VALUE) });

		addShapelessAuto(new ItemStack(Powders.redstone_depleted, 1), new Object[] { new ItemStack(Batteries.battery_su, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Powders.redstone_depleted, 2), new Object[] { new ItemStack(Batteries.battery_su_l, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Items.REDSTONE, 1), new Object[] { Powders.redstone_depleted, Powders.redstone_depleted });

		addShapelessAuto(new ItemStack(ModItems.bobmazon_materials), new Object[] { Items.BOOK, GOLD.nugget(), Items.STRING });
		addShapelessAuto(new ItemStack(ModItems.bobmazon_machines), new Object[] { Items.BOOK, GOLD.nugget(), new ItemStack(Items.DYE, 1, 1) });
		addShapelessAuto(new ItemStack(ModItems.bobmazon_weapons), new Object[] { Items.BOOK, GOLD.nugget(), new ItemStack(Items.DYE, 1, 8) });
		addShapelessAuto(new ItemStack(ModItems.bobmazon_tools), new Object[] { Items.BOOK, GOLD.nugget(), new ItemStack(Items.DYE, 1, 2) });

		addRecipeAuto(new ItemStack(Blocks.TORCH, 3), new Object[] { "L", "S", 'L', ModItems.lignite, 'S', Items.STICK });
		addRecipeAuto(new ItemStack(Blocks.TORCH, 6), new Object[] { "L", "S", 'L', ModItems.briquette_lignite, 'S', Items.STICK });
		addRecipeAuto(new ItemStack(Blocks.TORCH, 8), new Object[] { "L", "S", 'L', ANY_COKE.gem(), 'S', Items.STICK });

		addRecipeAuto(new ItemStack(ModBlocks.machine_missile_assembly, 1), new Object[] { "PWP", "SSS", "CCC", 'P', ModItems.pedestal_steel, 'W', ModItems.wrench, 'S', STEEL.plate(), 'C', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 1), new Object[] { "PPP", "SDS", "CCC", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', ModItems.pipes_steel, 'C', ModBlocks.concrete_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 1), new Object[] { "PPP", "SDS", "CCC", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', ModItems.pipes_steel, 'C', ModBlocks.concrete });
		addRecipeAuto(new ItemStack(ModBlocks.struct_scaffold, 1), new Object[] { "SSS", "DCD", "SSS", 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.fluid_duct_mk2, 'C', ModBlocks.red_cable });

		addRecipeAuto(new ItemStack(MissileParts.mp_stability_10_flat, 1), new Object[] { "PSP", "P P", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(MissileParts.mp_stability_10_cruise, 1), new Object[] { "ASA", " S ", "PSP", 'A', TI.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(MissileParts.mp_stability_10_space, 1), new Object[] { "ASA", "PSP", 'A', AL.plate(), 'P', STEEL.ingot(), 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(MissileParts.mp_stability_15_flat, 1), new Object[] { "ASA", "PSP", 'A', AL.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(MissileParts.mp_stability_15_thin, 1), new Object[] { "A A", "PSP", "PSP", 'A', AL.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold });

		addRecipeAuto(new ItemStack(MissileParts.mp_thruster_15_balefire_large_rad, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.board_copper, 'T', MissileParts.mp_thruster_15_balefire_large });
		
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_10_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', MissileParts.mp_fuselage_10_kerosene });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_10_long_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', MissileParts.mp_fuselage_10_long_kerosene });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_15_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', MissileParts.mp_fuselage_15_kerosene });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_10_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', MissileParts.mp_fuselage_10_solid });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_10_long_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', MissileParts.mp_fuselage_10_long_solid });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_15_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', MissileParts.mp_fuselage_15_solid });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_15_solid_desh, 1), new Object[] { "CCC", "CTC", "CCC", 'C', DESH.ingot(), 'T', MissileParts.mp_fuselage_15_solid });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_10_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', MissileParts.mp_fuselage_10_kerosene });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_10_long_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', MissileParts.mp_fuselage_10_long_kerosene });
		addRecipeAuto(new ItemStack(MissileParts.mp_fuselage_15_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', MissileParts.mp_fuselage_15_kerosene });

		addRecipeAuto(new ItemStack(MissileParts.mp_warhead_15_boxcar, 1), new Object[] { "SNS", "CBC", "SFS", 'S', STAR.ingot(), 'N', ModBlocks.det_nuke, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.boxcar, 'F', ModItems.tritium_deuterium_cake });

		addRecipeAuto(new ItemStack(ModItems.mp_chip_1, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier1, 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(ModItems.mp_chip_2, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier2, 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(ModItems.mp_chip_3, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier3, 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(ModItems.mp_chip_4, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier4, 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(ModItems.mp_chip_5, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier5, 'S', ModBlocks.steel_scaffold });

		addRecipeAuto(new ItemStack(ModItems.seg_10, 1), new Object[] { "P", "S", "B", 'P', AL.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModItems.seg_15, 1), new Object[] { "PP", "SS", "BB", 'P', TI.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModItems.seg_20, 1), new Object[] { "PGP", "SSS", "BBB", 'P', STEEL.plate(), 'G', GOLD.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });

		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core, 1), new Object[] { "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.IRON_BARS, 'C', ModItems.circuit_targeting_tier3, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core_large, 1), new Object[] { "SIS", "ICI", "BEB", 'S', ModItems.circuit_red_copper, 'I', Blocks.IRON_BARS, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
		addRecipeAuto(new ItemStack(ModBlocks.struct_soyuz_core, 1), new Object[] { "CUC", "TST", "TBT", 'C', ModItems.circuit_targeting_tier4, 'U', Upgrades.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery });

		//addRecipeAuto(new ItemStack(ModBlocks.obj_tester, 1), new Object[] { "P", "I", "S", 'P', ModItems.polaroid, 'I', ModItems.flame_pony, 'S', STEEL.plate() });

		addRecipeAuto(new ItemStack(ModBlocks.fence_metal, 6), new Object[] { "BIB", "BIB", 'B', Blocks.IRON_BARS, 'I', Items.IRON_INGOT });

		addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite), new Object[] { new ItemStack(Blocks.SAND, 1, 0), ModItems.trinitite });
		addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite_red), new Object[] { new ItemStack(Blocks.SAND, 1, 1), ModItems.trinitite });
		addShapelessAuto(new ItemStack(ModBlocks.sand_uranium), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", U.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_polonium), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", PO210.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_boron, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", B.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_lead, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", PB.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_quartz, 1), new Object[] { "sand", "sand", NETHERQUARTZ.dust(), NETHERQUARTZ.dust() });

		addRecipeAuto(new ItemStack(ModItems.rune_blank, 1), new Object[] { "PSP", "SDS", "PSP", 'P', Powders.powder_magic, 'S', STAR.ingot(), 'D', KEY_CIRCUIT_BISMUTH });
		addShapelessAuto(new ItemStack(ModItems.rune_isa, 1), new Object[] { ModItems.rune_blank, Powders.powder_spark_mix, ModItems.singularity_counter_resonant });
		addShapelessAuto(new ItemStack(ModItems.rune_dagaz, 1), new Object[] { ModItems.rune_blank, Powders.powder_spark_mix, ModItems.black_hole });
		addShapelessAuto(new ItemStack(ModItems.rune_hagalaz, 1), new Object[] { ModItems.rune_blank, Powders.powder_spark_mix, ModItems.singularity_super_heated });
		addShapelessAuto(new ItemStack(ModItems.rune_jera, 1), new Object[] { ModItems.rune_blank, Powders.powder_spark_mix, ModItems.singularity_spark });
		addShapelessAuto(new ItemStack(ModItems.rune_thurisaz, 1), new Object[] { ModItems.rune_blank, Powders.powder_spark_mix, ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.ams_focus_blank, 1), new Object[] { "PAP", "GBG", "PCP", 'P', ModItems.plate_dineutronium, 'G', ModBlocks.reinforced_glass, 'A', ModItems.rune_thurisaz, 'B', ModItems.hull_big_aluminium, 'C', ModItems.rune_jera });
		addRecipeAuto(new ItemStack(ModItems.ams_lens, 1), new Object[] { "PFP", "GEG", "PFP", 'P', ModItems.rune_dagaz, 'G', ModItems.ams_focus_blank, 'E', Upgrades.upgrade_overdrive_3, 'F', ModItems.fusion_shield_tungsten });
		addRecipeAuto(new ItemStack(ModItems.ams_focus_omega, 1), new Object[] { "PFP", "REG", "PFP", 'P', ModBlocks.dfc_stabilizer, 'R', ModItems.ams_focus_limiter, 'G', ModItems.ams_focus_booster, 'E', ModItems.laser_crystal_digamma, 'F', ModItems.fusion_shield_vaporwave });
		addRecipeAuto(new ItemStack(ModItems.ams_focus_booster, 1), new Object[] { "PFP", "GEG", "PFP", 'P', ModItems.rune_hagalaz, 'G', ModItems.ams_lens, 'E', Upgrades.upgrade_screm, 'F', ModItems.fusion_shield_desh });
		addRecipeAuto(new ItemStack(ModItems.ams_focus_limiter, 1), new Object[] { "PFP", "GEG", "PFP", 'P', ModItems.rune_isa, 'G', ModItems.ams_focus_blank, 'E', Upgrades.upgrade_power_3, 'F', ModItems.inf_water_mk4 });
		addRecipeAuto(new ItemStack(ModItems.ams_catalyst_blank, 1), new Object[] { "TET", "ETE", "TET", 'T', TS.dust(), 'E', EUPH.ingot() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_iron, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, IRON.dust(), IRON.dust(), IRON.dust(), IRON.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_copper, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_isa, CU.dust(), CU.dust(), CU.dust(), CU.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_aluminium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_isa, ModItems.rune_isa, AL.dust(), AL.dust(), AL.dust(), AL.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_lithium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_thurisaz, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_isa, LI.dust(), LI.dust(), LI.dust(), LI.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_beryllium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_dagaz, BE.dust(), BE.dust(), BE.dust(), BE.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_tungsten, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_dagaz, W.dust(), W.dust(), W.dust(), W.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cobalt, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_dagaz, CO.dust(), CO.dust(), CO.dust(), CO.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_niobium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_hagalaz, NB.dust(), NB.dust(), NB.dust(), NB.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cerium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, CE.dust(), CE.dust(), CE.dust(), CE.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_thorium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, TH232.dust(), TH232.dust(), TH232.dust(), TH232.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_strontium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, SR.dust(), SR.dust(), SR.dust(), SR.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_caesium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, CS.dust(), CS.dust(), CS.dust(), CS.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_schrabidium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, SA326.dust(), SA326.dust(), SA326.dust(), SA326.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_euphemium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, ModItems.rune_jera, EUPH.dust(), EUPH.dust(), EUPH.dust(), EUPH.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_dineutronium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_jera, ModItems.rune_jera, ModItems.rune_jera, ModItems.rune_jera, DNT.dust(), DNT.dust(), DNT.dust(), DNT.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_core, 1), new Object[] { "DLD", "LML", "DLD", 'D', Ingots.ingot_bismuth, 'L', DNT.block(), 'M', KEY_CIRCUIT_BISMUTH });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_emitter, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.ingot(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModItems.crystal_xen, 'L', ModItems.sat_head_laser });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_receiver, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.ingot(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', new ItemStack(ModBlocks.sellafield_core), 'L', ModItems.hull_small_steel });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_injector, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.ingot(), 'D', CMB.plate(), 'T', ModBlocks.machine_fluidtank, 'X', ModItems.motor, 'L', ModItems.pipes_steel });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_stabilizer, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.ingot(), 'D', ModItems.plate_desh, 'T', ModItems.singularity_spark, 'X', ModItems.magnet_circular, 'L', ModItems.crystal_xen });
		addRecipeAuto(new ItemStack(ModBlocks.spk_cable, 16), new Object[] { " W ", "RRR", " W ", 'W', ModItems.plate_dineutronium, 'R', ModItems.wire_magnetized_tungsten });

		addRecipeAuto(new ItemStack(ModBlocks.barrel_plastic, 1), new Object[] { "IPI", "I I", "IPI", 'I', ModItems.plate_polymer, 'P', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { "IPI", "I I", "IPI", 'I', IRON.plate(), 'P', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_steel, 1), new Object[] { "IPI", "IOI", "IPI", 'I', STEEL.plate(), 'P', STEEL.ingot(), 'O', ModItems.oil_tar });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_antimatter, 1), new Object[] { "IPI", "IPI", "IPI", 'I', BIGMT.plate(), 'P', ModItems.coil_advanced_torus });
		addRecipeAuto(new ItemStack(ModBlocks.tesla, 1), new Object[] { "CCC", "PIP", "WTW", 'C', ModItems.coil_copper, 'I', IRON.ingot(), 'P', ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer, 'W', KEY_PLANKS });

		addRecipeAuto(new ItemStack(ModItems.bottle_mercury, 1), new Object[] { "###", "#B#", "###", '#', Nuggies.nugget_mercury, 'B', Items.GLASS_BOTTLE });
		addRecipeAuto(new ItemStack(Nuggies.nugget_mercury, 8), new Object[] { "#", '#', ModItems.bottle_mercury });
		addRecipeAuto(new ItemStack(ModItems.egg_balefire, 1), new Object[] { "###", "###", "###", '#', ModItems.egg_balefire_shard });
		addRecipeAuto(new ItemStack(ModItems.egg_balefire_shard, 9), new Object[] { "#", '#', ModItems.egg_balefire });
		addRecipeAuto(new ItemStack(Armory.ammo_44_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_44, 'A', P_WHITE.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_50bmg_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50bmg, 'A', P_WHITE.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_phosphorus, 1), new Object[] { "G", "R", 'G', P_WHITE.ingot(), 'R', Armory.ammo_rocket });
		addRecipeAuto(new ItemStack(Armory.ammo_grenade_phosphorus, 2), new Object[] { "GIG", 'G', Armory.ammo_grenade, 'I', P_WHITE.ingot() });

		addRecipeAuto(new ItemStack(ModBlocks.block_u233, 1), new Object[] { "###", "###", "###", '#',U233.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_u235, 1), new Object[] { "###", "###", "###", '#', U235.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_u238, 1), new Object[] { "###", "###", "###", '#', U238.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', Ingots.ingot_uranium_fuel });
		addRecipeAuto(new ItemStack(ModBlocks.block_neptunium, 1), new Object[] { "###", "###", "###", '#', NP237.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_polonium, 1), new Object[] { "###", "###", "###", '#', PO210.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_plutonium, 1), new Object[] { "###", "###", "###", '#', PU.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_pu238, 1), new Object[] { "###", "###", "###", '#', PU238.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_pu239, 1), new Object[] { "###", "###", "###", '#', PU239.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_pu240, 1), new Object[] { "###", "###", "###", '#', PU240.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_plutonium_fuel, 1), new Object[] { "###", "###", "###", '#', Ingots.ingot_plutonium_fuel });
		addRecipeAuto(new ItemStack(ModBlocks.block_thorium_fuel, 1), new Object[] { "###", "###", "###", '#', Ingots.ingot_thorium_fuel });
		addRecipeAuto(new ItemStack(ModBlocks.block_solinium, 1), new Object[] { "###", "###", "###", '#', SA327.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_schrabidium_fuel, 1), new Object[] { "###", "###", "###", '#', Ingots.ingot_schrabidium_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_u233, 9), new Object[] { "#", '#', U233.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_u235, 9), new Object[] { "#", '#', U235.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_u238, 9), new Object[] { "#", '#', U238.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_uranium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_uranium_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_neptunium, 9), new Object[] { "#", '#', NP237.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_polonium, 9), new Object[] { "#", '#', PO210.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_plutonium, 9), new Object[] { "#", '#', PU.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_pu238, 9), new Object[] { "#", '#', PU238.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_pu239, 9), new Object[] { "#", '#', PU239.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_pu240, 9), new Object[] { "#", '#', PU240.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_plutonium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_plutonium_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_thorium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_thorium_fuel });
		addRecipeAuto(new ItemStack(Ingots.ingot_solinium, 9), new Object[] { "#", '#', SA327.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_schrabidium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_schrabidium_fuel });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_50, 'P', Armory.primer_50, 'L', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_50, 'P', Armory.primer_50, 'L', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_50, 'P', Armory.primer_50, 'L', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_explosive, 4), new Object[] { " I ", "GCL", " P ", 'I', Blocks.TNT, 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_50, 'P', Armory.primer_50, 'L', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_explosive, 6), new Object[] { " I ", "GCL", " P ", 'I', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_50, 'P', Armory.primer_50, 'L', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_semtex, 4), new Object[] { " I ", "GCL", " P ", 'I', ModBlocks.det_miner, 'G', ANY_SMOKELESS.dust(), 'C', Armory.casing_50, 'P', Armory.primer_50, 'L', ANY_RUBBER.ingot() });

		addRecipeAuto(new ItemStack(ModBlocks.block_mox_fuel, 1), new Object[] { "###", "###", "###", '#', Ingots.ingot_mox_fuel });
		addRecipeAuto(new ItemStack(ModBlocks.block_lithium, 1), new Object[] { "###", "###", "###", '#', LI.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_white_phosphorus, 1), new Object[] { "###", "###", "###", '#', P_WHITE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_red_phosphorus, 1), new Object[] { "###", "###", "###", '#', P_RED.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.block_insulator, 1), new Object[] { "###", "###", "###", '#', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(ModBlocks.block_asbestos, 1), new Object[] { "###", "###", "###", '#', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_fiberglass, 1), new Object[] { "###", "###", "###", '#', FIBER.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.block_cobalt, 1), new Object[] { "###", "###", "###", '#', CO.ingot() });

		addRecipeAuto(new ItemStack(Ingots.ingot_aluminium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(Ingots.ingot_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_copper });
		addRecipeAuto(new ItemStack(Ingots.ingot_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_tungsten });
		addRecipeAuto(new ItemStack(Ingots.ingot_red_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_red_copper });
		addRecipeAuto(new ItemStack(Ingots.ingot_advanced_alloy, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_advanced_alloy });
		addRecipeAuto(new ItemStack(Items.GOLD_INGOT, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_gold });
		addRecipeAuto(new ItemStack(Ingots.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_schrabidium });
		addRecipeAuto(new ItemStack(Ingots.ingot_magnetized_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_magnetized_tungsten });

		addRecipeAuto(new ItemStack(Ingots.ingot_mox_fuel, 9), new Object[] { "#", '#', ModBlocks.block_mox_fuel });
		addRecipeAuto(new ItemStack(ModItems.lithium, 9), new Object[] { "#", '#', LI.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_phosphorus, 9), new Object[] { "#", '#', P_WHITE.block() });
		addRecipeAuto(new ItemStack(Powders.powder_fire, 9), new Object[] { "#", '#', P_RED.block() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 9), new Object[] { "#", '#', ModBlocks.block_insulator });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 8), new Object[] { "DD", 'D', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 8), new Object[] { "DD", 'D', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', FIBER.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "SWS", 'S', Items.STRING, 'W', Blocks.WOOL });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', "ingotBrick" });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', "ingotNetherBrick" });

		addRecipeAuto(new ItemStack(Ingots.ingot_asbestos, 9), new Object[] { "#", '#', ASBESTOS.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_fiberglass, 9), new Object[] { "#", '#', FIBER.block() });
		addRecipeAuto(new ItemStack(Ingots.ingot_cobalt, 9), new Object[] { "#", '#', CO.block() });
		addRecipeAuto(new ItemStack(Armory.mechanism_special, 1), new Object[] { "PCI", "ISS", "PCI", 'P',ModItems.plate_desh, 'C', ModItems.coil_advanced_alloy, 'I', STAR.ingot(), 'S', ModItems.circuit_targeting_tier3 });
		addRecipeAuto(new ItemStack(Armory.gun_ks23, 1), new Object[] { "PPM", "SWL", 'P', STEEL.plate(), 'M', Armory.mechanism_rifle_1, 'S', Items.STICK, 'W', ModItems.wire_tungsten, 'L', KEY_LOG });
		addShapelessAuto(new ItemStack(Armory.gun_sauer, 1), new Object[] { ModItems.ducttape, Armory.gun_ks23, Blocks.LEVER, Armory.gun_ks23 });
		addRecipeAuto(new ItemStack(Armory.gun_flamer, 1), new Object[] { "WPP", "SCT", "WMI", 'W', ModItems.wire_gold, 'P', ModItems.pipes_steel, 'S', ModItems.hull_small_steel, 'C', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'M', Armory.mechanism_launcher_1, 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_fuel, 1), new Object[] { " P ", "BDB", " P ", 'P', STEEL.plate(), 'B', ModItems.bolt_tungsten, 'D', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)) });
		addRecipeAuto(new ItemStack(Armory.ammo_fuel_napalm, 1), new Object[] { " P ", "BDB", " P ", 'P', STEEL.plate(), 'B', ModItems.bolt_tungsten, 'D', ModItems.canister_napalm });
		addRecipeAuto(new ItemStack(Armory.ammo_fuel_phosphorus, 1), new Object[] { "CPC", "CDC", "CPC", 'C', COAL.dust(), 'P', P_WHITE.ingot(), 'D', Armory.ammo_fuel });
		addRecipeAuto(new ItemStack(Armory.ammo_fuel_gas, 1), new Object[] { "PDP", "BDB", "PDP", 'P', STEEL.plate(), 'B', ModItems.bolt_tungsten, 'D', ModItems.pellet_gas });
		addRecipeAuto(new ItemStack(Armory.ammo_fuel_vaporizer, 1), new Object[] { "PSP", "SNS", "PSP", 'P', P_WHITE.ingot(), 'S', Crystals.crystal_sulfur, 'N', Armory.ammo_fuel_napalm });
		addRecipeAuto(new ItemStack(ModBlocks.det_cord, 8), new Object[] { "TNT", "NGN", "TNT", 'T', STEEL.plate(), 'N', KNO.dust(), 'G', ANY_GUNPOWDER.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.det_charge, 1), new Object[] { "PDP", "DTD", "PDP", 'P', STEEL.plate(), 'D', ModBlocks.det_cord, 'T', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.det_n2, 1), new Object[] { "PDT", "DDD", "PDP", 'P', ModItems.plate_steel, 'D', ModItems.n2_charge, 'T', ModItems.circuit_targeting_tier3 });
		addRecipeAuto(new ItemStack(ModBlocks.det_nuke, 1), new Object[] { "PDP", "DCD", "PDP", 'P', ModItems.plate_desh, 'D', ModItems.man_explosive8, 'C', ModItems.man_core });
		addRecipeAuto(new ItemStack(ModBlocks.det_bale, 1), new Object[] { "DAP", "DCD", "DBD", 'D', ModItems.plate_titanium, 'A', Powders.powder_power, 'B', Powders.powder_magic, 'C', ModItems.egg_balefire, 'P', ModItems.circuit_targeting_tier4 });
		addRecipeAuto(new ItemStack(ModBlocks.det_miner, 3), new Object[] { "FFF", "ITI", "ITI", 'F', Items.FLINT, 'I', IRON.plate(), 'T', Blocks.TNT });
		addRecipeAuto(new ItemStack(ModBlocks.det_miner, 12), new Object[] { "FFF", "ITI", "ITI", 'F', Items.FLINT, 'I', STEEL.plate(), 'T', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.cobalt_helmet, 1), new Object[] { "EEE", "E E", 'E', CO.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.cobalt_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', CO.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.cobalt_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', CO.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.cobalt_boots, 1), new Object[] { "E E", "E E", 'E', CO.ingot() });

		addRecipeAuto(new ItemStack(ArmorSets.t45_helmet, 1), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_titanium, 'C', ModItems.circuit_targeting_tier3, 'I', ANY_RUBBER.ingot(), 'X', ModItems.gas_mask_m65, 'B', ArmorSets.titanium_helmet });
		addRecipeAuto(new ItemStack(ArmorSets.t45_plate, 1), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'T', ModItems.gas_canister, 'B', ArmorSets.titanium_plate });
		addRecipeAuto(new ItemStack(ArmorSets.t45_legs, 1), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'B', ArmorSets.titanium_legs });
		addRecipeAuto(new ItemStack(ArmorSets.t45_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_titanium, 'B', ArmorSets.titanium_boots });
		addRecipeAuto(new ItemStack(ArmorSets.bj_helmet, 1), new Object[] { "SBS", " C ", " I ", 'S', Items.STRING, 'B', new ItemStack(Blocks.WOOL, 1, 15), 'C', ModItems.circuit_targeting_tier4, 'I', STAR.ingot() });
		addRecipeAuto(new ItemStack(ArmorSets.bj_plate, 1), new Object[] { "N N", "MSM", "NCN", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ArmorSets.starmetal_plate, 'C', ModItems.circuit_targeting_tier5 });
		addRecipeAuto(new ItemStack(ArmorSets.bj_plate_jetpack, 1), new Object[] { "NFN", "TPT", "ICI", 'N', ModItems.plate_armor_lunar, 'F', ModItems.fins_quad_titanium, 'T', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.xenon)), 'P', ArmorSets.bj_plate, 'I', MissileParts.mp_thruster_10_xenon, 'C', P_RED.crystal() });
		addRecipeAuto(new ItemStack(ArmorSets.bj_legs, 1), new Object[] { "NBN", "MSM", "N N", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ArmorSets.starmetal_legs, 'B', STAR.block() });
		addRecipeAuto(new ItemStack(ArmorSets.bj_boots, 1), new Object[] { "N N", "BSB", 'N', ModItems.plate_armor_lunar, 'S', ArmorSets.starmetal_boots, 'B', STAR.block() });

		reg3();
	}

	public static void reg3(){
		addRecipeAuto(new ItemStack(ModBlocks.ladder_sturdy, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_iron, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_gold, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', GOLD.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_aluminium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', AL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_copper, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', CU.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_titanium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', TI.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_lead, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', PB.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_cobalt, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', CO.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_steel, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_tungsten, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', W.ingot() });

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe, 6), new Object[] { "PPP", 'P', ModItems.hull_small_steel });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_rim });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_framed });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_quad });
		addShapelessAuto(new ItemStack(ModBlocks.deco_rbmk, 8), new Object[] { ModBlocks.rbmk_blank });
		addShapelessAuto(new ItemStack(ModBlocks.deco_rbmk_smooth, 1), new Object[] { ModBlocks.deco_rbmk });
		

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad, 4), new Object[] { "PP", "PP", 'P', ModBlocks.deco_pipe });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', Blocks.IRON_BARS });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', Blocks.IRON_BARS });

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', "dyeGreen" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', "dyeGreen" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', "dyeGreen" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', "dyeGreen" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', "dyeRed" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', "dyeRed" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', "dyeRed" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', "dyeRed" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', "dyeGreen" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', "dyeGreen" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', "dyeGreen" });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', "dyeGreen" });

		addRecipeAuto(new ItemStack(ArmorSets.rpa_helmet, 1), new Object[] { "PXP", "PLP", "KFK", 'L', ArmorSets.cmb_helmet, 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_lunar, 'F', ModItems.gas_mask_m65, 'X', ModItems.circuit_targeting_tier5 });
		addRecipeAuto(new ItemStack(ArmorSets.rpa_plate, 1), new Object[] { "MXM", "KLK", "PPP", 'L', ArmorSets.cmb_plate, 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'X', ModItems.circuit_bismuth });
		addRecipeAuto(new ItemStack(ArmorSets.rpa_legs, 1), new Object[] { "MXM", "KLK", "P P", 'L', ArmorSets.cmb_legs, 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'X', ModItems.circuit_tantalium });
		addRecipeAuto(new ItemStack(ArmorSets.rpa_boots, 1), new Object[] { "P P", "PLP", 'L', ArmorSets.cmb_boots, 'P', ModItems.plate_armor_lunar });
		
		addRecipeAuto(new ItemStack(ArmorSets.ajr_helmet, 1), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_ajr, 'C', ModItems.circuit_targeting_tier4, 'I', ANY_PLASTIC.ingot(), 'X', ModItems.gas_mask_m65, 'B', ArmorSets.alloy_helmet });
		addRecipeAuto(new ItemStack(ArmorSets.ajr_plate, 1), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'T', ModItems.gas_canister, 'B', ArmorSets.alloy_plate });
		addRecipeAuto(new ItemStack(ArmorSets.ajr_legs, 1), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'B', ArmorSets.alloy_legs });
		addRecipeAuto(new ItemStack(ArmorSets.ajr_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_ajr, 'B', ArmorSets.alloy_boots });
		addShapelessAuto(new ItemStack(ArmorSets.ajro_helmet, 1), new Object[] { ArmorSets.ajr_helmet, "dyeRed", "dyeBlack" });
		addShapelessAuto(new ItemStack(ArmorSets.ajro_plate, 1), new Object[] { ArmorSets.ajr_plate, "dyeRed", "dyeBlack" });
		addShapelessAuto(new ItemStack(ArmorSets.ajro_legs, 1), new Object[] { ArmorSets.ajr_legs, "dyeRed", "dyeBlack" });
		addShapelessAuto(new ItemStack(ArmorSets.ajro_boots, 1), new Object[] { ArmorSets.ajr_boots, "dyeRed", "dyeBlack" });
		addRecipeAuto(new ItemStack(ArmorSets.fau_helmet, 1), new Object[] { "PWP", "PBP", "FSF", 'P', ModItems.plate_armor_fau, 'W', new ItemStack(Blocks.WOOL, 1, 14), 'B', ArmorSets.schrabidium_helmet, 'F', ModItems.gas_mask_filter, 'S', ModItems.pipes_steel });
		addRecipeAuto(new ItemStack(ArmorSets.fau_plate, 1), new Object[] { "MCM", "PBP", "PSP", 'M', ModItems.motor_desh, 'C', ModItems.demon_core_closed, 'P', ModItems.plate_armor_fau, 'B', ArmorSets.schrabidium_plate, 'S', ModBlocks.ancient_scrap });
		addRecipeAuto(new ItemStack(ArmorSets.fau_legs, 1), new Object[] { "MPM", "PBP", "PDP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_fau, 'B', ArmorSets.schrabidium_legs, 'D', PO210.billet() });
		addRecipeAuto(new ItemStack(ArmorSets.fau_boots, 1), new Object[] { "PDP", "PBP", 'P', ModItems.plate_armor_fau, 'D', PO210.billet(), 'B', ArmorSets.schrabidium_boots });
		addRecipeAuto(new ItemStack(ArmorSets.dns_helmet, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', Ingots.ingot_chainsteel, 'B', ArmorSets.bj_helmet, 'C', ModItems.circuit_targeting_tier6 });
		addRecipeAuto(new ItemStack(ArmorSets.dns_plate, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', Ingots.ingot_chainsteel, 'B', ArmorSets.bj_plate_jetpack, 'C', ModItems.singularity_spark });
		addRecipeAuto(new ItemStack(ArmorSets.dns_legs, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', Ingots.ingot_chainsteel, 'B', ArmorSets.bj_legs, 'C', ModItems.coin_worm });
		addRecipeAuto(new ItemStack(ArmorSets.dns_boots, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', Ingots.ingot_chainsteel, 'B', ArmorSets.bj_boots, 'C', ModItems.demon_core_closed });

		addRecipeAuto(new ItemStack(Armory.ammo_shell, 4), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', Items.GUNPOWDER, 'H', ModItems.hull_small_steel, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell, 5), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', ModItems.ballistite, 'H', ModItems.hull_small_steel, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell, 6), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', ModItems.cordite, 'H', ModItems.hull_small_steel, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_explosive, 4), new Object[] { " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', Items.GUNPOWDER, 'H', ModItems.hull_small_steel, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_explosive, 5), new Object[] { " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.ballistite, 'H', ModItems.hull_small_steel, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_explosive, 6), new Object[] { " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.cordite, 'H', ModItems.hull_small_steel, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_apfsds_t, 4), new Object[] { " I ", "GIG", "CCC", 'I', W.ingot(), 'G', Items.GUNPOWDER, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_apfsds_t, 5), new Object[] { " I ", "GIG", "CCC", 'I', W.ingot(), 'G', ModItems.ballistite, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_apfsds_t, 6), new Object[] { " I ", "GIG", "CCC", 'I', W.ingot(), 'G', ModItems.cordite, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_apfsds_du, 4), new Object[] { " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', Items.GUNPOWDER, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_apfsds_du, 5), new Object[] { " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', ModItems.ballistite, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_apfsds_du, 6), new Object[] { " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', ModItems.cordite, 'C', CU.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_shell_w9, 1), new Object[] { " P ", "NSN", " P ", 'P', PU239.nugget(), 'N', OreDictManager.getReflector(), 'S', Armory.ammo_shell_explosive });

		addShapelessAuto(new ItemStack(Ingots.ingot_smore), new Object[] { Items.WHEAT, new ItemStack(Foods.marshmallow_roasted, 1), new ItemStack(Items.DYE, 1, 3) });
		addShapelessAuto(new ItemStack(Foods.marshmallow), new Object[] { Items.STICK, Items.SUGAR, Items.WHEAT_SEEDS });

		addRecipeAuto(new ItemStack(ModItems.coltass, 1), new Object[] { "ACA", "CXC", "ACA", 'A', ALLOY.ingot(), 'C', ModItems.cinnebar, 'X', Items.COMPASS });
		addRecipeAuto(new ItemStack(ModItems.bismuth_tool, 1), new Object[] { "TBT", "SRS", "SCS", 'T',TA.nugget(), 'B', ANY_BISMOID.nugget(), 'S', TCALLOY.ingot(), 'R', ModItems.reacher, 'C', ModItems.circuit_aluminium });
		addRecipeAuto(new ItemStack(ModItems.reacher, 1), new Object[] { "BIB", "P P", "B B", 'B', ModItems.bolt_tungsten, 'I', W.ingot(), 'P', ANY_RUBBER.ingot() });
		addShapelessAuto(new ItemStack(Powders.powder_tcalloy, 1), new Object[] { STEEL.dust(), TC99.nugget() });

		addRecipeAuto(new ItemStack(ModBlocks.depth_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth });
		addRecipeAuto(new ItemStack(ModBlocks.depth_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_brick });
		addRecipeAuto(new ItemStack(ModBlocks.depth_nether_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth_nether });
		addRecipeAuto(new ItemStack(ModBlocks.depth_nether_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_nether_brick });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_polished });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_brick });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_tcalloy, 1), new Object[] { "IPI", "I I", "IPI", 'I', TCALLOY.ingot(), 'P', TI.plate() });

		addMineralSet(Nuggies.nugget_cadmium, Ingots.ingot_cadmium, ModBlocks.block_cadmium);
		addMineralSet(Nuggies.nugget_bismuth, Ingots.ingot_bismuth, ModBlocks.block_bismuth);
		addMineralSet(Nuggies.nugget_tantalium, Ingots.ingot_tantalium, ModBlocks.block_tantalium);
		addMineralSet(Nuggies.nugget_zirconium, Ingots.ingot_zirconium, ModBlocks.block_zirconium);
		addMineralSet(Nuggies.nugget_dineutronium, Ingots.ingot_dineutronium, ModBlocks.block_dineutronium);

		add1To9Pair(Powders.powder_xe135, Powders.powder_xe135_tiny);
		add1To9Pair(Powders.powder_cs137, Powders.powder_cs137_tiny);
		add1To9Pair(Powders.powder_i131, Powders.powder_i131_tiny);
		add1To9Pair(Powders.powder_iodine, Powders.powder_iodine_tiny);
		add1To9Pair(Powders.powder_sr90, Powders.powder_sr90_tiny);
		add1To9Pair(Powders.powder_co60, Powders.powder_co60_tiny);
		add1To9Pair(Powders.powder_au198, Powders.powder_au198_tiny);
		add1To9Pair(Powders.powder_pb209, Powders.powder_pb209_tiny);
		add1To9Pair(Powders.powder_at209, Powders.powder_at209_tiny);
		add1To9Pair(Powders.powder_ac227, Powders.powder_ac227_tiny);
		add1To9Pair(Powders.powder_radspice, Powders.powder_radspice_tiny);
		
		add1To9Pair(Ingots.ingot_technetium, Nuggies.nugget_technetium);
		add1To9Pair(Ingots.ingot_arsenic, Nuggies.nugget_arsenic);
		add1To9Pair(Ingots.ingot_co60, Nuggies.nugget_co60);
		add1To9Pair(Ingots.ingot_au198, Nuggies.nugget_au198);

		add1To9Pair(Ingots.ingot_pu241, Nuggies.nugget_pu241);
		add1To9Pair(Ingots.ingot_am241, Nuggies.nugget_am241);
		add1To9Pair(Ingots.ingot_am242, Nuggies.nugget_am242);
		add1To9Pair(Ingots.ingot_am_mix, Nuggies.nugget_am_mix);
		add1To9Pair(Ingots.ingot_americium_fuel, Nuggies.nugget_americium_fuel);

		add1To9Pair(Powders.powder_coal, Powders.powder_coal_tiny);

		add1To9Pair(ModBlocks.sand_gold, Powders.powder_gold);
		add1To9Pair(ModBlocks.sand_gold198, Powders.powder_au198);
		add1To9Pair(ModBlocks.block_coltan, ModItems.fragment_coltan);
		add1To9Pair(ModBlocks.block_smore, Ingots.ingot_smore);
		add1To9Pair(ModItems.nuclear_waste_vitrified, ModItems.nuclear_waste_vitrified_tiny);
		add1To9Pair(ModBlocks.block_waste_vitrified, ModItems.nuclear_waste_vitrified);
		add1To9Pair(ModBlocks.block_niobium, Ingots.ingot_niobium);
		add1To9Pair(ModBlocks.block_boron, Ingots.ingot_boron);
		add1To9Pair(Powders.powder_boron, Powders.powder_boron_tiny);
		add1To9Pair(ModBlocks.block_graphite, Ingots.ingot_graphite);
		add1To9Pair(ModBlocks.block_coke, ModItems.coke);
		add1To9Pair(ModBlocks.block_lignite, ModItems.lignite);
		add1To9Pair(ModBlocks.block_coal_infernal, ModItems.coal_infernal);
		add1To9Pair(ModBlocks.block_solid_fuel, ModItems.solid_fuel);
		add1To9Pair(ModBlocks.block_solid_fuel_presto, ModItems.solid_fuel_presto);
		add1To9Pair(ModBlocks.block_solid_fuel_presto_triplet, ModItems.solid_fuel_presto_triplet);

		add1To9Pair(Ingots.ingot_osmiridium, Nuggies.nugget_osmiridium);
		add1To9Pair(Ingots.ingot_radspice, Nuggies.nugget_radspice);
		addRecipeAuto(new ItemStack(ModItems.circuit_bismuth_raw, 1), new Object[] { "RPR", "ABA", "RPR", 'R', REDSTONE.dust(), 'P', ANY_PLASTIC.ingot(), 'A', (GeneralConfig.enable528 ? ModItems.circuit_tantalium : ASBESTOS.ingot()), 'B', ANY_BISMOID.ingot() });
		addRecipeAuto(new ItemStack(ModItems.circuit_arsenic_raw, 1), new Object[] { "RPR", "ABA", "RPR", 'R', REDSTONE.dust(), 'P', ANY_PLASTIC.ingot(), 'A', (GeneralConfig.enable528 ? ModItems.circuit_tantalium : ASBESTOS.ingot()), 'B', AS.ingot() });
		addRecipeAuto(new ItemStack(ModItems.circuit_tantalium_raw, 1), new Object[] { "RWR", "PTP", "RWR", 'R', REDSTONE.dust(), 'W', ModItems.wire_gold, 'P', CU.plate(), 'T', TA.nugget() });
		
		addRecipeAuto(new ItemStack(ModItems.inf_water_mk2, 1), new Object[] { "BPB", "PTP", "BPB", 'B', ModItems.inf_water, 'P', ModBlocks.fluid_duct_mk2, 'T', ModBlocks.barrel_steel });
		addRecipeAuto(new ItemStack(ModItems.inf_water_mk3, 1), new Object[] { "BPB", "PTP", "BPB", 'B', ModItems.inf_water_mk2, 'P', ModBlocks.fluid_duct_mk2, 'T', ModBlocks.machine_fluidtank });
		addRecipeAuto(new ItemStack(ModItems.inf_water_mk4, 1), new Object[] { "BPB", "PTP", "BPB", 'B', ModItems.inf_water_mk3, 'P', ModBlocks.fluid_duct_mk2, 'T', ModBlocks.machine_bat9000 });

		addRecipeAuto(new ItemStack(ModBlocks.machine_condenser), new Object[] { "SIS", "ICI", "SIS", 'S', STEEL.ingot(), 'I', IRON.plate(), 'C', ModItems.board_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_storage_drum), new Object[] { "LLL", "L#L", "LLL", 'L', PB.plate(), '#', ModItems.tank_steel });

		addRecipeAuto(new ItemStack(Batteries.battery_sc_uranium), new Object[] { "NBN", "PCP", "NBN", 'N', GOLD.nugget(), 'B', U238.billet(), 'P', PB.plate(), 'C', ModItems.thermo_element });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_technetium), new Object[] { "NBN", "PCP", "NBN", 'N', GOLD.nugget(), 'B',TC99.billet(), 'P', PB.plate(), 'C', Batteries.battery_sc_uranium });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_plutonium), new Object[] { "NBN", "PCP", "NBN", 'N', TC99.nugget(), 'B', PU238.billet(), 'P', PB.plate(), 'C', Batteries.battery_sc_technetium });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_polonium), new Object[] { "NBN", "PCP", "NBN", 'N', TC99.nugget(), 'B', PO210.billet(), 'P', ANY_PLASTIC.ingot(), 'C', Batteries.battery_sc_plutonium });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_gold), new Object[] { "NBN", "PCP", "NBN", 'N',TA.nugget(), 'B', AU198.billet(), 'P', ANY_PLASTIC.ingot(), 'C', Batteries.battery_sc_polonium });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_lead), new Object[] { "NBN", "PCP", "NBN", 'N',TA.nugget(), 'B', PB209.billet(), 'P', ANY_PLASTIC.ingot(), 'C', Batteries.battery_sc_gold });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_americium), new Object[] { "NBN", "PCP", "NBN", 'N',TA.nugget(), 'B', AM241.billet(), 'P', ANY_PLASTIC.ingot(), 'C', Batteries.battery_sc_lead });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_balefire), new Object[] { "NBN", "PCP", "NBN", 'N', Nuggies.nugget_radspice, 'B', ModItems.pellet_rtg_balefire, 'P', ANY_PLASTIC.ingot(), 'C', Batteries.battery_sc_americium });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_schrabidium), new Object[] { "NBN", "PCP", "NBN", 'N', Nuggies.nugget_unobtainium_greater, 'B', SA326.billet(), 'P', ANY_PLASTIC.ingot(), 'C', Batteries.battery_sc_balefire });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_yharonite), new Object[] { "NBN", "PCP", "NBN", 'N', DNT.nugget(), 'B', Billets.billet_yharonite, 'P', ANY_PLASTIC.ingot(), 'C', Batteries.battery_sc_schrabidium });
		addRecipeAuto(new ItemStack(Batteries.battery_sc_electronium), new Object[] { "NBN", "PCP", "NBN", 'N', Nuggies.nugget_u238m2, 'B', ModItems.glitch, 'P', Ingots.ingot_electronium, 'C', Batteries.battery_sc_yharonite });

		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_alloy, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_advanced_alloy, 'C', ModBlocks.fusion_conductor });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_gold, 1), new Object[] { "PGP", "PCP", "PGP", 'G', GOLD.dust(), 'C', ModBlocks.hadron_coil_alloy, 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_neodymium, 1), new Object[] { "G", "C", "G", 'G', ND.dust(), 'C', ModBlocks.hadron_coil_gold });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_magtung, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'C', ModBlocks.fwatz_conductor });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidium, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_schrabidium, 'C', ModBlocks.hadron_coil_magtung });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidate, 1), new Object[] { " S ", "SCS", " S ", 'S', SBD.dust(), 'C', ModBlocks.hadron_coil_schrabidium });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_starmetal, 1), new Object[] { "SNS", "SCS", "SNS", 'S', STAR.ingot(), 'N', ModBlocks.hadron_coil_neodymium, 'C', ModBlocks.hadron_coil_schrabidate });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_chlorophyte, 1), new Object[] { "TCT", "TST", "TCT", 'T', ModItems.coil_tungsten, 'C', Powders.powder_chlorophyte, 'S', ModBlocks.hadron_coil_starmetal });
		
		addRecipeAuto(new ItemStack(ModItems.flywheel_beryllium, 1), new Object[] { "BBB", "BTB", "BBB", 'B', BE.block(), 'T', ModItems.bolt_compound });

		addShapelessAuto(new ItemStack(ModItems.siox, 8), new Object[] { COAL.dust(), ASBESTOS.dust(), ANY_BISMOID.nugget() });
		addShapelessAuto(new ItemStack(ModItems.xanax, 1), new Object[] { COAL.dust(), KNO.dust(), BR.dust() });
		addShapelessAuto(new ItemStack(ModItems.fmn, 1), new Object[] { COAL.dust(), PO210.dust(), SR.dust() });
		addShapelessAuto(new ItemStack(ModItems.five_htp, 1), new Object[] { COAL.dust(), EUPH.dust(), Foods.canteen_fab });

		addRecipeAuto(new ItemStack(ModBlocks.steel_grate, 4), new Object[] { "SS", "SS", 'S', ModBlocks.steel_beam });

		addRecipeAuto(new ItemStack(ModItems.pile_rod_uranium, 1), new Object[] { " U ", "PUP", " U ", 'P', IRON.plate(), 'U', U.billet() });
		addRecipeAuto(new ItemStack(ModItems.pile_rod_source, 1), new Object[] { " U ", "PUP", " U ", 'P', IRON.plate(), 'U', Billets.billet_ra226be });
		addRecipeAuto(new ItemStack(ModItems.pile_rod_boron, 1), new Object[] { "B", "W", "B", 'B',  B.ingot(), 'W', KEY_PLANKS });

		addRecipeAuto(new ItemStack(RBMKFuel.rbmk_fuel_empty, 1), new Object[] { "ZRZ", "Z Z", "ZRZ", 'Z',  ZR.ingot(), 'R', RetroRods.rod_quad_empty });
		addRBMKRod(Billets.billet_uranium, RBMKFuel.rbmk_fuel_ueu);
		addRBMKRod(Billets.billet_uranium_fuel, RBMKFuel.rbmk_fuel_meu);
		addRBMKRod(Billets.billet_u233, RBMKFuel.rbmk_fuel_heu233);
		addRBMKRod(Billets.billet_u235, RBMKFuel.rbmk_fuel_heu235);
		addRBMKRod(Billets.billet_thorium_fuel, RBMKFuel.rbmk_fuel_thmeu);
		addRBMKRod(Billets.billet_mox_fuel, RBMKFuel.rbmk_fuel_mox);
		addRBMKRod(Billets.billet_plutonium_fuel, RBMKFuel.rbmk_fuel_lep);
		addRBMKRod(Billets.billet_pu_mix, RBMKFuel.rbmk_fuel_mep);
		addRBMKRod(Billets.billet_pu239, RBMKFuel.rbmk_fuel_hep239);
		addRBMKRod(Billets.billet_pu241, RBMKFuel.rbmk_fuel_hep241);
		addRBMKRod(Billets.billet_americium_fuel, RBMKFuel.rbmk_fuel_lea);
		addRBMKRod(Billets.billet_am_mix, RBMKFuel.rbmk_fuel_mea);
		addRBMKRod(Billets.billet_am241, RBMKFuel.rbmk_fuel_hea241);
		addRBMKRod(Billets.billet_am242, RBMKFuel.rbmk_fuel_hea242);
		addRBMKRod(Billets.billet_neptunium_fuel, RBMKFuel.rbmk_fuel_men);
		addRBMKRod(Billets.billet_neptunium, RBMKFuel.rbmk_fuel_hen);
		addRBMKRod(Billets.billet_po210be, RBMKFuel.rbmk_fuel_po210be);
		addRBMKRod(Billets.billet_ra226be, RBMKFuel.rbmk_fuel_ra226be);
		addRBMKRod(Billets.billet_pu238be, RBMKFuel.rbmk_fuel_pu238be);
		addRBMKRod(Billets.billet_australium_lesser, RBMKFuel.rbmk_fuel_leaus);
		addRBMKRod(Billets.billet_australium_greater, RBMKFuel.rbmk_fuel_heaus);
		addRBMKRod(Billets.billet_unobtainium, RBMKFuel.rbmk_fuel_unobtainium);
		addRBMKRod(ModItems.egg_balefire_shard, RBMKFuel.rbmk_fuel_balefire);
		addRBMKRod(Billets.billet_les, RBMKFuel.rbmk_fuel_les);
		addRBMKRod(Billets.billet_schrabidium_fuel, RBMKFuel.rbmk_fuel_mes);
		addRBMKRod(Billets.billet_hes, RBMKFuel.rbmk_fuel_hes);
		addRBMKRod(Billets.billet_balefire_gold, RBMKFuel.rbmk_fuel_balefire_gold);
		addRBMKRod(Billets.billet_flashlead, RBMKFuel.rbmk_fuel_flashlead);
		addRBMKRod(Billets.billet_zfb_bismuth, RBMKFuel.rbmk_fuel_zfb_bismuth);
		addRBMKRod(Billets.billet_zfb_pu241, RBMKFuel.rbmk_fuel_zfb_pu241);
		addRBMKRod(Billets.billet_zfb_am_mix, RBMKFuel.rbmk_fuel_zfb_am_mix);
		addShapelessAuto(new ItemStack(RBMKFuel.rbmk_fuel_drx, 1), new Object[] { RBMKFuel.rbmk_fuel_balefire, ModItems.particle_digamma });

		addRecipeAuto(new ItemStack(ModItems.rbmk_lid, 4), new Object[] { "PPP", "CCC", "PPP", 'P', STEEL.plate(), 'C', ModBlocks.concrete_asbestos });
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "LLL", "BBB", "P P", 'P', STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "BBB", "LLL", "P P", 'P', STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });

		addRecipeAuto(new ItemStack(ModBlocks.rbmk_moderator, 1), new Object[] { " G ", "GRG", " G ", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_absorber, 1), new Object[] { "GGG", "GRG", "GGG", 'G', B.ingot(), 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_reflector, 1), new Object[] { "GGG", "GRG", "GGG", 'G', ModItems.neutron_reflector, 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control, 1), new Object[] { " B ", "GRG", " B ", 'G', GRAPHITE.ingot(), 'B', ModItems.motor, 'R', ModBlocks.rbmk_absorber });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_control, 'B', ANY_BISMOID.nugget() });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_auto, 1), new Object[] { "C", "R", "C", 'C', ModItems.circuit_targeting_tier1, 'R', ModBlocks.rbmk_control });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim, 1), new Object[] { "ZCZ", "ZRZ", "ZCZ", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank, 'Z',  ZR.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_rod_reasim, 'B', TCALLOY.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_outgasser, 1), new Object[] { "GHG", "GRG", "GTG", 'G', ModBlocks.steel_grate, 'H', Blocks.HOPPER, 'T', ModItems.tank_steel, 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_storage, 1), new Object[] { "C", "R", "C", 'C', ModBlocks.crate_steel, 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_loader, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.plate(), 'C', CU.ingot(), 'B', ModItems.tank_steel });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_inlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', IRON.plate(), 'B', ModItems.tank_steel });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_outlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', CU.plate(), 'B', ModItems.tank_steel });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_heater, 1), new Object[] { "CIC", "PRP", "CIC", 'C', ModItems.board_copper, 'P', ModItems.pipes_steel, 'R', ModBlocks.rbmk_blank, 'I', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_cooler, 1), new Object[] { "IGI", "GCG", "IGI", 'C', ModBlocks.rbmk_blank, 'I', ANY_RUBBER.ingot(), 'G', ModBlocks.steel_grate });
		
		addRecipeAuto(new ItemStack(ModBlocks.anvil_iron, 1), new Object[] { "III", " B ", "III", 'I', IRON.ingot(), 'B', IRON.block() });
		addRecipeAuto(new ItemStack(ModBlocks.anvil_lead, 1), new Object[] { "III", " B ", "III", 'I', PB.ingot(), 'B', PB.block() });
		addRecipeAuto(new ItemStack(ModBlocks.anvil_murky, 1), new Object[] { "UUU", "UAU", "UUU", 'U', ModItems.undefined, 'A', ModBlocks.anvil_steel });
		addRecipeAuto(new ItemStack(ModBlocks.machine_fraction_tower), new Object[] { "SHS", "SGS", "SHS", 'S', STEEL.plate(), 'H', ModItems.hull_big_steel, 'G', ModBlocks.steel_grate });
		addRecipeAuto(new ItemStack(ModBlocks.fraction_spacer), new Object[] { "BHB", 'H', ModItems.hull_big_steel, 'B', Blocks.IRON_BARS });
		addRecipeAuto(new ItemStack(ModBlocks.furnace_iron), new Object[] { "III", "IFI", "BBB", 'I', IRON.ingot(), 'F', Blocks.FURNACE, 'B', Blocks.STONEBRICK });
		addRecipeAuto(new ItemStack(ModBlocks.machine_mixer), new Object[] { "PIP", "GCG", "PMP", 'P', STEEL.plate(), 'I', DURA.ingot(), 'G', KEY_ANYPANE, 'C', ModItems.circuit_copper, 'M', ModItems.motor });
		
		//Cladding
		addShapelessAuto(new ItemStack(Inserts.cladding_paint, 1), new Object[] { PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), Items.CLAY_BALL, Items.GLASS_BOTTLE });
		addRecipeAuto(new ItemStack(Inserts.cladding_rubber, 1), new Object[] { "RCR", "CDC", "RCR", 'R', ANY_RUBBER.ingot(), 'C', COAL.dust(), 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_lead, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_rubber, 'P', PB.plate(), 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_desh, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_lead, 'P',ModItems.plate_desh, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_paa, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_desh, 'P', ModItems.plate_paa, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_ghiorsium, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_paa, 'P', GH336.ingot(), 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_euphemium, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_ghiorsium, 'P', ModItems.plate_euphemium, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_di, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_euphemium, 'P', ModItems.plate_dineutronium, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_electronium, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_di, 'P', Ingots.ingot_electronium, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_obsidian, 1), new Object[] { "OOO", "PDP", "OOO", 'O', Blocks.OBSIDIAN, 'P', STEEL.plate(), 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_iron, 1), new Object[] { "OOO", "PDP", "OOO", 'O', IRON.plate(), 'P', ANY_RUBBER.ingot(), 'D', ModItems.ducttape });

		//Inserts
		addRecipeAuto(new ItemStack(Inserts.insert_steel, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', IRON.plate(), 'S', STEEL.block() });
		addRecipeAuto(new ItemStack(Inserts.insert_du, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', IRON.plate(), 'S', U238.block() });
		addRecipeAuto(new ItemStack(Inserts.insert_ferrouranium, 1), new Object[] { "PIP", "IDI", "PIP", 'D', Inserts.insert_kevlar, 'P', ModItems.ducttape, 'I', FERRO.ingot() });
		addRecipeAuto(new ItemStack(Inserts.insert_polonium, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', IRON.plate(), 'S', PO210.block() });
		addRecipeAuto(new ItemStack(Inserts.insert_era, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', IRON.plate(), 'S', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(Inserts.insert_kevlar, 1), new Object[] { "KIK", "IDI", "KIK", 'K', ModItems.plate_kevlar, 'I', ANY_RUBBER.ingot(), 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.insert_sapi, 1), new Object[] { "PKP", "DPD", "PKP", 'P', ANY_PLASTIC.ingot(), 'K', Inserts.insert_kevlar, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.insert_esapi, 1), new Object[] { "PKP", "DSD", "PKP", 'P', ANY_PLASTIC.ingot(), 'K', Inserts.insert_sapi, 'D', ModItems.ducttape, 'S', BIGMT.plate() });
		addRecipeAuto(new ItemStack(Inserts.insert_xsapi, 1), new Object[] { "PKP", "DSD", "PKP", 'P', ASBESTOS.ingot(), 'K', Inserts.insert_esapi, 'D', ModItems.ducttape, 'S', Ingots.ingot_meteorite_forged });
		addRecipeAuto(new ItemStack(Inserts.insert_ghiorsium, 1), new Object[] { "PKP", "KSK", "PKP", 'P', ModItems.ducttape, 'K', GH336.ingot(), 'S', U238.ingot() });
		addRecipeAuto(new ItemStack(Inserts.insert_di, 1), new Object[] { "PKP", "KSK", "PKP", 'P', ModItems.ducttape, 'K', ModItems.plate_dineutronium, 'S', Inserts.insert_ghiorsium });
		addRecipeAuto(new ItemStack(Inserts.insert_yharonite, 1), new Object[] { "YIY", "IYI", "YIY", 'Y', Billets.billet_yharonite, 'I', Inserts.insert_du });

		//Servos
		addRecipeAuto(new ItemStack(Inserts.servo_set, 1), new Object[] { "MBM", "PBP", "MBM", 'M', ModItems.motor, 'B', ModItems.bolt_tungsten, 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(Inserts.servo_set_desh, 1), new Object[] { "MBM", "PSP", "MBM", 'M', ModItems.motor_desh, 'B', ModItems.bolt_dura_steel, 'P', ALLOY.plate(), 'S', Inserts.servo_set });

		//Helmet Mods
		addRecipeAuto(new ItemStack(ModItems.attachment_mask, 1), new Object[] { "DID", "IGI", " F ", 'D', ModItems.ducttape, 'I', ANY_RUBBER.ingot(), 'G', KEY_ANYPANE, 'F', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.attachment_mask_mono, 1), new Object[] { " D ", "DID", " F ", 'D', ModItems.ducttape, 'I', ANY_RUBBER.ingot(), 'F', IRON.plate() });

		//Boot Mods
		addRecipeAuto(new ItemStack(Inserts.pads_rubber, 1), new Object[] { "P P", "IDI", "P P", 'P', ANY_RUBBER.ingot(), 'I', IRON.plate(), 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.pads_slime, 1), new Object[] { "SPS", "DSD", "SPS", 'S', KEY_SLIME, 'P', Inserts.pads_rubber, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.pads_static, 1), new Object[] { "CDC", "ISI", "CDC", 'C', CU.plate(), 'D', ModItems.ducttape, 'I', ModItems.plate_polymer, 'S', Inserts.pads_slime });

		//Special Mods
		addRecipeAuto(new ItemStack(Inserts.horseshoe_magnet, 1), new Object[] { "L L", "I I", "ILI", 'L', Inserts.lodestone, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(Inserts.industrial_magnet, 1), new Object[] { "SMS", " B ", "SMS", 'S', STEEL.ingot(), 'M', Inserts.horseshoe_magnet, 'B', ModBlocks.fusion_conductor });
		addRecipeAuto(new ItemStack(Inserts.heart_container, 1), new Object[] { "HAH", "ACA", "HAH", 'H', Inserts.heart_piece, 'A', AL.ingot(), 'C', ModItems.coin_creeper });
		addRecipeAuto(new ItemStack(Inserts.heart_booster, 1), new Object[] { "GHG", "MCM", "GHG", 'G', GOLD.ingot(), 'H', Inserts.heart_container, 'M', Inserts.morning_glory, 'C', ModItems.coin_maskman });
		addRecipeAuto(new ItemStack(Inserts.heart_fab, 1), new Object[] { "GHG", "MCM", "GHG", 'G', PO210.billet(), 'H', Inserts.heart_booster, 'M', Foods.canteen_fab, 'C', ModItems.coin_worm });
		addRecipeAuto(new ItemStack(Inserts.ink, 1), new Object[] { "FPF", "PIP", "FPF", 'F', new ItemStack(Blocks.RED_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 'P', Inserts.armor_polish, 'I', "dyeBlack" });
		addRecipeAuto(new ItemStack(Inserts.bathwater_mk2, 1), new Object[] { "MWM", "WBW", "MWM", 'M', ModItems.bottle_mercury, 'W', ModItems.nuclear_waste, 'B', Inserts.bathwater });
		addRecipeAuto(new ItemStack(Inserts.bathwater_mk3, 1), new Object[] { "MWM", "WBW", "MWM", 'M', ModBlocks.block_corium_cobble, 'W', Powders.powder_radspice, 'B', Inserts.bathwater_mk2 });
		addRecipeAuto(new ItemStack(Inserts.back_tesla, 1), new Object[] { "DGD", "GTG", "DGD", 'D', ModItems.ducttape, 'G', ModItems.wire_gold, 'T', ModBlocks.tesla });
		addRecipeAuto(new ItemStack(Inserts.medal_liquidator, 1), new Object[] { "GBG", "BFB", "GBG", 'G',AU198.nugget(), 'B', B.ingot(), 'F', ModItems.debris_fuel });
		addRecipeAuto(new ItemStack(Inserts.medal_ghoul, 1), new Object[] { "GEG", "BFB", "GEG", 'G',Nuggies.nugget_u238m2, 'B', ModBlocks.pribris_digamma, 'E', ModItems.glitch, 'F', Inserts.medal_liquidator });
		addShapelessAuto(new ItemStack(Inserts.injector_5htp, 1), new Object[] { ModItems.five_htp, ModItems.circuit_targeting_tier1, BIGMT.plate() });
		addShapelessAuto(new ItemStack(Inserts.injector_knife, 1), new Object[] { Inserts.injector_5htp, Items.IRON_SWORD });
		addRecipeAuto(new ItemStack(Inserts.shackles, 1), new Object[] { "CIC", "C C", "I I", 'I', Ingots.ingot_chainsteel, 'C', ModBlocks.chain });
		addRecipeAuto(new ItemStack(Inserts.black_diamond, 1), new Object[] { "NIN", "IGI", "NIN", 'N',AU198.nugget(), 'I', Inserts.ink, 'G', VOLCANIC.gem() });
		addRecipeAuto(new ItemStack(Inserts.protection_charm, 1), new Object[] { " M ", "MDM", " M ", 'M', ModItems.fragment_meteorite, 'D', DIAMOND.gem() });
		addRecipeAuto(new ItemStack(Inserts.meteor_charm, 1), new Object[] { " M ", "MDM", " M ", 'M', ModItems.fragment_meteorite, 'D', VOLCANIC.gem() });
		addRecipeAuto(new ItemStack(Inserts.pocket_ptsd, 1), new Object[] { " R ", "PBP", "PSP", 'R', ModBlocks.machine_radar, 'P', ANY_PLASTIC.ingot(), 'B', Batteries.battery_sc_polonium, 'S', ModBlocks.machine_siren });
		addShapelessAuto(new ItemStack(Inserts.cladding_paint, 1), new Object[] { PB.dust(), Items.CLAY_BALL, Items.GLASS_BOTTLE });
		addRecipeAuto(new ItemStack(Inserts.cladding_rubber, 1), new Object[] { "RCR", "CDC", "RCR", 'R', ANY_RUBBER.ingot(), 'C', COAL.dust(), 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_lead, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_rubber, 'P', PB.plate(), 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(Inserts.cladding_desh, 1), new Object[] { "DPD", "PRP", "DPD", 'R', Inserts.cladding_lead, 'P',ModItems.plate_desh, 'D', ModItems.ducttape });
		addRecipeAuto(new ItemStack(ModBlocks.struct_plasma_core, 1), new Object[] { "CBC", "BHB", "CBC", 'C', ModItems.circuit_gold, 'B', ModBlocks.machine_desh_battery, 'H', ModBlocks.fusion_heater });

		addRecipeAuto(new ItemStack(ModItems.drax, 1), new Object[] { "BDS", "CDC", "FMF", 'B', ToolSets.starmetal_pickaxe, 'S', ToolSets.starmetal_shovel, 'C', CO.ingot(), 'F', ModItems.fusion_core, 'D', DESH.ingot(), 'M', ModItems.motor_desh });
		addRecipeAuto(new ItemStack(ModItems.drax_mk2, 1), new Object[] { "SCS", "IDI", "FEF", 'S', STAR.ingot(), 'C', Crystals.crystal_trixite, 'I', BIGMT.ingot(), 'D', ModItems.drax, 'F', ModItems.fusion_core, 'E', ModItems.circuit_targeting_tier5 });
		addRecipeAuto(new ItemStack(ModItems.drax_mk3, 1), new Object[] { "ECE", "CDC", "SBS", 'E', ModBlocks.block_euphemium_cluster, 'C', SA326.crystal(), 'D', ModItems.drax_mk2, 'S', ModItems.circuit_targeting_tier6, 'B', ItemBattery.getFullBattery(Batteries.battery_spark) });

		addRecipeAuto(new ItemStack(ToolSets.cobalt_decorated_sword, 1), new Object[] { " I ", " I ", "SBS", 'I', CO.ingot(), 'S', Ingots.ingot_meteorite_forged, 'B', ToolSets.cobalt_sword });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_decorated_pickaxe, 1), new Object[] { "III", " B ", " S ", 'I', CO.ingot(), 'S', Ingots.ingot_meteorite_forged, 'B', ToolSets.cobalt_pickaxe });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_decorated_axe, 1), new Object[] { "II", "IB", " S", 'I', CO.ingot(), 'S', Ingots.ingot_meteorite_forged, 'B', ToolSets.cobalt_axe });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_decorated_shovel, 1), new Object[] { "I", "B", "S", 'I', CO.ingot(), 'S', Ingots.ingot_meteorite_forged, 'B', ToolSets.cobalt_shovel });
		addRecipeAuto(new ItemStack(ToolSets.cobalt_decorated_hoe, 1), new Object[] { "II", " B", " S", 'I', CO.ingot(), 'S', Ingots.ingot_meteorite_forged, 'B', ToolSets.cobalt_hoe });
		addRecipeAuto(new ItemStack(ToolSets.starmetal_sword, 1), new Object[] { " I ", " I ", "SBS", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ToolSets.cobalt_decorated_sword });
		addRecipeAuto(new ItemStack(ToolSets.starmetal_pickaxe, 1), new Object[] { "III", " B ", " S ", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ToolSets.cobalt_decorated_pickaxe });
		addRecipeAuto(new ItemStack(ToolSets.starmetal_axe, 1), new Object[] { "II", "IB", " S", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ToolSets.cobalt_decorated_axe });
		addRecipeAuto(new ItemStack(ToolSets.starmetal_shovel, 1), new Object[] { "I", "B", "S", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ToolSets.cobalt_decorated_shovel });
		addRecipeAuto(new ItemStack(ToolSets.starmetal_hoe, 1), new Object[] { "II", " B", " S", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ToolSets.cobalt_decorated_hoe });

		addRecipeAuto(new ItemStack(ToolSets.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', Powders.powder_chlorophyte, 'A', FIBER.ingot(), 'P', ToolSets.bismuth_pickaxe, 'F', ModItems.bolt_dura_steel });
		addRecipeAuto(new ItemStack(ToolSets.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', Powders.powder_chlorophyte, 'A', FIBER.ingot(), 'P', ToolSets.volcanic_pickaxe, 'F', ModItems.bolt_dura_steel });
		addRecipeAuto(new ItemStack(ToolSets.bismuth_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ANY_BISMOID.ingot(), 'M', Ingots.ingot_meteorite, 'P', ToolSets.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ToolSets.volcanic_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', VOLCANIC.gem(), 'M', Ingots.ingot_meteorite, 'P', ToolSets.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ToolSets.mese_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_desh, 'D', DNT.dust(), 'A', ModItems.plate_paa, 'P', ToolSets.chlorophyte_pickaxe, 'F', ModItems.shimmer_handle });

		addRecipeAuto(new ItemStack(Upgrades.upgrade_nullifier, 1), new Object[] { "SPS", "PUP", "SPS", 'S', STEEL.plate(), 'P',P_RED.dust(), 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_smelter, 1), new Object[] { "PHP", "CUC", "DTD", 'P', CU.plate(), 'H', Blocks.HOPPER, 'C', ModItems.coil_tungsten, 'U', ModItems.upgrade_template, 'D', ModItems.coil_copper, 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_shredder, 1), new Object[] { "PHP", "CUC", "DTD", 'P', ModItems.motor, 'H', Blocks.HOPPER, 'C', ModItems.blades_advanced_alloy, 'U', Upgrades.upgrade_smelter, 'D', TI.plate(), 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_centrifuge, 1), new Object[] { "PHP", "PUP", "DTD", 'P', ModItems.centrifuge_element, 'H', Blocks.HOPPER, 'U', Upgrades.upgrade_shredder, 'D', ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_crystallizer, 1), new Object[] { "PHP", "CUC", "DTD", 'P', new IngredientContainsTag(ItemFluidTank.getFullBarrel(ModForgeFluids.acid)), 'H', ModItems.circuit_targeting_tier4, 'C', ModBlocks.barrel_steel, 'U', Upgrades.upgrade_centrifuge, 'D', ModItems.motor, 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_screm, 1), new Object[] { "SUS", "SCS", "SUS", 'S', STEEL.plate(), 'U', Upgrades.upgrade_overdrive_3, 'C', ModItems.crystal_xen });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_stack_1, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_aluminium, 'P', ModItems.piston_pneumatic, 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_stack_2, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_copper, 'P', ModItems.piston_hydraulic, 'U', new ItemStack(Upgrades.upgrade_stack_1) });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_stack_3, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_red_copper, 'P', ModItems.piston_electro, 'U', new ItemStack(Upgrades.upgrade_stack_2) });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_ejector_1, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_copper, 'P', ModItems.motor, 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_ejector_2, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_gold, 'P', ModItems.motor, 'U', new ItemStack(Upgrades.upgrade_ejector_1) });
		addRecipeAuto(new ItemStack(Upgrades.upgrade_ejector_3, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_saturnite, 'P', ModItems.motor, 'U', new ItemStack(Upgrades.upgrade_ejector_2) });

		addRecipeAuto(new ItemStack(ModItems.piston_pneumatic, 4), new Object[] { " I ", "CPC", " I ", 'I', IRON.ingot(), 'C', CU.ingot(), 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.piston_hydraulic, 4), new Object[] { " I ", "CPC", " I ", 'I', STEEL.ingot(), 'C', TI.ingot(), 'P', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.lubricant)) });
		addRecipeAuto(new ItemStack(ModItems.piston_electro, 4), new Object[] { " I ", "CPC", " I ", 'I', ANY_RESISTANTALLOY.ingot(), 'C', ANY_PLASTIC.ingot(), 'P', ModItems.motor });

		addRecipeAuto(new ItemStack(Armory.charge_railgun), new Object[] { "PDP", "DDD", "PDP", 'P', STEEL.plate(), 'D', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.deuterium)) });

		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_canister, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_4gauge_kampf, 'A', ModItems.pellet_canister });
		addRecipeAuto(new ItemStack(Armory.ammo_44_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_44, 'A', ModItems.pellet_chlorophyte });
		addRecipeAuto(new ItemStack(Armory.ammo_5mm_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_5mm, 'A', ModItems.pellet_chlorophyte });
		addRecipeAuto(new ItemStack(Armory.ammo_9mm_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_9mm, 'A', ModItems.pellet_chlorophyte });
		addRecipeAuto(new ItemStack(Armory.ammo_22lr_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_22lr, 'A', ModItems.pellet_chlorophyte });
		addRecipeAuto(new ItemStack(Armory.ammo_50bmg_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50bmg, 'A', ModItems.pellet_chlorophyte });
		addRecipeAuto(new ItemStack(Armory.ammo_50ae_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_50ae, 'A', ModItems.pellet_chlorophyte });
		addRecipeAuto(new ItemStack(Armory.ammo_556_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556, 'A', ModItems.pellet_chlorophyte });
		addRecipeAuto(new ItemStack(Armory.ammo_556_flechette_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', Armory.ammo_556_flechette, 'A', ModItems.pellet_chlorophyte });
		addRecipeAuto(new ItemStack(Armory.ammo_rocket_canister, 1), new Object[] { "G", "R", 'G', ModItems.pellet_canister, 'R', Armory.ammo_rocket });

		addShapelessAuto(new ItemStack(ModItems.circuit_red_copper, 48), new Object[] { ModBlocks.fusion_core });
		addShapelessAuto(new ItemStack(ModBlocks.fusion_heater), new Object[] { ModBlocks.fusion_hatch });
		addShapelessAuto(new ItemStack(ModItems.energy_core), new Object[] { ModItems.fusion_core, ModItems.fuse });

		addRecipeAuto(new ItemStack(ModItems.plate_armor_titanium, 1), new Object[] { "NPN", "PIP", "NPN", 'N', ModItems.bolt_tungsten, 'P', TI.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_armor_lunar, 1), new Object[] { "NPN", "PIP", "NPN", 'N', ModItems.wire_magnetized_tungsten, 'P', OreDictManager.getReflector(), 'I', ModItems.plate_armor_hev });

		addRecipeAuto(new ItemStack(ModItems.wood_gavel, 1), new Object[] { "SWS", " R ", " R ", 'S', KEY_SLAB, 'W', KEY_LOG, 'R', "stickWood" });
		addRecipeAuto(new ItemStack(ModItems.lead_gavel, 1), new Object[] { "PIP", "IGI", "PIP", 'P', ModItems.pellet_buckshot, 'I', PB.ingot(), 'G', ModItems.wood_gavel });

		addRecipeAuto(new ItemStack(ModItems.mech_key, 1), new Object[] { "MCM", "MKM", "MMM", 'M', Ingots.ingot_meteorite_forged, 'C', ModItems.coin_maskman, 'K', ModItems.key });
		addRecipeAuto(new ItemStack(ModItems.spawn_ufo, 1), new Object[] { "MMM", "DCD", "MMM", 'M', Ingots.ingot_meteorite, 'D', DNT.ingot(), 'C', ModItems.coin_worm });

		addRecipeAuto(new ItemStack(ModItems.particle_empty, 2), new Object[] { "STS", "G G", "STS", 'S', STEEL.plate(), 'T', W.ingot(), 'G', KEY_ANYPANE });
		addShapelessAuto(new ItemStack(ModItems.particle_copper, 1), new Object[] { ModItems.particle_empty, CU.dust(), ModItems.pellet_charged });
		addShapelessAuto(new ItemStack(ModItems.particle_lead, 1), new Object[] { ModItems.particle_empty, PB.dust(), ModItems.pellet_charged });
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.amat), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, new IngredientNBT2(new ItemStack(ModItems.cell)) });
		addShapelessAuto(new ItemStack(ModItems.particle_amat, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.particle_empty });
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.aschrab), new Object[] { ModItems.particle_aschrab, new IngredientNBT2(new ItemStack(ModItems.cell)) });
		addShapelessAuto(new ItemStack(ModItems.particle_aschrab), new Object[] { new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.aschrab)), ModItems.particle_empty });
		addShapelessAuto(new ItemStack(ModItems.particle_amat), new Object[] { new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)), ModItems.particle_empty });
		addRecipeAuto(new ItemStack(ModItems.capsule_empty, 1), new Object[] { "STS", "GXG", "STS", 'S', ModItems.plate_armor_lunar, 'T', ModItems.coil_advanced_torus, 'G', GH336.ingot(), 'X', ModItems.particle_empty });
		addShapelessAuto(new ItemStack(ModItems.capsule_xen), new Object[] { ModItems.capsule_empty, ModItems.crystal_xen });

		ItemStack infinity = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(infinity, Enchantments.INFINITY, 1);
		addRecipeAuto(infinity, new Object[] { "SBS", "BDB", "SBS", 'S', Armory.ammo_50bmg_star, 'B', Armory.ammo_5mm_star, 'D', Powders.powder_magic });
		ItemStack unbreaking = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(unbreaking, Enchantments.UNBREAKING, 3);
		addRecipeAuto(unbreaking, new Object[] { "SBS", "BDB", "SBS", 'S', BIGMT.ingot(), 'B', ModItems.plate_armor_lunar, 'D', Powders.powder_magic });
		ItemStack thorns = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(thorns, Enchantments.THORNS, 3);
		addRecipeAuto(thorns, new Object[] { "SBS", "BDB", "SBS", 'S', ModBlocks.barbed_wire, 'B', ModBlocks.spikes, 'D', Powders.powder_magic });

		addRecipeAuto(new ItemStack(ModBlocks.hadron_diode, 1), new Object[] { "CIC", "ISI", "CIC", 'C', ModBlocks.hadron_coil_alloy, 'I', STEEL.ingot(), 'S', ModItems.circuit_gold });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_plating, 1), new Object[] { "IPI", "P P", "IPI", 'I', STEEL.ingot(), 'P', STEEL.plate() });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_blue, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlue" });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_black, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlack" });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_yellow, 1), new Object[] { ModBlocks.hadron_plating, "dyeYellow" });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_striped, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlack", "dyeYellow" });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_glass, 1), new Object[] { ModBlocks.hadron_plating, KEY_ANYGLASS });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_voltz, 1), new Object[] { ModBlocks.hadron_plating, "dyeRed" });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_power, 1), new Object[] { "STS", "CPC", "STS", 'S', BIGMT.ingot(), 'T', ModBlocks.machine_transformer, 'C', ModItems.circuit_targeting_tier3, 'P', ModBlocks.hadron_plating_blue });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_analysis, 1), new Object[] { "IPI", "PCP", "IPI", 'I', TI.ingot(), 'P', OreDictManager.getReflector(), 'C', ModItems.circuit_gold });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_analysis_glass, 1), new Object[] { ModBlocks.hadron_analysis, KEY_ANYGLASS });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_access, 1), new Object[] { "IGI", "CRC", "IPI", 'I', ModItems.plate_polymer, 'G', KEY_ANYPANE, 'C', ModItems.circuit_aluminium, 'R', REDSTONE.block(), 'P', ModBlocks.hadron_plating_blue });

		addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_advanced_alloy });
		addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_gold });
		addRecipeAuto(new ItemStack(ModBlocks.machine_solar_boiler), new Object[] { "SHS", "DHD", "SHS", 'S', STEEL.ingot(), 'H', ModItems.hull_big_steel, 'D', "dyeBlack" });
		addRecipeAuto(new ItemStack(ModBlocks.solar_mirror, 3), new Object[] { "AAA", " B ", "SSS", 'A', AL.plate(), 'B', ModBlocks.steel_beam, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.steel_beam, 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(ModItems.mirror_tool), new Object[] { " A ", " IA", "I  ", 'A', AL.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.rbmk_tool), new Object[] { " A ", " IA", "I  ", 'A', PB.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hand_drill), new Object[] { " D", "S ", " S", 'D', DURA.ingot(), 'S', Items.STICK });
		addRecipeAuto(new ItemStack(ModItems.hand_drill_desh), new Object[] { " D", "S ", " S", 'D', DESH.ingot(), 'S', ANY_PLASTIC.ingot() });
		
		addRecipeAuto(new ItemStack(ArmorSets.hev_helmet, 1), new Object[] { "PCP", "PBP", "IFI", 'P', ModItems.plate_armor_hev, 'C', ModItems.circuit_targeting_tier4, 'B', ArmorSets.cobalt_helmet, 'I', ANY_RUBBER.ingot(), 'F', ModItems.gas_mask_filter });
		addRecipeAuto(new ItemStack(ArmorSets.hev_plate, 1), new Object[] { "MPM", "IBI", "PPP", 'P', ModItems.plate_armor_hev, 'B', ArmorSets.cobalt_plate, 'I', ANY_PLASTIC.ingot(), 'M', ModItems.motor_desh });
		addRecipeAuto(new ItemStack(ArmorSets.hev_legs, 1), new Object[] { "MPM", "IBI", "P P", 'P', ModItems.plate_armor_hev, 'B', ArmorSets.cobalt_legs, 'I', ANY_PLASTIC.ingot(), 'M', ModItems.motor_desh });
		addRecipeAuto(new ItemStack(ArmorSets.hev_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_hev, 'B', ArmorSets.cobalt_boots });
		addRecipeAuto(new ItemStack(ModItems.plate_armor_hev, 1), new Object[] { "NPN", "AIA", "NPN", 'N', ModItems.wire_tungsten, 'P', ALLOY.plate(), 'I', ModItems.plate_armor_ajr, 'A', ModItems.plate_paa });

		addRecipeAuto(new ItemStack(ModBlocks.machine_detector, 1), new Object[] { "IRI", "CTC", "IRI", 'I', ModItems.plate_polymer, 'R', Items.REDSTONE, 'C', ModItems.wire_red_copper, 'T', ModItems.coil_tungsten });
		addRecipeAuto(new ItemStack(ToolSets.meteorite_sword, 1), new Object[] { "  B", "GB ", "SG ", 'B', ModItems.blade_meteorite, 'G', GOLD.plate(), 'S', Items.STICK });
		addShapelessAuto(new ItemStack(Powders.powder_semtex_mix, 1), new Object[] { ModItems.solid_fuel, ModItems.ballistite, KNO.dust() });
		add9To1(Ingots.ingot_aluminium, ModBlocks.block_aluminium);
		add1To9(ModBlocks.block_aluminium, Ingots.ingot_aluminium);

		add9To1(Ingots.ingot_schraranium, ModBlocks.block_schraranium);
		add1To9(ModBlocks.block_schraranium, Ingots.ingot_schraranium);

		add1To9Pair(Powders.powder_paleogenite, Powders.powder_paleogenite_tiny);
		add1To9Pair(ModBlocks.block_lanthanium, Ingots.ingot_lanthanium);
		add1To9Pair(ModBlocks.block_actinium, Ingots.ingot_actinium);
		addShapelessAuto(new ItemStack(ModBlocks.block_tritium), new Object[] {new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium))});
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.tritium, 9), new Object[] {ModBlocks.block_tritium});
		add1To9Pair(ModBlocks.block_schrabidate, Ingots.ingot_schrabidate);

		add9To1(Ingots.ingot_dineutronium, ModBlocks.block_dineutronium);
		add1To9(ModBlocks.block_dineutronium, Ingots.ingot_dineutronium);
		addRecipeAuto(new ItemStack(Foods.canteen_fab, 1), new Object[] { "VMV", "MVM", "VMV", 'V', Foods.canteen_vodka, 'M', Powders.powder_magic });
		addRecipeAuto(new ItemStack(ModBlocks.fireworks, 1), new Object[] { "PPP", "PPP", "WIW", 'P', Items.PAPER, 'W', KEY_PLANKS, 'I', IRON.ingot() });

		addRecipeAuto(new ItemStack(ModItems.pellet_claws, 1), new Object[] { " X ", "X X", " XX", 'X', STEEL.plate() });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_claw, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_4gauge, 'A', ModItems.pellet_claws });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_vampire, 4), new Object[] { "ABA", "BAB", "ABA", 'B', Armory.ammo_4gauge, 'A', ModItems.toothpicks });
		addRecipeAuto(new ItemStack(Armory.ammo_4gauge_void, 4), new Object[] { " B ", "BAB", " B ", 'B', Armory.ammo_4gauge, 'A', ModItems.pellet_charged });

		addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "IEI", "ICI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', Powders.powder_power, 'C', CO.dust() });
		addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "ICI", "IEI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', Powders.powder_power, 'C', CO.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.chain, 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_beam });

		addRecipeAuto(new ItemStack(ModBlocks.spinny_light), new Object[] { " G ", "GFG", "SRS", 'G', KEY_ANYGLASS, 'F', ModItems.fuse, 'S', new ItemStack(Blocks.STONE_SLAB, 1, 0), 'R', REDSTONE.dust() });

		addRecipeAuto(new ItemStack(ModItems.jetpack_glider), new Object[] { "CSC", "DJD", "T T", 'J', ModItems.jetpack_boost, 'C', ModItems.circuit_targeting_tier5, 'D', ModItems.plate_desh, 'T', ModItems.thruster_nuclear, 'S', ModItems.motor });

		addRecipeAuto(new ItemStack(ModItems.sliding_blast_door_skin0), new Object[] { "SPS", "DPD", "SPS", 'P', Items.PAPER, 'D', "dye", 'S', STEEL.plate() });
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin1), new Object[] { ModItems.sliding_blast_door_skin0 });
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin2), new Object[] { ModItems.sliding_blast_door_skin1 });
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin0), new Object[] { ModItems.sliding_blast_door_skin2 });

		addRecipeAuto(new ItemStack(Items.NAME_TAG), new Object[] { "SB ", "BPB", " BP", 'S', Items.STRING, 'B', KEY_SLIME, 'P', Items.PAPER });
		addRecipeAuto(new ItemStack(Items.NAME_TAG), new Object[] { "SB ", "BPB", " BP", 'S', Items.STRING, 'B', ANY_TAR.any(), 'P', Items.PAPER });
		addRecipeAuto(new ItemStack(ModItems.rag, 4), new Object[] { "SW", "WS", 'S', Items.STRING, 'W', Blocks.WOOL });


		//Peas
		addRecipeAuto(new ItemStack(ModItems.peas), new Object[] { " S ", "SNS", " S ", 'S', Items.WHEAT_SEEDS, 'N', GOLD.nugget() });

		//Liquidator Suit
		addRecipeAuto(new ItemStack(ArmorSets.liquidator_helmet, 1), new Object[] { "III", "CBC", "IFI", 'I', ANY_RUBBER.ingot(), 'C', Inserts.cladding_lead, 'B', ArmorSets.hazmat_helmet_grey, 'F', ModItems.gas_mask_filter_mono });
		addRecipeAuto(new ItemStack(ArmorSets.liquidator_plate, 1), new Object[] { "ICI", "TBT", "ICI", 'I', ANY_RUBBER.ingot(), 'C', Inserts.cladding_lead, 'B', ArmorSets.hazmat_plate_grey, 'T', ModItems.gas_canister });
		addRecipeAuto(new ItemStack(ArmorSets.liquidator_legs, 1), new Object[] { "III", "CBC", "I I", 'I', ANY_RUBBER.ingot(), 'C', Inserts.cladding_lead, 'B', ArmorSets.hazmat_legs_grey });
		addRecipeAuto(new ItemStack(ArmorSets.liquidator_boots, 1), new Object[] { "ICI", "IBI", 'I', ANY_RUBBER.ingot(), 'C', Inserts.cladding_lead, 'B', ArmorSets.hazmat_boots_grey });

		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter, 1), new Object[] { "I", "F", 'F', ModItems.filter_coal, 'I', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_mono, 1), new Object[] { "ZZZ", "ZCZ", "ZZZ", 'Z', ZR.nugget(), 'C', ModItems.catalyst_clay });
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_combo, 1), new Object[] { "ZCZ", "CFC", "ZCZ", 'Z', ZR.ingot(), 'C', ModItems.catalyst_clay, 'F', ModItems.gas_mask_filter });
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_radon, 1), new Object[] { "ZCZ", "NFN", "ZCZ", 'Z', Nuggies.nugget_verticium, 'N', Powders.powder_radspice, 'C', Inserts.insert_xsapi, 'F', ModItems.gas_mask_filter_combo });
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_rag, 1), new Object[] { "I", "F", 'F', ModItems.rag_damp, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_piss, 1), new Object[] { "I", "F", 'F', ModItems.rag_piss, 'I', IRON.ingot() });
	
		addRecipeAuto(new ItemStack(Armory.jshotgun, 1), new Object[] { "LPP", "SSW", "PPD", 'S', Armory.gun_uboinik, 'P', STEEL.plate(), 'D', new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()), 'L', ModBlocks.spinny_light, 'W', Armory.mechanism_rifle_2 });

		addRecipeAuto(new ItemStack(ModBlocks.machine_armor_table, 1), new Object[] { "PPP", "TCT", "TST", 'P', STEEL.plate(), 'T', W.ingot(), 'C', Blocks.CRAFTING_TABLE, 'S', STEEL.block() });

		addShapelessAuto(new ItemStack(Ingots.ingot_steel_dusted, 1), new Object[] { STEEL.ingot(), COAL.dust() });

		addRecipeAuto(new ItemStack(ModItems.demon_core_open, 1), new Object[] { "PRP", " CS", "PRP", 'P', TI.plate(), 'R', OreDictManager.getReflector(), 'C', ModItems.man_core, 'S', ModItems.screwdriver });
		addShapelessAuto(new ItemStack(ModItems.demon_core_open, 1), new Object[] { ModItems.demon_core_closed, ModItems.screwdriver });
		addRecipeAuto(new ItemStack(ModBlocks.lamp_demon, 1), new Object[] { " D ", "S S", 'D', ModItems.demon_core_closed, 'S', STEEL.ingot() });

		addRecipeAuto(new ItemStack(Armory.crucible, 1, 3), new Object[] { "MEM", "YDY", "YCY", 'M', Ingots.ingot_meteorite_forged, 'E', EUPH.ingot(), 'Y', Billets.billet_yharonite, 'D', ModItems.demon_core_closed, 'C', Ingots.ingot_chainsteel });
		addRecipeAuto(new ItemStack(Armory.hf_sword), new Object[] { "MEM", "YDY", "YCY", 'M', ModItems.blade_meteorite, 'E', Ingots.ingot_radspice, 'Y', UNOBTAINIUM.billet(), 'D', ModItems.particle_strange, 'C', Ingots.ingot_chainsteel });
		addRecipeAuto(new ItemStack(Armory.hs_sword), new Object[] { "MEM", "YDY", "YCY", 'M', ModItems.blade_meteorite, 'E', GH336.ingot(), 'Y', Billets.billet_gh336, 'D', ModItems.particle_dark, 'C', Ingots.ingot_chainsteel });

		for(int i = 0; i < ItemWasteLong.WasteClass.values().length; i++) {
			add1To9PairSameMeta(ModItems.nuclear_waste_long, ModItems.nuclear_waste_long_tiny, i);
			add1To9PairSameMeta(ModItems.nuclear_waste_long_depleted, ModItems.nuclear_waste_long_depleted_tiny, i);
		}

		for(int i = 0; i < ItemWasteShort.WasteClass.values().length; i++) {
			add1To9PairSameMeta(ModItems.nuclear_waste_short, ModItems.nuclear_waste_short_tiny, i);
			add1To9PairSameMeta(ModItems.nuclear_waste_short_depleted, ModItems.nuclear_waste_short_depleted_tiny, i);
		}

		add1To9Pair(ModBlocks.block_fallout, ModItems.fallout);
		addRecipeAuto(new ItemStack(ModBlocks.fallout, 2), new Object[] { "##", '#', ModItems.fallout });

		addRecipeAuto(new ItemStack(ModItems.ashglasses, 1), new Object[] { "I I", "GPG", 'I', ModItems.plate_polymer, 'G', ModBlocks.glass_ash, 'P', ANY_PLASTIC.ingot() });

		addMineralSet(Nuggies.nugget_pu_mix, Ingots.ingot_pu_mix, ModBlocks.block_pu_mix);
		add1To9Pair(Ingots.ingot_neptunium_fuel, Nuggies.nugget_neptunium_fuel);

		addShapelessAuto(new ItemStack(Billets.billet_uranium, 2), new Object[] { Billets.billet_uranium_fuel, U238.billet() });
		addRecipeAuto(new ItemStack(Billets.billet_uranium, 2), new Object[] { "ABC", "BB ", "BB ", 'A', U238.billet(), 'B', U238.nugget(), 'C', U235.nugget() });

		addBillet(Billets.billet_uranium, Nuggies.nugget_uranium, U.nugget());
		addBillet(Billets.billet_u233, Nuggies.nugget_u233, U233.nugget());
		addBillet(Billets.billet_u235, Nuggies.nugget_u235, U235.nugget());
		addBillet(Billets.billet_u238, Nuggies.nugget_u238, U238.nugget());
		addBillet(Billets.billet_th232, Nuggies.nugget_th232, TH232.nugget());
		addBillet(Billets.billet_plutonium, Nuggies.nugget_plutonium, PU.nugget());
		addBillet(Billets.billet_pu238, Nuggies.nugget_pu238, PU238.nugget());
		addBillet(Billets.billet_pu239, Nuggies.nugget_pu239, PU239.nugget());
		addBillet(Billets.billet_pu240, Nuggies.nugget_pu240, PU240.nugget());
		addBillet(Billets.billet_pu241, Nuggies.nugget_pu241, PU241.nugget());
		addBillet(Billets.billet_pu_mix, Nuggies.nugget_pu_mix, PURG.nugget());
		addBillet(Billets.billet_am241, Nuggies.nugget_am241, AM241.nugget());
		addBillet(Billets.billet_am242, Nuggies.nugget_am242, AM242.nugget());
		addBillet(Billets.billet_am_mix, Nuggies.nugget_am_mix, AMRG.nugget());
		addBillet(Billets.billet_americium_fuel, Nuggies.nugget_americium_fuel);
		addBillet(Billets.billet_neptunium, Nuggies.nugget_neptunium, NP237.nugget());
		addBillet(Billets.billet_polonium, Nuggies.nugget_polonium, PO210.nugget());
		addBillet(Billets.billet_technetium, Nuggies.nugget_technetium, TC99.nugget());
		addBillet(Billets.billet_au198, Nuggies.nugget_au198, AU198.nugget());
		addBillet(Billets.billet_schrabidium, Nuggies.nugget_schrabidium, SA326.nugget());
		addBillet(Billets.billet_solinium, Nuggies.nugget_solinium, SA327.nugget());
		addBillet(Billets.billet_uranium_fuel, Nuggies.nugget_uranium_fuel);
		addBillet(Billets.billet_thorium_fuel, Nuggies.nugget_thorium_fuel);
		addBillet(Billets.billet_plutonium_fuel, Nuggies.nugget_plutonium_fuel);
		addBillet(Billets.billet_neptunium_fuel, Nuggies.nugget_neptunium_fuel);
		addBillet(Billets.billet_mox_fuel, Nuggies.nugget_mox_fuel);
		addBillet(Billets.billet_les, Nuggies.nugget_les);
		addBillet(Billets.billet_schrabidium_fuel, Nuggies.nugget_schrabidium_fuel);
		addBillet(Billets.billet_hes, Nuggies.nugget_hes);
		addBillet(Billets.billet_australium, Nuggies.nugget_australium, AUSTRALIUM.nugget());
		addBillet(Billets.billet_australium_lesser, Nuggies.nugget_australium_lesser);
		addBillet(Billets.billet_australium_greater, Nuggies.nugget_australium_greater);
		addBillet(Billets.billet_ac227, Nuggies.nugget_ac227, AC227.nugget());
		addBillet(Billets.billet_bismuth, Nuggies.nugget_bismuth, ANY_BISMOID.nugget());
		addBillet(Billets.billet_pb209, Nuggies.nugget_pb209, PB209.nugget());
		addBillet(Billets.billet_ra226, Nuggies.nugget_ra226, RA226.nugget());
		addBillet(Billets.billet_sr90, Nuggies.nugget_sr90, SR90.nugget());
		addBillet(Billets.billet_co60, Nuggies.nugget_co60, CO60.nugget());
		addBillet(Billets.billet_ra226, Nuggies.nugget_ra226, RA226.nugget());
		addBillet(Billets.billet_gh336, Nuggies.nugget_gh336, GH336.nugget());
		addBillet(Billets.billet_beryllium, Nuggies.nugget_beryllium, BE.nugget());
		addBillet(Billets.billet_zirconium, Nuggies.nugget_zirconium, ZR.nugget());
		addBillet(Billets.billet_bismuth, Nuggies.nugget_bismuth, ANY_BISMOID.nugget());
		addBillet(Billets.billet_nuclear_waste, ModItems.nuclear_waste_tiny);

		addBilletByIngot(Billets.billet_uranium, Ingots.ingot_uranium, U.ingot());
		addBilletByIngot(Billets.billet_u233, Ingots.ingot_u233, U233.ingot());
		addBilletByIngot(Billets.billet_u235, Ingots.ingot_u235, U235.ingot());
		addBilletByIngot(Billets.billet_u238, Ingots.ingot_u238, U238.ingot());
		addBilletByIngot(Billets.billet_th232, Ingots.ingot_th232, TH232.ingot());
		addBilletByIngot(Billets.billet_plutonium, Ingots.ingot_plutonium, PU.ingot());
		addBilletByIngot(Billets.billet_pu238, Ingots.ingot_pu238, PU238.ingot());
		addBilletByIngot(Billets.billet_pu239, Ingots.ingot_pu239, PU239.ingot());
		addBilletByIngot(Billets.billet_pu240, Ingots.ingot_pu240, PU240.ingot());
		addBilletByIngot(Billets.billet_pu241, Ingots.ingot_pu241, PU241.ingot());
		addBilletByIngot(Billets.billet_pu_mix, Ingots.ingot_pu_mix, PURG.ingot());
		addBilletByIngot(Billets.billet_am241, Ingots.ingot_am241, AM241.ingot());
		addBilletByIngot(Billets.billet_am242, Ingots.ingot_am242, AM242.ingot());
		addBilletByIngot(Billets.billet_am_mix, Ingots.ingot_am_mix, AMRG.ingot());
		addBilletByIngot(Billets.billet_americium_fuel, Ingots.ingot_americium_fuel);
		addBilletByIngot(Billets.billet_neptunium, Ingots.ingot_neptunium, NP237.ingot());
		addBilletByIngot(Billets.billet_polonium, Ingots.ingot_polonium, PO210.ingot());
		addBilletByIngot(Billets.billet_technetium, Ingots.ingot_technetium, TC99.ingot());
		addBilletByIngot(Billets.billet_au198, Ingots.ingot_au198, AU198.ingot());
		addBilletByIngot(Billets.billet_schrabidium, Ingots.ingot_schrabidium, SA326.ingot());
		addBilletByIngot(Billets.billet_solinium, Ingots.ingot_solinium, SA327.ingot());
		addBilletByIngot(Billets.billet_uranium_fuel, Ingots.ingot_uranium_fuel);
		addBilletByIngot(Billets.billet_thorium_fuel, Ingots.ingot_thorium_fuel);
		addBilletByIngot(Billets.billet_plutonium_fuel, Ingots.ingot_plutonium_fuel);
		addBilletByIngot(Billets.billet_neptunium_fuel, Ingots.ingot_neptunium_fuel);
		addBilletByIngot(Billets.billet_mox_fuel, Ingots.ingot_mox_fuel);
		addBilletByIngot(Billets.billet_les, Ingots.ingot_les);
		addBilletByIngot(Billets.billet_schrabidium_fuel, Ingots.ingot_schrabidium_fuel);
		addBilletByIngot(Billets.billet_hes, Ingots.ingot_hes);
		addBilletByIngot(Billets.billet_australium, Ingots.ingot_australium, AUSTRALIUM.ingot());
		addBilletByIngot(Billets.billet_ac227, Ingots.ingot_ac227, AC227.ingot());
		addBilletByIngot(Billets.billet_bismuth, Ingots.ingot_bismuth, ANY_BISMOID.ingot());
		addBilletByIngot(Billets.billet_pb209, Ingots.ingot_pb209, PB209.ingot());
		addBilletByIngot(Billets.billet_ra226, Ingots.ingot_ra226, RA226.ingot());
		addBilletByIngot(Billets.billet_sr90, Ingots.ingot_sr90, SR90.ingot());
		addBilletByIngot(Billets.billet_co60, Ingots.ingot_co60, CO60.ingot());
		addBilletByIngot(Billets.billet_ra226, Ingots.ingot_ra226, RA226.ingot());
		addBilletByIngot(Billets.billet_gh336, Ingots.ingot_gh336, GH336.ingot());
		addBilletByIngot(Billets.billet_beryllium, Ingots.ingot_beryllium, BE.ingot());
		addBilletByIngot(Billets.billet_zirconium, Ingots.ingot_zirconium, ZR.ingot());
		addBilletByIngot(Billets.billet_bismuth, Ingots.ingot_bismuth, ANY_BISMOID.ingot());
		addBilletByIngot(Billets.billet_nuclear_waste, ModItems.nuclear_waste);


		addShapelessAuto(new ItemStack(ModBlocks.block_thorium_fuel, 3), new Object[] { TH232.block(), TH232.block(), U233.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_thorium_fuel, 3), new Object[] { TH232.ingot(), TH232.ingot(), U233.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_thorium_fuel, 3), new Object[] { TH232.billet(), TH232.billet(), U233.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_thorium_fuel, 1), new Object[] { TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), U233.nugget(), U233.nugget() });
		
		addShapelessAuto(new ItemStack(ModBlocks.block_uranium_fuel, 6), new Object[] { U238.block(), U238.block(), U238.block(), U238.block(), U238.block(), U235.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_uranium_fuel, 6), new Object[] { U238.ingot(), U238.ingot(), U238.ingot(), U238.ingot(), U238.ingot(), U235.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_uranium_fuel, 6), new Object[] { U238.billet(), U238.billet(), U238.billet(), U238.billet(), U238.billet(), U235.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_uranium_fuel, 1), new Object[] { U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U235.nugget() });
		
		addShapelessAuto(new ItemStack(ModBlocks.block_plutonium_fuel, 3), new Object[] { U238.block(), PURG.block(), PURG.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_plutonium_fuel, 3), new Object[] { U238.ingot(), PURG.ingot(), PURG.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_plutonium_fuel, 3), new Object[] { U238.billet(), PURG.billet(), PURG.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_plutonium_fuel, 1), new Object[] { PURG.nugget(), PURG.nugget(), PURG.nugget(), PURG.nugget(), U238.nugget(), U238.nugget() });
		
		addShapelessAuto(new ItemStack(ModBlocks.block_pu_mix, 3), new Object[] { PU239.block(), PU239.block(), PU240.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_pu_mix, 3), new Object[] { PU239.ingot(), PU239.ingot(), PU240.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_pu_mix, 3), new Object[] { PU239.billet(), PU239.billet(), PU240.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_pu_mix, 1), new Object[] { PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU240.nugget(), PU240.nugget() });
		
		addShapelessAuto(new ItemStack(Ingots.ingot_americium_fuel, 3), new Object[] { U238.ingot(), U238.ingot(), AMRG.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_americium_fuel, 3), new Object[] { U238.billet(), U238.billet(), AMRG.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_americium_fuel, 1), new Object[] { U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), AMRG.nugget(), AMRG.nugget() });
		
		addShapelessAuto(new ItemStack(Ingots.ingot_am_mix, 3), new Object[] { AM241.ingot(), AM242.ingot(), AM242.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_am_mix, 3), new Object[] { AM241.billet(), AM242.billet(), AM242.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_am_mix, 1), new Object[] { AM241.nugget(), AM241.nugget(), AM242.nugget(), AM242.nugget(), AM242.nugget(), AM242.nugget() });
		
		addShapelessAuto(new ItemStack(Ingots.ingot_neptunium_fuel, 27), new Object[] { U238.block(), U238.block(), NP237.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_neptunium_fuel, 3), new Object[] { U238.ingot(), U238.ingot(), NP237.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_neptunium_fuel, 3), new Object[] { U238.billet(), U238.billet(), NP237.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_neptunium_fuel, 1), new Object[] { U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), NP237.nugget(), NP237.nugget() });
		
		addShapelessAuto(new ItemStack(ModBlocks.block_mox_fuel, 3), new Object[] { U238.block(), U235.block(), PURG.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_mox_fuel, 3), new Object[] { U238.ingot(), U235.ingot(), PURG.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_mox_fuel, 3), new Object[] { U238.billet(), U235.billet(), PURG.billet() });
		addShapelessAuto(new ItemStack(Billets.billet_mox_fuel, 1), new Object[] { U238.nugget(), U238.nugget(), U235.nugget(), U235.nugget(), PURG.nugget(), PURG.nugget() });
		
		addShapelessAuto(new ItemStack(Billets.billet_les, 54), new Object[] { SA326.block(), NP237.block(), NP237.block(), NP237.block(), NP237.block(), BE.block(), BE.block(), BE.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_les, 9), new Object[] { SA326.ingot(), NP237.ingot(), NP237.ingot(), NP237.ingot(), NP237.ingot(), BE.ingot(), BE.ingot(), BE.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_les, 9), new Object[] { SA326.billet(), NP237.billet(), NP237.billet(), NP237.billet(), NP237.billet(), BE.billet(), BE.billet(), BE.billet() });
		addShapelessAuto(new ItemStack(Nuggies.nugget_les, 9), new Object[] { SA326.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), BE.nugget(), BE.nugget(), BE.nugget() });
		
		addShapelessAuto(new ItemStack(ModBlocks.block_schrabidium_fuel, 3), new Object[] { SA326.block(), NP237.block(), BE.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_schrabidium_fuel, 3), new Object[] { SA326.ingot(), NP237.ingot(), BE.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_schrabidium_fuel, 3), new Object[] { SA326.billet(), NP237.billet(), BE.billet() });
		addShapelessAuto(new ItemStack(Nuggies.nugget_schrabidium_fuel, 3), new Object[] { SA326.nugget(), NP237.nugget(), BE.nugget() });
		
		addShapelessAuto(new ItemStack(Billets.billet_hes, 54), new Object[] { SA326.block(), SA326.block(), NP237.block(), BE.block() });
		addShapelessAuto(new ItemStack(Ingots.ingot_hes, 4), new Object[] { SA326.ingot(), SA326.ingot(), NP237.ingot(), BE.ingot() });
		addShapelessAuto(new ItemStack(Billets.billet_hes, 4), new Object[] { SA326.billet(), SA326.billet(), NP237.billet(), BE.billet() });
		addShapelessAuto(new ItemStack(Nuggies.nugget_hes, 4), new Object[] { SA326.nugget(), SA326.nugget(), NP237.nugget(), BE.nugget() });
		
		addShapelessAuto(new ItemStack(Billets.billet_po210be, 1), new Object[] { PO210.nugget(), PO210.nugget(), PO210.nugget(), BE.nugget(), BE.nugget(), BE.nugget() });
		addShapelessAuto(new ItemStack(Billets.billet_pu238be, 1), new Object[] { PU238.nugget(), PU238.nugget(), PU238.nugget(), BE.nugget(), BE.nugget(), BE.nugget() });
		addShapelessAuto(new ItemStack(Billets.billet_ra226be, 1), new Object[] { RA226.nugget(), RA226.nugget(), RA226.nugget(), BE.nugget(), BE.nugget(), BE.nugget() });

		addShapelessAuto(new ItemStack(Billets.billet_uranium_fuel, 9), new Object[] { U238.ingot(), U238.ingot(), U238.ingot(), U238.ingot(), U238.ingot(), U235.ingot() });

		addShapelessAuto(new ItemStack(Ingots.ingot_uranium, 2), new Object[] { U.billet(), U.billet(), U.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_u233, 2), new Object[] { U233.billet(), U233.billet(), U233.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_u235, 2), new Object[] { U235.billet(), U235.billet(), U235.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_u238, 2), new Object[] { U238.billet(), U238.billet(), U238.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_plutonium, 2), new Object[] { PU.billet(), PU.billet(), PU.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_pu238, 2), new Object[] { PU238.billet(), PU238.billet(), PU238.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_pu239, 2), new Object[] { PU239.billet(), PU239.billet(), PU239.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_pu240, 2), new Object[] { PU240.billet(), PU240.billet(), PU240.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_pu241, 2), new Object[] { PU241.billet(), PU241.billet(), PU241.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_pu_mix, 2), new Object[] { PURG.billet(), PURG.billet(), PURG.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_am241, 2), new Object[] { AM241.billet(), AM241.billet(), AM241.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_am242, 2), new Object[] { AM242.billet(), AM242.billet(), AM242.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_am_mix, 2), new Object[] { AMRG.billet(), AMRG.billet(), AMRG.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_uranium_fuel, 2), new Object[] { Billets.billet_uranium_fuel, Billets.billet_uranium_fuel, Billets.billet_uranium_fuel });
		addShapelessAuto(new ItemStack(Ingots.ingot_plutonium_fuel, 2), new Object[] { Billets.billet_plutonium_fuel, Billets.billet_plutonium_fuel, Billets.billet_plutonium_fuel });
		addShapelessAuto(new ItemStack(Ingots.ingot_americium_fuel, 2), new Object[] { Billets.billet_americium_fuel, Billets.billet_americium_fuel, Billets.billet_americium_fuel });
		addShapelessAuto(new ItemStack(Ingots.ingot_mox_fuel, 2), new Object[] { Billets.billet_mox_fuel, Billets.billet_mox_fuel, Billets.billet_mox_fuel });
		addShapelessAuto(new ItemStack(Ingots.ingot_neptunium, 2), new Object[] { NP237.billet(), NP237.billet(), NP237.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_neptunium_fuel, 2), new Object[] { Billets.billet_neptunium_fuel, Billets.billet_neptunium_fuel, Billets.billet_neptunium_fuel });
		addShapelessAuto(new ItemStack(Ingots.ingot_polonium, 2), new Object[] { PO210.billet(), PO210.billet(), PO210.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_technetium, 2), new Object[] {TC99.billet(),TC99.billet(),TC99.billet() });
		addShapelessAuto(new ItemStack(Nuggies.nugget_schrabidium, 2), new Object[] { SA326.billet(), SA326.billet(), SA326.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_solinium, 2), new Object[] { SA327.billet(), SA327.billet(), SA327.billet() });
		addShapelessAuto(new ItemStack(Ingots.ingot_les, 2), new Object[] { Billets.billet_les, Billets.billet_les, Billets.billet_les });
		addShapelessAuto(new ItemStack(Ingots.ingot_schrabidium_fuel, 2), new Object[] {Billets.billet_schrabidium_fuel,Billets.billet_schrabidium_fuel,Billets.billet_schrabidium_fuel });
		addShapelessAuto(new ItemStack(Ingots.ingot_hes, 2), new Object[] { Billets.billet_hes, Billets.billet_hes, Billets.billet_hes });

		addShapelessAuto(new ItemStack(Billets.billet_balefire_gold, 1), new Object[] { AU198.billet(), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)), ModItems.pellet_charged });
		addShapelessAuto(new ItemStack(Billets.billet_flashlead, 2), new Object[] { Billets.billet_balefire_gold, PB209.billet(), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)) });
		addShapelessAuto(new ItemStack(Billets.billet_flashlead, 2), new Object[] { AU198.billet(), PB209.billet(), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)), new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)), ModItems.pellet_charged });

		addRecipeAuto(new ItemStack(Armory.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', PB.plate(), 'G', ANY_SMOKELESS.dust(), 'C', CU.ingot() });

		addShapelessAuto(new ItemStack(ModBlocks.ladder_red), new Object[] { "dyeRed", ModBlocks.ladder_steel });
		addShapelessAuto(new ItemStack(ModBlocks.ladder_red_top), new Object[] { ModBlocks.ladder_red });
		addRecipeAuto(new ItemStack(ModBlocks.railing_normal), new Object[] { "   ", "SSS", "S S", 'S', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModBlocks.railing_bend), new Object[] { "   ", "S  ", " S ", 'S', ModBlocks.railing_normal });
		addRecipeAuto(new ItemStack(ModBlocks.railing_end_self, 2), new Object[] { "   ", " SS", "   ", 'S', ModBlocks.railing_normal });
		addRecipeAuto(new ItemStack(ModBlocks.railing_end_floor, 2), new Object[] { "   ", " S ", " S ", 'S', ModBlocks.railing_normal });
		addShapelessAuto(new ItemStack(ModBlocks.railing_end_flipped_self), new Object[] { ModBlocks.railing_end_self });
		addShapelessAuto(new ItemStack(ModBlocks.railing_end_flipped_floor), new Object[] { ModBlocks.railing_end_floor });

		addRecipeAuto(new ItemStack(Armory.assembly_nuke, 1), new Object[] { " WP", "SEP", " WP", 'W', ModItems.wire_aluminium, 'P', STEEL.plate(), 'S', ModItems.hull_small_steel, 'E', ANY_HIGHEXPLOSIVE.ingot() })	;
		
		//Mini Nuke
		addRecipeAuto(new ItemStack(Armory.ammo_nuke, 1), new Object[] { "P", "S", "P", 'P', PU239.nugget(), 'S', Armory.assembly_nuke });
		addRecipeAuto(new ItemStack(Armory.ammo_nuke_low, 1), new Object[] { "P", "S", 'P', PU239.nugget(), 'S', Armory.assembly_nuke });
		addRecipeAuto(new ItemStack(Armory.ammo_nuke_high, 1), new Object[] { "PPP", "PSP", "PPP", 'P', PU239.nugget(), 'S', Armory.assembly_nuke });
		addRecipeAuto(new ItemStack(Armory.ammo_nuke_tots, 1), new Object[] { "PPP", "PIP", "PPP", 'P', ModItems.pellet_cluster, 'I', PU239.ingot() });
		addRecipeAuto(new ItemStack(Armory.ammo_nuke_safe, 1), new Object[] { "G", "N", 'G', Items.GLOWSTONE_DUST, 'N', Armory.ammo_nuke_low });
		addRecipeAuto(new ItemStack(Armory.ammo_nuke_pumpkin, 1), new Object[] { " T ", "TST", " T ", 'T', Blocks.TNT, 'S', Armory.assembly_nuke });

		//MIRV recycling
		addShapelessAuto(new ItemStack(Armory.ammo_nuke, 6), new Object[] { Armory.ammo_mirv });
		addShapelessAuto(new ItemStack(Armory.ammo_nuke_low, 6), new Object[] { Armory.ammo_mirv_low });
		addShapelessAuto(new ItemStack(Armory.ammo_nuke_high, 6), new Object[] { Armory.ammo_mirv_high });
		addShapelessAuto(new ItemStack(Armory.ammo_nuke_safe, 6), new Object[] { Armory.ammo_mirv_safe });

		//MIRV
		addRecipeAuto(new ItemStack(Armory.ammo_mirv, 1), new Object[] { "NNN", "CDS", "NNN", 'N', Armory.ammo_nuke, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		addRecipeAuto(new ItemStack(Armory.ammo_mirv_low, 1), new Object[] { "NNN", "CDS", "NNN", 'N', Armory.ammo_nuke_low, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		addRecipeAuto(new ItemStack(Armory.ammo_mirv_high, 1), new Object[] { "NNN", "CDS", "NNN", 'N', Armory.ammo_nuke_high, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		addRecipeAuto(new ItemStack(Armory.ammo_mirv_safe, 1), new Object[] { "NNN", "CDS", "NNN", 'N', Armory.ammo_nuke_safe, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		//since the milk part of the recipe isn't realy present in the MIRV's effect, it might as well be replaced with something more sensible, i.e. duct tape
		addRecipeAuto(new ItemStack(Armory.ammo_mirv_special, 1), new Object[] { "CBC", "MCM", "CBC", 'C', Foods.canned_jizz, 'B', Armory.gun_bf_ammo, 'M', Armory.ammo_mirv });

		addShapelessAuto(new ItemStack(ModItems.ball_fireclay, 4), new Object[] { Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, AL.dust()});
		addShapelessAuto(new ItemStack(ModItems.ball_fireclay, 4), new Object[] { Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, AL.ore()});

		add1To9Pair(ModBlocks.block_semtex, ModItems.ingot_semtex);

		if(!GeneralConfig.enable528) {
			addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core, 1), new Object[] { "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.IRON_BARS, 'C', ModItems.circuit_targeting_tier3, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			addRecipeAuto(new ItemStack(ModBlocks.struct_soyuz_core, 1), new Object[] { "CUC", "TST", "TBT", 'C', ModItems.circuit_targeting_tier4, 'U', Upgrades.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery });
			addRecipeAuto(new ItemStack(ModItems.reactor_sensor, 1), new Object[] { "WPW", "CMC", "PPP", 'W', ModItems.wire_tungsten, 'P', PB.plate(), 'C', ModItems.circuit_targeting_tier3, 'M', ModItems.magnetron });
			addRecipeAuto(new ItemStack(ModBlocks.reactor_ejector, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', PB.plate(), 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch });
			addRecipeAuto(new ItemStack(ModBlocks.reactor_inserter, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', CU.plate(), 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_console, 1), new Object[] { "BBB", "DGD", "DCD", 'B', B.ingot(), 'D', ModBlocks.deco_rbmk, 'G', KEY_ANYPANE, 'C', ModItems.circuit_targeting_tier3 });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_crane_console, 1), new Object[] { "BCN", "DDD", 'B', B.ingot(), 'D', ModBlocks.deco_rbmk, 'C', ModItems.circuit_targeting_tier3, 'N', Armory.ammo_nuke });
			
			addRecipeAuto(new ItemStack(ModBlocks.hadron_core, 1), new Object[] { "CCC", "DSD", "CCC", 'C', ModBlocks.hadron_coil_alloy, 'D', ModBlocks.hadron_diode, 'S', ModItems.circuit_schrabidium });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod, 1), new Object[] { "C", "R", "C", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_rod, 'B', ANY_BISMOID.nugget() });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_boiler, 1), new Object[] { "CPC", "CRC", "CPC", 'C', ModItems.board_copper, 'P', ModItems.pipes_steel, 'R', ModBlocks.rbmk_blank });
		}

		if(GeneralConfig.enableBabyMode) {
			addShapelessAuto(new ItemStack(ModItems.cordite, 3), new Object[] { ModItems.ballistite, Items.GUNPOWDER, new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) });
			addShapelessAuto(new ItemStack(ModItems.ingot_semtex, 3), new Object[] { Items.SLIME_BALL, Blocks.TNT, KNO.dust() });
			addShapelessAuto(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.oil)), Items.REDSTONE });

			addShapelessAuto(new ItemStack(ModBlocks.ore_uranium, 1), new Object[] { ModBlocks.ore_uranium_scorched, Items.WATER_BUCKET });
			addRecipeAuto(new ItemStack(ModBlocks.ore_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_uranium_scorched, 'B', Items.WATER_BUCKET });
			addShapelessAuto(new ItemStack(ModBlocks.ore_nether_uranium, 1), new Object[] { ModBlocks.ore_nether_uranium_scorched, Items.WATER_BUCKET });
			addRecipeAuto(new ItemStack(ModBlocks.ore_nether_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_nether_uranium_scorched, 'B', Items.WATER_BUCKET });
			addShapelessAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 1), new Object[] { ModBlocks.ore_gneiss_uranium_scorched, Items.WATER_BUCKET });
			addRecipeAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_gneiss_uranium_scorched, 'B', Items.WATER_BUCKET });

			addRecipeAuto(new ItemStack(ModItems.plate_iron, 4), new Object[] { "##", "##", '#', IRON.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_gold, 4), new Object[] { "##", "##", '#', GOLD.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_aluminium, 4), new Object[] { "##", "##", '#', AL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_titanium, 4), new Object[] { "##", "##", '#', TI.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_copper, 4), new Object[] { "##", "##", '#', CU.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_lead, 4), new Object[] { "##", "##", '#', PB.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_steel, 4), new Object[] { "##", "##", '#', STEEL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_schrabidium, 4), new Object[] { "##", "##", '#', SA326.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_advanced_alloy, 4), new Object[] { "##", "##", '#', ALLOY.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_saturnite, 4), new Object[] { "##", "##", '#', BIGMT.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_combine_steel, 4), new Object[] { "##", "##", '#', CMB.ingot() });
			addRecipeAuto(new ItemStack(ModItems.neutron_reflector, 4), new Object[] { "##", "##", '#', W.ingot() });

			addRecipeAuto(new ItemStack(ModItems.wire_aluminium, 16), new Object[] { "###", '#', AL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_copper, 16), new Object[] { "###", '#', CU.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_tungsten, 16), new Object[] { "###", '#', W.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_red_copper, 16), new Object[] { "###", '#', MINGRADE.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_advanced_alloy, 16), new Object[] { "###", '#', ALLOY.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_gold, 16), new Object[] { "###", '#', GOLD.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_schrabidium, 16), new Object[] { "###", '#', SA326.ingot() });

			addRecipeAuto(new ItemStack(ModItems.book_of_), new Object[] { "BGB", "GAG", "BGB", 'B', ModItems.egg_balefire_shard, 'G', GOLD.ingot(), 'A', Items.BOOK });
		}

		addShapelessAuto(new ItemStack(ModItems.drillbit_dnt_diamond, 1), new Object[] { ModItems.drillbit_dnt, new ItemStack(ModItems.ore_bedrock, 1, 3) });

		addRecipeAuto(new ItemStack(LeafiaRods.leafRod,4),new Object[] {"O","I","O", 'O',ZR.billet(),'I',ZR.nugget()});
		for (ItemLeafiaRod rod : ItemLeafiaRod.fromResourceMap.values()) {
			addShapelessAuto(new ItemStack(rod, 1), new Object[] { LeafiaRods.leafRod, rod.baseItem });
			addShapelessAuto(new ItemStack(rod.baseItem, 1), new Object[] { rod });
		}
	}

	public static void addSmelting(){
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_thorium), new ItemStack(Ingots.ingot_th232), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium), new ItemStack(Ingots.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium_scorched), new ItemStack(Ingots.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_uranium), new ItemStack(Ingots.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_uranium_scorched), new ItemStack(Ingots.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_plutonium), new ItemStack(Ingots.ingot_plutonium), 24.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_titanium), new ItemStack(Ingots.ingot_titanium), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_copper), new ItemStack(Ingots.ingot_copper), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_tungsten), new ItemStack(Ingots.ingot_tungsten), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_tungsten), new ItemStack(Ingots.ingot_tungsten), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_aluminium), new ItemStack(Ingots.ingot_aluminium), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_lead), new ItemStack(Ingots.ingot_lead), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_beryllium), new ItemStack(Ingots.ingot_beryllium), 2.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_schrabidium), new ItemStack(Ingots.ingot_schrabidium), 128.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_schrabidium), new ItemStack(Ingots.ingot_schrabidium), 256.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_uranium), new ItemStack(Ingots.ingot_uranium, 2), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_thorium), new ItemStack(Ingots.ingot_th232, 2), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_titanium), new ItemStack(Ingots.ingot_titanium, 3), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_copper), new ItemStack(Ingots.ingot_copper, 3), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_tungsten), new ItemStack(Ingots.ingot_tungsten, 3), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_aluminium), new ItemStack(Ingots.ingot_aluminium, 3), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_lead), new ItemStack(Ingots.ingot_lead, 3), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_lithium), new ItemStack(ModItems.lithium), 20.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_starmetal), new ItemStack(Ingots.ingot_starmetal), 50.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_cobalt), new ItemStack(Ingots.ingot_cobalt), 2.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_cobalt), new ItemStack(Ingots.ingot_cobalt), 2.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_cinnebar), new ItemStack(ModItems.cinnebar), 1.0F);

		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_iron), new ItemStack(Items.IRON_INGOT), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_gold), new ItemStack(Items.GOLD_INGOT), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium), new ItemStack(Ingots.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium_scorched), new ItemStack(Ingots.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_copper), new ItemStack(Ingots.ingot_copper), 5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_lithium), new ItemStack(ModItems.lithium), 10F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_schrabidium), new ItemStack(Ingots.ingot_schrabidium), 256.0F);
		;

		GameRegistry.addSmelting(Armory.casing_357, new ItemStack(Ingots.ingot_copper), 0.1F);
		GameRegistry.addSmelting(Armory.casing_44, new ItemStack(Ingots.ingot_copper), 0.1F);
		GameRegistry.addSmelting(Armory.casing_9, new ItemStack(Ingots.ingot_copper), 0.1F);
		GameRegistry.addSmelting(Armory.casing_50, new ItemStack(Ingots.ingot_copper), 0.1F);
		GameRegistry.addSmelting(Armory.casing_buckshot, new ItemStack(Ingots.ingot_copper), 0.1F);

		GameRegistry.addSmelting(ModItems.circuit_schrabidium, new ItemStack(ModItems.circuit_gold, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_gold, new ItemStack(ModItems.circuit_red_copper, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_red_copper, new ItemStack(ModItems.circuit_copper, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_copper, new ItemStack(ModItems.circuit_aluminium, 1), 1.0F);

		GameRegistry.addSmelting(Powders.powder_australium, new ItemStack(Ingots.ingot_australium), 5.0F);
		GameRegistry.addSmelting(Powders.powder_weidanium, new ItemStack(Ingots.ingot_weidanium), 5.0F);
		GameRegistry.addSmelting(Powders.powder_reiium, new ItemStack(Ingots.ingot_reiium), 5.0F);
		GameRegistry.addSmelting(Powders.powder_unobtainium, new ItemStack(Ingots.ingot_unobtainium), 5.0F);
		GameRegistry.addSmelting(Powders.powder_daffergon, new ItemStack(Ingots.ingot_daffergon), 5.0F);
		GameRegistry.addSmelting(Powders.powder_verticium, new ItemStack(Ingots.ingot_verticium), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_australium), new ItemStack(Nuggies.nugget_australium), 2.5F);

		GameRegistry.addSmelting(Powders.powder_radspice, new ItemStack(Ingots.ingot_radspice), 1.0F);
		GameRegistry.addSmelting(Powders.powder_lead, new ItemStack(Ingots.ingot_lead), 1.0F);
		GameRegistry.addSmelting(Powders.powder_neptunium, new ItemStack(Ingots.ingot_neptunium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_polonium, new ItemStack(Ingots.ingot_polonium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_schrabidium, new ItemStack(Ingots.ingot_schrabidium), 5.0F);
		GameRegistry.addSmelting(Powders.powder_aluminium, new ItemStack(Ingots.ingot_aluminium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_beryllium, new ItemStack(Ingots.ingot_beryllium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_copper, new ItemStack(Ingots.ingot_copper), 1.0F);
		GameRegistry.addSmelting(Powders.powder_gold, new ItemStack(Items.GOLD_INGOT), 1.0F);
		GameRegistry.addSmelting(Powders.powder_iron, new ItemStack(Items.IRON_INGOT), 1.0F);
		GameRegistry.addSmelting(Powders.powder_titanium, new ItemStack(Ingots.ingot_titanium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_tungsten, new ItemStack(Ingots.ingot_tungsten), 1.0F);
		GameRegistry.addSmelting(Powders.powder_uranium, new ItemStack(Ingots.ingot_uranium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_thorium, new ItemStack(Ingots.ingot_th232), 1.0F);
		GameRegistry.addSmelting(Powders.powder_plutonium, new ItemStack(Ingots.ingot_plutonium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_advanced_alloy, new ItemStack(Ingots.ingot_advanced_alloy), 1.0F);
		GameRegistry.addSmelting(Powders.powder_combine_steel, new ItemStack(Ingots.ingot_combine_steel), 1.0F);
		GameRegistry.addSmelting(Powders.powder_magnetized_tungsten, new ItemStack(Ingots.ingot_magnetized_tungsten), 1.0F);
		GameRegistry.addSmelting(Powders.powder_red_copper, new ItemStack(Ingots.ingot_red_copper), 1.0F);
		GameRegistry.addSmelting(Powders.powder_steel, new ItemStack(Ingots.ingot_steel), 1.0F);
		GameRegistry.addSmelting(Powders.powder_lithium, new ItemStack(ModItems.lithium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_dura_steel, new ItemStack(Ingots.ingot_dura_steel), 1.0F);
		GameRegistry.addSmelting(Powders.powder_polymer, new ItemStack(Ingots.ingot_polymer), 1.0F);
		GameRegistry.addSmelting(Powders.powder_bakelite, new ItemStack(Ingots.ingot_bakelite), 1.0F);
		GameRegistry.addSmelting(Powders.powder_lanthanium, new ItemStack(Ingots.ingot_lanthanium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_actinium, new ItemStack(Ingots.ingot_actinium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_boron, new ItemStack(Ingots.ingot_boron), 1.0F);
		GameRegistry.addSmelting(Powders.powder_desh, new ItemStack(Ingots.ingot_desh), 1.0F);
		GameRegistry.addSmelting(Powders.powder_cobalt, new ItemStack(Ingots.ingot_cobalt), 1.0F);
		GameRegistry.addSmelting(Powders.powder_schrabidate, new ItemStack(Ingots.ingot_schrabidate), 5.0F);
		GameRegistry.addSmelting(Powders.powder_asbestos, new ItemStack(Ingots.ingot_asbestos), 1.0F);
		GameRegistry.addSmelting(Powders.powder_cadmium, new ItemStack(Ingots.ingot_cadmium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_bismuth, new ItemStack(Ingots.ingot_bismuth), 1.0F);
		GameRegistry.addSmelting(Powders.powder_zirconium, new ItemStack(Ingots.ingot_zirconium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_tcalloy, new ItemStack(Ingots.ingot_tcalloy), 1.0F);
		GameRegistry.addSmelting(Powders.powder_cdalloy, new ItemStack(Ingots.ingot_cdalloy), 1.0F);
		GameRegistry.addSmelting(Powders.powder_au198, new ItemStack(Ingots.ingot_au198), 1.0F);
		GameRegistry.addSmelting(Powders.powder_tantalium, new ItemStack(Ingots.ingot_tantalium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_astatine, new ItemStack(Ingots.ingot_astatine), 1.0F);
		GameRegistry.addSmelting(Powders.powder_ac227, new ItemStack(Ingots.ingot_ac227), 1.0F);
		GameRegistry.addSmelting(Powders.powder_co60, new ItemStack(Ingots.ingot_co60), 1.0F);
		GameRegistry.addSmelting(Powders.powder_bromine, new ItemStack(Ingots.ingot_bromine), 1.0F);
		GameRegistry.addSmelting(Powders.powder_cerium, new ItemStack(Ingots.ingot_cerium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_caesium, new ItemStack(Ingots.ingot_caesium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_iodine, new ItemStack(Ingots.ingot_iodine), 1.0F);
		GameRegistry.addSmelting(Powders.powder_i131, new ItemStack(Ingots.ingot_i131), 1.0F);
		GameRegistry.addSmelting(Powders.powder_neodymium, new ItemStack(Ingots.ingot_neodymium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_pb209, new ItemStack(Ingots.ingot_pb209), 1.0F);
		GameRegistry.addSmelting(Powders.powder_ra226, new ItemStack(Ingots.ingot_ra226), 1.0F);
		GameRegistry.addSmelting(Powders.powder_strontium, new ItemStack(Ingots.ingot_strontium), 1.0F);
		GameRegistry.addSmelting(Powders.powder_sr90, new ItemStack(Ingots.ingot_sr90), 1.0F);
		GameRegistry.addSmelting(Powders.powder_tennessine, new ItemStack(Ingots.ingot_tennessine), 1.0F);
		GameRegistry.addSmelting(Powders.powder_niobium, new ItemStack(Ingots.ingot_niobium), 1.0F);
		GameRegistry.addSmelting(ModItems.ball_resin, new ItemStack(Ingots.ingot_biorubber), 0.1F);

		GameRegistry.addSmelting(Powders.powder_cobalt_tiny, new ItemStack(Nuggies.nugget_cobalt), 0.1F);
		GameRegistry.addSmelting(Powders.powder_co60_tiny, new ItemStack(Nuggies.nugget_co60), 0.1F);
		GameRegistry.addSmelting(Powders.powder_pb209_tiny, new ItemStack(Nuggies.nugget_pb209), 0.1F);
		GameRegistry.addSmelting(Powders.powder_sr90_tiny, new ItemStack(Nuggies.nugget_sr90), 0.1F);
		GameRegistry.addSmelting(Powders.powder_au198_tiny, new ItemStack(Nuggies.nugget_au198), 0.1F);
		GameRegistry.addSmelting(Powders.powder_radspice_tiny, new ItemStack(Nuggies.nugget_radspice), 0.1F);
		
		GameRegistry.addSmelting(ModItems.rag_damp, new ItemStack(ModItems.rag), 0.1F);
		GameRegistry.addSmelting(ModItems.rag_piss, new ItemStack(ModItems.rag), 0.1F);
		GameRegistry.addSmelting(ModItems.mask_damp, new ItemStack(ModItems.mask_rag), 0.3F);
		GameRegistry.addSmelting(ModItems.mask_piss, new ItemStack(ModItems.mask_rag), 0.3F);
		
		GameRegistry.addSmelting(Powders.powder_coal, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.briquette_lignite, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.oil_tar, new ItemStack(Powders.powder_coal), 1.0F);

		GameRegistry.addSmelting(ModItems.combine_scrap, new ItemStack(Ingots.ingot_combine_steel), 1.0F);

		GameRegistry.addSmelting(Items.BONE, new ItemStack(Items.SLIME_BALL, 3), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.SLIME_BALL, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Blocks.GRAVEL, 1), new ItemStack(Blocks.COBBLESTONE, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.gravel_obsidian), new ItemStack(Blocks.OBSIDIAN), 0.0F);

		GameRegistry.addSmelting(Powders.powder_euphemium, new ItemStack(Ingots.ingot_euphemium), 10.0F);
		GameRegistry.addSmelting(Powders.powder_dineutronium, new ItemStack(Ingots.ingot_dineutronium), 5.0F);
		GameRegistry.addSmelting(Powders.powder_osmiridium, new ItemStack(Ingots.ingot_osmiridium), 10.0F);

		GameRegistry.addSmelting(Inserts.lodestone, new ItemStack(Crystals.crystal_iron, 1), 5.0F);
		GameRegistry.addSmelting(Crystals.crystal_iron, new ItemStack(Items.IRON_INGOT, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_gold, new ItemStack(Items.GOLD_INGOT, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_redstone, new ItemStack(Items.REDSTONE, 6), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_uranium, new ItemStack(Ingots.ingot_uranium, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_thorium, new ItemStack(Ingots.ingot_th232, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_plutonium, new ItemStack(Ingots.ingot_plutonium, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_titanium, new ItemStack(Ingots.ingot_titanium, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_sulfur, new ItemStack(ModItems.sulfur, 6), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_niter, new ItemStack(ModItems.niter, 6), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_copper, new ItemStack(Ingots.ingot_copper, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_tungsten, new ItemStack(Ingots.ingot_tungsten, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_aluminium, new ItemStack(Ingots.ingot_aluminium, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_fluorite, new ItemStack(ModItems.fluorite, 6), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_beryllium, new ItemStack(Ingots.ingot_beryllium, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_lead, new ItemStack(Ingots.ingot_lead, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_schrabidium, new ItemStack(Ingots.ingot_schrabidium, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_rare, new ItemStack(Powders.powder_desh_mix, 1), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_phosphorus, new ItemStack(Powders.powder_fire, 6), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_lithium, new ItemStack(ModItems.lithium, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_starmetal, new ItemStack(Ingots.ingot_starmetal, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_trixite, new ItemStack(Ingots.ingot_plutonium, 4), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_cinnebar, new ItemStack(ModItems.cinnebar, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_cobalt, new ItemStack(Ingots.ingot_cobalt, 2), 2.0F);

		GameRegistry.addSmelting(new ItemStack(ModBlocks.gravel_diamond), new ItemStack(Items.DIAMOND), 3.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_uranium), new ItemStack(ModBlocks.glass_uranium), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_polonium), new ItemStack(ModBlocks.glass_polonium), 0.75F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.waste_trinitite), new ItemStack(ModBlocks.glass_trinitite), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.waste_trinitite_red), new ItemStack(ModBlocks.glass_trinitite), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_quartz), new ItemStack(ModBlocks.glass_quartz), 0.75F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_boron), new ItemStack(ModBlocks.glass_boron), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_lead), new ItemStack(ModBlocks.glass_lead), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.ash_digamma), new ItemStack(ModBlocks.glass_ash), 10F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.basalt), new ItemStack(ModBlocks.basalt_smooth), 0.1F);
		GameRegistry.addSmelting(Crystals.crystal_diamond, new ItemStack(Items.DIAMOND, 2), 2.0F);
		GameRegistry.addSmelting(Crystals.crystal_schraranium, new ItemStack(Nuggies.nugget_schrabidium, 2), 2.0F);
		GameRegistry.addSmelting(Ingots.ingot_schraranium, new ItemStack(Nuggies.nugget_schrabidium, 1), 2.0F);

		GameRegistry.addSmelting(Ingots.ingot_chainsteel, ItemHot.heatUp(new ItemStack(Ingots.ingot_chainsteel)), 1.0F);
		for(int i = 0; i < 10; i++)
			GameRegistry.addSmelting(new ItemStack(Ingots.ingot_steel_dusted, 1, i), ItemHot.heatUp(new ItemStack(Ingots.ingot_steel_dusted, 1, i)), 1.0F);
		GameRegistry.addSmelting(Ingots.ingot_meteorite, ItemHot.heatUp(new ItemStack(Ingots.ingot_meteorite)), 1.0F);
		GameRegistry.addSmelting(Ingots.ingot_meteorite_forged, ItemHot.heatUp(new ItemStack(Ingots.ingot_meteorite_forged)), 1.0F);
		GameRegistry.addSmelting(ModItems.blade_meteorite, ItemHot.heatUp(new ItemStack(ModItems.blade_meteorite)), 1.0F);
		GameRegistry.addSmelting(ToolSets.meteorite_sword, ItemHot.heatUp(new ItemStack(ToolSets.meteorite_sword_seared)), 1.0F);

		GameRegistry.addSmelting(ModItems.ball_fireclay, new ItemStack(Ingots.ingot_firebrick, 1), 0.1F);
	}

	public static void addBedrockOreSmelting(){
		for(Integer oreMeta : BedrockOreRegistry.oreIndexes.keySet()) {
			GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), 2F);
			GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_cleaned, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), 2F);
			GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_deepcleaned, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), 2F);
			GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_nitrated, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), 2F);
			GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_seared, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), 2F);
			GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_perfect, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), 2F);
			GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, ItemBedrockOre.getOutType(oreMeta) == 2 ? 2 : 1), 2F);
		}
	}

	public static void addSlabStair(Block slab, Block stair, Block block){
		addRecipeAuto(new ItemStack(slab, 6), new Object[] { "###", '#', block });
		addRecipeAuto(new ItemStack(stair, 8), new Object[] { "#  ","## ","###", '#', block });
		addShapelessAuto(new ItemStack(block, 3), new Object[] { stair, stair, stair, stair });
		addRecipeAuto(new ItemStack(stair, 4), new Object[] { "#  ","## ","###", '#', slab });
		addShapelessAuto(new ItemStack(block, 1), new Object[] { slab, slab });
	}

	public static void addBillet(Item billet, Item nugget, String... ore){
		for(String o : ore)
			addRecipeAuto(new ItemStack(billet), new Object[] { "###", "###", '#', o });

		addBillet(billet, nugget);
	}

	public static void addBillet(Item billet, Item nugget){
		addRecipeAuto(new ItemStack(billet), new Object[] { "###", "###", '#', nugget });
		addShapelessAuto(new ItemStack(nugget, 6), new Object[] { billet });
	}

	public static void addBilletByIngot(Item billet, Item ingot, String... ore){
		for(String o : ore)
			addShapelessAuto(new ItemStack(billet, 3), new Object[] { o, o });
		addShapelessAuto(new ItemStack(billet, 3), new Object[] { ingot, ingot });
		addShapelessAuto(new ItemStack(ingot, 2), new Object[] { billet, billet, billet });
	}

	public static void addBilletByIngot(Item billet, Item ingot){
		addShapelessAuto(new ItemStack(billet, 3), new Object[] { ingot, ingot });
		addShapelessAuto(new ItemStack(ingot, 2), new Object[] { billet, billet, billet });
	}

	//Fill rods with one billet
	public static void addRodBillet(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_empty, billet });
	}

	//Fill rods with two billets
	public static void addDualRodBillet(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_dual_empty, billet, billet });
	}

	//Fill rods with three billets
	public static void addQuadRodBillet(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_quad_empty, billet, billet, billet, billet });
	}

	//Fill rods with one billet + unload
	public static void addRodBilletUnload(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_empty, billet });
		addShapelessAuto(new ItemStack(billet, 1), new Object[] { new ItemStack(out, 1, 0) });
	}

	//Fill rods with two billets + unload
	public static void addDualRodBilletUnload(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_dual_empty, billet, billet });
		addShapelessAuto(new ItemStack(billet, 2), new Object[] { new ItemStack(out, 1, 0) });
	}

	//Fill rods with three billets + unload
	public static void addQuadRodBilletUnload(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_quad_empty, billet, billet, billet, billet });
		addShapelessAuto(new ItemStack(billet, 4), new Object[] { new ItemStack(out, 1, 0) });
	}

	//Fill rods with 6 nuggets
	public static void addRBMKRod(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RBMKFuel.rbmk_fuel_empty, billet, billet, billet, billet, billet, billet, billet, billet });
	}

	//Bundled 1/9 recipes
	public static void add1To9Pair(Item one, Item nine){
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add1To9Pair(Block one, Item nine){
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add1To9PairSameMeta(Item one, Item nine, int meta){
		add1To9SameMeta(one, nine, meta);
		add9To1SameMeta(nine, one, meta);
	}

	public static void add1To9SameMeta(Item one, Item nine, int meta){
		add1To9(new ItemStack(one, 1, meta), new ItemStack(nine, 9, meta));
	}

	//Full set of nugget, ingot and block
	public static void addMineralSet(Item nugget, Item ingot, Block block){
		add1To9(new ItemStack(ingot), new ItemStack(nugget, 9));
		add9To1(new ItemStack(nugget), new ItemStack(ingot));
		add1To9(new ItemStack(block), new ItemStack(ingot, 9));
		add9To1(new ItemStack(ingot), new ItemStack(block));
	}

	public static void add9To1SameMeta(Item nine, Item one, int meta){
		add9To1(new ItemStack(nine, 1, meta), new ItemStack(one, 1, meta));
	}

	//Decompress one item into nine
	public static void add1To9(Block one, Item nine){
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
	}

	public static void add1To9(Item one, Item nine){
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
	}

	public static void add1To9(ItemStack one, ItemStack nine){
		addShapelessAuto(nine, new Object[] { one });
	}

	//Compress nine items into one
	public static void add9To1(Item nine, Block one){
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add9To1(Item nine, Item one){
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add9To1(ItemStack nine, ItemStack one){
		addRecipeAuto(one, new Object[] { "###", "###", "###", '#', nine });
	}

	//Fill rods with 6 nuggets
	public static void addRod(Item nugget, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_empty, nugget, nugget, nugget, nugget, nugget, nugget });
	}

	//Fill rods with 12 nuggets
	public static void addDualRod(Item ingot, Item nugget, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_dual_empty, ingot, nugget, nugget, nugget });
	}

	//Fill rods with 24 nuggets
	public static void addQuadRod(Item ingot, Item nugget, Item out){
		addShapelessAuto(new ItemStack(out), new Object[] { RetroRods.rod_quad_empty, ingot, ingot, nugget, nugget, nugget, nugget, nugget, nugget });
	}

	public static void addSword(Item ingot, Item sword){
		addRecipeAuto(new ItemStack(sword), new Object[] { "I", "I", "S", 'I', ingot, 'S', Items.STICK });
	}

	public static void addPickaxe(Item ingot, Item pick){
		addRecipeAuto(new ItemStack(pick), new Object[] { "III", " S ", " S ", 'I', ingot, 'S', Items.STICK });
	}

	public static void addAxe(Item ingot, Item axe){
		addRecipeAuto(new ItemStack(axe), new Object[] { "II", "IS", " S", 'I', ingot, 'S', Items.STICK });
	}

	public static void addShovel(Item ingot, Item shovel){
		addRecipeAuto(new ItemStack(shovel), new Object[] { "I", "S", "S", 'I', ingot, 'S', Items.STICK });
	}

	public static void addHoe(Item ingot, Item hoe){
		addRecipeAuto(new ItemStack(hoe), new Object[] { "II", " S", " S", 'I', ingot, 'S', Items.STICK });
	}

	public static void addRecipeAuto(ItemStack output, Object... args){

		boolean shouldUseOD = false;
		boolean patternEnded = false;
		for(int i = 0; i < args.length; i++) {
			if(args[i] instanceof String) {
				if(patternEnded) {
					shouldUseOD = true;
					break;
				}
			} else {
				patternEnded = true;
			}
		}

		ResourceLocation loc = getRecipeName(output);
		IRecipe recipe;
		if(shouldUseOD){
			recipe = new ShapedOreRecipe(loc, output, args);
		}else {
			CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(args);
			recipe = new ShapedRecipes(output.getItem().getRegistryName().toString(), primer.width, primer.height, primer.input, output);
		}
		recipe.setRegistryName(loc);
		hack.getRegistry().register(recipe);
	}

	public static void addShapelessAuto(ItemStack output, Object... args) {

		boolean shouldUseOD = false;
		
		for(int i = 0; i < args.length; i ++) {		
			if(args[i] instanceof String) {
				shouldUseOD = true;
				break;
			}
		}

		ResourceLocation loc = getRecipeName(output);
		IRecipe recipe;
		if(shouldUseOD){
			recipe = new ShapelessOreRecipe(loc, output, args);
		}else{
			recipe = new ShapelessRecipes(loc.getResourceDomain(), output, buildInput(args));
		}
		recipe.setRegistryName(loc);
		hack.getRegistry().register(recipe);
	}

	public static ResourceLocation getRecipeName(ItemStack output){
		ResourceLocation loc = new ResourceLocation(RefStrings.MODID, output.getItem().getRegistryName().getResourcePath());
		int i = 0;
		ResourceLocation r_loc = loc;
		while(net.minecraft.item.crafting.CraftingManager.REGISTRY.containsKey(r_loc)) {
			i++;
			r_loc = new ResourceLocation(RefStrings.MODID, loc.getResourcePath() + "_" + i);
		}
		return r_loc;
	}

	public static NonNullList<Ingredient> buildInput(Object[] args){
		NonNullList<Ingredient> list = NonNullList.create();
		for(Object obj : args) {
			if(obj instanceof ItemFuelRod) {
				obj = new ItemStack((Item)obj);
			}
			if(obj instanceof Ingredient) {
				list.add((Ingredient)obj);
			} else {
				Ingredient i = CraftingHelper.getIngredient(obj);
				if(i == null) {
					i = Ingredient.EMPTY;
				}
				list.add(i);
			}
		}
		return list;
	}

	public static class IngredientContainsTag extends Ingredient {

		private final ItemStack stack;

		public IngredientContainsTag(ItemStack stack){
			super(stack);
			this.stack = stack;
		}

		@Override
		public boolean apply(ItemStack p_apply_1_){
			if(p_apply_1_ == null) {
				return false;
			} else {
				if(Library.areItemStacksCompatible(stack, p_apply_1_, false)) {
					return true;
				}
				return false;
			}
		}

		@Override
		public boolean isSimple(){
			return false;
		}
	}

	//B r u h why wasn't the constructor visible in the first place?
	public static class IngredientNBT2 extends IngredientNBT {

		public IngredientNBT2(ItemStack stack){
			super(stack);
		}

	}
}
