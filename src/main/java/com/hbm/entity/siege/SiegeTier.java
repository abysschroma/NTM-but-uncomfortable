package com.hbm.entity.siege;

import com.hbm.items.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SiegeTier {
	
	public static SiegeTier[] tiers = new SiegeTier[100];
	private static int nextID = 0;
	
	public static int getLength() {
		return nextID;
	}
	
	public static SiegeTier DEFAULT_BUFF;
	public static SiegeTier CLAY;
	public static SiegeTier STONE;
	public static SiegeTier IRON;
	public static SiegeTier SILVER;
	public static SiegeTier GOLD;
	public static SiegeTier DESH;
	public static SiegeTier SCHRAB;
	public static SiegeTier DNT;
	
	public static void registerTiers() {
		DEFAULT_BUFF =	new SiegeTier(20, "buff")		.addDrop(new ItemStack(ModItems.coin_siege, 1, 0))	.setDR(0.2F)										.setDMG(2F);
		CLAY =			new SiegeTier(30, "clay")		.addDrop(new ItemStack(ModItems.coin_siege, 1, 1))	.setDR(0.2F)										.setDMG(3F);
		STONE =			new SiegeTier(40, "stone")		.addDrop(new ItemStack(ModItems.coin_siege, 1, 2))	.setDR(0.3F)	.setDT(1F)				.setFP()	.setDMG(5F);
		IRON =			new SiegeTier(50, "iron")		.addDrop(new ItemStack(ModItems.coin_siege, 1, 3))	.setDR(0.3F)	.setDT(2F)				.setFP()	.setDMG(7.5F)					.setFF();
		SILVER =		new SiegeTier(70, "silver")		.addDrop(new ItemStack(ModItems.coin_siege, 1, 4))	.setDR(0.5F)	.setDT(3F)	.setNF()	.setFP()	.setDMG(10F)	.setSP(0.5F)	.setFF();
		GOLD =			new SiegeTier(100, "gold")		.addDrop(new ItemStack(ModItems.coin_siege, 1, 5))	.setDR(0.5F)	.setDT(5F)	.setNF()	.setFP()	.setDMG(15F)	.setSP(0.5F)	.setFF();
		DESH =			new SiegeTier(150, "desh")		.addDrop(new ItemStack(ModItems.coin_siege, 1, 6))	.setDR(0.7F)	.setDT(7F)	.setNF()	.setFP()	.setDMG(25F)	.setSP(0.5F)	.setFF();
		SCHRAB =		new SiegeTier(250, "schrab")	.addDrop(new ItemStack(ModItems.coin_siege, 1, 7))	.setDR(0.7F)	.setDT(10F)	.setNF()	.setFP()	.setDMG(50F)	.setSP(1F)		.setFF();
		DNT =			new SiegeTier(500, "dnt")		.addDrop(new ItemStack(ModItems.coin_siege, 1, 8))	.setDR(0.9F)	.setDT(20F)	.setNF()	.setFP()	.setDMG(100F)	.setSP(1F)		.setFF();
	}

	public int id;
	public float dt = 0F;
	public float dr = 0F;
	public float health = 20F;
	public String name = "";
	public float damageMod = 1F;
	public float speedMod = 0F;
	public boolean fireProof = false;
	public boolean noFall = false;
	public boolean noFriendlyFire = false;
	public List<ItemStack> dropItem = new ArrayList<>();
	
	//so this is basically delegates but in java? or like, uh, storing lambdas? i don't know what it is but i feel like playing god. i like it.
	public Consumer<EntityLivingBase> delegate;
	
	public SiegeTier(float baseHealth, String name) {
		this.id = nextID;
		SiegeTier.tiers[this.id] = this;
		nextID++;
		
		this.health = baseHealth;
		this.name = name;
	}
	
	private SiegeTier setDT(float dt) {
		this.dt = dt;
		return this;
	}
	
	private SiegeTier setDR(float dr) {
		this.dr = dr;
		return this;
	}
	
	private SiegeTier setDMG(float dmg) {
		this.damageMod = dmg;
		return this;
	}
	
	private SiegeTier setSP(float sp) {
		this.speedMod = sp;
		return this;
	}
	
	private SiegeTier setFP() {
		this.fireProof = true;
		return this;
	}
	
	private SiegeTier setNF() {
		this.noFall = true;
		return this;
	}
	
	private SiegeTier setFF() {
		this.noFriendlyFire = true;
		return this;
	}
	
	private SiegeTier addDrop(Item drop) {
		return addDrop(new ItemStack(drop));
	}
	
	private SiegeTier addDrop(ItemStack drop) {
		this.dropItem.add(drop);
		return this;
	}
	
	private SiegeTier setAura(int range, PotionEffect... effects) {
		this.daisyChain(x -> SiegeTier.doAura(x, range, effects)); //HOLY SHIT THAT ACTUALLY WORKS!!
		return this;
	}
	
	private SiegeTier daisyChain(Consumer<EntityLivingBase> link) {
		
		if(this.delegate == null)
			this.delegate = link;
		else
			this.delegate.andThen(link); //HOLY FUCK!
		
		return this;
	}
	
	public void runDelegate(EntityLivingBase entity) {
		if(this.delegate != null) this.delegate.accept(entity);
	}
	
	/*
	 * DELEGATIONS
	 */
	private static void doAura(EntityLivingBase entity, int range, PotionEffect... effects) {
		
		List<EntityPlayer> players = entity.world.getEntitiesWithinAABB(EntityPlayer.class, entity.getEntityBoundingBox().grow(range, range, range));
		
		for(EntityPlayer player : players) {
			
			if(player.getDistanceSq(entity) < range * range) {
				
				for(PotionEffect e : effects) {
					player.addPotionEffect(new PotionEffect(e));
				}
			}
		}
	}
}