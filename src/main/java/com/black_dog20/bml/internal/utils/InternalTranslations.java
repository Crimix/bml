package com.black_dog20.bml.internal.utils;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.bml.utils.translate.TranslationUtil;

public class InternalTranslations extends TranslationUtil {
    public enum Translations implements ITranslation {
        PAGE_FOOTER("radial.page_footer");


        Translations(String modId, String key) {
            this.modId = modId;
            this.key = key;
        }

        Translations(String key) {
            this.modId = Bml.MOD_ID;
            this.key = key;
        }

        private final String modId;
        private final String key;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getModId() {
            return modId;
        }
    }
}
