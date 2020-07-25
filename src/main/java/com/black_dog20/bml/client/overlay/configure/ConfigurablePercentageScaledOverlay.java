package com.black_dog20.bml.client.overlay.configure;

import com.black_dog20.bml.client.overlay.Overlay;
import net.minecraft.client.Minecraft;

public abstract class ConfigurablePercentageScaledOverlay extends Overlay implements IConfigurableOverlay {

    public abstract static class Pre extends Overlay.Pre implements IConfigurableOverlay {
        @Override
        public void setPosition(int x, int y) {
            int resX = Math.round(x / (((float) Minecraft.getInstance().getMainWindow().getScaledWidth()) / 100));
            int resY = Math.round(y / (((float) Minecraft.getInstance().getMainWindow().getScaledHeight()) / 100));

            setPercentagePosition(resX, resY);
        }

        public abstract void setPercentagePosition(int percentageX, int percentageY);

        public abstract int getPercentagePosX();

        public abstract int getPercentagePosY();

        @Override
        public int getPosX() {
            return Math.round((((float) Minecraft.getInstance().getMainWindow().getScaledWidth() / 100) * getPercentagePosX()));
        }

        @Override
        public int getPosY() {
            return Math.round((((float) Minecraft.getInstance().getMainWindow().getScaledHeight() / 100) * getPercentagePosY()));
        }
    }

    public abstract static class Post extends Overlay.Post implements IConfigurableOverlay {
        @Override
        public void setPosition(int x, int y) {
            int resX = Math.round(x / (((float) Minecraft.getInstance().getMainWindow().getScaledWidth()) / 100));
            int resY = Math.round(y / (((float) Minecraft.getInstance().getMainWindow().getScaledHeight()) / 100));

            setPercentagePosition(resX, resY);
        }

        public abstract void setPercentagePosition(int percentageX, int percentageY);

        public abstract int getPercentagePosX();

        public abstract int getPercentagePosY();

        @Override
        public int getPosX() {
            return Math.round((((float) Minecraft.getInstance().getMainWindow().getScaledWidth() / 100) * getPercentagePosX()));
        }

        @Override
        public int getPosY() {
            return Math.round((((float) Minecraft.getInstance().getMainWindow().getScaledHeight() / 100) * getPercentagePosY()));
        }
    }
}
