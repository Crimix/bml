package com.black_dog20.bml.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * {@link PlayerOpChangeEvent} is fired when a player's op status changes
 * It only fires if the status changes.
 * This event is fired on server-side only.
 * {@link #opStatus} contains the the new op status.
 * This event is not {@link Cancelable}.
 * This event does not have a result {@link HasResult}.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
public class PlayerOpChangeEvent extends PlayerEvent {

    private final boolean opStatus;

    public PlayerOpChangeEvent(ServerPlayerEntity player, boolean opStatus) {
        super(player);
        this.opStatus = opStatus;
    }

    /**
     * Gets the new op status of the player.
     *
     * @return the new op status.
     */
    public boolean getNewStatus() {
        return opStatus;
    }
}
