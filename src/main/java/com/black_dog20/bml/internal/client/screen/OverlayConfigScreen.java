package com.black_dog20.bml.internal.client.screen;


import com.black_dog20.bml.client.overlay.OverlayRegistry;
import com.black_dog20.bml.client.overlay.configure.IConfigurableOverlay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class OverlayConfigScreen extends Screen {

    public OverlayConfigScreen() {
        super(new StringTextComponent(""));
    }

    @Override
    protected void init() {
        for (IConfigurableOverlay overlay : OverlayRegistry.CONFIGURABLE_OVERLAYS)
            this.addButton(new OverlayConfigWidget(overlay));
    }
}
