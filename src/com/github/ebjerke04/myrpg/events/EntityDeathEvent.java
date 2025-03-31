package com.github.ebjerke04.myrpg.events;

import java.util.UUID;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.entities.CustomMob;

public class EntityDeathEvent extends BaseEvent {

    public EntityDeathEvent() {
        super();
    }

    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        LivingEntity killed = event.getEntity();
        UUID killedId = killed.getUniqueId();

        CustomMob cMob = Plugin.getWorldManager().getCustomMob(killedId);
        if (cMob == null) return;

        for (Player player : cMob.getDamagers()) {
            player.sendMessage("You were involved in killing an entity");
        }
    }
    
}
