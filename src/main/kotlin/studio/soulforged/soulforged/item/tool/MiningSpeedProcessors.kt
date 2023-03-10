package studio.soulforged.soulforged.item.tool

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import net.minecraft.registry.tag.BlockTags
import studio.soulforged.soulforged.material.Material


object MiningSpeedProcessors {
    val HAND = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (!state!!.isToolRequired) mat!!.miningLevel / 2.0f else if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state.block)).defaultState.isIn(
                BlockTags.PICKAXE_MINEABLE)) 0.0f else 1.0f
    }
    val AXE = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.AXE_MINEABLE)) mat!!.miningSpeed.toFloat()
        else if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f
        else 1.0f
    }
    val SHOVEL = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.SHOVEL_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                state?.block
            )).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f else 1.0f
    }
    val PICKAXE = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                state?.block
            )).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f else 1.0f
    }
    val HOE = MiningSpeedProcessor { state: BlockState?, mat: Material? ->
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.HOE_MINEABLE)) mat!!.miningSpeed.toFloat() else if (Registries.BLOCK.get(
                Registries.BLOCK.getRawId(
                state?.block
            )).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f else 1.0f
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
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state.block)).defaultState.isIn(BlockTags.PICKAXE_MINEABLE)) 0.0f else 1.0f
    }
}