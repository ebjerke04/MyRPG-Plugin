package com.github.ebjerke04.myrpg.entities;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.util.Logging;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.monster.Zombie;

public class CustomMobAI extends BukkitRunnable {

    private class MoveToLocationGoal extends Goal {
        private final Zombie mob;
        private final double speed;
        private BlockPos targetPos;
        
        public MoveToLocationGoal(Zombie mob, double speed) {
            this.mob = mob;
            this.speed = speed;
        }

        public void setTargetLocation(int x, int y, int z) {
            this.targetPos = new BlockPos(x, y, z);
        }

        @Override
        public boolean canUse() {
            return targetPos != null;
        }

        @Override
        public void tick() {
            if (targetPos != null) {
                mob.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return canUse() && !mob.getNavigation().isDone();
        }
    }
    private final MoveToLocationGoal moveGoal;


    private final double MAX_DISTANCE_DETECT = 30.0;
    private final double MIN_ATTACK_RANGE = 2.0;

    private CustomMob customMob;

    // NMS test
    private final Zombie nmsEntity;

    public CustomMobAI(CustomMob customMob) {
        this.customMob = customMob;

        CraftEntity craftEntity = (CraftEntity) customMob.getEntity();
        if (!(craftEntity.getHandle() instanceof Zombie)) {
            Logging.sendConsole(Level.SEVERE, "Shouldn't have hit this branch...");
            throw new IllegalStateException();
        }
        nmsEntity = (Zombie) craftEntity.getHandle();

        this.moveGoal = new MoveToLocationGoal(nmsEntity, 1.0D);

        setupAIGoals();
        //

        final long updateTickRate = 5L;
        this.runTaskTimer(Plugin.get(), 0L, updateTickRate); // 4 updates/second
    }

    private void setupAIGoals() {
        //nmsEntity.goalSelector.removeAllGoals(null);
        nmsEntity.goalSelector.addGoal(1, moveGoal);

        moveGoal.setTargetLocation((int)customMob.getLocation().getX(), (int)customMob.getLocation().getY(), (int)customMob.getLocation().getZ() + 50);
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
                //EntityHuman nmsPlayer = ((CraftPlayer)lowestHealthTarget).getHandle();
                //nmsEntity.setTarget(nmsPlayer, TargetReason.CUSTOM, true);
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
        //EntityHuman nmsPlayer = ((CraftPlayer)nearestPlayer).getHandle();
        //nmsEntity.setTarget(nmsPlayer, TargetReason.CUSTOM, true);
        //
        
        customMob.setTarget(nearestPlayer);
    }

    public void cleanup() {
        this.cancel();
        customMob = null;
    }
    
}
