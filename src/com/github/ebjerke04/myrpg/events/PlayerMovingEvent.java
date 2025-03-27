package com.github.ebjerke04.myrpg.events;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.players.RpgPlayer;
import com.github.ebjerke04.myrpg.quests.QuestInProgress;
import com.github.ebjerke04.myrpg.quests.QuestStep;
import com.github.ebjerke04.myrpg.quests.QuestStepEnterArea;
import com.github.ebjerke04.myrpg.quests.QuestStepType;
import com.github.ebjerke04.myrpg.world.Region3D;

public class PlayerMovingEvent extends BaseEvent {

    public PlayerMovingEvent() {
        super();
    }

    @EventHandler
    public void onPlayerMoving(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        
        UUID playerId = player.getUniqueId();
        RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);

        List<QuestInProgress> questsInProgress = rpgPlayer.getQuestsInProgress();
        if (questsInProgress.isEmpty()) return;

        for (QuestInProgress questInProgress : questsInProgress) {
            QuestStep currentStep = questInProgress.getCurrentStep();
            if (currentStep.getType() == QuestStepType.ENTER_AREA) {
                QuestStepEnterArea enterStep = (QuestStepEnterArea) currentStep;
                Region3D stepRegion = enterStep.getRegion3D();
                if (stepRegion.isInRegion(player.getLocation())) {
                    rpgPlayer.attemptQuestProgression(questInProgress);
                    break;
                }
            }
        }
    }
    
}
