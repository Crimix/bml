package com.black_dog20.bml.internal.capability;

import com.black_dog20.bml.capability.IBaseCapability;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IArmorInventoryCapability extends IBaseCapability<IArmorInventoryCapability> {

    ItemStack getStackInSlot(int slot);

    void setStackInSlot(int slot, ItemStack stack);

    void setSize(int size);

    int getSize();

    void copyArmor(NonNullList<ItemStack> armorInventory);
}
