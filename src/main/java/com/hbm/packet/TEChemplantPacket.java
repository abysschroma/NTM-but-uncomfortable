package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TEChemplantPacket extends RecordablePacket {

	int x;
	int y;
	int z;
	boolean isProgressing;

	public TEChemplantPacket()
	{
		
	}

	public TEChemplantPacket(int x, int y, int z, boolean isProgressing)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.isProgressing = isProgressing;
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		isProgressing = buf.readBoolean();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(isProgressing);
	}

	public static class Handler implements IMessageHandler<TEChemplantPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEChemplantPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

				if (te != null && te instanceof TileEntityMachineChemplant) {
						
					TileEntityMachineChemplant gen = (TileEntityMachineChemplant) te;
					gen.isProgressing = m.isProgressing;
				}
			});
			
			return null;
		}
	}
}
