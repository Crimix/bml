package com.black_dog20.bml.utils.item;

import net.minecraft.world.item.ItemStack;

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
     * @deprecated use {@link NBTUtil#putBoolean(ItemStack, String) instead}
     */
    @Deprecated
    public static ItemStack addTag(ItemStack stack, String tag) {
        return putBoolean(stack, tag);
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

    /**
     * Gets the specific tag from the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return returns the value stored or the default.
     */
    public static boolean getBoolean(ItemStack stack, String tag) {
        return stack.getOrCreateTag().getBoolean(tag);
    }

    /**
     * Puts true into the tag for the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return returns the itemstack where the tag was added.
     */
    public static ItemStack putBoolean(ItemStack stack, String tag) {
        return putBoolean(stack, tag, true);
    }

    /**
     * Puts the specific value into the tag for the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @param value the value.
     * @return returns the itemstack where the tag was added.
     */
    public static ItemStack putBoolean(ItemStack stack, String tag, boolean value) {
        stack.getOrCreateTag().putBoolean(tag, value);
        return stack;
    }

    /**
     * Gets the specific tag from the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return returns the value stored or the default.
     */
    public static int getInt(ItemStack stack, String tag) {
        return stack.getOrCreateTag().getInt(tag);
    }

    /**
     * Puts the specific value into the tag for the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @param value the value.
     * @return returns the itemstack where the tag was added.
     */
    public static ItemStack putInt(ItemStack stack, String tag, int value) {
        stack.getOrCreateTag().putInt(tag, value);
        return stack;
    }

    /**
     * Gets the specific tag from the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return returns the value stored or the default.
     */
    public static float getFloat(ItemStack stack, String tag) {
        return stack.getOrCreateTag().getFloat(tag);
    }

    /**
     * Puts the specific value into the tag for the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @param value the value.
     * @return returns the itemstack where the tag was added.
     */
    public static ItemStack putFloat(ItemStack stack, String tag, float value) {
        stack.getOrCreateTag().putFloat(tag, value);
        return stack;
    }

    /**
     * Gets the specific tag from the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return returns the value stored or the default.
     */
    public static double getDouble(ItemStack stack, String tag) {
        return stack.getOrCreateTag().getDouble(tag);
    }

    /**
     * Puts the specific value into the tag for the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @param value the value.
     * @return returns the itemstack where the tag was added.
     */
    public static ItemStack putDouble(ItemStack stack, String tag, double value) {
        stack.getOrCreateTag().putDouble(tag, value);
        return stack;
    }

    /**
     * Gets the specific tag from the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return returns the value stored or the default.
     */
    public static long getLong(ItemStack stack, String tag) {
        return stack.getOrCreateTag().getLong(tag);
    }

    /**
     * Puts the specific value into the tag for the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @param value the value.
     * @return returns the itemstack where the tag was added.
     */
    public static ItemStack putLong(ItemStack stack, String tag, long value) {
        stack.getOrCreateTag().putLong(tag, value);
        return stack;
    }

    /**
     * Gets the specific tag from the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @return returns the value stored or the default.
     */
    public static String getString(ItemStack stack, String tag) {
        return stack.getOrCreateTag().getString(tag);
    }

    /**
     * Puts the specific value into the tag for the itemstack.
     *
     * @param stack the itemstack.
     * @param tag   the tag.
     * @param value the value.
     * @return returns the itemstack where the tag was added.
     */
    public static ItemStack putString(ItemStack stack, String tag, String value) {
        stack.getOrCreateTag().putString(tag, value);
        return stack;
    }
}
