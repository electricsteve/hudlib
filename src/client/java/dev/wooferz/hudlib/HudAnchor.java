package dev.wooferz.hudlib;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Rect2i;

public class HudAnchor {
    public boolean rightAnchor = false;
    public boolean bottomAnchor = false;

    public HudAnchor(boolean rightAnchor, boolean bottomAnchor) {
        this.rightAnchor = rightAnchor;
        this.bottomAnchor = bottomAnchor;
    }

    public void applyTransform(DrawContext context) {
        applyTransform(context.getMatrices());
    }

    public void applyTransform(MatrixStack stack) {
        stack.push();
        stack.translate(rightAnchor ? (double) MinecraftClient.getInstance().getWindow().getWidth() : 0.0, bottomAnchor ? (double) MinecraftClient.getInstance().getWindow().getHeight() : 0.0, 0.0);
    }
    public void undoTransform(DrawContext context) {
        undoTransform(context.getMatrices());
    }
    public void undoTransform(MatrixStack stack) {
        stack.pop();
    }

    public Rect2i convert(Rect2i rect) {
        Window window = MinecraftClient.getInstance().getWindow();
        Rect2i newRect = new Rect2i(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        if (rightAnchor) {
            newRect.setX(window.getScaledWidth() - rect.getX() - rect.getWidth());
        }
        if (bottomAnchor) {
            newRect.setY(window.getScaledHeight()-rect.getY()-rect.getHeight());
        }
        return newRect;

    }
    public Rect2i convertBack(Rect2i rect) {
        Window window = MinecraftClient.getInstance().getWindow();
        Rect2i newRect = new Rect2i(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        if (rightAnchor) {
            newRect.setX(window.getScaledWidth() - rect.getX());
        }
        if (bottomAnchor) {
            newRect.setY(window.getScaledHeight()-rect.getY());
        }
        return newRect;
    }
}
