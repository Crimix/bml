package com.black_dog20.bml.client.overlay.configure;

import com.black_dog20.bml.utils.color.Color4i;
import net.minecraft.util.text.ITextComponent;

public interface IConfigurableOverlay {

    int getHeight();

    int getWidth();

    void setPosition(int x, int y);

    int getPosX();

    int getPosY();

    Color4i getActiveColor();

    default Color4i getInactiveColor() {
        return Color4i.of(128, 128, 128);
    }

    boolean getActive();

    boolean canBeInactive();

    void setInactive(boolean active);

    ITextComponent getName();
}
