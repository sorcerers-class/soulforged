package studio.soulforged.soulforged.block

import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.block.entity.BlastFurnaceBlockEntity

class BlastFurnaceBlock() : AbstractFurnaceBlock(QuiltBlockSettings.copyOf(Blocks.BLAST_FURNACE)) {
    init {
        defaultState = this.stateManager.defaultState
    }
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? {
        return BlastFurnaceBlockEntity(pos!!, state!!)
    }

    override fun openScreen(world: World?, pos: BlockPos?, player: PlayerEntity?) {

    }
}