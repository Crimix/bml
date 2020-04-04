package com.black_dog20.bml.capability;

import net.minecraft.nbt.CompoundNBT;

/**
 * Base capability interface.
 * Contains methods to implement, which helps in making capabilities.
 *
 * @author black_dog20
 */
public interface IBaseCapability<T> {

    /**
     * Copy this capability's values to the input capability of the same type.
     *
     * @param other the other capability.
     */
    void copyTo(T other);

    /**
     * Write this capability's values to a NBT compound.
     *
     * @return the NBT compound.
     */
    CompoundNBT writeToNbt();

    /**
     * Read the values from the NBT compound and set them on this capability.
     *
     * @param compoundNBT the NBT compound.
     */
    void readFromNbt(CompoundNBT compoundNBT);

}
