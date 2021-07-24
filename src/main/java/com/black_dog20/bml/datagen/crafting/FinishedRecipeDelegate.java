package com.black_dog20.bml.datagen.crafting;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;

/**
 * An {@link IFinishedRecipe} that delegates to another {@link IFinishedRecipe} instance.
 *
 * @author Choonster
 * @see <a href="https://github.com/Choonster-Minecraft-Mods/TestMod3">TestMod3</a>
 */
public class FinishedRecipeDelegate implements FinishedRecipe {
    protected final FinishedRecipe baseRecipe;

    public FinishedRecipeDelegate(final FinishedRecipe baseRecipe) {
        this.baseRecipe = baseRecipe;
    }

    /**
     * Serializes the recipe
     *
     * @param json A json object.
     */
    @Override
    public void serializeRecipeData(final JsonObject json) {
        baseRecipe.serializeRecipeData(json);
    }

    /**
     * Gets the ID for the recipe.
     *
     * @return the id.
     */
    @Override
    public ResourceLocation getId() {
        return baseRecipe.getId();
    }

    /**
     * Gets the recipe serializer.
     *
     * @return the recipe serializer.
     */
    @Override
    public RecipeSerializer<?> getType() {
        return baseRecipe.getType();
    }

    /**
     * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
     *
     * @return the json object for the advancement.
     */
    @Override
    @Nullable
    public JsonObject serializeAdvancement() {
        return baseRecipe.serializeAdvancement();
    }

    /**
     * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #getAdvancementJson} is
     * non-null.
     *
     * @return the advancement id.
     */
    @Override
    @Nullable
    public ResourceLocation getAdvancementId() {
        return baseRecipe.getAdvancementId();
    }
}