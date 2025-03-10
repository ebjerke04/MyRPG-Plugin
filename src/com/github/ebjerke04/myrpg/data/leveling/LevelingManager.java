package com.github.ebjerke04.myrpg.data.leveling;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.data.ConfigManager;

public class LevelingManager {

	private Map<Integer, PlayerLevel> levels = new HashMap<>();
	
	public LevelingManager() {
		populateLevelDataMap();
	}
	
	private void populateLevelDataMap() {
		Set<String> levelConfigSections = ConfigManager.get().getConfig("config")
				.getConfigurationSection("level-data").getKeys(false);
	
		int maxLevel = 1;
		for (String levelString : levelConfigSections) {
			try {
				int level = Integer.parseInt(levelString);
				
				if (level > maxLevel) maxLevel = level;
			} catch (NumberFormatException e) {
				Plugin.get().getLogger().log(Level.SEVERE, "Check config.cfg. Under \"level-data\", \"" + levelString + "\" should be an integer!");
				return;
			}
		}
		
		for (int i = 1; i <= maxLevel; i++) {
			String path = "level-data." + Integer.toString(i);
			if (!ConfigManager.get().getConfig("config").contains(path)) {
				Plugin.get().getLogger().log(Level.SEVERE, "Check config.cfg. Max level detected is " + maxLevel + " however data for level " + i + " is missing!");
				return;
			}
			
			int levelExp = ConfigManager.get().getConfig("config").getInt(path + ".expToNextLevel");
			levels.put(i, new PlayerLevel(levelExp));
		}
	}
	
}
