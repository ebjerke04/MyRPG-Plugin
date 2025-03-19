package com.github.ebjerke04.myrpg.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import com.github.ebjerke04.myrpg.classes.ClassDataHolder;
import com.github.ebjerke04.myrpg.classes.ClassType;
import com.github.ebjerke04.myrpg.data.leveling.LevelingManager;
import com.github.ebjerke04.myrpg.players.RpgPlayer;

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

	public RpgPlayer loadPlayerData(UUID playerId) {
		RpgPlayer dataHolder = new RpgPlayer();
		
		// Load level from config, default to 1 if not found
		//int level = getPlayerData().getInt("players." + playerId + ".level", 1);
		//dataHolder.level = level;
		
		return dataHolder;
	}

	public List<ClassDataHolder> loadPlayerClassData(UUID playerId) {
		List<ClassDataHolder> classData = new ArrayList<>();
		String path = "players." + playerId + ".classes";

		Set<String> classes = getPlayerData().getConfigurationSection(path).getKeys(false);
		for (String className : classes) {
			ClassDataHolder classDataHolder = new ClassDataHolder();
			
			ClassType classType = ClassType.fromString(getPlayerData().getString(path + "." + className + ".type"));
			classDataHolder.type = classType;

			classDataHolder.level = getPlayerData().getInt(path + "." + className + ".level");
			classDataHolder.exp = getPlayerData().getInt(path + "." + className + ".exp");

			List<String> questsCompleted = getPlayerData().getStringList(path + "." + className + ".quests.completed");
			classDataHolder.questsCompleted = questsCompleted;

			classData.add(classDataHolder);
		}
		
		return classData;
	}

	public void storePlayerData(UUID playerId, RpgPlayer playerData) {
		if (playerData == null) return;
		
		//getPlayerData().set("players." + playerId + ".level", playerData.level);
		savePlayerData();
	}

	/*
	public void registerPlayer(Player player) {
		getPlayerData().set("players." + player.getUniqueId() + ".classes.archer1.level", 1);
		getPlayerData().set("players." + player.getUniqueId() + ".classes.archer1.exp", 0);

		savePlayerData();
	}
	*/
	
	private FileConfiguration getPlayerData() {
		return ConfigManager.get().getConfig("player_data");
	}
	
	private void savePlayerData() {
		ConfigManager.get().saveConfig("player_data");
	}

}
