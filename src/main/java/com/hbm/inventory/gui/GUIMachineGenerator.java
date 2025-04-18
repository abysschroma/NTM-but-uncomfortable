package com.hbm.inventory.gui;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineGenerator;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;
import com.hbm.util.I18nUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUIMachineGenerator extends GuiInfoContainer {

	private static final ResourceLocation TEXTURE = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_generator.png");
	private TileEntityMachineGenerator diFurnace;
	
	public GUIMachineGenerator(EntityPlayer invPlayer, TileEntityMachineGenerator tedf) {
		super(new ContainerMachineGenerator(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 88 - 52, 16, 52, diFurnace.tanks[0], diFurnace.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 88 - 52, 16, 52, diFurnace.tanks[1], diFurnace.tankTypes[1]);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 88 - 52, 16, 52, diFurnace.power, diFurnace.maxPower);

		if(diFurnace.tanks[0].getFluidAmount() <= 0) {
			String[] text = I18nUtil.resolveKeyArray("desc.guimachinegen1");
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		}

		if(diFurnace.tanks[1].getFluidAmount() <= 0) {
			String[] text1 = I18nUtil.resolveKeyArray("desc.guimachinegen2");
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		}
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(diFurnace.hasPower()) {
			int i = (int)diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 62, guiTop + 88 - i, 224, 52 - i, 16, i);
		}
		
		if(diFurnace.hasHeat()) {
			int i = diFurnace.getHeatScaled(52);
			drawTexturedModalRect(guiLeft + 98, guiTop + 88 - i, 208, 52 - i, 16, i);
		}
		
		if(diFurnace.tanks[0].getFluidAmount() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 6);
		
		if(diFurnace.tanks[1].getFluidAmount() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 7);
		
		
		FFUtils.drawLiquid(diFurnace.tanks[0], guiLeft, guiTop, zLevel, 16, 52, 8, 88);
		FFUtils.drawLiquid(diFurnace.tanks[1], guiLeft, guiTop, zLevel, 16, 52, 26, 88);
	}
	
	
}
