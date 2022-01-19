package online.maestoso.soulforged.item.tool;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.tag.BlockTags;
import online.maestoso.soulforged.material.SmithingMaterial;

public final class MiningProfiles {
    public static final ToolType.MiningSpeedGetter HAND = (BlockState state, SmithingMaterial mat) -> 1.0f;
    public static final ToolType.MiningSpeedGetter AXE = (BlockState state, SmithingMaterial mat) -> BlockTags.AXE_MINEABLE.contains(state.getBlock()) ? 15.0f : 1.0f;
    public static final ToolType.MiningSpeedGetter SHOVEL = (BlockState state, SmithingMaterial mat) -> BlockTags.SHOVEL_MINEABLE.contains(state.getBlock()) ? 15.0f : 1.0f;
    public static final ToolType.MiningSpeedGetter PICKAXE = (BlockState state, SmithingMaterial mat) -> BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) ? 15.0f : 1.0f;
    public static final ToolType.MiningSpeedGetter HOE = (BlockState state, SmithingMaterial mat) -> BlockTags.HOE_MINEABLE.contains(state.getBlock()) ? 15.0f : 1.0f;
    public static final ToolType.MiningSpeedGetter SWORD = (BlockState state, SmithingMaterial mat) -> {
        if (state.isOf(Blocks.COBWEB)) {
            return 15.0f;
        }
        Material material = state.getMaterial();
        if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT || state.isIn(BlockTags.LEAVES) || material == Material.GOURD) {
            return 1.5f;
        }
        return 1.0f;
    };
}
