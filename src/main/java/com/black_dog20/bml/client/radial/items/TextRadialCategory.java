package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.radial.api.RadialDrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialCategory;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Radial category for a text item.
 *
 * @author black_dog20
 */
public class TextRadialCategory implements IRadialCategory {

    private final Component text;
    private final int color;
    private final List<IRadialItem> items = new ArrayList<>();
    private boolean hovered;

    public TextRadialCategory(Component text) {
        this.text = text;
        this.color = ChatFormatting.WHITE.getColor();
    }

    public TextRadialCategory(Component text, ChatFormatting color) {
        this.text = text;
        this.color = color.getColor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(RadialDrawingContext context) {
        Component textComponent = TextComponentBuilder.of("[")
                .with(text)
                .with("]")
                .build();
        float y = context.y;
        MultiLineLabel lines = MultiLineLabel.create(context.fontRenderer, textComponent, 60);
        if (lines.getLineCount() > 1) {
            y = y - context.fontRenderer.lineHeight / 1.5f;
        } else {
            y = y - context.fontRenderer.lineHeight / 2.0f;
        }
        lines.renderCentered(context.poseStack, (int) context.x, (int) y, context.fontRenderer.lineHeight, color);
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
    public Component getCenterText() {
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
