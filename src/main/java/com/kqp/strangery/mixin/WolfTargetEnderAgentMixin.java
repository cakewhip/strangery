package com.kqp.strangery.mixin;

import com.kqp.strangery.entity.mob.EnderAgentEntity;
import com.kqp.strangery.mixin.accessor.MobEntityAccessor;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.WolfEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
public class WolfTargetEnderAgentMixin {

  @Inject(method = "initGoals", at = @At("HEAD"))
  private void injectInitGoals(CallbackInfo callbackInfo) {
    WolfEntity wolf = (WolfEntity) (Object) this;
    GoalSelector targetSelector =
      ((MobEntityAccessor) wolf).getTargetSelector();

    targetSelector.add(
      4,
      new FollowTargetGoal(wolf, EnderAgentEntity.class, true, false)
    );
  }
}
