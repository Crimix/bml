package com.black_dog20.bml.client.rows;

import com.black_dog20.bml.client.DrawingContext;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;

public class RowDrawingContext extends DrawingContext {

    public final int columnMaxWidth;

    public RowDrawingContext(MatrixStack matrixStack, int width, int height, float x, float y, float z, FontRenderer fontRenderer, ItemRenderer itemRenderer, int columnMaxWidth) {
        super(matrixStack, width, height, x, y, z, fontRenderer, itemRenderer);
        this.columnMaxWidth = columnMaxWidth;
    }

    public RowDrawingContext of(float x, float y, int columnMaxWidth) {
        return new RowDrawingContext(matrixStack, width, height, x, y, z, fontRenderer, itemRenderer, columnMaxWidth);
    }

    public static RowDrawingContext of(DrawingContext context, float x, float y, int columnMaxWidth) {
        return new RowDrawingContext(context.matrixStack, context.width, context.height, x, y, context.z, context.fontRenderer, context.itemRenderer, columnMaxWidth);
    }
}
