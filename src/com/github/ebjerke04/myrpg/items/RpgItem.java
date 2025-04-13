package com.github.ebjerke04.myrpg.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

public class RpgItem {

    private ItemStack itemStack;

    public RpgItem(Component itemName, List<Component> itemLore) {
        itemStack = new ItemStack(Material.DIAMOND, 1);

        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(itemName);
        meta.lore(itemLore);
        itemStack.setItemMeta(meta);
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }
    
}
