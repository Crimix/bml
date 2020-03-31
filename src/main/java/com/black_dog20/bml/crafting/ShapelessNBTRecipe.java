package com.black_dog20.bml.crafting;

import com.black_dog20.bml.init.BmlCrafting;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

/**
 * Special class for shapeless recipes using NBT data.
 *
 * @author black_dog20
 */
public class ShapelessNBTRecipe extends ShapelessRecipe {

    private ShapelessNBTRecipe(final ResourceLocation id, final String group, final ItemStack recipeOutput, final NonNullList<Ingredient> recipeItems) {
        super(id, group, recipeOutput, recipeItems);
    }

    /**
     * @return the specialized serializer for this recipe type.
     */
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return BmlCrafting.SHAPELESS_NBT.get();
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapelessRecipe> {

        public ShapelessRecipe read(ResourceLocation recipeId, JsonObject json) {
            final String group = JSONUtils.getString(json, "group", "");
            final NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(json);
            final ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);

            return new ShapelessNBTRecipe(recipeId, group, result, ingredients);

        }

        public ShapelessRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final String group = buffer.readString(32767);
            final int numIngredients = buffer.readVarInt();
            final NonNullList<Ingredient> ingredients = NonNullList.withSize(numIngredients, Ingredient.EMPTY);

            for (int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.read(buffer));
            }

            final ItemStack result = buffer.readItemStack();
            return new ShapelessNBTRecipe(recipeId, group, result, ingredients);
        }

        public void write(PacketBuffer buffer, ShapelessRecipe recipe) {
            buffer.writeString(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());

            for (final Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.getRecipeOutput());
        }
    }
}