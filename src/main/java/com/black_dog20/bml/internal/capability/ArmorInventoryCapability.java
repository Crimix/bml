package com.black_dog20.bml.internal.capability;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class ArmorInventoryCapability implements IArmorInventoryCapability {

    @CapabilityInject(IArmorInventoryCapability.class)
    public static final Capability<IArmorInventoryCapability> CAP = null;

    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < 0 || slot >= inventory.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + inventory.size() + ")");
        return inventory.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (slot < 0 || slot >= inventory.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + inventory.size() + ")");
        inventory.set(slot, stack);
    }

    @Override
    public void setSize(int size) {
        inventory = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public int getSize() {
        return inventory.size();
    }

    @Override
    public void copyArmor(NonNullList<ItemStack> armorInventory) {
        int size = armorInventory.size();
        this.inventory = NonNullList.withSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            inventory.set(i, armorInventory.get(i));
        }
    }

    @Override
    public void copyTo(IArmorInventoryCapability other) {
        other.copyArmor(inventory);
    }

    public static final class Factory implements Callable<ArmorInventoryCapability> {
        @Override
        public ArmorInventoryCapability call() {
            return new ArmorInventoryCapability();
        }
    }

    @Override
    public CompoundTag writeToNbt() {
        int size = getSize();
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < size; i++) {
            if (!getStackInSlot(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                getStackInSlot(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", size);
        return nbt;
    }

    @Override
    public void readFromNbt(CompoundTag compoundNBT) {
        setSize(compoundNBT.contains("Size", Constants.NBT.TAG_INT) ? compoundNBT.getInt("Size") : getSize());
        int size = getSize();
        ListTag tagList = compoundNBT.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < size) {
                setStackInSlot(slot, ItemStack.of(itemTags));
            }
        }
    }

    public static ICapabilityProvider createProvider() {

        return new Provider();
    }

    public static class Provider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

        final LazyOptional<IArmorInventoryCapability> optional;
        final IArmorInventoryCapability handler;

        Provider() {

            this.handler = new ArmorInventoryCapability();
            this.optional = LazyOptional.of(() -> handler);
        }

        @SuppressWarnings("ConstantConditions")
        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nullable Capability<T> capability, Direction facing) {

            return CAP.orEmpty(capability, optional);
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public CompoundTag serializeNBT() {
            return handler.writeToNbt();
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void deserializeNBT(CompoundTag nbt) {
            handler.readFromNbt(nbt);
        }
    }
}
