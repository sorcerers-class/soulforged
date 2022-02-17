package online.maestoso.soulforged.item.tool;

import net.minecraft.block.BlockState;

import online.maestoso.soulforged.material.Material;

@FunctionalInterface
public interface MiningSpeedProcessor {
    float getMiningSpeed(BlockState state, Material mat);
}
