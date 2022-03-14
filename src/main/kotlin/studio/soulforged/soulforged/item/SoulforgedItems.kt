package studio.soulforged.soulforged.item

import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import studio.soulforged.soulforged.item.tool.ToolItem

object SoulforgedItems {
    var TOOL: Item = Registry.register(Registry.ITEM, Identifier("soulforged", "tool"), ToolItem())
}