package com.black_dog20.bml.client.radial.api;

import com.black_dog20.bml.client.DrawingContext;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class RadialDrawingContext extends DrawingContext {

    private final ITooltipDrawingHelper tooltipDrawingHelper;

    public RadialDrawingContext(GuiGraphics guiGraphics, int width, int height, float x, float y, float z, Font fontRenderer, ITooltipDrawingHelper tooltipDrawingHelper) {
        super(guiGraphics, width, height, x, y, z, fontRenderer);
        this.tooltipDrawingHelper = tooltipDrawingHelper;
    }

    public ITooltipDrawingHelper getTooltipDrawingHelper() {
        return tooltipDrawingHelper;
    }
}
