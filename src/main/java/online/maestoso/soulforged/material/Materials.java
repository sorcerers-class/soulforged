package online.maestoso.soulforged.material;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.mixin.common.RegistryAccessor;

@SuppressWarnings("unused")
public class Materials {

    public static final RegistryKey<Registry<Material>> TOOL_MATERIALS_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "tool_materials"));
    public static final Registry<Material> TOOL_MATERIALS_REGISTRY = RegistryAccessor.create(TOOL_MATERIALS_REGISTRY_KEY, () -> Materials.IRON); // This is possibly the worst way of doing this but i don't care

    // Earlygame Materials
    public static final Material WOOD = register(new Identifier("soulforged", "wood"), new Material(4, 2.5, 5, 300, 200, 0, 0));
    public static final Material STONE = register(new Identifier("soulforged", "stone"), new ToolMaterial(5, 3.9, 5, 700, 250, 0));
    public static final Material LEATHER = register(new Identifier("soulforged", "leather"), new ArmorMaterial(0.5, 5, 150, 250, 0, 20));
    public static final Material FLINT = register(new Identifier("soulforged", "flint"), new ToolMaterial(4.9, 4.5, 5, 660, 300, 0));
    public static final Material BONE = register(new Identifier("soulforged", "bone"), new Material(5, 3.8f, 5, 200, 100, 0, 0));
    public static final Material WOOL = register(new Identifier("soulforged", "wool"), new ArmorMaterial(0.2, 5, 80, 100, 0, 20));
    public static final Material SCUTE = register(new Identifier("soulforged", "scute"), new ArmorMaterial(7, 3, 550, 850, 0, 10));
    public static final Material SPIDER_WEAVE = register(new Identifier("soulforged", "spider_weave"), new ArmorMaterial(0.6, 5, 180, 1100, 0, 12));
    public static final Material SLIME_WEAVE = register(new Identifier("soulforged", "slime_weave"), new ArmorMaterial(0.1, 5, 300, 300, 0, 40));
    // Earlygame Metals
    public static final Material IRON = register(new Identifier("soulforged", "iron"), new Material(7, 5.8, 3, 600, 600, 3, 0));
    public static final Material COPPER = register(new Identifier("soulforged", "copper"), new Material(6.5, 5, 3, 570, 700, 3, 0));
    public static final Material GOLD = register(new Identifier("soulforged", "gold"), new Material(2.5, 2, 4, 1150, 150, 2, 3));
    public static final Material SILVER = register(new Identifier("soulforged", "silver"), new Material(5, 5, 3, 800, 670, 3, 0));
    public static final Material LEAD = register(new Identifier("soulforged", "lead"), new Material(4.5, 2.6, 4, 950, 300, 2, 1));
    public static final Material STEEL = register(new Identifier("soulforged", "steel"), new Material(7.5, 7, 2, 590, 850, 4, 0));
    // Earthly Crystals
    public static final Material AMETHYST = register(new Identifier("soulforged", "amethyst"), new CrystalMaterial(9, 12, 2, 400, 200));
    public static final Material EMERALD = register(new Identifier("soulforged", "emerald"), new CrystalMaterial(6.5, 7, 2, 440, 770));
    public static final Material DIAMOND = register(new Identifier("soulforged", "diamond"), new CrystalMaterial(7, 8, 2, 460, 1300));
    public static final Material OBSIDIAN = register(new Identifier("soulforged", "obsidian"), new CrystalMaterial(11, 5.7, 2, 1000, 1000));
    //Lategame Materials
    public static final Material WITHERBONE = register(new Identifier("soulforged", "witherbone"), new Material(7, 4.5, 3, 150, 980, 0, 0));
    public static final Material ENDICITE = register(new Identifier("soulforged", "endicite"), new CrystalMaterial(4, 3.1, 4, 5, 150));
    public static final Material MONSTER_LEATHER = register(new Identifier("soulforged", "monster_leather"), new ArmorMaterial(0.8, 5, 200, 800, 0, 30));
    public static final Material MONSTER_SCALES = register(new Identifier("soulforged", "monster_scales"), new ArmorMaterial(6.5, 3, 400, 690, 0, 15));
    // Corundum
    public static final Material SAPPHIRE = register(new Identifier("soulforged", "sapphire"), new CorundumMaterial(CorundumTypes.SAPPHIRE));
    public static final Material RUBY = register(new Identifier("soulforged", "ruby"), new CorundumMaterial(CorundumTypes.RUBY));
    public static final Material LOTUS_GEM = register(new Identifier("soulforged", "lotus_gem"), new CorundumMaterial(CorundumTypes.LOTUS));
    public static final Material STAR_GEM = register(new Identifier("soulforged", "star_gem"), new CorundumMaterial(CorundumTypes.STAR));
    // Lategame Metals
    public static final Material OSMIUM = register(new Identifier("soulforged", "osmium"), new Material(5, 9, 2, 1400, 1100, 4, 0));
    public static final Material NETHERITE = register(new Identifier("soulforged", "netherite"), new Material(9.5, 9, 3, 630, 1500, 5, 0));
    public static final Material ALUMINIUM = register(new Identifier("soulforged", "aluminium"), new Material(5.5, 6.8, 4, 320, 400, 3, 0));
    public static final Material TITANIUM = register(new Identifier("soulforged", "titanium"), new Material(7.7, 7.5, 1, 450, 900, 4, 0));
    // Endgame Alloys
    public static final Material VOID_METAL = register(new Identifier("soulforged", "void_metal"), new Material(8.9, 9, 1, 90, 1000, 4, 0));
    public static final Material NETHERIUM = register(new Identifier("soulforged", "netherium"), new Material(12, 11, 0, 500, 2000, 5, 0));
    public static final Material NEUTRONIUM = register(new Identifier("soulforged", "neutronium"), new Material(13, 13, 0, 2000, 1500, 5, 0));
    public static final Material DIVINIUM = register(new Identifier("soulforged", "divinium"), new Material(7, 8, 1, 660, 900, 5, 0));
    public static final Material STAR_METAL = register(new Identifier("soulforged", "star_metal"), new Material(9, 7, 1, 590, 1300, 4, 0));
    public static final Material MERCURIUM = register(new Identifier("soulforged", "mercurium"), new Material(6.9 /*nice*/, 6, 3, 690, 800, 4, 0));
    public static final Material ETERNICITE = register(new Identifier("soulforged", "eternicite"), new CrystalMaterial(8.5, 6, 0, 650, 3000));


    private static Material register(Identifier id, Material mat) {
        Soulforged.LOGGER.debug("Try register: " + mat.getHardness());
        return Registry.register(TOOL_MATERIALS_REGISTRY, id, mat);
    }
}
