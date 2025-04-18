package com.leafia.contents.machines.processing.liquefactor;

import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class LiquefactorRender extends TileEntitySpecialRenderer<TileEntity> {

	@Override
	public boolean isGlobalRenderer(TileEntity te) {
		return true;
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		LiquefactorTE liq = (LiquefactorTE)te;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		mdl.__getModel().renderPart("Main");

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		if(liq.tank.getFluid() != null) {
			int color = liq.tank.getFluid().getFluid().getColor();
			GL11.glColor3ub((byte) ((color & 0xFF0000) >> 16), (byte) ((color & 0x00FF00) >> 8), (byte) ((color & 0x0000FF) >> 0));

			double height = (double)liq.tank.getFluidAmount() / (double)liq.tank.getCapacity();
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1, 0);
			GL11.glScaled(1, height, 1);
			GL11.glTranslated(0, -1, 0);
			mdl.__getModel().renderPart("Fluid");
			GL11.glPopMatrix();
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(0.75F, 1.0F, 1.0F, 0.15F);
		GL11.glDepthMask(false);

		mdl.__getModel().renderPart("Glass");

		GL11.glDepthMask(true);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}
