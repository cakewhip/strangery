package com.kqp.strangery.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.kqp.strangery.init.StrangeryItems;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Rarity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobEntity.class)
public class MiniBossMixin {

    private static final Random RANDOM = new Random();
    private static final float BOSS_CHANCE = 0.2F;
    private static final Item[] POSSIBLE_LOOT = new Item[] {
        Items.DIAMOND_PICKAXE,
        Items.DIAMOND_AXE,
        Items.DIAMOND_SWORD,
        Items.DIAMOND_HELMET,
        Items.DIAMOND_CHESTPLATE,
        Items.DIAMOND_LEGGINGS,
        Items.DIAMOND_BOOTS,
        StrangeryItems.BEBSOFYR_PICKAXE,
        StrangeryItems.BEBSOFYR_AXE,
        StrangeryItems.BEBSOFYR_SWORD,
        StrangeryItems.BEBSOFYR_HELMET,
        StrangeryItems.BEBSOFYR_CHESTPLATE,
        StrangeryItems.BEBSOFYR_LEGGINGS,
        StrangeryItems.BEBSOFYR_BOOTS,
        Items.DIAMOND,
        Items.EMERALD,
        Items.IRON_BLOCK,
        Items.GOLD_BLOCK,
        Items.GOLDEN_APPLE,
        Items.ENCHANTED_GOLDEN_APPLE,
    };

    @Shadow
    protected int experiencePoints;

    private ServerBossBar strangeryBossBar;
    private int level;

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

        level = 1 + RANDOM.nextInt(Rarity.values().length - 1);
        Rarity rarity = Rarity.values()[level];

        if (mob instanceof HostileEntity && RANDOM.nextFloat() < BOSS_CHANCE) {
            strangeryBossBar =
                new ServerBossBar(
                    new LiteralText("Level " + level + " ")
                        .append(mob.getDisplayName())
                        .formatted(rarity.formatting),
                    BossBar.Color.values()[RANDOM.nextInt(
                            BossBar.Color.values().length
                        )],
                    BossBar.Style.PROGRESS
                );

            this.experiencePoints = level * (5 + RANDOM.nextInt(5));

            mob.addStatusEffect(
                new StatusEffectInstance(
                    StatusEffects.STRENGTH,
                    Integer.MAX_VALUE,
                    RANDOM.nextInt(1 + level)
                )
            );
            mob.addStatusEffect(
                new StatusEffectInstance(
                    StatusEffects.SPEED,
                    Integer.MAX_VALUE,
                    RANDOM.nextInt(level)
                )
            );
            mob.addStatusEffect(
                new StatusEffectInstance(
                    StatusEffects.REGENERATION,
                    Integer.MAX_VALUE,
                    RANDOM.nextInt(1 + level)
                )
            );
            mob.addStatusEffect(
                new StatusEffectInstance(
                    StatusEffects.RESISTANCE,
                    Integer.MAX_VALUE,
                    RANDOM.nextInt(1 + level)
                )
            );

            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(
                    "strangery_boss",
                    level * (0.25D + RANDOM.nextDouble() * 0.25D),
                    EntityAttributeModifier.Operation.MULTIPLY_BASE
                )
            );
            builder.put(
                EntityAttributes.GENERIC_MAX_HEALTH,
                new EntityAttributeModifier(
                    "strangery_boss",
                    level,
                    EntityAttributeModifier.Operation.MULTIPLY_BASE
                )
            );
            mob.getAttributes().addTemporaryModifiers(builder.build());
            mob.setHealth(mob.getMaxHealth());
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
                        mob.getVisibilityCache().canSee(player) && mob.isAlive()
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
        if (strangeryBossBar != null) {
            for (int i = 0; i < level; i++) {
                ItemStack drop = new ItemStack(
                    POSSIBLE_LOOT[RANDOM.nextInt(POSSIBLE_LOOT.length)]
                );

                if (
                    drop.getItem() instanceof ToolItem ||
                    drop.getItem() instanceof ArmorItem
                ) {
                    drop = EnchantmentHelper.enchant(RANDOM, drop, 30, true);
                } else {
                    drop.setCount(4 + RANDOM.nextInt(32));
                }

                ((MobEntity) (Object) this).dropStack(drop);
            }
        }
    }
}
