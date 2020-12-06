package com.kqp.strangery.gen;

import com.google.common.collect.ImmutableSet;
import com.kqp.strangery.Strangery;
import com.kqp.strangery.util.GenUtil;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class StrangeMonumentPiece extends SimpleStructurePiece {

  private boolean generated = false;

  public StrangeMonumentPiece(
    StructureManager structureManager,
    CompoundTag compoundTag
  ) {
    super(Strangery.WF.STRANGE_MONUMENT_PIECE, compoundTag);
  }

  public StrangeMonumentPiece(
    StructureManager structureManager,
    BlockPos blockPos
  ) {
    super(Strangery.WF.STRANGE_MONUMENT_PIECE, 0);
    this.pos = blockPos;
    this.setOrientation(Direction.NORTH);
    this.boundingBox =
      new BlockBox(
        pos.getX(),
        pos.getY(),
        pos.getZ(),
        pos.getX() + 1,
        pos.getY() + 1,
        pos.getZ() + 1
      );
  }

  @Override
  public boolean generate(
    StructureWorldAccess world,
    StructureAccessor structureAccessor,
    ChunkGenerator chunkGenerator,
    Random random,
    BlockBox boundingBox,
    ChunkPos chunkPos,
    BlockPos blockPos
  ) {
    if (!generated) {
      generated = true;

      int radius = 8 + random.nextInt(16);
      final BlockPos originPos = blockPos.add(
        0,
        -(radius * 0.65 + radius * (2 * random.nextDouble())),
        0
      );

      Set<BlockPos> spherePosSet = GenUtil
        .getSphereBlockOffsets(radius)
        .stream()
        .map(
          spherePos ->
            originPos.add(spherePos.getX(), spherePos.getY(), spherePos.getZ())
        )
        .collect(Collectors.toSet());

      for (BlockPos spherePos : spherePosSet) {
        world.setBlockState(
          spherePos,
          Strangery.B.OILY_BLACK_STONE.getDefaultState(),
          2
        );
      }

      for (BlockPos spherePos : spherePosSet) {
        if (random.nextFloat() < 0.025F) {
          genOre(world, spherePos, random, spherePosSet);
        }
      }

      return true;
    } else {
      return false;
    }
  }

  private void genOre(
    StructureWorldAccess world,
    BlockPos blockPos,
    Random random,
    Set<BlockPos> sphereBlocks
  ) {
    BlockState blockState = random.nextBoolean()
      ? Strangery.B.MOONSTONE_ORE.getDefaultState()
      : Strangery.B.SUNSTONE_ORE.getDefaultState();

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        for (int k = 0; k < 2; k++) {
          BlockPos orePos = blockPos.add(i, j, k);

          if (sphereBlocks.contains(orePos)) {
            world.setBlockState(orePos, blockState, 2);
          }
        }
      }
    }
  }

  @Override
  protected void handleMetadata(
    String metadata,
    BlockPos pos,
    ServerWorldAccess serverWorldAccess,
    Random random,
    BlockBox boundingBox
  ) {}
}
