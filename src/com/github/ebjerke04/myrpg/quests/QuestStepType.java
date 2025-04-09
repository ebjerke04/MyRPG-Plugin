package com.github.ebjerke04.myrpg.quests;

public enum QuestStepType {

    NPC_INTERACT,
    KILL_MOB,
    ENTER_AREA;

    public static QuestStepType fromString(String classType) {
        if (classType.equals("npc_interact")) {
            return NPC_INTERACT;
        } else if (classType.equals("kill_mob")) {
            return KILL_MOB;
        } else if (classType.equals("enter_area")) {
            return ENTER_AREA;
        }

        return null;
    }

}