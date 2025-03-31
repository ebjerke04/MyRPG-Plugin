package com.github.ebjerke04.myrpg.events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.players.RpgPlayer;

public class PlayerDeathEvent extends BaseEvent {

    public PlayerDeathEvent() {
        super();
    }

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);

        rpgPlayer.handlePlayerDeath();
    }
    
}
