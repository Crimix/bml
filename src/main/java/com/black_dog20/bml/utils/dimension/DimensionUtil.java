package com.black_dog20.bml.utils.dimension;

import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.world.dimension.DimensionType;

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
     * @param dimensionType the dimension type of the dimension.
     * @return the formatted name.
     */
    public static String getFormattedDimensionName(DimensionType dimensionType) {
        return getFormattedDimensionName(dimensionType.getRegistryName().getPath());
    }
}
