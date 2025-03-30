package com.github.ebjerke04.myrpg.quests;

import java.util.List;

public class QuestStep {

    private QuestStepType stepType;
    private String description;
    private List<String> dialogue;
    private boolean dialoguing = false;

    public QuestStep(QuestStepType stepType, String description, List<String> dialogue) {
        this.stepType = stepType;
        this.description = description;
        this.dialogue = dialogue;
    }

    public QuestStepType getType() {
        return stepType;
    }

    public String getDescription() {
        return description;
    }

    public void setDialoguing(boolean status) {
        dialoguing = status;
    }

    public boolean isDialoguing() {
        return dialoguing;
    }

    public List<String> getDialogue() {
        return dialogue;
    }
    
}
