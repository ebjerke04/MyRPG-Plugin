package com.github.ebjerke04.myrpg.entities;

import org.bukkit.entity.EntityType;

public class EntityDataHolder {

    public String mobName;
    public EntityType entityType;
    public String displayName;
    
    public int level;
    public int baseExperienceReward;
    public double maxHealth;

    public EntityDataHolder() {
        this.mobName = "";
        this.entityType = null;
        this.displayName = "";
        
        this.level = 1;
        this.baseExperienceReward = 10; 
        this.maxHealth = 20.0;
    }
    
}
