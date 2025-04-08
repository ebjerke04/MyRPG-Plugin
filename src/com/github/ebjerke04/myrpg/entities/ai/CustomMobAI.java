package com.github.ebjerke04.myrpg.entities.ai;

import java.util.List;
import java.util.Stack;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.entities.CustomMob;
import com.github.ebjerke04.myrpg.util.Logging;

import net.minecraft.world.entity.PathfinderMob;

public class CustomMobAI extends BukkitRunnable {

    private final double MAX_DISTANCE_DETECT = 30.0;
    private final double MIN_ATTACK_RANGE = 2.0;

    private CustomMob customMob;

    // NMS test
    private final PathfinderMob nmsEntity;
    private final MoveAlongPathGoal pathGoal;

    public CustomMobAI(CustomMob customMob) {
        this.customMob = customMob;
        

        CraftEntity craftEntity = (CraftEntity) customMob.getEntity();
        if (!(craftEntity.getHandle() instanceof PathfinderMob)) {
            Logging.sendConsole(Level.SEVERE, "Shouldn't have hit this branch...");
            throw new IllegalStateException();
        }
        nmsEntity = (PathfinderMob) craftEntity.getHandle();

        Location first = customMob.getLocation().subtract(10.0f, 0.0f, 5.0f);
        Location second = customMob.getLocation().add(3.0f, 0.0, -5.0f);
        Location third = customMob.getLocation().add(-3.0f, 0.0f, 5.0f);
        Stack<Location> path = new Stack<>();
        path.insertElementAt(first, 0);
        path.insertElementAt(second, 0);
        path.insertElementAt(third, 0);

        this.pathGoal = new MoveAlongPathGoal(nmsEntity, path, 1.0D);

        setupAIGoals();

        final long updateTickRate = 5L;
        this.runTaskTimer(Plugin.get(), 0L, updateTickRate); // 4 updates/second
    }

    private void setupAIGoals() {
        //nmsEntity.goalSelector.removeAllGoals(null);
        nmsEntity.goalSelector.addGoal(1, pathGoal);

        //moveGoal.setTargetLocation((int)customMob.getLocation().getX(), (int)customMob.getLocation().getY(), (int)customMob.getLocation().getZ() + 50);
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
    }
    
}
