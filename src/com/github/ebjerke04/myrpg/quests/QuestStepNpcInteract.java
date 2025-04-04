package com.github.ebjerke04.myrpg.quests;

import java.util.List;

public class QuestStepNpcInteract extends QuestStep {

    private QuestNPC questNPC;

    public QuestStepNpcInteract(QuestNPC questNPC, String description, List<String> dialogue) {
        super(QuestStepType.NPC_INTERACT, description, dialogue);

        this.questNPC = questNPC;
    }

    public QuestNPC getQuestNPC() {
        return questNPC;
    }

}