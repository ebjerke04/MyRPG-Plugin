package com.github.ebjerke04.myrpg.entities;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.scripting.ScriptComponent;
import com.github.ebjerke04.myrpg.scripting.objects.PositionScriptObject;
import com.github.ebjerke04.myrpg.scripting.objects.PlayerScriptObject;
import com.github.ebjerke04.myrpg.util.Logging;

import net.kyori.adventure.text.Component;

public class CustomMob {

    private LivingEntity entity;
    private UUID id;
    private List<Player> damagers = new CopyOnWriteArrayList<>();

    private String mobName;
    private EntityType entityType;
    private double maxHealth;
    private String displayName;

    private ScriptComponent scriptComponent = null;

    /**
     * @see BELOW Constructor simply for templating a CustomMob as it is loaded from a config
     * @param mobName name of the mob
     * @param entityType mob type
     * @param maxHealth max health
     * @param displayName display name
     */
    public CustomMob(String mobName, EntityType entityType, double maxHealth, String displayName) {
        this.mobName = mobName;
        this.entityType = entityType;
        this.maxHealth = maxHealth;
        this.displayName = displayName;
    }

    private CustomMob(String mobName, EntityType entityType, double maxHealth, String displayName, Location location, ScriptComponent scriptComponent) {
        this.mobName = mobName;
        this.entityType = entityType;
        this.maxHealth = maxHealth;
        this.displayName = displayName;
        this.scriptComponent = scriptComponent;

        spawn(location);

        boolean debug = true;
		if (debug) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Logging.sendConsole(Level.INFO, mobName + ": " + damagers.size());

                    if (scriptComponent != null) {
                        // THIS WORKS!
                        Player player = Bukkit.getPlayer("NoHaxJustTopG");
                        PlayerScriptObject playerObject = new PlayerScriptObject();
                        PositionScriptObject playerLoc = new PositionScriptObject();
                        playerLoc.x = player.getLocation().getX();
                        playerLoc.y = player.getLocation().getY();
                        playerLoc.z = player.getLocation().getZ();
                        playerObject.name = player.getName();
                        playerObject.position = playerLoc;
                        
                        scriptComponent.invokeFunction("testFunction", playerObject);
                    }
                }
            }.runTaskTimer(Plugin.get(), 60L, 60L);
        }
    }

    public void makeScripted(String scriptName) {
        this.scriptComponent = new ScriptComponent(scriptName);
    }

    public CustomMob spawnFromTemplate(Location location) {
        if (entity != null) {
            throw new IllegalStateException("Cannot spawn from non-template mob");
        }

        return new CustomMob(mobName, entityType, maxHealth, displayName, location, scriptComponent);
    }

    private void spawn(Location location) {
        entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        
        setupEntity();
        id = entity.getUniqueId();
    }

    private void setupEntity() {
        entity.customName(Component.text(displayName));
        entity.setCustomNameVisible(true);

        entity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);

        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            equipment.setHelmetDropChance(0.0f);
            equipment.setChestplateDropChance(0.0f);
            equipment.setLeggingsDropChance(0.0f);
            equipment.setBootsDropChance(0.0f);
            equipment.setItemInMainHandDropChance(0.0f);
            equipment.setItemInOffHandDropChance(0.0f);

            equipment.setHelmet(createEnchantedItem(Material.DIAMOND_HELMET));
            equipment.setChestplate(createEnchantedItem(Material.DIAMOND_CHESTPLATE));
            equipment.setLeggings(createEnchantedItem(Material.DIAMOND_LEGGINGS));
            equipment.setBoots(createEnchantedItem(Material.DIAMOND_BOOTS));

            ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
            weapon.addEnchantment(Enchantment.SHARPNESS, 1);
            equipment.setItemInMainHand(weapon);
        }
    }

    private ItemStack createEnchantedItem(Material material) {
        ItemStack item = new ItemStack(material, 1);
        item.addEnchantment(Enchantment.PROTECTION, 1);
        return item;
    }

    // ADD CODE HERE

    // -------------

    public void addDamager(Player player) {
        for (Player damager : damagers) {
            if (damager.getUniqueId().equals(player.getUniqueId())) return;
        }

        damagers.add(player);
    }

    public void removeDamager(Player player) {
        for (int i = 0; i < damagers.size(); i++) {
            if (damagers.get(i).getUniqueId().equals(player.getUniqueId()))
            {
                damagers.remove(i);
                return;
            }
        }
    }

    public List<Player> getDamagers() {
        return damagers;
    }

    public Location getLocation() {
        if (entity != null) {
            return entity.getLocation();
        }

        return null;
    }
    
    public UUID getUniqueId() {
        return id;
    }

    private boolean isCleanedUp = false;
    public synchronized void cleanup() {
        if (isCleanedUp) return;

        if (entity != null) {
            entity.remove();
            entity = null;
        }

        if (damagers != null) {
            damagers.clear();
            damagers = null;
        }

        id = null;
        isCleanedUp = true;
    }
    
}
