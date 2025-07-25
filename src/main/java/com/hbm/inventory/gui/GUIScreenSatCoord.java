package com.hbm.inventory.gui;

import com.hbm.items.tool.ItemSatInterface;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.SatCoordPacket;
import com.hbm.saveddata.satellites.Satellite.CoordActions;
import com.hbm.saveddata.satellites.Satellite.Interfaces;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GUIScreenSatCoord extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/satellites/gui_sat_coord.png");
    protected int xSize = 176;
    protected int ySize = 126;
    protected int guiLeft;
    protected int guiTop;
    private final EntityPlayer player;

    private GuiTextField xField;
    private GuiTextField yField;
    private GuiTextField zField;
    
    public GUIScreenSatCoord(EntityPlayer player) {
    	
    	this.player = player;
    }
    
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        Keyboard.enableRepeatEvents(true);
        this.xField = new GuiTextField(0, this.fontRenderer, guiLeft + 66, guiTop + 21, 48, 12);
        this.xField.setTextColor(-1);
        this.xField.setDisabledTextColour(-1);
        this.xField.setEnableBackgroundDrawing(false);
        this.xField.setMaxStringLength(10);
        this.yField = new GuiTextField(1, this.fontRenderer, guiLeft + 66, guiTop + 56, 48, 12);
        this.yField.setTextColor(-1);
        this.yField.setDisabledTextColour(-1);
        this.yField.setEnableBackgroundDrawing(false);
        this.yField.setMaxStringLength(10);
        this.zField = new GuiTextField(2, this.fontRenderer, guiLeft + 66, guiTop + 92, 48, 12);
        this.zField.setTextColor(-1);
        this.zField.setDisabledTextColour(-1);
        this.zField.setEnableBackgroundDrawing(false);
        this.zField.setMaxStringLength(10);
        if(ItemSatInterface.currentSat != null){
        	xField.setText(Integer.toString((int) player.posX));
        	if(ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y))
        		yField.setText(Integer.toString((int) player.posY));
            zField.setText(Integer.toString((int) player.posZ));
        }
        
    }
    
    protected void mouseClicked(int i, int j, int k) throws IOException
    {
        super.mouseClicked(i, j, k);
    	if(ItemSatInterface.currentSat == null)
    		return;
    	
        this.xField.mouseClicked(i, j, k);
        if(ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y)) this.yField.mouseClicked(i, j, k);
        this.zField.mouseClicked(i, j, k);

    	if(i >= this.guiLeft + 133 && i < this.guiLeft + 133 + 18 && j >= this.guiTop + 52 && j < this.guiTop + 52 + 18 && player != null) {
    		
    		if(NumberUtils.isCreatable(xField.getText()) && NumberUtils.isCreatable(zField.getText())) {

    			if(ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y)) {
    				
    				if(NumberUtils.isCreatable(yField.getText())) {
    					
        	    		mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(HBMSoundEvents.techBleep, 1.0F));
        	    		PacketDispatcher.wrapper.sendToServer(
        	    				new SatCoordPacket(
        	    						(int)Double.parseDouble(xField.getText()),
        	    						(int)Double.parseDouble(yField.getText()),
        	    						(int)Double.parseDouble(zField.getText()),
        	    						ItemSatInterface.getFreq(player.getHeldItemMainhand())));
        	    		
        	            this.mc.player.closeScreen();
    				}
    				
    			} else {
    	    		
    	    		mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(HBMSoundEvents.techBleep, 1.0F));
    	    		PacketDispatcher.wrapper.sendToServer(
    	    				new SatCoordPacket(
    	    						(int)Double.parseDouble(xField.getText()),
    	    						0,
    	    						(int)Double.parseDouble(zField.getText()),
    	    						ItemSatInterface.getFreq(player.getHeldItemMainhand())));
    	    		
    	            this.mc.player.closeScreen();
    			}
    		}
    	}
    }
    
    public void drawScreen(int mouseX, int mouseY, float f) {
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
        GlStateManager.enableLighting();
        GlStateManager.disableLighting();
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.enableLighting();
    }
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

        this.xField.drawTextBox();
        if(ItemSatInterface.currentSat != null && ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y)) this.yField.drawTextBox();
        this.zField.drawTextBox();
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(xField.isFocused())
			drawTexturedModalRect(guiLeft + 61, guiTop + 16, 0, 126, 54, 18);

		if(yField.isFocused())
			drawTexturedModalRect(guiLeft + 61, guiTop + 52, 0, 126, 54, 18);

		if(zField.isFocused())
			drawTexturedModalRect(guiLeft + 61, guiTop + 88, 0, 126, 54, 18);
		
		if(ItemSatInterface.currentSat != null) {
			
			if(!ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y))
				drawTexturedModalRect(guiLeft + 61, guiTop + 52, 0, 144, 54, 18);
			
			drawTexturedModalRect(guiLeft + 120, guiTop + 17, 194, 0, 7, 7);
			
			if(ItemSatInterface.currentSat.satIface == Interfaces.SAT_COORD) {
				
				drawTexturedModalRect(guiLeft + 120, guiTop + 25, 194, 0, 7, 7);
			}
		}
	}
	
	@Override
	public void drawBackground(int tint) {
		super.drawDefaultBackground();
		super.drawBackground(tint);
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
    	

        if (this.xField.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
        } else if (ItemSatInterface.currentSat != null && ItemSatInterface.currentSat.coordAcs.contains(CoordActions.HAS_Y) && this.yField.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
        } else if (this.zField.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
        } else {
        	
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    	
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.player.closeScreen();
        }
        
    }

}