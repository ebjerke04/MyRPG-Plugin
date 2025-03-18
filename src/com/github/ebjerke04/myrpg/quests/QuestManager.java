package com.github.ebjerke04.myrpg.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.ebjerke04.myrpg.data.QuestDataManager;

public class QuestManager {
	
	List<QuestNPC> npcs = new ArrayList<QuestNPC>();
	
	List<Quest> quests = new ArrayList<Quest>();
	
	public QuestManager() {
		
	}

	public void init() {
		loadNPCsFromConfig();
		loadQuestsFromConfig();

		spawnNPCs();
	}

	private void spawnNPCs() {
		for (QuestNPC npc : npcs) {
			npc.spawn();
		}
	}

	private void despawnNPCs() {
		for (QuestNPC npc : npcs) {
			npc.despawn();
		}
	}

	private void loadNPCsFromConfig() {
		for (String npcName : QuestDataManager.get().getQuestNPCNames()) {
			npcs.add(QuestDataManager.get().createNPC(npcName));
		}
	}
	
	private void loadQuestsFromConfig() {
		for (String questName : QuestDataManager.get().getQuestNames()) {
			quests.add(QuestDataManager.get().createQuest(questName));
		}
	}

	public void shutdown() {
		despawnNPCs();
	}

	public List<Quest> getQuestsForNPC(QuestNPC npc) {
		List<Quest> npcQuests = new ArrayList<>();
		for (Quest quest : quests) {
			if (npc.equals(quest.getStartNPC())) {
				npcQuests.add(quest);
			}
		}

		return npcQuests;
	}
	
	public List<Quest> getQuests() {
		return quests;
	}

	public QuestNPC getNPCbyId(UUID id) {
		for (QuestNPC npc : npcs) {
			if (npc.getUniqueId().equals(id)) return npc;
		}
		
		return null;
	}

	public QuestNPC getNPCbyName(String name) {
		for (QuestNPC npc : npcs) {
			if (npc.getName().equals(name)) return npc;
		}

		return null;
	}
	

}
