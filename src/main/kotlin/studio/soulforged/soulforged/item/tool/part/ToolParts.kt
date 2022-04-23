package studio.soulforged.soulforged.item.tool.part

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

@Suppress("unused")
object ToolParts {
    val TOOL_PARTS_REGISTRY_KEY: RegistryKey<Registry<ToolPart>> = RegistryKey.ofRegistry<ToolPart>(Identifier("soulforged", "tool_parts"))
    val TOOL_PARTS_REGISTRY: Registry<ToolPart> = Registry.registerSimple(TOOL_PARTS_REGISTRY_KEY, ) {HANDLE}
    val HANDLE = register(Identifier("soulforged", "handle"), ToolPart("item.soulforged.part.handle", 0.1, 40.0))
    val LONG_HANDLE = register(Identifier("soulforged", "long_handle"), ToolPart("item.soulforged.part.long_handle", 0.2, 33.0))
    val SHORT_SHAFT = register(Identifier("soulforged", "short_shaft"), ToolPart("item.soulforged.part.short_shaft", 0.25, 35.0))
    val LONG_SHAFT = register(Identifier("soulforged", "long_shaft"), ToolPart("item.soulforged.part.long_shaft", 0.4, 25.0))
    val VERY_LONG_SHAFT /*😩*/ = register(Identifier("soulforged", "very_long_shaft"), ToolPart("item.soulforged.part.very_long_shaft", 0.6, 20.0))
    val JAVELIN_SHAFT = register(Identifier("soulforged", "javelin_shaft"), ToolPart("item.soulforged.part.javelin_shaft", 0.3, 28.0))
    val BINDING = register(Identifier("soulforged", "binding"), ToolPart("item.soulforged.part.binding", 0.1, 50.0))
    val TOUGH_BINDING = register(Identifier("soulforged", "tough_binding"), ToolPart("item.soulforged.part.tough_binding", 0.2, 50.0))
    val SLIM_HILT = register(Identifier("soulforged", "slim_hilt"), ToolPart("item.soulforged.part.slim_hilt", 0.15, 33.0))
    val HILT = register(Identifier("soulforged", "hilt"), ToolPart("item.soulforged.part.hilt", 0.2, 30.0))
    val WIDE_HILT = register(Identifier("soulforged", "wide_hilt"), ToolPart("item.soulforged.part.wide_hilt", 0.301, 28.0))
    val SHORTSWORD_BLADE = register(Identifier("soulforged", "shortsword_blade"), ToolPart("item.soulforged.part.shortsword_blade", 0.75, 1.0))
    val BROADSWORD_BLADE = register(Identifier("soulforged", "broadsword_blade"), ToolPart("item.soulforged.part.broadsword_blade", 1.0, 1.0))
    val LONGSWORD_BLADE = register(Identifier("soulforged", "longsword_blade"), ToolPart("item.soulforged.part.longsword_blade", 1.15, 0.9))
    val GREATSWORD_BLADE = register(Identifier("soulforged", "greatsword_blade"), ToolPart("item.soulforged.part.greatsword_blade", 1.3, 0.75))
    val RAPIER_BLADE = register(Identifier("soulforged", "rapier_blade"), ToolPart("item.soulforged.part.rapier_blade", 0.5, 0.9))
    val SPEARHEAD = register(Identifier("soulforged", "spearhead"), ToolPart("item.soulforged.part.spearhead", 0.25, 1.3))
    val MACEHEAD = register(Identifier("soulforged", "macehead"), ToolPart("item.soulforged.part.macehead", 1.0, 2.0))
    val MORNINGSTAR_HEAD = register(Identifier("soulforged", "morningstar_head"), ToolPart("item.soulforged.part.morningstar_head", 1.1, 0.8))
    val GREATAXE_HEAD = register(Identifier("soulforged", "greataxe_head"), ToolPart("item.soulforged.part.greataxe_head", 1.5, 0.85))
    val GREATHAMMER_HEAD = register(Identifier("soulforged", "greathammer_head"), ToolPart("item.soulforged.part.greathammer_head", 1.3, 1.4))
    val WARHAMMER_HEAD = register(Identifier("soulforged", "warhammer_head"), ToolPart("item.soulforged.part.warhammer_head", 1.2, 1.8))
    val AXE_HEAD = register(Identifier("soulforged", "axe_head"), ToolPart("item.soulforged.part.axe_head", 0.8, 0.95))
    val PICKAXE_HEAD = register(Identifier("soulforged", "pickaxe_head"), ToolPart("item.soulforged.part.pickaxe_head", 1.01, 1.0))
    val SHOVEL_HEAD = register(Identifier("soulforged", "shovel_head"), ToolPart("item.soulforged.part.shovel_head", 0.5, 1.0))
    val HOE_HEAD = register(Identifier("soulforged", "hoe_head"), ToolPart("item.soulforged.part.hoe_head", 0.2, 1.0))
    private fun register(id: Identifier, type: ToolPart): ToolPart {
        return Registry.register(TOOL_PARTS_REGISTRY, id, type)
    }
}