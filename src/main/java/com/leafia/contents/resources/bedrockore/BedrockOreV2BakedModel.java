package com.leafia.contents.resources.bedrockore;

import com.leafia.contents.resources.bedrockore.BedrockOreV2Item.V2Type;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;

public class BedrockOreV2BakedModel implements IBakedModel {
	public final V2Type type;
	public final int meta;
	public final IBakedModel original;
	public BedrockOreV2BakedModel(V2Type type,int meta,IBakedModel original) {
		this.type = type;
		this.meta = meta;
		this.original = original;
	}

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
		return BedrockOreV2Render.INSTANCE.itemModels.get(type)[meta].getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	@Override
	public Pair<? extends IBakedModel,Matrix4f> handlePerspective(TransformType cameraTransformType) {
		BedrockOreV2Render.INSTANCE.type = cameraTransformType;
		Pair<? extends IBakedModel, Matrix4f> par = BedrockOreV2Render.INSTANCE.itemModels.get(type)[meta].handlePerspective(cameraTransformType);
		return Pair.of(this, par.getRight());
	}
}
