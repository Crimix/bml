package com.black_dog20.bml.api;

import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import net.minecraft.world.item.ItemStack;

public interface ILevelableItem {

    default int getMaxLevel() {
        return 10;
    }

    default double getSoulboundLevel() {
        return 0.9;
    }

    default boolean isSoulboundByLevel(ItemStack tool) {
        return ItemLevelProperties.isLevelAbovePercentage(getSoulboundLevel(), tool);
    }

    default int getBaseXp() {
        return 500;
    }

    default double getLevelXpMultiplier() {
        return 1.1;
    }
}
