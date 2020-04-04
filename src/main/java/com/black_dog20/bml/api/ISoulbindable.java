package com.black_dog20.bml.api;

import com.black_dog20.bml.utils.nbt.NBTUtil;
import net.minecraft.item.ItemStack;

public interface ISoulbindable {

    default void soulbind(ItemStack stack) {
        NBTUtil.addTag(stack, NBTUtil.getSoulboundTag());
    }

    default void unSoulbind(ItemStack stack) {
        NBTUtil.removeTag(stack, NBTUtil.getSoulboundTag());
    }

    default boolean isSoulbound(ItemStack stack) {
        return NBTUtil.hasTag(stack, NBTUtil.getSoulboundTag());
    }
}
