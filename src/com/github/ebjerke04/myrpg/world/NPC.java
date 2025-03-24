package com.github.ebjerke04.myrpg.world;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import com.github.ebjerke04.myrpg.quests.NPCDataHolder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public abstract class NPC {

    private NPCDataHolder data;
    private UUID id;

    public NPC(NPCDataHolder data) {
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

    public abstract void rightClicked(Player player);
    
    public String getName() {
		return data.name;
	}
	
	public UUID getUniqueId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NPC) {
			NPC npc = (NPC) obj;
			if (npc.getUniqueId() == this.getUniqueId()) return true;
		}

		return false;
	}

}
