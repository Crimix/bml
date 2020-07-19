package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialCategory;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        String textString = TextUtil.getFormattedText(text);
        String unformattedString = StringUtils.stripControlCodes(textString);
        float y = context.y - context.fontRenderer.FONT_HEIGHT / 2.0f;
        // Split on space
        String[] unformattedStrings = unformattedString.split(" ");
        List<ITextProperties> strings = Arrays.asList(textString.split(" ")).stream()
                .filter(TextUtil::isNotNullOrEmpty)
                .map(ITextProperties::func_240652_a_)
                .collect(Collectors.toList());
        // No spaces, if too long hard split
        if (unformattedStrings.length == 1) {
            if (unformattedStrings[0].length() > 16) {
                int l = context.fontRenderer.func_238414_a_(strings.get(0));
                strings = context.fontRenderer.func_238425_b_(strings.get(0), l / 2);
            }
        }

        if (strings.size() > 1) {
            y = y - context.fontRenderer.FONT_HEIGHT / 2.0f;
        }
        int count = 0;

        for (ITextProperties s : strings) {
            if (count < 2) {
                float x = context.x - context.fontRenderer.func_238414_a_(s) / 2.0f;
                context.fontRenderer.func_238407_a_(context.matrixStack, s, x, y, color);
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
        List<ITextComponent> tooltips = getTooltips();
        GuiUtils.drawHoveringText(context.matrixStack, tooltips, (int) context.x, (int) context.y, context.width, context.height, -1, context.fontRenderer);
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
