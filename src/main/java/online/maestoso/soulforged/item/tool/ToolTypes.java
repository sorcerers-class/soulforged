package online.maestoso.soulforged.item.tool;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import online.maestoso.soulforged.item.tool.attack.AttackProperties;
import online.maestoso.soulforged.item.tool.attack.CritTypes;
import online.maestoso.soulforged.item.tool.attack.WeaponCategories;
import online.maestoso.soulforged.mixin.common.RegistryAccessor;

import java.util.Optional;
@SuppressWarnings("unused")
public class ToolTypes {
    public static final RegistryKey<Registry<ToolType>> TOOL_TYPES_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "tool_types"));
    public static final Registry<ToolType> TOOL_TYPES_REGISTRY = RegistryAccessor.create(TOOL_TYPES_REGISTRY_KEY, () -> ToolTypes.SHORTSWORD);

    public static final ToolType SHORTSWORD = register(new Identifier("soulforged", "shortsword"), new ToolType(new AttackProperties(0.7, 0.2, 10, 1.2, 0.1, WeaponCategories.THRUSTING, CritTypes.SIDE), Optional.empty(), Optional.of(new AttackProperties(0.5, 0.2, 18, 1.2, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD)), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ToolType BROADSWORD = register(new Identifier("soulforged", "broadsword"), new ToolType(new AttackProperties(1.0, 0.2, 8, 1, 0.2, WeaponCategories.SLASHING, CritTypes.SIDE), Optional.of(new AttackProperties(0.9, 0.2, 1, 1, 0.2, WeaponCategories.SLASHING, CritTypes.SIDE)), Optional.of(new AttackProperties(0.75, 0.2, 15, 1, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD)), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ToolType LONGSWORD = register(new Identifier("soulforged", "longsword"), new ToolType(new AttackProperties(1.3, 0.25, 3, 0.8, 0.35, WeaponCategories.SLASHING, CritTypes.SIDE), Optional.of(new AttackProperties(1.1, 0.25, 1, 0.8, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE)), Optional.of(new AttackProperties(1, 0.25, 10, 0.8,0.35, WeaponCategories.THRUSTING, CritTypes.FORWARD)), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ToolType GREATSWORD = register(new Identifier("soulforged", "greatsword"), new ToolType(new AttackProperties(1.5, 0.3, 1, 0.6, 0.4, WeaponCategories.SLASHING, CritTypes.SIDE), Optional.of(new AttackProperties(1.3, 0.3, 1, 0.6, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE)), Optional.empty(), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ToolType RAPIER = register(new Identifier("soulforged", "rapier"), new ToolType(new AttackProperties(0.6, 0.1, 20, 1.3, 0.4, WeaponCategories.THRUSTING, CritTypes.FORWARD), Optional.empty(), Optional.empty(), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ToolType SHORTSPEAR = register(new Identifier("soulforged", "shortspear"), new ToolType(new AttackProperties(0.75, 0.25, 5, 0.8, 1, WeaponCategories.THRUSTING, CritTypes.FORWARD), Optional.of(new AttackProperties(0.75, 0.25, 0, 0.8, 0.9, WeaponCategories.THRUSTING, CritTypes.FORWARD)), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ToolType LONGSPEAR = register(new Identifier("soulforged", "longspear"), new ToolType(new AttackProperties(0.8, 0.3, 3, 0.5, 1.3, WeaponCategories.THRUSTING, CritTypes.FORWARD), Optional.of(new AttackProperties(0.8, 0.3, 0, 0.5, 1.2, WeaponCategories.THRUSTING, CritTypes.FORWARD)), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ToolType JAVELIN = register(new Identifier("soulforged", "javelin"), new ToolType(new AttackProperties(0.7, 0.25, 4.5, 0.9, 0.9, WeaponCategories.THRUSTING, CritTypes.FORWARD), Optional.of(new AttackProperties(0.8, 0.25, 0, 0.9, 0.85, WeaponCategories.THRUSTING, CritTypes.FORWARD)), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ToolType MACE = register(new Identifier("soulforged", "mace"), new ToolType(new AttackProperties(0, 0.8, 10, 1.2, 0, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ToolType MORNINGSTAR = register(new Identifier("soulforged", "morningstar"), new ToolType(new AttackProperties(1, 1, 5, 0.75, 0.75, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ToolType GREATAXE = register(new Identifier("soulforged", "greataxe"), new ToolType(new AttackProperties(1.2, 0.6, 3, 0.5, 0.4, WeaponCategories.SLASHING, CritTypes.DOWN), Optional.of(new AttackProperties(1, 0.4, 1, 0.5, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE)), Optional.of(new AttackProperties(1.4, 0.7, 1, 0.5, 0.6, WeaponCategories.CRUSHING, CritTypes.DOWN)), MiningSpeedProcessors.AXE, RightClickEventProcessors.AXE_INTERACTIONS));
    public static final ToolType GREATHAMMER = register(new Identifier("soulforged", "greathammer"), new ToolType(new AttackProperties(0, 1.3, 1, 0.35, 0, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.of(new AttackProperties(0, 1.4, 1, 0.35,  0, WeaponCategories.CRUSHING, CritTypes.DOWN)), Optional.empty(), MiningSpeedProcessors.PICKAXE, RightClickEventProcessors.NONE));
    public static final ToolType WARHAMMER = register(new Identifier("soulforged", "warhammer"), new ToolType(new AttackProperties(0, 1, 1.5, 0.8, 0, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.of(new AttackProperties(0.75, 0.75, 2, 0.8,  1, WeaponCategories.THRUSTING, CritTypes.DOWN)), MiningSpeedProcessors.PICKAXE, RightClickEventProcessors.NONE));
    public static final ToolType AXE = register(new Identifier("soulforged", "axe"), new ToolType(new AttackProperties(0.9, 0.5, 4, 0.9, 0.3, WeaponCategories.SLASHING, CritTypes.DOWN), Optional.empty(), Optional.of(new AttackProperties(0.8, 0.6, 1, 0.9, 0.4, WeaponCategories.CRUSHING, CritTypes.DOWN)), MiningSpeedProcessors.AXE, RightClickEventProcessors.AXE_INTERACTIONS));
    public static final ToolType PICKAXE = register(new Identifier("soulforged", "pickaxe"), new ToolType(new AttackProperties(1, 0.3, 4, 0.6, 1.3, WeaponCategories.THRUSTING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.PICKAXE, RightClickEventProcessors.NONE));
    public static final ToolType SHOVEL = register(new Identifier("soulforged", "shovel"), new ToolType(new AttackProperties(0.1, 0.75, 3, 1.3, 0, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.SHOVEL, RightClickEventProcessors.SHOVEL_INTERACTIONS));
    public static final ToolType HOE = register(new Identifier("soulforged", "hoe"), new ToolType(new AttackProperties(0.2, 0.2, 5, 1.4, 0.2, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.HOE, RightClickEventProcessors.HOE_INTERACTIONS));


    private static ToolType register(Identifier id, ToolType type) {
        return Registry.register(TOOL_TYPES_REGISTRY, id, type);
    }
}
