package dev.wooferz.hudlib.hud;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.wooferz.hudlib.HudAnchor;
import dev.wooferz.hudlib.InfoHUD;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.*;
import static dev.wooferz.hudlib.InfoHUDClient.LOGGER;

public class ExampleHUDElement extends HUDElement{

    ExampleHUDConfig config = new ExampleHUDConfig();

    public ExampleHUDElement(int defaultX, int defaultY, int defaultWidth, int defaultHeight, int padding) {
        super("FPS Display", defaultX, defaultY, defaultWidth, defaultHeight, padding, InfoHUD.MOD_ID, "example-hud-element", HudAnchor.HorizontalAnchor.LEFT, HudAnchor.VerticalAnchor.TOP);
    }

    @Override
    public void render(int x, int y, int width, int height, DrawContext context, float tickDelta) {
        int padding = 4;


        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String text = String.valueOf(MinecraftClient.getInstance().getCurrentFps()) + " FPS";

        int argb = (config.bgColor.getAlpha() << 24) | (config.bgColor.getRed() << 16) | (config.bgColor.getGreen() << 8) | config.bgColor.getBlue();
        context.fill(x, y, x + width, y + 9 + (padding * 2), argb);



        context.drawCenteredTextWithShadow(textRenderer, text, (width/2)+x, y + padding + 1, config.color.getRGB());

    }

    @Override
    public void setConfig(HUDConfig x) {
        if (config != null) {
            if (x instanceof ExampleHUDConfig) {
                config = (ExampleHUDConfig) x;
            }
        }
    }

    @Override
    public HUDConfig getConfig() {
        return config;
    }

    @Override
    public Class<ExampleHUDConfig> getConfigType() {
        return ExampleHUDConfig.class;
    }

    @Override
    public OptionGroup generateConfig() {
        OptionGroup optionGroup = OptionGroup.createBuilder()
                .name(Text.of(displayName))
                .option(Option.<Color>createBuilder()
                        .name(Text.of("Text Color"))
                        .binding(Color.WHITE,
                                () -> config.color,
                                newColor -> config.color = newColor)
                        .controller(ColorControllerBuilder::create)
                        .build()
                )
                .option(Option.<Color>createBuilder()
                        .name(Text.of("Background Color"))
                        .binding(new Color(0x99000000, true),
                                () -> config.bgColor,
                                newColor -> config.bgColor = newColor)
                        .controller(opt -> ColorControllerBuilder.create(opt)
                                .allowAlpha(true))
                        .build()
                )
                .build();
        return optionGroup;
    }
}
