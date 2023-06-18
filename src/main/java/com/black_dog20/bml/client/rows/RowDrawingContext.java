package com.black_dog20.bml.client.rows;

import com.black_dog20.bml.client.DrawingContext;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Row drawing context.
 */
public class RowDrawingContext extends DrawingContext {

    /**
     * The max width of the column that is being drawn.
     */
    public final int columnMaxWidth;

    public RowDrawingContext(GuiGraphics guiGraphics, int width, int height, float x, float y, float z, Font fontRenderer, int columnMaxWidth) {
        super(guiGraphics, width, height, x, y, z, fontRenderer);
        this.columnMaxWidth = columnMaxWidth;
    }

    public RowDrawingContext of(float x, float y, int columnMaxWidth) {
        return new RowDrawingContext(guiGraphics, width, height, x, y, z, fontRenderer, columnMaxWidth);
    }

    public static RowDrawingContext of(DrawingContext context, float x, float y, int columnMaxWidth) {
        return new RowDrawingContext(context.guiGraphics, context.width, context.height, x, y, context.z, context.fontRenderer, columnMaxWidth);
    }
}
