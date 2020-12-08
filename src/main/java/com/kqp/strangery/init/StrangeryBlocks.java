package com.kqp.strangery.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class StrangeryBlocks {

    public static final Block FOODIUM_ORE = register(
        new Block(
            FabricBlockSettings
                .of(Material.STONE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, 2)
                .strength(3.0F, 3.0F)
        ),
        "foodium_ore"
    );

    public static final Block RANDOMIUM_ORE = register(
        new Block(
            FabricBlockSettings
                .of(Material.STONE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, 2)
                .strength(9.0F, 9.0F)
        ),
        "randomium_ore"
    );

    public static final Block LOOSE_STONE = register(
        new FallingBlock(
            FabricBlockSettings
                .of(Material.STONE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(0.75F, 3.0F)
        ),
        "loose_stone"
    );

    public static final Block BEBSOFYR_ORE = register(
        new Block(
            FabricBlockSettings
                .of(Material.STONE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, 3)
                .strength(5.0F, 5.0F)
        ),
        "bebsofyr_ore"
    );
    public static final Block BEBSOFYR_BLOCK = register(
        new Block(
            FabricBlockSettings
                .of(Material.METAL)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL)
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(5.0F, 6.0F)
        ),
        "bebsofyr_block"
    );

    public static final Block OILY_BLACK_STONE = register(
        new Block(
            FabricBlockSettings
                .of(Material.STONE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, 4)
                .strength(7.5F, 12000.0F)
        ),
        "oily_black_stone"
    );

    public static final Block MOONSTONE_ORE = register(
        new Block(
            FabricBlockSettings
                .of(Material.STONE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, 4)
                .strength(7.5F, 12000.0F)
        ),
        "moonstone_ore"
    );
    public static final Block MOONSTONE_BLOCK = register(
        new Block(
            FabricBlockSettings
                .of(Material.METAL)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL)
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(5.0F, 6.0F)
        ),
        "moonstone_block"
    );
    public static final Block SUNSTONE_ORE = register(
        new Block(
            FabricBlockSettings
                .of(Material.STONE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, 4)
                .strength(7.5F, 12000.0F)
        ),
        "sunstone_ore"
    );
    public static final Block SUNSTONE_BLOCK = register(
        new Block(
            FabricBlockSettings
                .of(Material.METAL)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL)
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(5.0F, 6.0F)
        ),
        "sunstone_block"
    );

    public static void init() {}

    private static Block register(Block block, String name) {
        Registry.register(Registry.BLOCK, Strangery.id(name), block);
        Registry.register(
            Registry.ITEM,
            Strangery.id(name),
            new BlockItem(
                block,
                new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)
            )
        );

        return block;
    }
}
