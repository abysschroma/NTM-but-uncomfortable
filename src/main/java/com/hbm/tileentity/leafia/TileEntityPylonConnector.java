package com.hbm.tileentity.leafia;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.LeafiaQuickModel;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.tileentity.RenderPylonConnector;
import com.hbm.tileentity.network.energy.TileEntityPylonBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TileEntityPylonConnector extends TileEntityPylonBase implements LeafiaQuickModel {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3[] getMountPos() {
		return new Vec3[]{new Vec3(0.5D, 0.5D, 0.5D)};
	}

	@Override
	public int getMaxWireLength() {
		return 10;
	}
	
	@Override
	public List<BlockPos> getConnectionPoints() {
		List<BlockPos> positions = new ArrayList(connected);
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			positions.add(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ));
		}
		return positions;
	}

	@Override
	public String _resourcePath() {
		return "pylon_connector";
	}

	@Override
	public String _assetPath() {
		return "network/connector";
	}

	@Override
	public TileEntitySpecialRenderer _renderer() {
		return new RenderPylonConnector();
	}

	@Override
	public Block _block() {
		return ModBlocks.red_connec;
	}

	@Override
	public double _sizeReference() {
		return 0.8;
	}
	@Override
	public double _itemYoffset() {
		return 1.25;
	}
}