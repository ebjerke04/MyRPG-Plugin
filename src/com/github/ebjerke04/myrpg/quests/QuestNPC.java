package com.github.ebjerke04.myrpg.quests;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.ebjerke04.myrpg.Plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class QuestNPC {
	
	private Location location;
	private World world;
	
	private UUID id;
	
	public QuestNPC() {
		world = Bukkit.getWorld("world");
		location = new Location(world, -28.0, 80.0, 90.0);
	}
	
	public void spawn() {
		Villager villager = world.spawn(location, Villager.class);
		
		villager.setRemoveWhenFarAway(false);
		villager.setAI(false);
		villager.customName(Component.text("Test Villager").color(TextColor.color(0x00FF00)));
		villager.setCustomNameVisible(true);
		
		id = villager.getUniqueId();
		
		new BukkitRunnable() {
			int count = 0;
			
			@Override
			public void run() {
				count++;
				
				villager.customName(Component.text(Integer.toString(count)).color(TextColor.color(0x00FF00)));
			}
		}.runTaskTimer(Plugin.get(), 0, 10);
	}
	
	public void rightClicked(Player player) {
		player.sendMessage("Event passed on to class");
	}
	
	public UUID getUniqueId() {
		return id;
	}

}
