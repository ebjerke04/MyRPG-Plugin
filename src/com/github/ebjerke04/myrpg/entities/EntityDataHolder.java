package com.github.ebjerke04.myrpg.entities;

import org.bukkit.entity.EntityType;

public class EntityDataHolder {

    public String mobName;
    public EntityType entityType;
    public double maxHealth;
    public String displayName;

    public EntityDataHolder() {
        this.mobName = "";
        this.entityType = null;
        this.maxHealth = 20.0;
        this.displayName = "";
    }
    
}
