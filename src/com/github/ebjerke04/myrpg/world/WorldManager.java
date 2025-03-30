package com.github.ebjerke04.myrpg.world;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.ebjerke04.myrpg.data.QuestDataManager;
import com.github.ebjerke04.myrpg.data.WorldDataManager;
import com.github.ebjerke04.myrpg.economy.BankingService;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestNPC;

public class WorldManager {

	private BankingService bankingService;
	
	List<NPC> npcs = new ArrayList<>();
	
	List<Quest> quests = new ArrayList<Quest>();
	
	public WorldManager() {
		
	}

	public void init() {
		loadNPCsFromConfig();
		loadQuestsFromConfig();

		spawnNPCs();

		bankingService = new BankingService();
	}

	private void spawnNPCs() {
		for (NPC npc : npcs) {
			npc.spawn();
		}
	}

	private void despawnNPCs() {
		for (NPC npc : npcs) {
			npc.despawn();
		}
	}

	private void loadNPCsFromConfig() {
		for (String npcName : QuestDataManager.get().getQuestNPCNames()) {
			npcs.add(QuestDataManager.get().createQuestNPC(npcName));
		}
		
		npcs.addAll(WorldDataManager.get().createBankingNPCs());
	}
	
	private void loadQuestsFromConfig() {
		for (String questName : QuestDataManager.get().getQuestNames()) {
			quests.add(QuestDataManager.get().createQuest(questName));
		}
	}

	public void shutdown() {
		despawnNPCs();
	}

	public List<Quest> getQuestsByStartNPC(QuestNPC npc) {
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

	public Quest getQuestByName(String questName) {
		for (Quest quest : quests) {
			if (quest.getName().equals(questName)) return quest;
		}

		return null;
	}

	public NPC getNPCbyId(UUID id) {
		for (NPC npc : npcs) {
			if (npc.getUniqueId().equals(id)) return npc;
		}
		
		return null;
	}

	public NPC getNPCbyName(String name) {
		for (NPC npc : npcs) {
			if (npc.getName().equals(name)) return npc;
		}

		return null;
	}
	
	public BankingService getBankingService() {
		return bankingService;
	}

}
