package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.radial.api.RadialDrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.Component;

/**
 * Radial item for a text item.
 *
 * @author black_dog20
 */
public class TextRadialItem implements IRadialItem {

    protected final Component text;
    protected final int color;
    protected boolean hovered;

    public TextRadialItem(Component text) {
        this.text = text;
        this.color = ChatFormatting.WHITE.getColor();
    }

    public TextRadialItem(Component text, ChatFormatting color) {
        this.text = text;
        this.color = color.getColor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(RadialDrawingContext context) {
        drawComponent(context, text);
    }

    protected void drawComponent(RadialDrawingContext context, Component component) {
        float y = context.y;
        MultiLineLabel lines = MultiLineLabel.create(context.fontRenderer, component, 60);
        if (lines.getLineCount() > 1) {
            y = y - context.fontRenderer.lineHeight / 1.5f;
        } else {
            y = y - context.fontRenderer.lineHeight / 2.0f;
        }
        lines.renderCentered(context.guiGraphics, (int) context.x, (int) y, context.fontRenderer.lineHeight, color);
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
    public Component getCenterText() {
        return text;
    }
}
