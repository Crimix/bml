package com.black_dog20.bml.utils.translate;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Utility class for use in translations.
 *
 * @author black_dog20
 */
public class TranslationUtil {

    /**
     * Translates a key to a string.
     *
     * @param translation an enum containing the key and modid.
     * @return the formatted translated text as a string.
     */
    public static String translateToString(ITranslation translation) {
        return translate(translation).getFormattedText();
    }

    /**
     * Translates a key to a string.
     *
     * @param translation an enum containing the key and modid.
     * @param color       the color for the text.
     * @return the formatted translated text as a string.
     */
    public static String translateToString(ITranslation translation, TextFormatting color) {
        return translate(translation, color).getFormattedText();
    }

    /**
     * Translates a key to a string and formats it with the objects.
     *
     * @param translation an enum containing the key and modid.
     * @param objs        the objects.
     * @return the formatted translated text as a string.
     */
    public static String translateToString(ITranslation translation, Object... objs) {
        return translate(translation, objs).getFormattedText();
    }

    /**
     * Translates a key.
     *
     * @param translation an enum containing the key and modid.
     * @return the formatted translated text.
     */
    public static TranslationTextComponent translate(ITranslation translation) {
        return translate(translation, TextFormatting.WHITE);
    }

    /**
     * Translates a key and formats it with the objects.
     *
     * @param translation an enum containing the key and modid.
     * @param objs        the objects.
     * @return the formatted translated text.
     */
    public static TranslationTextComponent translate(ITranslation translation, Object... objs) {
        return translate(translation, TextFormatting.WHITE, objs);
    }

    /**
     * Translates a key.
     *
     * @param translation an enum containing the key and modid.
     * @param color       the color for the text.
     * @return the formatted translated text.
     */
    public static TranslationTextComponent translate(ITranslation translation, TextFormatting color) {
        return (TranslationTextComponent) new TranslationTextComponent(String.format("%s.%s", translation.getModId(), translation.getKey()))
                .applyTextStyle(color);
    }

    /**
     * Translates a key and formats it with the objects.
     *
     * @param translation an enum containing the key and modid.
     * @param color       the color for the text.
     * @param objs        the objects.
     * @return the formatted translated text.
     */
    public static TranslationTextComponent translate(ITranslation translation, TextFormatting color, Object... objs) {
        return (TranslationTextComponent) new TranslationTextComponent(String.format("%s.%s", translation.getModId(), translation.getKey()), objs)
                .applyTextStyle(color);
    }
}
