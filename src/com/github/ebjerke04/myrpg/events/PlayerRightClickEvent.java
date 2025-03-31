package com.github.ebjerke04.myrpg.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.github.ebjerke04.myrpg.quests.items.QuestBook;

public class PlayerRightClickEvent extends BaseEvent {

	public PlayerRightClickEvent() {
		super();
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			
			if (item != null && item.hasItemMeta()) {
				String itemName = item.getItemMeta().getDisplayName();
				
				if (item.getType() == Material.WRITTEN_BOOK && itemName.equals("Quest Book")) {
					QuestBook.open(player);
					
					event.setCancelled(true);
				}
			}
		}
	}
	
}
