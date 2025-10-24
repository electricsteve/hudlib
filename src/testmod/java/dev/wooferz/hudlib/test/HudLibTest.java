package dev.wooferz.hudlib.test;

import dev.wooferz.hudlib.HudManager;
import dev.wooferz.hudlib.test.element.CoordinateElement;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HudLibTest implements ClientModInitializer {
    public static final String MOD_ID = "hudlib-test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        HudManager.registerHudElement(new CoordinateElement());

    }
}
