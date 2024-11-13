package dev.wooferz.hudlib;
import dev.wooferz.hudlib.config.Config;
import dev.wooferz.hudlib.config.ConfigManager;
import dev.wooferz.hudlib.hud.HUDElement;
import dev.wooferz.hudlib.screens.EditScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static dev.wooferz.hudlib.InfoHUDClient.openEditorKey;

public class HudManager {

    public static ArrayList<HUDElement> hudElements = new ArrayList<HUDElement>();
    public static HashMap<String, Rect2i> hudPositions = new HashMap<String, Rect2i>();
    public static HashMap<String, Boolean> hudEnabled = new HashMap<String, Boolean>();
    public static HashMap<String, HudAnchor> hudAnchors = new HashMap<String, HudAnchor>();

    public static void render(DrawContext context, float tickDelta) {


        for (int i = 0; i < hudElements.size(); i++) {
            HUDElement element = hudElements.get(i);
            if (hudEnabled.get(element.identifier) || MinecraftClient.getInstance().currentScreen instanceof EditScreen) {
                Rect2i positionUnanchored = hudPositions.get(element.identifier);
                Rect2i position = hudAnchors.get(element.identifier).convert(positionUnanchored);
                element.render(position.getX(), position.getY(), position.getWidth(), position.getHeight(), context, tickDelta);
            }
        }

    }
    public static void openEditor(MinecraftClient minecraftClient) {
        if (openEditorKey.wasPressed()) {
            minecraftClient.setScreen(new EditScreen(Text.of("")));
        }
    }
    public static void registerHudElement(HUDElement hudElement) {
        ConfigManager.getInstance().read();
        hudElements.add(hudElement);
        Config config = ConfigManager.getInstance().config;
        Rect2i position;
        boolean enabled = true;
        HudAnchor anchor = null;
        if (config.getElement(hudElement.identifier) != null) {
            position = config.getElement(hudElement.identifier).position;
            if (!hudElement.canResize()) {
                position.setWidth(hudElement.defaultWidth);
                position.setHeight(hudElement.defaultHeight);
            }
            if (config.getElement(hudElement.identifier).anchor != null) {
                anchor = config.getElement(hudElement.identifier).anchor;
            }
            enabled = config.getElement(hudElement.identifier).enabled;
        } else {
            position = new Rect2i(hudElement.defaultX, hudElement.defaultY, hudElement.defaultWidth, hudElement.defaultHeight);
        }
        if (anchor == null) {
            anchor = new HudAnchor(hudElement.defaultAnchorRight, hudElement.defaultAnchorBottom);
        }
        hudPositions.put(hudElement.identifier, position);
        hudEnabled.put(hudElement.identifier, enabled);
        hudAnchors.put(hudElement.identifier, anchor);
    }

}
