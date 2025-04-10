package com.github.ebjerke04.myrpg.quests;

import java.util.List;

public class QuestStepKillMob extends QuestStep {

    private final String mobName;
    private final int amountRequired;

    private int amountRemaining;

    public QuestStepKillMob(String mobName, int amountRequired, String description, List<String> dialogue) {
        super(QuestStepType.KILL_MOB, description, dialogue);

        this.mobName = mobName;
        this.amountRequired = amountRequired;
        this.amountRemaining = amountRequired;
    }

    public void mobKilled() {
        amountRemaining--;
    }

    public String getMobName() {
        return mobName;
    }

    public int getAmountRemaining() {
        return amountRemaining;
    }

    public int getAmountRequired() {
        return amountRequired;
    }
    
}
