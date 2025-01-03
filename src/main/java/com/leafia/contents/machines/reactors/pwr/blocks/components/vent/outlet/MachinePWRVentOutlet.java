package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.outlet;

import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.MachinePWRVentBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MachinePWRVentOutlet extends MachinePWRVentBase {
	public MachinePWRVentOutlet() {
		super(Material.IRON,"pwr_vent_outlet");
		setSoundType(SoundType.METAL);
	}
	@Override
	public boolean tileEntityShouldCreate(World world,BlockPos pos) {
		return false;
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new TileEntityPWRVentOutlet();
	}
}