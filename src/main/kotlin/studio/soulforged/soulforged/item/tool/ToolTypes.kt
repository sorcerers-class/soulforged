package studio.soulforged.soulforged.item.tool

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.combat.CritTypes
import studio.soulforged.soulforged.item.tool.combat.WeaponCategories

@Suppress("unused")
object ToolTypes {
    private val TOOL_TYPES_REGISTRY_KEY: RegistryKey<Registry<ToolType>> = RegistryKey.ofRegistry<ToolType>(Identifier("soulforged", "tool_types"))
    val TOOL_TYPES_REGISTRY: Registry<ToolType> = Registry.registerSimple(TOOL_TYPES_REGISTRY_KEY) { SHORTSWORD }
    val SHORTSWORD = register(
        Identifier("soulforged", "shortsword"),
        ToolType(
            "item.soulforged.tool.type.shortsword",
            AttackProperties(0.7, 0.2, 10.0, 1.2, 0.1, WeaponCategories.THRUSTING, CritTypes.SIDE),
            null,
            AttackProperties(0.5, 0.2, 18.0, 1.2, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE
        )
    )
    val BROADSWORD = register(
        Identifier("soulforged", "broadsword"),
        ToolType(
            "item.soulforged.tool.type.broadsword",
            AttackProperties(1.0, 0.2, 8.0, 1.0, 0.2, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(0.9, 0.2, 1.0, 1.0, 0.2, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(0.75, 0.2, 15.0, 1.0, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE
        )
    )
    val LONGSWORD = register(
        Identifier("soulforged", "longsword"),
        ToolType(
            "item.soulforged.tool.type.longsword",
            AttackProperties(1.3, 0.25, 3.0, 0.8, 0.35, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(1.1, 0.25, 1.0, 0.8, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(1.0, 0.25, 10.0, 0.8, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE
        )
    )
    val GREATSWORD = register(
        Identifier("soulforged", "greatsword"),
        ToolType(
            "item.soulforged.tool.type.greatsword",
            AttackProperties(1.5, 0.3, 1.0, 0.6, 0.4, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(1.3, 0.3, 1.0, 0.6, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE),
            null,
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE
        )
    )
    val RAPIER = register(
        Identifier("soulforged", "rapier"),
        ToolType(
            "item.soulforged.tool.type.rapier",
            AttackProperties(0.6, 0.1, 20.0, 1.3, 0.4, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            null,
            null,
            MiningSpeedProcessors.SWORD,
            RightClickEventProcessors.NONE
        )
    )
    val SHORTSPEAR = register(
        Identifier("soulforged", "shortspear"),
        ToolType(
            "item.soulforged.tool.type.shortspear",
            AttackProperties(0.75, 0.25, 5.0, 0.8, 1.0, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            AttackProperties(0.75, 0.25, 0.0, 0.8, 0.9, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE
        )
    )
    val LONGSPEAR = register(
        Identifier("soulforged", "longspear"),
        ToolType(
            "item.soulforged.tool.type.longspear",
            AttackProperties(0.8, 0.3, 3.0, 0.5, 1.3, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            AttackProperties(0.8, 0.3, 0.0, 0.5, 1.2, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE
        )
    )
    val JAVELIN = register(
        Identifier("soulforged", "javelin"),
        ToolType(
            "item.soulforged.tool.type.javelin",
            AttackProperties(0.8, 0.3, 3.0, 0.5, 1.3, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            AttackProperties(0.8, 0.25, 0.0, 0.9, 0.85, WeaponCategories.THRUSTING, CritTypes.FORWARD),
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE
        )
    )
    val MACE = register(
        Identifier("soulforged", "mace"),
        ToolType(
            "item.soulforged.tool.type.mace",
            AttackProperties(0.0, 0.8, 10.0, 1.2, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE
        )
    )
    val MORNINGSTAR = register(
        Identifier("soulforged", "morningstar"),
        ToolType(
            "item.soulforged.tool.type.morningstar",
            AttackProperties(1.0, 1.0, 5.0, 0.75, 0.75, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.HAND,
            RightClickEventProcessors.NONE
        )
    )
    val GREATAXE = register(
        Identifier("soulforged", "greataxe"),
        ToolType(
            "item.soulforged.tool.type.greataxe",
            AttackProperties(1.2, 0.6, 3.0, 0.5, 0.4, WeaponCategories.SLASHING, CritTypes.DOWN),
            AttackProperties(1.0, 0.4, 1.0, 0.5, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE),
            AttackProperties(1.4, 0.7, 1.0, 0.5, 0.6, WeaponCategories.CRUSHING, CritTypes.DOWN),
            MiningSpeedProcessors.AXE,
            RightClickEventProcessors.AXE_INTERACTIONS
        )
    )
    val GREATHAMMER = register(
        Identifier("soulforged", "greathammer"),
        ToolType(
            "item.soulforged.tool.type.greathammer",
            AttackProperties(0.0, 1.3, 1.0, 0.35, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            AttackProperties(0.0, 1.4, 1.0, 0.35, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            MiningSpeedProcessors.PICKAXE,
            RightClickEventProcessors.NONE
        )
    )
    val WARHAMMER = register(
        Identifier("soulforged", "warhammer"),
        ToolType(
            "item.soulforged.tool.type.warhammer",
            AttackProperties(0.0, 1.0, 1.5, 0.8, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            AttackProperties(0.75, 0.75, 2.0, 0.8, 1.0, WeaponCategories.THRUSTING, CritTypes.DOWN),
            MiningSpeedProcessors.PICKAXE,
            RightClickEventProcessors.NONE
        )
    )
    val AXE = register(
        Identifier("soulforged", "axe"),
        ToolType(
            "item.soulforged.tool.type.axe",
            AttackProperties(0.9, 0.5, 4.0, 0.9, 0.3, WeaponCategories.SLASHING, CritTypes.DOWN),
            null,
            AttackProperties(0.8, 0.6, 1.0, 0.9, 0.4, WeaponCategories.CRUSHING, CritTypes.DOWN),
            MiningSpeedProcessors.AXE,
            RightClickEventProcessors.AXE_INTERACTIONS
        )
    )
    val PICKAXE = register(
        Identifier("soulforged", "pickaxe"),
        ToolType(
            "item.soulforged.tool.type.pickaxe",
            AttackProperties(1.0, 0.3, 4.0, 0.6, 1.3, WeaponCategories.THRUSTING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.PICKAXE,
            RightClickEventProcessors.NONE
        )
    )
    val SHOVEL = register(
        Identifier("soulforged", "shovel"),
        ToolType(
            "item.soulforged.tool.type.shovel",
            AttackProperties(0.1, 0.75, 3.0, 1.3, 0.0, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.SHOVEL,
            RightClickEventProcessors.SHOVEL_INTERACTIONS
        )
    )
    val HOE = register(
        Identifier("soulforged", "hoe"),
        ToolType(
            "item.soulforged.tool.type.hoe",
            AttackProperties(0.2, 0.2, 5.0, 1.4, 0.2, WeaponCategories.CRUSHING, CritTypes.DOWN),
            null,
            null,
            MiningSpeedProcessors.HOE,
            RightClickEventProcessors.HOE_INTERACTIONS
        )
    )

    private fun register(id: Identifier, type: ToolType): ToolType {
        return Registry.register(TOOL_TYPES_REGISTRY, id, type)
    }
}