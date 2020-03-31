package com.black_dog20.bml.datagen.crafting;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * An {@link IFinishedRecipe} that delegates to another {@link IFinishedRecipe} instance.
 *
 * @author Choonster
 * @see <a href="https://github.com/Choonster-Minecraft-Mods/TestMod3">TestMod3</a>
 */
public class FinishedRecipeDelegate implements IFinishedRecipe {
    protected final IFinishedRecipe baseRecipe;

    public FinishedRecipeDelegate(final IFinishedRecipe baseRecipe) {
        this.baseRecipe = baseRecipe;
    }

    /**
     * Serializes the recipe
     *
     * @param json A json object.
     */
    @Override
    public void serialize(final JsonObject json) {
        baseRecipe.serialize(json);
    }

    /**
     * Gets the ID for the recipe.
     *
     * @return the id.
     */
    @Override
    public ResourceLocation getID() {
        return baseRecipe.getID();
    }

    /**
     * Gets the recipe serializer.
     *
     * @return the recipe serializer.
     */
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return baseRecipe.getSerializer();
    }

    /**
     * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
     *
     * @return the json object for the advancement.
     */
    @Override
    @Nullable
    public JsonObject getAdvancementJson() {
        return baseRecipe.getAdvancementJson();
    }

    /**
     * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #getAdvancementJson} is
     * non-null.
     *
     * @return the advancement id.
     */
    @Override
    @Nullable
    public ResourceLocation getAdvancementID() {
        return baseRecipe.getAdvancementID();
    }
}