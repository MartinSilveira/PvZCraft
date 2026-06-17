package com.martinsil.pvzcraft.block;

import com.martinsil.pvzcraft.PvZCraft;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block LAWN_BLOCK_GREEN = registerBlock("lawn_block_green", new Block(AbstractBlock.Settings.create()
            .strength(0.6F).sounds(BlockSoundGroup.GRASS)));
    public static final Block LAWN_BLOCK_DARK_GREEN = registerBlock("lawn_block_dark_green", new Block(AbstractBlock.Settings.create()
            .strength(0.6F).sounds(BlockSoundGroup.GRASS)));
    public static final Block LAWN_BLOCK_LIGHT_GREEN = registerBlock("lawn_block_light_green", new Block(AbstractBlock.Settings.create()
            .strength(0.6F).sounds(BlockSoundGroup.GRASS)));
    public static final Block DIRT_BLOCK = registerBlock("dirt_block", new Block(AbstractBlock.Settings.create()
            .strength(0.6F).sounds(BlockSoundGroup.GRASS)));
    public static final Block DIRT_ROCK_BLOCK = registerBlock("dirt_rock_block", new Block(AbstractBlock.Settings.create()
            .strength(0.6F).sounds(BlockSoundGroup.GRASS)));

    // If I'm facing South, block is facing North
    public static final Block FENCE_BOTTOM = registerBlock("fence_bottom", new FacingBlock(
            AbstractBlock.Settings.create().strength(0.6F).nonOpaque().sounds(BlockSoundGroup.WOOD),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 4.0),  // North
            Block.createCuboidShape(0.0, 0.0, 12.0, 16.0, 16.0, 16.0), // South
            Block.createCuboidShape(12.0, 0.0, 0.0, 16.0, 16.0, 16.0), // East
            Block.createCuboidShape(0.0, 0.0, 0.0, 4.0, 16.0, 16.0)    // West
    ));

    public static final Block FENCE_TOP = registerBlock("fence_top", new FacingBlock(
            AbstractBlock.Settings.create().strength(0.6F).nonOpaque().sounds(BlockSoundGroup.WOOD),
            Block.createCuboidShape(6.0, 0.0, 0.0, 16.0, 6.0, 4.0),  // North
            Block.createCuboidShape(0.0, 0.0, 12.0, 10.0, 6.0, 16.0), // South
            Block.createCuboidShape(12.0, 0.0, 6.0, 16.0, 6.0, 16.0), // East
            Block.createCuboidShape(0.0, 0.0, 0.0, 4.0, 6.0, 10.0)    // West
    ));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(PvZCraft.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(PvZCraft.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        PvZCraft.LOGGER.info("Registering Mod Blocks for " + PvZCraft.MOD_ID);
    }
}
