package com.github.ebjerke04.myrpg.quests;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;
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
		Logging.sendConsole(Level.INFO, "Clicked NPC: " + getConfigName());
		
		UUID playerId = player.getUniqueId();
		RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);
		RpgClass activeClass = rpgPlayer.getActiveClass();

		if (activeClass == null) {
			Logging.sendConsole(Level.INFO, "Tried to click NPC without class selected");
			return;
		}

		List<QuestInProgress> questsInProgress = rpgPlayer.getQuestsInProgress();
		if (!questsInProgress.isEmpty()) {
			for (QuestInProgress questInProgress : questsInProgress) {
				QuestStep currentStep = questInProgress.getCurrentStep();
				if (currentStep.getType() == QuestStepType.NPC_INTERACT) {
					QuestStepNpcInteract interactStep = (QuestStepNpcInteract) currentStep;
					QuestNPC npc = interactStep.getQuestNPC();
					if (npc.equals(this)) {
						rpgPlayer.attemptQuestProgression(questInProgress);
						return;
					}
				}
			}
		}

		List<Quest> availableQuests = Plugin.getWorldManager().getQuestsByStartNPC(this);
		if (!availableQuests.isEmpty()) {
			List<String> completedQuests = activeClass.getQuestsCompleted();
			Quest earliestQuest = null;
			for (Quest quest : availableQuests) {
				if (completedQuests.contains(quest.getName())) continue;
				if (earliestQuest == null) earliestQuest = quest;
				
				if (quest.getMinLevel() < earliestQuest.getMinLevel())
					earliestQuest = quest;
			}
			
			if (earliestQuest != null) {
				for (QuestInProgress questInProgress : questsInProgress) {
					if (earliestQuest.getUniqueId().equals(questInProgress.getRespectiveId())) {
						player.sendMessage(Component.text("You have already started the quest for this NPC")
							.color(TextColor.color(0xFF00FF)));
						return;
					}
				}

				Plugin.getPlayerManager().assignQuestToPlayer(player, earliestQuest);
			}
		}
	}

}
