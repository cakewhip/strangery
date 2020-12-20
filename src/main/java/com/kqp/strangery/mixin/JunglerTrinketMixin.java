package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryItems;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class JunglerTrinketMixin {

    private static final Set<RegistryKey<Biome>> BIOMES = new HashSet<RegistryKey<Biome>>();

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void injectTickMovement(CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (
            TrinketsApi
                .getTrinketsInventory(player)
                .count(StrangeryItems.EMBER_KNIFE) >
            0 ||
            TrinketsApi
                .getTrinketsInventory(player)
                .count(StrangeryItems.HAIL_BLADE) >
            0
        ) {
            player.world
                .method_31081(player.getBlockPos())
                .ifPresent(
                    biome -> {
                        if (
                            getBiomes().contains(biome) && player.age % 40 == 0
                        ) {
                            player.heal(0.5F);
                        }
                    }
                );
        }
    }

    private static Set<RegistryKey<Biome>> getBiomes() {
        if (BIOMES.isEmpty()) {
            BIOMES.add(BiomeKeys.JUNGLE);
            BIOMES.add(BiomeKeys.JUNGLE_EDGE);
            BIOMES.add(BiomeKeys.JUNGLE_HILLS);
            BIOMES.add(BiomeKeys.BAMBOO_JUNGLE);
            BIOMES.add(BiomeKeys.MODIFIED_JUNGLE);
            BIOMES.add(BiomeKeys.MODIFIED_JUNGLE_EDGE);
            BIOMES.add(BiomeKeys.BAMBOO_JUNGLE_HILLS);
            BIOMES.add(BiomeKeys.RIVER);
        }

        return BIOMES;
    }
}
