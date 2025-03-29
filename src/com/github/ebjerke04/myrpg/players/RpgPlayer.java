package com.github.ebjerke04.myrpg.players;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.classes.RpgClass;
import com.github.ebjerke04.myrpg.quests.QuestInProgress;

import net.kyori.adventure.text.Component;

public class RpgPlayer {

	private Player player;
	private RpgClass activeClass = null;
	private List<QuestInProgress> questsInProgress = new ArrayList<>();

	public RpgPlayer(Player player) {
		this.player = player;
	}

	public void setActiveClass(RpgClass activeClass) {
		this.activeClass = activeClass;

		// TODO: Player active class set. Figure out what is next.
		// Send player to last location they left off at.
		// Load their saved inventory.
	}

	public void assignQuest(QuestInProgress quest) {
		quest.attemptProgression();
		questsInProgress.add(quest);
	}

	public void attemptQuestProgression(QuestInProgress quest) {
		if (quest.attemptProgression()) {
			// progression successful, do stuff here

			if (quest.isComplete()) {
				for (int i = 0; i < questsInProgress.size(); i++) {
					if (questsInProgress.get(i).equals(quest)) {
						activeClass.setQuestCompleted(quest.getName());
						player.sendMessage(Component.text("Quest has been completed!"));
						questsInProgress.remove(i);
						break;
					}
				}
			}
		}
	}

	public RpgClass getActiveClass() {
		return activeClass;
	}
	
	public List<QuestInProgress> getQuestsInProgress() {
		return questsInProgress;
	}
	
}
