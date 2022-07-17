package com.black_dog20.bml.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Base class for item model providers.
 * Exposes helper methods.
 *
 * @author black_dog20
 */
public abstract class BaseItemModelProvider extends ItemModelProvider {

    /**
     * The constructor for the provider.
     *
     * @param generator          the data generator.
     * @param modid              the mod id.
     * @param existingFileHelper the existing file helper.
     */
    public BaseItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    /**
     * Registers a block model from a block.
     *
     * @param block the block.
     */
    protected void registerBlockModel(Block block) {
        String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
    }

    /**
     * Registers a item model from an item.
     *
     * @param item the item.
     */
    protected void registerItemModel(Item item) {
        String path = ForgeRegistries.ITEMS.getKey(item).getPath();
        singleTexture(path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
    }
}
