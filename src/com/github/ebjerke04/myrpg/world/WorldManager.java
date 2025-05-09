package com.github.ebjerke04.myrpg.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.data.QuestDataManager;
import com.github.ebjerke04.myrpg.data.WorldDataManager;
import com.github.ebjerke04.myrpg.economy.BankingService;
import com.github.ebjerke04.myrpg.entities.CustomMob;
import com.github.ebjerke04.myrpg.entities.CustomMobAttributes;
import com.github.ebjerke04.myrpg.entities.EntityDataHolder;
import com.github.ebjerke04.myrpg.items.RpgItem;
import com.github.ebjerke04.myrpg.items.RpgItemDataHolder;
import com.github.ebjerke04.myrpg.players.RpgPlayer;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestInProgress;
import com.github.ebjerke04.myrpg.quests.QuestNPC;
import com.github.ebjerke04.myrpg.quests.QuestStep;
import com.github.ebjerke04.myrpg.quests.QuestStepKillMob;
import com.github.ebjerke04.myrpg.quests.QuestStepType;

public class WorldManager {

	private BankingService bankingService;
	
	private final List<NPC> npcs = new ArrayList<>();
	private final List<Quest> quests = new ArrayList<Quest>();

	private final Map<String, CustomMob> mobTemplates = new HashMap<>();
	private final Map<UUID, CustomMob> spawnedMobs = new ConcurrentHashMap<>();

	private final List<RpgItem> items = new ArrayList<>();

	public WorldManager() {

	}

	public void init() {
		loadNPCsFromConfig();
		loadQuestsFromConfig();
		loadMobTemplates();
		loadRpgItems();

		spawnNPCs();
		
		bankingService = new BankingService();
	}

	public void testCustomMob(Player player, String mobName) {
		CustomMob templateMob = mobTemplates.get(mobName);
		//templateMob.makeScripted("test_script.js");
		
		CustomMob spawnedMob = templateMob.spawnFromTemplate(player.getLocation());
		spawnedMobs.put(spawnedMob.getUniqueId(), spawnedMob);
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
	
	private void loadMobTemplates() {
		List<EntityDataHolder> templateData = WorldDataManager.get().loadCustomMobTemplates();

		for (EntityDataHolder entityData : templateData) {
			CustomMobAttributes attributes = new CustomMobAttributes();
			attributes.level = entityData.level;
			attributes.baseExperienceReward = entityData.baseExperienceReward;
			attributes.maxHealth = entityData.maxHealth;
			mobTemplates.put(entityData.mobName, new CustomMob(
				entityData.mobName,
				entityData.entityType,
				entityData.displayName,
				attributes
			));
		}
	}

	private void loadRpgItems() {
		List<RpgItemDataHolder> itemDataList = WorldDataManager.get().loadRpgItemData();

		for (RpgItemDataHolder itemData : itemDataList) {
			items.add(new RpgItem(itemData));
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
			if (npc.getConfigName().equals(name)) return npc;
		}

		return null;
	}

	public void handleCustomMobDeath(CustomMob customMob) {
		for (Player player : customMob.getDamagers()) {
            player.sendMessage("You were involved in killing an entity");
			
            UUID playerId = player.getUniqueId();
            RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);

			Plugin.getPlayerManager().playerKillsMob(rpgPlayer, customMob);
			rpgPlayer.removeMobInCombat(customMob);

			List<QuestInProgress> questsInProgress = rpgPlayer.getQuestsInProgress();
			if (questsInProgress.isEmpty()) return;
	
			for (QuestInProgress questInProgress : questsInProgress) {
				QuestStep currentStep = questInProgress.getCurrentStep();
				
				// Not very graceful way of handling this problem, but hey it works.
				// Step gets popped from the stack before message delivery service ends.
				if (currentStep == null) continue;
	
				if (currentStep.getType() == QuestStepType.KILL_MOB) {
					QuestStepKillMob killStep = (QuestStepKillMob) currentStep;
					String mobName = killStep.getMobName();

					if (mobName.equals(customMob.getName())) {
						rpgPlayer.attemptQuestProgression(questInProgress);
						break;
					}
				}
			}
        }

		UUID customMobId = customMob.getUniqueId();
		spawnedMobs.remove(customMobId);

		customMob.cleanup();
	}

	public CustomMob getCustomMob(UUID mobId) {
		return spawnedMobs.get(mobId);
	}
	
	public BankingService getBankingService() {
		return bankingService;
	}

}
