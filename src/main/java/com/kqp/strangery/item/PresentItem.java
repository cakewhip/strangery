package com.kqp.strangery.item;

import com.kqp.strangery.init.StrangeryItems;
import com.kqp.strangery.init.StrangerySounds;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class PresentItem extends Item {

    private static final double XP_CHANCE = 0.075D;
    private static final double ENCHANTMENT_CHANCE = 0.75D;

    private static final Set<Item> ITEM_BLACK_LIST = new HashSet<Item>();

    public PresentItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(
        World world,
        PlayerEntity user,
        Hand hand
    ) {
        ItemStack presentItemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);

        if (!world.isClient) {
            Random random = user.getRandom();

            if (random.nextDouble() < XP_CHANCE) {
                user.addExperienceLevels(2 + random.nextInt(3));

                world.playSound(
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                    SoundCategory.PLAYERS,
                    0.1F,
                    (random.nextFloat() - random.nextFloat()) * 0.35F + 0.9F,
                    false
                );
            } else {
                ItemStack contentsItemStack = getDropItemStack(random);
                if (!user.inventory.insertStack(contentsItemStack)) {
                    user.dropItem(contentsItemStack, true);
                }

                presentItemStack.decrement(1);
                if (presentItemStack.isEmpty()) {
                    user.inventory.removeOne(presentItemStack);
                }
            }

            user.playSound(
                StrangerySounds.PRESENT_UNWRAP,
                SoundCategory.PLAYERS,
                0.75F,
                (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F
            );
        }

        return TypedActionResult.consume(presentItemStack);
    }

    private static ItemStack getDropItemStack(Random random) {
        Item item = Registry.ITEM.getRandom(random);

        if (item instanceof SpawnEggItem || ITEM_BLACK_LIST.contains(item)) {
            return getDropItemStack(random);
        }

        ItemStack itemStack = new ItemStack(item);
        itemStack.setCount(
            Math.min(item.getMaxCount(), 1 + random.nextInt(64))
        );

        if (
            item.getEnchantability() > 0 &&
            random.nextDouble() < ENCHANTMENT_CHANCE
        ) {
            EnchantmentHelper.enchant(random, itemStack, 30, true);
            itemStack.setCount(1);
        }

        return itemStack;
    }

    static {
        ITEM_BLACK_LIST.add(StrangeryItems.CELESTIAL_STEEL_INGOT);
        ITEM_BLACK_LIST.add(StrangeryItems.MOONSTONE_FRAGMENT);
        ITEM_BLACK_LIST.add(StrangeryItems.SUNSTONE_FRAGMENT);
        ITEM_BLACK_LIST.add(Items.NETHERITE_INGOT);
        ITEM_BLACK_LIST.add(Items.ANCIENT_DEBRIS);
        ITEM_BLACK_LIST.add(Items.NETHER_STAR);
    }
}
