package dev.wooferz.hudlib.screens;

import dev.wooferz.hudlib.HudAnchor;
import dev.wooferz.hudlib.HudManager;
import dev.wooferz.hudlib.hud.HUDElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;


public class DraggableWidget extends ClickableWidget {

    double realX;
    double realY;
    double realWidth;
    double realHeight;
    HUDElement element;
    Boolean enabled;
    boolean pressed = false;
    boolean resizing = false;

    public DraggableWidget(int x, int y, int width, int height, Boolean enabled, HUDElement element) {
        super(x - element.padding, y - element.padding, width + (element.padding * 2), height + (element.padding * 2), Text.empty());
        this.realX = (double) x;
        this.realY = (double) y;
        this.realWidth = (double) width;
        this.realHeight = (double) height;
        this.element = element;
        this.enabled = enabled;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        int borderColor = 0xFFFFFFFF;
        int disabledOverlay = 0x55000000;

        if (!this.enabled) {
            context.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), disabledOverlay);
        }

            context.drawBorder(getX(), getY(), getWidth(), getHeight(), borderColor);

        if (element.canResize()) {
            context.fill(getX() + getWidth() - 5, getY() + getHeight() - 5, getX() + getWidth(), getY() + getHeight(), borderColor);
        }

    }

    private boolean isResizing(double mouseX, double mouseY) {
        boolean resizing = mouseX >= getX() + getWidth() - 5 && mouseY >= getY() + getHeight() - 5 && mouseX <= getX() + getWidth() && mouseY <= getY() + getHeight();
        return resizing;
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {

        Screen window = MinecraftClient.getInstance().currentScreen;
        if (window == null) return;
        int width = window.width;
        int height = window.height;

        if (resizing) {
            this.realWidth += deltaX;
            this.realHeight += deltaY;

            if (realWidth < 5) {
                realWidth = 5;
            }
            if (realHeight < 5 ) {
                realHeight = 5;
            }

            setWidth((int) realWidth);
            height = ((int) realHeight);
        } else {
            this.realX += deltaX;
            this.realY += deltaY;

            if (this.realX + this.realWidth > width) {
                this.realX = width - this.realWidth;
            }
            if (this.realY + this.realHeight > height) {
                this.realY = height - this.realHeight;
            }
            if (this.realX < 0) {
                this.realX = 0;
            }
            if (this.realY < 0) {
                this.realY = 0;
            }

            setX((int) realX);
            setY((int) realY);

        }
        pressed = false;

        Rect2i position = getRect();
        Rect2i fixedPosition = HudManager.hudAnchors.get(element.identifier).convert(position);


        HudManager.hudPositions.put(element.identifier, fixedPosition);
    }

    public Rect2i getRect() {
        return new Rect2i(this.getX() + element.padding, this.getY() + element.padding, this.getWidth() - (element.padding * 2), this.getHeight() - (element.padding * 2));
    }

    @Override
    public void playDownSound(SoundManager soundManager) {

    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        pressed = true;
        resizing = isResizing(mouseX, mouseY) && element.canResize();

        super.onClick(mouseX, mouseY);
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {

        if (pressed && !(resizing)) {
            enabled = !enabled;
            if (enabled) {
                element.onEnable();
            } else {
                element.onDisable();
            }
            HudManager.hudEnabled.put(element.identifier, enabled);
        }
        if (!pressed && !resizing) {
            HudAnchor anchor = HudManager.hudAnchors.get(element.identifier);
            Window window = MinecraftClient.getInstance().getWindow();
            int centerX = (getWidth() + getX() + getX()) / 2;
            int centerY = (getHeight() + getY() + getY()) / 2;
            if (centerX < (window.getScaledWidth() / 2)) {
                anchor.rightAnchor = false;
            } else {
                anchor.rightAnchor = true;
            }
            if (centerY < (window.getScaledHeight() /2)) {
                anchor.bottomAnchor = false;
            } else {
                anchor.bottomAnchor = true;
            }
            HudManager.hudPositions.put(element.identifier, anchor.convert(getRect()));
        }
        super.onRelease(mouseX, mouseY);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
