package com.github.ebjerke04.myrpg.quests;

import com.github.ebjerke04.myrpg.world.Region3D;

public class QuestStepEnterArea extends QuestStep {

    private Region3D region3D;

    public QuestStepEnterArea(Region3D region3D) {
        super(QuestStepType.ENTER_AREA);

        this.region3D = region3D;
    }

    public Region3D getRegion3D() {
        return region3D;
    }

}
