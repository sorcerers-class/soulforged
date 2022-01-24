package online.maestoso.soulforged.util;

import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;

import net.minecraft.block.BlockState;

import net.minecraft.item.ItemStack;

import net.minecraft.tag.BlockTags;

import net.minecraft.util.Identifier;

import online.maestoso.soulforged.item.tool.MiningSpeedProcessors;
import online.maestoso.soulforged.item.tool.ForgedToolTypes;

import online.maestoso.soulforged.material.SmithingMaterials;

import java.util.Objects;

public class BlockUtil {
    public static boolean isMineable(BlockState state, ItemStack tool) {
        if(BlockTags.PICKAXE_MINEABLE.contains(state.getBlock())) {
            assert tool.getNbt() != null;
            int level = Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(new Identifier(tool.getNbt().getCompound("sf_head").getString("material")))).miningLevel();
            if(BlockTags.PICKAXE_MINEABLE.contains(state.getBlock())) {
                if (Objects.requireNonNull(ForgedToolTypes.TOOL_TYPES_REGISTRY.get(new Identifier(tool.getNbt().getString("sf_tool_type")))).getMiningSpeed() == MiningSpeedProcessors.PICKAXE) {
                    return level >= MiningLevelManager.getRequiredMiningLevel(state);
                }
                return false;
            }
            if (MiningLevelManager.getRequiredMiningLevel(state) == -1) return true;
        }
        return true;
    }
}
