package com.black_dog20.bml.client.rows.columns;

import com.black_dog20.bml.client.rows.RowDrawingContext;

/**
 * Base abstract class for all columns.
 */
public abstract class Column {

    /**
     * The different alignments that columns can be.
     */
    public enum Alignment {
        LEFT,
        RIGHT,
        CENTER
    }

    protected final String id;
    protected final Alignment alignment;

    protected Column(String id, Alignment alignment) {
        this.id = id;
        this.alignment = alignment;
    }

    /**
     * @return the id of the column.
     */
    public String getId() {
        return id;
    }

    /**
     * Renders the column content.
     *
     * @param context the context.
     */
    public abstract void render(RowDrawingContext context);

    /**
     * @return the width of the column content.
     */
    public abstract int getWidth();

    /**
     * @return the height of the column content.
     */
    public abstract int getHeight();
}
