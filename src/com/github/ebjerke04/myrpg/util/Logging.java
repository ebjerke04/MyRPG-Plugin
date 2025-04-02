package com.github.ebjerke04.myrpg.util;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class Logging {

    private static Component tag = Component.text("[").color(TextColor.color(0x00FFFF))
        .append(Component.text("MyRPG").color(TextColor.color(0xFFD700)))
        .append(Component.text("] ").color(TextColor.color(0x00FFFF)));

    public static void sendConsole(Level level, String message) {
        TextColor textColor = TextColor.color(0xFFFFFF);
        
        if (level == Level.INFO) {
            textColor = TextColor.color(0x00FF00);
        } else if (level == Level.WARNING) {
            textColor = TextColor.color(0xFFFF00);
        } else if (level == Level.SEVERE) {
            textColor = TextColor.color(0xFF0000);
        }

        Bukkit.getConsoleSender().sendMessage(tag.append(
            Component.text(message).color(textColor)));
    }
    
}
