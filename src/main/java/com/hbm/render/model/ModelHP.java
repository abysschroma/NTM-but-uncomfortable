package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelHP extends ModelBase {

	ModelRenderer Printer;
	ModelRenderer Hatch;
	ModelRenderer Display;
	ModelRenderer Stock;
	ModelRenderer StockBottom;
	ModelRenderer StockPlate;
	ModelRenderer Barrel;
	ModelRenderer Lens1;
	ModelRenderer Lens2;
	ModelRenderer Lens3;
	ModelRenderer Beam;
	ModelRenderer Handle;
	ModelRenderer Dot;
	ModelRenderer Back;
	ModelRenderer BackShaft;
	ModelRenderer Muzzle;
	ModelRenderer BarrelPlate;

	public ModelHP() {
		textureWidth = 64;
		textureHeight = 64;

		Printer = new ModelRenderer(this, 0, 0);
		Printer.addBox(0F, 0F, 0F, 12, 9, 6);
		Printer.setRotationPoint(-6F, -3F, -3F);
		Printer.setTextureSize(64, 64);
		Printer.mirror = true;
		setRotation(Printer, 0F, 0F, 0F);
		Hatch = new ModelRenderer(this, 0, 15);
		Hatch.addBox(0F, 0F, 0F, 6, 5, 1);
		Hatch.setRotationPoint(-3F, -1F, -3F);
		Hatch.setTextureSize(64, 64);
		Hatch.mirror = true;
		setRotation(Hatch, 0F, 0F, 0F);
		Display = new ModelRenderer(this, 14, 15);
		Display.addBox(0F, -2F, 0F, 2, 2, 1);
		Display.setRotationPoint(3.5F, 0F, -3F);
		Display.setTextureSize(64, 64);
		Display.mirror = true;
		setRotation(Display, 0.7853982F, 0F, 0F);
		Stock = new ModelRenderer(this, 0, 21);
		Stock.addBox(0F, 0F, 0F, 12, 3, 2);
		Stock.setRotationPoint(6F, 0F, -1F);
		Stock.setTextureSize(64, 64);
		Stock.mirror = true;
		setRotation(Stock, 0F, 0F, 0F);
		StockBottom = new ModelRenderer(this, 20, 15);
		StockBottom.addBox(0F, 0F, 0F, 3, 3, 2);
		StockBottom.setRotationPoint(15F, 3F, -1F);
		StockBottom.setTextureSize(64, 64);
		StockBottom.mirror = true;
		setRotation(StockBottom, 0F, 0F, 0F);
		StockPlate = new ModelRenderer(this, 0, 26);
		StockPlate.addBox(-8F, -3F, 0F, 8, 3, 2);
		StockPlate.setRotationPoint(15F, 6F, -1F);
		StockPlate.setTextureSize(64, 64);
		StockPlate.mirror = true;
		setRotation(StockPlate, 0F, 0F, 0.3839724F);
		Barrel = new ModelRenderer(this, 0, 31);
		Barrel.addBox(0F, 0F, 0F, 15, 2, 2);
		Barrel.setRotationPoint(-21F, 0F, -1F);
		Barrel.setTextureSize(64, 64);
		Barrel.mirror = true;
		setRotation(Barrel, 0F, 0F, 0F);
		Lens1 = new ModelRenderer(this, 0, 35);
		Lens1.addBox(0F, 0F, 0F, 1, 3, 3);
		Lens1.setRotationPoint(-19F, -3F, -1.5F);
		Lens1.setTextureSize(64, 64);
		Lens1.mirror = true;
		setRotation(Lens1, 0F, 0F, 0F);
		Lens2 = new ModelRenderer(this, 8, 35);
		Lens2.addBox(0F, 0F, 0F, 1, 4, 4);
		Lens2.setRotationPoint(-16F, -3.5F, -2F);
		Lens2.setTextureSize(64, 64);
		Lens2.mirror = true;
		setRotation(Lens2, 0F, 0F, 0F);
		Lens3 = new ModelRenderer(this, 0, 41);
		Lens3.addBox(0F, 0F, 0F, 1, 3, 3);
		Lens3.setRotationPoint(-13F, -3F, -1.466667F);
		Lens3.setTextureSize(64, 64);
		Lens3.mirror = true;
		setRotation(Lens3, 0F, 0F, 0F);
		Beam = new ModelRenderer(this, 0, 47);
		Beam.addBox(0F, 0F, 0F, 12, 1, 1);
		Beam.setRotationPoint(-18F, -2F, -0.5F);
		Beam.setTextureSize(64, 64);
		Beam.mirror = true;
		setRotation(Beam, 0F, 0F, 0F);
		Handle = new ModelRenderer(this, 18, 35);
		Handle.addBox(0F, 0F, 0F, 2, 6, 1);
		Handle.setRotationPoint(-16F, 2F, -0.5F);
		Handle.setTextureSize(64, 64);
		Handle.mirror = true;
		setRotation(Handle, 0F, 0F, -0.1745329F);
		Dot = new ModelRenderer(this, 0, 49);
		Dot.addBox(0F, 0F, 0F, 2, 1, 1);
		Dot.setRotationPoint(-20F, 2F, -0.5F);
		Dot.setTextureSize(64, 64);
		Dot.mirror = true;
		setRotation(Dot, 0F, 0F, 0F);
		Back = new ModelRenderer(this, 0, 51);
		Back.addBox(-1F, -3F, 0F, 1, 3, 2);
		Back.setRotationPoint(7F, 0F, -1F);
		Back.setTextureSize(64, 64);
		Back.mirror = true;
		setRotation(Back, 0F, 0F, -0.3490659F);
		BackShaft = new ModelRenderer(this, 0, 56);
		BackShaft.addBox(0F, 0F, 0F, 4, 1, 1);
		BackShaft.setRotationPoint(6F, -1F, -0.5F);
		BackShaft.setTextureSize(64, 64);
		BackShaft.mirror = true;
		setRotation(BackShaft, 0F, 0F, 0F);
		Muzzle = new ModelRenderer(this, 0, 58);
		Muzzle.addBox(0F, 0F, 0F, 1, 2, 2);
		Muzzle.setRotationPoint(-6.5F, -2.5F, -1F);
		Muzzle.setTextureSize(64, 64);
		Muzzle.mirror = true;
		setRotation(Muzzle, 0F, 0F, 0F);
		BarrelPlate = new ModelRenderer(this, 6, 58);
		BarrelPlate.addBox(-4F, -2F, 0F, 4, 2, 1);
		BarrelPlate.setRotationPoint(-6F, 4F, -0.5F);
		BarrelPlate.setTextureSize(64, 64);
		BarrelPlate.mirror = true;
		setRotation(BarrelPlate, 0F, 0F, 0.5235988F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Printer.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Hatch.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		Display.render(f5);
		Stock.render(f5);
		StockBottom.render(f5);
		StockPlate.render(f5);
		Barrel.render(f5);
		GL11.glEnable(GL11.GL_BLEND);
		// GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		Lens1.render(f5);
		Lens2.render(f5);
		Lens3.render(f5);
		Beam.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
		Handle.render(f5);
		Dot.render(f5);
		Back.render(f5);
		BackShaft.render(f5);
		Muzzle.render(f5);
		BarrelPlate.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
