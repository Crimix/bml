package com.black_dog20.bml.client.rows;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.rows.columns.Column;
import net.minecraft.client.gui.screen.Screen;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Row helper class.
 */
public class RowHelper {

    /**
     * Draws rows using the DrawingContext.
     *
     * @param context the drawing context.
     * @param rows    the rows to draw.
     * @return the y point after drawing the rows.
     */
    public static int drawRows(DrawingContext context, List<Row> rows) {
        Map<String, Integer> columnMap = getColumnMaxWidthMap(rows);

        int currentY = (int) context.y;
        for (Row row : rows) {
            currentY += row.render(context.of(context.x, currentY), columnMap);
        }
        return currentY;
    }

    /**
     * Draws rows with background using the DrawingContext.
     *
     * @param context the drawing context.
     * @param rows    the rows to draw.
     * @return the y point after drawing the rows.
     */
    public static int drawRowsWithBackground(DrawingContext context, List<Row> rows) {
        Map<String, Integer> columnMap = getColumnMaxWidthMap(rows);
        int maxWidth = getMaxWidth(rows);
        int maxHeight = getMaxHeight(rows);

        Screen.fill(context.matrixStack, (int) context.x - 1, (int) context.y - 1, context.width / 2 + maxWidth / 2 + 1, (int) context.y + rows.size() * maxHeight, Integer.MIN_VALUE);

        int currentY = (int) context.y;
        for (Row row : rows) {
            Screen.fill(context.matrixStack, context.width / 2 - maxWidth / 2, currentY, context.width / 2 + maxWidth / 2, currentY + getHeight(row) - 1, 553648127);
            currentY += row.render(context.of(context.x, currentY), columnMap);
        }
        return currentY;
    }

    /**
     * Gets the max width of the row considering the columns.
     *
     * @param rows the rows.
     * @return the width.
     */
    public static int getMaxWidth(List<Row> rows) {
        return getColumnMaxWidthMap(rows).values().stream()
                .reduce(0, Integer::sum);
    }

    /**
     * Gets the max height of the row considering the columns.
     *
     * @param rows the rows.
     * @return the height.
     */
    public static int getMaxHeight(List<Row> rows) {
        return getColumnMaxHeightMap(rows).values().stream()
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    private static Map<String, Integer> getColumnMaxWidthMap(List<Row> rows) {
        return rows.stream()
                .map(Row::getColumns)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Column::getId, Column::getWidth, Math::max));
    }

    private static Map<String, Integer> getColumnMaxHeightMap(List<Row> rows) {
        return rows.stream()
                .map(Row::getColumns)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Column::getId, Column::getHeight, Math::max));
    }

    /**
     * Gets the height of the row.
     *
     * @param row the row
     * @return the height.
     */
    public static int getHeight(Row row) {
        return row.getColumns().stream()
                .map(Column::getHeight)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}
