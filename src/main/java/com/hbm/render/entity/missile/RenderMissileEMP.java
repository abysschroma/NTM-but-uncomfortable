package com.hbm.render.entity.missile;

import com.hbm.entity.missile.EntityMissileEMP;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class RenderMissileEMP extends Render<EntityMissileEMP> {


	public static final IRenderFactory<EntityMissileEMP> FACTORY = (RenderManager man) -> {return new RenderMissileEMP(man);};
	
	protected RenderMissileEMP(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityMissileEMP missile, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.enableLighting();
		double[] renderPos = RenderHelper.getRenderPosFromMissile(missile, partialTicks);
		x = renderPos[0];
		y = renderPos[1];
		z = renderPos[2];
		GL11.glTranslated(x, y, z);
        GL11.glRotatef(missile.prevRotationYaw + (missile.rotationYaw - missile.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(missile.prevRotationPitch + (missile.rotationPitch - missile.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(2F, 2F, 2F);

        GL11.glDisable(GL11.GL_CULL_FACE);
        bindTexture(getEntityTexture(missile));
        ResourceManager.missileTaint.renderAll();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityMissileEMP entity) {
		return ResourceManager.missileMicroEMP_tex;
	}
}
