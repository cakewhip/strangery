package com.kqp.strangery.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

// TODO this doesn't make sense as a sub class of MoveToTargetPosGoal
public class MoveToTargetGoal extends MoveToTargetPosGoal {

    public MoveToTargetGoal(PathAwareEntity mob, double speed, int range) {
        super(mob, speed, range);
    }

    @Override
    public boolean canStart() {
        this.cooldown = this.getInterval(this.mob);
        return this.findTargetPos();
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();

        this.mob.getNavigation()
            .startMovingTo(
                target.getX(),
                target.getY(),
                target.getZ(),
                this.speed
            );
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean shouldContinue() {
        return this.mob.getTarget() != null;
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
