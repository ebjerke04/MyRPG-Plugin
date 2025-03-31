package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.ebjerke04.myrpg.creation.RpgCreationManager;
import com.github.ebjerke04.myrpg.quests.RpgCreationStep;

public class PlayerMessageEvent extends BaseEvent {

	public PlayerMessageEvent() {
		super();
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		RpgCreationStep step = RpgCreationManager.get().getCreationStatus(player);
		
		switch (step) {
		case ENTER_QUEST_NAME:
			event.setCancelled(true);
			String message = event.getMessage();
			RpgCreationManager.get().submitQuestName(player, message);
			break;
		case CONFIRM_QUEST_NAME:
			break;
		case NOT_CREATING:
			break;
		default:
			break;
		}
		
		
	}
	
}
