package studio.soulforged.soulforged.util

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.tag.BlockTags
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.item.tool.MiningSpeedProcessors
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.*

object BlockUtil {
    fun isMineable(state: BlockState, tool: ItemStack): Boolean {
        if (Registry.BLOCK.get(Registry.BLOCK.getRawId(state.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) {
            assert(tool.nbt != null)
            val level: Int =
                Materials.MATERIAL_REGISTRY[Identifier(
                    tool.nbt!!.getCompound("sf_head").getString("material")
                )]?.miningLevel!!
            if (Registry.BLOCK.get(Registry.BLOCK.getRawId(state.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) {
                return if (Objects.requireNonNull(
                        ToolTypes.TOOL_TYPES_REGISTRY[Identifier(
                            tool.nbt!!.getString("sf_tool_type")
                        )]
                    )?.miningSpeedProcessor === MiningSpeedProcessors.PICKAXE
                ) {
                    level >= MiningLevelManager.getRequiredMiningLevel(state)
                } else false
            }
            if (MiningLevelManager.getRequiredMiningLevel(state) == -1) return true
        }
        return true
    }
}