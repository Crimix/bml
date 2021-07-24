package com.black_dog20.bml.client.rows.columns;

import com.black_dog20.bml.client.rows.RowDrawingContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * TextComponent column uses to display ITextComponents.
 */
public class ITextComponentColumn extends Column {

    private final Component component;

    protected ITextComponentColumn(String id, Component component, Alignment alignment) {
        super(id, alignment);
        this.component = component;
    }

    /**
     * Creates a new TextComponent column with default alignment LEFT.
     *
     * @param id        the id of the column.
     * @param component the text component to display.
     * @return a new ITextComponentColumn.
     */
    public static ITextComponentColumn of(String id, Component component) {
        return new ITextComponentColumn(id, component, Alignment.LEFT);
    }

    /**
     * Creates a new TextComponent column.
     *
     * @param id        the id of the column.
     * @param component the text component to display.
     * @param alignment the alignment to draw the text using.
     * @return a new ITextComponentColumn.
     */
    public static ITextComponentColumn of(String id, Component component, Alignment alignment) {
        return new ITextComponentColumn(id, component, alignment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        if (hasValue())
            return Minecraft.getInstance().font.width(component);
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(RowDrawingContext context) {
        if (hasValue()) {
            float x = context.x;
            int valueWidth = context.fontRenderer.width(component);
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
            context.fontRenderer.drawShadow(context.poseStack, component, x, context.y, -1);
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
        return component != null && !component.getString().isEmpty();
    }


}
