package com.black_dog20.bml.internal.event;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.event.PlayerMoveEvent;
import com.black_dog20.bml.event.PlayerOpChangeEvent;
import com.black_dog20.bml.utils.math.Pos3D;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = Bml.MOD_ID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;
        PlayerEntity player = event.player;
        Pos3D prevPos = new Pos3D(player.prevChasingPosX, 0, player.prevChasingPosZ);
        Pos3D currPos = new Pos3D(player.chasingPosX, 0, player.chasingPosZ);
        double distance = prevPos.distanceTo(currPos);
        if (distance == 0.0)
            return;
        MinecraftForge.EVENT_BUS.post(new PlayerMoveEvent(event.player, distance));
    }

    @SubscribeEvent
    public static void onCommand(CommandEvent event) {
        if (event.getException() != null)
            return;
        String command = event.getParseResults().getReader().getString();
        CommandSource source = event.getParseResults().getContext().getSource();
        if (command.startsWith("/op") || (command.startsWith("op") && source.getEntity() == null)) {
            String playerName = command.split(" ")[1];
            PlayerList playerList = ServerLifecycleHooks.getCurrentServer().getPlayerList();
            ServerPlayerEntity playerEntity = playerList.getPlayerByUsername(playerName);
            if (!playerList.canSendCommands(playerEntity.getGameProfile())) {
                MinecraftForge.EVENT_BUS.post(new PlayerOpChangeEvent(playerEntity, true));
            }
        } else if (command.startsWith("/deop") || (command.startsWith("deop") && source.getEntity() == null)) {
            String playerName = command.split(" ")[1];
            PlayerList playerList = ServerLifecycleHooks.getCurrentServer().getPlayerList();
            ServerPlayerEntity playerEntity = playerList.getPlayerByUsername(playerName);
            if (playerList.canSendCommands(playerEntity.getGameProfile())) {
                MinecraftForge.EVENT_BUS.post(new PlayerOpChangeEvent(playerEntity, false));
            }
        }
    }
}
