package studio.soulforged.soulforged.block.multiblock

import net.minecraft.block.*
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
import net.minecraft.world.BlockView
import net.minecraft.world.World
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.block.entity.SoulforgedBlockEntityTypes


val TIER: IntProperty = IntProperty.of("tier", 1, 2)
object DeepslateForgeBurner : DeepslateForgeComponentBlock(QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS).sounds(BlockSoundGroup.DEEPSLATE_BRICKS).luminance { state -> return@luminance if(state.get(Properties.LIT)) 7 else 0 }) {
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
open class DeepslateForgeComponentBlock(settings: QuiltBlockSettings) : BlockWithEntity(settings) {
    override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL
    override fun onBreak(world: World, pos: BlockPos?, state: BlockState?, player: PlayerEntity?) {
        super.onBreak(world, pos, state, player)
        val controller = (world.getBlockEntity(pos) as? DeepslateForgeMultiblockComponent)?.controllerPos
        if (world.getBlockEntity(controller) is DeepslateForgeControllerBlockEntity) {
            (world.getBlockEntity(controller) as DeepslateForgeControllerBlockEntity).deleteMultiblock(world)
        }
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return when(state.block) {
            DeepslateForgeController -> DeepslateForgeControllerBlockEntity(pos, state)
            DeepslateForgeBunker -> DeepslateForgeBunkerBlockEntity(pos, state)
            else -> DeepslateForgeDeepslateBrickBlockEntity(pos, state)
        }
    }
}
object DeepslateForgeDeepslateBrickBlock : DeepslateForgeComponentBlock(QuiltBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS)) {
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
        nbt?.putIntArray("ControllerPos", with(controllerPos) {intArrayOf(this?.x ?: Int.MAX_VALUE, this?.y ?: Int.MAX_VALUE, this?.z ?: Int.MAX_VALUE)})
    }

    fun readNbt(nbt: NbtCompound?) {
        val pos = nbt?.getIntArray("ControllerPos")
        controllerPos = BlockPos(pos?.get(0) ?: -Int.MAX_VALUE, pos?.get(1) ?: -Int.MAX_VALUE, pos?.get(2) ?: -Int.MAX_VALUE)
    }
}