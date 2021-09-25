package com.black_dog20.bml.client.overlay;

import com.black_dog20.bml.client.overlay.configure.IConfigurableOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry for {@link GameOverlay}s.
 */
@OnlyIn(Dist.CLIENT)
public class OverlayRegistry {

    /**
     * Contains all the configurable overlays that have been registered.
     */
    public static List<IConfigurableOverlay> CONFIGURABLE_OVERLAYS = new ArrayList<>();

    /**
     * Registers an {@link GameOverlay}.
     *
     * @param overlay the overlay to register.
     */
    public static void register(GameOverlay overlay) {
        if (overlay instanceof IConfigurableOverlay)
            CONFIGURABLE_OVERLAYS.add((IConfigurableOverlay) overlay);
        MinecraftForge.EVENT_BUS.register(overlay);
    }
}
