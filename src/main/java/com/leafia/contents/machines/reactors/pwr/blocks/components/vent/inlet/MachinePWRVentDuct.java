package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.inlet;

import com.hbm.blocks.ILookOverlay;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.MachinePWRVentBase;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MachinePWRVentDuct extends MachinePWRVentBase implements ILookOverlay {
	public MachinePWRVentDuct() {
		super(Material.IRON,"pwr_vent_duct");
		setSoundType(SoundType.METAL);
	}

	@Override
	public boolean tileEntityShouldCreate(World world,BlockPos pos) {
		return false;
	}

	@Override
	public boolean correctDirection(World world,BlockPos pos) {
		return false;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new TileEntityPWRVentDuct();
	}
	@Override
	public void onNeighborChange(IBlockAccess world,BlockPos pos,BlockPos neighbor) {
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityFFDuctBaseMk2){
			((TileEntityFFDuctBaseMk2)te).onNeighborChange();
		}
	}
	@Override
	public void neighborChanged(IBlockState state,World worldIn,BlockPos pos,Block blockIn,BlockPos fromPos) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileEntityFFDuctBaseMk2){
			((TileEntityFFDuctBaseMk2)te).onNeighborChange();
		}
		boolean trigger = false;
		for (EnumFacing facing : EnumFacing.values()) {
			if (worldIn.getBlockState(pos.offset(facing)).getBlock() instanceof MachinePWRVentInlet) {
				if (!trigger)
					trigger = true;
				else {
					trigger = false;
					break;
				}
			}
		}
		if (!trigger)
			worldIn.setBlockToAir(pos);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		//getActualState appears to be called when the neighbor changes on client, so I can use this to update instead of a buggy packet.
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileEntityFFDuctBaseMk2)
			((TileEntityFFDuctBaseMk2)te).onNeighborChange();
		return state;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);

		if(te instanceof TileEntityFFDuctBaseMk2){
			TileEntityFFDuctBaseMk2.breakBlock(worldIn, pos);
		}
		super.breakBlock(worldIn, pos, state);
	}
	@Override
	public void printHook(Pre event,World world,int x,int y,int z) {

		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

		if(!(te instanceof TileEntityFFDuctBaseMk2))
			return;

		Fluid ductFluid = ((TileEntityFFDuctBaseMk2) te).getType();

		List<String> text = new ArrayList();
		if(ductFluid == null){
			text.add("§7" + I18nUtil.resolveKey("desc.none"));
		} else{
			int color = ModForgeFluids.getFluidColor(ductFluid);
			text.add("&[" + color + "&]" +I18nUtil.resolveKey(ductFluid.getUnlocalizedName()));
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xffff00, 0x404000, text);
	}
}