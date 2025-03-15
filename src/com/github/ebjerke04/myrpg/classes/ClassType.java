package com.github.ebjerke04.myrpg.classes;

public enum ClassType {
    
    ARCHER("Archer", "Master of ranged combat"),
    WARRIOR("Warrior", "Skilled with sword and shield"),
    MAGE("Mage", "Wielder of arcane magic"),
    ASSASSIN("Assassin", "Expert in stealth and daggers");

    private final String displayName;
    private final String description;

    ClassType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

}
