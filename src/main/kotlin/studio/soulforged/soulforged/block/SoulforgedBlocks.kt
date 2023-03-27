package studio.soulforged.soulforged.block

import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import java.util.*

@Suppress("unused")
object SoulforgedBlocks {
    val BLAST_FURNACE: Block = register("blast_furnace", BlastFurnaceBlock())
    val FLETCHING_TABLE: Block = register("fletching_table", FletchingTableBlock())
    val IRON_ANVIL: Block = register("iron_anvil", IronAnvilBlock())
    val SMITHING_TABLE: Block = register("smithing_table", SmithingTableBlock())
    val WORKSTATION: Block = register("workstation", WorkstationBlock(QuiltBlockSettings.of(Material.WOOD)))
    private fun register(id: String, block: Block): Block {
        return Registry.register(Registry.BLOCK, Identifier("soulforged", id), block)
    }
    internal fun init() {}
}