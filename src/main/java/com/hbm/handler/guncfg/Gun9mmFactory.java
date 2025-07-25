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

import java.util.ArrayList;

public class Gun9mmFactory {

	public static GunConfiguration getMP40Config() {

		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 32;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = HBMSoundEvents.rifleShoot;
		config.reloadSoundEnd = false;

		config.name = "Maschinenpistole 40";
		config.manufacturer = "Erfurter Maschinenfabrik Geipel";

		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
		config.config.add(BulletConfigSyncingUtil.P9_AP);
		config.config.add(BulletConfigSyncingUtil.P9_DU);
		config.config.add(BulletConfigSyncingUtil.CHL_P9);
		config.config.add(BulletConfigSyncingUtil.P9_ROCKET);

		return config;
	}

	public static GunConfiguration getThompsonConfig() {

		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 30;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = HBMSoundEvents.rifleShoot;
		config.reloadSoundEnd = false;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 1, -5, 20))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 20))
						));

		config.name = "M1A1 Submachine Gun 9mm Mod";
		config.manufacturer = "Auto-Ordnance Corporation";

		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
		config.config.add(BulletConfigSyncingUtil.P9_AP);
		config.config.add(BulletConfigSyncingUtil.P9_DU);
		config.config.add(BulletConfigSyncingUtil.CHL_P9);
		config.config.add(BulletConfigSyncingUtil.P9_ROCKET);

		return config;
	}

	static float inaccuracy = 5;

	public static BulletConfiguration get9mmConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();

		bullet.ammo = Armory.ammo_9mm;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 2;
		bullet.dmgMax = 4;

		return bullet;
	}

	public static BulletConfiguration get9mmAPConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();

		bullet.ammo = Armory.ammo_9mm_ap;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		bullet.leadChance = 10;
		bullet.wear = 15;

		return bullet;
	}

	public static BulletConfiguration get9mmDUConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();

		bullet.ammo = Armory.ammo_9mm_du;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		bullet.leadChance = 50;
		bullet.wear = 25;

		return bullet;
	}

	public static BulletConfiguration get9mmRocketConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();

		bullet.ammo = Armory.ammo_9mm_rocket;
		bullet.velocity = 5;
		bullet.explosive = 7.5F;
		bullet.trail = 5;

		return bullet;
	}
}
