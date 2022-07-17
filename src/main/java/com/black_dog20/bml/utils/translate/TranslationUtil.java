package com.black_dog20.bml.utils.translate;

import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
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
    public static MutableComponent translate(ITranslation translation) {
        return translate(translation, ChatFormatting.WHITE);
    }

    /**
     * Translates a key and formats it with the objects.
     *
     * @param translation an enum containing the key and modid.
     * @param objs        the objects.
     * @return the formatted translated text.
     */
    public static MutableComponent translate(ITranslation translation, Object... objs) {
        return translate(translation, ChatFormatting.WHITE, objs);
    }

    /**
     * Translates a key.
     *
     * @param translation an enum containing the key and modid.
     * @param color       the color for the text.
     * @return the formatted translated text.
     */
    public static MutableComponent translate(ITranslation translation, ChatFormatting color) {
        MutableComponent  component = Component.translatable(String.format("%s.%s", translation.getModId(), translation.getKey()));
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
    public static MutableComponent translate(ITranslation translation, ChatFormatting color, Object... objs) {
        MutableComponent component = Component.translatable(String.format("%s.%s", translation.getModId(), translation.getKey()), objs);
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
        String s = TextUtil.getFormattedText(Component.translatable(resourceLocation.toString()));
        if (s.contains(resourceLocation.getNamespace()))
            return resourceLocation.toString();
        else
            return s;
    }


    /* =============== START METHODS INSPIRED/BORROWED FROM https://github.com/sciwhiz12/Concord ===============
     * MIT License
     *
     * Copyright (c) 2020 SciWhiz12
     *
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     *
     * The above copyright notice and this permission notice shall be included in all
     * copies or substantial portions of the Software.
     *
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     * SOFTWARE.
     */

    /**
     * Translate either eager or lazy the input translation.
     *
     * @param translation       The translation text component.
     * @param translateOnClient Whether the message gets translated on the client or server before sending.
     * @return a {@link Component} with the specified message
     */
    public static Component createPossibleEagerTranslation(Component translation, boolean translateOnClient) {
        return translateOnClient ? translation : eagerTranslate(translation);
    }

    public static Component eagerTranslate(Component component) {
        if (component instanceof MutableComponent translation) {
            if (translation.getContents() instanceof TranslatableContents contents) {
                Object[] newArgs = Arrays.stream(contents.getArgs())
                        .map(TranslationUtil::eagerEvaluateArg)
                        .toArray();

                MutableComponent result = Component.translatable(Language.getInstance().getOrDefault(contents.getKey()), newArgs);
                result.setStyle(component.getStyle());

                component.getSiblings().stream()
                        .map(TranslationUtil::eagerEvaluateSiblings)
                        .forEachOrdered(result::append);

                return eagerEvaluateStyle(result);
            } else {
                return eagerEvaluateStyle(translation);
            }
        }

        return component;
    }

    private static Object eagerEvaluateArg(Object arg) {
        if (arg instanceof MutableComponent translatableComponent) {
            return eagerTranslate(translatableComponent);
        } else {
            return arg;
        }
    }

    private static Component eagerEvaluateSiblings(Component sibling) {
        if (sibling instanceof MutableComponent translatableComponent) {
            return eagerTranslate(translatableComponent);
        } else {
            return sibling;
        }
    }

    private static <T extends MutableComponent> T eagerEvaluateStyle(T component) {
        Style style = component.getStyle();
        HoverEvent hover = style.getHoverEvent();
        if (hover != null && hover.getAction() == HoverEvent.Action.SHOW_TEXT) {
            Component hoverText = hover.getValue(HoverEvent.Action.SHOW_TEXT);
            if (hoverText instanceof MutableComponent) {
                style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, eagerTranslate(hoverText)));
            }
        }

        component.setStyle(style);
        return component;
    }

    /* =============== END METHODS INSPIRED/BORROWED FROM https://github.com/sciwhiz12/Concord =============== */
}
