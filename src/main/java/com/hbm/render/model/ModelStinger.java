package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelStinger extends ModelBase {

	ModelRenderer B1;
	ModelRenderer B2;
	ModelRenderer B3;
	ModelRenderer E1;
	ModelRenderer E2;
	ModelRenderer E3;
	ModelRenderer F1;
	ModelRenderer F2;
	ModelRenderer F3;
	ModelRenderer D1;
	ModelRenderer D2;
	ModelRenderer D3;
	ModelRenderer F;
	ModelRenderer H1;
	ModelRenderer H2;
	ModelRenderer H3;
	ModelRenderer E4;

	public ModelStinger() {
		textureWidth = 128;
		textureHeight = 64;

		B1 = new ModelRenderer(this, 0, 0);
		B1.addBox(0F, 0F, 0F, 52, 4, 2);
		B1.setRotationPoint(-26F, 0F, -1F);
		B1.setTextureSize(128, 64);
		B1.mirror = true;
		setRotation(B1, 0F, 0F, 0F);
		B2 = new ModelRenderer(this, 0, 6);
		B2.addBox(0F, 0F, 0F, 52, 2, 4);
		B2.setRotationPoint(-26F, 1F, -2F);
		B2.setTextureSize(128, 64);
		B2.mirror = true;
		setRotation(B2, 0F, 0F, 0F);
		B3 = new ModelRenderer(this, 0, 12);
		B3.addBox(0F, 0F, 0F, 52, 3, 3);
		B3.setRotationPoint(-26F, 0.5F, -1.5F);
		B3.setTextureSize(128, 64);
		B3.mirror = true;
		setRotation(B3, 0F, 0F, 0F);
		E1 = new ModelRenderer(this, 0, 18);
		E1.addBox(0F, 0F, 0F, 2, 6, 3);
		E1.setRotationPoint(26F, -1F, -1.5F);
		E1.setTextureSize(128, 64);
		E1.mirror = true;
		setRotation(E1, 0F, 0F, 0F);
		E2 = new ModelRenderer(this, 10, 18);
		E2.addBox(0F, 0F, 0F, 2, 3, 6);
		E2.setRotationPoint(26F, 0.5F, -3F);
		E2.setTextureSize(128, 64);
		E2.mirror = true;
		setRotation(E2, 0F, 0F, 0F);
		E3 = new ModelRenderer(this, 26, 18);
		E3.addBox(0F, 0F, 0F, 2, 5, 5);
		E3.setRotationPoint(26F, -0.5F, -2.5F);
		E3.setTextureSize(128, 64);
		E3.mirror = true;
		setRotation(E3, 0F, 0F, 0F);
		F1 = new ModelRenderer(this, 0, 27);
		F1.addBox(0F, 0F, 0F, 4, 5, 5);
		F1.setRotationPoint(-30F, -0.5F, -2.5F);
		F1.setTextureSize(128, 64);
		F1.mirror = true;
		setRotation(F1, 0F, 0F, 0F);
		F2 = new ModelRenderer(this, 0, 37);
		F2.addBox(0F, 0F, 0F, 4, 6, 3);
		F2.setRotationPoint(-30F, -1F, -1.5F);
		F2.setTextureSize(128, 64);
		F2.mirror = true;
		setRotation(F2, 0F, 0F, 0F);
		F3 = new ModelRenderer(this, 14, 37);
		F3.addBox(0F, 0F, 0F, 4, 3, 6);
		F3.setRotationPoint(-30F, 0.5F, -3F);
		F3.setTextureSize(128, 64);
		F3.mirror = true;
		setRotation(F3, 0F, 0F, 0F);
		D1 = new ModelRenderer(this, 0, 46);
		D1.addBox(0F, 0F, 0F, 16, 8, 3);
		D1.setRotationPoint(-25F, 4F, -1F);
		D1.setTextureSize(128, 64);
		D1.mirror = true;
		setRotation(D1, 0F, 0F, 0F);
		D2 = new ModelRenderer(this, 38, 46);
		D2.addBox(0F, 0F, 0F, 12, 8, 1);
		D2.setRotationPoint(-21F, 4F, -2F);
		D2.setTextureSize(128, 64);
		D2.mirror = true;
		setRotation(D2, 0F, 0F, 0F);
		D3 = new ModelRenderer(this, 34, 38);
		D3.addBox(0F, 0F, 0F, 16, 6, 2);
		D3.setRotationPoint(-21F, 0.5F, -4F);
		D3.setTextureSize(128, 64);
		D3.mirror = true;
		setRotation(D3, 0F, 0F, 0F);
		F = new ModelRenderer(this, 40, 18);
		F.addBox(0F, 0F, 0F, 12, 8, 5);
		F.setRotationPoint(-25F, -8F, -2.5F);
		F.setTextureSize(128, 64);
		F.mirror = true;
		setRotation(F, 0F, 0F, 0F);
		H1 = new ModelRenderer(this, 18, 27);
		H1.addBox(0F, 0F, 0F, 2, 7, 1);
		H1.setRotationPoint(-4F, 4F, -0.5F);
		H1.setTextureSize(128, 64);
		H1.mirror = true;
		// setRotation(H1, 0F, 0F, -0.2617994F);
		setRotation(H1, 0F, 0F, 0F);
		H2 = new ModelRenderer(this, 24, 31);
		H2.addBox(0F, 0F, 0F, 8, 1, 2);
		H2.setRotationPoint(-9F, 4F, -1F);
		H2.setTextureSize(128, 64);
		H2.mirror = true;
		setRotation(H2, 0F, 0F, 0F);
		H3 = new ModelRenderer(this, 44, 31);
		H3.addBox(0F, 0F, 0F, 2, 3, 2);
		H3.setRotationPoint(-12F, 12F, -1F);
		H3.setTextureSize(128, 64);
		H3.mirror = true;
		setRotation(H3, 0F, 0F, 0F);
		E4 = new ModelRenderer(this, 38, 55);
		E4.addBox(0F, 0F, 0F, 8, 6, 2);
		E4.setRotationPoint(16F, -1F, -4F);
		E4.setTextureSize(128, 64);
		E4.mirror = true;
		setRotation(E4, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		B1.render(f5);
		B2.render(f5);
		B3.render(f5);
		E1.render(f5);
		E2.render(f5);
		E3.render(f5);
		F1.render(f5);
		F2.render(f5);
		F3.render(f5);
		D1.render(f5);
		D2.render(f5);
		D3.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		F.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		H1.render(f5);
		H2.render(f5);
		H3.render(f5);
		E4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
