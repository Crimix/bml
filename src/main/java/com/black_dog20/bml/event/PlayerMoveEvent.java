package com.black_dog20.bml.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * {@link PlayerMoveEvent} is fired when a player moves.
 * It does not always fire every tick.
 * This event is fired on server-side only.
 * {@link #distance} contains the distance the player moved along the x and z axis.
 * This event is not {@link Cancelable}.
 * This event does not have a result {@link HasResult}.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
public class PlayerMoveEvent extends PlayerEvent {

    private final double distance;

    public PlayerMoveEvent(Player player, double distance) {
        super(player);
        this.distance = distance;
    }

    /**
     * Gets the horizontal distance the player has moved.
     *
     * @return the distance.
     */
    public double getDistance() {
        return distance;
    }
}
