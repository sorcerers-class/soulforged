package online.maestoso.soulforged.item.tool;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import net.minecraft.tag.BlockTags;

import online.maestoso.soulforged.material.Material;

public final class MiningSpeedProcessors {
    public static final MiningSpeedProcessor HAND = (BlockState state, Material mat) -> !state.isToolRequired() ? mat.miningSpeed() / 2 : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor AXE = (BlockState state, Material mat) -> BlockTags.AXE_MINEABLE.contains(state.getBlock()) ? mat.miningSpeed() : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor SHOVEL = (BlockState state, Material mat) -> BlockTags.SHOVEL_MINEABLE.contains(state.getBlock()) ? mat.miningSpeed() : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor PICKAXE = (BlockState state, Material mat) -> BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? mat.miningSpeed() : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor HOE = (BlockState state, Material mat) -> BlockTags.HOE_MINEABLE.contains(state.getBlock()) ? mat.miningSpeed() : (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f);
    public static final MiningSpeedProcessor SWORD = (BlockState state, Material mat) -> {
        if (state.isOf(Blocks.COBWEB)) {
            return mat.miningSpeed();
        }
        net.minecraft.block.Material material = state.getMaterial();
        if (material == net.minecraft.block.Material.PLANT || material == net.minecraft.block.Material.REPLACEABLE_PLANT || state.isIn(BlockTags.LEAVES) || material == net.minecraft.block.Material.GOURD) {
            return mat.miningSpeed() / 10;
        }
        return BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 0.0f : 1.0f;

    };
}
