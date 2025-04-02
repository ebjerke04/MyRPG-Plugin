package com.github.ebjerke04.myrpg.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.quests.NPCDataHolder;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestDataHolder;
import com.github.ebjerke04.myrpg.quests.QuestNPC;
import com.github.ebjerke04.myrpg.quests.QuestStep;
import com.github.ebjerke04.myrpg.quests.QuestStepEnterArea;
import com.github.ebjerke04.myrpg.quests.QuestStepKillBoss;
import com.github.ebjerke04.myrpg.quests.QuestStepNpcInteract;
import com.github.ebjerke04.myrpg.quests.QuestStepType;
import com.github.ebjerke04.myrpg.util.Logging;
import com.github.ebjerke04.myrpg.world.NPC;
import com.github.ebjerke04.myrpg.world.Region3D;

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
		List<String> questNames = new ArrayList<>();
		if (getQuestData().contains("quests")) {
			questNames = new ArrayList<String>(getQuestData().getConfigurationSection("quests").getKeys(false));
		}
		
		if (questNames.isEmpty()) Logging.sendConsole(Level.INFO, "No quests have been created yet");
		return questNames;
	}
	
	public boolean questPublished(String name) {
		return getQuestData().getBoolean("quests." + name + ".published");
	}

	public QuestNPC createQuestNPC(String name) {
		return new QuestNPC(getQuestNPCData(name));
	}
	
	public Quest createQuest(String name) {
		return new Quest(getQuestData(name));
	}

	private NPCDataHolder getQuestNPCData(String name) {
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
		String startNPCName = getQuestData().getString(path + ".steps.1.npc-name");
		NPC startNPC = Plugin.getWorldManager().getNPCbyName(startNPCName);
		
		if (startNPC instanceof QuestNPC) {
			data.startNPC = (QuestNPC) startNPC;
		} else {
			Logging.sendConsole(Level.SEVERE, "Start NPC for quest, " + questName + ", not a QuestNPC");
			throw new IllegalStateException();
		}
		
		List<String> stepConfigKeys = new ArrayList<String>(getQuestData().getConfigurationSection(path + ".steps").getKeys(false));
		Map<Integer, QuestStep> questSteps = new HashMap<>();
		for (String stepString : stepConfigKeys) {
			String stepPath = path + ".steps." + stepString + ".";

			String description = getQuestData().getString(stepPath + "description");
			List<String> dialogue = getQuestData().getStringList(stepPath + "dialogue");
			
			QuestStepType stepType = QuestStepType.fromString(getQuestData().getString(stepPath + "type"));
			switch (stepType) {
			case NPC_INTERACT:
				String npcName = getQuestData().getString(stepPath + "npc-name");
				// TODO: make sure actually QuestNPC
				QuestNPC questNPC = (QuestNPC) Plugin.getWorldManager().getNPCbyName(npcName);
				questSteps.put(Integer.parseInt(stepString), 
					new QuestStepNpcInteract(questNPC, description, dialogue));
				break;
			case ENTER_AREA:
				Location corner1 = getQuestData().getLocation(stepPath + "corner1");
				Location corner2 = getQuestData().getLocation(stepPath + "corner2");
				Region3D region3D = new Region3D(corner1, corner2);
				questSteps.put(Integer.parseInt(stepString), 
					new QuestStepEnterArea(region3D, description, dialogue));
				break;
			case KILL_BOSS:
				String bossName = getQuestData().getString(stepPath + "boss-name");
				questSteps.put(Integer.parseInt(stepString),
					new QuestStepKillBoss(bossName, description, dialogue));
				break;
			case null:
				Logging.sendConsole(Level.SEVERE, "Quest step type registered as null for quest step, " + stepString
					+ ", for quest: ");
				break;
			default:
				Logging.sendConsole(Level.SEVERE, "Yeahhh this should not happen...");
				break;
			}
		}

		Stack<QuestStep> questStepStack = new Stack<>();
		for (int i = 0; i < questSteps.size(); i++) {
			int stepNumber = i + 1;
			questStepStack.insertElementAt(questSteps.get(stepNumber), 0);
		}
		data.steps = questStepStack;
		
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
