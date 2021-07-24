//package com.black_dog20.bml.internal.mixin;
//
//import com.black_dog20.bml.event.PlayerOpChangeEvent;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.server.players.PlayerList;
//import net.minecraftforge.common.MinecraftForge;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(PlayerList.class)
//public class PlayerListMixin {
//
//    @Inject(method = "sendPlayerPermissionLevel(Lnet/minecraft/entity/player/ServerPlayerEntity;I)V", at = @At("HEAD"))
//    private void updatePermissionLevel(ServerPlayer player, int permLevel, CallbackInfo ci) {
//        MinecraftForge.EVENT_BUS.post(new PlayerOpChangeEvent(player, permLevel != 0, permLevel));
//    }
//}
//TODO when mixins are back