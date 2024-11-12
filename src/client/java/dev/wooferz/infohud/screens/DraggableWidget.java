package dev.wooferz.infohud.screens;

import dev.wooferz.infohud.HudManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class DraggableWidget extends ClickableWidget {

    double realX;
    double realY;

    public DraggableWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.realX = (double) x;
        this.realY = (double) y;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        int startColor = 0xFF00FF00;
        int endColor = 0xAA0000FF;

        context.fillGradient(getX(), getY(), getX() + this.width, getY() + this.height, startColor, endColor);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.realX += deltaX;
        this.realY += deltaY;
        setX((int) realX);
        setY((int) realY);
        HudManager.temporaryX = (int) realX;
        HudManager.temporaryY = (int) realY;
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
