package online.maestoso.soulforged.material;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.mixin.common.RegistryAccessor;

import java.awt.*;

@SuppressWarnings("unused")
public class SmithingMaterials {

    public static final RegistryKey<Registry<SmithingMaterial>> SMITHING_MATERIALS_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "smithing_materials"));
    public static final Registry<SmithingMaterial> SMITHING_MATERIALS_REGISTRY = RegistryAccessor.create(SMITHING_MATERIALS_REGISTRY_KEY, () -> SmithingMaterials.IRON); // This is possibly the worst way of doing this but i don't care

    // Earlygame SmithingMaterials
    public static final SmithingMaterial WOOD = register(new Identifier("soulforged", "wood"), new SmithingMaterial(4, 2.5, 0, 300, 200, 0, 0, 0, 2, new Color(89, 42, 15)));
    public static final SmithingMaterial STONE = register(new Identifier("soulforged", "stone"), new ToolSmithingMaterial(5, 3.9, 0, 700, 250, 0, 1, 4, new Color(63, 63, 63)));
    public static final SmithingMaterial LEATHER = register(new Identifier("soulforged", "leather"), new ArmorSmithingMaterial(0.5, 0, 150, 250, 0, 20, new Color(211, 76, 19)));
    public static final SmithingMaterial FLINT = register(new Identifier("soulforged", "flint"), new ToolSmithingMaterial(4.9, 4.5, 0, 660, 300, 0, 1, 3, new Color(22, 22, 22)));
    public static final SmithingMaterial BONE = register(new Identifier("soulforged", "bone"), new SmithingMaterial(5, 3.8f, 0, 200, 100, 0, 0, 0, 1, new Color(252, 252, 252)));
    public static final SmithingMaterial WOOL = register(new Identifier("soulforged", "wool"), new ArmorSmithingMaterial(0.2, 0, 80, 100, 0, 20, new Color(255, 255, 255)));
    public static final SmithingMaterial SCUTE = register(new Identifier("soulforged", "scute"), new ArmorSmithingMaterial(7, 1, 550, 850, 0, 10, new Color(0, 122, 18)));
    public static final SmithingMaterial SPIDER_WEAVE = register(new Identifier("soulforged", "spider_weave"), new ArmorSmithingMaterial(0.6, 1, 180, 1100, 0, 12, new Color(255, 255, 255)));
    public static final SmithingMaterial SLIME_WEAVE = register(new Identifier("soulforged", "slime_weave"), new ArmorSmithingMaterial(0.1, 1, 300, 300, 0, 40, new Color(156, 252, 170)));
    // Earlygame Metals
    public static final SmithingMaterial IRON = register(new Identifier("soulforged", "iron"), new SmithingMaterial(7, 5.8, 1, 600, 600, 3, 0, 2, 6, new Color(104, 98, 94)));
    public static final SmithingMaterial COPPER = register(new Identifier("soulforged", "copper"), new SmithingMaterial(6.5, 5, 1, 570, 700, 3, 0, 2, 5, new Color(232, 124, 23)));
    public static final SmithingMaterial GOLD = register(new Identifier("soulforged", "gold"), new SmithingMaterial(2.5, 2, 2, 1150, 150, 2, 3, 0, 12, new Color(239, 177, 31)));
    public static final SmithingMaterial SILVER = register(new Identifier("soulforged", "silver"), new SmithingMaterial(5, 5, 2, 800, 670, 3, 0, 1, 10, new Color(151, 175, 175)));
    public static final SmithingMaterial LEAD = register(new Identifier("soulforged", "lead"), new SmithingMaterial(4.5, 2.6, 2, 950, 300, 2, 1, 2, 6, new Color(42, 55, 63)));
    public static final SmithingMaterial STEEL = register(new Identifier("soulforged", "steel"), new SmithingMaterial(7.5, 7, 3, 590, 850, 4, 0, 3, 7, new Color(119, 119, 119)));
    // Earthly Crystals
    public static final SmithingMaterial AMETHYST = register(new Identifier("soulforged", "amethyst"), new CrystalSmithingMaterial(9, 12, 3, 400, 200, 2, 5, new Color(103, 31, 158)));
    public static final SmithingMaterial EMERALD = register(new Identifier("soulforged", "emerald"), new CrystalSmithingMaterial(6.5, 7, 3, 440, 770, 1, 4, new Color(42, 211, 47)));
    public static final SmithingMaterial DIAMOND = register(new Identifier("soulforged", "diamond"), new CrystalSmithingMaterial(7, 8, 4, 460, 1300, 3, 8, new Color(32, 247, 233)));
    public static final SmithingMaterial OBSIDIAN = register(new Identifier("soulforged", "obsidian"), new CrystalSmithingMaterial(11, 5.7, 4, 1000, 1000, 3, 10, new Color(12, 4, 25)));
    //Lategame SmithingMaterials
    public static final SmithingMaterial WITHERBONE = register(new Identifier("soulforged", "witherbone"), new SmithingMaterial(7, 4.5, 3, 150, 980, 0, 0, 1, 1, new Color(12, 12, 12)));
    public static final SmithingMaterial ENDICITE = register(new Identifier("soulforged", "endicite"), new CrystalSmithingMaterial(4, 3.1, 3, 5, 150, 3, 7, new Color(12, 30, 61)));
    public static final SmithingMaterial MONSTER_LEATHER = register(new Identifier("soulforged", "monster_leather"), new ArmorSmithingMaterial(0.8, 2, 200, 800, 0, 30, new Color(61, 39, 12)));
    public static final SmithingMaterial SCALES = register(new Identifier("soulforged", "scales"), new ArmorSmithingMaterial(6.5, 2, 400, 690, 0, 15, new Color(42, 66, 25)));
    // Corundum
    public static final SmithingMaterial SAPPHIRE = register(new Identifier("soulforged", "sapphire"), new CorundumSmithingMaterial(CorundumTypes.SAPPHIRE, new Color(0, 0, 255)));
    public static final SmithingMaterial RUBY = register(new Identifier("soulforged", "ruby"), new CorundumSmithingMaterial(CorundumTypes.RUBY, new Color(255, 0, 0)));
    public static final SmithingMaterial LOTUS_GEM = register(new Identifier("soulforged", "lotus_gem"), new CorundumSmithingMaterial(CorundumTypes.LOTUS, new Color(252, 138, 242)));
    public static final SmithingMaterial STAR_GEM = register(new Identifier("soulforged", "star_gem"), new CorundumSmithingMaterial(CorundumTypes.STAR, new Color(252, 248, 171)));
    // Lategame Metals
    public static final SmithingMaterial OSMIUM = register(new Identifier("soulforged", "osmium"), new SmithingMaterial(5, 9, 4, 1400, 1100, 4, 0, 3, 7, new Color(169, 237, 252)));
    public static final SmithingMaterial NETHERITE = register(new Identifier("soulforged", "netherite"), new SmithingMaterial(9.5, 9, 3, 630, 1500, 5, 0, 4, 9, new Color(25, 22, 22)));
    public static final SmithingMaterial ALUMINIUM = register(new Identifier("soulforged", "aluminium"), new SmithingMaterial(5.5, 6.8, 2, 320, 400, 3, 0, 1, 5, new Color(226, 220, 220)));
    public static final SmithingMaterial TITANIUM = register(new Identifier("soulforged", "titanium"), new SmithingMaterial(7.7, 7.5, 4, 450, 900, 4, 0, 4, 11, new Color(22, 25, 30)));
    // Endgame Alloys
    public static final SmithingMaterial VOID_METAL = register(new Identifier("soulforged", "void_metal"), new SmithingMaterial(8.9, 9, 4, 90, 1000, 4, 0, 4, 9, new Color(0, 0, 0)));
    public static final SmithingMaterial NETHERIUM = register(new Identifier("soulforged", "netherium"), new SmithingMaterial(12, 11, 5, 500, 2000, 5, 0, 5, 12, new Color(5, 0, 0)));
    public static final SmithingMaterial NEUTRONIUM = register(new Identifier("soulforged", "neutronium"), new SmithingMaterial(13, 13, 5, 2000, 1500, 5, 0, 5, 14, new Color(255, 255, 255)));
    public static final SmithingMaterial DIVINIUM = register(new Identifier("soulforged", "divinium"), new SmithingMaterial(7, 4, 5, 660, 900, 5, 0, 5, 12, new Color(252, 251, 232)));
    public static final SmithingMaterial STAR_METAL = register(new Identifier("soulforged", "star_metal"), new SmithingMaterial(9, 7, 4, 590, 1300, 4, 0, 4, 10, new Color(255, 255, 0)));
    public static final SmithingMaterial MERCURIUM = register(new Identifier("soulforged", "mercurium"), new SmithingMaterial(6.9 /*nice*/, 6, 3, 690, 800, 4, 0, 4, 16, new Color(127, 127, 127)));
    public static final SmithingMaterial ETERNICITE = register(new Identifier("soulforged", "eternicite"), new CrystalSmithingMaterial(8.5, 6, 5, 650, 3000, 4, 8, new Color(14, 114, 83)));


    private static SmithingMaterial register(Identifier id, SmithingMaterial mat) {
        Soulforged.LOGGER.debug("Try register: " + mat.getHardness());
        return Registry.register(SMITHING_MATERIALS_REGISTRY, id, mat);
    }
}
