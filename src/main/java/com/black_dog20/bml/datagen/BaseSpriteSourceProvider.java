package com.black_dog20.bml.datagen;

import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

import java.util.Optional;

public abstract class BaseSpriteSourceProvider extends SpriteSourceProvider {

    /**
     * The constructor for the provider.
     *
     * @param packOutput   the pack output.
     * @param modid              the mod id.
     * @param existingFileHelper the existing file helper.
     */
    public BaseSpriteSourceProvider(PackOutput packOutput, String modid, ExistingFileHelper existingFileHelper) {
        super(packOutput, existingFileHelper, modid);
    }

    /**
     * Adds a block atlas
     * @param path the path to the
     */
    protected void blockAtlas(String path) {
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new SingleFile(new ResourceLocation(modid, path), Optional.empty()));
    }
}
