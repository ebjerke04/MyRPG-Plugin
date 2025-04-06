package com.github.ebjerke04.myrpg.events;

import java.util.UUID;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.entities.CustomMob;

public class EntityDeathEvent extends BaseEvent {

    public EntityDeathEvent() {
        super();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        LivingEntity killed = event.getEntity();
        UUID killedId = killed.getUniqueId();

        CustomMob customMob = Plugin.getWorldManager().getCustomMob(killedId);
        if (customMob == null) return;

        Plugin.getWorldManager().handleCustomMobDeath(customMob);
    }
    
}
