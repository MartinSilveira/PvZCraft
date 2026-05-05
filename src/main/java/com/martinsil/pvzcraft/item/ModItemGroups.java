package com.martinsil.pvzcraft.item;

import com.martinsil.pvzcraft.PvZCraft;
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
                        entries.add(new ItemStack(ModItems.SUN));

                        entries.add(new ItemStack(ModItems.PEA));

                        entries.add(new ItemStack(ModItems.PEASHOOTER));
                    }).build());

    public static void registerItemGroups() {
        PvZCraft.LOGGER.info("Registering Item Groups for " + PvZCraft.MOD_ID);
    }



}
