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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class ClassSelectionMenu {

    public static final int SLOT_START = 9;

    public static void openClassSelectionMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 18, Component.text("Class Selection")
            .color(TextColor.color(0xFF00FF)));

        // Create new class button
        ItemStack createButton = new ItemStack(Material.EMERALD);
        ItemMeta createMeta = createButton.getItemMeta();
        createMeta.displayName(Component.text("Create New Class").color(TextColor.color(0x00FF00)));
        List<Component> createLore = new ArrayList<>();
        createLore.add(Component.text("Click to create a new class").color(TextColor.color(0x00FFFF)));
        createMeta.lore(createLore);
        createButton.setItemMeta(createMeta);
        inv.setItem(4, createButton);

        List<ClassDataHolder> classData = PlayerDataManager.get().loadPlayerClassData(player.getUniqueId());
        for (int i = 0; i < classData.size(); i++) {
            ClassDataHolder classDataHolder = classData.get(i);

            ItemStack classButton = new ItemStack(Material.BOOK);
            ItemMeta classMeta = classButton.getItemMeta();
            classMeta.displayName(Component.text("CHANGE LATER").color(TextColor.color(0xFFD700)));
            List<Component> classLore = new ArrayList<>();
            classLore.add(Component.text("Level " + classDataHolder.level).color(TextColor.color(0x00FFFF)));
            classLore.add(Component.text("Exp " + classDataHolder.exp).color(TextColor.color(0x00FFFF)));
            classMeta.lore(classLore);
            classButton.setItemMeta(classMeta);

            inv.setItem(SLOT_START + i, classButton);
        }

        player.openInventory(inv);
    }
    
}
