package com.github.ebjerke04.myrpg;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.ebjerke04.myrpg.commands.CommandManager;
import com.github.ebjerke04.myrpg.creation.RpgCreationManager;
import com.github.ebjerke04.myrpg.data.ConfigManager;
import com.github.ebjerke04.myrpg.data.PlayerDataManager;
import com.github.ebjerke04.myrpg.data.QuestDataManager;
import com.github.ebjerke04.myrpg.data.WorldDataManager;
import com.github.ebjerke04.myrpg.events.EventManager;
import com.github.ebjerke04.myrpg.players.PlayerManager;
import com.github.ebjerke04.myrpg.scripting.ScriptComponent;
import com.github.ebjerke04.myrpg.util.Logging;
import com.github.ebjerke04.myrpg.world.WorldManager;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class Plugin extends JavaPlugin {
	
	private static Plugin instance;
	
	private WorldManager worldManager;
	private PlayerManager playerManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		ConfigManager.init();
		WorldDataManager.init();
		PlayerDataManager.init();
		QuestDataManager.init();
		
		worldManager = new WorldManager();
		worldManager.init();

		playerManager = new PlayerManager();
		
		CommandManager.registerCommands();
		
		EventManager.registerEvents();
		
		RpgCreationManager.init();

		// TODO: More refined system of handling a server reload.
		// Simply calling the handlePlayerConnect function is not the best looking way of handling this.
		// Good temporary fix to aid in making testing of new features quick and simple.
		for (Player player : Bukkit.getOnlinePlayers()) {
			playerManager.handlePlayerConnect(player);
			String playerName = PlainTextComponentSerializer.plainText().serialize(player.displayName());
			Logging.sendConsole(Level.INFO, playerName + " has been registered with MyRPG");
		}
	}
	
	@Override
	public void onDisable() {
		worldManager.shutdown();
		
		PlayerDataManager.get().shutdown();
		QuestDataManager.get().shutdown();
		
		RpgCreationManager.get().shutdown();
	}
	
	public static WorldManager getWorldManager() {
		return instance.worldManager;
	}
	
	public static PlayerManager getPlayerManager() {
		return instance.playerManager;
	}
	
	public static Plugin get() {
		return instance;
	}

}
