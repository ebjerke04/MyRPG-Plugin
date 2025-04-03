package com.github.ebjerke04.myrpg.entities;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;

public class CustomMob {

    private LivingEntity entity;
    private UUID id;
    private List<Player> damagers = new CopyOnWriteArrayList<>();

    private String mobName;
    private EntityType entityType;
    private double maxHealth;
    private String displayName;

    /**
     * @see BELOW Constructor simply for templating a CustomMob as it is loaded from a config
     * @param mobName name of the mob
     * @param entityType mob type
     * @param maxHealth max health
     * @param displayName display name
     */
    public CustomMob(String mobName, EntityType entityType, double maxHealth, String displayName) {
        this.mobName = mobName;
        this.entityType = entityType;
        this.maxHealth = maxHealth;
        this.displayName = displayName;
    }

    private CustomMob(String mobName, EntityType entityType, double maxHealth, String displayName, Location location) {
        this.mobName = mobName;
        this.entityType = entityType;
        this.maxHealth = maxHealth;
        this.displayName = displayName;

        spawn(location);
    }

    public CustomMob spawnFromTemplate(Location location) {
        if (entity != null) {
            throw new IllegalStateException("Cannot spawn from non-template mob");
        }

        return new CustomMob(mobName, entityType, maxHealth, displayName, location);
    }

    private void spawn(Location location) {
        entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        
        setupEntity();
        id = entity.getUniqueId();
    }

    private void setupEntity() {
        entity.customName(Component.text(displayName));
        entity.setCustomNameVisible(true);

        entity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);
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

    private boolean isCleanedUp = false;
    public synchronized void cleanup() {
        if (isCleanedUp) return;

        if (entity != null) {
            entity.remove();
            entity = null;
        }

        if (damagers != null) {
            damagers.clear();
            damagers = null;
        }

        id = null;
        isCleanedUp = true;
    }
    
}
