package dev.wooferz.hudlib.hud;

import dev.wooferz.hudlib.InfoHUD;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class ExampleHUDElement extends HUDElement{

    public ExampleHUDElement(int defaultX, int defaultY, int defaultWidth, int defaultHeight, int padding, boolean rightAnchor, boolean bottomAnchor) {
        super(defaultX, defaultY, defaultWidth, defaultHeight, padding, InfoHUD.MOD_ID, "example-hud-element", rightAnchor, bottomAnchor);
    }

    @Override
    public void render(int x, int y, int width, int height, DrawContext context, float tickDelta) {
        int padding = 4;


        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String text = String.valueOf(MinecraftClient.getInstance().getCurrentFps()) + " FPS";
        context.fill(x, y, x + width, y + 9 + (padding * 2), 0x99000000);
        context.drawCenteredTextWithShadow(textRenderer, text, (width/2)+x, y + padding + 1, 0xFFFFFF);

    }


}
