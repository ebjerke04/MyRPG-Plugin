package com.github.ebjerke04.myrpg.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.ebjerke04.myrpg.data.QuestDataManager;
import com.github.ebjerke04.myrpg.economy.BankingService;
import com.github.ebjerke04.myrpg.world.NPC;

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
