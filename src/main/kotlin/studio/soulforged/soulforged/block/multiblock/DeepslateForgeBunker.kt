package studio.soulforged.soulforged.block.multiblock

import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.block.entity.SoulforgedBlockEntityTypes

object DeepslateForgeBunker : DeepslateForgeDummyBlock(
    QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS).sounds(
        BlockSoundGroup.DEEPSLATE_BRICKS).luminance { state -> return@luminance if(state.get(Properties.LIT)) 7 else 0 }),
    BlockEntityProvider {
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
class DeepslateForgeBunkerBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(SoulforgedBlockEntityTypes.DEEPSLATE_FORGE_BUNKER, pos, state),
    DeepslateForgeMultiblockComponent {
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