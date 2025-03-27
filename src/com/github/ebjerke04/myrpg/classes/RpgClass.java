package com.github.ebjerke04.myrpg.classes;

import java.util.List;

import com.github.ebjerke04.myrpg.quests.QuestInProgress;

public class RpgClass {

    private ClassType classType;

    private List<QuestInProgress> questsInProgress;
    private List<String> questsCompleted;

    public RpgClass(ClassType classType) {
        this.classType = classType;
    }

    public void setUserData(ClassDataHolder classData) {
        this.questsInProgress = classData.questsInProgress;
        this.questsCompleted = classData.questsCompleted;
    }

    public ClassType getType() {
        return classType;
    }

    public List<QuestInProgress> getQuestsInProgress() {
        return questsInProgress;
    }

    public void setQuestCompleted(String questName) {
        questsCompleted.add(questName);
    }

    public List<String> getQuestsCompleted() {
        return questsCompleted;
    }
    
}
