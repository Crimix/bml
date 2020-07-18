package com.black_dog20.bml.utils.text;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TextUtil {

    /**
     * Returns a string where the first letter in every word is capitalised.
     * Does not make the rest of the string lowered.
     *
     * @param text the text string to capitalise.
     * @return the capitalized string or empty string if the input is null or empty
     */
    public static String capitaliseFirstLetterFully(String text) {
        if (isNullOrEmpty(text))
            return "";
        return Arrays.stream(text.split(" "))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
    }

    /**
     * Returns if the string is null or empty.
     *
     * @param text the string to test.
     * @return true if null or empty.
     */
    public static boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    /**
     * Returns if the string is not null or empty.
     *
     * @param text the string to test.
     * @return true if not null or empty.
     */
    public static boolean isNotNullOrEmpty(String text) {
        return !isNullOrEmpty(text);
    }

}
