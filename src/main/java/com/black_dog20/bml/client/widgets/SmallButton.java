package com.black_dog20.bml.client.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;

public class SmallButton extends Button {

    public SmallButton(int x, int y, int widthIn, int heightIn, Component buttonText, OnPress callback, Button.CreateNarration createNarration) {
        super(x, y, widthIn, heightIn, buttonText, callback, createNarration);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            this.isHovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + width && mouseY < getY() + height;
            Minecraft minecraft = Minecraft.getInstance();
            Font fontrenderer = minecraft.font;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            int i = this.getYImage(isHoveredOrFocused());
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();

            int halfwidth1 = this.width / 2;
            int halfwidth2 = this.width - halfwidth1;
            int halfheight1 = this.height / 2;
            int halfheight2 = this.height - halfheight1;
            blit(poseStack, getX(), getY(), 0,
                    46 + i * 20, halfwidth1, halfheight1);
            blit(poseStack, getX() + halfwidth1, getY(), 200 - halfwidth2,
                    46 + i * 20, halfwidth2, halfheight1);

            blit(poseStack, getX(), getY() + halfheight1,
                    0, 46 + i * 20 + 20 - halfheight2, halfwidth1, halfheight2);
            blit(poseStack, getX() + halfwidth1, getY() + halfheight1,
                    200 - halfwidth2, 46 + i * 20 + 20 - halfheight2, halfwidth2, halfheight2);

            int textColor = getColor();

            drawCenteredString(poseStack, fontrenderer, getMessage(), getX() + halfwidth2, getY() + (this.height - 8) / 2, textColor);
        }
    }

    private int getColor() {
        int textColor = 14737632;

        if (packedFGColor != 0) {
            textColor = packedFGColor;
        } else if (!this.visible) {
            textColor = 10526880;
        } else if (this.isHovered) {
            textColor = 16777120;
        }
        return textColor;
    }
}