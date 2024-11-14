package dev.wooferz.hudlib;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.wooferz.hudlib.config.ColorTypeAdapter;
import dev.wooferz.hudlib.config.Config;
import dev.wooferz.hudlib.config.ConfigManager;
import dev.wooferz.hudlib.config.ElementConfig;
import dev.wooferz.hudlib.hud.HUDConfig;
import dev.wooferz.hudlib.hud.HUDElement;
import dev.wooferz.hudlib.screens.EditScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static dev.wooferz.hudlib.InfoHUDClient.openEditorKey;
import static dev.wooferz.hudlib.InfoHUDClient.LOGGER;

public class HudManager {

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Color.class,new ColorTypeAdapter())
            .create();

    public static ArrayList<HUDElement> hudElements = new ArrayList<HUDElement>();
    public static HashMap<String, Rect2i> hudPositions = new HashMap<String, Rect2i>();
    public static HashMap<String, Boolean> hudEnabled = new HashMap<String, Boolean>();
    public static HashMap<String, HudAnchor> hudAnchors = new HashMap<String, HudAnchor>();

    public static boolean isElementsConfigLoaded = false;

    public static void addConfigOptionGroups(ConfigCategory.Builder builder) {
        for (int i = 0; i < hudElements.size(); i++) {
            HUDElement element = hudElements.get(i);
            OptionGroup optionGroup = element.generateConfig();
            if (optionGroup != null) {
                builder.group(optionGroup);
            }
        }
    }

    public static void render(DrawContext context, float tickDelta) {

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

       /* context.drawVerticalLine(width/3, 0, height, 0xFFFFFFFF);
        context.drawVerticalLine(width*2/3, 0, height, 0xFFFFFFFF);
        context.drawHorizontalLine(0, width, height/3, 0xFFFFFFFF);
        context.drawHorizontalLine(0, width, height*2/3, 0xFFFFFFFF);*/

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
            anchor = new HudAnchor(hudElement.defaultHorizontalAnchor, hudElement.defaultVerticalAnchor);
        }
        hudPositions.put(hudElement.identifier, position);
        hudEnabled.put(hudElement.identifier, enabled);
        hudAnchors.put(hudElement.identifier, anchor);

        if (!isElementsConfigLoaded) {
            ElementConfig.HANDLER.load();

            isElementsConfigLoaded = true;
        }

        String elementConfigString = ElementConfig.HANDLER.instance().elementConfigs.get(hudElement.identifier);
        HUDConfig elementConfig = (HUDConfig) gson.fromJson(elementConfigString, hudElement.getConfigType());
        hudElement.setConfig(elementConfig);

        LOGGER.info("Successfully registered " + hudElement.displayName + ".");

    }

    public static void saveConfig() {
        ArrayList<HUDElement> elements = hudElements;
        for (int i = 0; i < elements.size(); i++) {
            HUDElement element = elements.get(i);
            HUDConfig config = element.getConfig();
            if (config != null) {
                ElementConfig elementConfig = ElementConfig.HANDLER.instance();
                String configString = gson.toJson(config);
                elementConfig.elementConfigs.put(element.identifier, configString);
            }
        }

        ElementConfig.HANDLER.save();
    }
}
