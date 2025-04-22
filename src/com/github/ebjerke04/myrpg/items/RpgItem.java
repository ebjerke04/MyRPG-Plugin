package com.github.ebjerke04.myrpg.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

public class RpgItem {

    private final int MINIMUM_LEVEL;

    private ItemStack itemStack;

    public RpgItem(RpgItemDataHolder itemData) {
        itemStack = new ItemStack(itemData.itemMaterial, 1);

        // TODO: Eventually change this to include formatting color and other stuff...
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text(itemData.displayName));
        List<Component> loreComponents = new ArrayList<>();

        this.MINIMUM_LEVEL = itemData.minimumLevel;
        loreComponents.add(Component.text("Minimum Level: " + this.MINIMUM_LEVEL));

        // TODO: To loreComponents List add modifiers and other stuff in the future...

        // Actually lore of item
        loreComponents.add(Component.text("Lore:"));
        for (String loreString : itemData.lore) {
            loreComponents.add(Component.text(loreString));
        }

        meta.lore(loreComponents);
        itemStack.setItemMeta(meta);
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }
    
}
