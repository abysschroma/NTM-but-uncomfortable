package com.leafia.contents.machines.reactors.zirnox.debris;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.leafia.contents.machines.reactors.zirnox.debris.ZirnoxDebrisEntity.DebrisType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class ZirnoxDebrisRender extends Render<ZirnoxDebrisEntity> {

	public static final IRenderFactory<ZirnoxDebrisEntity> FACTORY = man -> new ZirnoxDebrisRender(man);

	//for fallback only
	public static final ResourceLocation tex_graphite = new ResourceLocation(RefStrings.MODID + ":textures/blocks/block_graphite.png");
	private static final ResourceLocation tex_rod = new ResourceLocation(RefStrings.MODID + ":textures/models/leafia/zirnox_deb_element.png");

	protected ZirnoxDebrisRender(RenderManager renderManager){
		super(renderManager);
	}

	@Override
	public void doRender(ZirnoxDebrisEntity entity,double x,double y,double z,float entityYaw,float partialTicks){
		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 0.125D, z);

		ZirnoxDebrisEntity debris = (ZirnoxDebrisEntity)entity;

		GL11.glRotatef(debris.getEntityId() % 360, 0, 1, 0); //rotate based on entity ID to add unique randomness
		GL11.glRotatef(debris.lastRot + (debris.rot - debris.lastRot) * partialTicks, 1, 1, 1);
		
		DebrisType type = debris.getType();
		
		switch(type) {
			case BLANK: bindTexture(ResourceManager.zirnox_tex); ResourceManager.deb_zirnox_blank.renderAll(); break;
			case ELEMENT: bindTexture(tex_rod); ResourceManager.deb_zirnox_element.renderAll(); break;
			case SHRAPNEL: bindTexture(ResourceManager.zirnox_tex); ResourceManager.deb_zirnox_shrapnel.renderAll(); break;
			case GRAPHITE: bindTexture(tex_graphite); ResourceManager.deb_graphite.renderAll(); break;
			case CONCRETE: bindTexture(ResourceManager.zirnox_destroyed_tex); ResourceManager.deb_zirnox_concrete.renderAll(); break;
			case EXCHANGER: bindTexture(ResourceManager.zirnox_tex); ResourceManager.deb_zirnox_exchanger.renderAll(); break;
			default: break;
		}
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(ZirnoxDebrisEntity entity){
		return tex_graphite;
	}

}
