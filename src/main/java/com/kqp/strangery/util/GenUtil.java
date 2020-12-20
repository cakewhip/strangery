package com.kqp.strangery.util;

import com.google.common.collect.ImmutableSet;
import java.util.HashMap;
import java.util.HashSet;
import net.minecraft.util.math.BlockPos;

public class GenUtil {

    private static final HashMap<Integer, ImmutableSet<BlockPos>> SPHERE_CACHE = new HashMap();
    private static final HashMap<Integer, ImmutableSet<BlockPos>> DISK_CACHE = new HashMap();
    private static final HashMap<Integer, ImmutableSet<BlockPos>> HOLLOW_DISK_CACHE = new HashMap();

    static {
        SPHERE_CACHE.put(1, ImmutableSet.of(new BlockPos(0, 0, 0)));
    }

    /**
     * Returns a set of block positions used to offset from a center to form a sphere.
     *
     * @param radius radius of sphere
     * @return set of block positions
     */
    public static ImmutableSet<BlockPos> getSphereBlockOffsets(int radius) {
        if (!SPHERE_CACHE.containsKey(radius)) {
            HashSet<BlockPos> offsets = new HashSet();

            for (int i = -radius + 1; i < radius; i++) {
                for (int j = -radius + 1; j < radius; j++) {
                    for (int k = -radius + 1; k < radius; k++) {
                        double distance = Math.sqrt(i * i + j * j + k * k);

                        if (distance <= radius) {
                            offsets.add(new BlockPos(i, j, k));
                        }
                    }
                }
            }

            SPHERE_CACHE.put(radius, ImmutableSet.copyOf(offsets));
        }

        return SPHERE_CACHE.get(radius);
    }

    public static ImmutableSet<BlockPos> getDiskOffsets(int radius) {
        if (!DISK_CACHE.containsKey(radius)) {
            HashSet<BlockPos> offsets = new HashSet();

            for (int i = -radius + 1; i < radius; i++) {
                for (int k = -radius + 1; k < radius; k++) {
                    double distance = Math.sqrt(i * i + k * k);

                    if (distance <= radius) {
                        offsets.add(new BlockPos(i, 0, k));
                    }
                }
            }

            DISK_CACHE.put(radius, ImmutableSet.copyOf(offsets));
        }

        return DISK_CACHE.get(radius);
    }

    public static ImmutableSet<BlockPos> getHollowDiskOffsets(int radius) {
        if (!HOLLOW_DISK_CACHE.containsKey(radius)) {
            HashSet<BlockPos> offsets = new HashSet();

            for (int i = -radius + 1; i < radius; i++) {
                for (int k = -radius + 1; k < radius; k++) {
                    double distance = Math.sqrt(i * i + k * k);

                    if (distance <= radius && distance >= radius - 1) {
                        offsets.add(new BlockPos(i, 0, k));
                    }
                }
            }

            HOLLOW_DISK_CACHE.put(radius, ImmutableSet.copyOf(offsets));
        }

        return HOLLOW_DISK_CACHE.get(radius);
    }
}
