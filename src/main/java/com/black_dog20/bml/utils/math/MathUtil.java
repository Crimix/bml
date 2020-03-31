package com.black_dog20.bml.utils.math;

import java.util.Locale;

/**
 * Utility math helper class.
 *
 * @author black_dog20
 */
public class MathUtil {

    /**
     * Clamps a value between min and max.
     *
     * @param value the value.
     * @param min   the minimum value.
     * @param max   the maximum value.
     * @return a value between min and max.
     */
    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        return Math.min(value, max);
    }

    /**
     * Clamps a value between min and max.
     *
     * @param value the value.
     * @param min   the minimum value.
     * @param max   the maximum value.
     * @return a value between min and max.
     */
    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        return Math.min(value, max);
    }

    /**
     * Clamps a value between min and max.
     *
     * @param value the value.
     * @param min   the minimum value.
     * @param max   the maximum value.
     * @return a value between min and max.
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        return Math.min(value, max);
    }

    /**
     * Formats a value to a string with a separator for thousands.
     *
     * @param value the value to format.
     * @return the formatted value.
     */
    public static String formatThousands(int value) {
        if (value < 1000)
            return String.valueOf(value);

        return String.format(Locale.US, "%,d", value);
    }
}
