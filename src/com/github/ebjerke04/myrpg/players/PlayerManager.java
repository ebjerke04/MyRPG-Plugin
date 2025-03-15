package com.github.ebjerke04.myrpg.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.classes.ClassDataHolder;
import com.github.ebjerke04.myrpg.data.PlayerDataManager;
import com.github.ebjerke04.myrpg.classes.RpgClass;
import com.github.ebjerke04.myrpg.classes.types.ArcherClass;

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

	public void handleClassSelection(Player player, ClassDataHolder classData) {
		RpgClass playerClass;

		switch (classData.type) {
		case ARCHER:
			playerClass = new ArcherClass();
			break;
		case WARRIOR:
			return;
		case MAGE:
			return;
		case ASSASSIN:
			return;
		default:
			throw new IllegalArgumentException("Class type not recognized: " + classData.type.getDisplayName());
		}

		playerClass.loadUserData(classData);

		UUID playerId = player.getUniqueId();
		players.get(playerId).setActiveClass(playerClass);
	}
	
	public PlayerDataHolder getPlayerData(UUID playerId) {
		return players.get(playerId);
	}
	
}
