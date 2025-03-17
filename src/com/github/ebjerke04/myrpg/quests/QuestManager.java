package com.github.ebjerke04.myrpg.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.ebjerke04.myrpg.data.QuestDataManager;

public class QuestManager {
	
	List<QuestNPC> npcs = new ArrayList<QuestNPC>();
	
	List<Quest> quests = new ArrayList<Quest>();
	
	public QuestManager() {
		loadNPCsFromConfig();
		loadQuestsFromConfig();

		// TODO: Move this spawning of NPCs to somewhere else
		// Also make sure to despawn NPCs when server shuts down
		// ^ Important to not have NPCs overlap.
		for (QuestNPC npc : npcs) {
			npc.spawn();
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
	
	public List<Quest> getQuests() {
		return quests;
	}

	public QuestNPC getNPCbyId(UUID id) {
		for (QuestNPC npc : npcs) {
			if (npc.getUniqueId().equals(id)) return npc;
		}
		
		return null;
	}
	

}
