package com.github.ebjerke04.myrpg.quests;

import java.util.Stack;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

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
    public boolean attemptProgression(Player player) {
        // TODO: handle quest progression.
        // if requirement is met, pop QuestStep from stack and expose next step.
        boolean successful = true;
        if (getCurrentStep() instanceof QuestStepKillMob) {
            QuestStepKillMob killStep = (QuestStepKillMob) getCurrentStep();
            killStep.mobKilled();
            if (killStep.getAmountRemaining() > 0) {
                player.sendMessage(Component.text("Kill " + killStep.getAmountRemaining() + " more mobs"));
                successful = false;
            }
        }
        
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
