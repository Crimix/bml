package com.black_dog20.bml.client.radial.api.items;

import com.black_dog20.bml.client.radial.api.DrawingContext;
import net.minecraft.util.text.ITextComponent;

/**
 * Interface for radial items.
 *
 * @author black_dog20
 */
public interface IRadialItem {

    /**
     * Draw the item using the context.
     *
     * @param context the drawing context.
     */
    void draw(DrawingContext context);

    /**
     * Draw the item's tooltips using the context.
     *
     * @param context the drawing context.
     */
    void drawTooltips(DrawingContext context);

    /**
     * Is this item the hovered item in the menu.
     *
     * @return true if it is.
     */
    boolean isHovered();

    /**
     * Sets this empty to be the hovered item.
     *
     * @param hovered the hovered state.
     */
    void setHovered(boolean hovered);

    /**
     * Method to execute on click.
     */
    void click();

    /**
     * Override if the radial should close on click.
     *
     * @return true to close on click.
     */
    boolean closeOnClick();

    /**
     * Get the text to be rendered in the center of the radial.
     *
     * @return an ITextComponent.
     */
    ITextComponent getCenterText();
}
