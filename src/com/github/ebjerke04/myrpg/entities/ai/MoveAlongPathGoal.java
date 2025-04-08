package com.github.ebjerke04.myrpg.entities.ai;

import java.util.Stack;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class MoveAlongPathGoal extends Goal {

    private final PathfinderMob entity;
    private final double speed;
    private BlockPos targetPos;

    private Stack<Location> pathNodes;
    private boolean pathComplete;

    private static final double ARRIVAL_THRESHOLD = 2.0; // Distance in blocks to consider "arrived"
        
    public MoveAlongPathGoal(PathfinderMob entity, Stack<Location> pathNodes, double speed) {
        this.entity = entity;
        this.pathNodes = pathNodes;
        this.speed = speed;

        this.pathComplete = false;
        
        Location firstTarget = pathNodes.peek();
        this.targetPos = new BlockPos((int) firstTarget.getX(), (int) firstTarget.getY(), (int) firstTarget.getZ());
    }

    private void setNewTarget() {
        if (pathNodes.isEmpty()) {
            pathComplete = true;
            targetPos = null;
            return;
        }

        pathNodes.pop();

        if (!pathNodes.isEmpty()) {
            Location newTarget = pathNodes.peek();
            this.targetPos = new BlockPos((int) newTarget.getX(), (int) newTarget.getY(), (int) newTarget.getZ());
        } else {
            pathComplete = true;
            targetPos = null;
        }
    }

    @Override
    public boolean canUse() {
        return targetPos != null;
    }

    @Override
    public void tick() {
        if (targetPos != null) {
            entity.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
            
            // Check if we've reached the target to a certain degree
            double entityX = entity.getX();
            double entityZ = entity.getZ();
            double targetX = targetPos.getX();
            double targetZ = targetPos.getZ();
            Vector entityPosVec = new Vector(entityX, 0, entityZ);
            Vector targetPosVec = new Vector(targetX, 0, targetZ);
            double distance = entityPosVec.distance(targetPosVec);

            if (distance <= ARRIVAL_THRESHOLD) {
                setNewTarget();
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !pathComplete && canUse();
    }
    
}
