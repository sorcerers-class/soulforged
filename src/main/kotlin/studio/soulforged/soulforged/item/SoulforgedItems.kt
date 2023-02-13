package studio.soulforged.soulforged.item

import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings
import studio.soulforged.soulforged.block.SoulforgedBlocks
import studio.soulforged.soulforged.item.tool.ToolItem

object SoulforgedItems {
    val TOOL: Item = Registry.register(Registries.ITEM, Identifier("soulforged", "tool"), ToolItem())
    val WORKSTATION: Item = Registry.register(Registries.ITEM, Identifier("soulforged", "workstation"), BlockItem(
        SoulforgedBlocks.WORKSTATION, QuiltItemSettings())
    )
    fun init() {}
}