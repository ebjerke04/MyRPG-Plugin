package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.github.ebjerke04.myrpg.creation.RpgCreationManager;
import com.github.ebjerke04.myrpg.quests.RpgCreationStep;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerMessageEvent extends BaseEvent {

	public PlayerMessageEvent() {
		super();
	}
	
	@EventHandler
	public void onPlayerChat(AsyncChatEvent event) {
		Player player = event.getPlayer();
		RpgCreationStep step = RpgCreationManager.get().getCreationStatus(player);
		
		switch (step) {
		case ENTER_QUEST_NAME:
			event.setCancelled(true);
			String message = PlainTextComponentSerializer.plainText().serialize(event.message());
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
