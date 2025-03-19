package com.github.ebjerke04.myrpg.quests;

import java.util.Stack;

public class QuestInProgress {

    private Stack<QuestStep> steps;

    public QuestInProgress(Quest quest) {
        this.steps = quest.getSteps();
    }

    public QuestStep getCurrentStep() {
        return steps.isEmpty() ? null : steps.peek();
    }
    
}
