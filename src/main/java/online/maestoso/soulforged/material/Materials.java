package online.maestoso.soulforged.material;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.mixin.common.RegistryAccessor;

import java.util.Optional;

@SuppressWarnings("unused")
public class Materials {

    public static final RegistryKey<Registry<Material>> MATERIAL_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "materials"));
    public static final Registry<Material> MATERIAL_REGISTRY = RegistryAccessor.create(MATERIAL_REGISTRY_KEY, () -> Materials.IRON); // This is possibly the worst way of doing this but i don't care

    // Earlygame Materials
    public static final Material WOOD = register(new Identifier("soulforged", "wood"), new Material(4, 2.5, 0, 300, 200, 0, 0, 0, 2, true, true, Optional.empty()));
    public static final Material STONE = register(new Identifier("soulforged", "stone"), new Material(5, 3.9, 0, 700, 250, 0, 0, 1, 4, true, false, Optional.empty()));
    public static final Material LEATHER = register(new Identifier("soulforged", "leather"), new Material(0.5, 0, 0, 250, 150, 0, 20, 0, 0, false, true, Optional.empty()));
    public static final Material FLINT = register(new Identifier("soulforged", "flint"), new Material(4.9, 4.5, 0, 0, 660, 300, 0, 1, 3, true, false, Optional.empty()));
    public static final Material BONE = register(new Identifier("soulforged", "bone"), new Material(5, 3.8, 0, 200, 100, 0, 0, 0, 1, true, true, Optional.empty()));
    public static final Material WOOL = register(new Identifier("soulforged", "wool"), new Material(0.2, 0, 0, 80, 100, 0, 20, 0, 0,false, true, Optional.empty()));
    public static final Material SCUTE = register(new Identifier("soulforged", "scute"), new Material(7, 0, 1, 550, 850, 0, 10, 0, 0, false, true, Optional.empty()));
    public static final Material SPIDER_WEAVE = register(new Identifier("soulforged", "spider_weave"), new Material(0.6, 0, 1, 180, 1100, 0, 12, 0, 0, false, true, Optional.empty()));
    public static final Material SLIME_WEAVE = register(new Identifier("soulforged", "slime_weave"), new Material(0.1, 0, 1, 300, 300, 0, 40, 0, 0, false, true, Optional.empty()));
    // Earlygame Metals
    public static final Material IRON = register(new Identifier("soulforged", "iron"), new Material(7, 5.8, 1, 600, 600, 3, 0, 2, 6, true, true, Optional.empty()));
    public static final Material COPPER = register(new Identifier("soulforged", "copper"), new Material(6.5, 5, 1, 570, 700, 3, 0, 2, 5, true, true, Optional.empty()));
    public static final Material GOLD = register(new Identifier("soulforged", "gold"), new Material(2.5, 2, 2, 1150, 150, 2, 3, 0, 12, true, true, Optional.empty()));
    public static final Material SILVER = register(new Identifier("soulforged", "silver"), new Material(5, 5, 2, 800, 670, 3, 0, 1, 10, true, true, Optional.empty()));
    public static final Material LEAD = register(new Identifier("soulforged", "lead"), new Material(4.5, 2.6, 2, 950, 300, 2, 1, 2, 6, true, true, Optional.empty()));
    public static final Material STEEL = register(new Identifier("soulforged", "steel"), new Material(7.5, 7, 3, 590, 850, 4, 0, 3, 7, true, true, Optional.empty()));
    // Earthly Crystals
    public static final Material AMETHYST = register(new Identifier("soulforged", "amethyst"), new Material(9, 12, 3, 400, 200, 0, 0, 2, 5, true, false, Optional.empty()));
    public static final Material EMERALD = register(new Identifier("soulforged", "emerald"), new Material(6.5, 7, 3, 440, 770, 0, 0,1, 4, true, false, Optional.empty()));
    public static final Material DIAMOND = register(new Identifier("soulforged", "diamond"), new Material(7, 8, 4, 460, 1300, 0, 0,3, 8, true, false, Optional.empty()));
    public static final Material OBSIDIAN = register(new Identifier("soulforged", "obsidian"), new Material(11, 5.7, 4, 1000, 1000, 0, 0, 3, 10, true, false, Optional.empty()));
    //Lategame Materials
    public static final Material WITHERBONE = register(new Identifier("soulforged", "witherbone"), new Material(7, 4.5, 3, 150, 980, 0, 0, 1, 1, true, true, Optional.empty()));
    public static final Material ENDICITE = register(new Identifier("soulforged", "endicite"), new Material(4, 3.1, 3, 5, 150, 0, 0, 3, 7, true, false, Optional.empty()));
    public static final Material MONSTER_LEATHER = register(new Identifier("soulforged", "monster_leather"), new Material(0.8, 0, 2, 200, 800, 0, 30, 0, 0, false, true, Optional.empty()));
    public static final Material SCALES = register(new Identifier("soulforged", "scales"), new Material(6.5, 0, 2, 400, 690, 15, 0, 0, 0, false, true, Optional.empty()));
    // Corundum
    public static final Material SAPPHIRE = register(new Identifier("soulforged", "sapphire"), new Material(8, 8, 4, 390, 680, 0, 0,3, 7, true, false, Optional.of(Classifiers.SAPPHIRE)));
    public static final Material RUBY = register(new Identifier("soulforged", "ruby"), new Material(8, 8, 4, 390, 680, 0, 0,3, 7, true, false, Optional.of(Classifiers.RUBY)));
    public static final Material LOTUS_GEM = register(new Identifier("soulforged", "lotus_gem"), new Material(8, 8, 4, 390, 680, 0, 0, 3, 7, true, false, Optional.of(Classifiers.LOTUS)));
    public static final Material STAR_GEM = register(new Identifier("soulforged", "star_gem"), new Material(8, 8, 4, 390, 680, 0, 0, 3, 7, true, false, Optional.of(Classifiers.STAR)));
    // Lategame Metals
    public static final Material OSMIUM = register(new Identifier("soulforged", "osmium"), new Material(5, 9, 4, 1400, 1100, 4, 0, 3, 7, true, true, Optional.empty()));
    public static final Material NETHERITE = register(new Identifier("soulforged", "netherite"), new Material(9.5, 9, 3, 630, 1500, 5, 0, 4, 9, true, true, Optional.empty()));
    public static final Material ALUMINIUM = register(new Identifier("soulforged", "aluminium"), new Material(5.5, 6.8, 2, 320, 400, 3, 0, 1, 5, true, true, Optional.empty()));
    public static final Material TITANIUM = register(new Identifier("soulforged", "titanium"), new Material(7.7, 7.5, 4, 450, 900, 4, 0, 4, 11, true, true, Optional.empty()));
    // Endgame Alloys
    public static final Material VOID_METAL = register(new Identifier("soulforged", "void_metal"), new Material(8.9, 9, 4, 90, 1000, 4, 0, 4, 9, true, true, Optional.empty()));
    public static final Material NETHERIUM = register(new Identifier("soulforged", "netherium"), new Material(12, 11, 5, 500, 2000, 5, 0, 5, 12, true, true, Optional.empty()));
    public static final Material NEUTRONIUM = register(new Identifier("soulforged", "neutronium"), new Material(13, 13, 5, 2000, 1500, 5, 0, 5, 14, true, true, Optional.empty()));
    public static final Material DIVINIUM = register(new Identifier("soulforged", "divinium"), new Material(7, 4, 5, 660, 900, 5, 0, 5, 12, true, true, Optional.empty()));
    public static final Material STAR_METAL = register(new Identifier("soulforged", "star_metal"), new Material(9, 7, 4, 590, 1300, 4, 0, 4, 10, true, true, Optional.empty()));
    public static final Material MERCURIUM = register(new Identifier("soulforged", "mercurium"), new Material(6.9 /*nice*/, 6, 3, 690, 800, 4, 0, 4, 16, true, true, Optional.empty()));
    public static final Material ETERNICITE = register(new Identifier("soulforged", "eternicite"), new Material(8.5, 6, 5, 650, 3000, 0, 0, 4, 8, true, false, Optional.empty()));


    private static Material register(Identifier id, Material mat) {
        Soulforged.LOGGER.debug("Try register: " + id.toString());
        return Registry.register(MATERIAL_REGISTRY, id, mat);
    }
}
