package com.black_dog20.bml.internal.event;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.internal.utils.SoulBoundInventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bml.MOD_ID)
public class SoulboundHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof Player && !(event.getEntity() instanceof FakePlayer)) {
            Player player = (Player) event.getEntity();
            if (player.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY))
                return;
            if (event.isCanceled())
                return;
            SoulBoundInventory soul = new SoulBoundInventory(player);
            soul.writeToNBT();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerSpawn(PlayerEvent.Clone event) {
        if (!event.isWasDeath() || event.isCanceled())
            return;

        if (event.getOriginal() == null || event.getPlayer() == null || event.getPlayer() instanceof FakePlayer)
            return;
        Player player = event.getPlayer();
        Player original = event.getOriginal();

        if (player.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY))
            return;

        if (original.equals(player) || original.getInventory().equals(player.getInventory()) || (original.getInventory().armor.equals(player.getInventory().armor) && original.getInventory().items.equals(player.getInventory().items)))
            return;

        SoulBoundInventory soul = SoulBoundInventory.GetForPlayer(original);
        soul.restoreMain(player);
        soul.restoreArmor(player);
        soul.restoreHand(player);
        soul.clear();

    }
}
