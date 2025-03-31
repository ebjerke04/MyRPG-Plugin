package com.github.ebjerke04.myrpg.quests.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.players.RpgPlayer;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestInProgress;
import com.github.ebjerke04.myrpg.util.AdventureToSpigot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class QuestBook {

	public static void addToPlayerInventory(Player player) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		ItemMeta meta = book.getItemMeta();
		
		Component dispName = Component.text("Quest Book");
		meta.setDisplayName(AdventureToSpigot.compToString(dispName));
		List<String> lore = List.of(
			AdventureToSpigot.compToString(Component.text("Right-click to view quests!"))
		);
		meta.setLore(lore);
		book.setItemMeta(meta);
		
		player.getInventory().setItem(8, book);
	}
	
	public static void open(Player player) {
		Component title = Component.text("Quest Book");
		Inventory inventory = Bukkit.createInventory(null, 9 * 6, AdventureToSpigot.compToString(title));
		
		UUID playerId = player.getUniqueId();
		RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);
		
		List<QuestInProgress> questsInProgress = rpgPlayer.getQuestsInProgress();
		for (QuestInProgress questInProgress : questsInProgress) {
			String questName = questInProgress.getName();
			String stepDescription = questInProgress.getCurrentStep().getDescription();

			ItemStack questIcon = new ItemStack(Material.LIME_WOOL, 1);
			ItemMeta iconMeta = questIcon.getItemMeta();
			iconMeta.setDisplayName(AdventureToSpigot.compToString(Component.text(questName).color(TextColor.color(0x00FF00))));
			iconMeta.setLore(List.of(
				AdventureToSpigot.compToString(Component.text(stepDescription).color(TextColor.color(0xFF0000))),
				AdventureToSpigot.compToString(Component.text("Started").color(TextColor.color(0x0000FF))),
				AdventureToSpigot.compToString(Component.text("Left-click to track").color(TextColor.color(0xFFFF00)))
			));
			questIcon.setItemMeta(iconMeta);

			inventory.addItem(questIcon);
		}

		List<Quest> allQuests = Plugin.getWorldManager().getQuests();
		for (Quest quest : allQuests) {
			if (rpgPlayer.getActiveClass() == null) break;
			if (!isQuestInProgress(quest, questsInProgress) && !isQuestComplete(quest, rpgPlayer.getActiveClass().getQuestsCompleted())) {
				String questName = quest.getName();
				String stepDescription = quest.getSteps().peek().getDescription();
				int minLevel = quest.getMinLevel();

				Material iconColor = Material.RED_WOOL;
				int playerLevel = 10; // TODO: design system to fetch player level
				if (playerLevel >= minLevel) iconColor = Material.GREEN_WOOL;
				
				ItemStack questIcon = new ItemStack(iconColor, 1);
				ItemMeta iconMeta = questIcon.getItemMeta();
				iconMeta.setDisplayName(AdventureToSpigot.compToString(Component.text(questName).color(TextColor.color(0x00FF00))));
				iconMeta.setLore(List.of(
					AdventureToSpigot.compToString(Component.text(stepDescription).color(TextColor.color(0xFF0000))),
					AdventureToSpigot.compToString(Component.text("Min: " + minLevel + "lvl").color(TextColor.color(0x0000FF))),
					AdventureToSpigot.compToString(Component.text("Left-click to track").color(TextColor.color(0xFFFF00)))
				));
				questIcon.setItemMeta(iconMeta);

				inventory.addItem(questIcon);
			}
		}

		player.openInventory(inventory);
	}

	private static boolean isQuestInProgress(Quest quest, List<QuestInProgress> questsInProgresses) {
		for (QuestInProgress questInProgress : questsInProgresses) {
			if (quest.getUniqueId().equals(questInProgress.getRespectiveId())) return true;
		}

		return false;
	}

	private static boolean isQuestComplete(Quest quest, List<String> questsComplete) {
		return questsComplete.contains(quest.getName());
	}
	
}
