package com.black_dog20.bml.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.Map;

/**
 * Utility methods for {@link Recipe}s.
 *
 * @author Choonster
 * @see <a href="https://github.com/Choonster-Minecraft-Mods/TestMod3">TestMod3</a>
 */
public class RecipeUtil {

    /**
     * Parse the ingredients of a shaped recipe.
     * <p>
     * Adapted from {@link ShapedRecipe.Serializer#fromJson(ResourceLocation, JsonObject)}.
     *
     * @param json The recipe's JSON object
     * @return A primer containing the ingredients, recipe width and recipe height specified in the JSON object.
     */
    public static ShapedPrimer parseShaped(final JsonObject json) {
        final Map<String, Ingredient> key = ShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
        final String[] pattern = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
        final int recipeWidth = pattern[0].length();
        final int recipeHeight = pattern.length;
        final NonNullList<Ingredient> ingredients = ShapedRecipe.dissolvePattern(pattern, key, recipeWidth, recipeHeight);

        return new ShapedPrimer(ingredients, recipeWidth, recipeHeight);
    }

    /**
     * Parse the input of a shapeless recipe.
     *
     * @param json The recipe's JSON object
     * @return A NonNullList containing the ingredients specified in the JSON object
     */
    public static NonNullList<Ingredient> parseShapeless(final JsonObject json) {
        final NonNullList<Ingredient> ingredients = NonNullList.create();
        for (final JsonElement element : GsonHelper.getAsJsonArray(json, "ingredients"))
            ingredients.add(CraftingHelper.getIngredient(element));

        if (ingredients.isEmpty())
            throw new JsonParseException("No ingredients for shapeless recipe");

        return ingredients;
    }

    public static class ShapedPrimer {
        private final NonNullList<Ingredient> ingredients;
        private final int recipeWidth;
        private final int recipeHeight;

        public ShapedPrimer(final NonNullList<Ingredient> ingredients, final int recipeWidth, final int recipeHeight) {
            this.ingredients = ingredients;
            this.recipeWidth = recipeWidth;
            this.recipeHeight = recipeHeight;
        }

        public NonNullList<Ingredient> getIngredients() {
            return ingredients;
        }

        public int getRecipeWidth() {
            return recipeWidth;
        }

        public int getRecipeHeight() {
            return recipeHeight;
        }
    }
}
