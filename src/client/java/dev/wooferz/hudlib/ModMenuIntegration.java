package dev.wooferz.hudlib;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.wooferz.hudlib.config.ElementConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import static dev.wooferz.hudlib.InfoHUDClient.LOGGER;


public class ModMenuIntegration implements ModMenuApi {

  //  YetAnotherConfigLib configMenu;

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {


        return ModMenuIntegration::finishScreen;
    }

    public static Screen finishScreen(Screen parent) {
        LOGGER.info("Requested Mod Config Menu?!?! will deliver shortly");

        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Text.of("Used for narration. Could be used to render a title in the future."));
        ConfigCategory.Builder categoryBuilder = ConfigCategory.createBuilder()
                .name(Text.of("HUD Elements"));

        HudManager.addConfigOptionGroups(categoryBuilder);

        builder.category(categoryBuilder.build()).save(HudManager::saveConfig);

        return builder.build().generateScreen(parent);
    }
}
