package online.maestoso.soulforged.material;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.mixin.common.RegistryAccessor;

public class Materials {

    public static final RegistryKey<Registry<Material>> TOOL_MATERIALS_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "tool_materials"));
    public static final Registry<Material> TOOL_MATERIALS_REGISTRY = RegistryAccessor.create(TOOL_MATERIALS_REGISTRY_KEY, () -> Materials.WOOD);
    public static final Material WOOD = register(new Identifier("soulforged", "wood"), new Material(4, 2.5, 1.0, 300, 200, 0, 0));

    private static Material register(Identifier id, Material mat) {
        Soulforged.LOGGER.debug("Try register: " + mat.getHardness());
        return Registry.register(TOOL_MATERIALS_REGISTRY, id, mat);
    }
}
