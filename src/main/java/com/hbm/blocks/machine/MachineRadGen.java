package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class MachineRadGen extends BlockContainer implements IMultiBlock {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public MachineRadGen(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
		MachineTooltip.addGenerator(tooltip);
		super.addInformation(stack,player,tooltip,advanced);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineRadGen();
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_radgen);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing e = placer.getHorizontalFacing().getOpposite();
		world.setBlockState(pos, state.withProperty(FACING, e));
		if (e == EnumFacing.EAST) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.radGenDimensionEast)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.radGenDimensionEast, ModBlocks.dummy_block_radgen);
				
				//
				DummyBlockRadGen.safeBreak = true;
				world.setBlockState(pos.add(0, 0, 4), ModBlocks.dummy_port_radgen.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(0, 0, 4));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				DummyBlockRadGen.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (e == EnumFacing.SOUTH) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.radGenDimensionSouth)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.radGenDimensionSouth, ModBlocks.dummy_block_radgen);
				
				//
				DummyBlockRadGen.safeBreak = true;
				world.setBlockState(pos.add(-4, 0, 0), ModBlocks.dummy_port_radgen.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(-4, 0, 0));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				DummyBlockRadGen.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (e == EnumFacing.WEST) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.radGenDimensionWest)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.radGenDimensionWest, ModBlocks.dummy_block_radgen);
				
				//
				DummyBlockRadGen.safeBreak = true;
				world.setBlockState(pos.add(0, 0, -4), ModBlocks.dummy_port_radgen.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(0, 0, -4));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				DummyBlockRadGen.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
		if (e == EnumFacing.NORTH) {
			if(MultiblockHandler.checkSpace(world, pos, MultiblockHandler.radGenDimensionNorth)) {
				MultiblockHandler.fillUp(world, pos, MultiblockHandler.radGenDimensionNorth, ModBlocks.dummy_block_radgen);
				
				//
				DummyBlockRadGen.safeBreak = true;
				world.setBlockState(pos.add(4, 0, 0), ModBlocks.dummy_port_radgen.getDefaultState());
				TileEntity te = world.getTileEntity(pos.add(4, 0, 0));
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.target = pos;
				}
				DummyBlockRadGen.safeBreak = false;
				//
				
			} else
				world.destroyBlock(pos, true);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityMachineRadGen entity = (TileEntityMachineRadGen) world.getTileEntity(pos);
    		if(entity != null)
    		{
    			player.openGui(MainRegistry.instance, ModBlocks.guiID_radgen, world, pos.getX(), pos.getY(), pos.getZ());
    		}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
	   return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

}
