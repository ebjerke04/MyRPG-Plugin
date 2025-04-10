package com.github.ebjerke04.myrpg.data.leveling;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import com.github.ebjerke04.myrpg.data.ConfigManager;
import com.github.ebjerke04.myrpg.util.Logging;

public class LevelingManager {

	private static Map<Integer, PlayerLevel> levels = new HashMap<>();
	
	public LevelingManager() {
		populateLevelDataMap();
	}
	
	private static void populateLevelDataMap() {
		Set<String> levelConfigSections = ConfigManager.get().getConfig("config")
				.getConfigurationSection("level-data").getKeys(false);
	
		int maxLevel = 1;
		for (String levelString : levelConfigSections) {
			try {
				int level = Integer.parseInt(levelString);
				
				if (level > maxLevel) maxLevel = level;
			} catch (NumberFormatException e) {
				Logging.sendConsole(Level.SEVERE, "Check config.cfg. Under \"level-data\", \"" + levelString + "\" should be an integer!");
				return;
			}
		}
		
		for (int i = 1; i <= maxLevel; i++) {
			String path = "level-data." + Integer.toString(i);
			if (!ConfigManager.get().getConfig("config").contains(path)) {
				Logging.sendConsole(Level.SEVERE, "Check config.cfg. Max level detected is " + maxLevel + " however data for level " + i + " is missing!");
				return;
			}
			
			int levelExp = ConfigManager.get().getConfig("config").getInt(path + ".expToNextLevel");
			levels.put(i, new PlayerLevel(levelExp));
		}
	}

	public static int getExperienceToNext(int currentLevel) {
		return levels.get(currentLevel).getExperienceToNext();
	}

	public static int calculateExperienceReward(int playerLevel, int mobLevel, int baseExperienceReward) {
		double levelDifference = Math.abs(mobLevel - playerLevel);

		double rewardFactor;
		int fullRewardMaxDifference = 2;
		if (levelDifference <= fullRewardMaxDifference) {
			rewardFactor = 1.0;
		} else {
			rewardFactor = Math.exp(-1.0 * Math.pow((levelDifference - 2.0) / 4.0, 2.0));
		}

		return (int) ((double) baseExperienceReward * rewardFactor);
	}
	
}
