package com.black_dog20.bml.datagen;

import com.black_dog20.bml.utils.text.TextUtil;
import com.black_dog20.bml.utils.translate.ITranslation;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Supplier;

/**
 * Base class for language providers.
 * Exposes helper methods.
 *
 * @author black_dog20
 */
public abstract class BaseLanguageProvider extends LanguageProvider {
    private final String modid;

    /**
     * The constructor for the provider.
     *
     * @param gen    the data generator.
     * @param modid  the mod id.
     * @param locale the locale.
     */
    public BaseLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
        this.modid = modid;
    }

    /**
     * Adds a prefixed text in the following format: modid.key:text
     *
     * @param key  the key.
     * @param text the text.
     */
    protected void addPrefixed(String key, String text) {
        add(String.format("%s.%s", modid, key), text);
    }

    /**
     * Adds a prefixed text in the following format: modid.key:text
     *
     * @param translation the key.
     * @param text        the text.
     */
    protected void addPrefixed(ITranslation translation, String text) {
        if (!modid.equals(translation.getModId())) {
            throw new IllegalStateException("Mod id for translation is not the same as for the generator");
        }
        add(translation.getDescription(), text);
    }

    /**
     * Adds a prefixed text in the following format: modid.key:text
     *
     * @param key   the key.
     * @param text  the text.
     * @param color the color.
     */
    protected void addPrefixed(String key, String text, ChatFormatting color) {
        add(String.format("%s.%s", modid, key), TextUtil.getFormattedText(Component.literal(text).withStyle(color)));
    }

    /**
     * Adds a prefixed text in the following format: modid.key:text
     *
     * @param translation the key.
     * @param text        the text.
     * @param color       the color.
     */
    protected void addPrefixed(ITranslation translation, String text, ChatFormatting color) {
        if (!modid.equals(translation.getModId())) {
            throw new IllegalStateException("Mod id for translation is not the same as for the generator");
        }
        add(translation.getDescription(), TextUtil.getFormattedText(Component.literal(text).withStyle(color)));
    }

    /**
     * Adds a prefixed text in the following format: modid.key:text
     * Objects can either be Strings or TextFormatting, they are applied in order.
     *
     * @param text   the text.
     * @param styles the styles to apply.
     * @return a formatted string.
     */
    protected String style(String text, ChatFormatting... styles) {
        return TextUtil.getFormattedText(Component.literal(text).withStyle(styles));
    }

    /**
     * Adds a enchantment and its description to be used by <a href="https://www.curseforge.com/minecraft/mc-mods/enchantment-descriptions">Enchantment Descriptions</a>
     *
     * @param key         the enchantment.
     * @param name        the name of the enchantment
     * @param description the description of the enchantment.
     */
    protected void addEnchantment(Supplier<? extends Enchantment> key, String name, String description) {
        add(key.get(), name, description);
    }

    /**
     * Adds a enchantment and its description to be used by <a href="https://www.curseforge.com/minecraft/mc-mods/enchantment-descriptions">Enchantment Descriptions</a>
     *
     * @param key         the enchantment.
     * @param name        the name of the enchantment
     * @param description the description of the enchantment.
     */
    protected void add(Enchantment key, String name, String description) {
        add(key.getDescriptionId(), name);
        add(String.format("%s.desc", key.getDescriptionId()), description);
    }
}
