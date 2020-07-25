package com.black_dog20.bml.client.overlay;

import com.black_dog20.bml.client.overlay.configure.IConfigurableOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class OverlayRegistry {

    public static List<IConfigurableOverlay> CONFIGURABLE_OVERLAYS = new ArrayList<>();

    public static void register(Overlay overlay) {
        if (overlay instanceof IConfigurableOverlay)
            CONFIGURABLE_OVERLAYS.add((IConfigurableOverlay) overlay);
        MinecraftForge.EVENT_BUS.register(overlay);
    }
}
