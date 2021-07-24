package com.black_dog20.bml.datagen.patchouli.objects;

import net.minecraft.world.item.Item;

public class Page {
    private String type;
    private String advancement;
    private String flag;
    private String anchor;
    private String text;
    private String title;
    private String[] images;
    private Boolean border;
    private String recipe;
    private String recipe2;
    private String multiblock_id;
    private Object multiblock;
    private Boolean enable_visualize;
    private String entity;
    private Float scale;
    private Float offset;
    private Boolean rotate;
    private Float default_rotation;
    private String name;
    private String item;
    private Boolean link_recipe;
    private String url;
    private String link_text;
    private String[] entries;
    private String trigger;
    private Boolean draw_filler;

    private Page(String type) {
        this.type = type;
    }

    public Page withType(String type) {
        this.type = type;
        return this;
    }

    public Page withAdvancement(String advancement) {
        this.advancement = advancement;
        return this;
    }

    public Page withFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Page withAnchor(String anchor) {
        this.anchor = anchor;
        return this;
    }

    public Page withText(String text) {
        this.text = text;
        return this;
    }

    public Page withTitle(String title) {
        this.title = title;
        return this;
    }

    public Page withImages(String[] images) {
        this.images = images;
        return this;
    }

    public Page withBorder(boolean border) {
        this.border = border;
        return this;
    }

    public Page withRecipe(String recipe) {
        this.recipe = recipe;
        return this;
    }

    public Page withRecipe2(String recipe2) {
        this.recipe2 = recipe2;
        return this;
    }

    public Page withMultiblockId(String multiblock_id) {
        this.multiblock_id = multiblock_id;
        return this;
    }

    public Page withMultiblock(Object multiblock) {
        this.multiblock = multiblock;
        return this;
    }

    public Page withEnableVisualize(Boolean enable_visualize) {
        this.enable_visualize = enable_visualize;
        return this;
    }

    public Page withEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public Page withScale(Float scale) {
        this.scale = scale;
        return this;
    }

    public Page withOffset(Float offset) {
        this.offset = offset;
        return this;
    }

    public Page withRotate(boolean rotate) {
        this.rotate = rotate;
        return this;
    }

    public Page withDefaultRotation(Float default_rotation) {
        this.default_rotation = default_rotation;
        return this;
    }

    public Page withName(String name) {
        this.name = name;
        return this;
    }

    public Page withItem(String item) {
        this.item = item;
        return this;
    }

    public Page withLinkRecipe(boolean link_recipe) {
        this.link_recipe = link_recipe;
        return this;
    }

    public Page withUrl(String url) {
        this.url = url;
        return this;
    }

    public Page withLinkText(String link_text) {
        this.link_text = link_text;
        return this;
    }

    public Page withEntries(String[] entries) {
        this.entries = entries;
        return this;
    }

    public Page withTrigger(String trigger) {
        this.trigger = trigger;
        return this;
    }

    public Page withDrawFiller(boolean draw_filler) {
        this.draw_filler = draw_filler;
        return this;
    }

    public static Page TextPage(String text) {
        return new Page("text")
                .withText(text);
    }

    public static Page TextPage(String title, String text) {
        return new Page("text")
                .withText(text)
                .withTitle(title);
    }

    public static Page ImagePage(String[] images) {
        return new Page("image")
                .withImages(images);
    }

    public static Page CraftingPage(String recipe) {
        return new Page("crafting")
                .withRecipe(recipe);
    }

    public static Page SmeltingPage(String recipe) {
        return new Page("smelting")
                .withRecipe(recipe);
    }

    public static Page MultiblockPage(String name, String multiblock_id) {
        return new Page("multiblock")
                .withName(name)
                .withMultiblockId(multiblock_id);
    }

    public static Page MultiblockPage(String name, Object multiblock) {
        return new Page("multiblock")
                .withName(name)
                .withMultiblock(multiblock);
    }

    public static Page EntityPage(String entity) {
        return new Page("entity")
                .withEntity(entity);
    }

    public static Page SpotlightPage(Item item) {
        return new Page("spotlight")
                .withItem(item.getRegistryName().toString());
    }

    public static Page SpotlightPage(String item) {
        return new Page("spotlight")
                .withItem(item);
    }

    public static Page LinkPage(String url, String link_text) {
        return new Page("link")
                .withUrl(url)
                .withLinkText(link_text);
    }

    public static Page RelationsPage() {
        return new Page("relations");
    }

    public static Page QuestPage() {
        return new Page("quest");
    }

    public static Page EmptyPage() {
        return new Page("empty");
    }

}
