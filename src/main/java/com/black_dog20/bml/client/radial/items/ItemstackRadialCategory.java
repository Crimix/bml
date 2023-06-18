package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.radial.api.RadialDrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialItemStack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/**
 * Radial category for an itemstack.
 *
 * @author black_dog20
 */
public class ItemstackRadialCategory extends TextRadialCategory implements IRadialItemStack {

    private final ItemStack stack;

    public ItemstackRadialCategory(ItemStack stack, Component text) {
        super(text);
        this.stack = stack;
    }

    public ItemstackRadialCategory(ItemStack stack, Component text, ChatFormatting color) {
        super(text, color);
        this.stack = stack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(RadialDrawingContext context) {
        if (stack.getCount() > 0) {
            drawStack(context, stack);
        } else {
            super.draw(context);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTooltips(RadialDrawingContext context) {
        if (stack.getCount() > 0) {
            context.getTooltipDrawingHelper().renderComponentTooltip(getItemToolTip(stack));
        } else {
            super.drawTooltips(context);
        }
    }
}
