package com.github.ebjerke04.myrpg.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.data.QuestDataManager;
import com.github.ebjerke04.myrpg.data.WorldDataManager;
import com.github.ebjerke04.myrpg.economy.BankingService;
import com.github.ebjerke04.myrpg.entities.CustomMob;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestNPC;

public class WorldManager {

	private BankingService bankingService;
	
	private List<NPC> npcs = new ArrayList<>();
	private List<Quest> quests = new ArrayList<Quest>();
	
	private Map<UUID, CustomMob> spawnedMobs = new ConcurrentHashMap<>();

	public WorldManager() {
		
	}

	public void init() {
		loadNPCsFromConfig();
		loadQuestsFromConfig();

		spawnNPCs();

		bankingService = new BankingService();
	}

	public void test(Player player) {
		CustomMob cMob = new CustomMob(player.getLocation());
		spawnedMobs.put(cMob.getUniqueId(), cMob);
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

	public CustomMob getCustomMob(UUID mobId) {
		return spawnedMobs.get(mobId);
	}

	public Collection<CustomMob> getCustomMobs() {
		return spawnedMobs.values();
	}
	
	public BankingService getBankingService() {
		return bankingService;
	}

}
