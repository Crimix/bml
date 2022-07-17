package com.black_dog20.bml;

import com.black_dog20.bml.init.BmlCrafting;
import com.black_dog20.bml.internal.capability.ArmorInventoryCapability;
import com.black_dog20.bml.internal.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Mod(Bml.MOD_ID)
public class Bml {
    public static final String MOD_ID = "blackdogsmoddinglibrary";
    private static final Logger LOGGER = LogManager.getLogger();

    public Bml() {
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        event.addListener(this::setup);
        event.addListener(this::setupClient);
        event.addListener(this::registerCapabilities);
        event.addListener(BmlCrafting::registerRecipeSerialziers);
        BmlCrafting.RECIPE_SERIALIZERS.register(event);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        PacketHandler.register();
        LOGGER.info("Setup Complete!");
    }

    private void setupClient(final FMLClientSetupEvent event) {
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ArmorInventoryCapability.class);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "NONE";
    }

    public static boolean isDevBuild() {
        return getVersion().contains("NONE");
    }
}
