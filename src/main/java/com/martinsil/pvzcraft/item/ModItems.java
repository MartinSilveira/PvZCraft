package com.martinsil.pvzcraft.item;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.ModEntities;
import com.martinsil.pvzcraft.item.custom.PeaItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item SUN = registerItem("sun", new Item(new Item.Settings()));

    public static final Item PEA = registerItem("pea", new PeaItem(new Item.Settings()));

    public static final Item PEASHOOTER = registerItem("peashooter", new PlantItem(ModEntities.PEASHOOTER, new Item.Settings()));

    public static final Item REGULAR_ZOMBIE = registerItem("regular_zombie", new ZombieItem(ModEntities.REGULAR_ZOMBIE, new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(PvZCraft.MOD_ID, name), item);
    }


    public static void registerModItems() {
        PvZCraft.LOGGER.info("Registering Mod Items for " + PvZCraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(SUN);
        });
    }
}
