package com.kqp.strangery.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.kqp.strangery.entity.BossLevel;
import com.kqp.strangery.mixin.accessor.CreeperEntityAccessor;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MiniBossMixin {

    private static final Random RANDOM = new Random();
    private static final float BOSS_CHANCE = 0.025F;
    private static final double TRACKING_DISTANCE = 32.0D;

    @Shadow
    protected int experiencePoints;

    private ServerBossBar strangeryBossBar;
    private BossLevel bossLevel;

    @Inject(method = "initialize", at = @At("HEAD"))
    private void injectInitialize(
        ServerWorldAccess world,
        LocalDifficulty difficulty,
        SpawnReason spawnReason,
        @Nullable EntityData entityData,
        @Nullable CompoundTag entityTag,
        CallbackInfoReturnable<EntityData> callbackInfo
    ) {
        MobEntity mob = (MobEntity) (Object) this;

        bossLevel = BossLevel.roll(RANDOM);

        if (mob instanceof HostileEntity && RANDOM.nextFloat() < BOSS_CHANCE) {
            mob.setCustomName(
                new TranslatableText(
                    "entity.strangery.miniboss.level" + bossLevel.ordinal()
                )
                    .append(" ")
                    .append(mob.getDisplayName())
                    .formatted(bossLevel.formatting)
            );

            strangeryBossBar =
                new ServerBossBar(
                    mob.getName(),
                    BossBar.Color.WHITE,
                    BossBar.Style.PROGRESS
                );

            this.experiencePoints = bossLevel.level * (5 + RANDOM.nextInt(5));

            if (RANDOM.nextBoolean()) {
                mob.addStatusEffect(
                    new StatusEffectInstance(
                        StatusEffects.STRENGTH,
                        Integer.MAX_VALUE,
                        RANDOM.nextInt(4)
                    )
                );
            }
            if (RANDOM.nextBoolean()) {
                mob.addStatusEffect(
                    new StatusEffectInstance(
                        StatusEffects.SPEED,
                        Integer.MAX_VALUE,
                        RANDOM.nextInt(3)
                    )
                );
            }
            if (RANDOM.nextBoolean()) {
                mob.addStatusEffect(
                    new StatusEffectInstance(
                        StatusEffects.REGENERATION,
                        Integer.MAX_VALUE,
                        RANDOM.nextInt(2)
                    )
                );
            }
            if (RANDOM.nextBoolean()) {
                mob.addStatusEffect(
                    new StatusEffectInstance(
                        StatusEffects.RESISTANCE,
                        Integer.MAX_VALUE,
                        RANDOM.nextInt(3)
                    )
                );
            }

            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(
                    "strangery_boss",
                    3.15D *
                    ((double) bossLevel.level / BossLevel.GODLIKE.level),
                    EntityAttributeModifier.Operation.MULTIPLY_BASE
                )
            );
            builder.put(
                EntityAttributes.GENERIC_MAX_HEALTH,
                new EntityAttributeModifier(
                    "strangery_boss",
                    Math.min(bossLevel.level, 15.0D),
                    EntityAttributeModifier.Operation.MULTIPLY_BASE
                )
            );
            mob.getAttributes().addTemporaryModifiers(builder.build());
            mob.setHealth(mob.getMaxHealth());

            if (mob instanceof CreeperEntity) {
                mob
                    .getDataTracker()
                    .set(((CreeperEntityAccessor) mob).getCharged(), true);
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    protected void injectTick(CallbackInfo callbackInfo) {
        MobEntity mob = (MobEntity) (Object) this;

        if (strangeryBossBar != null) {
            strangeryBossBar.setPercent(mob.getHealth() / mob.getMaxHealth());

            if (!mob.world.isClient) {
                ServerWorld serverWorld = (ServerWorld) mob.world;

                for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                    if (
                        mob.distanceTo(player) < TRACKING_DISTANCE &&
                        mob.getVisibilityCache().canSee(player) &&
                        mob.isAlive() &&
                        player.isAlive()
                    ) {
                        strangeryBossBar.addPlayer(player);
                    } else {
                        strangeryBossBar.removePlayer(player);
                    }
                }
            }
        }
    }

    @Inject(method = "dropEquipment", at = @At("HEAD"))
    protected void injectDropEquipment(
        DamageSource source,
        int lootingMultiplier,
        boolean allowDrops,
        CallbackInfo callbackInfo
    ) {
        if (strangeryBossBar != null && source.getAttacker() instanceof PlayerEntity) {
            int loot = 1 + RANDOM.nextInt(2);
            for (int i = 0; i < loot; i++) {
                ItemStack drop = new ItemStack(
                    bossLevel.loot[RANDOM.nextInt(bossLevel.loot.length)]
                );

                if (
                    drop.getItem() instanceof ToolItem ||
                    drop.getItem() instanceof ArmorItem
                ) {
                    drop = EnchantmentHelper.enchant(RANDOM, drop, 15 + RANDOM.nextInt(15), true);
                } else {
                    drop.setCount(Math.min(drop.getMaxCount(), 4 + RANDOM.nextInt(32)));
                }

                ((MobEntity) (Object) this).dropStack(drop);
            }
        }
    }

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    private void injectWriteCustomDataToTag(
        CompoundTag tag,
        CallbackInfo callbackInfo
    ) {
        if (strangeryBossBar != null) {
            tag.putInt("BossLevel", bossLevel.ordinal());
        }
    }

    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    private void injectReadCustomDataFromTag(
        CompoundTag tag,
        CallbackInfo callbackInfo
    ) {
        if (tag.contains("BossLevel")) {
            MobEntity mob = (MobEntity) (Object) this;

            this.bossLevel = BossLevel.values()[tag.getInt("BossLevel")];

            strangeryBossBar =
                new ServerBossBar(
                    mob.getName(),
                    BossBar.Color.WHITE,
                    BossBar.Style.PROGRESS
                );
        }
    }
}
