package studio.soulforged.soulforged.block

import net.minecraft.block.AnvilBlock
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.block.entity.IronAnvilBlockEntity

class IronAnvilBlock : AnvilBlock(QuiltBlockSettings.copyOf(Blocks.ANVIL)), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? {
        return IronAnvilBlockEntity(pos!!, state!!)
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        return ActionResult.PASS
    }
}