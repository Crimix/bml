package com.black_dog20.bml.utils.item;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Utility class for items.
 *
 * @author black_dog20
 */
public class ItemUtil {

    /**
     * Compares two list of itemstacks to see if they are equal.
     *
     * @param list1 the first list.
     * @param list2 the second list.
     * @return true if the two lists are equal.
     */
    public static boolean areItemStacksListEqual(List<ItemStack> list1, List<ItemStack> list2) {
        boolean equal = list1.stream().allMatch(i -> areItemStackInList(i, list2));

        return list1.size() == list2.size() && equal;
    }

    private static boolean areItemStackInList(ItemStack stack, List<ItemStack> list) {
        return list.stream().anyMatch(i -> ItemStack.areItemStacksEqual(stack, i));
    }
}
