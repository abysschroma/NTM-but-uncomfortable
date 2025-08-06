package com.hbm.render.tileentity;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
import com.leafia.contents.machines.processing.gascent.GasCentTE;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderGasCent extends TileEntitySpecialRenderer<GasCentTE> {

	@Override
	public boolean isGlobalRenderer(GasCentTE te) {
		return true;
	}
	
	@Override
	public void render(GasCentTE te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		switch(te.getBlockMetadata())
		{
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		GL11.glRotated(90,0,1,0);

		bindTexture(ResourceManager.centrifuge_gas_tex);
        ResourceManager.centrifuge_gas.renderAll();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        
        GL11.glPopMatrix();
	}
}
