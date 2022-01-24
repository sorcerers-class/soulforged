package online.maestoso.soulforged.item.tool;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import online.maestoso.soulforged.mixin.common.RegistryAccessor;

import java.util.Optional;
@SuppressWarnings("unused")
public class ForgedToolTypes {
    public static final RegistryKey<Registry<ForgedToolType>> TOOL_TYPES_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "tool_types"));
    public static final Registry<ForgedToolType> TOOL_TYPES_REGISTRY = RegistryAccessor.create(TOOL_TYPES_REGISTRY_KEY, () -> ForgedToolTypes.SHORTSWORD);

    public static final ForgedToolType SHORTSWORD = register(new Identifier("soulforged", "shortsword"), new ForgedToolType(new AttackProperties(0.7, 0.2, 10, 1.2, 0.1, WeaponCategories.THRUSTING, CritTypes.SIDE), Optional.empty(), Optional.of(new AttackProperties(0.5, 0.2, 18, 1.2, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD)), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ForgedToolType BROADSWORD = register(new Identifier("soulforged", "broadsword"), new ForgedToolType(new AttackProperties(1.0, 0.2, 8, 1, 0.2, WeaponCategories.SLASHING, CritTypes.SIDE), Optional.of(new AttackProperties(0.9, 0.2, 1, 1, 0.2, WeaponCategories.SLASHING, CritTypes.SIDE)), Optional.of(new AttackProperties(0.75, 0.2, 15, 1, 0.8, WeaponCategories.THRUSTING, CritTypes.FORWARD)), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ForgedToolType LONGSWORD = register(new Identifier("soulforged", "longsword"), new ForgedToolType(new AttackProperties(1.3, 0.25, 3, 0.8, 0.35, WeaponCategories.SLASHING, CritTypes.SIDE), Optional.of(new AttackProperties(1.1, 0.25, 1, 0.8, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE)), Optional.of(new AttackProperties(1, 0.25, 10, 0.8,0.35, WeaponCategories.THRUSTING, CritTypes.FORWARD)), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ForgedToolType GREATSWORD = register(new Identifier("soulforged", "greatsword"), new ForgedToolType(new AttackProperties(1.5, 0.3, 1, 0.6, 0.4, WeaponCategories.SLASHING, CritTypes.SIDE), Optional.of(new AttackProperties(1.3, 0.3, 1, 0.6, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE)), Optional.empty(), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ForgedToolType RAPIER = register(new Identifier("soulforged", "rapier"), new ForgedToolType(new AttackProperties(0.6, 0.1, 20, 1.3, 0.4, WeaponCategories.THRUSTING, CritTypes.FORWARD), Optional.empty(), Optional.empty(), MiningSpeedProcessors.SWORD, RightClickEventProcessors.NONE));
    public static final ForgedToolType SHORTSPEAR = register(new Identifier("soulforged", "shortspear"), new ForgedToolType(new AttackProperties(0.75, 0.25, 5, 0.8, 1, WeaponCategories.THRUSTING, CritTypes.FORWARD), Optional.of(new AttackProperties(0.75, 0.25, 0, 0.8, 0.9, WeaponCategories.THRUSTING, CritTypes.FORWARD)), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ForgedToolType LONGSPEAR = register(new Identifier("soulforged", "longspear"), new ForgedToolType(new AttackProperties(0.8, 0.3, 3, 0.5, 1.3, WeaponCategories.THRUSTING, CritTypes.FORWARD), Optional.of(new AttackProperties(0.8, 0.3, 0, 0.5, 1.2, WeaponCategories.THRUSTING, CritTypes.FORWARD)), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ForgedToolType JAVELIN = register(new Identifier("soulforged", "javelin"), new ForgedToolType(new AttackProperties(0.7, 0.25, 4.5, 0.9, 0.9, WeaponCategories.THRUSTING, CritTypes.FORWARD), Optional.of(new AttackProperties(0.8, 0.25, 0, 0.9, 0.85, WeaponCategories.THRUSTING, CritTypes.FORWARD)), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ForgedToolType MACE = register(new Identifier("soulforged", "mace"), new ForgedToolType(new AttackProperties(0, 0.8, 10, 1.2, 0, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ForgedToolType MORNINGSTAR = register(new Identifier("soulforged", "morningstar"), new ForgedToolType(new AttackProperties(1, 1, 5, 0.75, 0.75, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.HAND, RightClickEventProcessors.NONE));
    public static final ForgedToolType GREATAXE = register(new Identifier("soulforged", "greataxe"), new ForgedToolType(new AttackProperties(1.2, 0.6, 3, 0.5, 0.4, WeaponCategories.SLASHING, CritTypes.DOWN), Optional.of(new AttackProperties(1, 0.4, 1, 0.5, 0.3, WeaponCategories.SLASHING, CritTypes.SIDE)), Optional.of(new AttackProperties(1.4, 0.7, 1, 0.5, 0.6, WeaponCategories.CRUSHING, CritTypes.DOWN)), MiningSpeedProcessors.AXE, RightClickEventProcessors.AXE_INTERACTIONS));
    public static final ForgedToolType GREATHAMMER = register(new Identifier("soulforged", "greathammer"), new ForgedToolType(new AttackProperties(0, 1.3, 1, 0.35, 0, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.of(new AttackProperties(0, 1.4, 1, 0.35,  0, WeaponCategories.CRUSHING, CritTypes.DOWN)), Optional.empty(), MiningSpeedProcessors.PICKAXE, RightClickEventProcessors.NONE));
    public static final ForgedToolType WARHAMMER = register(new Identifier("soulforged", "warhammer"), new ForgedToolType(new AttackProperties(0, 1, 1.5, 0.8, 0, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.of(new AttackProperties(0.75, 0.75, 2, 0.8,  1, WeaponCategories.THRUSTING, CritTypes.DOWN)), MiningSpeedProcessors.PICKAXE, RightClickEventProcessors.NONE));
    public static final ForgedToolType AXE = register(new Identifier("soulforged", "axe"), new ForgedToolType(new AttackProperties(0.9, 0.5, 4, 0.9, 0.3, WeaponCategories.SLASHING, CritTypes.DOWN), Optional.empty(), Optional.of(new AttackProperties(0.8, 0.6, 1, 0.9, 0.4, WeaponCategories.CRUSHING, CritTypes.DOWN)), MiningSpeedProcessors.AXE, RightClickEventProcessors.AXE_INTERACTIONS));
    public static final ForgedToolType PICKAXE = register(new Identifier("soulforged", "pickaxe"), new ForgedToolType(new AttackProperties(1, 0.3, 4, 0.6, 1.3, WeaponCategories.THRUSTING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.PICKAXE, RightClickEventProcessors.NONE));
    public static final ForgedToolType SHOVEL = register(new Identifier("soulforged", "shovel"), new ForgedToolType(new AttackProperties(0.1, 0.75, 3, 1.3, 0, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.SHOVEL, RightClickEventProcessors.SHOVEL_INTERACTIONS));
    public static final ForgedToolType HOE = register(new Identifier("soulforged", "hoe"), new ForgedToolType(new AttackProperties(0.2, 0.2, 5, 1.4, 0.2, WeaponCategories.CRUSHING, CritTypes.DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.HOE, RightClickEventProcessors.HOE_INTERACTIONS));


    private static ForgedToolType register(Identifier id, ForgedToolType type) {
        return Registry.register(TOOL_TYPES_REGISTRY, id, type);
    }
}
