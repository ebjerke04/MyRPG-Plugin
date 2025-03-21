package com.github.ebjerke04.myrpg.quests;

public class QuestStep {

    private QuestStepType stepType;

    public QuestStep(QuestStepType stepType) {
        this.stepType = stepType;
    }

    public QuestStepType getType() {
        return stepType;
    }
    
}
