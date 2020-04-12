package com.black_dog20.bml.utils.text;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TextUtil {

    public static String capitaliseFirstLetterFully(String text) {
        return Arrays.stream(text.split(" "))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
    }
}
