package com.black_dog20.bml.client.overlay.configure;

import com.black_dog20.bml.utils.color.Color4i;
import com.black_dog20.bml.utils.math.MathUtil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OverlayConfigWidget extends Widget {

    private IConfigurableOverlay overlay;
    private boolean isActive;
    private boolean isDragging = false;

    public OverlayConfigWidget(IConfigurableOverlay overlay) {
        super(overlay.getPosX(), overlay.getPosY(), overlay.getWidth(), overlay.getHeight(), overlay.getName());
        this.overlay = overlay;
        this.isActive = overlay.getActive();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft minecraft = Minecraft.getInstance();
        Color4i color4f = getColor();
        int color1 = color4f.getValue();
        int color2 = color4f.getValue(100);
        hLine(this.x - 3, this.x + 1 + this.width + 1, this.y - 3, color1);
        vLine(this.x - 3, this.y + this.height + 1, this.y - 3, color1);
        fill(this.x - 2, this.y - 2, this.x + 2 + this.width, this.y + 1 + this.height, color2);
        vLine(this.x + this.width + 2, this.y + this.height + 1, this.y - 3, color1);
        hLine(this.x - 3, this.x + 1 + this.width + 1, this.y + this.height + 1, color1);
        this.drawString(minecraft.fontRenderer, this.getMessage(), this.x, this.y, 16777215);
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
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        int windowWidth = mainWindow.getScaledWidth();
        int windowHeight = mainWindow.getScaledHeight();

        this.x = MathUtil.clamp(Math.round((float) p_onDrag_1_ - ((float) this.width / 2)), 0, windowWidth - this.width);
        this.y = MathUtil.clamp(Math.round((float) p_onDrag_3_ - ((float) this.height / 2)), 0, windowHeight - this.height);
    }

    @Override
    public void onRelease(double p_onClick_1_, double p_onClick_3_) {
        if (isDragging) {
            overlay.setPosition(x, y);
            isDragging = false;
        } else if (overlay.canBeInactive()) {
            this.isActive = !overlay.getActive();
            overlay.setInactive(isActive);
        }
    }

    private Color4i getColor() {
        if (overlay.canBeInactive()) {
            return isActive ? overlay.getActiveColor() : overlay.getInactiveColor();
        } else {
            return overlay.getActiveColor();
        }
    }
}
