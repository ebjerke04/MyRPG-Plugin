package com.github.ebjerke04.myrpg.scripting;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.ebjerke04.myrpg.scripting.objects.PlayerScriptObject;
import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;

public class FunctionContainer {

    public void logToServer(PlayerScriptObject player) {
        Logging.sendConsole(Level.WARNING, "Position of " + player.name + ": " 
            + player.position.x + ", " + player.position.y + ", " + player.position.z);
    }

    public void messagePlayer(PlayerScriptObject scriptPlayer, String message) {
        Player player = scriptPlayerToBukkitPlayer(scriptPlayer);

        if (player == null) return;
        player.sendMessage(Component.text(message));
    }

    public void effectPlayer(PlayerScriptObject scriptPlayer, int effectId, int durationSeconds, int effectLevel) {
        Player player = scriptPlayerToBukkitPlayer(scriptPlayer);

        if (player == null) return;

        PotionEffectType effectType = PotionEffectType.getById(effectId);
        int durationTicks = durationSeconds * 20;
        player.addPotionEffect(new PotionEffect(
            effectType,
            durationTicks,
            effectLevel,
            true,
            true,
            true
        ));
    }

    private Player scriptPlayerToBukkitPlayer(PlayerScriptObject scriptPlayer) {
        String playerName = scriptPlayer.name;
        Player player = Bukkit.getPlayer(playerName);

        return player;
    }
    
}
