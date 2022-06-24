package com.black_dog20.bml.compat;

import net.minecraftforge.common.MinecraftForge;

/**
 * Annotation for optional mod compatibility which should be loaded and registered if target mods is present.
 * ModCompat classes if the conditions are meet is registered as an instance to the {@link MinecraftForge#EVENT_BUS}
 */
public @interface ModCompatPlugin {
    /**
     * The source mod id (your mod) which should be found and handled.
     * @return the source mod id
     */
    String sourceMod();

    /**
     * The target mods which needs to be loaded before the class annotated with this is loaded and registered.
     * @return the target mod(s) id(s).
     */
    String[] targetMods();
}
