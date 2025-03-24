package com.github.ebjerke04.myrpg.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.ebjerke04.myrpg.economy.BankerNPC;

public class WorldDataManager {

    private static WorldDataManager instance;
	
	private WorldDataManager() {
		ConfigManager.get().createConfig("world_data");
		ConfigManager.get().reloadConfig("world_data");
	}

    public List<BankerNPC> createBankingNPCs() {
        String path = "economy.banker-locations";
        Set<String> npcLocationPaths = getWorldData().getConfigurationSection(path).getKeys(false);

        List<BankerNPC> bankingNpcs = new ArrayList<>();
        for (String subPath : npcLocationPaths) {
            Location bankerLocation = getWorldData().getLocation(path + "." + subPath);
            bankingNpcs.add(new BankerNPC(bankerLocation));
        } 

        return bankingNpcs;
    }
	
	public static void init() {
		instance = new WorldDataManager();
	}
	
	public static WorldDataManager get() {
		return instance;
	}
	
	public void shutdown() {
		saveWorldData();
	}
	
	private FileConfiguration getWorldData() {
		return ConfigManager.get().getConfig("world_data");
	}
	
	private void saveWorldData() {
		ConfigManager.get().saveConfig("world_data");
	}
    
}
