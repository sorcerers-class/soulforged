package studio.soulforged.soulforged.item.tool.part

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import studio.soulforged.soulforged.util.RegistryUtil

@Suppress("unused")
object ToolParts {
    val DEFAULT = ToolPart(Identifier("soulforged:none"), "missingno", 0.0, 0.0)
    val TOOL_PARTS_REGISTRY: Registry<ToolPart> = RegistryUtil.createRegistry("soulforged:tool_parts", DEFAULT)
    val HANDLE = register(ToolPart(Identifier("soulforged", "handle"), "item.soulforged.part.handle", 0.1, 40.0))
    val LONG_HANDLE = register(ToolPart(Identifier("soulforged", "long_handle"), "item.soulforged.part.long_handle", 0.2, 33.0))
    val SHORT_SHAFT = register(ToolPart(Identifier("soulforged", "short_shaft"), "item.soulforged.part.short_shaft", 0.25, 35.0))
    val LONG_SHAFT = register(ToolPart(Identifier("soulforged", "long_shaft"), "item.soulforged.part.long_shaft", 0.4, 25.0))
    val VERY_LONG_SHAFT /*😩*/ = register(ToolPart(Identifier("soulforged", "very_long_shaft"), "item.soulforged.part.very_long_shaft", 0.6, 20.0))
    val JAVELIN_SHAFT = register(ToolPart(Identifier("soulforged", "javelin_shaft"), "item.soulforged.part.javelin_shaft", 0.3, 28.0))
    val BINDING = register(ToolPart(Identifier("soulforged", "binding"), "item.soulforged.part.binding", 0.1, 50.0))
    val TOUGH_BINDING = register(ToolPart(Identifier("soulforged", "tough_binding"), "item.soulforged.part.tough_binding", 0.2, 50.0))
    val SLIM_HILT = register(ToolPart(Identifier("soulforged", "slim_hilt"), "item.soulforged.part.slim_hilt", 0.15, 33.0))
    val HILT = register(ToolPart(Identifier("soulforged", "hilt"), "item.soulforged.part.hilt", 0.2, 30.0))
    val WIDE_HILT = register(ToolPart(Identifier("soulforged", "wide_hilt"), "item.soulforged.part.wide_hilt", 0.301, 28.0))
    val SHORTSWORD_BLADE = register(ToolPart(Identifier("soulforged", "shortsword_blade"), "item.soulforged.part.shortsword_blade", 0.75, 1.0))
    val BROADSWORD_BLADE = register(ToolPart(Identifier("soulforged", "broadsword_blade"), "item.soulforged.part.broadsword_blade", 1.0, 1.0))
    val LONGSWORD_BLADE = register(ToolPart(Identifier("soulforged", "longsword_blade"), "item.soulforged.part.longsword_blade", 1.15, 0.9))
    val GREATSWORD_BLADE = register(ToolPart(Identifier("soulforged", "greatsword_blade"), "item.soulforged.part.greatsword_blade", 1.3, 0.75))
    val RAPIER_BLADE = register(ToolPart(Identifier("soulforged", "rapier_blade"), "item.soulforged.part.rapier_blade", 0.5, 0.9))
    val SPEARHEAD = register(ToolPart(Identifier("soulforged", "spearhead"), "item.soulforged.part.spearhead", 0.25, 1.3))
    val MACEHEAD = register(ToolPart(Identifier("soulforged", "macehead"), "item.soulforged.part.macehead", 1.0, 2.0))
    val MORNINGSTAR_HEAD = register(ToolPart(Identifier("soulforged", "morningstar_head"), "item.soulforged.part.morningstar_head", 1.1, 0.8))
    val GREATAXE_HEAD = register(ToolPart(Identifier("soulforged", "greataxe_head"), "item.soulforged.part.greataxe_head", 1.5, 0.85))
    val GREATHAMMER_HEAD = register(ToolPart(Identifier("soulforged", "greathammer_head"), "item.soulforged.part.greathammer_head", 1.3, 1.4))
    val WARHAMMER_HEAD = register(ToolPart(Identifier("soulforged", "warhammer_head"), "item.soulforged.part.warhammer_head", 1.2, 1.8))
    val AXE_HEAD = register(ToolPart(Identifier("soulforged", "axe_head"), "item.soulforged.part.axe_head", 0.8, 0.95))
    val PICKAXE_HEAD = register(ToolPart(Identifier("soulforged", "pickaxe_head"), "item.soulforged.part.pickaxe_head", 1.01, 1.0))
    val SHOVEL_HEAD = register(ToolPart(Identifier("soulforged", "shovel_head"), "item.soulforged.part.shovel_head", 0.5, 1.0))
    val HOE_HEAD = register(ToolPart(Identifier("soulforged", "hoe_head"), "item.soulforged.part.hoe_head", 0.2, 1.0))
    val HAMMER_HEAD = register(ToolPart(Identifier("soulforged", "hammer_head"), "item.soulforged.part.hammer_head", 0.1, 5.0))

    private fun register(type: ToolPart): ToolPart {
        return Registry.register(TOOL_PARTS_REGISTRY, type.id, type)
    }
    fun init() {
        register(DEFAULT)
    }
}

