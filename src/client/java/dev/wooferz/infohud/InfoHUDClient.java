package dev.wooferz.infohud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class InfoHUDClient implements ClientModInitializer {

	public static KeyBinding openEditorKey;

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
	}
}