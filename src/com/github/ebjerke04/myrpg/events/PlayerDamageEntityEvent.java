package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageEntityEvent extends BaseEvent {

    public PlayerDamageEntityEvent() {
        super();
    }
    
    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            player.sendMessage("Damage of " + event.getDamage() + " done");
        }
    }
    
}
