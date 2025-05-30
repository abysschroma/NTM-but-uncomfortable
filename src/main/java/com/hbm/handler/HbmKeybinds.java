package com.hbm.handler;

import com.hbm.capability.HbmCapability;
import com.hbm.capability.HbmCapability.IHBMData;
import com.hbm.main.MainRegistry;
import com.hbm.packet.KeybindPacket;
import com.hbm.packet.PacketDispatcher;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

public class HbmKeybinds {

	public static final String category = "key.categories.hbm";
	
	public static KeyBinding jetpackKey = new KeyBinding(category + ".toggleBack", Keyboard.KEY_C, category);
	public static KeyBinding hudKey = new KeyBinding(category + ".toggleHUD", Keyboard.KEY_V, category);
	public static KeyBinding reloadKey = new KeyBinding(category + ".reload", Keyboard.KEY_R, category);

	public static KeyBinding craneUpKey = new KeyBinding(category + ".craneMoveUp", Keyboard.KEY_UP, category);
	public static KeyBinding craneDownKey = new KeyBinding(category + ".craneMoveDown", Keyboard.KEY_DOWN, category);
	public static KeyBinding craneLeftKey = new KeyBinding(category + ".craneMoveLeft", Keyboard.KEY_LEFT, category);
	public static KeyBinding craneRightKey = new KeyBinding(category + ".craneMoveRight", Keyboard.KEY_RIGHT, category);
	public static KeyBinding craneLoadKey = new KeyBinding(category + ".craneLoad", Keyboard.KEY_RETURN, category);
	
	public static void register() {
		ClientRegistry.registerKeyBinding(jetpackKey);
		ClientRegistry.registerKeyBinding(hudKey);
		ClientRegistry.registerKeyBinding(reloadKey);

		ClientRegistry.registerKeyBinding(craneUpKey);
		ClientRegistry.registerKeyBinding(craneDownKey);
		ClientRegistry.registerKeyBinding(craneLeftKey);
		ClientRegistry.registerKeyBinding(craneRightKey);
		ClientRegistry.registerKeyBinding(craneLoadKey);
	}
	
	@SubscribeEvent
	public void keyEvent(KeyInputEvent event) {
		
		IHBMData props = HbmCapability.getData(MainRegistry.proxy.me());
		
		for(EnumKeybind key : EnumKeybind.values()) {
			boolean last = props.getKeyPressed(key);
			boolean current = MainRegistry.proxy.getIsKeyPressed(key);
			
			if(last != current) {
				PacketDispatcher.wrapper.sendToServer(new KeybindPacket(key, current));
				props.setKeyPressed(key, current);
			}
		}
	}
	
	public static enum EnumKeybind {
		JETPACK,
		TOGGLE_JETPACK,
		TOGGLE_HEAD,
		RELOAD,
		CRANE_UP,
		CRANE_DOWN,
		CRANE_LEFT,
		CRANE_RIGHT,
		CRANE_LOAD
	}
}
