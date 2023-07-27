package studio.soulforged.soulforged.block

import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import studio.soulforged.soulforged.block.entity.WorkstationBlockEntity
import studio.soulforged.soulforged.item.SoulforgedItems

class WorkstationBlock(settings: Settings) : HorizontalFacingBlock(settings.strength(1.0f)), BlockEntityProvider {
    init {
        defaultState = this.stateManager.defaultState
            .with(FACING, Direction.NORTH)
    }
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(FACING)
    }

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        for (direction in ctx?.placementDirections!!) {
            val blockState: BlockState = if (direction.axis === Direction.Axis.Y) {
                defaultState
                    .with(FACING, ctx.playerFacing) as BlockState
            } else {
                defaultState.with(
                    FACING,
                    direction.opposite
                ) as BlockState
            }
            if (blockState.canPlaceAt(ctx.world, ctx.blockPos)) {
                return blockState
            }
        }
        return null
    }

    override fun getPickStack(world: BlockView?, pos: BlockPos?, state: BlockState?): ItemStack {
        return SoulforgedItems.WORKSTATION.defaultStack
    }
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity {
        return WorkstationBlockEntity(pos!!, state!!)
    }
}