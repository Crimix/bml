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
        IBidiRenderer lines = IBidiRenderer.func_243258_a(context.fontRenderer, text, 20);
        lines.func_241863_a(context.matrixStack, (int) context.x, (int) context.y);
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
