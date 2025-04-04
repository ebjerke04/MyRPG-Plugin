package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.world.NPC;

public class PlayerRightClickEntityEvent extends BaseEvent {

	public PlayerRightClickEntityEvent() {
		super();
	}
	
	@EventHandler
	public void onEntityRightClick(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Villager) {
			Player player = event.getPlayer();
			Villager villager = (Villager) event.getRightClicked();

			NPC npc = Plugin.getWorldManager().getNPCbyId(villager.getUniqueId());
			if (npc != null) {
				npc.rightClicked(player);
			}
		}
	}
	
}