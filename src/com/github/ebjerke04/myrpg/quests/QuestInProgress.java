package com.github.ebjerke04.myrpg.quests;

import java.util.Stack;
import java.util.UUID;

import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

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

    /**
     * @return true if attempt at quest progression is successful
     */
    public boolean attemptProgression() {
        // TODO: handle quest progression.
        // if requirement is met, pop QuestStep from stack and expose next step.
        boolean succesful = true;
        if (succesful) {
            steps.pop();

            if (steps.isEmpty()) isComplete = true;
            return true;
        }

        return false;
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
