package com.black_dog20.bml.client.rows;

import com.black_dog20.bml.client.DrawingContext;
import net.minecraft.client.gui.screen.Screen;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RowHelper {

    public static void drawRows(DrawingContext context, List<Row> rows) {
        Map<String, Integer> columnMap = getColumnMaxWidthMap(rows);

        int currentY = (int) context.y;
        for (Row row : rows) {
            currentY += row.render(context.of(context.x, currentY), columnMap);
        }
    }

    public static void drawRowsWithBackground(DrawingContext context, List<Row> rows) {
        Map<String, Integer> columnMap = getColumnMaxWidthMap(rows);
        int maxWidth = getMaxWidth(rows);
        int maxHeight = getMaxHeight(rows);

        Screen.fill(context.matrixStack, (int) context.x - 1, (int) context.y - 1, context.width / 2 + maxWidth / 2 + 1, (int) context.y + rows.size() * (maxHeight + 1), Integer.MIN_VALUE);

        int currentY = (int) context.y;
        for (Row row : rows) {
            Screen.fill(context.matrixStack, context.width / 2 - maxWidth / 2, currentY, context.width / 2 + maxWidth / 2, currentY + getHeight(row), 553648127);
            currentY += row.render(context.of(context.x, currentY), columnMap) + 1;
        }
    }

    public static int getMaxWidth(List<Row> rows) {
        return getColumnMaxWidthMap(rows).values().stream()
                .reduce(0, Integer::sum);
    }

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

    public static int getHeight(Row row) {
        return row.getColumns().stream()
                .map(Column::getHeight)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}
