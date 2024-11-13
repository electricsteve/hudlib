package dev.wooferz.hudlib.screens;

import dev.wooferz.hudlib.HudManager;
import dev.wooferz.hudlib.config.ConfigElementInformation;
import dev.wooferz.hudlib.config.ConfigManager;
import dev.wooferz.hudlib.hud.HUDElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;


public class EditScreen extends Screen {
    public EditScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        /*ButtonWidget backButton = ButtonWidget.builder(Text.of("Back"), (btn) -> {
            MinecraftClient.getInstance().setScreen(null);
        }).dimensions(40, 40, 120, 20).build();



        this.addDrawableChild(backButton);*/
        for (int i = 0; i < HudManager.hudElements.size(); i++) {
            HUDElement element = HudManager.hudElements.get(i);
            Rect2i unanchoredPosition = HudManager.hudPositions.get(element.identifier);
            Rect2i position = HudManager.hudAnchors.get(element.identifier).convert(unanchoredPosition);

            element.renderAnyway = true;

            DraggableWidget elementMover = new DraggableWidget(position.getX(), position.getY(), position.getWidth(), position.getHeight(), HudManager.hudEnabled.get(element.identifier), element);
            this.addDrawableChild(elementMover);
        }


    }

    @Override
    public void close() {
        for (int i = 0; i < HudManager.hudElements.size(); i++) {
            HUDElement element = HudManager.hudElements.get(i);

            element.renderAnyway = false;

            ConfigElementInformation information = new ConfigElementInformation();
            information.enabled = HudManager.hudEnabled.get(element.identifier);
            information.position = HudManager.hudPositions.get(element.identifier);
            information.anchor = HudManager.hudAnchors.get(element.identifier);

            ConfigManager.getInstance().config.saveElement(element.identifier, information);
            ConfigManager.getInstance().saveConfig();

        }
        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
