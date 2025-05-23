package com.hbm.render.entity;

import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.lib.RefStrings;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderTaintedCreeper extends RenderLiving<EntityTaintedCreeper> {

    private static final ResourceLocation creeperTextures = new ResourceLocation(RefStrings.MODID + ":" + "textures/entity/creeper_tainted.png");
	
	public RenderTaintedCreeper(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelCreeper(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTaintedCreeper entity) {
		return creeperTextures;
	}
	
	@Override
	protected void preRenderCallback(EntityTaintedCreeper entitylivingbaseIn, float partialTickTime) {
		float f1 = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        f1 *= f1;
        f1 *= f1;
        float f3 = (1.0F + f1 * 0.4F) * f2;
        float f4 = (1.0F + f1 * 0.1F) / f2;
        GL11.glScalef(f3, f4, f3);
	}
	
	@Override
	protected int getColorMultiplier(EntityTaintedCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime) {
		float f2 = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

        if ((int)(f2 * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int)(f2 * 0.2F * 255.0F);

            if (i < 0)
            {
                i = 0;
            }

            if (i > 255)
            {
                i = 255;
            }

            short short1 = 255;
            short short2 = 255;
            short short3 = 255;
            return i << 24 | short1 << 16 | short2 << 8 | short3;
        }
	}

}
