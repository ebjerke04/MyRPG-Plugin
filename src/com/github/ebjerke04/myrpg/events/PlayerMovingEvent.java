package com.github.ebjerke04.myrpg.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.ebjerke04.myrpg.world.Region3D;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class PlayerMovingEvent extends BaseEvent {

    public PlayerMovingEvent() {
        super();
    }

    @EventHandler
    public void onPlayerMoving(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location corner1 = new Location(player.getWorld(), -21.0, 78.0, 95.0);
        Location corner2 = new Location(player.getWorld(), -32.0, 84.0, 85.0);
        Region3D region = new Region3D(corner1, corner2);
        
        if (region.isInRegion(player.getLocation())) {
            player.sendMessage(Component.text("You are in this region!")
                .color(TextColor.color(0x0000FF)));
        }
    }
    
}
