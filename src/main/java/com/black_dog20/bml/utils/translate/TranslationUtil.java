package com.black_dog20.bml.utils.translate;

import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

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
    public static String translateToString(ITranslation translation, ChatFormatting color) {
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
    public static String translateToString(ITranslation translation, ChatFormatting color, Object... objs) {
        return TextUtil.getFormattedText(translate(translation, color, objs));
    }

    /**
     * Translates a key.
     *
     * @param translation an enum containing the key and modid.
     * @return the formatted translated text.
     */
    public static TranslatableComponent translate(ITranslation translation) {
        return translate(translation, ChatFormatting.WHITE);
    }

    /**
     * Translates a key and formats it with the objects.
     *
     * @param translation an enum containing the key and modid.
     * @param objs        the objects.
     * @return the formatted translated text.
     */
    public static TranslatableComponent translate(ITranslation translation, Object... objs) {
        return translate(translation, ChatFormatting.WHITE, objs);
    }

    /**
     * Translates a key.
     *
     * @param translation an enum containing the key and modid.
     * @param color       the color for the text.
     * @return the formatted translated text.
     */
    public static TranslatableComponent translate(ITranslation translation, ChatFormatting color) {
        TranslatableComponent component = new TranslatableComponent(String.format("%s.%s", translation.getModId(), translation.getKey()));
        component.setStyle(component.getStyle().withColor(color));
        return component;
    }

    /**
     * Translates a key and formats it with the objects.
     *
     * @param translation an enum containing the key and modid.
     * @param color       the color for the text.
     * @param objs        the objects.
     * @return the formatted translated text.
     */
    public static TranslatableComponent translate(ITranslation translation, ChatFormatting color, Object... objs) {
        TranslatableComponent component = new TranslatableComponent(String.format("%s.%s", translation.getModId(), translation.getKey()), objs);
        component.setStyle(component.getStyle().withColor(color));
        return component;
    }

    /**
     * Tries to translate a resource location, will return the translated string or
     * returns the result of the function to the resource location.
     *
     * @param resourceLocation the resource location
     * @param defaultFunc      the function to apply if translation failed.
     * @return translated text or some string based on the resource location.
     */
    public static String translateResourceLocation(ResourceLocation resourceLocation, Function<ResourceLocation, String> defaultFunc) {
        String s = TextUtil.getFormattedText(new TranslatableComponent(resourceLocation.toString()));
        if (s.contains(resourceLocation.getNamespace()))
            return resourceLocation.toString();
        else
            return s;
    }
}
