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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

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
		
		UUID playerId = player.getUniqueId();
		RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);
		
		List<QuestInProgress> questsInProgress = rpgPlayer.getQuestsInProgress();
		for (QuestInProgress questInProgress : questsInProgress) {
			String questName = questInProgress.getName();
			String stepDescription = questInProgress.getCurrentStep().getDescription();

			ItemStack questIcon = new ItemStack(Material.LIME_WOOL, 1);
			ItemMeta iconMeta = questIcon.getItemMeta();
			iconMeta.displayName(Component.text(questName).color(TextColor.color(0x00FF00)));
			iconMeta.lore(List.of(
				Component.text(stepDescription),
				Component.text("Started"),
				Component.text("Left-click to track")
			));
			questIcon.setItemMeta(iconMeta);

			inventory.addItem(questIcon);
		}

		List<Quest> allQuests = Plugin.getWorldManager().getQuests();
		for (Quest quest : allQuests) {
			if (!isQuestInProgress(quest, questsInProgress)) {
				String questName = quest.getName();
				String stepDescription = quest.getSteps().peek().getDescription();
				int minLevel = quest.getMinLevel();

				ItemStack questIcon = new ItemStack(Material.GREEN_WOOL, 1);
				ItemMeta iconMeta = questIcon.getItemMeta();
				iconMeta.displayName(Component.text(questName).color(TextColor.color(0x00FF00)));
				iconMeta.lore(List.of(
					Component.text(stepDescription),
					Component.text("Minimum level to start: " + minLevel),
					Component.text("Left-click to track")
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
	
}
