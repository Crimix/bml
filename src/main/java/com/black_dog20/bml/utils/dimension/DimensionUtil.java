package com.black_dog20.bml.utils.dimension;

import com.black_dog20.bml.utils.text.TextUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
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
    public static String getFormattedDimensionName(String dimensionName) {
        if (!dimensionName.contains(".")) {
            return TextUtil.capitaliseFirstLetterFully(dimensionName.replaceAll("_", " "));
        } else {
            return dimensionName;
        }
    }

    /**
     * Get the formatted name of a dimension.
     *
     * @param dimension the dimension.
     * @return the formatted name.
     */
    public static String getFormattedDimensionName(RegistryKey<World> dimension) {
        return getFormattedDimensionName(TranslationUtil.translateResourceLocation(dimension.getRegistryName(), ResourceLocation::getPath));
    }
}
