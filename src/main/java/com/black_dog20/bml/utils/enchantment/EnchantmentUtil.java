package com.black_dog20.bml.utils.enchantment;

import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.stream.StreamUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utility class for enchantments.
 *
 * @author black_dog20
 */
public class EnchantmentUtil {

    public enum Level {
        MIN,
        MAX,
        RANDOM
    }

    /**
     * Adds a random enchantment to a itemstack.
     *
     * @param stack         the itmstack.
     * @param allowTreasure if treasure enchantments are included in the pool.
     * @param level         what level it should be.
     * @return the stack enchanted with the random enchantment.
     */
    public static ItemStack addRandomEnchantment(ItemStack stack, boolean allowTreasure, Level level) {
        Optional<EnchantmentData> enchantmentData = getRandomEnchantmentData(stack, allowTreasure, level);

        boolean flag = stack.getItem() == Items.BOOK;
        if (flag) {
            stack = new ItemStack(Items.ENCHANTED_BOOK);
        }

        if (!enchantmentData.isPresent()) {
            return stack;
        }

        if (flag) {
            EnchantedBookItem.addEnchantment(stack, enchantmentData.get());
        } else {
            stack.addEnchantment(enchantmentData.get().enchantment, enchantmentData.get().enchantmentLevel);
        }
        return stack;
    }

    private static Optional<EnchantmentData> getRandomEnchantmentData(ItemStack stack, boolean allowTreasure, Level level) {

        return ForgeRegistries.ENCHANTMENTS.getValues().stream()
                .filter(getEnchantmentFilter(stack, allowTreasure))
                .map(createEnchantmentData(level))
                .collect(StreamUtils.toShuffledStream())
                .limit(1)
                .findFirst();
    }

    private static Function<Enchantment, EnchantmentData> createEnchantmentData(Level level) {
        switch (level) {
            case MIN:
                return e -> new EnchantmentData(e, e.getMinLevel());
            case MAX:
                return e -> new EnchantmentData(e, e.getMaxLevel());
            case RANDOM:
                return e -> new EnchantmentData(e, MathUtil.random(e.getMinLevel(), e.getMaxLevel()));
            default:
                throw new UnsupportedOperationException("Got level which is not supported, level = " + level.name());
        }
    }

    private static Predicate<Enchantment> getEnchantmentFilter(ItemStack stack, boolean allowTreasure) {
        boolean flag = stack.getItem() == Items.BOOK;
        return enchantment -> (!enchantment.isTreasureEnchantment() || allowTreasure) && (enchantment.canApplyAtEnchantingTable(stack) || (flag && enchantment.isAllowedOnBooks()));
    }
}
