package com.black_dog20.bml.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface ShapelessNBTRecipeConsumer {
    ShapelessNBTRecipe create(final ResourceLocation id, final String group, final ItemStack recipeOutput, final NonNullList<Ingredient> recipeItems);
}
