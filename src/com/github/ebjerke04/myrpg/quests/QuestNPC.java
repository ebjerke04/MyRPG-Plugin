package com.github.ebjerke04.myrpg.quests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.classes.RpgClass;
import com.github.ebjerke04.myrpg.players.RpgPlayer;
import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class QuestNPC {
	
	private NPCDataHolder data;
	
	private UUID id;
	
	public QuestNPC(NPCDataHolder data) {
		this.data = data;
	}
	
	public void spawn() {
		if (data.location == null) {
			Bukkit.getLogger().log(Level.SEVERE, "Location for NPC " + data.name + " was null, did not spawn");
			return;
		}
		
		Villager villager = data.location.getWorld().spawn(data.location, Villager.class);
		
		villager.setRemoveWhenFarAway(false);
		villager.setAI(false);
		villager.customName(Component.text(data.name).color(TextColor.color(0x00FF00)));
		villager.setCustomNameVisible(true);
		
		id = villager.getUniqueId();
	}

	public void despawn() {
		Chunk chunk = data.location.getChunk();
		if (!chunk.isLoaded()) chunk.load();

		Entity entity = Bukkit.getEntity(id);
		if (entity != null) {
			entity.remove();
		}
	}
	
	public void rightClicked(Player player) {
		Logging.sendConsole(Component.text("Clicked NPC: " + getName())
			.color(TextColor.color(0xFF00FF)));

		List<Quest> availableQuests = Plugin.getQuestManager().getQuestsForNPC(this);
		if (!availableQuests.isEmpty()) {
			UUID playerId = player.getUniqueId();
			RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);
			RpgClass activeClass = rpgPlayer.getActiveClass();

			if (activeClass == null) {
				Logging.sendConsole(Component.text("Tried to click NPC without class selected")
					.color(TextColor.color(0xFF00FF)));
				return;
			}

			List<String> completedQuests = activeClass.getQuestsCompleted();
			// Sort through available quests.
			// Check if quest has been completed, ensure only the quest with the lowest level is began.
			Quest earliestQuest = null;
			for (Quest quest : availableQuests) {
				if (completedQuests.contains(quest.getName())) continue;
				if (earliestQuest == null) earliestQuest = quest;

				if (quest.getMinLevel() < earliestQuest.getMinLevel())
					earliestQuest = quest;
			}
			
			if (earliestQuest != null) {
				player.sendMessage(Component.text("Quest: " + earliestQuest.getName() + " can be started!")
					.color(TextColor.color(0xFF00FF)));

				// TODO: be able to figure out if a quest is in progress.
				// if the NPC is related to a quest in progress and allows the quest to progress do that
				boolean questInProgress = false;
				if (questInProgress) {
					// send message to player...
					
				} else {
					Plugin.getPlayerManager().assignQuest(player, earliestQuest);
				}
			}
		}
	}

	public String getName() {
		return data.name;
	}
	
	public UUID getUniqueId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof QuestNPC) {
			QuestNPC npc = (QuestNPC) obj;
			if (npc.getUniqueId() == this.getUniqueId()) return true;
		}

		return false;
	}

}
