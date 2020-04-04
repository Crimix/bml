package com.black_dog20.bml.internal.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ArmorInventoryStorage implements Capability.IStorage<IArmorInventoryCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IArmorInventoryCapability> capability, IArmorInventoryCapability instance, Direction side) {
        return instance.writeToNbt();
    }

    @Override
    public void readNBT(Capability<IArmorInventoryCapability> capability, IArmorInventoryCapability instance, Direction side, INBT nbt) {
        if (!(nbt instanceof CompoundNBT))
            return;

        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        instance.readFromNbt(compoundNBT);
    }
}
