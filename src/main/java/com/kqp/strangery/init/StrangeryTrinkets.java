package com.kqp.strangery.init;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.minecraft.util.Identifier;

public class StrangeryTrinkets {

    public static void init() {
        TrinketSlots.addSlot(
            SlotGroups.LEGS,
            Slots.CHARM,
            new Identifier(
                "trinkets",
                "textures/item/empty_trinket_slot_charm.png"
            )
        );
    }
}
