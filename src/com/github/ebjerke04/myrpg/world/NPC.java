package com.github.ebjerke04.myrpg.world;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.github.ebjerke04.myrpg.entities.ai.CustomNPCAI;
import com.github.ebjerke04.myrpg.quests.NPCDataHolder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;

public abstract class NPC extends Villager {

	private NPCDataHolder data;
	private UUID id;

	private LivingEntity villager;

	public NPC(NPCDataHolder data) {
		super(EntityType.VILLAGER, ((CraftWorld) data.location.getWorld()).getHandle());
		this.data = data;
	}

	public void spawn() {
		if (data.location == null) {
			Bukkit.getLogger().log(Level.SEVERE, "Location for NPC " + data.name + " was null, did not spawn");
			return;
		}
		
		ServerLevel serverLevel = ((CraftWorld) data.location.getWorld()).getHandle();
		this.setPos(data.location.getX(), data.location.getY(), data.location.getZ());
		serverLevel.addFreshEntity(this, SpawnReason.CUSTOM);

		villager = (LivingEntity) this.getBukkitEntity();
		villager.setRemoveWhenFarAway(false);
		villager.customName(Component.text(data.name).color(TextColor.color(0x00FF00)));
		villager.setCustomNameVisible(true);

		id = villager.getUniqueId();

		CustomNPCAI aiController = new CustomNPCAI(this);
	}

	@Override
	public void push(double d0, double d1, double d2) {
		// Override default push method when collision occurs
		this.setDeltaMovement(this.getDeltaMovement().add(0, 0, 0));
      	this.hasImpulse = true;
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

	public LivingEntity getEntity() {
		return villager;
	}
    
    public String getConfigName() {
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
