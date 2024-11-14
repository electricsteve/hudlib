package dev.wooferz.hudlib;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.wooferz.hudlib.config.ConfigManager;
import dev.wooferz.hudlib.config.ElementConfig;
import dev.wooferz.hudlib.hud.BoxHUDElement;
import dev.wooferz.hudlib.hud.ExampleHUDElement;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.slf4j.LoggerFactory;

public class InfoHUDClient implements ClientModInitializer {

	public static final String MOD_ID = "hudlib";

	public static boolean myBooleanOption;

	public static KeyBinding openEditorKey;
	public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		if (!HudManager.isElementsConfigLoaded) {

			ElementConfig.HANDLER.load();
			HudManager.isElementsConfigLoaded = true;
		}
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		HudRenderCallback.EVENT.register(HudManager::render);
		openEditorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.hudlib.openeditor",
				InputUtil.GLFW_KEY_RIGHT_SHIFT,
				"category.hudlib.keys"
		));
		ClientTickEvents.END_CLIENT_TICK.register(HudManager::openEditor);



		ConfigManager.getInstance().read();

		ExampleHUDElement exampleHUDElement = new ExampleHUDElement(5, 5, 55, 17, 1);
		HudManager.registerHudElement(exampleHUDElement);
		BoxHUDElement boxHUDElement = new BoxHUDElement(15, 5, 10, 10, 1);
		//HudManager.registerHudElement(boxHUDElement);

	}


}