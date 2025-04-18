package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelBoltAction extends ModelBase {

	ModelRenderer Barrel1;
	ModelRenderer Barrel2;
	ModelRenderer Grip;
	ModelRenderer BodyFront;
	ModelRenderer BodyMain;
	ModelRenderer LeverFront;
	ModelRenderer LeverBottom;
	ModelRenderer LeverMid;
	ModelRenderer Trigger;
	ModelRenderer GripFront;
	ModelRenderer GropFrontBottom;
	ModelRenderer GripBottom;
	ModelRenderer Bolt;
	ModelRenderer ChamberFront;
	ModelRenderer ChamberBack;
	ModelRenderer BodyBack;
	ModelRenderer LeverTip;
	ModelRenderer Lever;
	ModelRenderer HandleFront;
	ModelRenderer Pointer;
	ModelRenderer HandleBottom;
	ModelRenderer HandleGrip;

	public ModelBoltAction() {
		textureWidth = 128;
		textureHeight = 64;

		Barrel1 = new ModelRenderer(this, 0, 0);
		Barrel1.addBox(0F, 0F, 0F, 60, 3, 2);
		Barrel1.setRotationPoint(-60F, 1.5F, -1F);
		Barrel1.setTextureSize(64, 32);
		Barrel1.mirror = true;
		setRotation(Barrel1, 0F, 0F, 0F);
		Barrel2 = new ModelRenderer(this, 0, 5);
		Barrel2.addBox(0F, 0F, 0F, 60, 2, 3);
		Barrel2.setRotationPoint(-60F, 2F, -1.5F);
		Barrel2.setTextureSize(64, 32);
		Barrel2.mirror = true;
		setRotation(Barrel2, 0F, 0F, 0F);
		Grip = new ModelRenderer(this, 0, 10);
		Grip.addBox(0F, 0F, 0F, 28, 5, 4);
		Grip.setRotationPoint(-28F, 3F, -2F);
		Grip.setTextureSize(64, 32);
		Grip.mirror = true;
		setRotation(Grip, 0F, 0F, 0F);
		BodyFront = new ModelRenderer(this, 0, 19);
		BodyFront.addBox(0F, 0F, 0F, 3, 7, 4);
		BodyFront.setRotationPoint(0F, 2.5F, -2F);
		BodyFront.setTextureSize(64, 32);
		BodyFront.mirror = true;
		setRotation(BodyFront, 0F, 0F, 0F);
		BodyMain = new ModelRenderer(this, 14, 19);
		BodyMain.addBox(0F, 0F, 0F, 8, 7, 4);
		BodyMain.setRotationPoint(3F, 2.5F, -2F);
		BodyMain.setTextureSize(64, 32);
		BodyMain.mirror = true;
		setRotation(BodyMain, 0F, 0F, 0F);
		LeverFront = new ModelRenderer(this, 62, 30);
		LeverFront.addBox(-1F, 0F, 0F, 2, 4, 2);
		LeverFront.setRotationPoint(7F, 9F, -1F);
		LeverFront.setTextureSize(64, 32);
		LeverFront.mirror = true;
		setRotation(LeverFront, 0F, 0F, 0F);
		LeverBottom = new ModelRenderer(this, 70, 30);
		LeverBottom.addBox(0F, 4F, 0F, 6, 1, 2);
		LeverBottom.setRotationPoint(7F, 9F, -1F);
		LeverBottom.setTextureSize(64, 32);
		LeverBottom.mirror = true;
		setRotation(LeverBottom, 0F, 0F, 0F);
		LeverMid = new ModelRenderer(this, 62, 36);
		LeverMid.addBox(6F, 0F, 0F, 1, 5, 2);
		LeverMid.setRotationPoint(7F, 9F, -1F);
		LeverMid.setTextureSize(64, 32);
		LeverMid.mirror = true;
		setRotation(LeverMid, 0F, 0F, 0F);
		Trigger = new ModelRenderer(this, 88, 30);
		Trigger.addBox(-1F, 0F, 0F, 1, 3, 1);
		Trigger.setRotationPoint(12.5F, 9F, -0.5F);
		Trigger.setTextureSize(64, 32);
		Trigger.mirror = true;
		setRotation(Trigger, 0F, 0F, 0.3490659F);
		GripFront = new ModelRenderer(this, 0, 30);
		GripFront.addBox(0F, 0F, 0F, 18, 3, 4);
		GripFront.setRotationPoint(-46F, 3F, -2F);
		GripFront.setTextureSize(128, 64);
		GripFront.mirror = true;
		setRotation(GripFront, 0F, 0F, 0F);
		GropFrontBottom = new ModelRenderer(this, 0, 37);
		GropFrontBottom.addBox(0F, 0F, 0F, 18, 1, 2);
		GropFrontBottom.setRotationPoint(-46F, 6F, -1F);
		GropFrontBottom.setTextureSize(128, 64);
		GropFrontBottom.mirror = true;
		setRotation(GropFrontBottom, 0F, 0F, 0F);
		GripBottom = new ModelRenderer(this, 0, 40);
		GripBottom.addBox(0F, 0F, 0F, 28, 1, 2);
		GripBottom.setRotationPoint(-28F, 8F, -1F);
		GripBottom.setTextureSize(128, 64);
		GripBottom.mirror = true;
		setRotation(GripBottom, 0F, 0F, 0F);
		Bolt = new ModelRenderer(this, 0, 43);
		Bolt.addBox(0F, 0F, 0F, 10, 2, 2);
		Bolt.setRotationPoint(3F, 2F, -1F);
		Bolt.setTextureSize(128, 64);
		Bolt.mirror = true;
		setRotation(Bolt, 0F, 0F, 0F);
		ChamberFront = new ModelRenderer(this, 0, 47);
		ChamberFront.addBox(0F, 0F, 0F, 3, 1, 3);
		ChamberFront.setRotationPoint(0F, 1.5F, -1.5F);
		ChamberFront.setTextureSize(128, 64);
		ChamberFront.mirror = true;
		setRotation(ChamberFront, 0F, 0F, 0F);
		ChamberBack = new ModelRenderer(this, 12, 47);
		ChamberBack.addBox(0F, 0F, 0F, 3, 1, 3);
		ChamberBack.setRotationPoint(8F, 1.5F, -1.5F);
		ChamberBack.setTextureSize(128, 64);
		ChamberBack.mirror = true;
		setRotation(ChamberBack, 0F, 0F, 0F);
		BodyBack = new ModelRenderer(this, 0, 51);
		BodyBack.addBox(0F, 0F, 0F, 4, 6, 4);
		BodyBack.setRotationPoint(11F, 3.5F, -2F);
		BodyBack.setTextureSize(128, 64);
		BodyBack.mirror = true;
		setRotation(BodyBack, 0F, 0F, 0F);
		LeverTip = new ModelRenderer(this, 24, 43);
		LeverTip.addBox(0F, -1F, 3F, 2, 2, 2);
		LeverTip.setRotationPoint(11F, 3F, 0F);
		LeverTip.setTextureSize(128, 64);
		LeverTip.mirror = true;
		setRotation(LeverTip, -0.4363323F, 0F, 0F);
		Lever = new ModelRenderer(this, 32, 43);
		Lever.addBox(0F, -0.5F, 0F, 1, 1, 4);
		Lever.setRotationPoint(11.5F, 3F, 0F);
		Lever.setTextureSize(128, 64);
		Lever.mirror = true;
		setRotation(Lever, -0.4363323F, 0F, 0F);
		HandleFront = new ModelRenderer(this, 16, 51);
		HandleFront.addBox(0F, 0F, 0F, 9, 5, 4);
		HandleFront.setRotationPoint(15F, 4.5F, -2F);
		HandleFront.setTextureSize(128, 64);
		HandleFront.mirror = true;
		setRotation(HandleFront, 0F, 0F, 0.4363323F);
		Pointer = new ModelRenderer(this, 42, 43);
		Pointer.addBox(0F, 0F, 0F, 3, 1, 1);
		Pointer.setRotationPoint(-49F, 5.5F, -0.5F);
		Pointer.setTextureSize(128, 64);
		Pointer.mirror = true;
		setRotation(Pointer, 0F, 0F, 0F);
		HandleBottom = new ModelRenderer(this, 64, 10);
		HandleBottom.addBox(0F, -8F, 0F, 23, 8, 4);
		HandleBottom.setRotationPoint(19F, 16F, -2F);
		HandleBottom.setTextureSize(128, 64);
		HandleBottom.mirror = true;
		setRotation(HandleBottom, 0F, 0F, 0F);
		HandleGrip = new ModelRenderer(this, 38, 19);
		HandleGrip.addBox(0F, 0F, 0F, 2, 5, 4);
		HandleGrip.setRotationPoint(17F, 9.5F, -2F);
		HandleGrip.setTextureSize(128, 64);
		HandleGrip.mirror = true;
		setRotation(HandleGrip, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Barrel1.render(f5);
		Barrel2.render(f5);
		Grip.render(f5);
		BodyFront.render(f5);
		BodyMain.render(f5);
		LeverFront.render(f5);
		LeverBottom.render(f5);
		LeverMid.render(f5);
		Trigger.render(f5);
		GripFront.render(f5);
		GropFrontBottom.render(f5);
		GripBottom.render(f5);
		Bolt.render(f5);
		ChamberFront.render(f5);
		ChamberBack.render(f5);
		BodyBack.render(f5);
		LeverTip.render(f5);
		Lever.render(f5);
		HandleFront.render(f5);
		Pointer.render(f5);
		HandleBottom.render(f5);
		HandleGrip.render(f5);
		
		
		
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALPHA_BITS);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(-32F / 16F, 0 + 4F / 16F, 0).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        buf.pos(-150, 0, 0).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();;
        tessellator.draw();
        
        GlStateManager.enableLighting();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	public void renderAnim(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float rot, float tran) {

		LeverTip.rotateAngleX += rot;
		Lever.rotateAngleX += rot;
		Bolt.offsetX += tran;
		LeverTip.offsetX += tran;
		Lever.offsetX += tran;
		
		render(entity, f, f1, f2, f3, f4, f5);

		setRotation(LeverTip, -0.4363323F, 0F, 0F);
		setRotation(Lever, -0.4363323F, 0F, 0F);
		Bolt.offsetX -= tran;
		LeverTip.offsetX -= tran;
		Lever.offsetX -= tran;
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
