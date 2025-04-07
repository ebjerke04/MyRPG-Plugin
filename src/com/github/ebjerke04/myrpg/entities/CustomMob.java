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
import com.github.ebjerke04.myrpg.entities.ai.CustomMobAI;
import com.github.ebjerke04.myrpg.players.RpgPlayer;
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
    private Player target = null; 
    private String displayName;

    private CustomMobAttributes attributes;

    private CustomMobAI aiController = null;
    private ScriptComponent scriptComponent = null;

    /**
     * @see BELOW Constructor simply for templating a CustomMob as it is loaded from a config
     * @param mobName name of the mob
     * @param entityType mob type
     * @param maxHealth max health
     * @param displayName display name
     */
    public CustomMob(String mobName, EntityType entityType, String displayName, CustomMobAttributes attributes) {
        this.mobName = mobName;
        this.entityType = entityType;
        this.displayName = displayName;
        this.attributes = attributes;
    }

    private CustomMob(String mobName, EntityType entityType, String displayName, CustomMobAttributes attributes, Location location, ScriptComponent scriptComponent) {
        this.mobName = mobName;
        this.entityType = entityType;
        this.displayName = displayName;
        this.attributes = attributes;
        this.scriptComponent = scriptComponent;

        spawn(location);

        aiController = new CustomMobAI(this);

        boolean debug = false;
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

        return new CustomMob(mobName, entityType, displayName, attributes, location, scriptComponent);
    }

    private void spawn(Location location) {
        entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        
        setupEntity();
        id = entity.getUniqueId();
    }

    private void setupEntity() {
        entity.customName(Component.text(displayName));
        entity.setCustomNameVisible(true);

        entity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(attributes.maxHealth);
        entity.setHealth(attributes.maxHealth);

        // TEMP SETUP
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

    // TEMP SETUP
    private ItemStack createEnchantedItem(Material material) {
        ItemStack item = new ItemStack(material, 1);
        item.addEnchantment(Enchantment.PROTECTION, 1);
        return item;
    }

    public void receiveDamage(Player player) {
        addDamager(player);
        UUID playerId = player.getUniqueId();
        RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(playerId);
        rpgPlayer.setMobInCombat(this);

        // TODO: need a way to fetch a specific damage amount.
        // create custom weapons /& a formula to calculate damage.
        entity.damage(20.0);
    }

    public void setTarget(Player player) {
        this.target = player;
    }

    public Player getTarget() {
        return target;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void teleport(Location location) {
        entity.teleport(location);
    }

    private void addDamager(Player player) {
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

    public boolean isDead() {
        return entity.isDead();
    }

    private boolean isScripted() {
        return (scriptComponent == null);
    }
    
    public UUID getUniqueId() {
        return id;
    }

    private boolean isCleanedUp = false;
    public synchronized void cleanup() {
        if (isCleanedUp) return;

        if (aiController != null) {
            aiController.cleanup();
            aiController = null;
        }
        
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
