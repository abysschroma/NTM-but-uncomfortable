package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Armory;
import com.hbm.items.ModItems.Batteries;
import com.hbm.items.ModItems.Materials.Ingots;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.items.ModItems.Materials.Powders;
import com.hbm.main.MainRegistry;
import com.hbm.modules.ItemHazardModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockNTMOre extends BlockOre implements IItemHazard {
	
	ItemHazardModule module;
	public static int xp;

	public BlockNTMOre(String name, int harvestLvl, int xp) {
		super();
		this.xp = xp;
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setTickRandomly(false);
		this.setHarvestLevel("pickaxe", harvestLvl);
		this.module = new ItemHazardModule();
		ModBlocks.ALL_BLOCKS.add(this);
	}

	public BlockNTMOre(String name, int harvestLvl) {
		this(name, harvestLvl, 2);
	}
	
	public BlockNTMOre(SoundType sound, String name, int harvestLvl){
		this(name, harvestLvl);
		super.setSoundType(sound);
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune){
		if(this.getItemDropped(state, RANDOM, fortune) != Item.getItemFromBlock(this))
			return xp;
		return 0;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(this == ModBlocks.ore_asbestos || this == ModBlocks.ore_gneiss_asbestos || this == ModBlocks.basalt_asbestos)
		{
			return Ingots.ingot_asbestos;
		}
		if(this == ModBlocks.ore_nether_fire){
			return rand.nextInt(10) == 0 ? Ingots.ingot_phosphorus : Powders.powder_fire;
		}
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur || this == ModBlocks.ore_meteor_sulfur || this == ModBlocks.basalt_sulfur){
			return ModItems.sulfur;
		}
		if(this == ModBlocks.ore_niter){
			return ModItems.niter;
		}
		if(this == ModBlocks.ore_fluorite){
			return Ingots.fluorite;
		}
		if(this == ModBlocks.ore_lignite){
			return ModItems.lignite;
		}
		if(this == ModBlocks.ore_rare || this == ModBlocks.ore_gneiss_rare){	
			return ModItems.rare_earth_chunk;
		}
		if(this == ModBlocks.block_meteor)
		{
			return rand.nextInt(10) == 0 ? ModItems.plate_dalekanium : Item.getItemFromBlock(ModBlocks.block_meteor);
		}
		if(this == ModBlocks.block_meteor_cobble)
		{
			return ModItems.fragment_meteorite;
		}
		if(this == ModBlocks.block_meteor_broken)
		{
			return ModItems.fragment_meteorite;
		}
		if(this == ModBlocks.block_meteor_treasure)
		{
			switch(rand.nextInt(35)) {
			case 0: return ModItems.coil_advanced_alloy;
			case 1: return ModItems.plate_advanced_alloy;
			case 2: return Powders.powder_desh_mix;
			case 3: return Ingots.ingot_desh;
			case 4: return Batteries.battery_advanced;
			case 5: return Batteries.battery_lithium_cell;
			case 6: return Batteries.battery_advanced_cell;
			case 7: return Nuggies.nugget_schrabidium;
			case 8: return Ingots.ingot_plutonium;
			case 9: return Ingots.ingot_thorium_fuel;
			case 10: return Ingots.ingot_u233;
			case 11: return ModItems.turbine_tungsten;
			case 12: return Ingots.ingot_dura_steel;
			case 13: return Ingots.ingot_polymer;
			case 14: return Ingots.ingot_tungsten;
			case 15: return Ingots.ingot_combine_steel;
			case 16: return Ingots.ingot_lanthanium;
			case 17: return Ingots.ingot_actinium;
			case 18: return Item.getItemFromBlock(ModBlocks.block_meteor);
			case 19: return Item.getItemFromBlock(ModBlocks.fusion_heater);
			case 20: return Item.getItemFromBlock(ModBlocks.fusion_core);
			case 21: return Item.getItemFromBlock(ModBlocks.watz_element);
			case 22: return Item.getItemFromBlock(ModBlocks.ore_rare);
			case 23: return Item.getItemFromBlock(ModBlocks.fusion_conductor);
			case 24: return Item.getItemFromBlock(ModBlocks.reactor_computer);
			case 25: return Item.getItemFromBlock(ModBlocks.machine_diesel);
			case 26: return Item.getItemFromBlock(ModBlocks.machine_rtg_grey);
			case 27: return ModItems.pellet_rtg;
			case 28: return ModItems.pellet_rtg_weak;
			case 29: return ModItems.rtg_unit;
			case 30: return Armory.gun_spark_ammo;
			case 31: return Armory.ammo_nuke;
			case 32: return Armory.ammo_mirv;
			case 33: return Armory.gun_defabricator_ammo;
			case 34: return Armory.gun_osipr_ammo2;
			}
		}
		if(this == ModBlocks.deco_aluminium)
		{
			return Ingots.ingot_aluminium;
		}
		if(this == ModBlocks.deco_beryllium)
		{
			return Ingots.ingot_beryllium;
		}
		if(this == ModBlocks.deco_lead)
		{
			return Ingots.ingot_lead;
		}
		if(this == ModBlocks.deco_red_copper)
		{
			return Ingots.ingot_red_copper;
		}
		if(this == ModBlocks.deco_steel)
		{
			return Ingots.ingot_steel;
		}
		if(this == ModBlocks.deco_titanium)
		{
			return Ingots.ingot_titanium;
		}
		if(this == ModBlocks.deco_tungsten)
		{
			return Ingots.ingot_tungsten;
		}
		if(this == ModBlocks.deco_asbestos)
		{
			return Ingots.ingot_asbestos;
		}
		if(this == ModBlocks.ore_cinnebar) {
			return ModItems.cinnebar;
		}
		if(this == ModBlocks.ore_coltan) {
			return ModItems.fragment_coltan;
		}
		if(this == ModBlocks.ore_cobalt || this == ModBlocks.ore_nether_cobalt) {
			return ModItems.fragment_cobalt;
		}
		return super.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random rand) {
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur || this == ModBlocks.ore_meteor_sulfur || this == ModBlocks.basalt_sulfur){
			return 2 + rand.nextInt(3) * fortune;
		}
		if(this == ModBlocks.block_niter){
			return 4 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_niter){
			return 1 + rand.nextInt(2) * fortune;
		}
		if(this == ModBlocks.ore_fluorite){
			return 2 + rand.nextInt(3) * fortune;
		}
		if(this == ModBlocks.block_meteor_broken)
		{
			return 1 + rand.nextInt(3);
		}
		if(this == ModBlocks.block_meteor_treasure)
		{
			return 1 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_cobalt) {
			return 4 + rand.nextInt(6);
		}
		if(this == ModBlocks.ore_nether_cobalt) {
			return 5 + rand.nextInt(8);
		}
		return super.quantityDropped(state, fortune, rand);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}
	
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (world.getBlockState(pos.down()).getBlock() == ModBlocks.ore_oil_empty) {
        	world.setBlockState(pos, ModBlocks.ore_oil_empty.getDefaultState());
        	world.setBlockState(pos.down(), ModBlocks.ore_oil.getDefaultState());
        }
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_uranium) || stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium) || stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_uranium)){
			tooltip.add("High-Radiation creates medium amounts of schrabidium inside this block");
		}
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_schrabidium) || stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_gneiss_schrabidium) || stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_schrabidium)){
			tooltip.add("High-Radiation has created medium amounts of schrabidium inside this block");
		}
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.ore_oil)){
			tooltip.add("You weren't supposed to mine that.");
			tooltip.add("Come on, get a derrick you doofus.");
		}
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entity) {
		if(entity instanceof EntityLivingBase)
			this.module.applyEffects((EntityLivingBase)entity, 0.5F, 0, false, EnumHand.MAIN_HAND);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity){
		if(entity instanceof EntityLivingBase)
			this.module.applyEffects((EntityLivingBase)entity, 0.5F, 0, false, EnumHand.MAIN_HAND);
	}

	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
}
