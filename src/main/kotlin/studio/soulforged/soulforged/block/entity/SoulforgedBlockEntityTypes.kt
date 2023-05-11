package studio.soulforged.soulforged.block.entity

import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import org.quiltmc.qkl.library.registry.invoke
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.block.SoulforgedBlocks
import studio.soulforged.soulforged.block.multiblock.*
import kotlin.reflect.full.primaryConstructor

object SoulforgedBlockEntityTypes {
    val WORKSTATION: BlockEntityType<WorkstationBlockEntity> = withBlocks(SoulforgedBlocks.WORKSTATION)
    val DEEPSLATE_FORGE_CONTROLLER: BlockEntityType<DeepslateForgeControllerBlockEntity> = withBlocks(
        DeepslateForgeController
    )
    val DEEPSLATE_FORGE_BUNKER: BlockEntityType<DeepslateForgeBunkerBlockEntity> = withBlocks(DeepslateForgeBunker)
    val DEEPSLATE_FORGE_BLOCK: BlockEntityType<DeepslateForgeDeepslateBrickBlockEntity> = withBlocks(DeepslateForgeBurner, DeepslateForgeDeepslateBrickBlock
    )
    private inline fun<reified T: BlockEntity> withBlocks(vararg blocks: Block): BlockEntityType<T> {
        return QuiltBlockEntityTypeBuilder.create({pos, state -> T::class.primaryConstructor!!.call(pos, state)}, *blocks).build()
    }
    internal fun init() {
        Registries.BLOCK_ENTITY_TYPE(Soulforged.NAME) {
            WORKSTATION withId "workstation"
            DEEPSLATE_FORGE_CONTROLLER withId "deepslate_forge_controller"
            DEEPSLATE_FORGE_BUNKER withId "deepslate_forge_bunker"
            DEEPSLATE_FORGE_BLOCK withId "deepslate_forge_block"
        }
    }
}