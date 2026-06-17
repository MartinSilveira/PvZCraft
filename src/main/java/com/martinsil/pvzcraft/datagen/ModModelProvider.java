package com.martinsil.pvzcraft.datagen;

import com.martinsil.pvzcraft.block.ModBlocks;
import com.martinsil.pvzcraft.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LAWN_BLOCK_GREEN);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LAWN_BLOCK_DARK_GREEN);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LAWN_BLOCK_LIGHT_GREEN);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DIRT_ROCK_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DIRT_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SUN, Models.GENERATED);
        itemModelGenerator.register(ModItems.PEA, Models.GENERATED);
    }
}
