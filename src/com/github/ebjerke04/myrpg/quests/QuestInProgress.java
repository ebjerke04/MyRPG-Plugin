package com.github.ebjerke04.myrpg.quests;

import java.util.Stack;
import java.util.UUID;

public class QuestInProgress {

    private UUID respectiveId;

    private Stack<QuestStep> steps; 
    
    public QuestInProgress(Quest quest) {
        respectiveId = quest.getUniqueId();
        this.steps = quest.getSteps();
    }

    public UUID getRespectiveId() {
        return respectiveId;
    }
    
    public QuestStep getCurrentStep() {
        return steps.isEmpty() ? null : steps.peek();
    }
    
}
