package com.black_dog20.bml.client.radial.api;

import com.black_dog20.bml.client.DrawingContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class RadialDrawingContext extends DrawingContext {

    private final ITooltipDrawingHelper tooltipDrawingHelper;

    public RadialDrawingContext(PoseStack poseStack, int width, int height, float x, float y, float z, Font fontRenderer, ItemRenderer itemRenderer, ITooltipDrawingHelper tooltipDrawingHelper) {
        super(poseStack, width, height, x, y, z, fontRenderer, itemRenderer);
        this.tooltipDrawingHelper = tooltipDrawingHelper;
    }

    public ITooltipDrawingHelper getTooltipDrawingHelper() {
        return tooltipDrawingHelper;
    }
}
