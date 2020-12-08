package com.kqp.strangery.init;

import com.kqp.strangery.gen.StrangeMonumentFeature;
import com.kqp.strangery.gen.StrangeMonumentPiece;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class StrangeryWorldFeatures {

    private static final ConfiguredFeature<?, ?> FOODIUM_ORE_OVERWORLD = Feature.ORE
        .configure(
            new OreFeatureConfig(
                OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                StrangeryBlocks.RANDOMIUM_ORE.getDefaultState(),
                8
            )
        )
        .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 64)))
        .spreadHorizontally()
        .repeat(12);

    private static final ConfiguredFeature<?, ?> RANDOMIUM_ORE_OVERWORLD = Feature.ORE
        .configure(
            new OreFeatureConfig(
                OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                StrangeryBlocks.RANDOMIUM_ORE.getDefaultState(),
                8
            )
        )
        .decorate(
            Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 128))
        )
        .spreadHorizontally()
        .repeat(16);

    private static final ConfiguredFeature<?, ?> LOOSE_STONE_OVERWORLD = Feature.ORE
        .configure(
            new OreFeatureConfig(
                OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                StrangeryBlocks.LOOSE_STONE.getDefaultState(),
                33
            )
        )
        .rangeOf(256)
        .spreadHorizontally()
        .repeat(8);

    private static final ConfiguredFeature<?, ?> BEBSOFYR_ORE_OVERWORLD = Feature.ORE
        .configure(
            new OreFeatureConfig(
                OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                StrangeryBlocks.BEBSOFYR_ORE.getDefaultState(),
                6
            )
        )
        .rangeOf(16)
        .spreadHorizontally();

    public static final StructurePieceType STRANGE_MONUMENT_PIECE =
        StrangeMonumentPiece::new;
    public static final StructureFeature<DefaultFeatureConfig> STRANGE_MONUMENT = new StrangeMonumentFeature(
        DefaultFeatureConfig.CODEC
    );
    public static final ConfiguredStructureFeature<?, ?> STRANGE_MONUMENT_CONFIGURED = STRANGE_MONUMENT.configure(
        DefaultFeatureConfig.DEFAULT
    );

    public static void init() {
        registerUndergroundFeature(
            FOODIUM_ORE_OVERWORLD,
            GenerationStep.Feature.UNDERGROUND_ORES,
            "foodium_ore"
        );
        registerUndergroundFeature(
            RANDOMIUM_ORE_OVERWORLD,
            GenerationStep.Feature.UNDERGROUND_ORES,
            "randomium_ore"
        );
        registerUndergroundFeature(
            LOOSE_STONE_OVERWORLD,
            GenerationStep.Feature.UNDERGROUND_DECORATION,
            "loose_stone"
        );
        registerUndergroundFeature(
            BEBSOFYR_ORE_OVERWORLD,
            GenerationStep.Feature.UNDERGROUND_ORES,
            "bebsofyr_ore"
        );

        Registry.register(
            Registry.STRUCTURE_PIECE,
            Strangery.id("strange_monument_piece"),
            STRANGE_MONUMENT_PIECE
        );
        FabricStructureBuilder
            .create(Strangery.id("strange_monument"), STRANGE_MONUMENT)
            .step(GenerationStep.Feature.VEGETAL_DECORATION)
            .defaultConfig(32, 8, 12345)
            .adjustsSurface()
            .register();
        RegistryKey strangeMonumentKey = RegistryKey.of(
            Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN,
            Strangery.id("strange_monument")
        );
        BuiltinRegistries.add(
            BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE,
            strangeMonumentKey.getValue(),
            STRANGE_MONUMENT_CONFIGURED
        );
        BiomeModifications.addStructure(
            BiomeSelectors.all(),
            strangeMonumentKey
        );
    }

    private static void registerUndergroundFeature(
        ConfiguredFeature<?, ?> feature,
        GenerationStep.Feature step,
        String name
    ) {
        Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            Strangery.id(name),
            feature
        );
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld(),
            step,
            BuiltinRegistries.CONFIGURED_FEATURE.getKey(feature).get()
        );
    }
}
