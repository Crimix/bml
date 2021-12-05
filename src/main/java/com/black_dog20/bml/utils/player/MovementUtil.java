package com.black_dog20.bml.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MovementUtil {

    /**
     * Speeds up the player elytra flying with a speed and acceleration.
     *
     * @param player the player.
     * @param speed  the speed.
     * @param accel  the acceleration.
     */
    public static void speedUpElytraFlight(Player player, double speed, double accel) {
        speedUpElytraFlight(player, speed, accel, accel);
    }

    /**
     * Speeds up the player elytra flying with a speed and acceleration.
     *
     * @param player the player.
     * @param speed  the speed.
     * @param accel  the acceleration.
     * @param accel2 the second acceleration.
     */
    public static void speedUpElytraFlight(Player player, double speed, double accel, double accel2) {
        Vec3 look = player.getLookAngle();
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.add(
                look.x * accel + (look.x * speed - motion.x) * accel2,
                look.y * accel + (look.y * speed - motion.y) * accel2,
                look.z * accel + (look.z * speed - motion.z) * accel2
        ));
    }

    /**
     * Replaces the players current y motion with the input which can make it ascend in the y axis.
     *
     * @param player  the player.
     * @param motionY the new y motion.
     */
    public static void jetpackFly(Player player, double motionY) {
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.get(Direction.Axis.X), motionY, motion.get(Direction.Axis.Z));
    }

    /**
     * Speeds up the players movement based on the two input speeds and which keys the player is holding down.
     *
     * @param player         the player
     * @param baseSpeed      the base speed.
     * @param sprintModifier the sprint modifier
     */
    @OnlyIn(Dist.CLIENT)
    public static void speedUp(Player player, double baseSpeed, double sprintModifier) {
        float speedSideways = (float) (player.isCrouching() ? baseSpeed * 0.5F : baseSpeed);
        float speedForward = (float) (player.isSprinting() ? speedSideways * sprintModifier : speedSideways);

        if (Minecraft.getInstance().options.keyUp.isDown()) {
            player.moveRelative(1, new Vec3(0, 0, speedForward));
        }
        if (Minecraft.getInstance().options.keyDown.isDown()) {
            player.moveRelative(1, new Vec3(0, 0, -speedSideways * 0.8F));
        }
        if (Minecraft.getInstance().options.keyLeft.isDown()) {
            player.moveRelative(1, new Vec3(speedSideways, 0, 0));
        }
        if (Minecraft.getInstance().options.keyRight.isDown()) {
            player.moveRelative(1, new Vec3(-speedSideways, 0, 0));
        }
    }
}
