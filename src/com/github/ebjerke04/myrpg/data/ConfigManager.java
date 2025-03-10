package com.github.ebjerke04.myrpg.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.ebjerke04.myrpg.Plugin;

public class ConfigManager {
	
	private static ConfigManager instance;

	private final JavaPlugin plugin;
	
	private final Map<String, FileConfiguration> configs = new HashMap<>();
	private final Map<String, File> configFiles = new HashMap<>();
	
	public ConfigManager() {
		this.plugin = Plugin.get();
		
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		setupDefaultConfig();
	}
	
	public static void init() {
		instance = new ConfigManager();
	}
	
	public static ConfigManager get() {
		return instance;
	}
	
	private void setupDefaultConfig() {
		plugin.saveDefaultConfig();
		configs.put("config", plugin.getConfig());
		configFiles.put("config", new File(plugin.getDataFolder(), "config.yml"));
		plugin.reloadConfig();
	}
	
	public void createConfig(String name) {
		if (configs.containsKey(name)) return;
		
		File file = new File(plugin.getDataFolder(), name + ".yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().severe("Could not create " + name + ".yml!");
				e.printStackTrace();
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		configs.put(name, config);
		configFiles.put(name, file);
	}
	
	public FileConfiguration getConfig(String name) {
		return configs.get(name);
	}
	
	public void saveConfig(String name) {
		if (configs.containsKey(name)) {
			try {
				configs.get(name).save(configFiles.get(name));
			} catch (IOException e) {
				plugin.getLogger().severe("Could not save " + name + ".yml!");
				e.printStackTrace();
			}
		}
	}
	
	public void reloadConfig(String name) {
		if (configs.containsKey(name)) {
			configs.put(name, YamlConfiguration.loadConfiguration(configFiles.get(name)));
		}
	}
	
}
