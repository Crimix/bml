package com.black_dog20.bml.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public abstract class Overlay {

    public abstract static class Pre extends Overlay {
        public abstract void onRender(MatrixStack matrixStack, int scaledwidth, int scaledheight);

        @SubscribeEvent
        public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
            if (skipRender(event.getType()))
                return;
            int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
            int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
            onRender(event.getMatrixStack(), width, height);
            if (event.isCancelable())
                event.setCanceled(doesCancelEvent());
        }
    }

    public abstract static class Post extends Overlay {
        public abstract void onRender(MatrixStack matrixStack, int scaledwidth, int scaledheight);

        @SubscribeEvent
        public void onOverlayRender(RenderGameOverlayEvent.Post event) {
            if (skipRender(event.getType()))
                return;
            int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
            int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
            onRender(event.getMatrixStack(), width, height);
            if (event.isCancelable())
                event.setCanceled(doesCancelEvent());
        }
    }

    public abstract boolean skipRender(RenderGameOverlayEvent.ElementType elementType);

    public abstract boolean doesCancelEvent();
}
