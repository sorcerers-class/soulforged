package studio.soulforged.soulforged.block.entity

import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import org.quiltmc.qkl.library.registry.invoke
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.block.SoulforgedBlocks
import kotlin.reflect.full.primaryConstructor

object SoulforgedBlockEntityTypes {
    val WORKSTATION: BlockEntityType<WorkstationBlockEntity> = withBlocks(SoulforgedBlocks.WORKSTATION)
    private inline fun<reified T: BlockEntity> withBlocks(vararg blocks: Block): BlockEntityType<T> {
        return QuiltBlockEntityTypeBuilder.create({pos, state -> T::class.primaryConstructor!!.call(pos, state)}, *blocks).build()
    }
    internal fun init() {
        Registries.BLOCK_ENTITY_TYPE(Soulforged.NAME) {
            WORKSTATION withId "workstation"
        }
    }
}