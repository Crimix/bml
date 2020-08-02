package com.black_dog20.bml.client.rows;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.rows.columns.Column;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Row class.
 * Contains multiple columns.
 */
public class Row {

    private final LinkedList<Column> columns;

    private Row(RowBuilder builder) {
        columns = builder.columns;
    }

    /**
     * Draws the row.
     *
     * @param context     the drawing context.
     * @param columnWidth the map of column widths.
     * @return the height of the row being drawn.
     */
    public int render(DrawingContext context, Map<String, Integer> columnWidth) {
        int currentX = (int) context.x;
        for (Column column : columns) {
            int width = columnWidth.getOrDefault(column.getId(), 0);
            column.render(RowDrawingContext.of(context, currentX, context.y, width));
            currentX += width;
        }

        return RowHelper.getHeight(this);
    }

    /**
     * Gets the columns of this row.
     *
     * @return a list of columns.
     */
    public List<Column> getColumns() {
        return columns;
    }

    /**
     * Builder for rows.
     */
    public static final class RowBuilder {
        private LinkedList<Column> columns = new LinkedList<>();

        public RowBuilder() {
        }

        /**
         * Adds a column.
         *
         * @param column the column to be added.
         * @return the current builder.
         */
        public RowBuilder withColumn(Column column) {
            this.columns.add(column);
            return this;
        }

        /**
         * Builds the row.
         *
         * @return a new row containing the columns.
         */
        public Row build() {
            return new Row(this);
        }
    }
}
