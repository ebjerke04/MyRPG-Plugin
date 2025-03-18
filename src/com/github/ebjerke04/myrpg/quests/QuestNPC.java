package com.github.ebjerke04.myrpg.quests;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import com.github.ebjerke04.myrpg.Plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class QuestNPC {
	
	private NPCDataHolder data;
	
	private UUID id;
	
	public QuestNPC(NPCDataHolder data) {
		this.data = data;
	}
	
	public void spawn() {
		if (data.location == null) {
			Bukkit.getLogger().log(Level.SEVERE, "Location for NPC " + data.name + " was null, did not spawn");
			return;
		}

		Villager villager = data.location.getWorld().spawn(data.location, Villager.class);
		
		villager.setRemoveWhenFarAway(false);
		villager.setAI(false);
		villager.customName(Component.text(data.name).color(TextColor.color(0x00FF00)));
		villager.setCustomNameVisible(true);
		
		id = villager.getUniqueId();
	}

	public void despawn() {
		Chunk chunk = data.location.getChunk();
		if (!chunk.isLoaded()) chunk.load();

		Entity entity = Bukkit.getEntity(id);
		if (entity != null) {
			entity.remove();
		}
	}
	
	public void rightClicked(Player player) {
		player.sendMessage(Component.text("You clicked NPC: " + data.name));

		List<Quest> availableQuests = Plugin.getQuestManager().getQuestsForNPC(this);
		if (!availableQuests.isEmpty()) {
			for (Quest quest : availableQuests) {
				player.sendMessage(Component.text("Quest: " + quest.getName() + " is available!")
					.color(TextColor.color(0xFF00FF)));
			}
		}
	}

	public String getName() {
		return data.name;
	}
	
	public UUID getUniqueId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof QuestNPC) {
			QuestNPC npc = (QuestNPC) obj;
			if (npc.getUniqueId() == this.getUniqueId()) return true;
		}

		return false;
	}

}
