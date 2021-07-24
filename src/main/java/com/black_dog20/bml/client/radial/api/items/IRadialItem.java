package com.black_dog20.bml.client.radial.api.items;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.internal.utils.InternalTranslations;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fmlclient.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

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
    default void drawTooltips(DrawingContext context) {
        List<Component> tooltips = getTooltips();
        GuiUtils.drawHoveringText(context.poseStack, tooltips, (int) context.x, (int) context.y, context.width, context.height, -1, context.fontRenderer);
    }

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
    Component getCenterText();

    /**
     * Gets the context items for this category.
     *
     * @return a list of context items.
     */
    default List<IRadialItem> getContextItems() {
        return new ArrayList<>();
    }

    /**
     * Gets if the context menu should be skipped if there is only one context item.
     * This changes the default tooltip and right click behavior.
     *
     * @return true if the menu should be skipped.
     */
    default boolean skipMenuIfSingleContextItem() {
        return true;
    }

    /**
     * Gets the tooltips  this item.
     *
     * @return a list of tooltips.
     */
    default List<Component> getTooltips() {
        List<Component> tooltips = new ArrayList<>();
        if (!getContextItems().isEmpty()) {
            if (getContextItems().size() == 1 && skipMenuIfSingleContextItem()) {
                IRadialItem item = getContextItems().get(0);
                Component text = TextComponentBuilder.of(InternalTranslations.Translations.RIGHT_CLICK_TO.get())
                        .space()
                        .with(item.getCenterText())
                        .build();
                tooltips.add(text);
            } else {
                tooltips.add(TranslationUtil.translate(InternalTranslations.Translations.RIGHT_CLICK_FOR_OPTIONS));
            }
        }
        return tooltips;
    }
}
