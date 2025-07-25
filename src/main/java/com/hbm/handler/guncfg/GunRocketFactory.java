package com.hbm.handler.guncfg;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletRicochetBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.ModItems.Armory;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GunRocketFactory {

	public static GunConfiguration getGustavConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 30;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = HBMSoundEvents.rpgShoot;
		config.reloadSound = GunConfiguration.RSOUND_LAUNCHER;
		config.reloadSoundEnd = false;
		
		config.name = "Carl Gustav Recoilless Rifle M1";
		config.manufacturer = "Saab Bofors Dynamics";
		config.comment.add("Fun fact of the day: Recoilless");
		config.comment.add("rifles don't actually fire rockets.");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ROCKET_NORMAL);
		config.config.add(BulletConfigSyncingUtil.ROCKET_HE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.ROCKET_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.ROCKET_EMP);
		config.config.add(BulletConfigSyncingUtil.ROCKET_GLARE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_TOXIC);
		config.config.add(BulletConfigSyncingUtil.ROCKET_CANISTER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SLEEK);
		config.config.add(BulletConfigSyncingUtil.ROCKET_NUKE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_CHAINSAW);
		config.durability = 140;
		
		return config;
	}
	
	public static GunConfiguration getQuadroConfig() {

		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 5;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 100;
		config.firingDuration = 0;
		config.ammoCap = 4;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = HBMSoundEvents.rpgShoot;
		config.reloadSound = HBMSoundEvents.quadroReload;
		config.reloadSoundEnd = false;

		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("QUADRO_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, -0.5, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						)
				);

		config.animations.put(AnimType.RELOAD, new BusAnimation()
				.addBus("QUADRO_RELOAD_ROTATE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 60, 750))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 60, 3500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 750))
						)
				.addBus("QUADRO_RELOAD_PUSH", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(-1, -1, 0, 0))
						.addKeyframe(new BusAnimationKeyframe(-1, -1, 0, 750))
						.addKeyframe(new BusAnimationKeyframe(-1, 0, 0, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 3000))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 750))
						)
				);

		config.name = "OpenQuadro Guided Man-Portable Missile Launcher";
		config.manufacturer = "Open Mann Co.";
		config.comment.add("For the next three hundred years, people who needed to get to the second");
		config.comment.add("floor used the only method available to them, which was rocket jumping.");
		config.comment.add("This persisted until 1857, when the young bearded inventor named");
		config.comment.add("President Abraham Lincoln invented stairs.");

		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ROCKET_NORMAL_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_HE_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_INCENDIARY_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_PHOSPHORUS_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SHRAPNEL_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_EMP_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_GLARE_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_TOXIC_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_CANISTER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SLEEK_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_NUKE_LASER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_CHAINSAW_LASER);
		config.durability = 500;

		return config;
	}
	
	public static GunConfiguration getKarlConfig() {
		
		GunConfiguration config = getGustavConfig();
		
		config.reloadDuration = 20;
		
		config.name = "M1 Karl-Gerät";
		config.manufacturer = "???";
		config.comment.clear();
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ROCKET_HE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_EMP);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SLEEK);
		config.config.add(BulletConfigSyncingUtil.ROCKET_TOXIC);
		config.config.add(BulletConfigSyncingUtil.ROCKET_CANISTER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_NUKE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_CHAINSAW);
		config.durability = 500;
		
		return config;
	}
	
	public static GunConfiguration getPanzConfig() {
		
		GunConfiguration config = getGustavConfig();
		
		config.reloadDuration = 25;
		config.hasSights = true;
		
		config.name = "Raketenpanzerbüchse 54";
		config.manufacturer = "Enzinger Union";
		config.comment.clear();
		config.comment.add("Panzer-Shrek");
		
		config.durability = 260;
		
		return config;
	}
	
	public static BulletConfiguration getRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 4F;
		bullet.trail = 0;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketHEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_he;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 6.5F;
		bullet.trail = 1;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketIncendiaryConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_incendiary;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 4F;
		bullet.incendiary = 5;
		bullet.trail = 2;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketEMPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_emp;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 2.5F;
		bullet.emp = 10;
		bullet.trail = 4;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_sleek;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 10F;
		bullet.trail = 6;
		bullet.gravity = 0;
		bullet.jolt = 6.5D;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketShrapnelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_shrapnel;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 4F;
		bullet.shrapnel = 25;
		bullet.trail = 3;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketGlareConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_glare;
		bullet.velocity = 5.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 20;
		bullet.explosive = 4F;
		bullet.incendiary = 5;
		bullet.trail = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketNukeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_nuclear;
		bullet.velocity = 1.5F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 35;
		bullet.explosive = 0;
		bullet.incendiary = 0;
		bullet.trail = 7;
		bullet.nuke = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketChlorineConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_toxic;
		bullet.velocity = 1.5F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 10;
		bullet.explosive = 0;
		bullet.chlorine = 50;
		bullet.trail = 7;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketRPCConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_rpc;
		bullet.velocity = 3.0F;
		bullet.dmgMin = 20;
		bullet.dmgMax = 25;
		bullet.wear = 15;
		bullet.explosive = 0;
		bullet.incendiary = 0;
		bullet.trail = 8;
		bullet.gravity = 0.000D;
		bullet.ricochetAngle = 90;
		bullet.LBRC = 100;
		bullet.doesPenetrate = true;
		
		bullet.bRicochet = new IBulletRicochetBehavior() {
			
			public void behaveBlockRicochet(EntityBulletBase bullet, int bX, int bY, int bZ) {
				BlockPos pos = new BlockPos(bX, bY, bZ);
				World worldObj = bullet.world;
				if(!worldObj.isRemote && 
						(worldObj.getBlockState(pos).getMaterial() == Material.WOOD ||
						worldObj.getBlockState(pos).getMaterial() == Material.PLANTS ||
						worldObj.getBlockState(pos).getMaterial() == Material.GLASS ||
						worldObj.getBlockState(pos).getMaterial() == Material.LEAVES))
					worldObj.destroyBlock(pos, false);}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketPhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = Armory.ammo_rocket_phosphorus;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 4F;
		bullet.incendiary = 5;
		bullet.trail = 9;
		
		bullet.bImpact = BulletConfigFactory.getPhosphorousEffect(10, 60 * 20, 100, 0.5D, 1F);
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketCanisterConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();

		bullet.ammo = Armory.ammo_rocket_canister;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 2F;
		bullet.trail = 0;

		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {

				if(!bullet.world.isRemote) {

					if(bullet.ticksExisted > 10) {
						bullet.setDead();

						for(int i = 0; i < 50; i++) {

							EntityBulletBase bolt = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.M44_AP);
							bolt.setPosition(bullet.posX, bullet.posY, bullet.posZ);
							bolt.shoot(bullet.motionX, bullet.motionY, bullet.motionZ, 0.25F, 0.1F);
							bullet.world.spawnEntity(bolt);
						}
					}
				}
			}
		};

		return bullet;
	}
}
