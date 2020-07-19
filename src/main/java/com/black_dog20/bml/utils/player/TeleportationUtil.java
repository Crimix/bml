package com.black_dog20.bml.utils.player;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IWorldInfo;

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
    public static boolean teleportPlayerToDestination(ServerPlayerEntity player, TeleportDestination destination) {
        try {
            ServerWorld world = player.server.getWorld(destination.getDimension());
            BlockPos pos = destination.getPosition();
            player.teleport(world, pos.getX(), pos.getY(), pos.getZ(), destination.getRotationYaw(), destination.getRotationPitch());
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
    public static boolean teleportPlayerToSpawn(ServerPlayerEntity player) {
        try {
            ServerWorld world = player.server.getWorld(World.field_234918_g_);
            IWorldInfo worldInfo = world.getWorldInfo();
            player.teleport(world, worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ(), player.rotationYaw, player.rotationPitch);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
