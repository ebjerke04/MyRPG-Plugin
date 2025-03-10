package com.github.ebjerke04.myrpg.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestDataHolder;

public class QuestDataManager {
	
	private static QuestDataManager instance;
	
	private QuestDataManager() {
		ConfigManager.get().createConfig("quest_data");
		ConfigManager.get().reloadConfig("quest_data");
	}
	
	public static void init() {
		instance = new QuestDataManager();
	}
	
	public static QuestDataManager get() {
		return instance;
	}
	
	public void shutdown() {
		saveQuestData();
	}
	
	private FileConfiguration getQuestData() {
		return ConfigManager.get().getConfig("quest_data");
	}
	
	private void saveQuestData() {
		ConfigManager.get().saveConfig("quest_data");
	}
	
	public boolean questExists(String name) {
		return getQuestData().contains("quests." + name);
	}
	
	public List<String> getQuestNames() {
		if (getQuestData().contains("quests")) {
			return new ArrayList<String>(getQuestData().getConfigurationSection("quests").getKeys(false));
		}
		
		return null;
	}
	
	public boolean questPublished(String name) {
		return getQuestData().getBoolean("quests." + name + ".published");
	}
	
	public Quest createQuest(String name) {
		return new Quest(getQuestData(name));
	}
	
	// ADD CHECK TO MAKE SURE 
	private QuestDataHolder getQuestData(String questName) {
		QuestDataHolder data = new QuestDataHolder();
		data.name = questName;
		
		String path = "quests." + questName;
		data.minLevel = getQuestData().getInt(path + ".min-level");
		
		return data;
	}
	
	// ----------------------------------------------------------------
	public void addQuestToConfig(String name) {
		String path = "quests." + name;
		getQuestData().set(path + ".min-level", 0);
		getQuestData().set(path + ".exp-reward", 0);
		getQuestData().set(path + ".start-npc-id", 1234);
		getQuestData().set(path + ".published", false);
		
		saveQuestData();
	}

}
