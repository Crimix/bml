package com.black_dog20.bml.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * Events for armor items.
 *
 * @author black_dog20
 */
public class ArmorEvent extends Event {

    public ItemStack armor;
    public Player player;

    private ArmorEvent(Player player, ItemStack armor) {
        super();
        this.player = player;
        this.armor = armor;
    }

    /**
     * Returns if the armor of the event is instance of the class.
     *
     * @param clazz the class, should extend Item.
     * @return true if the armor is of that class.
     */
    public boolean isArmorInstanceOf(Class clazz) {
        return !armor.isEmpty() && clazz.isInstance(armor.getItem());
    }

    /**
     * Returns if the armor of the event is equal to the input item.
     *
     * @param item the item to compare to.
     * @return true if the armor is equal to the item.
     */
    public boolean isArmorEqualTo(Item item) {
        return !armor.isEmpty() && armor.getItem() == item;
    }

    /**
     * {@link Equip} is fired when a player equips a new armor item
     * This event is fired on server-side only.
     * {@link #armor} contains the affected {@link ItemStack}.
     * This event is not {@link Cancelable}.
     * This event does not have a result {@link HasResult}.
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    public static class Equip extends ArmorEvent {

        public Equip(Player player, ItemStack armor) {
            super(player, armor);
        }
    }

    /**
     * {@link Equip} is fired when a player unequips an armor item
     * This event is fired on server-side only.
     * {@link #armor} contains the affected {@link ItemStack}.
     * This event is not {@link Cancelable}.
     * This event does not have a result {@link HasResult}.
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    public static class Unequip extends ArmorEvent {

        public Unequip(Player player, ItemStack armor) {
            super(player, armor);
        }
    }

    /**
     * {@link Tick} is fired for each armor item every tick
     * This event is fired on both sides.
     * {@link #armor} contains the affected {@link ItemStack}.
     * This event is not {@link Cancelable}.
     * This event does not have a result {@link HasResult}.
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    public static class Tick extends ArmorEvent {

        public LogicalSide side;

        public TickEvent.Phase phase;

        public Tick(TickEvent.Phase phase, Player player, ItemStack armor) {
            super(player, armor);
            this.phase = phase;
            this.side = player instanceof ServerPlayer ? LogicalSide.SERVER : LogicalSide.CLIENT;
        }
    }
}
