package com.martinsil.pvzcraft.item;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.block.ModBlocks;
import com.martinsil.pvzcraft.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup PVZCRAFT_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(PvZCraft.MOD_ID, "pvzcraft_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.SUN))
                    .displayName(Text.translatable("itemgroup.pvzcraft.pvzcraft_items"))
                    .entries((displayContext, entries) -> {
                        // Items
                        entries.add(new ItemStack(ModItems.SUN));

                        // Projectiles
                        entries.add(new ItemStack(ModItems.PEA));

                        // Plants
                        entries.add(ModItems.PEASHOOTER);

                        // Zombies
                        entries.add(ModItems.REGULAR_ZOMBIE);

                        // Blocks
                        entries.add(ModBlocks.LAWN_BLOCK_GREEN);
                        entries.add(ModBlocks.LAWN_BLOCK_DARK_GREEN);
                        entries.add(ModBlocks.LAWN_BLOCK_LIGHT_GREEN);
                        entries.add(ModBlocks.FENCE_BOTTOM);
                        entries.add(ModBlocks.FENCE_TOP);
                        entries.add(ModBlocks.DIRT_BLOCK);
                        entries.add(ModBlocks.DIRT_ROCK_BLOCK);

                        // Others
                        entries.add(ModItems.LAWNMOWER);

                    }).build());

    public static void registerItemGroups() {
        PvZCraft.LOGGER.info("Registering Item Groups for " + PvZCraft.MOD_ID);
    }
}
