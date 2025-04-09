package com.github.ebjerke04.myrpg.entities.ai;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.entities.CustomMob;
import com.github.ebjerke04.myrpg.util.Logging;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CustomMobAI extends BukkitRunnable {

    private final double MAX_DISTANCE_DETECT = 30.0;

    private CustomMob customMob;

    // NMS test
    private PathfinderMob nmsEntity;
    private MoveToLocationGoal moveGoal;

    public CustomMobAI(CustomMob customMob) {
        this.customMob = customMob;

        CraftEntity craftEntity = (CraftEntity) customMob.getEntity();
        if (!(craftEntity.getHandle() instanceof PathfinderMob)) {
            Logging.sendConsole(Level.SEVERE, "Shouldn't have hit this branch...");
            throw new IllegalStateException();
        }
        nmsEntity = (PathfinderMob) craftEntity.getHandle();

        setupAIGoals();

        final long updateTickRate = 5L;
        this.runTaskTimer(Plugin.get(), 0L, updateTickRate); // 4 updates/second
    }

    private void setupAIGoals() {
        nmsEntity.goalSelector.removeAllGoals(goal -> true);
        nmsEntity.targetSelector.removeAllGoals(target -> true);

        moveGoal = new MoveToLocationGoal(nmsEntity, 1.0D);
        nmsEntity.goalSelector.addGoal(1, moveGoal);
        nmsEntity.goalSelector.addGoal(2, new MeleeAttackGoal(nmsEntity, 1.0D, true));
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
        if (customMob.getTarget() == null) return;
        moveGoal.setTargetLocation(customMob.getTarget().getLocation());

        CraftEntity craftEntity = (CraftEntity) customMob.getTarget();
        if (!(craftEntity.getHandle() instanceof LivingEntity)) {
            Logging.sendConsole(Level.SEVERE, "Target should be a LivingEntity");
            throw new IllegalStateException();
        }
        nmsEntity.setTarget((LivingEntity) craftEntity.getHandle(), TargetReason.CUSTOM, true);
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
        
        customMob.setTarget(nearestPlayer);
    }

    public void cleanup() {
        this.cancel();
        customMob = null;
        nmsEntity = null;
    }
    
}
