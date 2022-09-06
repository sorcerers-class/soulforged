package studio.soulforged.soulforged.item.tool

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.combat.CritTypes
import studio.soulforged.soulforged.item.tool.combat.WeaponCategories
import studio.soulforged.soulforged.item.tool.part.ToolParts

@Suppress("unused")
object ToolTypes {
    private val TOOL_TYPES_REGISTRY_KEY: RegistryKey<Registry<ToolType>> = RegistryKey.ofRegistry<ToolType>(Identifier("soulforged", "tool_types"))
    val TOOL_TYPES_REGISTRY: Registry<ToolType> = Registry.registerSimple(TOOL_TYPES_REGISTRY_KEY) { SHORTSWORD }
    val SHORTSWORD = register(
        ToolType(
            Identifier("soulforged", "shortsword"),
            "item.soulforged.tool.type.shortsword",
            AttackProperties(0.7, 0.2, 10.0, 1.2, 0.1, WeaponCategories.THRUSTING, CritTypes.SIDE),
            null,
            AttackProperties(0.5, 0.2, 18.0, 1.2, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.SHORTSWORD_BLADE, ToolParts.HILT, ToolParts.HANDLE)
        )
    )
    val BROADSWORD = register(
        ToolType(
            Identifier("soulforged", "broadsword"),
            "item.soulforged.tool.type.broadsword",
            AttackProperties(1.0, 0.2, 8.0, 1.0, 0.2, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(0.9, 0.2, 1.0, 1.0, 0.2, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(0.75, 0.2, 15.0, 1.0, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.BROADSWORD_BLADE, ToolParts.WIDE_HILT, ToolParts.HANDLE)
        )
    )
    val LONGSWORD = register(
        ToolType(
            Identifier("soulforged", "longsword"),
            "item.soulforged.tool.type.longsword",
            AttackProperties(1.3, 0.25, 3.0, 0.8, 0.35, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(1.1, 0.25, 1.0, 0.8, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(1.0, 0.25, 10.0, 0.8, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.LONGSWORD_BLADE, ToolParts.WIDE_HILT, ToolParts.LONG_HANDLE)
        )
    )
    val GREATSWORD = register(
        ToolType(
            Identifier("soulforged", "greatsword"),
            "item.soulforged.tool.type.greatsword",
            AttackProperties(1.5, 0.3, 1.0, 0.6, 0.4, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(1.3, 0.3, 1.0, 0.6, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE),
            null,
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.GREATSWORD_BLADE, ToolParts.WIDE_HILT, ToolParts.LONG_HANDLE)
        )
    )
    val RAPIER = register(
        ToolType(
            Identifier("soulforged", "rapier"),
            "item.soulforged.tool.type.rapier",
            AttackProperties(0.6, 0.1, 20.0, 1.3, 0.4, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            null,
            null,
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.RAPIER_BLADE, ToolParts.SLIM_HILT, ToolParts.HANDLE)
        )
    )
    val SHORTSPEAR = register(
        ToolType(
            Identifier("soulforged", "shortspear"),
            "item.soulforged.tool.type.shortspear",
            AttackProperties(0.75, 0.25, 5.0, 0.8, 1.0, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            AttackProperties(0.75, 0.25, 0.0, 0.8, 0.9, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.SPEARHEAD, ToolParts.BINDING, ToolParts.LONG_SHAFT)
        )
    )
    val LONGSPEAR = register(
        ToolType(
            Identifier("soulforged", "longspear"),
            "item.soulforged.tool.type.longspear",
            AttackProperties(0.8, 0.3, 3.0, 0.5, 1.3, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            AttackProperties(0.8, 0.3, 0.0, 0.5, 1.2, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.SPEARHEAD, ToolParts.BINDING, ToolParts.VERY_LONG_SHAFT)
        )
    )
    val JAVELIN = register(
        ToolType(
            Identifier("soulforged", "javelin"),
            "item.soulforged.tool.type.javelin",
            AttackProperties(0.8, 0.3, 3.0, 0.5, 1.3, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            AttackProperties(0.8, 0.25, 0.0, 0.9, 0.85, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.SPEARHEAD, ToolParts.BINDING, ToolParts.JAVELIN_SHAFT)
        )
    )
    val MACE = register(
        ToolType(
            Identifier("soulforged", "mace"),
            "item.soulforged.tool.type.mace",
            AttackProperties(0.0, 0.8, 10.0, 1.2, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.MACEHEAD, ToolParts.BINDING, ToolParts.SHORT_SHAFT)
        )
    )
    val MORNINGSTAR = register(
        ToolType(
            Identifier("soulforged", "morningstar"),
            "item.soulforged.tool.type.morningstar",
            AttackProperties(1.0, 1.0, 5.0, 0.75, 0.75, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.MORNINGSTAR_HEAD, ToolParts.BINDING, ToolParts.SHORT_SHAFT)
        )
    )
    val GREATAXE = register(
        ToolType(
            Identifier("soulforged", "greataxe"),
            "item.soulforged.tool.type.greataxe",
            AttackProperties(1.2, 0.6, 3.0, 0.5, 0.4, WeaponCategories.SLASHING, CritTypes.DOWN),
            AttackProperties(1.0, 0.4, 1.0, 0.5, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(1.4, 0.7, 1.0, 0.5, 0.6, WeaponCategories.CRUSHING, CritTypes.DOWN),
            MiningSpeedProcessors.AXE,
            RightClickEventProcessors.AXE_INTERACTIONS,
            arrayOf(ToolParts.GREATAXE_HEAD, ToolParts.TOUGH_BINDING, ToolParts.LONG_HANDLE)
        )
    )
    val GREATHAMMER = register(
        ToolType(
            Identifier("soulforged", "greathammer"),
            "item.soulforged.tool.type.greathammer",
            AttackProperties(0.0, 1.3, 1.0, 0.35, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            AttackProperties(0.0, 1.4, 1.0, 0.35, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            MiningSpeedProcessors.PICKAXE,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.GREATHAMMER_HEAD, ToolParts.TOUGH_BINDING, ToolParts.LONG_HANDLE)
        )
    )
    val WARHAMMER = register(
        ToolType(
            Identifier("soulforged", "warhammer"),
            "item.soulforged.tool.type.warhammer",
            AttackProperties(0.0, 1.0, 1.5, 0.8, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            AttackProperties(0.75, 0.75, 2.0, 0.8, 1.0, WeaponCategories.THRUSTING, CritTypes.DOWN),
            MiningSpeedProcessors.PICKAXE,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.WARHAMMER_HEAD, ToolParts.BINDING, ToolParts.LONG_HANDLE)
        )
    )
    val AXE = register(
        ToolType(
            Identifier("soulforged", "axe"),
            "item.soulforged.tool.type.axe",
            AttackProperties(0.9, 0.5, 4.0, 0.9, 0.3, WeaponCategories.SLASHING, CritTypes.DOWN),
            null,
            AttackProperties(0.8, 0.6, 1.0, 0.9, 0.4, WeaponCategories.CRUSHING, CritTypes.DOWN),
            MiningSpeedProcessors.AXE,
            RightClickEventProcessors.AXE_INTERACTIONS,
            arrayOf(ToolParts.AXE_HEAD, ToolParts.BINDING, ToolParts.SHORT_SHAFT)
        )
    )
    val PICKAXE = register(
        ToolType(
            Identifier("soulforged", "pickaxe"),
            "item.soulforged.tool.type.pickaxe",
            AttackProperties(1.0, 0.3, 4.0, 0.6, 1.3, WeaponCategories.THRUSTING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.PICKAXE,
            RightClickEventProcessors.NONE,
            arrayOf(ToolParts.PICKAXE_HEAD, ToolParts.BINDING, ToolParts.LONG_HANDLE)
        )
    )
    val SHOVEL = register(
        ToolType(
            Identifier("soulforged", "shovel"),
            "item.soulforged.tool.type.shovel",
            AttackProperties(0.1, 0.75, 3.0, 1.3, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.SHOVEL,
            RightClickEventProcessors.SHOVEL_INTERACTIONS,
            arrayOf(ToolParts.SHOVEL_HEAD, ToolParts.BINDING, ToolParts.LONG_HANDLE)
        )
    )
    val HOE = register(
        ToolType(
            Identifier("soulforged", "hoe"),
            "item.soulforged.tool.type.hoe",
            AttackProperties(0.2, 0.2, 5.0, 1.4, 0.2, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.HOE,
            RightClickEventProcessors.HOE_INTERACTIONS,
            arrayOf(ToolParts.HOE_HEAD, ToolParts.BINDING, ToolParts.LONG_HANDLE)
        )
    )

    private fun register(type: ToolType): ToolType {
        return Registry.register(TOOL_TYPES_REGISTRY, type.id, type)
    }
}