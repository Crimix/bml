package com.black_dog20.bml.internal.utils;

import com.black_dog20.bml.internal.client.screen.OverlayConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientUtil {

    public static void openOverlayConfigScreen() {
        Minecraft.getInstance().displayGuiScreen(new OverlayConfigScreen());
    }
}
