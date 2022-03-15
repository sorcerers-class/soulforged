package studio.soulforged.soulforged.item.tool

import net.minecraft.block.BlockState
import net.minecraft.tag.BlockTags
import net.minecraft.block.Blocks
import studio.soulforged.soulforged.material.Material

object MiningSpeedProcessors {
    val HAND = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (!state!!.isToolRequired) mat!!.miningLevel / 2.0f else if (BlockTags.PICKAXE_MINEABLE.contains(
                state.block
            )
        ) 0.0f else 1.0f
    }
    val AXE = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (BlockTags.AXE_MINEABLE.contains(
                state!!.block
            )
        ) mat!!.miningSpeed.toFloat() else if (BlockTags.PICKAXE_MINEABLE.contains(state.block)) 0.0f else 1.0f
    }
    val SHOVEL = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (BlockTags.SHOVEL_MINEABLE.contains(
                state!!.block
            )
        ) mat!!.miningSpeed.toFloat() else if (BlockTags.PICKAXE_MINEABLE.contains(state.block)) 0.0f else 1.0f
    }
    val PICKAXE = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (BlockTags.PICKAXE_MINEABLE.contains(
                state!!.block
            )
        ) mat!!.miningSpeed.toFloat() else if (BlockTags.PICKAXE_MINEABLE.contains(state.block)) 0.0f else 1.0f
    }
    val HOE = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (BlockTags.HOE_MINEABLE.contains(
                state!!.block
            )
        ) mat!!.miningSpeed.toFloat() else if (BlockTags.PICKAXE_MINEABLE.contains(state.block)) 0.0f else 1.0f
    }
    val SWORD = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (state!!.isOf(Blocks.COBWEB)) {
            mat!!.miningSpeed
        }
        val material = state.material
        if (material == net.minecraft.block.Material.PLANT || material == net.minecraft.block.Material.REPLACEABLE_PLANT || state.isIn(
                BlockTags.LEAVES
            ) || material == net.minecraft.block.Material.GOURD
        ) {
            mat!!.miningSpeed / 10
        }
        if (BlockTags.PICKAXE_MINEABLE.contains(state.block)) 0.0f else 1.0f
    }
}