package com.github.ebjerke04.myrpg.quests;

import java.util.List;

public class QuestStepKillBoss extends QuestStep {

    private String bossName;

    public QuestStepKillBoss(String bossName, String description, List<String> dialogue) {
        super(QuestStepType.KILL_BOSS, description, dialogue);

        this.bossName = bossName;
    }

    public String getBossName() {
        return bossName;
    }
    
}
