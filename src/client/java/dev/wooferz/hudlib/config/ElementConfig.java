package dev.wooferz.hudlib.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.wooferz.hudlib.HudManager;
import dev.wooferz.hudlib.hud.HUDConfig;
import dev.wooferz.hudlib.hud.HUDElement;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

public class ElementConfig {
    public static ConfigClassHandler<ElementConfig> HANDLER = ConfigClassHandler.createBuilder(ElementConfig.class)
            .id(new Identifier("hudlib", "element_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("hudlib-elements.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public HashMap<String, String> elementConfigs = new HashMap<>();
}
