package com.black_dog20.bml.utils.color;

import net.minecraft.client.renderer.texture.NativeImage;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Java Color Thief
 * by Sven Woltmann, Fonpit AG
 * <p>
 * http://www.androidpit.com
 * http://www.androidpit.de
 * <p>
 * License
 * -------
 * Creative Commons Attribution 2.5 License:
 * http://creativecommons.org/licenses/by/2.5/
 * <p>
 * Thanks
 * ------
 * Lokesh Dhakar - for the original Color Thief JavaScript version
 * available at http://lokeshdhakar.com/projects/color-thief/
 * <p>
 * <p>
 * This version has been taken from https://github.com/mezz/JustEnoughItems/blob/1.14/src/main/java/mezz/jei/color/ColorThief.java
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014-2015 mezz
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class ColorThief {
    /**
     * Use the median cut algorithm to cluster similar colors.
     *
     * @param sourceImage the source image
     * @param colorCount  the size of the palette; the number of colors returned
     * @param quality     0 is the highest quality settings. 10 is the default. There is
     *                    a trade-off between quality and speed. The bigger the number,
     *                    the faster the palette generation but the greater the
     *                    likelihood that colors will be missed.
     * @param ignoreWhite if <code>true</code>, white pixels are ignored
     * @return the palette as array of RGB arrays
     */
    @Nullable
    public static int[][] getPalette(NativeImage sourceImage, int colorCount, int quality, boolean ignoreWhite) {
        MMCQ.CMap cmap = getColorMap(sourceImage, colorCount, quality, ignoreWhite);
        if (cmap == null) {
            return null;
        }
        return cmap.palette();
    }

    /**
     * Use the median cut algorithm to cluster similar colors.
     *
     * @param sourceImage the source image
     * @param colorCount  the size of the palette; the number of colors returned
     * @param quality     0 is the highest quality settings. 10 is the default. There is
     *                    a trade-off between quality and speed. The bigger the number,
     *                    the faster the palette generation but the greater the
     *                    likelihood that colors will be missed.
     * @param ignoreWhite if <code>true</code>, white pixels are ignored
     * @return the color map
     */
    @Nullable
    public static MMCQ.CMap getColorMap(NativeImage sourceImage, int colorCount, int quality, boolean ignoreWhite) {
        if (sourceImage.getFormat() == NativeImage.PixelFormat.RGBA) {
            int[][] pixelArray = getPixels(sourceImage, quality, ignoreWhite);
            // Send array to quantize function which clusters values using median
            // cut algorithm
            return MMCQ.quantize(pixelArray, colorCount);
        }
        return null;
    }

    /**
     * Gets the image's pixels via BufferedImage.getRaster().getDataBuffer().
     * Fast, but doesn't work for all color models.
     *
     * @param sourceImage the source image
     * @param quality     1 is the highest quality settings. 10 is the default. There is
     *                    a trade-off between quality and speed. The bigger the number,
     *                    the faster the palette generation but the greater the
     *                    likelihood that colors will be missed.
     * @param ignoreWhite if <code>true</code>, white pixels are ignored
     * @return an array of pixels (each an RGB int array)
     */
    private static int[][] getPixels(NativeImage sourceImage, int quality, boolean ignoreWhite) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int pixelCount = width * height;

        // Store the RGB values in an array format suitable for quantize function

        // numRegardedPixels must be rounded up to avoid an
        // ArrayIndexOutOfBoundsException if all pixels are good.
        int numRegardedPixels = (pixelCount + quality - 1) / quality;

        int numUsedPixels = 0;
        int[][] pixelArray = new int[numRegardedPixels][];

        int i = 0;
        while (i < pixelCount) {
            int x = i % width;
            int y = i / width;
            int rgba = sourceImage.getPixelRGBA(x, y);
            int a = rgba >> 24 & 255;
            int b = rgba >> 16 & 255;
            int g = rgba >> 8 & 255;
            int r = rgba & 255;
            // If pixel is mostly opaque and not white
            if (a >= 125 && !(ignoreWhite && r > 250 && g > 250 && b > 250)) {
                pixelArray[numUsedPixels] = new int[]{r, g, b};
                numUsedPixels++;
                i += quality;
            } else {
                i++;
            }
        }
        // trim the array
        return Arrays.copyOfRange(pixelArray, 0, numUsedPixels);
    }
}
