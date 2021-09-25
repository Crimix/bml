package com.black_dog20.bml.internal.datagen;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.datagen.BaseLanguageProvider;
import net.minecraft.ChatFormatting;
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
        addPrefixed(RIGHT_CLICK_FOR_OPTIONS, style("Right click", ChatFormatting.BLUE) + " for options");
        addPrefixed(RIGHT_CLICK_TO, style("Right click", ChatFormatting.BLUE) + " to");
        addPrefixed(CONFIRM, "Confirm");
        addPrefixed(NOT_LEVELABLE, "Unlevelable", ChatFormatting.RED);
        addPrefixed(MAX_LEVEL, "Max", ChatFormatting.DARK_PURPLE);
        addPrefixed(LEVEL, "Level");
        addPrefixed(PROGRESS, "%d (%d/%d)");
        addPrefixed(SOULBOUND_ACHIEVED, "You feel fused together with your %s, not even death will part you with it now!");
    }
}
