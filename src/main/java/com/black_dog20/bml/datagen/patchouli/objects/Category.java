package com.black_dog20.bml.datagen.patchouli.objects;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class Category {

    private transient String category_id;
    private String name;
    private String description;
    private String icon;
    private String parent;
    private String flag;
    private Integer sortnum;
    private Boolean secret;


    private Category(String category_id, String name, String description, String icon) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public String getId() {
        return category_id;
    }

    public Category withParent(String parent) {
        this.parent = parent;
        return this;
    }

    public Category withFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Category withSortnum(Integer sortnum) {
        this.sortnum = sortnum;
        return this;
    }

    public Category withSecret(Boolean secret) {
        this.secret = secret;
        return this;
    }

    public static Category newCategory(String id, String name, String category, String icon) {
        return new Category(id, name, category, icon);
    }

    public static Category newCategory(String id, String name, String category, Item icon) {
        return new Category(id, name, category, ForgeRegistries.ITEMS.getKey(icon).toString());
    }

}
