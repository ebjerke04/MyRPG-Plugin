package com.github.ebjerke04.myrpg.economy;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.ebjerke04.myrpg.quests.NPCDataHolder;
import com.github.ebjerke04.myrpg.util.AdventureToSpigot;
import com.github.ebjerke04.myrpg.world.NPC;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class BankerNPC extends NPC {

    public BankerNPC(Location spawnLoc) {
        super(new NPCDataHolder() {{
            this.name = "Banker";
            this.location = spawnLoc;
        }});
    }

    @Override
    public void rightClicked(Player player) {
        Component inventoryTitle = Component.text("Currency Exchange").color(TextColor.color(0x00FF00));
        Inventory inventory = Bukkit.createInventory(null, 9 * 1, AdventureToSpigot.compToString(inventoryTitle));

        ItemStack copperFromIron = new ItemStack(Material.COPPER_INGOT, 64);
        ItemMeta cfiMeta = copperFromIron.getItemMeta();
        cfiMeta.setDisplayName(AdventureToSpigot.compToString(Component.text("Copper")
            .color(TextColor.color(0x00FFFF))));
        cfiMeta.setLore(List.of(
            AdventureToSpigot.compToString(Component.text("Cost: 1 iron").color(TextColor.color(0xFF00FF)))
        ));
        copperFromIron.setItemMeta(cfiMeta);
        inventory.addItem(copperFromIron);

        ItemStack ironFromCopper = new ItemStack(Material.IRON_INGOT, 1);
        ItemMeta ifcMeta = ironFromCopper.getItemMeta();
        ifcMeta.setDisplayName(AdventureToSpigot.compToString(Component.text("Iron")
            .color(TextColor.color(0x00FFFF))));
        ifcMeta.setLore(List.of(
            AdventureToSpigot.compToString(Component.text("Cost: 64 copper").color(TextColor.color(0xFF00FF)))
        ));
        ironFromCopper.setItemMeta(ifcMeta);
        inventory.addItem(ironFromCopper);

        ItemStack ironFromGold = new ItemStack(Material.IRON_INGOT, 64);
        ItemMeta ifgMeta = ironFromGold.getItemMeta();
        ifgMeta.setDisplayName(AdventureToSpigot.compToString(Component.text("Iron")
            .color(TextColor.color(0x00FFFF))));
        ifgMeta.setLore(List.of(
            AdventureToSpigot.compToString(Component.text("Cost: 1 gold").color(TextColor.color(0xFF00FF)))
        ));
        ironFromGold.setItemMeta(ifgMeta);
        inventory.addItem(ironFromGold);

        ItemStack goldFromIron = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta gfiMeta = goldFromIron.getItemMeta();
        gfiMeta.setDisplayName(AdventureToSpigot.compToString(Component.text("Gold")
            .color(TextColor.color(0x00FFFF))));
        gfiMeta.setLore(List.of(
            AdventureToSpigot.compToString(Component.text("Cost: 64 iron").color(TextColor.color(0xFF00FF)))
        ));
        goldFromIron.setItemMeta(gfiMeta);
        inventory.addItem(goldFromIron);
        
        player.openInventory(inventory);
    }
    
}
