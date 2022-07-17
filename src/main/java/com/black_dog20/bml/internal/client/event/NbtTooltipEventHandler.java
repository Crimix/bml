package com.black_dog20.bml.internal.client.event;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.internal.utils.DevEnvironmentChecker;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Bml.MOD_ID, value = Dist.CLIENT)
public class NbtTooltipEventHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (Screen.hasShiftDown() && Screen.hasControlDown() && DevEnvironmentChecker.isDev()) {
            ItemStack itemStack = event.getItemStack();
            List<Component> tooltips = event.getToolTip();
            CompoundTag nbtTag = itemStack.getTag();
            if (nbtTag != null) {
                for (String key : nbtTag.getAllKeys()) {
                    Tag tag = nbtTag.get(key);
                    if (tag != null) {
                        if (tag instanceof CollectionTag collectionTag) {
                            tooltips.add(Component.literal("#" + key + ":").withStyle(ChatFormatting.DARK_GRAY));
                            for (Object o : collectionTag) {
                                if (o instanceof Tag t) {
                                    tooltips.add(Component.literal(" " + t).withStyle(ChatFormatting.DARK_GRAY));
                                }
                            }
                        } else {
                            tooltips.add(Component.literal("#" + key + ": " + tag.getAsString()).withStyle(ChatFormatting.DARK_GRAY));
                        }

                    }
                }
            }
        }
    }
}
