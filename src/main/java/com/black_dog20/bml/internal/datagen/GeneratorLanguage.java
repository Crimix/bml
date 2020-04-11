package com.black_dog20.bml.internal.datagen;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.datagen.BaseLanguageProvider;
import net.minecraft.data.DataGenerator;

import static com.black_dog20.bml.internal.utils.InternalTranslations.Translations.*;

public class GeneratorLanguage extends BaseLanguageProvider {

    public GeneratorLanguage(DataGenerator gen) {
        super(gen, Bml.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        // Tooltips
        addPrefixed(PAGE_FOOTER, "Page %d of %d");
    }
}
