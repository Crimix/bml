package com.black_dog20.bml.utils.text;

import com.google.common.collect.Streams;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * Returns the formatted text of the text component.
     *
     * @param textComponent the text component.
     * @return the formatted text.
     */
    public static String getFormattedText(ITextComponent textComponent) {
        StringBuilder stringbuilder = new StringBuilder();
        String s = "";
        Iterator<ITextComponent> iterator = stream(textComponent).iterator();

        while (iterator.hasNext()) {
            ITextComponent itextcomponent = iterator.next();
            String s1 = itextcomponent.getUnformattedComponentText();
            if (!s1.isEmpty()) {
                String s2 = getFormattingCode(itextcomponent.getStyle());
                if (!s2.equals(s)) {
                    if (!s.isEmpty()) {
                        stringbuilder.append((Object) TextFormatting.RESET);
                    }

                    stringbuilder.append(s2);
                    s = s2;
                }

                stringbuilder.append(s1);
            }
        }

        if (!s.isEmpty()) {
            stringbuilder.append((Object) TextFormatting.RESET);
        }

        return stringbuilder.toString();
    }

    private static String getFormattingCode(Style style) {
        if (style.isEmpty()) {
            return "";
        } else {
            StringBuilder stringbuilder = new StringBuilder();
            if (style.getColor() != null) {
                String name = style.getColor().field_240741_d_;
                if (isNotNullOrEmpty(name))
                    stringbuilder.append((Object) TextFormatting.getValueByName(name));
                else
                    stringbuilder.append((Object) style.getColor().func_240747_b_());
            }

            if (style.getBold()) {
                stringbuilder.append((Object) TextFormatting.BOLD);
            }

            if (style.getItalic()) {
                stringbuilder.append((Object) TextFormatting.ITALIC);
            }

            if (style.getUnderlined()) {
                stringbuilder.append((Object) TextFormatting.UNDERLINE);
            }

            if (style.getObfuscated()) {
                stringbuilder.append((Object) TextFormatting.OBFUSCATED);
            }

            if (style.getStrikethrough()) {
                stringbuilder.append((Object) TextFormatting.STRIKETHROUGH);
            }

            return stringbuilder.toString();
        }
    }

    private static Stream<ITextComponent> stream(ITextComponent textComponent) {
        return Streams.concat(Stream.of(textComponent), textComponent.getSiblings().stream().flatMap(i -> stream(i)));
    }
}
