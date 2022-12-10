package com.black_dog20.bml.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

public interface ShapedNBTRecipeConsumer {
    ShapedNBTRecipe create(final ResourceLocation id, final String group, final CraftingBookCategory category, final int recipeWidth, final int recipeHeight, final NonNullList<Ingredient> ingredients, final ItemStack recipeOutput);
}
