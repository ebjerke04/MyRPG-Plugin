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
import com.github.ebjerke04.myrpg.events.EventManager;
import com.github.ebjerke04.myrpg.players.PlayerManager;
import com.github.ebjerke04.myrpg.quests.QuestManager;

public class Plugin extends JavaPlugin {
	
	private static Plugin instance;
	
	private QuestManager questManager;
	private PlayerManager playerManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		ConfigManager.init();
		PlayerDataManager.init();
		QuestDataManager.init();
		
		questManager = new QuestManager();
		playerManager = new PlayerManager();
		
		CommandManager.registerCommands();
		
		EventManager.registerEvents();
		
		RpgCreationManager.init();
		
		this.getLogger().log(Level.INFO, "Initialized MyRPG @ Version: 1.0");

		for (Player player : Bukkit.getOnlinePlayers()) {
			playerManager.handlePlayerConnect(player);
			this.getLogger().log(Level.INFO, player.displayName() + " was registered with MyRpg");
		}
	}
	
	@Override
	public void onDisable() {
		PlayerDataManager.get().shutdown();
		QuestDataManager.get().shutdown();
		
		RpgCreationManager.get().shutdown();
	}
	
	public static QuestManager getQuestManager() {
		return instance.questManager;
	}
	
	public static PlayerManager getPlayerManager() {
		return instance.playerManager;
	}
	
	public static Plugin get() {
		return instance;
	}

}
