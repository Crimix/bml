package com.black_dog20.bml.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

/**
 * Base class for blockstate providers.
 * Exposes helper methods and builders.
 *
 * @author black_dog20
 */
public abstract class BaseBlockStateProvider extends BlockStateProvider {

    /**
     * The constructor for the provider.
     *
     * @param gen          the data generator.
     * @param modid        the mod id.
     * @param exFileHelper the existing file helper.
     */
    public BaseBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }


    /**
     * Builds a block with the same texture on all sides.
     * The name of the texture should be the same as the block.
     *
     * @param block the block.
     */
    protected void buildCubeAll(Block block) {
        getVariantBuilder(block).forAllStates(state ->
                ConfiguredModel.builder().modelFile(cubeAll(block)).build()
        );
    }

    /**
     * Factory for MachineBuilder.
     *
     * @param block the block.
     * @return MachineBuilder.
     */
    protected MachineBuilder machineFromBlock(Block block) {
        return new MachineBuilder(block);
    }

    /**
     * Utility class to create side dependent textures.
     */
    protected class MachineBuilder {

        private final Block block;
        private ResourceLocation left;
        private ResourceLocation right;
        private ResourceLocation back;
        private ResourceLocation front;
        private ResourceLocation top;
        private ResourceLocation bottom;

        public MachineBuilder(Block block) {
            this.block = block;
        }

        public MachineBuilder withLeft(ResourceLocation left) {
            this.left = left;
            return this;
        }

        public MachineBuilder withRight(ResourceLocation right) {
            this.right = right;
            return this;
        }

        public MachineBuilder withBack(ResourceLocation back) {
            this.back = back;
            return this;
        }

        public MachineBuilder withFront(ResourceLocation front) {
            this.front = front;
            return this;
        }

        public MachineBuilder withTop(ResourceLocation top) {
            this.top = top;
            return this;
        }

        public MachineBuilder withBottom(ResourceLocation bottom) {
            this.bottom = bottom;
            return this;
        }

        public MachineBuilder withAllSides(ResourceLocation side) {
            this.top = side;
            this.bottom = side;
            this.front = side;
            this.back = side;
            this.left = side;
            this.right = side;
            return this;
        }

        public void buildHorizontal() {
            ModelFile model = models().cube(
                    block.getRegistryName().getPath(),
                    bottom,
                    top,
                    front, back, left, right
            ).texture("particle", back);
            horizontalBlock(block, $ -> model);
        }
    }
}
