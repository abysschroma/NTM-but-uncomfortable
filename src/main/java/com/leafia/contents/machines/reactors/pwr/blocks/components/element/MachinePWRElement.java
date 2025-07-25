package com.leafia.contents.machines.reactors.pwr.blocks.components.element;

import api.hbm.block.IToolable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.items.tool.ItemTooling;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.InventoryHelper;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentBlock;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MachinePWRElement extends BlockMachineBase implements ITooltipProvider, ILookOverlay, PWRComponentBlock {
	public MachinePWRElement() {
		super(Material.IRON,-1,"reactor_element");
		this.setTranslationKey("pwr_element");
		this.setSoundType(ModBlocks.PWR.soundTypePWRTube);
	}
	@Override
	public boolean shouldRenderOnGUI() {
		return true;
	}
	public void check(World world,BlockPos pos) { // Called only on server
		Chunk chunk = world.getChunk(pos);
		TileEntity entity = chunk.getTileEntity(pos,Chunk.EnumCreateEntityType.CHECK);
		if (entity != null) {
			if (entity instanceof TileEntityPWRElement) {
				if (!entity.isInvalid())
					((TileEntityPWRElement) entity).connectUpper();
			}
		} else {
			if (!(world.getBlockState(pos.up()).getBlock() instanceof MachinePWRElement))
				chunk.getTileEntity(pos,Chunk.EnumCreateEntityType.QUEUED);
		}
	}

	@Override
	public void neighborChanged(IBlockState state,World worldIn,BlockPos pos,Block blockIn,BlockPos fromPos) { // Fired only on server
		super.neighborChanged(state,worldIn,pos,blockIn,fromPos);
		check(worldIn,pos);
		beginDiagnosis(worldIn,pos,fromPos);
	}
	@Override
	public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
		MachineTooltip.addMultiblock(tooltip);
		MachineTooltip.addModular(tooltip);
		MachineTooltip.addNuclear(tooltip);
		addStandardInfo(tooltip);
		MachineTooltip.addUpdate(tooltip,"tile.reactor_element.name");
		super.addInformation(stack,player,tooltip,advanced);
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new TileEntityPWRElement();
	}

	public BlockPos getTopElement(World world,BlockPos pos) {
		BlockPos upPos = pos.up();
		while (world.isValid(upPos)) {
			if (world.getBlockState(upPos).getBlock() instanceof MachinePWRElement) {
				pos = upPos;
			} else break;
			upPos = pos.up();
		}
		return pos;
	}

	@Override
	public boolean onBlockActivated(World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		pos = getTopElement(world,pos);
		if (world.getBlockState(pos.up()).getBlock() instanceof MachinePWRElement)
			return false;
		TileEntity entity = world.getTileEntity(pos);
		if (!(entity instanceof TileEntityPWRElement))
			return false;

		TileEntityPWRElement element = (TileEntityPWRElement)entity;
		if (element.inventory == null) return false;

		ItemStack held = player.getHeldItem(hand);
		ItemStack stack = element.inventory.getStackInSlot(0);
		if (held.isEmpty()) return false;
		if (stack.isEmpty()) {
			if (held.getItem() instanceof ItemTooling)
				return false;
			element.inventory.setStackInSlot(0,held);
			if (world.isRemote) return true;
			player.setItemStackToSlot(
					hand.equals(EnumHand.MAIN_HAND) ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND,
					ItemStack.EMPTY
			);
			world.playSound(null,pos,HBMSoundEvents.upgradePlug,SoundCategory.BLOCKS,1,1);
			entity.markDirty();
			return true;
		} else {
			if (held.getItem() instanceof ItemTooling) {
				if (((ItemTooling)(held.getItem())).getType().equals(IToolable.ToolType.SCREWDRIVER)) {
					element.inventory.setStackInSlot(0,ItemStack.EMPTY);
					if (world.isRemote) return true;
					InventoryHelper.spawnItemStack(world,player.posX,player.posY,player.posZ,stack);
					held.damageItem(1,player);
					world.playSound(null,pos,HBMSoundEvents.lockHang,SoundCategory.BLOCKS,0.85f,1);
					world.playSound(null,pos,HBMSoundEvents.pipePlaced,SoundCategory.BLOCKS,0.65f,0.8f);
					entity.markDirty();
					return true;
				}
			}
		}

		return false; //super.onBlockActivated(world,pos,state,player,hand,facing,hitX,hitY,hitZ);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL; // grrrrwl
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void printHook(RenderGameOverlayEvent.Pre event,World world,int x,int y,int z) {
		BlockPos pos = getTopElement(world,new BlockPos(x,y,z));
		if (world.getBlockState(pos.up()).getBlock() instanceof MachinePWRElement)
			return;
		TileEntity entity = world.getTileEntity(pos);
		if (!(entity instanceof TileEntityPWRElement))
			return;
		List<String> texts = new ArrayList<>();
		TileEntityPWRElement element = (TileEntityPWRElement)entity;

		if (element.inventory != null) {
			ItemStack stack = element.inventory.getStackInSlot(0);
			if (stack.isEmpty())
				texts.add("Empty");
			else {
				texts.add(stack.getDisplayName());
				stack.getItem().addInformation(stack,world,texts,ITooltipFlag.TooltipFlags.NORMAL);
			}
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xFF55FF, 0x3F153F, texts);
	}

	@Override
	public boolean tileEntityShouldCreate(World world,BlockPos pos) {
		return !(world.getBlockState(pos.up()).getBlock() instanceof MachinePWRElement);
	}
}
