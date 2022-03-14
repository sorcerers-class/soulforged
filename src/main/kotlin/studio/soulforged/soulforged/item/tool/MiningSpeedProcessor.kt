package studio.soulforged.soulforged.item.tool

import net.minecraft.block.BlockState
import studio.soulforged.soulforged.material.Material

fun interface MiningSpeedProcessor {
    fun getMiningSpeed(state: BlockState?, mat: Material?): Float
}