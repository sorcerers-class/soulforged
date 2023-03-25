package studio.soulforged.soulforged.util

import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.tag.BlockTags
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.resource.callback.MiningSpeedProcessors
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.material.Materials
import java.util.*

object BlockUtil {
    fun isMineable(state: BlockState, tool: ItemStack): Boolean {
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) {
            assert(tool.nbt != null)
            val level: Int =
                Materials.MATERIAL_REGISTRY[Identifier(
                    tool.nbt!!.getCompound("sf_head").getString("material")
                )]?.miningLevel!!
            if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) {
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