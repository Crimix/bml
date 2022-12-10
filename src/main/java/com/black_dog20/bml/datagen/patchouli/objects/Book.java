package com.black_dog20.bml.datagen.patchouli.objects;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class Book {

    private String name;
    private String landing_text;
    private String book_texture;
    private String filler_texture;
    private String crafting_texture;
    private String model;
    private String text_color;
    private String header_color;
    private String nameplate_color;
    private String link_color;
    private String link_hover_color;
    private String progress_bar_color;
    private String progress_bar_background;
    private String open_sound;
    private String flip_sound;
    private String index_icon;
    private String show_progress;
    private String version;
    private String subtitle;
    private String creative_tab;
    private String advancements_tab;
    private Boolean dont_generate_book;
    private String custom_book_item;
    private Boolean show_toasts;
    private Boolean use_blocky_font;
    private Boolean i18n;
    private Map<String, String> macros;

    private Book(String name, String landingText) {
        this.name = name;
        this.landing_text = landingText;
    }

    public Book withBookTexture(String book_texture) {
        this.book_texture = book_texture;
        return this;
    }

    public Book withFillerTexture(String filler_texture) {
        this.filler_texture = filler_texture;
        return this;
    }

    public Book withCraftingTexture(String crafting_texture) {
        this.crafting_texture = crafting_texture;
        return this;
    }

    public Book withModel(String model) {
        this.model = model;
        return this;
    }

    public Book withTextColor(String text_color) {
        this.text_color = text_color;
        return this;
    }

    public Book withHeaderColor(String header_color) {
        this.header_color = header_color;
        return this;
    }

    public Book withNameplateColor(String nameplate_color) {
        this.nameplate_color = nameplate_color;
        return this;
    }

    public Book withLinkColor(String link_color) {
        this.link_color = link_color;
        return this;
    }

    public Book withLinkHoverColor(String link_hover_color) {
        this.link_hover_color = link_hover_color;
        return this;
    }

    public Book withProgressBarColor(String progress_bar_color) {
        this.progress_bar_color = progress_bar_color;
        return this;
    }

    public Book withProgressBarBackground(String progress_bar_background) {
        this.progress_bar_background = progress_bar_background;
        return this;
    }

    public Book withOpenSound(String open_sound) {
        this.open_sound = open_sound;
        return this;
    }

    public Book withFlipSound(String flip_sound) {
        this.flip_sound = flip_sound;
        return this;
    }

    public Book withIndexIcon(String index_icon) {
        this.index_icon = index_icon;
        return this;
    }

    public Book withShowProgress(String show_progress) {
        this.show_progress = show_progress;
        return this;
    }

    public Book withVersion(String version) {
        this.version = version;
        return this;
    }

    public Book withSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public Book withCreativeTab(CreativeModeTab creative_tab) {
        this.creative_tab = CreativeModeTabRegistry.getName(creative_tab).toString();
        return this;
    }

    public Book withAdvancementsTab(String advancements_tab) {
        this.advancements_tab = advancements_tab;
        return this;
    }

    public Book withDontGenerateBook(Boolean dont_generate_book) {
        this.dont_generate_book = dont_generate_book;
        return this;
    }

    public Book withCustomBookItem(Item custom_book_item) {
        this.custom_book_item = ForgeRegistries.ITEMS.getKey(custom_book_item).toString();
        return this;
    }

    public Book withShowToasts(boolean show_toasts) {
        this.show_toasts = show_toasts;
        return this;
    }

    public Book withUseBlockyFont(boolean use_blocky_font) {
        this.use_blocky_font = use_blocky_font;
        return this;
    }

    public Book withI18n(boolean i18n) {
        this.i18n = i18n;
        return this;
    }

    public Book withMacro(Macro macro) {
        if (macros == null)
            macros = new HashMap<>();
        macros.put(macro.getKey(), macro.getValue());
        return this;
    }

    public static Book newBook(String name, String landingText) {
        return new Book(name, landingText);
    }

}
