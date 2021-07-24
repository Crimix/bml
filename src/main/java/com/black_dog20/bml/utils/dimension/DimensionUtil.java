package com.black_dog20.bml.utils.dimension;

import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

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
    public static BaseComponent getFormattedDimensionName(String dimensionName) {
        if (!dimensionName.contains(".") && !dimensionName.contains(":")) {
            String name = TextUtil.capitaliseFirstLetterFully(dimensionName.replaceAll("_", " "));
            return new TextComponent(name);
        } else {
            return new TextComponent(dimensionName);
        }
    }

    /**
     * Get the formatted name of a dimension.
     *
     * @param dimension the dimension.
     * @return the formatted name.
     */
    public static BaseComponent getFormattedDimensionName(ResourceKey<Level> dimension) {
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
    public static BaseComponent getFormattedDimensionName(ResourceLocation dimensionName, String fallbackModId) {
        BaseComponent name = new TranslatableComponent(dimensionName.toString());
        if (name.getString().equals(dimensionName.toString())) {
            String alt = String.format("%s.%s", dimensionName.getNamespace(), dimensionName.getPath());
            name = new TranslatableComponent(alt);
            if (name.getString().equals(alt) && TextUtil.isNotNullOrEmpty(fallbackModId)) {
                String fallback = String.format("%s.%s", fallbackModId, dimensionName.toString());
                name = new TranslatableComponent(fallback);
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
    public static ResourceKey<Level> getDimension(Level world) {
        return world.dimension();
    }

    /**
     * Gets the dimensions registry keys resource location.
     *
     * @param world The world.
     * @return the registry key resource location for the dimension for that world.
     */
    public static ResourceLocation getDimensionResourceLocation(Level world) {
        return getDimension(world).location();
    }
}
