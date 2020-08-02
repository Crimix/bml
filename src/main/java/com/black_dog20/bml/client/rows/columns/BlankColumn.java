package com.black_dog20.bml.client.rows.columns;

import com.black_dog20.bml.client.rows.RowDrawingContext;

/**
 * Blank space column content.
 */
public class BlankColumn extends Column {

    private final int spacing;

    protected BlankColumn(String id, int spacing) {
        super(id, Alignment.CENTER);
        this.spacing = spacing;
    }

    /**
     * Creates a blank column with spacing.
     *
     * @param id      the id of the column.
     * @param spacing how wide the column should be
     * @return a blank column.
     */
    public static BlankColumn of(String id, int spacing) {
        return new BlankColumn(id, spacing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return spacing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(RowDrawingContext context) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return 0;
    }

}
