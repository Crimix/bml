package com.black_dog20.bml.internal.client.screen;


import com.black_dog20.bml.client.overlay.OverlayRegistry;
import com.black_dog20.bml.client.overlay.configure.IConfigurableOverlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OverlayConfigScreen extends Screen {

    public OverlayConfigScreen() {
        super(Component.literal(""));
    }

    @Override
    protected void init() {
        for (IConfigurableOverlay overlay : OverlayRegistry.CONFIGURABLE_OVERLAYS)
            this.addRenderableWidget(new OverlayConfigWidget(overlay));
    }
}
