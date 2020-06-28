package com.black_dog20.bml.internal.event;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.bml.internal.capability.ArmorInventoryCapability;
import com.black_dog20.bml.internal.capability.IArmorInventoryCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bml.MOD_ID)
public class ArmorEventHandler {

    @SubscribeEvent
    public static void onCapabilitiesEntity(AttachCapabilitiesEvent<Entity> evt) {
        if (evt.getObject() instanceof PlayerEntity) {
            evt.addCapability(new ResourceLocation(Bml.MOD_ID, "armor_inventory_capability"), ArmorInventoryCapability.createProvider());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerClone(PlayerEvent.Clone event) {
        LazyOptional<IArmorInventoryCapability> oldCap = event.getOriginal().getCapability(ArmorInventoryCapability.CAP);
        LazyOptional<IArmorInventoryCapability> newCap = event.getPlayer().getCapability(ArmorInventoryCapability.CAP);

        oldCap.ifPresent(o -> newCap.ifPresent(o::copyTo));
        for (ItemStack armor : event.getPlayer().inventory.armorInventory) {
            if (!armor.isEmpty()) {
                if (!event.getPlayer().world.isRemote) {
                    MinecraftForge.EVENT_BUS.post(new ArmorEvent.Equip(event.getPlayer(), armor));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        for (ItemStack armor : event.getPlayer().inventory.armorInventory) {
            if (!armor.isEmpty()) {
                if (!event.getPlayer().world.isRemote) {
                    MinecraftForge.EVENT_BUS.post(new ArmorEvent.Equip(event.getPlayer(), armor));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;
        LazyOptional<IArmorInventoryCapability> capability = event.player.getCapability(ArmorInventoryCapability.CAP);

        capability.ifPresent(cap -> {
            int size = cap.getSize();
            for (int i = 0; i < size; i++) {
                ItemStack prev = cap.getStackInSlot(i);
                ItemStack curr = event.player.inventory.armorInventory.get(i);
                if (!ItemStack.areItemsEqualIgnoreDurability(prev, curr)) {
                    if (!prev.isEmpty()) {
                        if (!event.player.world.isRemote) {
                            MinecraftForge.EVENT_BUS.post(new ArmorEvent.Unequip(event.player, prev));
                        }
                    }
                    if (!curr.isEmpty()) {
                        if (!event.player.world.isRemote) {
                            MinecraftForge.EVENT_BUS.post(new ArmorEvent.Equip(event.player, curr));
                        }
                    }
                }
                if (!curr.isEmpty())
                    MinecraftForge.EVENT_BUS.post(new ArmorEvent.Tick(event.player, curr));
                cap.setStackInSlot(i, curr.copy());
            }
        });
    }
}
