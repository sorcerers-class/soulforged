package online.maestoso.soulforged.item.tool;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;

import net.minecraft.tag.BlockTags;

import online.maestoso.soulforged.material.SmithingMaterial;

public final class MiningSpeedProcessors {
    public static final MiningSpeedProcessor HAND = (BlockState state, SmithingMaterial mat) -> !state.isToolRequired() ? mat.miningSpeed() / 2 : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor AXE = (BlockState state, SmithingMaterial mat) -> BlockTags.AXE_MINEABLE.contains(state.getBlock()) ? mat.miningSpeed() : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor SHOVEL = (BlockState state, SmithingMaterial mat) -> BlockTags.SHOVEL_MINEABLE.contains(state.getBlock()) ? mat.miningSpeed() : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor PICKAXE = (BlockState state, SmithingMaterial mat) -> BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? mat.miningSpeed() : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor HOE = (BlockState state, SmithingMaterial mat) -> BlockTags.HOE_MINEABLE.contains(state.getBlock()) ? mat.miningSpeed() : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor SWORD = (BlockState state, SmithingMaterial mat) -> {
        if (state.isOf(Blocks.COBWEB)) {
            return mat.miningSpeed();
        }
        Material material = state.getMaterial();
        if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT || state.isIn(BlockTags.LEAVES) || material == Material.GOURD) {
            return mat.miningSpeed() / 10;
        }
        return BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f;

    };
}
