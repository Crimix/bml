package com.black_dog20.bml.utils.player;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class TeleportDestination {

    private ResourceLocation dimension;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;

    public TeleportDestination(ResourceKey<Level> dimension, ServerPlayer player) {
        this(dimension, player.blockPosition(), player.getYRot(), player.getXRot());
    }

    public TeleportDestination(ResourceKey<Level> dimension, BlockPos pos, float yaw, float pitch) {
        this.dimension = dimension.location();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public ResourceKey<Level> getDimension() {
        return ResourceKey.create(Registries.DIMENSION, dimension);
    }

    public BlockPos getPosition() {
        return new BlockPos(x, y, z);
    }

    public float getRotationYaw() {
        return yaw;
    }

    public float getRotationPitch() {
        return pitch;
    }
}
