package com.kqp.strangery.entity.ai;

import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class MoveToTargetGoal extends MoveToTargetPosGoal {
    public MoveToTargetGoal(PathAwareEntity mob, double speed, int range) {
        super(mob, speed, range);
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean shouldContinue() {
        return this.mob.getTarget() != null && super.shouldContinue();
    }

    @Override
    protected boolean findTargetPos() {
        if (this.mob.getTarget() != null) {
            this.targetPos = this.mob.getTarget().getBlockPos();

            return true;
        }

        return false;
    }
}
