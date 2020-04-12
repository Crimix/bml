package com.black_dog20.bml.utils.translate;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Interface for enums used in translation.
 *
 * @author black_dog20
 */
public interface ITranslation {

    String getKey();

    String getModId();

    default String getDescription() {
        return String.format("%s.%s", getModId(), getKey());
    }

    default ITextComponent getComponent() {
        return new TranslationTextComponent(String.format("%s.%s", getModId(), getKey()));
    }

    default ITextComponent getComponent(Object... objs) {
        return new TranslationTextComponent(String.format("%s.%s", getModId(), getKey()), objs);
    }
}
