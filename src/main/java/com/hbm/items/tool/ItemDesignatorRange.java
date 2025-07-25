package com.hbm.items.tool;

import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemDesignatorRange extends ItemDesignator {

    public ItemDesignatorRange(String s) {
        super(s);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getTagCompound() != null) {
            tooltip.add(TextFormatting.GREEN + I18nUtil.resolveKey("desc.targetcoord") + "§r");
            tooltip.add("§aX: " + String.valueOf(stack.getTagCompound().getInteger("xCoord")) + "§r");
            tooltip.add("§aZ: " + String.valueOf(stack.getTagCompound().getInteger("zCoord")) + "§r");
        } else {
            tooltip.add(TextFormatting.YELLOW + I18nUtil.resolveKey("desc.choosetarget3"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        RayTraceResult mpos = Library.rayTrace(player, 300, 1);
        if (mpos.typeOfHit != Type.BLOCK)
            return super.onItemRightClick(world, player, hand);
        int x = mpos.getBlockPos().getX();
        int z = mpos.getBlockPos().getZ();
        BlockPos pos = mpos.getBlockPos();
        ItemStack stack = player.getHeldItem(hand);

        if (!(world.getBlockState(pos) instanceof LaunchPad)) {
            if (stack.getTagCompound() == null)
                stack.setTagCompound(new NBTTagCompound());

            stack.getTagCompound().setInteger("xCoord", x);
            stack.getTagCompound().setInteger("yCoord", mpos.getBlockPos().getY());
            stack.getTagCompound().setInteger("zCoord", z);

            if (world.isRemote) {
                player.sendMessage(new TextComponentTranslation(TextFormatting.GREEN + I18nUtil.resolveKey("chat.possetxz", x, z)));
            }

            world.playSound(player.posX, player.posY, player.posZ, HBMSoundEvents.techBleep, SoundCategory.PLAYERS, 1.0F, 1.0F, true);

            return super.onItemRightClick(world, player, hand);
        }

        return super.onItemRightClick(world, player, hand);
    }
}
