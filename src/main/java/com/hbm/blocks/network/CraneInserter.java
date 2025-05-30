package com.hbm.blocks.network;

import api.hbm.block.IConveyorItem;
import api.hbm.block.IEnterableBlock;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.network.TileEntityCraneBase;
import com.hbm.tileentity.network.TileEntityCraneInserter;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CraneInserter extends BlockCraneBase implements IEnterableBlock {
    public CraneInserter(Material materialIn, String s) {
        super(materialIn);
        this.setTranslationKey(s);
        this.setRegistryName(s);
        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public TileEntityCraneBase createNewTileEntity(World world, int meta) {
        return new TileEntityCraneInserter();
    }

    @Override
    public boolean canItemEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorItem entity) {
        BlockPos pos = new BlockPos(x, y, z);
        IBlockState state = world.getBlockState(pos);
        EnumFacing orientation = state.getValue(BlockHorizontal.FACING);
        return dir == orientation;
    }

    @Override
    public void onItemEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorItem entity) {
        if (entity == null || entity.getItemStack() == ItemStack.EMPTY || entity.getItemStack().getCount() <= 0) {
            return;
        }

        ItemStack toAdd = entity.getItemStack().copy();
        
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        boolean worked = false;
        if(te instanceof TileEntityCraneInserter)
            worked = ((TileEntityCraneInserter)te).tryFillTeDirect(toAdd);

        if(!worked) {
            EntityItem drop = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, toAdd.copy());
            world.spawnEntity(drop);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        this.dropContents(world, pos, state, 9, 20);
        super.breakBlock(world, pos, state);
    }

}
