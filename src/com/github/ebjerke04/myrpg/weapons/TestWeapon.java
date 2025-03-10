package com.github.ebjerke04.myrpg.weapons;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.github.ebjerke04.myrpg.events.BaseEvent;

public class TestWeapon extends BaseEvent {
	
	public TestWeapon() {
		super();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerUseWand(PlayerInteractEvent event) {
		if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) return;
		
		Player player = event.getPlayer();
		
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item.getType() != Material.STICK) return;
		
		Vector direction = player.getEyeLocation().getDirection();
		List<Entity> nearbyEntities = player.getNearbyEntities(10.0, 10.0, 10.0);
		
		for (Entity entity : nearbyEntities) {
			if (entity instanceof LivingEntity && entity != player) {
				LivingEntity target = (LivingEntity) entity;
				
				Vector entityVector = entity.getLocation().toVector().subtract(player.getEyeLocation().toVector()).normalize();
				if (direction.dot(entityVector) > 0.98) {
					target.damage(500.0, player);
					player.sendMessage("You hit " + target.getName() + " with wand!");
				}
			}
		}
	}
	
}
