package com.kqp.strangery.gen;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class StrangeMonumentFeature
  extends StructureFeature<DefaultFeatureConfig> {

  public StrangeMonumentFeature(Codec<DefaultFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
    return Start::new;
  }

  public static class Start extends StructureStart<DefaultFeatureConfig> {

    public Start(
      StructureFeature<DefaultFeatureConfig> structureFeature,
      int chunkX,
      int chunkZ,
      BlockBox blockBox,
      int references,
      long seed
    ) {
      super(structureFeature, chunkX, chunkZ, blockBox, references, seed);
    }

    public void init(
      DynamicRegistryManager dynamicRegistryManager,
      ChunkGenerator chunkGenerator,
      StructureManager structureManager,
      int chunkX,
      int chunkZ,
      Biome biome,
      DefaultFeatureConfig defaultFeatureConfig
    ) {
      int x = chunkX * 16;
      int z = chunkZ * 16;
      int y = chunkGenerator.getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);

      this.children.add(
          new StrangeMonumentPiece(structureManager, new BlockPos(x, y, z))
        );
      this.setBoundingBoxFromChildren();
    }
  }
}
