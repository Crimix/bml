package com.black_dog20.bml.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.NBTIngredient;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Special class that makes it possible to use NBT sensitive ingredients.
 *
 * @author black_dog20
 */
public class IngredientNBT extends NBTIngredient {

    protected ItemStack stack;

    protected IngredientNBT(ItemStack stack) {
        super(stack);
        this.stack = stack;
    }

    /**
     * Creates a IngredientNBT from an ItemStack.
     *
     * @param stack The itemStack to create the ingredient from.
     * @return the IngredientNBT from that itemStack.
     */
    public static IngredientNBT fromNBTStack(ItemStack stack) {
        return new IngredientNBT(stack);
    }

    /**
     * Specialized test method to make sure the ingredient is equal to the input.
     *
     * @param input the input itemStack to test the ingredient against.
     * @return true if the input is equal to the ingredient, otherwise false.
     */
    @Override
    public boolean test(@Nullable ItemStack input) {
        if (input == null)
            return false;
        //Can't use areItemStacksEqualUsingNBTShareTag because it compares stack size as well
        return this.stack.getItem() == input.getItem() && this.stack.getDamage() == input.getDamage() && areInputTagsInStackTage(this.stack.getShareTag(), input.getShareTag());

    }

    private static boolean areInputTagsInStackTage(CompoundNBT stackTag, CompoundNBT inputTag) {
        boolean res = true;
        Set<String> stackKeySet = stackTag.keySet();
        for (String key : stackKeySet) {
            if (!stackTag.get(key).equals(inputTag.get(key))) {
                return false;
            }
        }

        return res;
    }

    /**
     * @return the specialized serializer for this ingredient type.
     */
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    /**
     * Serializes the ingredient to a json element.
     *
     * @return The serialized ingredient.
     */
    @Override
    public JsonElement serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
        json.addProperty("item", stack.getItem().getRegistryName().toString());
        json.addProperty("count", stack.getCount());
        if (stack.hasTag())
            json.addProperty("nbt", stack.getTag().toString());
        return json;
    }

    /**
     * The specialized serializer for this ingredient type.
     */
    public static class Serializer implements IIngredientSerializer<IngredientNBT> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public IngredientNBT parse(PacketBuffer buffer) {
            return new IngredientNBT(buffer.readItemStack());
        }

        @Override
        public IngredientNBT parse(JsonObject json) {
            return new IngredientNBT(CraftingHelper.getItemStack(json, true));
        }

        @Override
        public void write(PacketBuffer buffer, IngredientNBT ingredient) {
            buffer.writeItemStack(ingredient.stack);
        }
    }


}
