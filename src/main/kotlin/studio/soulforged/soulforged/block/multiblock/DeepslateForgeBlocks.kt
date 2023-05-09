package studio.soulforged.soulforged.block.multiblock

import net.minecraft.block.*
import net.minecraft.block.HorizontalFacingBlock.FACING
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.block.entity.SoulforgedBlockEntityTypes


object DeepslateForgeBlocks {
    val TIER: IntProperty = IntProperty.of("tier", 1, 2)
    object DeepslateForgeBurner : DeepslateForgeDummyBlock(QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS).sounds(BlockSoundGroup.DEEPSLATE_BRICKS).luminance { state -> return@luminance if(state.get(Properties.LIT)) 7 else 0 }) {
        init {
            defaultState = this.stateManager.defaultState
                .with(TIER, 1)
                .with(Properties.LIT, false)
        }
        override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL
        override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
            builder?.add(Properties.LIT)
            builder?.add(TIER)
        }
        override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
            return defaultState
        }
    }
    object DeepslateForgeBunker : DeepslateForgeDummyBlock(QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS).sounds(BlockSoundGroup.DEEPSLATE_BRICKS).luminance { state -> return@luminance if(state.get(Properties.LIT)) 7 else 0 }), BlockEntityProvider {
        init {
            defaultState = this.stateManager.defaultState
                .with(Properties.LIT, false)
        }
        override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL
        override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
            builder?.add(Properties.LIT)
        }

        override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
            return defaultState
        }
    }
    class DeepslateForgeBunkerBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(SoulforgedBlockEntityTypes.DEEPSLATE_FORGE_BUNKER, pos, state), DeepslateForgeMultiblockComponent {
        override var controllerPos: BlockPos? = pos
        override fun readNbt(nbt: NbtCompound?) {
            super<BlockEntity>.readNbt(nbt)
            super<DeepslateForgeMultiblockComponent>.readNbt(nbt)
        }

        override fun writeNbt(nbt: NbtCompound?) {
            super<DeepslateForgeMultiblockComponent>.writeNbt(nbt)
            super<BlockEntity>.writeNbt(nbt)
        }
    }
    object DeepslateForgeController : DeepslateForgeDummyBlock(QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS).sounds(BlockSoundGroup.DEEPSLATE_BRICKS).luminance { state -> return@luminance if(state.get(Properties.LIT)) 7 else 0 }) {
        init {
            defaultState = this.stateManager.defaultState
                .with(FACING, Direction.NORTH)
                .with(Properties.LIT, false)
        }
        override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL
        override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
            builder?.add(FACING)
            builder?.add(Properties.LIT)
        }
        override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
            return defaultState.with(AbstractFurnaceBlock.FACING, ctx.playerFacing.opposite) as BlockState
        }

        override fun afterBreak(
            world: World?,
            player: PlayerEntity?,
            pos: BlockPos?,
            state: BlockState?,
            blockEntity: BlockEntity?,
            stack: ItemStack?
        ) {
            super.afterBreak(world, player, pos, state, blockEntity, stack)

        }
    }
    class DeepslateForgeControllerBlockEntity(pos: BlockPos, val state: BlockState) : BlockEntity(SoulforgedBlockEntityTypes.DEEPSLATE_FORGE_CONTROLLER, pos, state), DeepslateForgeMultiblockComponent {
        override var controllerPos: BlockPos? = pos
        override fun readNbt(nbt: NbtCompound?) {
            super<BlockEntity>.readNbt(nbt)
            super<DeepslateForgeMultiblockComponent>.readNbt(nbt)
        }

        override fun writeNbt(nbt: NbtCompound?) {
            super<DeepslateForgeMultiblockComponent>.writeNbt(nbt)
            super<BlockEntity>.writeNbt(nbt)
        }
        var bunkers: ArrayList<BlockPos> = arrayListOf()
        fun deleteMultiblock() {
            val facing = state.get(FACING)
            for(x in -1..1)
                for(y in -1..2)
                    for(z in 0..2) {
                        val pos = pos
                            .offset(facing.rotateYClockwise(), x)
                            .offset(Direction.UP, y)
                            .offset(facing.opposite, z)
                        world!!.setBlockState(pos, when(world!!.getBlockState(pos).block) {
                            DeepslateForgeDeepslateBrickBlock -> Blocks.DEEPSLATE_BRICKS.defaultState
                            DeepslateForgeBurner -> DeepslateForgeBurner.defaultState
                            DeepslateForgeBunker -> DeepslateForgeBunker.defaultState
                            DeepslateForgeController -> DeepslateForgeController.defaultState.with(FACING, facing)
                            else -> world!!.getBlockState(pos)
                        })
                        val entity = world!!.getBlockEntity(pos)
                        if(entity is DeepslateForgeMultiblockComponent) {
                            entity.controllerPos = pos
                        }
                    }
            bunkers.clear()
        }
        fun createMultiblock() {
            Soulforged.LOGGER.info("Trying to create Deepslate forge multiblock at $pos")
            val facing = state.get(FACING)
            val result = SoulforgedMultiblocks.MULTIBLOCK_PATTERN.searchAround(world, pos)
            if(
                result != null
                && world!!.getBlockState(pos.offset(Direction.DOWN))?.block == DeepslateForgeBunker
                && world!!.getBlockState(pos.offset(Direction.UP))?.block == DeepslateForgeBurner
            ) {
                val bottomCenter = pos.offset(Direction.DOWN).offset(facing.opposite)
                val bunkerPositions = arrayOf(bottomCenter.offset(facing.rotateYCounterclockwise()), bottomCenter.offset(facing.opposite), bottomCenter.offset(facing.rotateYClockwise()))
                for(bunker in bunkerPositions) {
                    if(world!!.getBlockState(bunker).block == DeepslateForgeBunker) {
                        if(world!!.getBlockState(bunker.offset(Direction.UP)).block == DeepslateForgeBurner && world!!.getBlockState(bunker.offset(Direction.UP, 2)).block == DeepslateForgeBurner) {
                            bunkers.add(bunker)
                        }
                    }
                }
                val controllerPos: BlockPos = pos
                for(x in -1..1)
                    for(y in -1..2)
                        for(z in 0..2) {
                            val pos = pos
                                .offset(facing.rotateYClockwise(), x)
                                .offset(Direction.UP, y)
                                .offset(facing.opposite, z)
                            world!!.setBlockState(pos, when(world!!.getBlockState(pos).block) {
                                Blocks.DEEPSLATE_BRICKS -> DeepslateForgeDeepslateBrickBlock.defaultState
                                DeepslateForgeBurner -> DeepslateForgeBurner.defaultState.with(TIER, (y + 1).coerceIn(1..2))
                                DeepslateForgeBunker -> DeepslateForgeBunker.defaultState
                                DeepslateForgeController -> DeepslateForgeController.defaultState.with(FACING, facing)
                                else -> world!!.getBlockState(pos)
                            })
                            val entity = world!!.getBlockEntity(pos)
                            (entity as? DeepslateForgeMultiblockComponent)?.controllerPos = controllerPos
                        }
            }
        }
    }
    open class DeepslateForgeDummyBlock(settings: QuiltBlockSettings) : BlockWithEntity(settings) {
        override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL
        override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
            super.onBreak(world, pos, state, player)
            (world.getBlockEntity((world.getBlockEntity(pos) as DeepslateForgeMultiblockComponent).controllerPos) as? DeepslateForgeControllerBlockEntity)?.deleteMultiblock()
        }

        override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
            return when(state.block) {
                DeepslateForgeController -> DeepslateForgeControllerBlockEntity(pos, state)
                DeepslateForgeBunker -> DeepslateForgeBunkerBlockEntity(pos, state)
                else -> DeepslateForgeDeepslateBrickBlockEntity(pos, state)
            }
        }
    }
    object DeepslateForgeDeepslateBrickBlock : DeepslateForgeDummyBlock(QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS)) {
        override fun getPickStack(world: BlockView?, pos: BlockPos?, state: BlockState?): ItemStack {
            return Items.DEEPSLATE_BRICKS.defaultStack
        }
    }
    class DeepslateForgeDeepslateBrickBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(SoulforgedBlockEntityTypes.DEEPSLATE_FORGE_BLOCK, pos, state), DeepslateForgeMultiblockComponent {
        override var controllerPos: BlockPos? = pos
        override fun readNbt(nbt: NbtCompound?) {
            super<BlockEntity>.readNbt(nbt)
            super<DeepslateForgeMultiblockComponent>.readNbt(nbt)
        }

        override fun writeNbt(nbt: NbtCompound?) {
            super<DeepslateForgeMultiblockComponent>.writeNbt(nbt)
            super<BlockEntity>.writeNbt(nbt)
        }

    }
    sealed interface DeepslateForgeMultiblockComponent {
        var controllerPos: BlockPos?
         fun writeNbt(nbt: NbtCompound?) {
            nbt?.putIntArray("ControllerPos", with(controllerPos) {intArrayOf(this?.x ?: 0, this?.y ?: -65, this?.z ?: 0)})
        }

        fun readNbt(nbt: NbtCompound?) {
            val pos = nbt?.getIntArray("ControllerPos")
            controllerPos = BlockPos(pos?.get(0) ?: -1, pos?.get(1) ?: -65, pos?.get(2) ?: -1)
        }
    }
}