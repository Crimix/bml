package com.black_dog20.bml.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * {@link PlayerOpChangeEvent} is fired when a player's op status changes
 * It fires when ever the players op status is updated.
 * This means also on login.
 * This event is fired on server-side only.
 * {@link #opStatus} contains the the new op status.
 * {@link #level} contains the the new permission level.
 * This event is not {@link Cancelable}.
 * This event does not have a result {@link HasResult}.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
public class PlayerOpChangeEvent extends PlayerEvent {

    private final boolean opStatus;
    private final int level;

    public PlayerOpChangeEvent(ServerPlayerEntity player, boolean opStatus, int level) {
        super(player);
        this.opStatus = opStatus;
        this.level = level;
    }

    /**
     * Gets the new op status of the player.
     *
     * @return the new op status.
     */
    public boolean getNewStatus() {
        return opStatus;
    }

    /**
     * Gets the new permission level of the player.
     *
     * @return new permission level.
     */
    public int getNewLevel() {
        return level;
    }
}
