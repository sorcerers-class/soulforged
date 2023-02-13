package studio.soulforged.soulforged.block.entity

import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixer.TypeReferences
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder
import studio.soulforged.soulforged.block.SoulforgedBlocks

object SoulforgedBlockEntityTypes {
    val WORKSTATION = register("workstation", QuiltBlockEntityTypeBuilder.create({pos, state -> WorkstationBlockEntity(pos, state)}, SoulforgedBlocks.WORKSTATION))
    val BLAST_FURNACE = register("blast_furnace", QuiltBlockEntityTypeBuilder.create({pos, state -> BlastFurnaceBlockEntity(pos, state)}, SoulforgedBlocks.BLAST_FURNACE))
    val FLETCHING_TABLE = register("fletching_table", QuiltBlockEntityTypeBuilder.create({pos, state -> FletchingTableBlockEntity(pos, state)}, SoulforgedBlocks.FLETCHING_TABLE))
    val IRON_ANVIL = register("iron_anvil", QuiltBlockEntityTypeBuilder.create({pos, state -> IronAnvilBlockEntity(pos, state)}, SoulforgedBlocks.IRON_ANVIL))
    val SMITHING_TABLE = register("smithing_table", QuiltBlockEntityTypeBuilder.create({pos, state -> SmithingTableBlockEntity(pos, state)}, SoulforgedBlocks.SMITHING_TABLE))


    fun<T : BlockEntity> register(path: String, builder: QuiltBlockEntityTypeBuilder<T>): BlockEntityType<T> {
        val type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, Identifier("soulforged", path).toString())
        val blockEntity = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier("soulforged", path), builder.build(type))

        return blockEntity
    }
    fun init() {}
}