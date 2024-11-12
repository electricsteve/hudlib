package dev.wooferz.infohud;
import dev.wooferz.infohud.screens.CustomScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import static dev.wooferz.infohud.InfoHUDClient.openEditorKey;

public class HudManager {




    public static void openEditor(MinecraftClient minecraftClient) {
        if (openEditorKey.wasPressed()) {
            minecraftClient.setScreen(new CustomScreen(Text.of("")));
        }
    }

}
