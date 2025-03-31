package com.github.ebjerke04.myrpg.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

public class CustomMob {

    private final LivingEntity entity;
    private UUID id;

    private List<Player> damagers = new ArrayList<>();

    public CustomMob(Location spawnLocation) {
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

    public void addDamager(Player player) {
        for (Player damager : damagers) {
            if (damager.getUniqueId().equals(player.getUniqueId())) return;
        }
        
        damagers.add(player);
    }

    public void removeDamager(Player player) {
        for (int i = 0; i < damagers.size(); i++) {
            if (damagers.get(i).getUniqueId().equals(player.getUniqueId()))
            {
                damagers.remove(i);
                return;
            }
        }
    }

    public List<Player> getDamagers() {
        return damagers;
    }
    
    public UUID getUniqueId() {
        return id;
    }
    
}
