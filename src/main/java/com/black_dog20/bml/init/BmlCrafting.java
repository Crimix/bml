package com.black_dog20.bml.init;

import com.black_dog20.bml.Bml;
import com.black_dog20.bml.crafting.IngredientNBT;
import com.black_dog20.bml.crafting.ShapedNBTRecipe;
import com.black_dog20.bml.crafting.ShapelessNBTRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BmlCrafting {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Bml.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> SHAPED_NBT = RECIPE_SERIALIZERS.register("shaped_nbt", ShapedNBTRecipe.factory());
    public static final RegistryObject<RecipeSerializer<?>> SHAPELESS_NBT = RECIPE_SERIALIZERS.register("shapeless_nbt", ShapelessNBTRecipe.factory());

    public static void registerRecipeSerialziers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(new ResourceLocation(Bml.MOD_ID, "nbt"), IngredientNBT.Serializer.INSTANCE);
    }
}
