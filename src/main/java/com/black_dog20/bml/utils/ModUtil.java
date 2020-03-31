package com.black_dog20.bml.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

/**
 * Utility class for random utils.
 *
 * @author black_dog20
 */
public class ModUtil {

    /**
     * Gets a fake player for a world.
     *
     * @param modId the modid.
     * @param world the world.
     * @return the fake player.
     */
    public static FakePlayer getFakePlayer(String modId, ServerWorld world) {
        GameProfile gameProfile = new GameProfile(UUID.fromString("6365307b-aa82-4e1d-b855-a841b869ef81"), modId);
        return new FakePlayer(world, gameProfile);
    }
}
