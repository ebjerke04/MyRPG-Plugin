package com.github.ebjerke04.myrpg.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

public class EntityDeathEvent extends BaseEvent {

    public EntityDeathEvent() {
        super();
    }

    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        Bukkit.getConsoleSender().sendMessage("Test");
    }
    
}
