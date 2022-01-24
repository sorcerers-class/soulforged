package online.maestoso.soulforged.item.tool.part;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import online.maestoso.soulforged.mixin.common.RegistryAccessor;

@SuppressWarnings("unused")
public class ForgedToolParts {
    public static final RegistryKey<Registry<ForgedToolPart>> TOOL_PARTS_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "tool_parts"));
    public static final Registry<ForgedToolPart> TOOL_PARTS_REGISTRY = RegistryAccessor.create(TOOL_PARTS_REGISTRY_KEY, () -> ForgedToolParts.HANDLE);

    public static final ForgedToolPart HANDLE = register(new Identifier("soulforged", "handle"), new ForgedToolPart(0.1, 40, 0/25f));
    public static final ForgedToolPart LONG_HANDLE = register(new Identifier("soulforged", "long_handle"), new ForgedToolPart(0.2, 33, 1/25f));

    public static final ForgedToolPart SHORT_SHAFT = register(new Identifier("soulforged", "short_shaft"), new ForgedToolPart(0.25, 35, 2/25f));
    public static final ForgedToolPart LONG_SHAFT = register(new Identifier("soulforged", "long_shaft"), new ForgedToolPart(0.4, 25, 3/25f));
    public static final ForgedToolPart VERY_LONG_SHAFT /*😩*/= register(new Identifier("soulforged", "very_long_shaft"), new ForgedToolPart(0.6, 20, 4/25f));
    public static final ForgedToolPart JAVELIN_SHAFT = register(new Identifier("soulforged", "javelin_shaft"), new ForgedToolPart(0.3, 28, 5/25f));

    public static final ForgedToolPart BINDING = register(new Identifier("soulforged", "binding"), new ForgedToolPart(0.1, 50, 6/25f));
    public static final ForgedToolPart TOUGH_BINDING = register(new Identifier("soulforged", "tough_binding"), new ForgedToolPart(0.2, 50, 7/25f));

    public static final ForgedToolPart SLIM_HILT = register(new Identifier("soulforged", "slim_hilt"), new ForgedToolPart(0.15, 33, 8/25f));
    public static final ForgedToolPart HILT = register(new Identifier("soulforged", "hilt"), new ForgedToolPart(0.2, 30, 9/25f));
    public static final ForgedToolPart WIDE_HILT = register(new Identifier("soulforged", "wide_hilt"), new ForgedToolPart(0.3, 28, 10/25f));

    public static final ForgedToolPart SHORTSWORD_BLADE = register(new Identifier("soulforged", "shortsword_blade"), new ForgedToolPart(0.75, 1, 11/25f));
    public static final ForgedToolPart BROADSWORD_BLADE = register(new Identifier("soulforged", "broadsword_blade"), new ForgedToolPart(1, 1, 12/25f));
    public static final ForgedToolPart LONGSWORD_BLADE = register(new Identifier("soulforged", "longsword_blade"), new ForgedToolPart(1.15, 0.9, 13/25f));
    public static final ForgedToolPart GREATSWORD_BLADE = register(new Identifier("soulforged", "greatsword_blade"), new ForgedToolPart(1.3, 0.75, 14/25f));
    public static final ForgedToolPart RAPIER_BLADE = register(new Identifier("soulforged", "rapier_blade"), new ForgedToolPart(0.5, 0.9, 15/25f));

    public static final ForgedToolPart SPEARHEAD = register(new Identifier("soulforged", "spearhead"), new ForgedToolPart(0.25, 1.3, 16/25f));
    public static final ForgedToolPart MACEHEAD = register(new Identifier("soulforged", "macehead"), new ForgedToolPart(1, 2, 17/25f));
    public static final ForgedToolPart MORNINGSTAR_HEAD = register(new Identifier("soulforged", "morningstar_head"), new ForgedToolPart(1.1, 0.8, 18/25f));

    public static final ForgedToolPart GREATAXE_HEAD = register(new Identifier("soulforged", "greataxe_head"), new ForgedToolPart(1.5, 0.85, 19/25f));
    public static final ForgedToolPart GREATHAMMER_HEAD = register(new Identifier("soulforged", "greathammer_head"), new ForgedToolPart(1.3, 1.4, 20/25f));
    public static final ForgedToolPart WARHAMMER_HEAD = register(new Identifier("soulforged", "warhammer_head"), new ForgedToolPart(1.2, 1.8, 21/25f));

    public static final ForgedToolPart AXE_HEAD = register(new Identifier("soulforged", "axe_head"), new ForgedToolPart(0.8, 0.95, 22/25f));
    public static final ForgedToolPart PICKAXE_HEAD = register(new Identifier("soulforged", "pickaxe_head"), new ForgedToolPart(1, 1, 23/25f));
    public static final ForgedToolPart SHOVEL_HEAD = register(new Identifier("soulforged", "shovel_head"), new ForgedToolPart(0.5, 1, 24/25f));
    public static final ForgedToolPart HOE_HEAD = register(new Identifier("soulforged", "hoe_head"), new ForgedToolPart(0.2, 1, 25/25f));


    private static ForgedToolPart register(Identifier id, ForgedToolPart type) {
        return Registry.register(TOOL_PARTS_REGISTRY, id, type);
    }
}
