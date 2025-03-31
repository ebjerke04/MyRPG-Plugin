package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class PlayerDeathEvent extends BaseEvent {

    public PlayerDeathEvent() {
        super();
    }

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getPlayer();
    }
    
}
