package com.kqp.strangery.mixin;

import com.kqp.strangery.init.data.StrangeryConfig;
import com.kqp.strangery.mixin.accessor.BlockAccessor;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Material;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Used to add damage when breaking blocks with fists.
 */
@Mixin(ServerWorld.class)
public class EmptyHandBreakDamageAdder {

    private static final Map<Material, DamageData> MATERIAL_DAMAGE_DATA_MAP = new HashMap<Material, DamageData>();

    static {
        MATERIAL_DAMAGE_DATA_MAP.put(Material.STONE, new DamageData(2, 4.0F));
        MATERIAL_DAMAGE_DATA_MAP.put(Material.WOOD, new DamageData(3, 1.0F));
        MATERIAL_DAMAGE_DATA_MAP.put(
            Material.NETHER_WOOD,
            new DamageData(3, 2.0F)
        );
        MATERIAL_DAMAGE_DATA_MAP.put(Material.BARRIER, new DamageData(2, 4.0F));
        MATERIAL_DAMAGE_DATA_MAP.put(Material.CACTUS, new DamageData(1, 2.0F));
        MATERIAL_DAMAGE_DATA_MAP.put(
            Material.REPAIR_STATION,
            new DamageData(2, 4.0F)
        );
        MATERIAL_DAMAGE_DATA_MAP.put(Material.METAL, new DamageData(2, 4.0F));
    }

    @Inject(method = "setBlockBreakingInfo", at = @At("TAIL"))
    private void addDamage(
        int entityId,
        BlockPos pos,
        int progress,
        CallbackInfo callbackInfo
    ) {
        if (!StrangeryConfig.get().fistMiningHurts) {
            return;
        }

        ServerWorld world = (ServerWorld) (Object) this;
        ServerPlayerEntity player = (ServerPlayerEntity) world.getEntityById(
            entityId
        );

        if (player.getMainHandStack().isEmpty() && progress > 0) {
            Material material =
                (
                    (BlockAccessor) world.getBlockState(pos).getBlock()
                ).getMaterial();
            DamageData damageData = MATERIAL_DAMAGE_DATA_MAP.getOrDefault(
                material,
                null
            );

            if (damageData != null && progress % damageData.hurtTick == 0) {
                player.damage(DamageSource.GENERIC, damageData.damage);
            }
        }
    }

    static class DamageData {

        private final int hurtTick;
        private final float damage;

        DamageData(int hurtTick, float damage) {
            this.hurtTick = hurtTick;
            this.damage = damage;
        }
    }
}
