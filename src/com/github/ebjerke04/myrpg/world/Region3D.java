package com.github.ebjerke04.myrpg.world;

import org.bukkit.Location;
import org.bukkit.World;

import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class Region3D {

    private final World world;
    private final Location minimum, maximum;

    public Region3D(Location corner1, Location corner2) {
        if (!corner1.getWorld().equals(corner2.getWorld()))
        {
            Logging.sendConsole(Component.text("Region3D cannot have two locations from different worlds.")
                .color(TextColor.color(0xFF0000)));
            throw new IllegalStateException();
        }
        this.world = corner1.getWorld();

        this.minimum = new Location(corner1.getWorld(),
            Math.min(corner1.getX(), corner2.getX()),
            Math.min(corner1.getY(), corner2.getY()),
            Math.min(corner1.getZ(), corner2.getZ())
        );

        this.maximum = new Location(corner1.getWorld(),
            Math.max(corner1.getX(), corner2.getX()),
            Math.max(corner1.getY(), corner2.getY()),
            Math.max(corner1.getZ(), corner2.getZ())
        );
    }

    public boolean isInRegion(Location location) {
        if (location.getWorld().equals(world)) {
            return location.getX() >= minimum.getX() && location.getX() <= maximum.getX() &&
                   location.getY() >= minimum.getY() && location.getY() <= maximum.getY() &&
                   location.getZ() >= minimum.getZ() && location.getZ() <= maximum.getZ();
        }
        return false;
    }
    
}
