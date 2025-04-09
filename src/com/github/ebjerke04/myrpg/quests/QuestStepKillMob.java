package com.github.ebjerke04.myrpg.quests;

import java.util.List;

public class QuestStepKillMob extends QuestStep {

    private String mobName;

    public QuestStepKillMob(String mobName, String description, List<String> dialogue) {
        super(QuestStepType.KILL_MOB, description, dialogue);

        this.mobName = mobName;
    }

    public String getMobName() {
        return mobName;
    }
    
}
