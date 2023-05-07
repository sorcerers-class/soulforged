package studio.soulforged.soulforged.item

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import org.quiltmc.qkl.library.items.itemSettingsOf
import org.quiltmc.qkl.library.registry.*
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.block.SoulforgedBlocks
import studio.soulforged.soulforged.item.tool.ToolItem

@Suppress("unused")
object SoulforgedItems {
    @JvmField
    val TOOL = ToolItem()

    val WORKSTATION = blockItem(SoulforgedBlocks.WORKSTATION)
    val DEEPSLATE_FORGE_CONTROLLER = blockItem(SoulforgedBlocks.DEEPSLATE_FORGE_CONTROLLER)
    val DEEPSLATE_FORGE_BUNKER = blockItem(SoulforgedBlocks.DEEPSLATE_FORGE_BUNKER)
    val DEEPSLATE_FORGE_BURNER = blockItem(SoulforgedBlocks.DEEPSLATE_FORGE_BURNER)
    // Ores
    val LEAD_ORE = blockItem(SoulforgedBlocks.LEAD_ORE)
    val CINNABAR_ORE = blockItem(SoulforgedBlocks.CINNABAR_ORE)
    val OSMIUM_ORE = blockItem(SoulforgedBlocks.OSMIUM_ORE)
    val CORUNDUM_ORE = blockItem(SoulforgedBlocks.CORUNDUM_ORE)
    val ALUMINUM_ORE = blockItem(SoulforgedBlocks.ALUMINUM_ORE)
    val END_ALUMINUM_ORE = blockItem(SoulforgedBlocks.END_ALUMINUM_ORE)
    val TITANIUM_ORE = blockItem(SoulforgedBlocks.TITANIUM_ORE)
    val END_TITANIUM_ORE = blockItem(SoulforgedBlocks.END_TITANIUM_ORE)
    val ENDICITE_ORE = blockItem(SoulforgedBlocks.ENDICITE_ORE)
    // Raw Ores
    val RAW_LEAD = item()
    val RAW_CINNABAR = item()
    val CINNABAR_DUST_BOTTLE = Item(itemSettingsOf(maxCount = 16))
    val RAW_OSMIUM = item()
    val SAPPHIRE = item()
    val RUBY = item()
    val PADPARADSCHA = item()
    val STAR_GEM = item()
    val RAW_ALUMINUM = item()
    val RAW_TITANIUM = item()
    val ENDICITE = item()
    // Ingots
    val LEAD_INGOT = item()
    val MERCURY_BOTTLE = Item(itemSettingsOf(maxCount = 16))
    val OSMIUM_INGOT = item()
    val ALUMINUM_INGOT = item()
    val TITANIUM_INGOT = item()

    val WITHER_BONE = item()
    val MONSTER_LEATHER = item()
    val SCALES = item()
    val SPIDER_WEAVE = item()
    val SLIME_WEAVE = item()

    private fun blockItem(block: Block): BlockItem = BlockItem(block, itemSettingsOf())
    private fun item() = Item(itemSettingsOf())
    internal fun init() {
        Registries.ITEM(Soulforged.NAME) {
            TOOL withId "tool"

            WORKSTATION withId "workstation"

            DEEPSLATE_FORGE_CONTROLLER withId "deepslate_forge_controller"
            DEEPSLATE_FORGE_BUNKER withId "deepslate_forge_bunker"
            DEEPSLATE_FORGE_BURNER withId "deepslate_forge_burner"

            LEAD_ORE withId "lead_ore"
            CINNABAR_ORE withId "cinnabar_ore"
            OSMIUM_ORE withId "osmium_ore"
            CORUNDUM_ORE withId "corundum_ore"
            ALUMINUM_ORE withId "aluminum_ore"
            END_ALUMINUM_ORE withId "end_aluminum_ore"
            TITANIUM_ORE withId "titanium_ore"
            END_TITANIUM_ORE withId "end_titanium_ore"
            ENDICITE_ORE withId "endicite_ore"

            RAW_LEAD withId "raw_lead"
            RAW_CINNABAR withId "raw_cinnabar"
            CINNABAR_DUST_BOTTLE withId "cinnabar_dust_bottle"
            RAW_OSMIUM withId "raw_osmium"
            SAPPHIRE withId "sapphire"
            RUBY withId "ruby"
            PADPARADSCHA withId "padparadscha"
            STAR_GEM withId "star_gem"
            RAW_ALUMINUM withId "raw_aluminum"
            RAW_TITANIUM withId "raw_titanium"
            ENDICITE withId "endicite"

            LEAD_INGOT withId "lead_ingot"
            MERCURY_BOTTLE withId "mercury_bottle"
            OSMIUM_INGOT withId "osmium_ingot"
            ALUMINUM_INGOT withId "aluminum_ingot"
            TITANIUM_INGOT withId "titanium_ingot"

            WITHER_BONE withId "wither_bone"
            MONSTER_LEATHER withId "monster_leather"
            SCALES withId "scales"
            SPIDER_WEAVE withId "spider_weave"
            SLIME_WEAVE withId "slime_weave"
        }
    }
}