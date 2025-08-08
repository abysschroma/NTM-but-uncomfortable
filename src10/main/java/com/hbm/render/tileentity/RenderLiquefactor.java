package com.hbm.render.tileentity;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.oil.TileEntityMachineLiquefactor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderLiquefactor extends TileEntitySpecialRenderer<TileEntityMachineLiquefactor> {

    @Override
    public void render(TileEntityMachineLiquefactor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.liquefactor_tex);
        ResourceManager.liquefactor.renderPart("Main");

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        if(te.tank.getFluidAmount() > 0 && te.tank.getFluid() != null) {
            FFUtils.setRGBFromHex(ModForgeFluids.getFluidColor(te.tank.getFluid().getFluid()));
            double height = (double)te.tank.getFluidAmount() / (double)te.tank.getCapacity();
            GL11.glPushMatrix();
            GL11.glTranslated(0, 1, 0);
            GL11.glScaled(1, height, 1);
            GL11.glTranslated(0, -1, 0);
            ResourceManager.liquefactor.renderPart("Fluid");
            GL11.glPopMatrix();
            GlStateManager.color(1, 1, 1, 1);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(0.75F, 1.0F, 1.0F, 0.15F);
        GL11.glDepthMask(false);

        ResourceManager.liquefactor.renderPart("Glass");

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
