package com.github.ebjerke04.myrpg.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class MoveToLocationGoal extends Goal {

    private final PathfinderMob entity;
    private final double speed;
    private BlockPos targetPos;
        
    public MoveToLocationGoal(PathfinderMob entity, double speed) {
        this.entity = entity;
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
            entity.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return canUse() && !entity.getNavigation().isDone();
    }
    
}
