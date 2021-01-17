package com.black_dog20.bml.utils.dimension;

import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * Utility class for dimensions.
 *
 * @author black_dog20
 */
public class DimensionUtil {

    /**
     * Get the formatted name of a dimension.
     *
     * @param dimensionName the unformatted name of the dimension
     * @return the formatted name.
     */
    public static TextComponent getFormattedDimensionName(String dimensionName) {
        if (!dimensionName.contains(".") && !dimensionName.contains(":")) {
            String name = TextUtil.capitaliseFirstLetterFully(dimensionName.replaceAll("_", " "));
            return new StringTextComponent(name);
        } else {
            return new StringTextComponent(dimensionName);
        }
    }

    /**
     * Get the formatted name of a dimension.
     *
     * @param dimension the dimension.
     * @return the formatted name.
     */
    public static TextComponent getFormattedDimensionName(RegistryKey<World> dimension) {
        return getFormattedDimensionName(dimension.getRegistryName(), null);
    }

    /**
     * Get the formatted name of a dimension.
     * If it does not find a translation for the dimension uses the falback which could be the following
     * "fallbackModId.minecraft:overworld".
     *
     * @param dimensionName the resource location for the dimension.
     * @param fallbackModId the fall back modid for the translation.
     * @return the formatted name.
     */
    public static TextComponent getFormattedDimensionName(ResourceLocation dimensionName, String fallbackModId) {
        TextComponent name = new TranslationTextComponent(dimensionName.toString());
        if (name.getString().equals(dimensionName.toString())) {
            String alt = String.format("%s.%s", dimensionName.getNamespace(), dimensionName.getPath());
            name = new TranslationTextComponent(alt);
            if (name.getString().equals(alt) && TextUtil.isNotNullOrEmpty(fallbackModId)) {
                String fallback = String.format("%s.%s", fallbackModId, dimensionName.toString());
                name = new TranslationTextComponent(fallback);
                if (name.getString().equals(fallback)) {
                    name = getFormattedDimensionName(dimensionName.getPath());
                }
            } else {
                name = getFormattedDimensionName(dimensionName.getPath());
            }
        }
        return name;
    }

    /**
     * Gets the dimensions registry key.
     *
     * @param world The world.
     * @return the registry key for the dimension for that world.
     */
    public static RegistryKey<World> getDimension(World world) {
        return world.func_234923_W_();
    }

    /**
     * Gets the dimensions registry keys resource location.
     *
     * @param world The world.
     * @return the registry key resource location for the dimension for that world.
     */
    public static ResourceLocation getDimensionResourceLocation(World world) {
        return getDimension(world).func_240901_a_();
    }
}
