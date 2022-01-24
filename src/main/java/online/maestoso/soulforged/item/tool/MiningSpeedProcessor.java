package online.maestoso.soulforged.item.tool;

import net.minecraft.block.BlockState;

import online.maestoso.soulforged.material.SmithingMaterial;

@FunctionalInterface
public interface MiningSpeedProcessor {
    float getMiningSpeed(BlockState state, SmithingMaterial mat);
}
