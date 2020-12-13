package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryEntities;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map.Entry;

@Mixin(DefaultBiomeFeatures.class)
public class EntitySpawnMixin {

    @Inject(method = "addMonsters", at = @At("HEAD"))
    private static void injectAddMonsters(
        SpawnSettings.Builder builder,
        int zombieWeight,
        int zombieVillagerWeight,
        int skeletonWeight,
        CallbackInfo callbackInfo
    ) {
        StrangeryEntities.initSpawns();

        for (Entry<SpawnGroup, List<SpawnEntry>> entry : StrangeryEntities.SPAWNS.entrySet()) {
            if (entry.getKey() == SpawnGroup.MONSTER) {
                for (SpawnEntry spawnEntry : entry.getValue()) {
                    builder.spawn(SpawnGroup.MONSTER, spawnEntry);
                }
            }
        }
    }
}
