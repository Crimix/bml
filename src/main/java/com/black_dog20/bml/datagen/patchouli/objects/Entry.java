package com.black_dog20.bml.datagen.patchouli.objects;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Entry {
    private String name;
    private String category;
    private String icon;
    private List<Page> pages;
    private String advancement;
    private String flag;
    private Boolean priority;
    private Boolean secret;
    private Boolean read_by_default;
    private Integer sortnum;
    private String turnin;

    private Entry(String name, String category, String icon) {
        this.name = name;
        this.category = category;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public List<Page> getPages() {
        return pages;
    }

    public Entry addPage(Page page) {
        if (pages == null) {
            pages = new ArrayList<>();
        }
        pages.add(page);
        return this;
    }

    public Entry withAdvancement(String advancement) {
        this.advancement = advancement;
        return this;
    }

    public Entry withFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Entry withPriority(Boolean priority) {
        this.priority = priority;
        return this;
    }

    public Entry withSecret(Boolean secret) {
        this.secret = secret;
        return this;
    }

    public Entry withReadByDefault(Boolean read_by_default) {
        this.read_by_default = read_by_default;
        return this;
    }

    public Entry withSortnum(Integer sortnum) {
        this.sortnum = sortnum;
        return this;
    }

    public Entry withTurnin(String turnin) {
        this.turnin = turnin;
        return this;
    }

    public static Entry newEntry(String name, String category, String icon) {
        return new Entry(name, category, icon);
    }

    public static Entry newEntry(String name, String category, Item icon) {
        return new Entry(name, category, icon.getRegistryName().toString());
    }

    public static Entry newEntry(String name, Category category, String icon) {
        return new Entry(name, category.getId(), icon);
    }

    public static Entry newEntry(String name, Category category, Item icon) {
        return new Entry(name, category.getId(), icon.getRegistryName().toString());
    }
}
