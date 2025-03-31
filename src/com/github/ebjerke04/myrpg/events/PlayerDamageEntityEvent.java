package com.github.ebjerke04.myrpg.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.entities.CustomMob;

public class PlayerDamageEntityEvent extends BaseEvent {

    public PlayerDamageEntityEvent() {
        super();
    }
    
    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            Entity damaged = event.getEntity();
            CustomMob cMob = Plugin.getWorldManager().getCustomMob(damaged.getUniqueId());
            if (cMob == null) return;

            cMob.addDamager(player);
        }
    }
    
}
