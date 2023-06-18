package com.black_dog20.bml.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Base class for loot table providers.
 * Exposes helper methods.
 *
 * @author black_dog20
 */
public abstract class BaseLootTableProvider extends LootTableProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    private final PackOutput packOutput;

    /**
     * The constructor for the provider.
     *
     * @param packOutput the pack output.
     */
    public BaseLootTableProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), VanillaLootTableProvider.create(packOutput).getTables());
        this.packOutput = packOutput;
    }

    /**
     * Method where the loot tables should be added.
     */
    protected abstract void addTables();

    /**
     * Creates a standard loot table to drop the block when the block is destroyed.
     *
     * @param name  the name of the loot table.
     * @param block the block to be dropped from itself.
     * @param type  the block entity type
     * @return LootTable.Builder.
     */
    protected LootTable.Builder createStandardTable(String name, Block block, BlockEntityType<?> type) {
        LootPool.Builder builder = LootPool.lootPool()
                //.name(name)
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                .copy("inv", "BlockEntityTag.inv", CopyNbtFunction.MergeStrategy.REPLACE)
                                .copy("energy", "BlockEntityTag.energy", CopyNbtFunction.MergeStrategy.REPLACE))
                        .apply(SetContainerContents.setContents(type)
                                .withEntry(DynamicLoot.dynamicEntry(new ResourceLocation("minecraft", "contents"))))
                );
        return LootTable.lootTable().withPool(builder);
    }

    /**
     * Creates a standard loot table to drop the block when the block is destroyed.
     *
     * @param block the block to be dropped from itself.
     * @param type  the block entity type
     * @return LootTable.Builder.
     */
    protected LootTable.Builder createStandardTable(Block block, BlockEntityType<?> type) {
        return createStandardTable(ForgeRegistries.BLOCKS.getKey(block).getPath(), block, type);
    }
}

