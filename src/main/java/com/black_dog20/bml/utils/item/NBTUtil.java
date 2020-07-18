package com.black_dog20.bml.utils.item;

import net.minecraft.item.ItemStack;

public class NBTUtil {

    /**
     * Gets the tag for soulbound.
     *
     * @return the tag for soulbound.
     */
    public static String getSoulboundTag() {
        return "bml-soulbound";
    }


    /**
     * Adds a tag to a itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return the itemstack with the tag.
     */
    public static ItemStack addTag(ItemStack stack, String tag) {
        stack.getOrCreateTag().putBoolean(tag, true);
        return stack;
    }

    /**
     * Removes a tag from a itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return the itemstack without the tag.
     */
    public static ItemStack removeTag(ItemStack stack, String tag) {
        stack.getOrCreateTag().remove(tag);
        return stack;
    }

    /**
     * Checks if an itemstack has a specific tag.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return returns true if the itemstack has the tag.
     */
    public static boolean hasTag(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.getOrCreateTag().contains(tag);
    }
}
