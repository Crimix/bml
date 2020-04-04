package com.black_dog20.bml;

import com.black_dog20.bml.init.BmlCrafting;
import com.black_dog20.bml.internal.capability.ArmorInventoryCapability;
import com.black_dog20.bml.internal.capability.ArmorInventoryStorage;
import com.black_dog20.bml.internal.capability.IArmorInventoryCapability;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Bml.MOD_ID)
public class Bml {
    public static final String MOD_ID = "blackdogsmoddinglibrary";
    private static final Logger LOGGER = LogManager.getLogger();

    public Bml() {
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        event.addListener(this::setup);
        event.addGenericListener(IRecipeSerializer.class, BmlCrafting::registerRecipeSerialziers);
        BmlCrafting.RECIPE_SERIALIZERS.register(event);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IArmorInventoryCapability.class, new ArmorInventoryStorage(), new ArmorInventoryCapability.Factory());
        LOGGER.info("Setup Complete!");
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
