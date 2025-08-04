package com.leafia.contents.machines.reactors.zirnox;

import com.hbm.main.ResourceManager;
import com.leafia.contents.machines.reactors.zirnox.container.DestroyedZirnoxTE;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class DestroyedZirnoxRender extends TileEntitySpecialRenderer<DestroyedZirnoxTE> {

	@Override
	public boolean isGlobalRenderer(DestroyedZirnoxTE fel) {
		return true;
	}

	@Override
	public void render(DestroyedZirnoxTE tileEntity,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		switch(tileEntity.getBlockMetadata() - 10) {
			case 2:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.zirnox_destroyed_tex);
		ResourceManager.zirnox_destroyed.renderAll();

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}