package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialCategory;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Radial category for a text item.
 *
 * @author black_dog20
 */
public class TextRadialCategory implements IRadialCategory {

    private final ITextComponent text;
    private final int color;
    private final List<IRadialItem> items = new ArrayList<>();
    private boolean hovered;

    public TextRadialCategory(ITextComponent text) {
        this.text = text;
        this.color = TextFormatting.WHITE.getColor();
    }

    public TextRadialCategory(ITextComponent text, TextFormatting color) {
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
    public ITextComponent getCenterText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IRadialItem> getItems() {
        return items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(IRadialItem item) {
        items.add(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean closeIfEmpty() {
        return false;
    }
}
