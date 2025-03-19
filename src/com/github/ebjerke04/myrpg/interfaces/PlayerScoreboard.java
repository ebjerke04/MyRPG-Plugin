package com.github.ebjerke04.myrpg.interfaces;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.kyori.adventure.text.Component;

public class PlayerScoreboard {
    
    private Scoreboard scoreboard;
    private Objective objective;

    public PlayerScoreboard(Component title, List<Component> content) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();

        objective = scoreboard.registerNewObjective("side_board", Criteria.DUMMY, title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (int i = 0; i < content.size(); i++) {
            int lineNumber = i + 1;
            
            Team newLine = scoreboard.registerNewTeam("line" + Integer.toString(lineNumber));
            newLine.prefix(content.get(i));
            
            String entry = "";
            for (int j = 0; j < lineNumber; j++) {
                entry += " ";
            }
            newLine.addEntry(entry);
            
            objective.getScore(entry).setScore(content.size() - i);
        }
    }

    public void sendToPlayer(Player player) {
        player.setScoreboard(scoreboard);
    }

}