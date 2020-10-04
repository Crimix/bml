package com.black_dog20.bml.network.messages;

import com.black_dog20.bml.network.Handlers;
import com.black_dog20.bml.utils.player.AbstractPlayerPermissions;
import com.google.gson.Gson;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

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

    public static <T extends AbstractPlayerPermissions> void encode(PacketPermission<T> msg, PacketBuffer buffer) {
        buffer.writeString(msg.clazz.getName(), 32767);
        buffer.writeString(gson.toJson(msg.permissions), 32767);
    }

    public static <T extends AbstractPlayerPermissions> PacketPermission<T> decode(PacketBuffer buffer) {
        Class<T> clazz = (Class<T>) AbstractPlayerPermissions.class;
        try {
            clazz = (Class<T>) Class.forName(buffer.readString(32767));
        } catch (ClassNotFoundException ignored) {
        }

        return new PacketPermission<>(clazz, gson.fromJson(buffer.readString(32767), clazz));
    }

    public static class Handler {
        public static <T extends AbstractPlayerPermissions> void handle(PacketPermission<T> msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Handlers.handle(msg)));
            ctx.get().setPacketHandled(true);
        }
    }
}
