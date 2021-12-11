package com.black_dog20.bml.client.radial.api;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface ITooltipDrawingHelper {

    /**
     * Draws tooltips
     *
     * @param poseStack the PoseStack
     * @param tooltips  the tooltips
     * @param mouseX    mouse x
     * @param mouseY    mouse y
     */
    void renderTooltip(PoseStack poseStack, List<Component> tooltips, int mouseX, int mouseY);
}
