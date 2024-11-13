package dev.wooferz.hudlib;

import dev.wooferz.hudlib.config.ConfigManager;
import dev.wooferz.hudlib.hud.BoxHUDElement;
import dev.wooferz.hudlib.hud.ExampleHUDElement;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.slf4j.LoggerFactory;

public class InfoHUDClient implements ClientModInitializer {

	public static final String MOD_ID = "hudlib";

	public static KeyBinding openEditorKey;
	public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		HudRenderCallback.EVENT.register(HudManager::render);
		openEditorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.infohud.openeditor",
				InputUtil.GLFW_KEY_RIGHT_SHIFT,
				"category.infohud.keys"
		));
		ClientTickEvents.END_CLIENT_TICK.register(HudManager::openEditor);



		ConfigManager.getInstance().read();

		ExampleHUDElement exampleHUDElement = new ExampleHUDElement(5, 5, 55, 17, 1, true, true);
		HudManager.registerHudElement(exampleHUDElement);
		BoxHUDElement boxHUDElement = new BoxHUDElement(15, 5, 10, 10, 1);
		//HudManager.registerHudElement(boxHUDElement);
	}
}