package com.github.ebjerke04.myrpg.players;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.classes.RpgClass;
import com.github.ebjerke04.myrpg.entities.CustomMob;
import com.github.ebjerke04.myrpg.interfaces.PlayerScoreboard;
import com.github.ebjerke04.myrpg.quests.Quest;
import com.github.ebjerke04.myrpg.quests.QuestInProgress;
import com.github.ebjerke04.myrpg.quests.QuestStep;
import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class RpgPlayer {

	private Player player;
	private RpgClass activeClass = null;
	private UUID trackedQuestId = null;

	private List<QuestInProgress> questsInProgress = new ArrayList<>();

	private List<CustomMob> mobsInCombat = new ArrayList<>();
	
	public RpgPlayer(Player player) {
		this.player = player;
		
		boolean debug = false;
		if (debug) {
			new BukkitRunnable() {
				@Override
				public void run() {
					Logging.sendConsole(Level.INFO, player.getName() + ": " + mobsInCombat.size());
				}
			}.runTaskTimer(Plugin.get(), 60L, 60L);
		}
	}

	public void setActiveClass(RpgClass activeClass) {
		this.activeClass = activeClass;

		// TODO: Player active class set. Figure out what is next.
		// Send player to last location they left off at.
		// Load their saved inventory.
	}

	public void setMobInCombat(CustomMob customMob) {
		customMob.addDamager(player);

		for (CustomMob mobInCombat : mobsInCombat) {
			if (mobInCombat.getUniqueId().equals(customMob.getUniqueId())) return;
		}
		mobsInCombat.add(customMob);
	}

	public void removeMobInCombat(CustomMob customMob) {
		customMob.removeDamager(player);

		for (int i = 0; i < mobsInCombat.size(); i++) {
			if (mobsInCombat.get(i).getUniqueId().equals(customMob.getUniqueId())) {
				mobsInCombat.remove(i);
				return;
			}
		}
	}

	public void handlePlayerDeath() {
		for (CustomMob customMob : mobsInCombat) {
			customMob.removeDamager(player);
		}

		mobsInCombat.clear();
	}

	public void assignQuest(QuestInProgress quest) {
		attemptQuestProgression(quest);
		questsInProgress.add(quest);	
	}

	// TODO: when updating the tracking scoreboard:
	// maybe break the description message down into a couple lines. (takes up a lot of space)
	public void attemptQuestProgression(QuestInProgress quest) {
		QuestStep currentStep = quest.getCurrentStep();
		if (quest.attemptProgression() && !currentStep.isDialoguing()) {
			// progression successful, do stuff here
			List<String> dialogueStrings = currentStep.getDialogue();
			currentStep.setDialoguing(true);

			List<Component> dialogue = new ArrayList<>();
			for (String dialogueString : dialogueStrings) dialogue.add(Component.text(dialogueString)
				.color(TextColor.color(0xFF00FF)));

			MessageDeliverer.deliverMessageList(player, dialogue, () -> {
				quest.revealNextStep();
				currentStep.setDialoguing(false);

				boolean completed = quest.isComplete();
				if (completed) {
					for (int i = 0; i < questsInProgress.size(); i++) {
						if (questsInProgress.get(i).equals(quest)) {
							activeClass.setQuestCompleted(quest.getName());
							player.sendMessage(Component.text("Quest has been completed!")
								.color(TextColor.color(0x00FF00)));
							questsInProgress.remove(i);

							PlayerScoreboard.defaultScoreboard.sendToPlayer(player);
							setTrackedQuest(null);
							break;
						}
					}
				} else {
					player.sendMessage(Component.text("Quest book updated for quest: " + quest.getName())
					.color(TextColor.color(0x00FF00)));

					if (trackedQuestId == null) return;
					if (trackedQuestId.equals(quest.getRespectiveId()))
						updateTrackedQuest();
				}
			});
			
		}
	}

	public void setTrackedQuest(UUID questId) {
		trackedQuestId = questId;
		updateTrackedQuest();
	}

	public void updateTrackedQuest() {
		for (QuestInProgress questInProgress : questsInProgress) {
			if (trackedQuestId.equals(questInProgress.getRespectiveId())) {
				QuestStep currentStep = questInProgress.getCurrentStep();
				
				Component title = Component.text("Tracking - " + questInProgress.getName());
				List<Component> items = List.of(
					Component.text("Task: " + currentStep.getDescription()),
					Component.text("Go to: [x,y,z]")
				);
				PlayerScoreboard scoreboard = new PlayerScoreboard(title, items);
				scoreboard.sendToPlayer(player);

				return;
			}
		}

		for (Quest quest : Plugin.getWorldManager().getQuests()) {
			if (quest.getUniqueId().equals(trackedQuestId)) {
				QuestStep firstStep = quest.getSteps().peek();

				Component title = Component.text("Tracking - " + quest.getName());
				List<Component> items = List.of(
					Component.text("Task: " + firstStep.getDescription()),
					Component.text("Go to: [x,y,z]")
				);
				PlayerScoreboard scoreboard = new PlayerScoreboard(title, items);
				scoreboard.sendToPlayer(player);

				return;
			}
		}
	}

	public List<CustomMob> getMobsInCombat() {
		return mobsInCombat;
	}

	public UUID getTrackedQuest() {
		return trackedQuestId;
	}

	public RpgClass getActiveClass() {
		return activeClass;
	}
	
	public List<QuestInProgress> getQuestsInProgress() {
		return questsInProgress;
	}
	
}
