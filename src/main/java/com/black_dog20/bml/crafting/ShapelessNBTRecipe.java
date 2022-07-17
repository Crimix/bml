package com.black_dog20.bml.crafting;

import com.black_dog20.bml.init.BmlCrafting;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.function.Supplier;

/**
 * Special class for shapeless recipes using NBT data.
 *
 * @author black_dog20
 */
public class ShapelessNBTRecipe extends ShapelessRecipe {

    public ShapelessNBTRecipe(final ResourceLocation id, final String group, final ItemStack recipeOutput, final NonNullList<Ingredient> recipeItems) {
        super(id, group, recipeOutput, recipeItems);
    }

    /**
     * @return the specialized serializer for this recipe type.
     */
    @Override
    public RecipeSerializer<?> getSerializer() {
        return BmlCrafting.SHAPELESS_NBT.get();
    }

    /**
     * When ever you are extending this recipe and implementing new logic, remember to change this factory.
     */
    public static Supplier<Serializer> factory() {
        return () -> new Serializer(ShapelessNBTRecipe::new);
    }

    public static class Serializer implements RecipeSerializer<ShapelessRecipe> {

        private final ShapelessNBTRecipeConsumer consumer;

        public Serializer(ShapelessNBTRecipeConsumer consumer) {
            this.consumer = consumer;
        }

        public ShapelessRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            final NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(json);
            final ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);

            return consumer.create(recipeId, group, result, ingredients);

        }

        public ShapelessRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            final String group = buffer.readUtf(32767);
            final int numIngredients = buffer.readVarInt();
            final NonNullList<Ingredient> ingredients = NonNullList.withSize(numIngredients, Ingredient.EMPTY);

            for (int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromNetwork(buffer));
            }

            final ItemStack result = buffer.readItem();
            return consumer.create(recipeId, group, result, ingredients);
        }

        public void toNetwork(FriendlyByteBuf buffer, ShapelessRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());

            for (final Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.getResultItem());
        }
    }
}