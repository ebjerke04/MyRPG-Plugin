package com.github.ebjerke04.myrpg.entities;

import java.util.List;

import com.github.ebjerke04.myrpg.data.QuestDataManager;

public class FunctionContainer {

    public int getPlayerLocation(String name) {
        List<String> questNames = QuestDataManager.get().getQuestNames();

        if (questNames.contains(name)) return 1;

        return 0;
    }
    
}
