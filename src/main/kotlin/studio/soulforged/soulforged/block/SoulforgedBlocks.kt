package studio.soulforged.soulforged.block

import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import java.util.*

@Suppress("unused")
object SoulforgedBlocks {
    // Crafting-related stuff. TODO
    val BLAST_FURNACE: Block = register("blast_furnace", BlastFurnaceBlock())
    val FLETCHING_TABLE: Block = register("fletching_table", FletchingTableBlock())
    val IRON_ANVIL: Block = register("iron_anvil", IronAnvilBlock())
    val SMITHING_TABLE: Block = register("smithing_table", SmithingTableBlock())
    val WORKSTATION: Block = register("workstation", WorkstationBlock(QuiltBlockSettings.of(Material.WOOD)))
    // Ores
    val SILVER_ORE: Block = register("silver_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val DEEPSLATE_SILVER_ORE: Block = register("deepslate_silver_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val LEAD_ORE: Block = register("lead_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val CINNABAR_ORE: Block = register("cinnabar_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val OSMIUM_ORE: Block = register("osmium_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val CORUNDUM_ORE: Block = register("corundum_ore", Block(QuiltBlockSettings.of(Material.AMETHYST)))
    val ALUMINUM_ORE: Block = register("aluminum_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val END_ALUMINUM_ORE: Block = register("end_aluminum_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val TITANIUM_ORE: Block = register("titanium_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val END_TITANIUM_ORE: Block = register("end_titanium_ore", Block(QuiltBlockSettings.of(Material.STONE)))
    val ENDICITE_ORE: Block = register("endicite_ore", Block(QuiltBlockSettings.of(Material.STONE)))

    private fun register(id: String, block: Block): Block {
        return Registry.register(Registries.BLOCK, Identifier("soulforged", id), block)
    }
    internal fun init() {}
}