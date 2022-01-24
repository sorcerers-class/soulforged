package online.maestoso.soulforged.material;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.mixin.common.RegistryAccessor;

import java.util.Optional;

@SuppressWarnings("unused")
public class SmithingMaterials {

    public static final RegistryKey<Registry<SmithingMaterial>> SMITHING_MATERIALS_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "smithing_materials"));
    public static final Registry<SmithingMaterial> SMITHING_MATERIALS_REGISTRY = RegistryAccessor.create(SMITHING_MATERIALS_REGISTRY_KEY, () -> SmithingMaterials.IRON); // This is possibly the worst way of doing this but i don't care

    // Earlygame SmithingMaterials
    public static final SmithingMaterial WOOD = register(new Identifier("soulforged", "wood"), new SmithingMaterial(4, 2.5, 0, 300, 200, 0, 0, 0, 2, true, true, Optional.empty()));
    public static final SmithingMaterial STONE = register(new Identifier("soulforged", "stone"), new SmithingMaterial(5, 3.9, 0, 700, 250, 0, 0, 1, 4, true, false, Optional.empty()));
    public static final SmithingMaterial LEATHER = register(new Identifier("soulforged", "leather"), new SmithingMaterial(0.5, 0, 0, 250, 150, 0, 20, 0, 0, false, true, Optional.empty()));
    public static final SmithingMaterial FLINT = register(new Identifier("soulforged", "flint"), new SmithingMaterial(4.9, 4.5, 0, 0, 660, 300, 0, 1, 3, true, false, Optional.empty()));
    public static final SmithingMaterial BONE = register(new Identifier("soulforged", "bone"), new SmithingMaterial(5, 3.8, 0, 200, 100, 0, 0, 0, 1, true, true, Optional.empty()));
    public static final SmithingMaterial WOOL = register(new Identifier("soulforged", "wool"), new SmithingMaterial(0.2, 0, 0, 80, 100, 0, 20, 0, 0,false, true, Optional.empty()));
    public static final SmithingMaterial SCUTE = register(new Identifier("soulforged", "scute"), new SmithingMaterial(7, 0, 1, 550, 850, 0, 10, 0, 0, false, true, Optional.empty()));
    public static final SmithingMaterial SPIDER_WEAVE = register(new Identifier("soulforged", "spider_weave"), new SmithingMaterial(0.6, 0, 1, 180, 1100, 0, 12, 0, 0, false, true, Optional.empty()));
    public static final SmithingMaterial SLIME_WEAVE = register(new Identifier("soulforged", "slime_weave"), new SmithingMaterial(0.1, 0, 1, 300, 300, 0, 40, 0, 0, false, true, Optional.empty()));
    // Earlygame Metals
    public static final SmithingMaterial IRON = register(new Identifier("soulforged", "iron"), new SmithingMaterial(7, 5.8, 1, 600, 600, 3, 0, 2, 6, true, true, Optional.empty()));
    public static final SmithingMaterial COPPER = register(new Identifier("soulforged", "copper"), new SmithingMaterial(6.5, 5, 1, 570, 700, 3, 0, 2, 5, true, true, Optional.empty()));
    public static final SmithingMaterial GOLD = register(new Identifier("soulforged", "gold"), new SmithingMaterial(2.5, 2, 2, 1150, 150, 2, 3, 0, 12, true, true, Optional.empty()));
    public static final SmithingMaterial SILVER = register(new Identifier("soulforged", "silver"), new SmithingMaterial(5, 5, 2, 800, 670, 3, 0, 1, 10, true, true, Optional.empty()));
    public static final SmithingMaterial LEAD = register(new Identifier("soulforged", "lead"), new SmithingMaterial(4.5, 2.6, 2, 950, 300, 2, 1, 2, 6, true, true, Optional.empty()));
    public static final SmithingMaterial STEEL = register(new Identifier("soulforged", "steel"), new SmithingMaterial(7.5, 7, 3, 590, 850, 4, 0, 3, 7, true, true, Optional.empty()));
    // Earthly Crystals
    public static final SmithingMaterial AMETHYST = register(new Identifier("soulforged", "amethyst"), new SmithingMaterial(9, 12, 3, 400, 200, 0, 0, 2, 5, true, false, Optional.empty()));
    public static final SmithingMaterial EMERALD = register(new Identifier("soulforged", "emerald"), new SmithingMaterial(6.5, 7, 3, 440, 770, 0, 0,1, 4, true, false, Optional.empty()));
    public static final SmithingMaterial DIAMOND = register(new Identifier("soulforged", "diamond"), new SmithingMaterial(7, 8, 4, 460, 1300, 0, 0,3, 8, true, false, Optional.empty()));
    public static final SmithingMaterial OBSIDIAN = register(new Identifier("soulforged", "obsidian"), new SmithingMaterial(11, 5.7, 4, 1000, 1000, 0, 0, 3, 10, true, false, Optional.empty()));
    //Lategame SmithingMaterials
    public static final SmithingMaterial WITHERBONE = register(new Identifier("soulforged", "witherbone"), new SmithingMaterial(7, 4.5, 3, 150, 980, 0, 0, 1, 1, true, true, Optional.empty()));
    public static final SmithingMaterial ENDICITE = register(new Identifier("soulforged", "endicite"), new SmithingMaterial(4, 3.1, 3, 5, 150, 0, 0, 3, 7, true, false, Optional.empty()));
    public static final SmithingMaterial MONSTER_LEATHER = register(new Identifier("soulforged", "monster_leather"), new SmithingMaterial(0.8, 0, 2, 200, 800, 0, 30, 0, 0, false, true, Optional.empty()));
    public static final SmithingMaterial SCALES = register(new Identifier("soulforged", "scales"), new SmithingMaterial(6.5, 0, 2, 400, 690, 15, 0, 0, 0, false, true, Optional.empty()));
    // Corundum
    public static final SmithingMaterial SAPPHIRE = register(new Identifier("soulforged", "sapphire"), new SmithingMaterial(8, 8, 4, 390, 680, 0, 0,3, 7, true, false, Optional.of(Classifiers.SAPPHIRE)));
    public static final SmithingMaterial RUBY = register(new Identifier("soulforged", "ruby"), new SmithingMaterial(8, 8, 4, 390, 680, 0, 0,3, 7, true, false, Optional.of(Classifiers.RUBY)));
    public static final SmithingMaterial LOTUS_GEM = register(new Identifier("soulforged", "lotus_gem"), new SmithingMaterial(8, 8, 4, 390, 680, 0, 0, 3, 7, true, false, Optional.of(Classifiers.LOTUS)));
    public static final SmithingMaterial STAR_GEM = register(new Identifier("soulforged", "star_gem"), new SmithingMaterial(8, 8, 4, 390, 680, 0, 0, 3, 7, true, false, Optional.of(Classifiers.STAR)));
    // Lategame Metals
    public static final SmithingMaterial OSMIUM = register(new Identifier("soulforged", "osmium"), new SmithingMaterial(5, 9, 4, 1400, 1100, 4, 0, 3, 7, true, true, Optional.empty()));
    public static final SmithingMaterial NETHERITE = register(new Identifier("soulforged", "netherite"), new SmithingMaterial(9.5, 9, 3, 630, 1500, 5, 0, 4, 9, true, true, Optional.empty()));
    public static final SmithingMaterial ALUMINIUM = register(new Identifier("soulforged", "aluminium"), new SmithingMaterial(5.5, 6.8, 2, 320, 400, 3, 0, 1, 5, true, true, Optional.empty()));
    public static final SmithingMaterial TITANIUM = register(new Identifier("soulforged", "titanium"), new SmithingMaterial(7.7, 7.5, 4, 450, 900, 4, 0, 4, 11, true, true, Optional.empty()));
    // Endgame Alloys
    public static final SmithingMaterial VOID_METAL = register(new Identifier("soulforged", "void_metal"), new SmithingMaterial(8.9, 9, 4, 90, 1000, 4, 0, 4, 9, true, true, Optional.empty()));
    public static final SmithingMaterial NETHERIUM = register(new Identifier("soulforged", "netherium"), new SmithingMaterial(12, 11, 5, 500, 2000, 5, 0, 5, 12, true, true, Optional.empty()));
    public static final SmithingMaterial NEUTRONIUM = register(new Identifier("soulforged", "neutronium"), new SmithingMaterial(13, 13, 5, 2000, 1500, 5, 0, 5, 14, true, true, Optional.empty()));
    public static final SmithingMaterial DIVINIUM = register(new Identifier("soulforged", "divinium"), new SmithingMaterial(7, 4, 5, 660, 900, 5, 0, 5, 12, true, true, Optional.empty()));
    public static final SmithingMaterial STAR_METAL = register(new Identifier("soulforged", "star_metal"), new SmithingMaterial(9, 7, 4, 590, 1300, 4, 0, 4, 10, true, true, Optional.empty()));
    public static final SmithingMaterial MERCURIUM = register(new Identifier("soulforged", "mercurium"), new SmithingMaterial(6.9 /*nice*/, 6, 3, 690, 800, 4, 0, 4, 16, true, true, Optional.empty()));
    public static final SmithingMaterial ETERNICITE = register(new Identifier("soulforged", "eternicite"), new SmithingMaterial(8.5, 6, 5, 650, 3000, 0, 0, 4, 8, true, false, Optional.empty()));


    private static SmithingMaterial register(Identifier id, SmithingMaterial mat) {
        Soulforged.LOGGER.debug("Try register: " + id.toString());
        return Registry.register(SMITHING_MATERIALS_REGISTRY, id, mat);
    }
}
