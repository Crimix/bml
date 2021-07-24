package com.black_dog20.bml.utils.player;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;

/**
 * Utility class for teleporting players.
 *
 * @author black_dog20
 */
public class TeleportationUtil {

    /**
     * Teleports the player to a destination.
     *
     * @param player      the player.
     * @param destination the destination to teleport to.
     * @return true if teleported.
     */
    public static boolean teleportPlayerToDestination(ServerPlayer player, TeleportDestination destination) {
        try {
            ServerLevel world = player.server.getLevel(destination.getDimension());
            BlockPos pos = destination.getPosition();
            player.teleportTo(world, pos.getX(), pos.getY(), pos.getZ(), destination.getRotationYaw(), destination.getRotationPitch());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Teleports the player to global world spawn.
     *
     * @param player the player.
     * @return true if teleported.
     */
    public static boolean teleportPlayerToSpawn(ServerPlayer player) {
        try {
            ServerLevel world = player.server.getLevel(Level.OVERWORLD);
            LevelData worldInfo = world.getLevelData();
            player.teleportTo(world, worldInfo.getXSpawn(), worldInfo.getYSpawn(), worldInfo.getZSpawn(), player.getYRot(), player.getXRot());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
