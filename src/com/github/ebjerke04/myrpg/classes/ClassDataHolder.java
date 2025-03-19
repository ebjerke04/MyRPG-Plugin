package com.github.ebjerke04.myrpg.classes;

import java.util.ArrayList;
import java.util.List;

import com.github.ebjerke04.myrpg.quests.QuestInProgress;

public class ClassDataHolder {
    
    public ClassType type;
    public int level;
    public int exp;

    public List<QuestInProgress> questsInProgress = new ArrayList<>();
    public List<String> questsCompleted = new ArrayList<>();
    
    public ClassDataHolder() {
        this.level = 1;
        this.exp = 0;
    }

}
