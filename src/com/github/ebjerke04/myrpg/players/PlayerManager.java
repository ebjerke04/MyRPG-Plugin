package com.github.ebjerke04.myrpg.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.data.PlayerDataManager;

public class PlayerManager {
	
	private Map<UUID, PlayerDataHolder> players = new HashMap<>();
	
	public PlayerManager() {
		// Constructor remains empty as we'll handle player data on join/quit
	}
	
	public void handlePlayerConnect(Player player) {
		UUID playerId = player.getUniqueId();
		PlayerDataHolder dataHolder = PlayerDataManager.get().loadPlayerData(playerId);
		players.put(playerId, dataHolder);
	}
	
	public void handlePlayerDisconnect(Player player) {
		UUID playerId = player.getUniqueId();
		PlayerDataManager.get().storePlayerData(playerId, players.get(playerId));
		players.remove(playerId);
	}
	
	public PlayerDataHolder getPlayerData(UUID playerId) {
		return players.get(playerId);
	}
	
}
