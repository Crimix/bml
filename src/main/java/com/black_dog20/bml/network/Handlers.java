package com.black_dog20.bml.network;

import com.black_dog20.bml.network.messages.PacketPermission;
import com.black_dog20.bml.utils.player.AbstractPlayerPermissions;
import net.minecraftforge.fml.DistExecutor;

public class Handlers {

    public static <T extends AbstractPlayerPermissions> DistExecutor.SafeRunnable handle(PacketPermission<T> packet) {
        return new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                packet.getPermissions().onReceiveClientMessage();
            }
        };
    }
}
