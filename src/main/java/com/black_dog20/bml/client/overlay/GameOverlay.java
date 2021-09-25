package com.black_dog20.bml.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * General overlay class.
 * Please extend one of the subclasses.
 * Please register this using the {@link OverlayRegistry#register(GameOverlay)}
 */
@OnlyIn(Dist.CLIENT)
public abstract class GameOverlay {

    /**
     * Corresponds to {@link RenderGameOverlayEvent.Pre}.
     */
    public abstract static class Pre extends Overlay {

        /**
         * Renders the overlay.
         * This is called only when {@link Overlay#doRender(RenderGameOverlayEvent.ElementType)} returns true.
         *
         * @param scaledwidth  the scaled width of the window.
         * @param scaledheight the scaled height of the window.
         */
        public abstract void onRender(PoseStack poseStack, int scaledwidth, int scaledheight);

        @SubscribeEvent
        public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
            if (!doRender(event.getType()))
                return;
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            onRender(event.getMatrixStack(), width, height);
            if (event.isCancelable())
                event.setCanceled(doesCancelEvent());
        }
    }

    /**
     * Corresponds to {@link RenderGameOverlayEvent.Post}.
     */
    public abstract static class Post extends Overlay {

        /**
         * Renders the overlay.
         * This is called only when {@link Overlay#doRender(RenderGameOverlayEvent.ElementType)} returns true.
         *
         * @param scaledwidth  the scaled width of the window.
         * @param scaledheight the scaled height of the window.
         */
        public abstract void onRender(PoseStack poseStack, int scaledwidth, int scaledheight);

        @SubscribeEvent
        public void onOverlayRender(RenderGameOverlayEvent.Post event) {
            if (!doRender(event.getType()))
                return;
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            onRender(event.getMatrixStack(), width, height);
            if (event.isCancelable())
                event.setCanceled(doesCancelEvent());
        }
    }

    /**
     * Corresponds to {@link RenderGameOverlayEvent.PreLayer}.
     */
    public abstract static class PreLayer extends LayerOverlay {

        /**
         * Renders the overlay.
         * This is called only when {@link LayerOverlay#doRender(IIngameOverlay)} returns true.
         *
         * @param scaledwidth  the scaled width of the window.
         * @param scaledheight the scaled height of the window.
         */
        public abstract void onRender(PoseStack poseStack, int scaledwidth, int scaledheight);

        @SubscribeEvent
        public void onOverlayRender(RenderGameOverlayEvent.PreLayer event) {
            if (!doRender(event.getOverlay()))
                return;
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            onRender(event.getMatrixStack(), width, height);
            if (event.isCancelable())
                event.setCanceled(doesCancelEvent());
        }
    }

    /**
     * Corresponds to {@link RenderGameOverlayEvent.PostLayer}.
     */
    public abstract static class PostLayer extends LayerOverlay {

        /**
         * Renders the overlay.
         * This is called only when {@link LayerOverlay#doRender(IIngameOverlay)} returns true.
         *
         * @param scaledwidth  the scaled width of the window.
         * @param scaledheight the scaled height of the window.
         */
        public abstract void onRender(PoseStack poseStack, int scaledwidth, int scaledheight);

        @SubscribeEvent
        public void onOverlayRender(RenderGameOverlayEvent.PostLayer event) {
            if (!doRender(event.getOverlay()))
                return;
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            onRender(event.getMatrixStack(), width, height);
            if (event.isCancelable())
                event.setCanceled(doesCancelEvent());
        }
    }

    private abstract static class Overlay extends GameOverlay {
        /**
         * Based on the supplied {@link RenderGameOverlayEvent.ElementType} should the overlay be rendered.
         *
         * @param elementType the RenderGameOverlayEvent element type
         * @return true if the overlay should be rendered.
         */
        public abstract boolean doRender(RenderGameOverlayEvent.ElementType elementType);

    }

    private abstract static class LayerOverlay extends GameOverlay {
        /**
         * Based on the supplied {@link RenderGameOverlayEvent.ElementType} should the overlay be rendered.
         *
         * @param iIngameOverlay the {@link IIngameOverlay}
         * @return true if the overlay should be rendered.
         */
        public abstract boolean doRender(IIngameOverlay iIngameOverlay);
    }

    /**
     * Does the overlay cancel the event.
     * This only has an effect when the event is cancelable.
     *
     * @return true if the event should be cancelled after render.
     */
    public abstract boolean doesCancelEvent();


}
