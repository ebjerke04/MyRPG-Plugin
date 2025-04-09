package com.github.ebjerke04.myrpg.entities.ai;

import java.util.Stack;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class MoveAlongPathGoal extends Goal {

    private final PathfinderMob entity;
    private final double speed;
    private Location targetPos;

    private Stack<Location> pathNodes;
    private boolean pathComplete;

    private static final double ARRIVAL_THRESHOLD = 2.2; // Distance in blocks to consider "arrived"

    private boolean executePath = false;
    
    public MoveAlongPathGoal(PathfinderMob entity, MovePath path, double speed) {
        this.entity = entity;
        this.speed = speed;

        if (path == null) {
            this.pathComplete = true;
            return;
        }
        
        this.pathNodes = path.getNodes();
        this.pathComplete = false;
        Location firstTarget = pathNodes.peek();
        this.targetPos = firstTarget;
    }

    public void setPath(MovePath path) {
        this.pathNodes = path.getNodes();
        this.targetPos = pathNodes.peek();
        this.pathComplete = false;
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
            this.targetPos = newTarget;
        } else {
            pathComplete = true;
            targetPos = null;
        }
    }

    public void executePath() {
        this.executePath = true;
    }

    public void abortPath() {
        this.executePath = false;
    }

    @Override
    public boolean canUse() {
        return targetPos != null;
    }

    @Override
    public void tick() {
        if (!executePath) return;

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
