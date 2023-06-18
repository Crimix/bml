package com.black_dog20.bml.client.radial.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public interface ITooltipDrawingHelper {

    /**
     * Draws tooltips
     *
     * @param tooltips  the tooltips
     */
    void renderTooltip(List<FormattedCharSequence> tooltips);

    /**
     * Draws tooltips
     *
     * @param tooltips  the tooltips
     */
    default void renderComponentTooltip(List<Component> tooltips) {
        List<FormattedCharSequence> charSequences = new ArrayList<>();
        for (Component component : tooltips) {
            charSequences.addAll(Tooltip.splitTooltip(Minecraft.getInstance(), component));
        }
    }
}
