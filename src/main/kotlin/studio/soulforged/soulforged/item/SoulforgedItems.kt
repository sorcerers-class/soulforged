package studio.soulforged.soulforged.item

import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings
import studio.soulforged.soulforged.block.SoulforgedBlocks
import studio.soulforged.soulforged.item.tool.ToolItem

object SoulforgedItems {
    val TOOL: Item = Registry.register(Registry.ITEM, Identifier("soulforged", "tool"), ToolItem())
    val WORKSTATION: Item = Registry.register(
        Registry.ITEM, Identifier("soulforged", "workstation"), BlockItem(
        SoulforgedBlocks.WORKSTATION, QuiltItemSettings())
    )
    fun init() {}
}