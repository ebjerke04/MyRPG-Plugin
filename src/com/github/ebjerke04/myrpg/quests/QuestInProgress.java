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
    
    public QuestInProgress(Quest quest) {
        respectiveId = quest.getUniqueId();
        name = quest.getName();

        this.steps.addAll(quest.getSteps());
    }

    /**
     * @return true if attempt at quest progression results in the quest being completed
     */
    public boolean attemptProgression() {
        // TODO: handle quest progression.
        // if requirement is met, pop QuestStep from stack and expose next step.
        steps.pop();

        if (steps.isEmpty()) {
            Logging.sendConsole(Component.text("Quest has been completed")
                .color(TextColor.color(0xFF00FF)));
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuestInProgress) {
            return ((QuestInProgress) obj).getRespectiveId().equals(respectiveId);
        }

        return false;
    }
    
}
