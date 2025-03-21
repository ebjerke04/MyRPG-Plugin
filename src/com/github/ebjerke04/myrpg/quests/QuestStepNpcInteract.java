package com.github.ebjerke04.myrpg.quests;

public class QuestStepNpcInteract extends QuestStep {

    // TODO: Maybe add dialogue list that gets sent to the player for this step/interaction

    private QuestNPC questNPC;

    public QuestStepNpcInteract(QuestNPC questNPC) {
        super(QuestStepType.NPC_INTERACT);

        this.questNPC = questNPC;
    }

    public QuestNPC getQuestNPC() {
        return questNPC;
    }

}