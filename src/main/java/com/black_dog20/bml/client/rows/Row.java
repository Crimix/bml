package com.black_dog20.bml.client.rows;

import com.black_dog20.bml.client.DrawingContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Row {

    private final LinkedList<Column> columns;

    private Row(RowBuilder builder) {
        columns = builder.columns;
    }

    public int render(DrawingContext context, Map<String, Integer> columnWidth) {
        int currentX = (int) context.x;
        for (Column column : columns) {
            int width = columnWidth.getOrDefault(column.getId(), 0);
            column.render(RowDrawingContext.of(context, currentX, context.y, width));
            currentX += width;
        }

        return RowHelper.getHeight(this);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public static final class RowBuilder {
        private LinkedList<Column> columns = new LinkedList<>();

        public RowBuilder() {
        }

        public RowBuilder withColumn(Column column) {
            this.columns.add(column);
            return this;
        }

        public Row build() {
            return new Row(this);
        }
    }
}
