package com.black_dog20.bml;

import com.black_dog20.bml.init.BmlCrafting;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Bml.MOD_ID)
public class Bml {
    public static final String MOD_ID = "blackdogsmoddinglibrary";
    private static final Logger LOGGER = LogManager.getLogger();

    public Bml() {
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        event.addGenericListener(IRecipeSerializer.class, BmlCrafting::registerRecipeSerialziers);
        BmlCrafting.RECIPE_SERIALIZERS.register(event);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
