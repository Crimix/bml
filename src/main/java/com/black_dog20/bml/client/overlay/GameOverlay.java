package com.black_dog20.bml.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * General overlay class.
 * Please extend one of the subclasses.
 * Please register this using the {@link OverlayRegistry#register(GameOverlay)}
 */
@OnlyIn(Dist.CLIENT)
public abstract class GameOverlay {

    /**
     * Corresponds to {@link RenderGuiOverlayEvent.Pre}.
     */
    public abstract static class Pre extends GameOverlay {

        /**
         * Renders the overlay.
         * This is called only when {@link GameOverlay#doRender(NamedGuiOverlay)} returns true.
         *
         * @param scaledwidth  the scaled width of the window.
         * @param scaledheight the scaled height of the window.
         */
        public abstract void onRender(PoseStack poseStack, int scaledwidth, int scaledheight);

        @SubscribeEvent
        public void onOverlayRender(RenderGuiOverlayEvent.Pre event) {
            if (!doRender(event.getOverlay()))
                return;
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            onRender(event.getPoseStack(), width, height);
            if (event.isCancelable())
                event.setCanceled(doesCancelEvent());
        }
    }

    /**
     * Corresponds to {@link RenderGuiOverlayEvent.Post}.
     */
    public abstract static class Post extends GameOverlay {

        /**
         * Renders the overlay.
         * This is called only when {@link GameOverlay#doRender(NamedGuiOverlay)} returns true.
         *
         * @param scaledwidth  the scaled width of the window.
         * @param scaledheight the scaled height of the window.
         */
        public abstract void onRender(PoseStack poseStack, int scaledwidth, int scaledheight);

        @SubscribeEvent
        public void onOverlayRender(RenderGuiOverlayEvent.Post event) {
            if (!doRender(event.getOverlay()))
                return;
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            onRender(event.getPoseStack(), width, height);
            if (event.isCancelable())
                event.setCanceled(doesCancelEvent());
        }
    }

    /**
     * Based on the supplied {@link NamedGuiOverlay} should the overlay be rendered.
     *
     * @param overlay the {@link NamedGuiOverlay}
     * @return true if the overlay should be rendered.
     */
    public abstract boolean doRender(NamedGuiOverlay overlay);

    /**
     * Does the overlay cancel the event.
     * This only has an effect when the event is cancelable.
     *
     * @return true if the event should be cancelled after render.
     */
    public abstract boolean doesCancelEvent();


}
