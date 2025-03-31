package com.github.ebjerke04.myrpg.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class AdventureToSpigot {

    public static String compToString(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }
    
}
