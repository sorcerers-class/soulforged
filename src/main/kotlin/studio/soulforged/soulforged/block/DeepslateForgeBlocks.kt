package studio.soulforged.soulforged.block

import net.minecraft.block.*
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings

object DeepslateForgeBlocks {
    class DeepslateForgeBurner : Block(QuiltBlockSettings.of(Material.STONE)) {
        companion object {
            val TIER: IntProperty = IntProperty.of("tier", 1, 2)
        }
        init {
            defaultState = this.stateManager.defaultState
                .with(TIER, 1)
                .with(Properties.LIT, false)
        }

        override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
            builder?.add(Properties.LIT)
            builder?.add(TIER)
        }
        override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
            return defaultState
        }
    }
    class DeepslateForgeBunker : Block(QuiltBlockSettings.of(Material.STONE)) {
        init {
            defaultState = this.stateManager.defaultState
                .with(Properties.LIT, false)
        }
        override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
            builder?.add(Properties.LIT)
        }

        override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
            return defaultState
        }
    }
    class DeepslateForgeController : HorizontalFacingBlock(QuiltBlockSettings.of(Material.STONE)) {
        init {
            defaultState = this.stateManager.defaultState
                .with(FACING, Direction.NORTH)
                .with(Properties.LIT, false)
        }
        override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
            builder?.add(FACING)
            builder?.add(Properties.LIT)
        }
        override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
            return defaultState.with(AbstractFurnaceBlock.FACING, ctx.playerFacing.opposite) as BlockState
        }
    }
}