package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.radial.api.RadialDrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialCategory;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Radial category for a text item.
 *
 * @author black_dog20
 */
public class TextRadialCategory extends TextRadialItem implements IRadialCategory {

    private final List<IRadialItem> items = new ArrayList<>();

    public TextRadialCategory(Component text) {
        super(text);
    }

    public TextRadialCategory(Component text, ChatFormatting color) {
        super(text, color);
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
        drawComponent(context, textComponent);
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
