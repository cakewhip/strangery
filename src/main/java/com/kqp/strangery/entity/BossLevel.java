package com.kqp.strangery.entity;

import net.minecraft.util.Formatting;

public enum BossLevel {
    UNCOMMON(1, Formatting.WHITE),
    RARE(2, Formatting.YELLOW),
    EPIC(3, Formatting.AQUA),
    LEGENDARY(4, Formatting.LIGHT_PURPLE),
    MYTHICAL(5, Formatting.GREEN),
    GODLIKE(6, Formatting.RED);

    public int level;
    public Formatting formatting;

    BossLevel(int level, Formatting formatting) {
        this.level = level;
        this.formatting = formatting;
    }
}
