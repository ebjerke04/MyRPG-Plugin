package com.github.ebjerke04.myrpg.quests.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.quests.Quest;

import net.kyori.adventure.text.Component;

public class QuestBook {

	public static void addToPlayerInventory(Player player) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		ItemMeta meta = book.getItemMeta();
		
		meta.displayName(Component.text("Quest Book"));
		List<Component> lore = new ArrayList<>();
		lore.add(Component.text("Right-click to view quests!"));
		meta.lore(lore);
		book.setItemMeta(meta);
		
		player.getInventory().setItem(8, book);
	}
	
	public static void open(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 9 * 6, Component.text("Quest Book"));
		
		for (int i = 0; i < Plugin.getQuestManager().getQuests().size(); i++) {
			Quest quest = Plugin.getQuestManager().getQuests().get(i);
			
			int playerLevel = 3; // somehow get player level
			boolean questActive = (i == 1) ? true : false;
			
			int questMinLevel = quest.getMinLevel();
			
			Material material = Material.WHITE_WOOL;
			if (playerLevel < questMinLevel) material = Material.RED_WOOL;
			else if (playerLevel >= questMinLevel) material = Material.GREEN_WOOL;
			
			if (questActive) material = Material.CYAN_WOOL;
			
			ItemStack questItem = new ItemStack(material);
			ItemMeta questItemMeta = questItem.getItemMeta();
			
			Component itemName = Component.text(quest.getName());
			questItemMeta.displayName(itemName);
			List<Component> lore = new ArrayList<>();
			lore.add(Component.text("Level needed: " + questMinLevel));
			questItemMeta.lore(lore);
			questItem.setItemMeta(questItemMeta);
			
			inventory.setItem(i, questItem);
		}
		
		player.openInventory(inventory);
	}
	
}
