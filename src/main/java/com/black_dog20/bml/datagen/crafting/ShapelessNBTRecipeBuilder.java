package com.black_dog20.bml.datagen.crafting;

import com.black_dog20.bml.init.BmlCrafting;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

public class ShapelessNBTRecipeBuilder extends ShapelessRecipeBuilder {
    private static final Method VALIDATE = ObfuscationReflectionHelper.findMethod(ShapelessRecipeBuilder.class, "func_200481_a" /* validate */, ResourceLocation.class);
    private static final Field ADVANCEMENT_BUILDER = ObfuscationReflectionHelper.findField(ShapelessRecipeBuilder.class, "field_200497_e" /* advancementBuilder */);
    private static final Field GROUP = ObfuscationReflectionHelper.findField(ShapelessRecipeBuilder.class, "field_200498_f" /* group */);
    private static final Field INGREDIENTS = ObfuscationReflectionHelper.findField(ShapelessRecipeBuilder.class, "field_200496_d" /* ingredients */);

    private final ItemStack result;
    private final IRecipeSerializer<?> serializer;
    private String itemGroup;

    private ShapelessNBTRecipeBuilder(final ItemStack result) {
        super(result.getItem(), result.getCount());
        this.result = result;
        this.serializer = BmlCrafting.SHAPELESS_NBT.get();
    }

    private ShapelessNBTRecipeBuilder(final ItemStack result, final IRecipeSerializer<?> serializer) {
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
    public static ShapelessNBTRecipeBuilder customShapelessNBTRecipe(final ItemStack result, final IRecipeSerializer<?> serializer) {
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
    public ShapelessNBTRecipeBuilder addIngredient(ITag<Item> tag) {
        return (ShapelessNBTRecipeBuilder) super.addIngredient(tag);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param item the item for the recipe ingredient.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder addIngredient(IItemProvider item) {
        return (ShapelessNBTRecipeBuilder) super.addIngredient(item);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param item  the item for the recipe ingredient.
     * @param count how many of the items is in the recipe, many used for storage block crafting.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder addIngredient(IItemProvider item, int count) {
        return (ShapelessNBTRecipeBuilder) super.addIngredient(item, count);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param ingredient the ingredient for the recipe ingredient.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder addIngredient(Ingredient ingredient) {
        return (ShapelessNBTRecipeBuilder) super.addIngredient(ingredient);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param ingredient the ingredient for the recipe ingredient.
     * @param count      how many of the items is in the recipe, many used for storage block crafting.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder addIngredient(Ingredient ingredient, int count) {
        return (ShapelessNBTRecipeBuilder) super.addIngredient(ingredient, count);
    }

    /**
     * Adds a criterion for the recipe to be unlocked.
     *
     * @param name      the name of the criterion.
     * @param criterion the criterion.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder addCriterion(final String name, final ICriterionInstance criterion) {
        return (ShapelessNBTRecipeBuilder) super.addCriterion(name, criterion);
    }

    /**
     * Sets the group of the recipe.
     *
     * @param group the group.
     * @return ShapelessNBTRecipeBuilder
     */
    @Override
    public ShapelessNBTRecipeBuilder setGroup(final String group) {
        return (ShapelessNBTRecipeBuilder) super.setGroup(group);
    }

    /**
     * Builds the recipe.
     *
     * @param consumer the consumer.
     */
    @Override
    public void build(final Consumer<IFinishedRecipe> consumer) {
        build(consumer, result.getItem().getRegistryName());
    }

    /**
     * Builds the recipe.
     *
     * @param consumer the consumer.
     * @param save     the name of the recipe for use in saving it.
     */
    @Override
    public void build(final Consumer<IFinishedRecipe> consumer, final String save) {
        final ResourceLocation registryName = result.getItem().getRegistryName();
        if (new ResourceLocation(save).equals(registryName)) {
            throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument");
        } else {
            build(consumer, new ResourceLocation(save));
        }
    }

    /**
     * Builds the recipe.
     *
     * @param consumer the consumer.
     * @param id       the id of the recipe for use in saving it.
     */
    @Override
    public void build(final Consumer<IFinishedRecipe> consumer, final ResourceLocation id) {
        try {
            // Perform the super class's validation
            VALIDATE.invoke(this, id);

            final Advancement.Builder advancementBuilder = ((Advancement.Builder) ADVANCEMENT_BUILDER.get(this))
                    .withParentId(new ResourceLocation("minecraft", "recipes/root"))
                    .withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id))
                    .withRewards(AdvancementRewards.Builder.recipe(id))
                    .withRequirementsStrategy(IRequirementsStrategy.OR);

            String group = (String) GROUP.get(this);
            if (group == null) {
                group = "";
            }

            String itemGroupName = itemGroup;
            if (itemGroupName == null) {
                final ItemGroup itemGroup = Preconditions.checkNotNull(result.getItem().getGroup());
                itemGroupName = itemGroup.getPath();
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
        private final CompoundNBT resultNBT;
        private final IRecipeSerializer<?> serializer;

        private Result(final ResourceLocation id, final ItemStack result, final String group, final List<Ingredient> ingredients, final Advancement.Builder advancementBuilder, final ResourceLocation advancementID, final IRecipeSerializer<?> serializer) {
            super(id, result.getItem(), result.getCount(), group, ingredients, advancementBuilder, advancementID);
            resultNBT = result.getTag();
            this.serializer = serializer;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return serializer;
        }

        @Override
        public void serialize(final JsonObject json) {
            super.serialize(json);

            if (resultNBT != null) {
                json.getAsJsonObject("result")
                        .addProperty("nbt", resultNBT.toString());
            }
        }
    }
}
