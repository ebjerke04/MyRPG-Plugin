package com.github.ebjerke04.myrpg.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import com.github.ebjerke04.myrpg.economy.BankerNPC;
import com.github.ebjerke04.myrpg.entities.EntityDataHolder;
import com.github.ebjerke04.myrpg.util.Logging;

public class WorldDataManager {

    private static WorldDataManager instance;
	
	private WorldDataManager() {
		ConfigManager.get().createConfig("world_data");
		ConfigManager.get().reloadConfig("world_data");
	}

    public List<BankerNPC> createBankingNPCs() {
        String path = "economy.banker-locations";

		List<BankerNPC> bankingNpcs = new ArrayList<>();
		try {
			Set<String> npcLocationPaths = getWorldData().getConfigurationSection(path).getKeys(false);
	
			for (String subPath : npcLocationPaths) {
				Location bankerLocation = getWorldData().getLocation(path + "." + subPath);
				bankingNpcs.add(new BankerNPC(bankerLocation));
			} 
		} catch (NullPointerException e) {
			Logging.sendConsole(Level.INFO, "No BankingNPCs have been created yet.");
		}

		return bankingNpcs;
    }

	public List<EntityDataHolder> loadCustomMobTemplates() {
		String path = "custom-mobs";

		List<EntityDataHolder> entityTemplates = new ArrayList<>();

		try {
			Set<String> templateLocationPaths = getWorldData().getConfigurationSection(path).getKeys(false);

			for (String nameInConfig : templateLocationPaths) {
				String subPath = path + "." + nameInConfig + ".";
				
				String entityTypeString = getWorldData().getString(subPath + "entity-type");
				
				EntityType entityType = EntityType.fromName(entityTypeString);
				double maxHealth = getWorldData().getDouble(subPath + "max-health");
				String displayName = getWorldData().getString(subPath + "display-name");

				EntityDataHolder templateData = new EntityDataHolder();
				templateData.mobName = nameInConfig;
				templateData.entityType = entityType;
				templateData.maxHealth = maxHealth;
				templateData.displayName = displayName;

				entityTemplates.add(templateData);
			}
		} catch (NullPointerException e) {
			Logging.sendConsole(Level.INFO, "No CustomMobs have been created yet.");
		}

		return entityTemplates;
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
