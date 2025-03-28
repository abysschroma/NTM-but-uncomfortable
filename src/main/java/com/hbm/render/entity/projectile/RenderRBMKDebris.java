package com.hbm.render.entity.projectile;

import com.hbm.entity.projectile.EntityRBMKDebris;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class RenderRBMKDebris extends Render<EntityRBMKDebris> {

	public static final IRenderFactory<EntityRBMKDebris> FACTORY = man -> new RenderRBMKDebris(man);
	
	//for fallback only
	private static final ResourceLocation tex_base = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_side.png");
	private static final ResourceLocation tex_element = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_element_fuelchunk.png");
	private static final ResourceLocation tex_control = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control.png");
	private static final ResourceLocation tex_blank = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_blank.png");
	private static final ResourceLocation tex_graphite = new ResourceLocation(RefStrings.MODID + ":textures/blocks/block_graphite.png");

	protected RenderRBMKDebris(RenderManager renderManager){
		super(renderManager);
	}

	@Override
	public void doRender(EntityRBMKDebris entity, double x, double y, double z, float entityYaw, float partialTicks){
		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 0.125D, z);
		
		EntityRBMKDebris debris = (EntityRBMKDebris)entity;

		GL11.glRotatef(debris.getEntityId() % 360, 0, 1, 0); //rotate based on entity ID to add unique randomness
		GL11.glRotatef(debris.lastRot + (debris.rot - debris.lastRot) * partialTicks, 1, 1, 1);
		
		DebrisType type = debris.getType();
		
		switch(type) {
		case BLANK: bindTexture(tex_blank); ResourceManager.deb_blank.renderAll(); break;
		case ELEMENT: bindTexture(tex_element); ResourceManager.deb_element.renderAll(); break;
		case FUEL: bindTexture(tex_element); ResourceManager.deb_fuel.renderAll(); break;
		case GRAPHITE: bindTexture(tex_graphite); ResourceManager.deb_graphite.renderAll(); break;
		case LID: bindTexture(tex_blank); ResourceManager.deb_lid.renderAll(); break;
		case ROD: bindTexture(tex_control); ResourceManager.deb_rod.renderAll(); break;
		default: break;
		}
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRBMKDebris entity){
		return tex_base;
	}

}
