package com.leafia.contents.gear.utility;

import com.hbm.render.item.FFIdentifierRender;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;

public class FuzzyIdentifierBakedModel implements IBakedModel {

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return Collections.emptyList();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return FFIdentifierRender.INSTANCE.itemModelFuzzy.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		FFIdentifierRender.INSTANCE.type = cameraTransformType;
		Pair<? extends IBakedModel, Matrix4f> pair = FFIdentifierRender.INSTANCE.itemModelFuzzy.handlePerspective(cameraTransformType);
		return Pair.of(this, pair.getRight());
	}

}
