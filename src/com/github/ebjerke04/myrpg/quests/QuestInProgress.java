package com.github.ebjerke04.myrpg.quests;

import java.util.Stack;
import java.util.UUID;

public class QuestInProgress {

    private UUID respectiveId;

    private Stack<QuestStep> steps = new Stack<>(); 
    
    public QuestInProgress(Quest quest) {
        respectiveId = quest.getUniqueId();

        this.steps.addAll(quest.getSteps());
    }

    public void attemptProgression() {
        // TODO: handle quest progression.
        // if requirement is met, pop QuestStep from stack and expose next step.
    }

    public UUID getRespectiveId() {
        return respectiveId;
    }
    
    public QuestStep getCurrentStep() {
        return steps.isEmpty() ? null : steps.peek();
    }
    
}
