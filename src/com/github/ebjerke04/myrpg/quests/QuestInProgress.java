package com.github.ebjerke04.myrpg.quests;

import java.util.Stack;
import java.util.UUID;

public class QuestInProgress {

    private UUID respectiveId;

    private Stack<QuestStep> steps = new Stack<>(); 
    private String name;

    private boolean isComplete = false;
    
    public QuestInProgress(Quest quest) {
        respectiveId = quest.getUniqueId();
        name = quest.getName();

        this.steps.addAll(quest.getSteps());
    }

    public void revealNextStep() {
        steps.pop();

        if (steps.isEmpty()) isComplete = true;
    }
    
    /**
     * @return true if attempt at quest progression is successful
     */
    public boolean attemptProgression() {
        // TODO: handle quest progression.
        // if requirement is met, pop QuestStep from stack and expose next step.
        boolean successful = true;
        
        return successful;
    }

    public UUID getRespectiveId() {
        return respectiveId;
    }
    
    public QuestStep getCurrentStep() {
        return steps.isEmpty() ? null : steps.peek();
    }

    public String getName() {
        return name;
    }

    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuestInProgress) {
            return ((QuestInProgress) obj).getRespectiveId().equals(respectiveId);
        }

        return false;
    }
    
}
