package com.black_dog20.bml.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

public class DrawUtil {

    /**
     * Draws textured rectangles of sizes other than 256x256
     *
     * @param x             The x value of the top-left corner point on the screen where drawing to starts
     * @param y             The y value of the top-left corner point on the screen where drawing to starts
     * @param u             The u (x) value of top-left corner point of the texture to start drawing from
     * @param v             The v (y) value of top-left corner point of the texture to start drawing from
     * @param width         The width of the rectangle to draw on screen
     * @param height        The height of the rectangle to draw on screen
     * @param textureWidth  The width of the whole texture
     * @param textureHeight The height of the whole texture
     */
    public static void drawNonStandardTexturedRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        float f = 1F / (float) textureWidth;
        float f1 = 1F / (float) textureHeight;
        BufferBuilder wr = Tesselator.getInstance().getBuilder();
        wr.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        wr.vertex(x, y + height, 0).uv(u * f, (v + height) * f1).endVertex();
        wr.vertex(x + width, y + height, 0).uv((u + width) * f, (v + height) * f1).endVertex();
        wr.vertex(x + width, y, 0).uv((u + width) * f, v * f1).endVertex();
        wr.vertex(x, y, 0).uv(u * f, v * f1).endVertex();
        Tesselator.getInstance().end();
    }
}
