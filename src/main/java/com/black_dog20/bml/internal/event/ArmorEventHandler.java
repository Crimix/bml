package com.black_dog20.bml.internal.event;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.bml.internal.capability.ArmorInventoryCapability;
import com.black_dog20.bml.internal.capability.IArmorInventoryCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
        if (evt.getObject() instanceof Player) {
            evt.addCapability(new ResourceLocation(Bml.MOD_ID, "armor_inventory_capability"), ArmorInventoryCapability.createProvider());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerClone(PlayerEvent.Clone event) {
        LazyOptional<IArmorInventoryCapability> oldCap = event.getOriginal().getCapability(ArmorInventoryCapability.CAP);
        LazyOptional<IArmorInventoryCapability> newCap = event.getEntity().getCapability(ArmorInventoryCapability.CAP);

        oldCap.ifPresent(o -> newCap.ifPresent(o::copyTo));
        for (ItemStack armor : event.getEntity().getInventory().armor) {
            if (!armor.isEmpty()) {
                if (!event.getEntity().level().isClientSide) {
                    MinecraftForge.EVENT_BUS.post(new ArmorEvent.Equip(event.getEntity(), armor));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        for (ItemStack armor : event.getEntity().getInventory().armor) {
            if (!armor.isEmpty()) {
                if (!event.getEntity().level().isClientSide) {
                    MinecraftForge.EVENT_BUS.post(new ArmorEvent.Equip(event.getEntity(), armor));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        LazyOptional<IArmorInventoryCapability> capability = event.player.getCapability(ArmorInventoryCapability.CAP);

        capability.ifPresent(cap -> {
            int size = cap.getSize();
            for (int i = 0; i < size; i++) {
                ItemStack prev = cap.getStackInSlot(i);
                ItemStack curr = event.player.getInventory().armor.get(i);
                if (!ItemStack.isSameItem(prev, curr)) {
                    if (!prev.isEmpty()) {
                        if (!event.player.level().isClientSide) {
                            MinecraftForge.EVENT_BUS.post(new ArmorEvent.Unequip(event.player, prev));
                        }
                    }
                    if (!curr.isEmpty()) {
                        if (!event.player.level().isClientSide) {
                            MinecraftForge.EVENT_BUS.post(new ArmorEvent.Equip(event.player, curr));
                        }
                    }
                }
                if (!curr.isEmpty())
                    MinecraftForge.EVENT_BUS.post(new ArmorEvent.Tick(event.phase, event.player, curr));
                cap.setStackInSlot(i, curr.copy());
            }
        });
    }
}
