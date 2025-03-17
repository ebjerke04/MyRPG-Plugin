package com.github.ebjerke04.myrpg.quests;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class QuestNPC {
	
	private NPCDataHolder data;
	
	private UUID id;
	
	public QuestNPC(NPCDataHolder data) {
		this.data = data;
	}
	
	public void spawn() {
		Bukkit.getLogger().log(Level.SEVERE, "TRYING TO LOAD NPC: " + data.name);

		Villager villager = data.location.getWorld().spawn(data.location, Villager.class);
		
		villager.setRemoveWhenFarAway(false);
		villager.setAI(false);
		villager.customName(Component.text(data.name).color(TextColor.color(0x00FF00)));
		villager.setCustomNameVisible(true);
		
		id = villager.getUniqueId();
	}
	
	public void rightClicked(Player player) {
		player.sendMessage(Component.text("You clicked NPC: " + data.name));
	}
	
	public UUID getUniqueId() {
		return id;
	}

}
