package com.github.ebjerke04.myrpg.quests;

import java.util.Stack;
import java.util.UUID;

public class Quest {
	
	private final String NAME;
	private final int MIN_LEVEL;
	private UUID questId;

	private QuestNPC startNPC;
	private Stack<QuestStep> steps;
	
	public Quest(QuestDataHolder data) {
		this.NAME = data.name;
		this.MIN_LEVEL = data.minLevel;

		this.startNPC = data.startNPC;
		this.steps = data.steps;

		questId = UUID.randomUUID();
	}
	
	public String getName() {
		return NAME;
	}
	
	public int getMinLevel() {
		return MIN_LEVEL;
	}

	public UUID getUniqueId() {
		return questId;
	}

	public QuestNPC getStartNPC() {
		return startNPC;
	}

	public Stack<QuestStep> getSteps() {
		return steps;
	}

}
