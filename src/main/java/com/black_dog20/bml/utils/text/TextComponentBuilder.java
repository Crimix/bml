package com.black_dog20.bml.utils.text;

import com.black_dog20.bml.utils.translate.ITranslation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Supplier;

/**
 * Builder to help with TextComponents.
 * Makes it easier to appends siblings and style them.
 */
public class TextComponentBuilder {

    private BaseComponent root;
    private BaseComponent prev;

    private TextComponentBuilder() {
        root = new TextComponent("");
    }

    /**
     * Creates a new builder using the component as the root.
     *
     * @param component the root component.
     * @return A new TextComponentBuilder.
     */
    public static TextComponentBuilder of(Component component) {
        TextComponentBuilder builder = new TextComponentBuilder();
        return builder.with((BaseComponent) component.copy());
    }

    /**
     * Creates a new builder using the text as a StringTextComponent as the root.
     *
     * @param text the text to be the root component.
     * @return A new TextComponentBuilder.
     */
    public static TextComponentBuilder of(String text) {
        return of(new TextComponent(text));
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
        return of(translation.get());
    }

    /**
     * Appends a component to the root.
     *
     * @param component the component to append.
     * @return the current builder.
     */
    public TextComponentBuilder with(Component component) {
        if (prev != null) {
            root.append(prev);
        }
        prev = (BaseComponent) component.copy();
        return this;
    }

    /**
     * Appends a text as a StringTextComponent to the root.
     *
     * @param text the string to append.
     * @return the current builder.
     */
    public TextComponentBuilder with(String text) {
        return with(new TextComponent(text));
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
        return with((BaseComponent) translation.get());
    }

    /**
     * Appends a blank space to the root.
     *
     * @return the current builder.
     */
    public TextComponentBuilder space() {
        return with(new TextComponent(" "));
    }

    /**
     * Applies the formatting to the last appended component.
     *
     * @return the current builder.
     */
    public TextComponentBuilder format(ChatFormatting formatting) {
        if (prev != null) {
            prev.setStyle(prev.getStyle().withColor(formatting));
        }
        return this;
    }

    /**
     * Builds and returns the TextComponent.
     *
     * @return the TextComponent.
     */
    public BaseComponent build() {
        if (prev != null) {
            root.append(prev);
        }
        return root;
    }


    /**
     * Appends a component to the root.
     *
     * @param component the component to append if the supplier returns true.
     * @param supplier  the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder with(Component component, Supplier<Boolean> supplier) {
        if (Boolean.TRUE.equals(supplier.get())) {
            return with(component);
        }
        return this;
    }

    /**
     * Appends a component to the root.
     *
     * @param componentTrue  the component to append if the supplier returns true.
     * @param componentFalse the component to append if the supplier returns false.
     * @param supplier       the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder with(Component componentTrue, Component componentFalse, Supplier<Boolean> supplier) {
        if (Boolean.TRUE.equals(supplier.get())) {
            return with(componentTrue);
        } else {
            return with(componentFalse);
        }
    }

    /**
     * Appends a text to the root.
     *
     * @param text     the text to append if the supplier returns true.
     * @param supplier the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder with(String text, Supplier<Boolean> supplier) {
        if (Boolean.TRUE.equals(supplier.get())) {
            return with(text);
        }
        return this;
    }

    /**
     * Appends a text to the root.
     *
     * @param textTrue  the text to append if the supplier returns true.
     * @param textFalse the text to append if the supplier returns false.
     * @param supplier  the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder with(String textTrue, String textFalse, Supplier<Boolean> supplier) {
        if (Boolean.TRUE.equals(supplier.get())) {
            return with(textTrue);
        } else {
            return with(textFalse);
        }
    }

    /**
     * Appends a number as a StringTextComponent to the root.
     *
     * @param integer  the number to append if the supplier returns true.
     * @param supplier the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder with(int integer, Supplier<Boolean> supplier) {
        if (Boolean.TRUE.equals(supplier.get())) {
            return with(integer);
        }
        return this;
    }

    /**
     * Appends a number as a StringTextComponent to the root.
     *
     * @param integerTrue  the number to append if the supplier returns true.
     * @param integerFalse the number to append if the supplier returns false.
     * @param supplier     the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder with(int integerTrue, int integerFalse, Supplier<Boolean> supplier) {
        if (Boolean.TRUE.equals(supplier.get())) {
            return with(integerTrue);
        } else {
            return with(integerFalse);
        }
    }

    /**
     * Appends a translation to the root.
     *
     * @param translation the translation to append if the supplier returns true.
     * @param supplier    the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder with(ITranslation translation, Supplier<Boolean> supplier) {
        if (Boolean.TRUE.equals(supplier.get())) {
            return with(translation);
        }
        return this;
    }

    /**
     * Appends a translation to the root.
     *
     * @param translationTrue  the translation to append if the supplier returns true.
     * @param translationFalse the translation to append if the supplier returns false.
     * @param supplier         the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder with(ITranslation translationTrue, ITranslation translationFalse, Supplier<Boolean> supplier) {
        if (Boolean.TRUE.equals(supplier.get())) {
            return with((BaseComponent) translationTrue.get());
        } else {
            return with((BaseComponent) translationFalse.get());
        }
    }

    /**
     * Appends a blank space to the root if the supplier returns true.
     *
     * @param supplier the supplier.
     * @return the current builder.
     */
    public TextComponentBuilder space(Supplier<Boolean> supplier) {
        if (supplier.get()) {
            return with(new TextComponent(" "));
        }
        return this;
    }
}
