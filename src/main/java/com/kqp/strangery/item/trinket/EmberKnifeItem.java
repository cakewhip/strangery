package com.kqp.strangery.item.trinket;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EmberKnifeItem extends TrinketItem {

    public EmberKnifeItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.LEGS) && slot.equals(Slots.CHARM);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(
        ItemStack stack,
        @Nullable World world,
        List<Text> tooltip,
        TooltipContext context
    ) {
        tooltip.add(
            new TranslatableText("item.strangery.ember_knife.d1")
            .formatted(Formatting.ITALIC, Formatting.YELLOW)
        );
        tooltip.add(
            new TranslatableText("item.strangery.ember_knife.d2")
            .formatted(Formatting.ITALIC, Formatting.YELLOW)
        );
        tooltip.add(
            new TranslatableText("item.strangery.ember_knife.d3")
            .formatted(Formatting.ITALIC, Formatting.YELLOW)
        );
    }
}
