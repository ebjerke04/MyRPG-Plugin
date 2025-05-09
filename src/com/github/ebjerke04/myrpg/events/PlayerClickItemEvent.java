package com.github.ebjerke04.myrpg.events;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.classes.ClassDataHolder;
import com.github.ebjerke04.myrpg.classes.ClassSelectionMenu;
import com.github.ebjerke04.myrpg.data.PlayerDataManager;
import com.github.ebjerke04.myrpg.economy.CurrencyConversionType;
import com.github.ebjerke04.myrpg.players.RpgPlayer;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerClickItemEvent extends BaseEvent {

	public PlayerClickItemEvent() {
		super();
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerClickItem(InventoryClickEvent event) {
		Component inventoryTitle = event.getView().title();
		String titleString = PlainTextComponentSerializer.plainText().serialize(inventoryTitle);

		if (titleString.equals("Class Selection")) {
			event.setCancelled(true);
			if (event.getCurrentItem() == null) return;
			
			ItemStack clickedItem = event.getCurrentItem();
			Player player = (Player) event.getWhoClicked();
			
			if (clickedItem.getType() == Material.EMERALD) {
				// Handle create new class button click
				player.closeInventory();
				// TODO: Open class creation menu
				Logging.sendConsole(Level.INFO, "Class creation button clicked");
			} else if (clickedItem.getType() == Material.BOOK) {
				// Handle existing class selection
				//String className = clickedItem.getItemMeta().displayName().toString();
				player.closeInventory();
				
				int clickedSlot = event.getSlot();
				int classIndex = clickedSlot - ClassSelectionMenu.SLOT_START;

				List<ClassDataHolder> classData = PlayerDataManager.get().loadPlayerClassData(player.getUniqueId());
				ClassDataHolder clickedClassData = classData.get(classIndex);
				
				Plugin.getPlayerManager().handleClassSelection(player, clickedClassData);
			}
		} else if (titleString.equals("Currency Exchange")) {
			event.setCancelled(true);
			if (event.getCurrentItem() == null) return;

			ItemStack clickedItem = event.getCurrentItem();
			Player player = (Player) event.getWhoClicked();

			Material itemType = clickedItem.getType();
			int itemCount = clickedItem.getAmount();
			CurrencyConversionType type = CurrencyConversionType.fromItemInfo(itemType, itemCount);
			
			boolean success = Plugin.getWorldManager().getBankingService().handleCurrencyConversion(player, type);
			if (!success) player.closeInventory();
		} else if (titleString.equals("Quest Book")) {
			event.setCancelled(true);
			if (event.getCurrentItem() == null) return;

			ItemStack clickedItem = event.getCurrentItem();
			Player player = (Player) event.getWhoClicked();
			UUID playerId = player.getUniqueId();
			RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);

			String questName = PlainTextComponentSerializer.plainText().serialize(clickedItem.effectiveName());
			Quest clickedQuest = Plugin.getWorldManager().getQuestByName(questName);
			if (clickedQuest == null) return; // TODO: maybe handle better
			
			if (rpgPlayer.getActiveClass().getQuestsCompleted().contains(questName)) {
				player.sendMessage(Component.text("This quest has already been completed"));
				return;
			}

			rpgPlayer.setTrackedQuest(clickedQuest.getUniqueId());
		}
	}
	
}
