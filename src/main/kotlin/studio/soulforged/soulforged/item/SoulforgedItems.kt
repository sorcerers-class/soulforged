package studio.soulforged.soulforged.item

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.tool.ToolItem

object SoulforgedItems {
    val TOOL: Item = Registry.register(Registries.ITEM, Identifier("soulforged", "tool"), ToolItem())
}