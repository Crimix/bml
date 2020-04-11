package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.radial.api.DrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialCategory;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
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
        String unformattedString = "[" + text.getUnformattedComponentText() + "]";
        String textString = "[" + text.getFormattedText() + "]";
        float y = context.y - context.fontRenderer.FONT_HEIGHT / 2.0f;
        // Split on space
        String[] unformattedStrings = unformattedString.split(" ");
        String[] strings = textString.split(" ");
        // No spaces, if too long hard split
        if (unformattedStrings.length == 1) {
            if (unformattedStrings[0].length() > 8) {
                int l = context.fontRenderer.getStringWidth(strings[0]);
                strings = (String[]) context.fontRenderer.listFormattedStringToWidth(strings[0], l / 2).toArray();
            }
        }

        if (strings.length > 1) {
            y = y - context.fontRenderer.FONT_HEIGHT / 2.0f;
        }
        int count = 0;

        for (String s : strings) {
            if (count < 2) {
                float x = context.x - context.fontRenderer.getStringWidth(s) / 2.0f;
                context.fontRenderer.drawStringWithShadow(s, x, y, color);
                y += context.fontRenderer.FONT_HEIGHT;
                count++;
            } else {
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTooltips(DrawingContext context) {

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
    public boolean closeIfEmpty() {
        return false;
    }
}
