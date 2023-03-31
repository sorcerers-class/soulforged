package studio.soulforged.soulforged.item

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings
import studio.soulforged.soulforged.block.SoulforgedBlocks
import studio.soulforged.soulforged.item.tool.ToolItem

@Suppress("unused")
object SoulforgedItems {
    val TOOL: Item = register("tool", ToolItem())
    val WORKSTATION: Item = register("workstation", BlockItem(
        SoulforgedBlocks.WORKSTATION, QuiltItemSettings())
    )

    // Ores
    val LEAD_ORE = registerBlockItem("lead_ore", SoulforgedBlocks.LEAD_ORE)
    val CINNABAR_ORE = registerBlockItem("cinnabar_ore", SoulforgedBlocks.CINNABAR_ORE)
    val OSMIUM_ORE = registerBlockItem("osmium_ore", SoulforgedBlocks.OSMIUM_ORE)
    val CORUNDUM_ORE = registerBlockItem("corundum_ore", SoulforgedBlocks.CORUNDUM_ORE)
    val ALUMINUM_ORE = registerBlockItem("aluminum_ore", SoulforgedBlocks.ALUMINUM_ORE)
    val END_ALUMINUM_ORE = registerBlockItem("end_aluminum_ore", SoulforgedBlocks.END_ALUMINUM_ORE)
    val TITANIUM_ORE = registerBlockItem("titanium_ore", SoulforgedBlocks.TITANIUM_ORE)
    val END_TITANIUM_ORE = registerBlockItem("end_titanium_ore", SoulforgedBlocks.END_TITANIUM_ORE)
    val ENDICITE_ORE = registerBlockItem("endicite_ore", SoulforgedBlocks.ENDICITE_ORE)
    // Raw Ores
    val RAW_LEAD = register("raw_lead", Item(QuiltItemSettings()))
    val RAW_CINNABAR = register("raw_cinnabar", Item(QuiltItemSettings()))
    val CINNABAR_DUST_BOTTLE = register("cinnabar_dust_bottle", Item(QuiltItemSettings().maxCount(16)))
    val RAW_OSMIUM = register("raw_osmium", Item(QuiltItemSettings()))
    val SAPPHIRE = register("sapphire", Item(QuiltItemSettings()))
    val RUBY = register("ruby", Item(QuiltItemSettings()))
    val PADPARADSCHA = register("padparadscha", Item(QuiltItemSettings()))
    val STAR_GEM = register("star_gem", Item(QuiltItemSettings()))
    val RAW_ALUMINUM = register("raw_aluminum", Item(QuiltItemSettings()))
    val RAW_TITANIUM = register("raw_titanium", Item(QuiltItemSettings()))
    val ENDICITE = register("endicite", Item(QuiltItemSettings()))
    // Ingots
    val LEAD_INGOT = register("lead_ingot", Item(QuiltItemSettings()))
    val MERCURY_BOTTLE = register("mercury_bottle", Item(QuiltItemSettings().maxCount(16)))
    val OSMIUM_INGOT = register("osmium_ingot", Item(QuiltItemSettings()))
    val ALUMINUM_INGOT = register("aluminum_ingot", Item(QuiltItemSettings()))
    val TITANIUM_INGOT = register("titanium_ingot", Item(QuiltItemSettings()))

    val WITHER_BONE = register("wither_bone", Item(QuiltItemSettings()))
    val MONSTER_LEATHER = register("monster_leather", Item(QuiltItemSettings()))
    val SCALES = register("scales", Item(QuiltItemSettings()))
    val SPIDER_WEAVE = register("spider_weave", Item(QuiltItemSettings()))
    val SLIME_WEAVE = register("slime_weave", Item(QuiltItemSettings()))

    val GROUP: ItemGroup = FabricItemGroup.builder(Identifier("soulforged", "soulforged"))
        .icon { ItemStack(MERCURY_BOTTLE) }
        .entries {_, content ->
            content.addItem(WORKSTATION)
            content.addItem(LEAD_ORE)
            content.addItem(CINNABAR_ORE)
            content.addItem(OSMIUM_ORE)
            content.addItem(ALUMINUM_ORE)
            content.addItem(END_ALUMINUM_ORE)
            content.addItem(TITANIUM_ORE)
            content.addItem(END_TITANIUM_ORE)
            content.addItem(CORUNDUM_ORE)
            content.addItem(ENDICITE_ORE)

            content.addItem(RAW_LEAD)
            content.addItem(RAW_CINNABAR)
            content.addItem(RAW_OSMIUM)
            content.addItem(RAW_ALUMINUM)
            content.addItem(RAW_TITANIUM)

            content.addItem(SAPPHIRE)
            content.addItem(RUBY)
            content.addItem(PADPARADSCHA)
            content.addItem(STAR_GEM)
            content.addItem(ENDICITE)

            content.addItem(LEAD_INGOT)
            content.addItem(OSMIUM_INGOT)
            content.addItem(ALUMINUM_INGOT)
            content.addItem(TITANIUM_INGOT)

            content.addItem(CINNABAR_DUST_BOTTLE)
            content.addItem(MERCURY_BOTTLE)

            content.addItem(WITHER_BONE)
            content.addItem(MONSTER_LEATHER)
            content.addItem(SCALES)
            content.addItem(SPIDER_WEAVE)
            content.addItem(SLIME_WEAVE)
        }
        .build()
    fun register(id: String, item: Item): Item {
        return Registry.register(Registries.ITEM, Identifier("soulforged", id), item)
    }
    fun registerBlockItem(id: String, block: Block): BlockItem {
        return register(id, BlockItem(block, QuiltItemSettings())) as BlockItem
    }
    fun init() {}
}