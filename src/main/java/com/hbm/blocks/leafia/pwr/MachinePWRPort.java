package com.hbm.blocks.leafia.pwr;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.generic.BlockRadResistant;
import com.hbm.blocks.leafia.MachineTooltip;
import com.hbm.tileentity.leafia.pwr.TileEntityPWRPort;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MachinePWRPort extends BlockRadResistant implements ITooltipProvider, ITileEntityProvider, PWRCore {
    public MachinePWRPort() {
        super(Material.IRON,"pwr_port");
    }
    @Override
    public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
        MachineTooltip.addMultiblock(tooltip);
        MachineTooltip.addModular(tooltip);
        addStandardInfo(tooltip);
        super.addInformation(stack,player,tooltip,advanced);
    }

    @Override
    public boolean tileEntityShouldCreate(World world,BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn,int meta) {
        return new TileEntityPWRPort();
    }
}