package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.ebjerke04.myrpg.Plugin;

public class PlayerDisconnectEvent extends BaseEvent {

    public PlayerDisconnectEvent() {
        super();
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Plugin.getPlayerManager().handlePlayerDisconnect(player);
    }
    
}
