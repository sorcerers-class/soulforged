package online.maestoso.soulforged.item.tool.part;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import online.maestoso.soulforged.mixin.common.RegistryAccessor;

@SuppressWarnings("unused")
public class ToolParts {
    public static final RegistryKey<Registry<ToolPart>> TOOL_PARTS_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "tool_parts"));
    public static final Registry<ToolPart> TOOL_PARTS_REGISTRY = RegistryAccessor.create(TOOL_PARTS_REGISTRY_KEY, () -> ToolParts.HANDLE);

    public static final ToolPart HANDLE = register(new Identifier("soulforged", "handle"), new ToolPart(0.1, 40));
    public static final ToolPart LONG_HANDLE = register(new Identifier("soulforged", "long_handle"), new ToolPart(0.2, 33));

    public static final ToolPart SHORT_SHAFT = register(new Identifier("soulforged", "short_shaft"), new ToolPart(0.25, 35));
    public static final ToolPart LONG_SHAFT = register(new Identifier("soulforged", "long_shaft"), new ToolPart(0.4, 25));
    public static final ToolPart VERY_LONG_SHAFT /*😩*/= register(new Identifier("soulforged", "very_long_shaft"), new ToolPart(0.6, 20));
    public static final ToolPart JAVELIN_SHAFT = register(new Identifier("soulforged", "javelin_shaft"), new ToolPart(0.3, 28));

    public static final ToolPart BINDING = register(new Identifier("soulforged", "binding"), new ToolPart(0.1, 50));
    public static final ToolPart TOUGH_BINDING = register(new Identifier("soulforged", "tough_binding"), new ToolPart(0.2, 50));

    public static final ToolPart SLIM_HILT = register(new Identifier("soulforged", "slim_hilt"), new ToolPart(0.15, 33));
    public static final ToolPart HILT = register(new Identifier("soulforged", "hilt"), new ToolPart(0.2, 30));
    public static final ToolPart WIDE_HILT = register(new Identifier("soulforged", "wide_hilt"), new ToolPart(0.301, 28));

    public static final ToolPart SHORTSWORD_BLADE = register(new Identifier("soulforged", "shortsword_blade"), new ToolPart(0.75, 1));
    public static final ToolPart BROADSWORD_BLADE = register(new Identifier("soulforged", "broadsword_blade"), new ToolPart(1, 1));
    public static final ToolPart LONGSWORD_BLADE = register(new Identifier("soulforged", "longsword_blade"), new ToolPart(1.15, 0.9));
    public static final ToolPart GREATSWORD_BLADE = register(new Identifier("soulforged", "greatsword_blade"), new ToolPart(1.3, 0.75));
    public static final ToolPart RAPIER_BLADE = register(new Identifier("soulforged", "rapier_blade"), new ToolPart(0.5, 0.9));

    public static final ToolPart SPEARHEAD = register(new Identifier("soulforged", "spearhead"), new ToolPart(0.25, 1.3));
    public static final ToolPart MACEHEAD = register(new Identifier("soulforged", "macehead"), new ToolPart(1, 2));
    public static final ToolPart MORNINGSTAR_HEAD = register(new Identifier("soulforged", "morningstar_head"), new ToolPart(1.1, 0.8));

    public static final ToolPart GREATAXE_HEAD = register(new Identifier("soulforged", "greataxe_head"), new ToolPart(1.5, 0.85));
    public static final ToolPart GREATHAMMER_HEAD = register(new Identifier("soulforged", "greathammer_head"), new ToolPart(1.3, 1.4));
    public static final ToolPart WARHAMMER_HEAD = register(new Identifier("soulforged", "warhammer_head"), new ToolPart(1.2, 1.8));

    public static final ToolPart AXE_HEAD = register(new Identifier("soulforged", "axe_head"), new ToolPart(0.8, 0.95));
    public static final ToolPart PICKAXE_HEAD = register(new Identifier("soulforged", "pickaxe_head"), new ToolPart(1.01, 1));
    public static final ToolPart SHOVEL_HEAD = register(new Identifier("soulforged", "shovel_head"), new ToolPart(0.5, 1));
    public static final ToolPart HOE_HEAD = register(new Identifier("soulforged", "hoe_head"), new ToolPart(0.2, 1));


    private static ToolPart register(Identifier id, ToolPart type) {
        return Registry.register(TOOL_PARTS_REGISTRY, id, type);
    }
}
