package com.hbm.tileentity.machine;

import com.hbm.inventory.leafia.inventoryutils.LeafiaPacket;
import com.hbm.inventory.leafia.inventoryutils.LeafiaPacketReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemLens;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoreStabilizer extends TileEntityMachineBase implements ITickable, IEnergyUser, LeafiaPacketReceiver {

	public long power;
	public static final long maxPower = 10000000000000L;

	@Override
	public String getPacketIdentifier() {
		return "dfc_stabilizer";
	}
	@SideOnly(Side.CLIENT)
	public int outerColor = LensType.STANDARD.outerColor;
	@SideOnly(Side.CLIENT)
	public int innerColor = LensType.STANDARD.innerColor;
	@SideOnly(Side.CLIENT)
	@Override
	public void onReceivePacketLocal(byte key, Object value) {
		if (key == 0)
			this.beam = (int)value;
		if (key == 1)
			this.outerColor = (int)value;
		if (key == 2)
			this.innerColor = (int)value;
	}
	@Override
	public void onReceivePacketServer(byte key, Object value, EntityPlayer plr) {}

	public enum LensType {
		STANDARD(0x0c222c,0x7F7F7F),
		BLANK(0x121212,0x646464),
		LIMITER(0x001733,0x7F7F7F),
		BOOSTER(0x4f1600,0x7F7F7F),
		OMEGA(0x64001e,0x9A9A9A);
		public int outerColor;
		public int innerColor;
		LensType(int outerColor,int innerColor) {
			this.outerColor = outerColor;
			this.innerColor = innerColor;
		}
	}
	public int watts;
	public int beam;
	public LensType lens = LensType.STANDARD;
	public boolean isOn;
	
	public static final int range = 15;
	
	public TileEntityCoreStabilizer() {
		super(1);
		
	}

	@Override
	public void update() {
		if(!world.isRemote) {

			this.updateStandardConnections(world, pos);
			
			watts = MathHelper.clamp(watts, 1, 100);
			long demand = (long) Math.pow(watts, 6);
			isOn = false;

			beam = 0;

			ItemLens lens = null;
			if(inventory.getStackInSlot(0).getItem() instanceof ItemLens){
				lens = (ItemLens) inventory.getStackInSlot(0).getItem();
				if (lens == ModItems.ams_focus_blank)
					this.lens = LensType.BLANK;
				else if (lens == ModItems.ams_lens)
					this.lens = LensType.STANDARD;
				else if (lens == ModItems.ams_focus_limiter)
					this.lens = LensType.LIMITER;
				else if (lens == ModItems.ams_focus_booster)
					this.lens = LensType.BOOSTER;
				else if (lens == ModItems.ams_focus_omega)
					this.lens = LensType.OMEGA;
			}

			if(lens != null && power >= demand * lens.drainMod) {
				isOn = true;
				EnumFacing dir = EnumFacing.getFront(this.getBlockMetadata());
				for(int i = 1; i <= range; i++) {
	
					int x = pos.getX() + dir.getFrontOffsetX() * i;
					int y = pos.getY() + dir.getFrontOffsetY() * i;
					int z = pos.getZ() + dir.getFrontOffsetZ() * i;
					BlockPos pos1 = new BlockPos(x, y, z);
					
					TileEntity te = world.getTileEntity(pos1);
	
					if(te instanceof TileEntityCore) {
						
						TileEntityCore core = (TileEntityCore)te;
						core.field += (int)(watts * lens.fieldMod);
						this.power -= (long)(demand * lens.drainMod);
						beam = i;
						
						long dmg = ItemLens.getLensDamage(inventory.getStackInSlot(0));
						dmg += watts;
						
						if(dmg >= lens.maxDamage)
							inventory.setStackInSlot(0, ItemStack.EMPTY);
						else
							ItemLens.setLensDamage(inventory.getStackInSlot(0), dmg);
						
						break;
					}

					if(te instanceof TileEntityCoreStabilizer)
						continue;
					
					if(world.getBlockState(pos1).getBlock() != Blocks.AIR)
						break;
				}
			}
			//PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, beam, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 250));
			LeafiaPacket._start(this)
					.__write((byte)0,this.beam)
					.__write((byte)1,this.lens.outerColor)
					.__write((byte)2,this.lens.innerColor)
					.__sendToClients(250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		power = data.getLong("power");
		watts = data.getInteger("watts");
		isOn = data.getBoolean("isOn");
	}
	
	@Override
	public String getName() {
		return "container.dfcStabilizer";
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getWattsScaled(int i) {
		return (watts * i) / 100;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		watts = compound.getInteger("watts");
		isOn = compound.getBoolean("isOn");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setInteger("watts", watts);
		compound.setBoolean("isOn", isOn);
		return super.writeToNBT(compound);
	}
}