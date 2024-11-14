package dev.wooferz.hudlib.screens;

import dev.wooferz.hudlib.HudManager;
import dev.wooferz.hudlib.ModMenuIntegration;
import dev.wooferz.hudlib.config.ConfigElementInformation;
import dev.wooferz.hudlib.config.ConfigManager;
import dev.wooferz.hudlib.hud.HUDElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;
import static dev.wooferz.hudlib.InfoHUDClient.LOGGER;
import net.minecraft.client.util.Window;

public class EditScreen extends Screen {
    public EditScreen(Text title) {
        super(title);
    }

    public boolean isCtrlHeld = false;

    public ButtonWidget settingsButton;

    @Override
    protected void init() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Window window = minecraftClient.getWindow();

        settingsButton = ButtonWidget.builder(Text.of("Settings"), (btn) -> {
            minecraftClient.setScreen(ModMenuIntegration.finishScreen(minecraftClient.currentScreen));

        }).dimensions(window.getScaledWidth() / 2 - 60, window.getScaledHeight() / 2 - 10, 120, 20).build();
        this.addDrawableChild(settingsButton);


        for (int i = 0; i < HudManager.hudElements.size(); i++) {
            HUDElement element = HudManager.hudElements.get(i);
            Rect2i unanchoredPosition = HudManager.hudPositions.get(element.identifier);
            Rect2i position = HudManager.hudAnchors.get(element.identifier).convert(unanchoredPosition);

            element.renderAnyway = true;

            DraggableWidget elementMover = new DraggableWidget(position.getX(), position.getY(), position.getWidth(), position.getHeight(), HudManager.hudEnabled.get(element.identifier), element, this);
            this.addDrawableChild(elementMover);
        }






    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Window window = minecraftClient.getWindow();

        settingsButton.setX(window.getScaledWidth() / 2 - 60);
        settingsButton.setY(window.getScaledHeight() / 2 - 10);


        super.resize(client, width, height);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 341) {
            isCtrlHeld = true;
            settingsButton.active = false;
            settingsButton.visible = false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 341) {
            isCtrlHeld = false;
            settingsButton.active = true;
            settingsButton.visible = true;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
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
