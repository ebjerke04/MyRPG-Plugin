package com.github.ebjerke04.myrpg.util;

import org.bukkit.Bukkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;

public class Logging {

    private static final BungeeComponentSerializer serializer = BungeeComponentSerializer.get();

    private static Component tag = Component.text("[").color(TextColor.color(0x00FFFF))
        .append(Component.text("MyRPG").color(TextColor.color(0xFFD700)))
        .append(Component.text("] ").color(TextColor.color(0x00FFFF)));

    public static void sendConsole(Component message) {
        Bukkit.getConsoleSender().spigot().sendMessage(serializer.serialize(tag.append(message)));
    }
    
}
