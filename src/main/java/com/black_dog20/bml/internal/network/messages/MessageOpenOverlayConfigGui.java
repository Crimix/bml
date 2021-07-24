package com.black_dog20.bml.internal.network.messages;

import com.black_dog20.bml.internal.utils.ClientUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;


public class MessageOpenOverlayConfigGui {

    public MessageOpenOverlayConfigGui() {
    }

    public static void encode(MessageOpenOverlayConfigGui msg, FriendlyByteBuf buffer) {
    }

    public static MessageOpenOverlayConfigGui decode(FriendlyByteBuf buffer) {
        return new MessageOpenOverlayConfigGui();
    }

    public static class Handler {
        public static void handle(MessageOpenOverlayConfigGui msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientUtil::openOverlayConfigScreen));
            ctx.get().setPacketHandled(true);
        }
    }
}
