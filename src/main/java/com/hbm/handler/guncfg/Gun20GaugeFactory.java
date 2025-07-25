package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems.Armory;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class Gun20GaugeFactory {

	public static GunConfiguration getShotgunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 25;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("LEVER_ROTATE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 45, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))
						)
				.addBus("LEVER_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 150))
						.addKeyframe(new BusAnimationKeyframe(0, -0.5, 0, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))
						)
				);
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		config.config.add(BulletConfigSyncingUtil.G20_SLEEK);
		
		return config;
	}
	
	public static GunConfiguration getMareConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 2000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = HBMSoundEvents.revolverShootAlt;
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 1887";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		config.config.add(BulletConfigSyncingUtil.G20_SLEEK);
		
		return config;
	}
	
	public static GunConfiguration getMareDarkConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = HBMSoundEvents.revolverShootAlt;
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 1887 Inox";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		config.config.add(BulletConfigSyncingUtil.G20_SLEEK);
		
		return config;
	}
	
	public static GunConfiguration getBoltConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = HBMSoundEvents.revolverShoot;
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 20 Inox";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		config.config.add(BulletConfigSyncingUtil.G20_SLEEK);
		
		return config;
	}
	
	public static GunConfiguration getBoltGreenConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = HBMSoundEvents.revolverShoot;
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 20 Polymer";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		config.config.add(BulletConfigSyncingUtil.G20_SLEEK);
		
		return config;
	}
	
	public static GunConfiguration getBoltSaturniteConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 4000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = HBMSoundEvents.revolverShoot;
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 20 D-25A";
		config.manufacturer = "Winchester Repeating Arms Company / Big MT";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_SLUG_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SLEEK);
		
		return config;
	}
	
	public static BulletConfiguration get20GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_20gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 3;
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeSlugConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = Armory.ammo_20gauge_slug;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_20gauge_flechette;
		bullet.dmgMin = 3;
		bullet.dmgMax = 6;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_20gauge_incendiary;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeShrapnelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_20gauge_shrapnel;
		bullet.wear = 15;
		bullet.dmgMin = 2;
		bullet.dmgMax = 6;
		bullet.ricochetAngle = 15;
		bullet.HBRC = 80;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_20gauge_explosive;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		bullet.wear = 25;
		bullet.explosive = 0.5F;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeCausticConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_20gauge_caustic;
		bullet.dmgMin = 2;
		bullet.dmgMax = 6;
		bullet.wear = 25;
		bullet.caustic = 5;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(MobEffects.POISON, 10 * 20, 1));
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeShockConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_20gauge_shock;
		bullet.dmgMin = 4;
		bullet.dmgMax = 8;
		bullet.wear = 25;
		bullet.emp = 2;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 1));
		bullet.effects.add(new PotionEffect(MobEffects.WEAKNESS, 10 * 20, 4));
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeWitherConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = Armory.ammo_20gauge_wither;
		bullet.dmgMin = 4;
		bullet.dmgMax = 8;
		
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(MobEffects.WITHER, 10 * 20, 2));
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeSleekConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardAirstrikeConfig();

		bullet.ammo = Armory.ammo_20gauge_sleek;

		return bullet;
	}
}
