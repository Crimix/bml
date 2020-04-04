package com.black_dog20.bml.init;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.crafting.IngredientNBT;
import com.black_dog20.bml.crafting.ShapedNBTRecipe;
import com.black_dog20.bml.crafting.ShapelessNBTRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BmlCrafting {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, Bml.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<?>> SHAPED_NBT = RECIPE_SERIALIZERS.register("shaped_nbt", ShapedNBTRecipe.factory());
    public static final RegistryObject<IRecipeSerializer<?>> SHAPELESS_NBT = RECIPE_SERIALIZERS.register("shapeless_nbt", ShapelessNBTRecipe.factory());

    public static void registerRecipeSerialziers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CraftingHelper.register(new ResourceLocation(Bml.MOD_ID, "nbt"), IngredientNBT.Serializer.INSTANCE);
    }
}
