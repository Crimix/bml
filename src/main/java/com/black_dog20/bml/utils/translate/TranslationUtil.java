package com.black_dog20.bml.utils.translate;

import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Function;

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
        return TextUtil.getFormattedText(translate(translation));
    }

    /**
     * Translates a key to a string.
     *
     * @param translation an enum containing the key and modid.
     * @param color       the color for the text.
     * @return the formatted translated text as a string.
     */
    public static String translateToString(ITranslation translation, TextFormatting color) {
        return TextUtil.getFormattedText(translate(translation, color));
    }

    /**
     * Translates a key to a string and formats it with the objects.
     *
     * @param translation an enum containing the key and modid.
     * @param objs        the objects.
     * @return the formatted translated text as a string.
     */
    public static String translateToString(ITranslation translation, Object... objs) {
        return TextUtil.getFormattedText(translate(translation, objs));
    }

    /**
     * Translates a key to a string and formats it with the objects.
     *
     * @param translation an enum containing the key and modid.
     * @param color       the color for the text.
     * @param objs        the objects.
     * @return the formatted translated text as a string.
     */
    public static String translateToString(ITranslation translation, TextFormatting color, Object... objs) {
        return TextUtil.getFormattedText(translate(translation, color, objs));
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
                .func_240701_a_(color);
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
                .func_240701_a_(color);
    }

    /**
     * Tries to translate a resource location, will return the translated string or
     * return the result of te function to the resource location.
     *
     * @param resourceLocation the resource location
     * @param defaultFunc      the function to apply if translation failed.
     * @return translated text or some string based on the resource location.
     */
    public static String translateResourceLocation(ResourceLocation resourceLocation, Function<ResourceLocation, String> defaultFunc) {
        String s = TextUtil.getFormattedText(new TranslationTextComponent(resourceLocation.toString()));
        if (s.contains(resourceLocation.getNamespace()))
            return resourceLocation.toString();
        else
            return s;
    }
}
