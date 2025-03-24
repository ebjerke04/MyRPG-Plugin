package com.github.ebjerke04.myrpg.players;

import java.util.ArrayList;
import java.util.List;

import com.github.ebjerke04.myrpg.classes.RpgClass;
import com.github.ebjerke04.myrpg.quests.QuestInProgress;

public class RpgPlayer {

	private RpgClass activeClass = null;
	private List<QuestInProgress> questsInProgress = new ArrayList<>();

	public RpgPlayer() {
		
	}

	public void setActiveClass(RpgClass activeClass) {
		this.activeClass = activeClass;

		// TODO: Player active class set. Figure out what is next.
		// Send player to last location they left off at.
		// Load their saved inventory.
	}

	public void assignQuest(QuestInProgress quest) {
		questsInProgress.add(quest);
	}

	public RpgClass getActiveClass() {
		return activeClass;
	}
	
	public List<QuestInProgress> getQuestsInProgress() {
		return questsInProgress;
	}
	
}
