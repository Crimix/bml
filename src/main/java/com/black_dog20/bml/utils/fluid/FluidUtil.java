package com.black_dog20.bml.utils.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import javax.annotation.Nonnull;

/**
 * Utility class for fluids.
 *
 * @author black_dog20
 */
public class FluidUtil {

    /**
     * Fills a fluid handler with a fluid, can be in simulation mode.
     *
     * @param fluidHandler the fluid handler to fill.
     * @param stack        the fluid stack.
     * @param simulate     if it should simulate the filling.
     * @return the resulting fluid stack, can be empty.
     */
    public static FluidStack fill(IFluidHandler fluidHandler, @Nonnull FluidStack stack, boolean simulate) {
        FluidStack copy = stack.copy();
        int newAmount = copy.getAmount() - fluidHandler.fill(stack, simulate ? FluidAction.SIMULATE : FluidAction.EXECUTE);
        if (newAmount == 0) {
            return FluidStack.EMPTY;
        } else {
            copy.setAmount(newAmount);
            return copy;
        }
    }
}
