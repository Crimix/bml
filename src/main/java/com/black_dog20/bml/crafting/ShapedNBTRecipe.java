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
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.function.Supplier;

/**
 * Special class for shaped recipes using NBT data.
 *
 * @author black_dog20
 */
public class ShapedNBTRecipe extends ShapedRecipe {

    public ShapedNBTRecipe(final ResourceLocation id, final String group, final int recipeWidth, final int recipeHeight, final NonNullList<Ingredient> ingredients, final ItemStack recipeOutput) {
        super(id, group, recipeWidth, recipeHeight, ingredients, recipeOutput);
    }

    /**
     * @return the specialized serializer for this recipe type.
     */
    @Override
    public RecipeSerializer<?> getSerializer() {
        return BmlCrafting.SHAPED_NBT.get();
    }


    /**
     * When ever you are extending this recipe and implementing new logic, remember to change this factory.
     */
    public static Supplier<Serializer> factory() {
        return () -> new Serializer(ShapedNBTRecipe::new);
    }

    public static class Serializer implements RecipeSerializer<ShapedRecipe> {

        private final ShapedNBTRecipeConsumer consumer;

        public Serializer(ShapedNBTRecipeConsumer consumer) {
            this.consumer = consumer;
        }

        public ShapedRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            final RecipeUtil.ShapedPrimer primer = RecipeUtil.parseShaped(json);
            final ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);

            return consumer.create(recipeId, group, primer.getRecipeWidth(), primer.getRecipeHeight(), primer.getIngredients(), result);

        }

        public ShapedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            String s = buffer.readUtf(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for (int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(buffer));
            }

            ItemStack itemstack = buffer.readItem();
            return consumer.create(recipeId, s, i, j, nonnulllist, itemstack);
        }

        public void toNetwork(FriendlyByteBuf buffer, ShapedRecipe recipe) {
            buffer.writeVarInt(recipe.getRecipeWidth());
            buffer.writeVarInt(recipe.getRecipeHeight());
            buffer.writeUtf(recipe.getGroup());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.getResultItem());
        }
    }
}