package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.interfaces.PlayerScoreboard;
import com.github.ebjerke04.myrpg.quests.ui.QuestBook;

public class PlayerConnectEvent extends BaseEvent {
	
	public PlayerConnectEvent() {
		super();
	}
	
	@EventHandler
	public void onPlayerConnect(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		PlayerScoreboard.defaultScoreboard.sendToPlayer(player);
		
		Plugin.getPlayerManager().handlePlayerConnect(player);
		// TEMP
		QuestBook.addToPlayerInventory(player);

		if (player.hasPlayedBefore()) {
			//PlayerDataManager.get().registerPlayer(player);
		} else {
			
		}
		
	}

}
