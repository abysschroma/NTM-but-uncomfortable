package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Materials.Powders;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityMissileTaint extends EntityMissileBaseAdvanced {

	public EntityMissileTaint(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1F, 7F);
	}

	public EntityMissileTaint(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.setSize(1F, 7F);
	}

	@Override
	public void onImpact() {
		this.world.createExplosion(this, this.posX, this.posY, this.posZ, 5.0F, true);
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for (int i = 0; i < 100; i++) {
			int a = rand.nextInt(11) + (int) this.posX - 5;
			int b = rand.nextInt(11) + (int) this.posY - 5;
			int c = rand.nextInt(11) + (int) this.posZ - 5;
			pos.setPos(a, b, c);
			if (world.getBlockState(pos).getBlock().isReplaceable(world, pos) && BlockTaint.hasPosNeightbour(world, pos))
				world.setBlockState(pos, ModBlocks.taint.getDefaultState());
		}
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.wire_aluminium, 4));
		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.hull_small_aluminium, 2));
		list.add(new ItemStack(Powders.powder_magic, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));

		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(Powders.powder_spark_mix, 1);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER0;
	}
}
