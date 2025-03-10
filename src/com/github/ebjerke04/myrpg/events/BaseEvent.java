package com.github.ebjerke04.myrpg.events;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.ebjerke04.myrpg.Plugin;

public abstract class BaseEvent implements Listener {
	
	public BaseEvent() {
		JavaPlugin plugin = Plugin.get();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

}
