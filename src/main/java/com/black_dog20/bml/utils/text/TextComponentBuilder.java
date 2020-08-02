package com.black_dog20.bml.utils.text;

import com.black_dog20.bml.utils.translate.ITranslation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;

/**
 * Builder to help with TextComponents.
 * Makes it easier to appends siblings and style them.
 */
public class TextComponentBuilder {

    private TextComponent root;
    private TextComponent prev;

    private TextComponentBuilder() {
        root = new StringTextComponent("");
    }

    /**
     * Creates a new builder using the component as the root.
     *
     * @param component the root component.
     * @return A new TextComponentBuilder.
     */
    public static TextComponentBuilder of(TextComponent component) {
        TextComponentBuilder builder = new TextComponentBuilder();
        return builder.with((TextComponent) component.deepCopy());
    }

    /**
     * Creates a new builder using the text as a StringTextComponent as the root.
     *
     * @param text the text to be the root component.
     * @return A new TextComponentBuilder.
     */
    public static TextComponentBuilder of(String text) {
        return of(new StringTextComponent(text));
    }

    /**
     * Creates a new builder using the number as a StringTextComponent as the root.
     *
     * @param integer the number to be the root component.
     * @return A new TextComponentBuilder.
     */
    public static TextComponentBuilder of(int integer) {
        return of(Integer.toString(integer));
    }

    /**
     * Creates a new builder using the translation as the root.
     *
     * @param translation the translation to be the root component.
     * @return A new TextComponentBuilder.
     */
    public static TextComponentBuilder of(ITranslation translation) {
        return of((TextComponent) translation.get());
    }

    /**
     * Appends a component to the root.
     *
     * @param component the component to append.
     * @return the current builder.
     */
    public TextComponentBuilder with(TextComponent component) {
        if (prev != null) {
            root.append(prev);
        }
        prev = component;
        return this;
    }

    /**
     * Appends a text as a StringTextComponent to the root.
     *
     * @param text the string to append.
     * @return the current builder.
     */
    public TextComponentBuilder with(String text) {
        return with(new StringTextComponent(text));
    }

    /**
     * Appends a number as a StringTextComponent to the root.
     *
     * @param integer the number to append.
     * @return the current builder.
     */
    public TextComponentBuilder with(int integer) {
        return with(Integer.toString(integer));
    }

    /**
     * Appends a translation to the root.
     *
     * @param translation the translation to append.
     * @return the current builder.
     */
    public TextComponentBuilder with(ITranslation translation) {
        return with((TextComponent) translation.get());
    }

    /**
     * Appends a blank space to the root.
     *
     * @return the current builder.
     */
    public TextComponentBuilder space() {
        return with(new StringTextComponent(" "));
    }

    /**
     * Applies the formatting to the last appended component.
     *
     * @return the current builder.
     */
    public TextComponentBuilder format(TextFormatting formatting) {
        if (prev != null) {
            prev.setStyle(prev.getStyle().setFormatting(formatting));
        }
        return this;
    }

    /**
     * Builds and returns the TextComponent.
     *
     * @return the TextComponent.
     */
    public TextComponent build() {
        if (prev != null) {
            root.append(prev);
        }
        return root;
    }
}
