package com.black_dog20.bml.datagen.crafting;

import com.black_dog20.bml.init.BmlCrafting;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

public class ShapelessNBTRecipeBuilder extends ShapelessRecipeBuilder {
    private static final Method VALIDATE = ObfuscationReflectionHelper.findMethod(ShapelessRecipeBuilder.class, "ensureValid" /* validate */, ResourceLocation.class);
    private static final Field ADVANCEMENT_BUILDER = ObfuscationReflectionHelper.findField(ShapelessRecipeBuilder.class, "advancement" /* advancementBuilder */);
    private static final Field GROUP = ObfuscationReflectionHelper.findField(ShapelessRecipeBuilder.class, "group" /* group */);
    private static final Field INGREDIENTS = ObfuscationReflectionHelper.findField(ShapelessRecipeBuilder.class, "ingredients" /* ingredients */);

    private final ItemStack result;
    private final RecipeSerializer<?> serializer;
    private String itemGroup;

    private ShapelessNBTRecipeBuilder(final ItemStack result) {
        super(result.getItem(), result.getCount());
        this.result = result;
        this.serializer = BmlCrafting.SHAPELESS_NBT.get();
    }

    private ShapelessNBTRecipeBuilder(final ItemStack result, final RecipeSerializer<?> serializer) {
        super(result.getItem(), result.getCount());
        this.result = result;
        this.serializer = serializer;
    }

    /**
     * Factory for recipe builder.
     *
     * @param result The end result of the recipe with its NBT data.
     * @return ShapelessNBTRecipeBuilder.
     */
    public static ShapelessNBTRecipeBuilder shapelessNBTRecipe(final ItemStack result) {
        return new ShapelessNBTRecipeBuilder(result);
    }

    /**
     * Factory for recipe builder.
     *
     * @param serializer the custom serializer to use.
     * @param result     the end result of the recipe with its NBT data.
     * @return ShapelessNBTRecipeBuilder.
     */
    public static ShapelessNBTRecipeBuilder customShapelessNBTRecipe(final ItemStack result, final RecipeSerializer<?> serializer) {
        return new ShapelessNBTRecipeBuilder(result, serializer);
    }

    /**
     * Sets the itemGroup for the recipe.
     *
     * @param group the item group.
     * @return ShapelessNBTRecipeBuilder.
     */
    public ShapelessNBTRecipeBuilder setItemGroup(final String group) {
        itemGroup = group;
        return this;
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param tag the item tag for the recipe ingredient.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder requires(TagKey<Item> tag) {
        return (ShapelessNBTRecipeBuilder) super.requires(tag);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param item the item for the recipe ingredient.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder requires(ItemLike item) {
        return (ShapelessNBTRecipeBuilder) super.requires(item);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param item  the item for the recipe ingredient.
     * @param count how many of the items is in the recipe, many used for storage block crafting.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder requires(ItemLike item, int count) {
        return (ShapelessNBTRecipeBuilder) super.requires(item, count);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param ingredient the ingredient for the recipe ingredient.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder requires(Ingredient ingredient) {
        return (ShapelessNBTRecipeBuilder) super.requires(ingredient);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param ingredient the ingredient for the recipe ingredient.
     * @param count      how many of the items is in the recipe, many used for storage block crafting.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder requires(Ingredient ingredient, int count) {
        return (ShapelessNBTRecipeBuilder) super.requires(ingredient, count);
    }

    /**
     * Adds a criterion for the recipe to be unlocked.
     *
     * @param name      the name of the criterion.
     * @param criterion the criterion.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder unlockedBy(final String name, final CriterionTriggerInstance criterion) {
        return (ShapelessNBTRecipeBuilder) super.unlockedBy(name, criterion);
    }

    /**
     * Sets the group of the recipe.
     *
     * @param group the group.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder group(final String group) {
        return (ShapelessNBTRecipeBuilder) super.group(group);
    }

    /**
     * Builds the recipe.
     *
     * @param consumer the consumer.
     */
    @Override
    public void save(final Consumer<FinishedRecipe> consumer) {
        save(consumer, ForgeRegistries.ITEMS.getKey(result.getItem()));
    }

    /**
     * Builds the recipe.
     *
     * @param consumer the consumer.
     * @param save     the name of the recipe for use in saving it.
     */
    @Override
    public void save(final Consumer<FinishedRecipe> consumer, final String save) {
        final ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(result.getItem());
        if (new ResourceLocation(save).equals(registryName)) {
            throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument");
        } else {
            save(consumer, new ResourceLocation(save));
        }
    }

    /**
     * Builds the recipe.
     *
     * @param consumer the consumer.
     * @param id       the id of the recipe for use in saving it.
     */
    @Override
    public void save(final Consumer<FinishedRecipe> consumer, final ResourceLocation id) {
        try {
            // Perform the super class's validation
            VALIDATE.invoke(this, id);

            final Advancement.Builder advancementBuilder = ((Advancement.Builder) ADVANCEMENT_BUILDER.get(this))
                    .parent(new ResourceLocation("minecraft", "recipes/root"))
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(RequirementsStrategy.OR);

            String group = (String) GROUP.get(this);
            if (group == null) {
                group = "";
            }

            String itemGroupName = itemGroup;
            if (itemGroupName == null) {
                final CreativeModeTab itemGroup = Preconditions.checkNotNull(result.getItem().getItemCategory());
                itemGroupName = itemGroup.getRecipeFolderName();
            }

            @SuppressWarnings("unchecked")
            List<Ingredient> ingredients = (List<Ingredient>) INGREDIENTS.get(this);

            final ResourceLocation advancementID = new ResourceLocation(id.getNamespace(), "recipes/" + itemGroupName + "/" + id.getPath());

            consumer.accept(new Result(id, result, group, ingredients, advancementBuilder, advancementID, serializer));
        } catch (final IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to build Shapeless NBT Recipe " + id, e);
        }
    }

    /**
     * Specialized result class for shapeless recipes using NBT data.
     */
    public class Result extends ShapelessRecipeBuilder.Result {
        private final CompoundTag resultNBT;
        private final RecipeSerializer<?> serializer;

        private Result(final ResourceLocation id, final ItemStack result, final String group, final List<Ingredient> ingredients, final Advancement.Builder advancementBuilder, final ResourceLocation advancementID, final RecipeSerializer<?> serializer) {
            super(id, result.getItem(), result.getCount(), group, ingredients, advancementBuilder, advancementID);
            resultNBT = result.getTag();
            this.serializer = serializer;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return serializer;
        }

        @Override
        public void serializeRecipeData(final JsonObject json) {
            super.serializeRecipeData(json);

            if (resultNBT != null) {
                json.getAsJsonObject("result")
                        .addProperty("nbt", resultNBT.toString());
            }
        }
    }
}
