package com.github.ebjerke04.myrpg.entities;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import net.kyori.adventure.text.Component;

public class CustomBoss {

    private final LivingEntity entity;
    private UUID id;

    public CustomBoss(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
        
        setupEntity();
        id = entity.getUniqueId();
    }

    private void setupEntity() {
        entity.customName(Component.text("Test entity"));
        entity.setCustomNameVisible(true);

        entity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(100.0);
        entity.setHealth(100.0);
    }
    
    public UUID getUniqueId() {
        return id;
    }
    
}
