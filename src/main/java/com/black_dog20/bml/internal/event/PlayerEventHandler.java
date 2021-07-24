package com.black_dog20.bml.internal.event;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.event.PlayerMoveEvent;
import com.black_dog20.bml.utils.math.Pos3D;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bml.MOD_ID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;
        Player player = event.player;
        Pos3D prevPos = new Pos3D(player.xCloakO, 0, player.zCloakO);
        Pos3D currPos = new Pos3D(player.xCloak, 0, player.zCloak);
        double distance = prevPos.distanceTo(currPos);
        if (distance == 0.0)
            return;
        MinecraftForge.EVENT_BUS.post(new PlayerMoveEvent(event.player, distance));
    }
}
