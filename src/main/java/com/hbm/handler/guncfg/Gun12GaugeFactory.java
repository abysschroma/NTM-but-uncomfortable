package com.hbm.handler.guncfg;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.items.ModItems.Armory;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.potion.HbmPotion;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class Gun12GaugeFactory {

	public static GunConfiguration getUboinikConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 8;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.durability = 1500;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = HBMSoundEvents.shotgunShoot;
		
		config.name = "Uboinik Revolving Shotgun";
		config.manufacturer = "Metro Gunsmiths";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G12_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G12_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.G12_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G12_DU);
		config.config.add(BulletConfigSyncingUtil.G12_AM);
		config.config.add(BulletConfigSyncingUtil.G12_SLEEK);
		
		return config;
	}
	
	public static GunConfiguration getShottyConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 26;
		config.roundsPerCycle = 2;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.durability = 3000;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = HBMSoundEvents.shottyShoot;
		
		config.animations.put(AnimType.ALT_CYCLE, new BusAnimation()
				.addBus("MEATHOOK_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, -0.5, 1.5, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250))));
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("SHOTTY_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						)
				.addBus("SHOTTY_BREAK", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 100))	//do nothing for 100ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 60, 200))	//open
						.addKeyframe(new BusAnimationKeyframe(0, 0, 60, 500))	//do nothing for 500ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))	//close
						)
				.addBus("SHOTTY_EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))	//do nothing for 300ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 700))	//fling!
						)
				.addBus("SHOTTY_INSERT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))	//do nothing for 300ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 1, 0))		//reposition
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 350))	//come in from the side
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 150))	//push
						)
				);
		
		config.name = "???";
		config.manufacturer = "???";
		config.comment.add("but bOB WhY iS TExtURE no woRk");
		config.comment.add("hoW do I cRAFT PleasE HElp");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G12_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G12_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.G12_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G12_DU);
		config.config.add(BulletConfigSyncingUtil.G12_AM);
		config.config.add(BulletConfigSyncingUtil.G12_SLEEK);
		
		return config;
	}
	
	public static GunConfiguration getJShotgunConfig(){
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 8;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 30;
		config.firingDuration = 0;
		config.ammoCap = 2;
		config.durability = 3000;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.NONE;
		config.reloadSound = null;
		config.firingSound = HBMSoundEvents.shottyShoot;
		
		config.animations.put(AnimType.ALT_CYCLE, new BusAnimation()
				.addBus("JS_RECOIL2", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, -0.5, 1.5, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250))
						)
				);
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("JS_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0.4, 0, 0, 150))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250))
						)
				);
		
		config.name = "Jade Shotgun";
		config.manufacturer = "Aranim Industrial";
		config.comment.add("Occult Weaponry");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G12_DU);
		return config;
	}
	
	public static BulletConfiguration get12GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_12gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_12gauge_incendiary;
		bullet.wear = 15;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeShrapnelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_12gauge_shrapnel;
		bullet.wear = 15;
		bullet.dmgMin = 2;
		bullet.dmgMax = 6;
		bullet.ricochetAngle = 15;
		bullet.HBRC = 80;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_12gauge_du;
		bullet.wear = 20;
		bullet.dmgMin = 3;
		bullet.dmgMax = 8;
		bullet.doesPenetrate = true;
		bullet.leadChance = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeAMConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_12gauge_marauder;
		bullet.wear = 20;
		bullet.dmgMin = 100;
		bullet.dmgMax = 500;
		bullet.leadChance = 50;
		
		bullet.bHurt = new IBulletHurtBehavior() {

			@Override
			public void behaveEntityHurt(EntityBulletBase bullet, Entity hit) {
				
				if(hit instanceof EntityLivingBase)
					((EntityLivingBase)hit).addPotionEffect(new PotionEffect(HbmPotion.bang, 20, 0));
			}
			
		};
		
		return bullet;
	}
	public static BulletConfiguration get12GaugeSleekConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardAirstrikeConfig();

		bullet.ammo = Armory.ammo_12gauge_sleek;

		return bullet;
	}
}
