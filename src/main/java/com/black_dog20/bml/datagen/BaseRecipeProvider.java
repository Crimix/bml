package com.black_dog20.bml.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

/**
 * Base class for recipe providers.
 * Exposes helper methods.
 *
 * @author black_dog20
 */
public abstract class BaseRecipeProvider extends RecipeProvider {
    private final String modid;

    /**
     * The constructor for the provider.
     *
     * @param packOutput the pack output.
     * @param modid     the modid.
     */
    public BaseRecipeProvider(PackOutput packOutput, String modid) {
        super(packOutput);
        this.modid = modid;
    }

    /**
     * Creates a special recipe.
     *
     * @param recipe the special recipe serializer.
     * @return CustomRecipeBuilder.
     */
    protected SpecialRecipeBuilder specialRecipe(SimpleCraftingRecipeSerializer<?> recipe) {
        return SpecialRecipeBuilder.special(recipe);
    }

    /**
     * Creates a recipe id from the modid and key.
     *
     * @param key the key
     * @return the recipe id.
     */
    protected String ID(String key) {
        return RL(key).toString();
    }

    /**
     * Creates a resource location from the modid and key.
     *
     * @param key the key.
     * @return the resource location.
     */
    protected ResourceLocation RL(String key) {
        return new ResourceLocation(modid, key);
    }

}
