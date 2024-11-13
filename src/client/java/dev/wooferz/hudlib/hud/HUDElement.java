package dev.wooferz.hudlib.hud;

import net.minecraft.client.gui.DrawContext;

public abstract class HUDElement {

    public int defaultX;
    public int defaultY;
    public int defaultWidth;
    public int defaultHeight;
    public int padding;
    public String identifier;
    public boolean renderAnyway = false;
    public boolean defaultAnchorRight;
    public boolean defaultAnchorBottom;

    public HUDElement(int defaultX, int defaultY, int defaultWidth, int defaultHeight, int padding, String modid, String identifier, boolean defaultAnchorRight, boolean defaultAnchorBottom) {
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.padding = padding;
        this.identifier = modid + ":" + identifier;
        this.defaultAnchorRight = defaultAnchorRight;
        this.defaultAnchorBottom = defaultAnchorBottom;
    }

    public HUDElement(int defaultX, int defaultY, int defaultWidth, int defaultHeight, int padding, String modid, String identifier) {
        this(defaultX, defaultY, defaultWidth, defaultHeight, padding, modid, identifier, false, false);
    }

    public boolean canResize() {
        return false;
    }

    public abstract void render(int x, int y, int width, int height, DrawContext context, float tickDelta);

    public void onDisable() {

    }
    public void onEnable() {

    }


}
