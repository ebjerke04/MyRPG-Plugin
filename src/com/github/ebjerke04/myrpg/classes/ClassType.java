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

    public static ClassType fromString(String classType) {
        if (classType.equals("archer")) {
            return ARCHER;
        } else if (classType.equals("warrior")) {
            return WARRIOR;
        } else if (classType.equals("mage")) {
            return MAGE;
        } else if (classType.equals("assassin")) {
            return ASSASSIN;
        }

        return null;
    }

}
