package com.github.ebjerke04.myrpg.players;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.classes.ClassDataHolder;
import com.github.ebjerke04.myrpg.data.PlayerDataManager;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestInProgress;
import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;

import com.github.ebjerke04.myrpg.classes.RpgClass;
import com.github.ebjerke04.myrpg.classes.types.ArcherClass;

public class PlayerManager {
	
	private Map<UUID, RpgPlayer> players = new HashMap<>();
	
	public PlayerManager() {
		// Constructor remains empty as we'll handle player data on join/quit
	}
	
	public void handlePlayerConnect(Player player) {
		UUID playerId = player.getUniqueId();
		RpgPlayer rpgPlayer = PlayerDataManager.get().loadPlayerData(playerId);
		players.put(playerId, rpgPlayer);
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
			playerClass = new ArcherClass();
			break;
		case MAGE:
			playerClass = new ArcherClass();
			break;
		case ASSASSIN:
			playerClass = new ArcherClass();
			break;
		default:
			throw new IllegalArgumentException("Class type not recognized: " + classData.type.getDisplayName());
		}
		
		playerClass.setUserData(classData);

		UUID playerId = player.getUniqueId();
		players.get(playerId).setActiveClass(playerClass);
	}

	public void assignQuest(Player player, Quest quest) {
		UUID playerId = player.getUniqueId();
		RpgPlayer rpgPlayer = players.get(playerId);
		
		QuestInProgress toAssign = new QuestInProgress(quest);
	}
	
	public RpgPlayer getRpgPlayer(UUID playerId) {
		return players.get(playerId);
	}
	
}
