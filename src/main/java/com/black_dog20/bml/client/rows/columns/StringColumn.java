package com.black_dog20.bml.client.rows.columns;

import com.black_dog20.bml.client.rows.Column;
import com.black_dog20.bml.client.rows.RowDrawingContext;
import net.minecraft.client.Minecraft;

public class StringColumn extends Column {

    private final String value;

    protected StringColumn(String id, String value, Alignment alignment) {
        super(id, alignment);
        this.value = value;
    }

    public static StringColumn of(String id, String value) {
        return new StringColumn(id, value, Alignment.LEFT);
    }

    public static StringColumn of(String id, String value, Alignment alignment) {
        return new StringColumn(id, value, alignment);
    }

    @Override
    public int getWidth() {
        if (hasValue())
            return Minecraft.getInstance().fontRenderer.getStringWidth(value);
        return 0;
    }

    @Override
    public void render(RowDrawingContext context) {
        if (hasValue()) {
            float x = context.x;
            int valueWidth = context.fontRenderer.getStringWidth(value);
            switch (alignment) {
                case LEFT:
                    break;
                case RIGHT:
                    x += (context.columnMaxWidth - valueWidth);
                    break;
                case CENTER:
                    x += (context.columnMaxWidth / 2F - valueWidth / 2F);
                    break;
            }
            context.fontRenderer.drawStringWithShadow(context.matrixStack, value, x, context.y, -1);
        }
    }

    @Override
    public int getHeight() {
        if (hasValue())
            return Minecraft.getInstance().fontRenderer.FONT_HEIGHT;
        return 0;
    }

    private boolean hasValue() {
        return value != null && !value.isEmpty();
    }


}
