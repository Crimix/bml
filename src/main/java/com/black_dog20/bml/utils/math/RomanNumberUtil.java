package com.black_dog20.bml.utils.math;

import java.util.Collections;

public class RomanNumberUtil {

    /**
     * Gets the number as a roman number string.
     *
     * @param number the number.
     * @return the roman number string
     */
    public static String getRomanNumber(int number) {
        return String.join("", Collections.nCopies(number, "I"))
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                .replace("LXL", "XC")
                .replace("CCCCC", "D")
                .replace("CCCC", "CD")
                .replace("DD", "M")
                .replace("DCD", "CM");
    }
}
