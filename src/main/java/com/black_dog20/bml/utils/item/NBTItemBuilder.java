package com.black_dog20.bml.utils.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

/**
 * Builder to build an item stack with NBT data.
 *
 * @author black_dog20
 */
public class NBTItemBuilder {

    private final ItemStack stack;
    private final CompoundNBT compoundNBT;

    private NBTItemBuilder(ItemStack stack) {
        this.stack = stack;
        this.compoundNBT = stack.getOrCreateTag();
    }

    /**
     * Factory for the builder.
     *
     * @param stack the input itemstack to add NBT to.
     * @return NBTItemBuilder.
     */
    public static NBTItemBuilder init(ItemStack stack) {
        return new NBTItemBuilder(stack);
    }

    /**
     * Factory for the builder.
     *
     * @param item the input item to add NBT to.
     * @return NBTItemBuilder.
     */
    public static NBTItemBuilder init(Item item) {
        return new NBTItemBuilder(new ItemStack(item));
    }

    /**
     * Adds a tag with a boolean value.
     *
     * @param key   the key for the tag.
     * @param value the value.
     * @return NBTItemBuilder.
     */
    public NBTItemBuilder addTag(String key, boolean value) {
        compoundNBT.putBoolean(key, value);
        return this;
    }

    /**
     * Adds a tag with a int value.
     *
     * @param key   the key for the tag.
     * @param value the value.
     * @return NBTItemBuilder.
     */
    public NBTItemBuilder addTag(String key, int value) {
        compoundNBT.putInt(key, value);
        return this;
    }

    /**
     * Adds a tag with a string value.
     *
     * @param key   the key for the tag.
     * @param value the value.
     * @return NBTItemBuilder.
     */
    public NBTItemBuilder addTag(String key, String value) {
        compoundNBT.putString(key, value);
        return this;
    }

    /**
     * Adds a tag with a long value.
     *
     * @param key   the key for the tag.
     * @param value the value.
     * @return NBTItemBuilder.
     */
    public NBTItemBuilder addTag(String key, long value) {
        compoundNBT.putLong(key, value);
        return this;
    }

    /**
     * Builds the final item.
     *
     * @return item stack with NBT data.
     */
    public ItemStack build() {
        return stack;
    }
}
