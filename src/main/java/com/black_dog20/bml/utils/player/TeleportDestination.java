package com.black_dog20.bml.utils.player;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class TeleportDestination {

    private String dimension;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;

    public TeleportDestination(DimensionType dimension, ServerPlayerEntity player) {
        this(dimension, player.getPosition(), player.rotationYaw, player.rotationPitch);
    }

    public TeleportDestination(DimensionType dimension, BlockPos pos, float yaw, float pitch) {
        this.dimension = dimension.getRegistryName().toString();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public DimensionType getDimension() {
        return DimensionType.byName(new ResourceLocation(dimension));
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
