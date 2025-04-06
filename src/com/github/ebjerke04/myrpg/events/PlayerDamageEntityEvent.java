package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.entities.CustomMob;

public class PlayerDamageEntityEvent extends BaseEvent {

    public PlayerDamageEntityEvent() {
        super();
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            Entity damaged = event.getEntity();
            CustomMob customMob = Plugin.getWorldManager().getCustomMob(damaged.getUniqueId());
            
            if (customMob == null) return;
            event.setCancelled(true); 
            // Canceling event, we want to handle how damage is done to the mob ourselves.

            customMob.receiveDamage(player);
        }
    }
    
}
