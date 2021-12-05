package com.black_dog20.bml.utils.leveling;

import com.black_dog20.bml.api.ILevelableItem;
import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

import static com.black_dog20.bml.internal.utils.InternalTranslations.Translations.*;

public class ItemLevelProperties {

    public static final String TAG_XP = "bml-item-xp";
    public static final String TAG_LEVEL = "bml-item-level";

    public static void addXp(Player player, ItemStack tool, int xp) {
        if (canLevel(tool)) {
            ILevelableItem levelableItem = (ILevelableItem) tool.getItem();
            int currentLevel = getCurrentLevel(tool);
            int currentXP = getCurrentXp(tool);

            if (currentLevel == levelableItem.getMaxLevel()) {
                return;
            }

            int nextXp = currentXP + xp;
            int nextLevel = currentLevel;


            int xpForLevelUp = getXpToNextLevel(tool, currentLevel + 1);
            if (nextXp >= xpForLevelUp) {
                nextXp -= xpForLevelUp;
                nextLevel++;
            }

            CompoundTag compoundNBT = tool.getOrCreateTag();
            compoundNBT.putInt(TAG_XP, nextXp);
            if (currentLevel != nextLevel) {
                boolean wasSoulbound = isLevelAbovePercentage(levelableItem.getSoulboundLevel(), currentLevel, tool);
                boolean isSoulbound = isLevelAbovePercentage(levelableItem.getSoulboundLevel(), nextLevel, tool);
                compoundNBT.putInt(TAG_LEVEL, nextLevel);
                if (!wasSoulbound && isSoulbound) {
                    player.sendMessage(SOULBOUND_ACHIEVED.get(ChatFormatting.AQUA, tool.getHoverName().getString().toLowerCase()), player.getUUID());
                }
            }
        }
    }

    private static int getXpToNextLevel(ItemStack tool, int level) {
        if (level <= 1) {
            return getBaseXp(tool);
        }
        return (int) ((float) getXpToNextLevel(tool, level - 1) * getLevelXpMultiplier(tool));
    }

    public static Component getXpToNextLevel(ItemStack tool) {
        if (!isLevelable(tool)) {
            return NOT_LEVELABLE.get();
        } else if (getCurrentLevel(tool) >= getMaxLevel(tool)) {
            return MAX_LEVEL.get();
        }

        int level = getCurrentLevel(tool) + 1;
        int xpToNextLevel = getXpToNextLevel(tool, level - 1);

        return PROGRESS.get(ChatFormatting.GRAY, getCurrentLevel(tool), getCurrentXp(tool), xpToNextLevel);
    }

    public static int getCurrentXp(ItemStack tool) {
        if (!isLevelable(tool))
            return 0;
        return NBTUtil.getInt(tool, TAG_XP);
    }

    public static int getCurrentLevel(ItemStack tool) {
        if (!isLevelable(tool))
            return 0;
        return NBTUtil.getInt(tool, TAG_LEVEL);
    }

    public static boolean isLevelAbovePercentage(double percentage, ItemStack tool) {
        if (!isLevelable(tool))
            return false;

        return isLevelAbovePercentage(percentage, getCurrentLevel(tool), tool);
    }

    public static boolean isLevelAbovePercentage(double percentage, int level, ItemStack tool) {
        return getLevelableItem(tool)
                .map(item -> level >= percentage * item.getMaxLevel())
                .orElse(false);
    }

    public static double calculateValue(double base, double wantedLevel, ItemStack tool) {
        if (!isLevelable(tool))
            return 0;
        ILevelableItem levelableItem = (ILevelableItem) tool.getItem();

        if (base < wantedLevel) {
            double gainPerLevel = (wantedLevel - base) / (double) levelableItem.getMaxLevel();
            return base + Math.abs(gainPerLevel * getCurrentLevel(tool));
        } else if (base > wantedLevel) {
            double lossPerLevel = (base - wantedLevel) / (double) levelableItem.getMaxLevel();
            return base - Math.abs(lossPerLevel * getCurrentLevel(tool));
        }
        return base;
    }

    private static boolean canLevel(ItemStack tool) {
        return getLevelableItem(tool)
                .map(item -> getCurrentLevel(tool) < item.getMaxLevel())
                .orElse(false);
    }

    private static int getBaseXp(ItemStack tool) {
        return getLevelableItem(tool)
                .map(ILevelableItem::getBaseXp)
                .orElse(500);
    }

    private static double getLevelXpMultiplier(ItemStack tool) {
        return getLevelableItem(tool)
                .map(ILevelableItem::getLevelXpMultiplier)
                .orElse(1.1);
    }

    private static boolean isLevelable(ItemStack tool) {
        return getLevelableItem(tool).isPresent();
    }

    public static int getMaxLevel(ItemStack tool) {
        return getLevelableItem(tool)
                .map(ILevelableItem::getMaxLevel)
                .orElse(0);
    }

    private static Optional<ILevelableItem> getLevelableItem(ItemStack tool) {
        return Optional.of(tool)
                .map(ItemStack::getItem)
                .filter(ILevelableItem.class::isInstance)
                .map(ILevelableItem.class::cast);
    }

    public static Component getLevelProgress(ItemStack stack) {
        return getLevelProgress(stack, ChatFormatting.GRAY);
    }

    public static Component getLevelProgress(ItemStack stack, ChatFormatting baseColor) {
        return TextComponentBuilder.of(LEVEL)
                .format(baseColor)
                .with(":")
                .format(baseColor)
                .space()
                .with(getXpToNextLevel(stack))
                .build();
    }

}
