package com.hbm.entity.grenade;

import com.hbm.items.ModItems.Armory;
import com.hbm.items.weapon.ItemGrenade;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityGrenadeMIRV extends EntityGrenadeBouncyBase {

    public EntityGrenadeMIRV(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeMIRV(World p_i1774_1_, EntityLivingBase p_i1774_2_, EnumHand hand)
    {
        super(p_i1774_1_, p_i1774_2_, hand);
    }

    public EntityGrenadeMIRV(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.world.isRemote)
        {
            this.setDead();
    		
    		for(int i = 0; i < 8; i++) {
    			
    			EntityGrenadeSmart grenade = new EntityGrenadeSmart(world);
    			grenade.posX = posX;
    			grenade.posY = posY;
    			grenade.posZ = posZ;
    			grenade.motionX = motionX + rand.nextGaussian() * 0.25D;
    			grenade.motionY = motionY + rand.nextGaussian() * 0.25D;
    			grenade.motionZ = motionZ + rand.nextGaussian() * 0.25D;
    			grenade.ticksExisted = 10;
    			
    			world.spawnEntity(grenade);
    		}
        }
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(Armory.grenade_mirv);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}