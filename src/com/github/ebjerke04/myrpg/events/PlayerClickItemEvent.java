package com.github.ebjerke04.myrpg.events;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
			// Cancel ALL item movement in this inventory
			event.setCancelled(true);
			
			// Prevent any item movement actions
			if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
				event.getAction() == InventoryAction.COLLECT_TO_CURSOR ||
				event.getAction() == InventoryAction.HOTBAR_SWAP) {
				return;
			}
			
			if (event.getCurrentItem() == null) return;
			
			ItemStack clickedItem = event.getCurrentItem();
			Player player = (Player) event.getWhoClicked();
			
			if (clickedItem.getType() == Material.EMERALD) {
				// Handle create new class button click
				player.closeInventory();
				// TODO: Open class creation menu
				Bukkit.getLogger().log(Level.SEVERE, "Test worked");
			} else if (clickedItem.getType() == Material.BOOK) {
				// Handle existing class selection
				String className = clickedItem.getItemMeta().displayName().toString();
				player.closeInventory();
				// TODO: Set active class
			}
		}
	}
	
}
