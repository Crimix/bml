package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.radial.api.DrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import net.minecraft.util.StringUtils;
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
        String textString = text.getFormattedText();
        String unformattedString = StringUtils.stripControlCodes(textString);
        float y = context.y - context.fontRenderer.FONT_HEIGHT / 2.0f;
        // Split on space
        String[] unformattedStrings = unformattedString.split(" ");
        String[] strings = textString.split(" ");
        // No spaces, if too long hard split
        if (unformattedStrings.length == 1) {
            if (unformattedStrings[0].length() > 16) {
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
