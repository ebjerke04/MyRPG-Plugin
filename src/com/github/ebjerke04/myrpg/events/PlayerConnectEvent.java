package com.github.ebjerke04.myrpg.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.interfaces.PlayerScoreboard;
import com.github.ebjerke04.myrpg.quests.items.QuestBook;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class PlayerConnectEvent extends BaseEvent {
	
	public PlayerConnectEvent() {
		super();
	}
	
	@EventHandler
	public void onPlayerConnect(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		Component title = Component.text("Title test").color(TextColor.color(0xFF0000));
		Component item1 = Component.text("Bruh bruh").color(TextColor.color(0xFF00FF));
		Component item2 = Component.text("What up?").color(TextColor.color(0x0000FF));
		
		List<Component> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);

		PlayerScoreboard testScoreboard = new PlayerScoreboard(title, items);
		testScoreboard.sendToPlayer(player);
		
		Plugin.getPlayerManager().handlePlayerConnect(player);
		// TEMP
		QuestBook.addToPlayerInventory(player);

		if (player.hasPlayedBefore()) {
			
		} else {
			
		}
		
	}

}
