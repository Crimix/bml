package com.black_dog20.bml.internal.client.screen;

import com.black_dog20.bml.client.overlay.configure.IConfigurableOverlay;
import com.black_dog20.bml.utils.color.Color4i;
import com.black_dog20.bml.utils.math.MathUtil;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class OverlayConfigWidget extends AbstractWidget {

    private IConfigurableOverlay overlay;
    private boolean isActive;
    private boolean isDragging = false;

    public OverlayConfigWidget(IConfigurableOverlay overlay) {
        super(overlay.getPosX(), overlay.getPosY(), overlay.getWidth(), overlay.getHeight(), TextComponent.EMPTY);
        this.overlay = overlay;
        this.isActive = overlay.getSate();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void renderButton(PoseStack matrixStack, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft minecraft = Minecraft.getInstance();
        Color4i color4f = getColor();
        int color1 = color4f.getValue();
        int color2 = color4f.getValue(100);
        hLine(matrixStack, this.x - 3, this.x + 1 + this.width + 1, this.y - 3, color1);
        vLine(matrixStack, this.x - 3, this.y + this.height + 1, this.y - 3, color1);
        fill(matrixStack, this.x - 2, this.y - 2, this.x + 2 + this.width, this.y + 1 + this.height, color2);
        vLine(matrixStack, this.x + this.width + 2, this.y + this.height + 1, this.y - 3, color1);
        hLine(matrixStack, this.x - 3, this.x + 1 + this.width + 1, this.y + this.height + 1, color1);
        overlay.getMessage()
                .ifPresent(msg -> this.drawString(matrixStack, minecraft.font, msg, this.x, this.y, 16777215));
    }

    @SubscribeEvent
    public void onMouseReleaseEvent(GuiScreenEvent.MouseReleasedEvent.Post event) {
        if (isDragging) {
            overlay.setPosition(x, y);
            isDragging = false;
        }
    }

    @Override
    protected void onDrag(double p_onDrag_1_, double p_onDrag_3_, double p_onDrag_5_, double p_onDrag_7_) {
        this.isDragging = true;
        Window mainWindow = Minecraft.getInstance().getWindow();
        int windowWidth = mainWindow.getGuiScaledWidth();
        int windowHeight = mainWindow.getGuiScaledHeight();

        this.x = MathUtil.clamp(Math.round((float) p_onDrag_1_ - ((float) this.width / 2)), 0, windowWidth - this.width);
        this.y = MathUtil.clamp(Math.round((float) p_onDrag_3_ - ((float) this.height / 2)), 0, windowHeight - this.height);
    }

    @Override
    public void onRelease(double p_onClick_1_, double p_onClick_3_) {
        if (isDragging) {
            overlay.setPosition(x, y);
            isDragging = false;
        } else if (overlay.isStateChangeable()) {
            this.isActive = !overlay.getSate();
            overlay.setState(isActive);
        }
    }

    private Color4i getColor() {
        if (overlay.isStateChangeable()) {
            return isActive ? overlay.getActiveColor() : overlay.getInactiveColor();
        } else {
            return overlay.getActiveColor();
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {

    }
}
