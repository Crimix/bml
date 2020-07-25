package com.black_dog20.bml.utils.color;

import com.black_dog20.bml.utils.math.MathUtil;

import java.awt.*;

public class Color4i {

    private int red;
    private int green;
    private int blue;
    private int alpha;
    private Color color;

    private Color4i(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.color = new Color(red, green, blue, alpha);
    }

    /**
     * Creates a color object with the specified RGBA values.
     *
     * @param red   The red value between 0 and 255.
     * @param green The green value between 0 and 255.
     * @param blue  The blue value between 0 and 255.
     * @param alpha The alpha value between 0 and 255.
     * @return a color object representation of the color.
     */
    public static Color4i of(int red, int green, int blue, int alpha) {
        int r = MathUtil.clamp(red, 0, 255);
        int g = MathUtil.clamp(green, 0, 255);
        int b = MathUtil.clamp(blue, 0, 255);
        int a = MathUtil.clamp(alpha, 0, 255);
        return new Color4i(r, g, b, a);
    }

    /**
     * Creates a color object with the specified RGB values.
     * It has a default alpha of 255.
     *
     * @param red   The red value between 0 and 255.
     * @param green The green value between 0 and 255.
     * @param blue  The blue value between 0 and 255.
     * @return a color object representation of the color.
     */
    public static Color4i of(int red, int green, int blue) {
        return of(red, green, blue, 255);
    }

    /**
     * Gets the red value.
     *
     * @return red value
     */
    public int getRed() {
        return red;
    }

    /**
     * Gets the green value.
     *
     * @return green value
     */
    public int getGreen() {
        return green;
    }

    /**
     * Gets the blue value.
     *
     * @return blue value
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Gets the alpha value.
     *
     * @return alpha value
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * Gets a {@link Color} object from this object.
     *
     * @return {@link Color} object
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets a {@link Color} object from this object.
     *
     * @param alpha The alpha value between 0 and 255 which should obe used.
     * @return {@link Color} object
     */
    public Color getColor(int alpha) {
        int a = MathUtil.clamp(alpha, 0, 255);
        return new Color(red, green, blue, a);
    }

    /**
     * Gets a RGB value as an integer.
     *
     * @return the color represented as an int
     */
    public int getValue() {
        return color.getRGB();
    }

    /**
     * Gets a RGB value as an integer.
     *
     * @param alpha The alpha value between 0 and 255 which should obe used.
     * @return the color represented as an int
     */
    public int getValue(int alpha) {
        int a = MathUtil.clamp(alpha, 0, 255);
        return new Color(red, green, blue, a).getRGB();
    }
}
