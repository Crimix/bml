package com.black_dog20.bml.client.rows.columns;

import com.black_dog20.bml.client.rows.Column;
import com.black_dog20.bml.client.rows.RowDrawingContext;

public class BlankColumn extends Column {

    private final int spacing;

    protected BlankColumn(String id, int spacing) {
        super(id, Alignment.CENTER);
        this.spacing = spacing;
    }

    public static BlankColumn of(String id, int spacing) {
        return new BlankColumn(id, spacing);
    }

    @Override
    public int getWidth() {
        return spacing;
    }

    @Override
    public void render(RowDrawingContext context) {
    }

    @Override
    public int getHeight() {
        return 0;
    }

}
