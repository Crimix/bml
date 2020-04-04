package com.black_dog20.bml.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public interface ShapelessNBTRecipeConsumer {
    ShapelessNBTRecipe create(final ResourceLocation id, final String group, final ItemStack recipeOutput, final NonNullList<Ingredient> recipeItems);
}
