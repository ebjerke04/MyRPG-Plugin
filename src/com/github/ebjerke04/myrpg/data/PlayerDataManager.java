package com.github.ebjerke04.myrpg.data;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.ebjerke04.myrpg.data.leveling.LevelingManager;
import com.github.ebjerke04.myrpg.players.PlayerDataHolder;

public class PlayerDataManager {
	
	private static PlayerDataManager instance;
	
	private LevelingManager levelingManager;
	
	private PlayerDataManager() {
		ConfigManager.get().createConfig("player_data");
		ConfigManager.get().reloadConfig("player_data");
		
		levelingManager = new LevelingManager();
	}
	
	public static void init() {
		instance = new PlayerDataManager();
	}
	
	public static PlayerDataManager get() {
		return instance;
	}
	
	public void shutdown() {
		savePlayerData();
	}

	public PlayerDataHolder loadPlayerData(UUID playerId) {
		PlayerDataHolder dataHolder = new PlayerDataHolder();
		
		// Load level from config, default to 1 if not found
		int level = getPlayerData().getInt("players." + playerId + ".level", 1);
		dataHolder.level = level;
		
		return dataHolder;
	}

	public void storePlayerData(UUID playerId, PlayerDataHolder playerData) {
		if (playerData == null) return;
		
		getPlayerData().set("players." + playerId + ".level", playerData.level);
		savePlayerData();
	}
	
	private FileConfiguration getPlayerData() {
		return ConfigManager.get().getConfig("player_data");
	}
	
	private void savePlayerData() {
		ConfigManager.get().saveConfig("player_data");
	}

}
