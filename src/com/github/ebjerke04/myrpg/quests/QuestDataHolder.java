package com.github.ebjerke04.myrpg.quests;

import java.util.Stack;

public class QuestDataHolder {
	
	public String name;
	public int minLevel;

	public QuestNPC startNPC;
	public Stack<QuestStep> steps;

}
