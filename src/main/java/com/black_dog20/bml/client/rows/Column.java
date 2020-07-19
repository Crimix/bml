package com.black_dog20.bml.client.rows;

public abstract class Column {

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

    public String getId() {
        return id;
    }

    public abstract void render(RowDrawingContext context);

    public abstract int getWidth();

    public abstract int getHeight();
}
