package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.quests.QuestNPC;

public class EntityRightClickEvent extends BaseEvent {

	public EntityRightClickEvent() {
		super();
	}
	
	@EventHandler
	public void onEntityRightClick(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Villager) {
			Player player = event.getPlayer();
			Villager villager = (Villager) event.getRightClicked();
			
			// TODO: Detect NPCs by UUID
			QuestNPC questNPC = Plugin.get().getQuestManager().getNPCbyId(villager.getUniqueId());
			if (questNPC != null) {
				questNPC.rightClicked(player);
			}
		}
	}
	
}