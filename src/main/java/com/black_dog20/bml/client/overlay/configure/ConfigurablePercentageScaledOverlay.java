package com.black_dog20.bml.client.overlay.configure;

import com.black_dog20.bml.client.overlay.Overlay;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

/**
 * Abstract implementation of a scaled percentage based overlay.
 * Please extend one of the subclasses.
 */
@OnlyIn(Dist.CLIENT)
public abstract class ConfigurablePercentageScaledOverlay extends Overlay implements IConfigurableOverlay {

    /**
     * Corresponds to {@link RenderGameOverlayEvent.Pre}.
     */
    public abstract static class Pre extends Overlay.Pre implements IConfigurableOverlay {

        /**
         * Do not override this.
         * Please use {@link ConfigurablePercentageScaledOverlay.Pre#setPercentagePosition}.
         *
         * @param x the new x position.
         * @param y the new x position.
         */
        @Override
        public void setPosition(int x, int y) {
            int resX = Math.round(x / (((float) Minecraft.getInstance().getWindow().getGuiScaledWidth()) / 100));
            int resY = Math.round(y / (((float) Minecraft.getInstance().getWindow().getGuiScaledHeight()) / 100));

            setPercentagePosition(resX, resY);
        }

        /**
         * Changes the position of the overlay.
         * This should be saved somewhere.
         *
         * @param percentageX the new x position in percentage based on the scaled width.
         * @param percentageY the new y position in percentage based on the scaled height.
         */
        public abstract void setPercentagePosition(int percentageX, int percentageY);

        /**
         * This should be read from a config or something.
         *
         * @return the x position in percentage for the leftmost top of the overlay.
         */
        public abstract int getPercentagePosX();

        /**
         * This should be read from a config or something.
         *
         * @return the y position in percentage for the leftmost top of the overlay.
         */
        public abstract int getPercentagePosY();

        /**
         * Do not override this.
         *
         * @return the x position on the leftmost top of the overlay.
         */
        @Override
        public int getPosX() {
            return Math.round((((float) Minecraft.getInstance().getWindow().getGuiScaledWidth() / 100) * getPercentagePosX()));
        }

        /**
         * Do not override this.
         *
         * @return the y position on the leftmost top of the overlay.
         */
        @Override
        public int getPosY() {
            return Math.round((((float) Minecraft.getInstance().getWindow().getGuiScaledHeight() / 100) * getPercentagePosY()));
        }
    }

    /**
     * Corresponds to {@link RenderGameOverlayEvent.Post}.
     */
    public abstract static class Post extends Overlay.Post implements IConfigurableOverlay {

        /**
         * Do not override this.
         * Please use {@link ConfigurablePercentageScaledOverlay.Post#setPercentagePosition}.
         *
         * @param x the new x position.
         * @param y the new x position.
         */
        @Override
        public void setPosition(int x, int y) {
            int resX = Math.round(x / (((float) Minecraft.getInstance().getWindow().getGuiScaledWidth()) / 100));
            int resY = Math.round(y / (((float) Minecraft.getInstance().getWindow().getGuiScaledHeight()) / 100));

            setPercentagePosition(resX, resY);
        }

        /**
         * Changes the position of the overlay.
         * This should be saved somewhere.
         *
         * @param percentageX the new x position in percentage based on the scaled width.
         * @param percentageY the new y position in percentage based on the scaled height.
         */
        public abstract void setPercentagePosition(int percentageX, int percentageY);

        /**
         * This should be read from a config or something.
         *
         * @return the x position in percentage for the leftmost top of the overlay.
         */
        public abstract int getPercentagePosX();

        /**
         * This should be read from a config or something.
         *
         * @return the y position in percentage for the leftmost top of the overlay.
         */
        public abstract int getPercentagePosY();

        /**
         * Do not override this.
         *
         * @return the x position on the leftmost top of the overlay.
         */
        @Override
        public int getPosX() {
            return Math.round((((float) Minecraft.getInstance().getWindow().getGuiScaledWidth() / 100) * getPercentagePosX()));
        }

        /**
         * Do not override this.
         *
         * @return the y position on the leftmost top of the overlay.
         */
        @Override
        public int getPosY() {
            return Math.round((((float) Minecraft.getInstance().getWindow().getGuiScaledHeight() / 100) * getPercentagePosY()));
        }
    }
}
