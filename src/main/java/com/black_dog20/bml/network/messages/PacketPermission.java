package com.black_dog20.bml.network.messages;

import com.black_dog20.bml.network.Handlers;
import com.black_dog20.bml.utils.player.AbstractPlayerPermissions;
import com.google.gson.Gson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketPermission<T extends AbstractPlayerPermissions> {

    private static final Gson gson = new Gson();

    private Class<T> clazz;
    private T permissions;

    public PacketPermission(Class<T> clazz, T permissions) {
        this.clazz = clazz;
        this.permissions = permissions;
    }

    public T getPermissions() {
        return permissions;
    }

    public static <T extends AbstractPlayerPermissions> void encode(PacketPermission<T> msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.clazz.getName(), 32767);
        buffer.writeUtf(gson.toJson(msg.permissions), 32767);
    }

    public static <T extends AbstractPlayerPermissions> PacketPermission<T> decode(FriendlyByteBuf buffer) {
        Class<T> clazz = (Class<T>) AbstractPlayerPermissions.class;
        try {
            clazz = (Class<T>) Class.forName(buffer.readUtf(32767));
        } catch (ClassNotFoundException ignored) {
        }

        return new PacketPermission<>(clazz, gson.fromJson(buffer.readUtf(32767), clazz));
    }

    public static class Handler {
        public static <T extends AbstractPlayerPermissions> void handle(PacketPermission<T> msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Handlers.handle(msg)));
            ctx.get().setPacketHandled(true);
        }
    }
}
