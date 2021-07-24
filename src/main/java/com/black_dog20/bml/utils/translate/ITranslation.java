package com.black_dog20.bml.utils.translate;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/**
 * Interface for enums used in translation.
 *
 * @author black_dog20
 */
public interface ITranslation {

    /**
     * Get the key for this translation.
     *
     * @return the key.
     */
    String getKey();

    /**
     * Get the mod id for this translation.
     *
     * @return the mod id.
     */
    String getModId();

    /**
     * Gets the description of this translation.
     * In the following format
     * modId.key
     *
     * @return the description.
     */
    default String getDescription() {
        return String.format("%s.%s", getModId(), getKey());
    }

    /**
     * Gets a TranslationTextComponent from this translation.
     *
     * @return the TranslationTextComponent.
     */
    default Component get() {
        return TranslationUtil.translate(this);
    }

    /**
     * Gets a TranslationTextComponent from this translation with text formatting.
     *
     * @param formatting the text formatting to be applied to it.
     * @return the TranslationTextComponent.
     */
    default Component get(ChatFormatting formatting) {
        return TranslationUtil.translate(this, formatting);
    }

    /**
     * Gets a TranslationTextComponent from this translation formatted with the objects.
     *
     * @param objs the objects.
     * @return the TranslationTextComponent.
     */
    default Component get(Object... objs) {
        return TranslationUtil.translate(this, objs);
    }

    /**
     * Gets a TranslationTextComponent from this translation with text formatting and formatted with the objects.
     *
     * @param formatting the text formatting to be applied to it.
     * @param objs       the objects.
     * @return the TranslationTextComponent.
     */
    default Component get(ChatFormatting formatting, Object... objs) {
        return TranslationUtil.translate(this, formatting, objs);
    }
}
