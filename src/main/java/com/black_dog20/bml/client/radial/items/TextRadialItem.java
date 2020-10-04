package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

/**
 * Radial item for a text item.
 *
 * @author black_dog20
 */
public class TextRadialItem implements IRadialItem {

    private final ITextComponent text;
    private final int color;
    private boolean hovered;

    public TextRadialItem(ITextComponent text) {
        this.text = text;
        this.color = TextFormatting.WHITE.getColor();
    }

    public TextRadialItem(ITextComponent text, TextFormatting color) {
        this.text = text;
        this.color = color.getColor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(DrawingContext context) {
        float y = context.y;
        IBidiRenderer lines = IBidiRenderer.func_243258_a(context.fontRenderer, text, 60);
        if (lines.func_241862_a() > 1) {
            y = y - context.fontRenderer.FONT_HEIGHT / 1.5f;
        } else {
            y = y - context.fontRenderer.FONT_HEIGHT / 2.0f;
        }
        lines.func_241864_a(context.matrixStack, (int) context.x, (int) y, context.fontRenderer.FONT_HEIGHT, color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHovered() {
        return hovered;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void click() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean closeOnClick() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITextComponent getCenterText() {
        return text;
    }
}
