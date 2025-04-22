package com.github.ebjerke04.myrpg.items;

public enum RpgItemType {

    ARMOR_HELMET,
    ARMOR_CHESTPLATE,
    ARMOR_LEGGINGS,
    ARMOR_BOOTS,
    WEAPON_BOW,
    WEAPON_DAGGER,
    WEAPON_WAND,
    WEAPON_SWORD;

    public static RpgItemType fromString(String itemType) {
        if (itemType.equals("armor_helmet")) {
            return ARMOR_HELMET;
        } else if (itemType.equals("armor_chestplate")) {
            return ARMOR_CHESTPLATE;
        } else if (itemType.equals("armor_leggings")) {
            return ARMOR_LEGGINGS;
        } else if (itemType.equals("armor_boots")) {
            return ARMOR_BOOTS;
        } else if (itemType.equals("weapon_bow")) {
            return WEAPON_BOW;
        } else if (itemType.equals("weapon_dagger")) {
            return WEAPON_DAGGER;
        } else if (itemType.equals("weapon_wand")) {
            return WEAPON_WAND;
        } else if (itemType.equals("weapon_sword")) {
            return WEAPON_SWORD;
        }

        return null;
    }
    
}
