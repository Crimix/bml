package com.black_dog20.bml.client.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class SmallButton extends Button {
    public SmallButton(int x, int y, int widthIn, int heightIn, Component buttonText, OnPress callback) {
        super(x, y, widthIn, heightIn, buttonText, callback);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            Minecraft minecraft = Minecraft.getInstance();
            Font fontrenderer = minecraft.font;
            minecraft.getTextureManager().bindForSetup(WIDGETS_LOCATION);

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            isHovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

            int i = getYImage(isHovered);

            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            RenderSystem.blendFunc(770, 771);

            int halfwidth1 = this.width / 2;
            int halfwidth2 = this.width - halfwidth1;
            int halfheight1 = this.height / 2;
            int halfheight2 = this.height - halfheight1;
            blit(matrixStack, x, y, 0,
                    46 + i * 20, halfwidth1, halfheight1);
            blit(matrixStack, x + halfwidth1, y, 200 - halfwidth2,
                    46 + i * 20, halfwidth2, halfheight1);

            blit(matrixStack, x, y + halfheight1,
                    0, 46 + i * 20 + 20 - halfheight2, halfwidth1, halfheight2);
            blit(matrixStack, x + halfwidth1, y + halfheight1,
                    200 - halfwidth2, 46 + i * 20 + 20 - halfheight2, halfwidth2, halfheight2);

            int textColor = 14737632;

            if (packedFGColor != 0) {
                textColor = packedFGColor;
            } else if (!this.visible) {
                textColor = 10526880;
            } else if (this.isHovered) {
                textColor = 16777120;
            }

            drawCenteredString(matrixStack, fontrenderer, getMessage(), x + halfwidth2, y + (this.height - 8) / 2, textColor);
        }
    }
}