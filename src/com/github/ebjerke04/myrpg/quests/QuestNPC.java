package com.github.ebjerke04.myrpg.quests;

import java.util.List;
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
import com.github.ebjerke04.myrpg.world.NPC;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class QuestNPC extends NPC {
	
	public QuestNPC(NPCDataHolder data) {
		super(data);
	}
	
	@Override
	public void rightClicked(Player player) {
		Logging.sendConsole(Component.text("Clicked NPC: " + getName())
			.color(TextColor.color(0xFF00FF)));
		
		// TODO: getQuestsForNPC() does not account for NPCs that aren't the start NPC.
		// Logic works for starting quests but will not capture all cases...
		List<Quest> availableQuests = Plugin.getWorldManager().getQuestsForNPC(this);
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

				// if the NPC is related to a quest in progress and allows the quest to progress do that
				boolean isQuestInProgress = false;
				List<QuestInProgress> questsInProgress = rpgPlayer.getQuestsInProgress();
				for (QuestInProgress questInProgress : questsInProgress) {
					if (earliestQuest.getUniqueId().equals(questInProgress.getRespectiveId()))
						isQuestInProgress = true;
				}
				if (isQuestInProgress) {
					// TODO: figure out what to do if a NPC related to quest in progress is clicked
					player.sendMessage(Component.text("Quest in progress detected")
						.color(TextColor.color(0x0000FF)));
				} else {
					Plugin.getPlayerManager().assignQuest(player, earliestQuest);
				}
			}
		}
	}

}
