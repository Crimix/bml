package com.black_dog20.bml.client.overlay.configure;

import com.black_dog20.bml.utils.color.Color4i;

import java.util.Optional;

/**
 * Interface for a configurable overlay.
 */
public interface IConfigurableOverlay {

    /**
     * @return the height of the overlay.
     */
    int getHeight();

    /**
     * @return the width of the overlay.
     */
    int getWidth();

    /**
     * Changes the position of the overlay.
     * This should be saved to a a config or something.
     *
     * @param x the new x position.
     * @param y the new y position.
     */
    void setPosition(int x, int y);

    /**
     * This should be read from a config or something.
     *
     * @return the x position for the leftmost top of the overlay.
     */
    int getPosX();

    /**
     * This should be read from a config or something.
     *
     * @return the y position for the leftmost top of the overlay.
     */
    int getPosY();

    /**
     * @return the active color for the overlay configure box.
     */
    Color4i getActiveColor();

    /**
     * @return the in-active color for the overlay configure box.
     */
    default Color4i getInactiveColor() {
        return Color4i.of(128, 128, 128);
    }

    /**
     * @return the active state of the overlay.
     */
    boolean getSate();

    /**
     * @return if the overlay can change active state
     */
    boolean isStateChangeable();

    /**
     * Changes the state of the overlay.
     * This should be saved to a config or something.
     *
     * @param state the new state
     */
    void setState(boolean state);

    /**
     * Returns the message to be displayed in the middle of the configure overlay box.
     *
     * @return the message or {@link Optional#empty()}.
     */
    Optional<String> getMessage();
}
