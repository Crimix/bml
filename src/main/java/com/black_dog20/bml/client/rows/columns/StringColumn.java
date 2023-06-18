package com.black_dog20.bml.client.rows.columns;

import com.black_dog20.bml.client.rows.RowDrawingContext;
import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.client.Minecraft;

/**
 * String column uses to display strings.
 */
public class StringColumn extends Column {

    private final String text;

    protected StringColumn(String id, String text, Alignment alignment) {
        super(id, alignment);
        this.text = text;
    }

    /**
     * Creates a new string column with default alignment LEFT.
     *
     * @param id   the id of the column.
     * @param text the text to display.
     * @return a new StringColumn.
     */
    public static StringColumn of(String id, String text) {
        return new StringColumn(id, text, Alignment.LEFT);
    }

    /**
     * Creates a new string column with default alignment LEFT.
     *
     * @param id        the id of the column.
     * @param text      the text to display.
     * @param alignment the alignment to draw the text using.
     * @return a new StringColumn.
     */
    public static StringColumn of(String id, String text, Alignment alignment) {
        return new StringColumn(id, text, alignment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        if (hasValue())
            return Minecraft.getInstance().font.width(text);
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(RowDrawingContext context) {
        if (hasValue()) {
            float x = context.x;
            int valueWidth = context.fontRenderer.width(text);
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
            context.guiGraphics.drawString(context.fontRenderer, text, (int)x, (int)context.y, -1, true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        if (hasValue())
            return Minecraft.getInstance().font.lineHeight;
        return 0;
    }

    private boolean hasValue() {
        return TextUtil.isNotNullOrEmpty(text);
    }


}
