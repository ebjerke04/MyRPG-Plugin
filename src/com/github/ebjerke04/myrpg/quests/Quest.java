package com.github.ebjerke04.myrpg.quests;

public class Quest {
	
	private final String NAME;
	private final int MIN_LEVEL;

	private QuestNPC startNPC;
	
	public Quest(QuestDataHolder data) {
		this.NAME = data.name;
		this.MIN_LEVEL = data.minLevel;

		this.startNPC = data.startNPC;
	}
	
	public String getName() {
		return NAME;
	}
	
	public int getMinLevel() {
		return MIN_LEVEL;
	}

	public QuestNPC getStartNPC() {
		return startNPC;
	}

}
