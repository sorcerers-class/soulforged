package online.maestoso.soulforged.item.tool;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import online.maestoso.soulforged.item.tool.combat.AttackProperties;
import online.maestoso.soulforged.mixin.common.RegistryAccessor;
import static online.maestoso.soulforged.item.tool.RightClickEventProcessors.*;
import static online.maestoso.soulforged.item.tool.MiningSpeedProcessors.*;
import static online.maestoso.soulforged.item.tool.combat.WeaponCategories.*;
import static online.maestoso.soulforged.item.tool.combat.CritTypes.*;

import java.util.Optional;
@SuppressWarnings("unused")
public class ToolTypes {
    public static final RegistryKey<Registry<ToolType>> TOOL_TYPES_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "tool_types"));
    public static final Registry<ToolType> TOOL_TYPES_REGISTRY = RegistryAccessor.create(TOOL_TYPES_REGISTRY_KEY, () -> ToolTypes.SHORTSWORD);

    public static final ToolType SHORTSWORD = register(new Identifier("soulforged", "shortsword"), new ToolType(new AttackProperties(0.7, 0.2, 10.0, 1.2, 0.1, THRUSTING, SIDE), Optional.empty(), Optional.of(new AttackProperties(0.5, 0.2, 18, 1.2, 0.8, THRUSTING, FORWARD)), SWORD, NONE));
    public static final ToolType BROADSWORD = register(new Identifier("soulforged", "broadsword"), new ToolType(new AttackProperties(1, 0.2, 8, 1, 0.2, SLASHING, SIDE), Optional.of(new AttackProperties(0.9, 0.2, 1, 1, 0.2, SLASHING, SIDE)), Optional.of(new AttackProperties(0.75, 0.2, 15, 1, 0.8, THRUSTING, FORWARD)), SWORD, NONE));
    public static final ToolType LONGSWORD = register(new Identifier("soulforged", "longsword"), new ToolType(new AttackProperties(1.3, 0.25, 3.0, 0.8, 0.35, SLASHING, SIDE), Optional.of(new AttackProperties(1.1, 0.25, 1, 0.8, 0.3, SLASHING, SIDE)), Optional.of(new AttackProperties(1, 0.25, 10, 0.8, 0.8, THRUSTING, FORWARD)), SWORD, NONE));
    public static final ToolType GREATSWORD = register(new Identifier("soulforged", "greatsword"), new ToolType(new AttackProperties(1.5, 0.3, 1, 0.6, 0.4, SLASHING, SIDE), Optional.of(new AttackProperties(1.3, 0.3, 1.0, 0.6, 0.3, SLASHING, SIDE)), Optional.empty(), SWORD, NONE));
    public static final ToolType RAPIER = register(new Identifier("soulforged", "rapier"), new ToolType(new AttackProperties(0.6, 0.1, 20, 1.3, 0.4, THRUSTING, FORWARD), Optional.empty(), Optional.empty(), SWORD, NONE));
    public static final ToolType SHORTSPEAR = register(new Identifier("soulforged", "shortspear"), new ToolType(new AttackProperties(0.75, 0.25, 5, 0.8, 1, THRUSTING, FORWARD), Optional.of(new AttackProperties(0.75, 0.25, 0, 0.8, 0.9, THRUSTING, FORWARD)), Optional.empty(), HAND, NONE));
    public static final ToolType LONGSPEAR = register(new Identifier("soulforged", "longspear"), new ToolType(new AttackProperties(0.8, 0.3, 3, 0.5, 1.3, THRUSTING, FORWARD), Optional.of(new AttackProperties(0.8, 0.3, 0, 0.5, 1.2, THRUSTING, FORWARD)), Optional.empty(), HAND, NONE));
    public static final ToolType JAVELIN = register(new Identifier("soulforged", "javelin"), new ToolType(new AttackProperties(0.8, 0.3, 3, 0.5, 1.3, THRUSTING, FORWARD), Optional.of(new AttackProperties(0.8, 0.25, 0, 0.9, 0.85, THRUSTING, FORWARD)), Optional.empty(), HAND, NONE));
    public static final ToolType MACE = register(new Identifier("soulforged", "mace"), new ToolType(new AttackProperties(0, 0.8, 10, 1.2, 0, CRUSHING, DOWN), Optional.empty(), Optional.empty(), HAND, NONE));
    public static final ToolType MORNINGSTAR = register(new Identifier("soulforged", "morningstar"), new ToolType(new AttackProperties(1, 1, 5, 0.75, 0.75, CRUSHING, DOWN), Optional.empty(), Optional.empty(), HAND, NONE));
    public static final ToolType GREATAXE = register(new Identifier("soulforged", "greataxe"), new ToolType(new AttackProperties(1.2, 0.6, 3, 0.5, 0.4, SLASHING, DOWN), Optional.of(new AttackProperties(1, 0.4, 1, 0.5, 0.3, SLASHING, SIDE)), Optional.of(new AttackProperties(1.4, 0.7, 1, 0.5, 0.6, CRUSHING, DOWN)), MiningSpeedProcessors.AXE, AXE_INTERACTIONS));
    public static final ToolType GREATHAMMER = register(new Identifier("soulforged", "greathammer"), new ToolType(new AttackProperties(0, 1.3, 1, 0.35, 0, CRUSHING, DOWN), Optional.of(new AttackProperties(0, 1.4, 1, 0.35, 0, CRUSHING, DOWN)), Optional.empty(), MiningSpeedProcessors.PICKAXE, NONE));
    public static final ToolType WARHAMMER = register(new Identifier("soulforged", "warhammer"), new ToolType(new AttackProperties(0, 1, 1.5, 0.8, 0, CRUSHING, DOWN), Optional.empty(), Optional.of(new AttackProperties(0.75, 0.75, 2, 0.8, 1, THRUSTING, DOWN)), MiningSpeedProcessors.PICKAXE, NONE));
    public static final ToolType AXE = register(new Identifier("soulforged", "axe"), new ToolType(new AttackProperties(0.9, 0.5, 4, 0.9, 0.3, SLASHING, DOWN), Optional.empty(), Optional.of(new AttackProperties(0.8, 0.6, 1, 0.9, 0.4, CRUSHING, DOWN)), MiningSpeedProcessors.AXE, AXE_INTERACTIONS));
    public static final ToolType PICKAXE = register(new Identifier("soulforged", "pickaxe"), new ToolType(new AttackProperties(1, 0.3, 4, 0.6, 1.3, THRUSTING, DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.PICKAXE, NONE));
    public static final ToolType SHOVEL = register(new Identifier("soulforged", "shovel"), new ToolType(new AttackProperties(0.1, 0.75, 3, 1.3, 0, CRUSHING, DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.SHOVEL, SHOVEL_INTERACTIONS));
    public static final ToolType HOE = register(new Identifier("soulforged", "hoe"), new ToolType(new AttackProperties(0.2, 0.2, 5, 1.4, 0.2, CRUSHING, DOWN), Optional.empty(), Optional.empty(), MiningSpeedProcessors.HOE, HOE_INTERACTIONS));


    private static ToolType register(Identifier id, ToolType type) {
        return Registry.register(TOOL_TYPES_REGISTRY, id, type);
    }
}
