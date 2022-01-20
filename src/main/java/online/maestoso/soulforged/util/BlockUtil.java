package online.maestoso.soulforged.util;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;

public class BlockUtil {
    public static boolean isMineable(BlockState state, int level) {
        return  (BlockTags.PICKAXE_MINEABLE.contains(state.getBlock()) && level >= MiningLevels.WOOD) ||
                (BlockTags.NEEDS_STONE_TOOL.contains(state.getBlock()) && level >= MiningLevels.STONE) ||
                (BlockTags.NEEDS_IRON_TOOL.contains(state.getBlock()) && level >= MiningLevels.IRON) ||
                (BlockTags.NEEDS_DIAMOND_TOOL.contains(state.getBlock()) && level >= MiningLevels.DIAMOND);
    }
}
