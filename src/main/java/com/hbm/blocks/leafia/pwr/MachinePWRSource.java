package com.hbm.blocks.leafia.pwr;

import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.leafia.MachineTooltip;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MachinePWRSource extends BlockBase implements ITooltipProvider {
    public MachinePWRSource() {
        super(Material.IRON,"pwr_source");
        setSoundType(SoundType.METAL);
    }
    @Override
    public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
        MachineTooltip.addMultiblock(tooltip);
        MachineTooltip.addModular(tooltip);
        MachineTooltip.addNuclear(tooltip);
        addStandardInfo(tooltip);
        super.addInformation(stack,player,tooltip,advanced);
    }
}