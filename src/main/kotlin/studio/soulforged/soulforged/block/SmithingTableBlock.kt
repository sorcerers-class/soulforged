package studio.soulforged.soulforged.block

import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.block.entity.SmithingTableBlockEntity

class SmithingTableBlock : BlockWithEntity(QuiltBlockSettings.copyOf(Blocks.SMITHING_TABLE)) {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? {
        return SmithingTableBlockEntity(pos!!, state!!)
    }
}