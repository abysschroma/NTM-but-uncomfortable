package com.hbm.render.item;

import com.hbm.items.ModItems.Armory;
import com.hbm.main.ResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ItemRenderObj extends TEISRBase {

	
	@Override
	public void renderByItem(ItemStack item) {
		if(item.getItem() == Armory.gun_brimstone)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.brimstone_tex);
		switch(type) {
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(2, 0, 0);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(40, 1, 0, 0);
			break;
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glTranslated(-0.9, -0.2, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(40, 1, 0, 0);
			break;
		case HEAD:
		case FIXED:
		case GROUND:
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
			GL11.glTranslated(0.5, -0.5, -0.85);
			break;
		default:
			break;
		}
		if(item.getItem() == Armory.gun_brimstone){
			GlStateManager.disableCull();
			ResourceManager.brimstone.renderAll();
			GlStateManager.enableCull();
		}
	}
}
