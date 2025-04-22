package com.github.ebjerke04.myrpg.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import com.github.ebjerke04.myrpg.economy.BankerNPC;
import com.github.ebjerke04.myrpg.entities.EntityDataHolder;
import com.github.ebjerke04.myrpg.items.RpgItemDataHolder;
import com.github.ebjerke04.myrpg.items.RpgItemType;
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
		List<EntityDataHolder> entityTemplates = new ArrayList<>();
		String path = "custom-mobs";

		try {
			Set<String> templateLocationPaths = getWorldData().getConfigurationSection(path).getKeys(false);

			for (String nameInConfig : templateLocationPaths) {
				String subPath = path + "." + nameInConfig + ".";
				
				String entityTypeString = getWorldData().getString(subPath + "entity-type");
				EntityType entityType = EntityType.fromName(entityTypeString);
				String displayName = getWorldData().getString(subPath + "display-name");
				int level = getWorldData().getInt(subPath + "level");
				int baseExperienceReward = getWorldData().getInt(subPath + "base-xp-reward");
				double maxHealth = getWorldData().getDouble(subPath + "max-health");

				EntityDataHolder templateData = new EntityDataHolder();
				templateData.mobName = nameInConfig;
				templateData.entityType = entityType;
				templateData.displayName = displayName;
				templateData.level = level;
				templateData.baseExperienceReward = baseExperienceReward;
				templateData.maxHealth = maxHealth;

				entityTemplates.add(templateData);
			}
		} catch (NullPointerException e) {
			Logging.sendConsole(Level.INFO, "No CustomMobs have been created yet.");
		}

		return entityTemplates;
	}

	public List<RpgItemDataHolder> loadRpgItemData() {
		List<RpgItemDataHolder> itemDataList = new ArrayList<>();
		String path = "rpg-items";

		try {
			Set<String> itemDataLocationPaths = getWorldData().getConfigurationSection(path).getKeys(false);

			for (String nameInConfig : itemDataLocationPaths) {
				String subPath = path + "." + nameInConfig + ".";
				
				String itemTypeString = getWorldData().getString(subPath + "item-type");
				RpgItemType itemType = RpgItemType.fromString(itemTypeString);
				int minimumLevel = getWorldData().getInt(subPath + "min-level");
				String materialName = getWorldData().getString(subPath + "material-name");
				Material itemMaterial = Material.getMaterial(materialName);
				String displayName = getWorldData().getString(subPath + "display-name");
				List<String> lore = getWorldData().getStringList(subPath + "lore");

				RpgItemDataHolder itemData = new RpgItemDataHolder();
				itemData.type = itemType;
				itemData.minimumLevel = minimumLevel;
				itemData.itemMaterial = itemMaterial;
				itemData.displayName = displayName;
				itemData.lore = lore;

				itemDataList.add(itemData);
			}
		} catch (NullPointerException e) {
			Logging.sendConsole(Level.INFO, "No RpgItems have been created yet.");
		}

		return itemDataList;
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
