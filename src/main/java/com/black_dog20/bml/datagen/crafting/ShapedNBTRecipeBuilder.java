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
import net.minecraft.data.recipes.ShapedRecipeBuilder;
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A recipe builder for {@link com.black_dog20.bml.crafting.ShapedNBTRecipe} for use in data generator.
 *
 * @author black_dog20
 */
public class ShapedNBTRecipeBuilder extends ShapedRecipeBuilder {
    private static final Method VALIDATE = ObfuscationReflectionHelper.findMethod(ShapedRecipeBuilder.class, "ensureValid" /* validate */, ResourceLocation.class);
    private static final Field ADVANCEMENT_BUILDER = ObfuscationReflectionHelper.findField(ShapedRecipeBuilder.class, "advancement" /* advancementBuilder */);
    private static final Field GROUP = ObfuscationReflectionHelper.findField(ShapedRecipeBuilder.class, "group" /* group */);
    private static final Field PATTERN = ObfuscationReflectionHelper.findField(ShapedRecipeBuilder.class, "rows" /* pattern */);
    private static final Field KEY = ObfuscationReflectionHelper.findField(ShapedRecipeBuilder.class, "key" /* key */);

    private final ItemStack result;
    private final RecipeSerializer<?> serializer;
    private String itemGroup;

    private ShapedNBTRecipeBuilder(final ItemStack result) {
        super(result.getItem(), result.getCount());
        this.result = result;
        this.serializer = BmlCrafting.SHAPED_NBT.get();
    }

    private ShapedNBTRecipeBuilder(final ItemStack result, final RecipeSerializer<?> serializer) {
        super(result.getItem(), result.getCount());
        this.result = result;
        this.serializer = serializer;
    }

    /**
     * Factory for recipe builder.
     *
     * @param result the end result of the recipe with its NBT data.
     * @return ShapedNBTRecipeBuilder.
     */
    public static ShapedNBTRecipeBuilder shapedNBTRecipe(final ItemStack result) {
        return new ShapedNBTRecipeBuilder(result);
    }

    /**
     * Factory for recipe builder.
     *
     * @param serializer the custom serializer to use.
     * @param result     the end result of the recipe with its NBT data.
     * @return ShapedNBTRecipeBuilder.
     */
    public static ShapedNBTRecipeBuilder customShapedNBTRecipe(RecipeSerializer<?> serializer, final ItemStack result) {
        return new ShapedNBTRecipeBuilder(result, serializer);
    }

    /**
     * Sets the itemGroup for the recipe.
     *
     * @param group the item group.
     * @return ShapedNBTRecipeBuilder.
     */
    public ShapedNBTRecipeBuilder setItemGroup(final String group) {
        itemGroup = group;
        return this;
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param symbol the ingredient symbol eg. 'd' for diamonds.
     * @param tag    the item tag for the recipe ingredient.
     * @return ShapedNBTRecipeBuilder
     */
    @Override
    public ShapedNBTRecipeBuilder define(final Character symbol, final TagKey<Item> tag) {
        return (ShapedNBTRecipeBuilder) super.define(symbol, tag);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param symbol the ingredient symbol eg. 'd' for diamonds.
     * @param item   the item for the recipe ingredient.
     * @return ShapedNBTRecipeBuilder
     */
    @Override
    public ShapedNBTRecipeBuilder define(final Character symbol, final ItemLike item) {
        return (ShapedNBTRecipeBuilder) super.define(symbol, item);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param symbol     the ingredient symbol eg. 'd' for diamonds.
     * @param ingredient the ingredient for the recipe ingredient.
     * @return ShapedNBTRecipeBuilder
     */
    @Override
    public ShapedNBTRecipeBuilder define(final Character symbol, final Ingredient ingredient) {
        return (ShapedNBTRecipeBuilder) super.define(symbol, ingredient);
    }

    /**
     * Adds a pattern line to the recipe.
     *
     * @param pattern the patter, eg. ddd or dd (if only two rows)
     * @return ShapedNBTRecipeBuilder
     */
    @Override
    public ShapedNBTRecipeBuilder pattern(final String pattern) {
        return (ShapedNBTRecipeBuilder) super.pattern(pattern);
    }

    /**
     * Adds a criterion for the recipe to be unlocked.
     *
     * @param name      the name of the criterion.
     * @param criterion the criterion.
     * @return ShapedNBTRecipeBuilder
     */
    @Override
    public ShapedNBTRecipeBuilder unlockedBy(final String name, final CriterionTriggerInstance criterion) {
        return (ShapedNBTRecipeBuilder) super.unlockedBy(name, criterion);
    }

    /**
     * Sets the group of the recipe.
     *
     * @param group the group.
     * @return ShapedNBTRecipeBuilder
     */
    @Override
    public ShapedNBTRecipeBuilder group(final String group) {
        return (ShapedNBTRecipeBuilder) super.group(group);
    }


    /**
     * Builds the recipe.
     *
     * @param consumer the consumer.
     */
    @Override
    public void save(final Consumer<FinishedRecipe> consumer) {
        save(consumer, result.getItem().getRegistryName());
    }

    /**
     * Builds the recipe.
     *
     * @param consumer the consumer.
     * @param save     the name of the recipe for use in saving it.
     */
    @Override
    public void save(final Consumer<FinishedRecipe> consumer, final String save) {
        final ResourceLocation registryName = result.getItem().getRegistryName();
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

            @SuppressWarnings("unchecked") final List<String> pattern = (List<String>) PATTERN.get(this);

            @SuppressWarnings("unchecked") final Map<Character, Ingredient> key = (Map<Character, Ingredient>) KEY.get(this);

            String itemGroupName = itemGroup;
            if (itemGroupName == null) {
                final CreativeModeTab itemGroup = Preconditions.checkNotNull(result.getItem().getItemCategory());
                itemGroupName = itemGroup.getRecipeFolderName();
            }

            final ResourceLocation advancementID = new ResourceLocation(id.getNamespace(), "recipes/" + itemGroupName + "/" + id.getPath());

            consumer.accept(new Result(id, result, group, pattern, key, advancementBuilder, advancementID, serializer));
        } catch (final IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to build Shaped NBT Recipe " + id, e);
        }
    }

    /**
     * Specialized result class for shaped recipes using NBT data.
     */
    public class Result extends ShapedRecipeBuilder.Result {
        private final CompoundTag resultNBT;
        private final RecipeSerializer<?> serializer;

        private Result(final ResourceLocation id, final ItemStack result, final String group, final List<String> pattern, final Map<Character, Ingredient> key, final Advancement.Builder advancementBuilder, final ResourceLocation advancementID, final RecipeSerializer<?> serializer) {
            super(id, result.getItem(), result.getCount(), group, pattern, key, advancementBuilder, advancementID);
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
