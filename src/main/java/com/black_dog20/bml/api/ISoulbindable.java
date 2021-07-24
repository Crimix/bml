package com.black_dog20.bml.api;

import com.black_dog20.bml.utils.item.NBTUtil;
import net.minecraft.world.item.ItemStack;

/**
 * Interface for soulbinding an item.
 * Every method can be overridden, to accommodate every system,
 * as long as {@link ISoulbindable#isSoulbound(ItemStack)} returns true if the stack should be kept when the player dies.
 *
 * @author black_dog20
 */
public interface ISoulbindable {

    default void soulbind(ItemStack stack) {
        NBTUtil.putBoolean(stack, NBTUtil.getSoulboundTag());
    }

    default void unSoulbind(ItemStack stack) {
        NBTUtil.removeTag(stack, NBTUtil.getSoulboundTag());
    }

    default boolean isSoulbound(ItemStack stack) {
        return NBTUtil.getBoolean(stack, NBTUtil.getSoulboundTag());
    }
}
