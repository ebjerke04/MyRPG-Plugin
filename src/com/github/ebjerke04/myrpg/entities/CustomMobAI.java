package com.github.ebjerke04.myrpg.entities;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.util.Logging;

import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveTowardsTarget;
import net.minecraft.world.entity.player.EntityHuman;

public class CustomMobAI extends BukkitRunnable {
    private final double MAX_DISTANCE_DETECT = 30.0;
    private final double MIN_ATTACK_RANGE = 2.0;

    private CustomMob customMob;

    // NMS test
    private final EntityCreature nmsEntity;

    public CustomMobAI(CustomMob customMob) {
        this.customMob = customMob;

        CraftEntity craftEntity = (CraftEntity) customMob.getEntity();
        if (!(craftEntity.getHandle() instanceof EntityCreature)) {
            throw new IllegalStateException();
        }
        this.nmsEntity = (EntityCreature) craftEntity.getHandle();

        setupAIGoals();
        //

        final long updateTickRate = 5L;
        this.runTaskTimer(Plugin.get(), 0L, updateTickRate); // 4 updates/second
    }

    private void setupAIGoals() {
        nmsEntity.bS.a();
        nmsEntity.bS.a(0, new PathfinderGoalMoveTowardsTarget(nmsEntity, 1.0D, (float)MAX_DISTANCE_DETECT));
    }

    @Override
    public void run() {
        if (customMob == null) {
            this.cancel();
            return;
        }
        
        if (customMob.isDead()) {
            this.cancel();
            return;
        }

        updateTarget();

        /*
        if (customMob.getTarget() != null && customMob.getTarget().isValid() && !customMob.getTarget().isDead()) {
            Location targetLocation = customMob.getTarget().getLocation();
            Location mobLocation = customMob.getLocation();

            double distance = mobLocation.distance(targetLocation);
            if (distance > MAX_DISTANCE_DETECT) {
                customMob.setTarget(null);
                return;
            }

            if (distance > MIN_ATTACK_RANGE) {
                Vector direction = targetLocation.toVector().subtract(mobLocation.toVector()).normalize();
                Vector currentVelocity = customMob.getEntity().getVelocity();
                double currentYVelocity = currentVelocity.getY();
                
                double speed = 0.5;
                Vector velocityYOnly = new Vector(0.0, currentYVelocity, 0.0);
                Vector moveVelocity = (new Vector(direction.getX(), 0.0, direction.getZ()).multiply(speed));
                Vector newVelocity = moveVelocity.add(velocityYOnly);

                customMob.getEntity().setVelocity(newVelocity);

                double dx = targetLocation.getX() - mobLocation.getX();
                double dz = targetLocation.getZ() - mobLocation.getZ();
                float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
                customMob.getEntity().setBodyYaw(yaw);
            }

            if (distance <= MIN_ATTACK_RANGE) {
                // ATTACK
            }
        }
        */
    }

    private void updateTarget() {
        if (!customMob.getDamagers().isEmpty()) {
            Player lowestHealthTarget = null;
            double lowestHealth = Double.MAX_VALUE;

            for (Player damager : customMob.getDamagers()) {
                if (!damager.isDead()) {
                    double health = damager.getHealth();
                    if (health < lowestHealth) {
                        lowestHealth = health;
                        lowestHealthTarget = damager;
                    }
                }
            }

            if (lowestHealthTarget != null) {
                customMob.setTarget(lowestHealthTarget);

                // NMS
                EntityHuman nmsPlayer = ((CraftPlayer)lowestHealthTarget).getHandle();
                nmsEntity.setTarget(nmsPlayer, TargetReason.CUSTOM, true);
                //
                
                return;
            }
        }

        List<Player> worldPlayers = customMob.getLocation().getWorld().getPlayers();
        if (worldPlayers.isEmpty()) return;

        Player nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Player player : worldPlayers) {
            if (!player.isDead()) {
                double distance = customMob.getLocation().distance(player.getLocation());
                if (distance < MAX_DISTANCE_DETECT && distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPlayer = player;
                }
            }
        }

        // NMS
        EntityHuman nmsPlayer = ((CraftPlayer)nearestPlayer).getHandle();
        nmsEntity.setTarget(nmsPlayer, TargetReason.CUSTOM, true);
        //
        
        customMob.setTarget(nearestPlayer);
    }

    public void cleanup() {
        this.cancel();
        customMob = null;
    }
    
}
