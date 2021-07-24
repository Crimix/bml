package com.black_dog20.bml.client.rows;

import com.black_dog20.bml.client.DrawingContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;

/**
 * Row drawing context.
 */
public class RowDrawingContext extends DrawingContext {

    /**
     * The max width of the column that is being drawn.
     */
    public final int columnMaxWidth;

    public RowDrawingContext(PoseStack matrixStack, int width, int height, float x, float y, float z, Font fontRenderer, ItemRenderer itemRenderer, int columnMaxWidth) {
        super(matrixStack, width, height, x, y, z, fontRenderer, itemRenderer);
        this.columnMaxWidth = columnMaxWidth;
    }

    public RowDrawingContext of(float x, float y, int columnMaxWidth) {
        return new RowDrawingContext(poseStack, width, height, x, y, z, fontRenderer, itemRenderer, columnMaxWidth);
    }

    public static RowDrawingContext of(DrawingContext context, float x, float y, int columnMaxWidth) {
        return new RowDrawingContext(context.poseStack, context.width, context.height, x, y, context.z, context.fontRenderer, context.itemRenderer, columnMaxWidth);
    }
}
