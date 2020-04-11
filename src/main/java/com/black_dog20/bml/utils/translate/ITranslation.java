package com.black_dog20.bml.utils.translate;

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
}
