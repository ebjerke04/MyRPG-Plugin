package com.github.ebjerke04.myrpg.classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.ebjerke04.myrpg.data.PlayerDataManager;
import com.github.ebjerke04.myrpg.util.AdventureToSpigot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class ClassSelectionMenu {

    public static final int SLOT_START = 9;

    public static void openClassSelectionMenu(Player player) {
        Component nameComponent = Component.text("Class Selection").color(TextColor.color(0xFF00FF));
        Inventory inv = Bukkit.createInventory(null, 18, AdventureToSpigot.compToString(nameComponent));

        // Create new class button
        ItemStack createButton = new ItemStack(Material.EMERALD);
        ItemMeta createMeta = createButton.getItemMeta();
        Component dispNameComponent = Component.text("Create New Class").color(TextColor.color(0x00FF00));
        createMeta.setDisplayName(AdventureToSpigot.compToString(dispNameComponent));
        List<String> createLore = new ArrayList<>();
        createLore.add(
            AdventureToSpigot.compToString(Component.text("Click to create a new class").color(TextColor.color(0x00FFFF)))
        );
        createMeta.setLore(createLore);
        createButton.setItemMeta(createMeta);
        inv.setItem(4, createButton);

        List<ClassDataHolder> classData = PlayerDataManager.get().loadPlayerClassData(player.getUniqueId());
        for (int i = 0; i < classData.size(); i++) {
            ClassDataHolder classDataHolder = classData.get(i);

            ItemStack classButton = new ItemStack(Material.BOOK);
            ItemMeta classMeta = classButton.getItemMeta();
            Component classDispName = Component.text(classDataHolder.type.getDisplayName()).color(TextColor.color(0xFFD700));
            classMeta.setDisplayName(AdventureToSpigot.compToString(classDispName));
            List<String> classLore = List.of(
                AdventureToSpigot.compToString(Component.text("Level " + classDataHolder.level).color(TextColor.color(0x00FFFF))),
                AdventureToSpigot.compToString(Component.text("Exp " + classDataHolder.exp).color(TextColor.color(0x00FFFF)))
            );
            classMeta.setLore(classLore);
            classButton.setItemMeta(classMeta);

            inv.setItem(SLOT_START + i, classButton);
        }

        player.openInventory(inv);
    }
    
}