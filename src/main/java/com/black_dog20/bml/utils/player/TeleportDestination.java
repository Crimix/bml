package com.black_dog20.bml.utils.player;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class TeleportDestination {

    private ResourceLocation dimension;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;

    public TeleportDestination(RegistryKey<World> dimension, ServerPlayerEntity player) {
        this(dimension, player.getPosition(), player.rotationYaw, player.rotationPitch);
    }

    public TeleportDestination(RegistryKey<World> dimension, BlockPos pos, float yaw, float pitch) {
        this.dimension = dimension.func_240901_a_();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public RegistryKey<World> getDimension() {
        return RegistryKey.func_240903_a_(Registry.WORLD_KEY, dimension);
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
