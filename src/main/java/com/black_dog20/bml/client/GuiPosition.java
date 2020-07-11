package com.black_dog20.bml.client;

import com.mojang.datafixers.util.Pair;

public enum GuiPosition {
    TopLeft,
    TopCenter,
    TopRight,
    Left,
    Center,
    Right,
    BottomLeft,
    BottomCenter,
    BottomRight;

    public Pair<Integer, Integer> getPos(int width, int height, int elementWidth, int elementHeight) {
        return getPos(width, height, elementWidth, elementHeight, 0);
    }

    public Pair<Integer, Integer> getPos(int width, int height, int elementWidth, int elementHeight, int offset) {
        return getPos(width, height, elementWidth, elementHeight, offset, offset);
    }

    public Pair<Integer, Integer> getPos(int width, int height, int elementWidth, int elementHeight, int offsetX, int offsetY) {
        switch (this) {
            case TopLeft:
            default:
                return Pair.of(offsetX, offsetY);
            case TopCenter:
                return Pair.of((width / 2) - (elementWidth / 2) + offsetX, offsetY);
            case TopRight:
                return Pair.of(width - elementWidth - offsetX, offsetY);
            case Left:
                return Pair.of(offsetX, (height / 2) - (elementHeight / 2) + offsetY);
            case Center:
                return Pair.of((width / 2) - (elementWidth / 2) + offsetX, (height / 2) - (elementHeight / 2) + offsetY);
            case Right:
                return Pair.of(width - elementWidth - offsetX, (height / 2) - (elementHeight / 2) + offsetY);
            case BottomLeft:
                return Pair.of(offsetX, height - elementHeight - offsetY);
            case BottomCenter:
                return Pair.of((width / 2) - (elementWidth / 2) + offsetX, height - elementHeight - offsetY);
            case BottomRight:
                return Pair.of(width - elementWidth - offsetX, height - elementHeight - offsetY);
        }
    }
}
