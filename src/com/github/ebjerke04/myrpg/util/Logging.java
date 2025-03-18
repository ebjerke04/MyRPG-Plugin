package com.github.ebjerke04.myrpg.util;

import org.bukkit.Bukkit;

import net.kyori.adventure.text.Component;

public class Logging {

    public static void sendConsole(Component message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }
    
}
