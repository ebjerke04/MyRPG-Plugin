package com.github.ebjerke04.myrpg.events;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
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
import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
			// Cancel ALL item movement in this inventory
			event.setCancelled(true);
			if (event.getCurrentItem() == null) return;
			
			ItemStack clickedItem = event.getCurrentItem();
			Player player = (Player) event.getWhoClicked();
			
			if (clickedItem.getType() == Material.EMERALD) {
				// Handle create new class button click
				player.closeInventory();
				// TODO: Open class creation menu
				Logging.sendConsole(Component.text("Class creation button clicked")
					.color(TextColor.color(0xFF0000)));
			} else if (clickedItem.getType() == Material.BOOK) {
				// Handle existing class selection
				String className = clickedItem.getItemMeta().displayName().toString();
				player.closeInventory();
				
				int clickedSlot = event.getSlot();
				int classIndex = clickedSlot - ClassSelectionMenu.SLOT_START;

				List<ClassDataHolder> classData = PlayerDataManager.get().loadPlayerClassData(player.getUniqueId());
				ClassDataHolder clickedClassData = classData.get(classIndex);
				
				Plugin.getPlayerManager().handleClassSelection(player, clickedClassData);
			}
		}
	}
	
}
