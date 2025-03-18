package com.github.ebjerke04.myrpg.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.quests.NPCDataHolder;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestDataHolder;
import com.github.ebjerke04.myrpg.quests.QuestNPC;

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

	public List<String> getQuestNPCNames() {
		if (getQuestData().contains("npcs")) {
			return new ArrayList<String>(getQuestData().getConfigurationSection("npcs").getKeys(false));
		}

		return null;
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

	public QuestNPC createNPC(String name) {
		return new QuestNPC(getNPCData(name));
	}
	
	public Quest createQuest(String name) {
		return new Quest(getQuestData(name));
	}

	private NPCDataHolder getNPCData(String name) {
		NPCDataHolder data = new NPCDataHolder();

		data.name = name;

		String path = "npcs." + name + ".spawn";
		Location location = getQuestData().getLocation(path);
		data.location = location;

		return data;
	}
	
	// ADD CHECK TO MAKE SURE 
	private QuestDataHolder getQuestData(String questName) {
		QuestDataHolder data = new QuestDataHolder();
		data.name = questName;
		
		String path = "quests." + questName;
		
		data.minLevel = getQuestData().getInt(path + ".min-level");

		// TODO: Add null check
		String startNPCName = getQuestData().getString(path + ".steps.1.npc"); 
		data.startNPC = Plugin.getQuestManager().getNPCbyName(startNPCName);
		
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
