package com.black_dog20.bml.utils.color;

import com.black_dog20.bml.utils.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The following methods has been taken from https://github.com/mezz/JustEnoughItems/blob/1.14/src/main/java/mezz/jei/color/ColorGetter.java
 * getNativeImage(TextureAtlasSprite textureAtlasSprite)
 * getColors(TextureAtlasSprite textureAtlasSprite, int renderColor, int colorCount)
 * <p>
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
public final class ColorUtil {

    /**
     * Gets a average color from a fluid.
     *
     * @param fluid the fluid.
     * @return the color.
     */
    public static int getColor(Fluid fluid) {
        if (fluid == Fluids.WATER) {
            return 0x0094FF;
        }
        TextureAtlasSprite[] sprites = ForgeHooksClient.getFluidSprites(Minecraft.getInstance().world, BlockPos.ZERO, fluid.getDefaultState());
        if (sprites.length > 0) {
            return getColor(sprites[0]);
        }
        return 0xFFFFFF;
    }

    /**
     * Gets a average color from a texture sprite.
     *
     * @param sprite the sprite.
     * @return the color.
     */
    public static int getColor(TextureAtlasSprite sprite) {
        List<Integer> colors = getColors(sprite, 0xFFFFFF, 1);
        return colors.isEmpty() ? 0xFFFFFF : colors.get(0);
    }

    private static List<Integer> getColors(TextureAtlasSprite textureAtlasSprite, int renderColor, int colorCount) {
        NativeImage bufferedImage = getNativeImage(textureAtlasSprite);
        if (bufferedImage == null) {
            return Collections.emptyList();
        } else {
            List<Integer> colors = new ArrayList(colorCount);
            int[][] palette = ColorThief.getPalette(bufferedImage, colorCount, 2, false);
            if (palette != null) {
                int[][] var6 = palette;
                int var7 = palette.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    int[] colorInt = var6[var8];
                    int red = (int) ((float) (colorInt[0] - 1) * (float) (renderColor >> 16 & 255) / 255.0F);
                    int green = (int) ((float) (colorInt[1] - 1) * (float) (renderColor >> 8 & 255) / 255.0F);
                    int blue = (int) ((float) (colorInt[2] - 1) * (float) (renderColor & 255) / 255.0F);
                    red = MathUtil.clamp(red, 0, 255);
                    green = MathUtil.clamp(green, 0, 255);
                    blue = MathUtil.clamp(blue, 0, 255);
                    int color = -16777216 | (red & 255) << 16 | (green & 255) << 8 | blue & 255;
                    colors.add(color);
                }
            }

            return colors;
        }
    }

    @Nullable
    private static NativeImage getNativeImage(TextureAtlasSprite textureAtlasSprite) {
        int iconWidth = textureAtlasSprite.getWidth();
        int iconHeight = textureAtlasSprite.getHeight();
        int frameCount = textureAtlasSprite.getFrameCount();
        if (iconWidth > 0 && iconHeight > 0 && frameCount > 0) {
            NativeImage[] frames = textureAtlasSprite.frames;
            return frames[0];
        } else {
            return null;
        }
    }
}
