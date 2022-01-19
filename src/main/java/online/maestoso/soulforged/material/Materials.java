package online.maestoso.soulforged.material;

import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import org.apache.commons.collections4.ListUtils;
import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.mixin.common.RegistryAccessor;

import java.awt.*;
import java.util.List;

@SuppressWarnings("unused")
public class Materials {

    public static final RegistryKey<Registry<Material>> TOOL_MATERIALS_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("soulforged", "tool_materials"));
    public static final Registry<Material> TOOL_MATERIALS_REGISTRY = RegistryAccessor.create(TOOL_MATERIALS_REGISTRY_KEY, () -> Materials.IRON); // This is possibly the worst way of doing this but i don't care

    // Earlygame Materials
    public static final Material WOOD = register(new Identifier("soulforged", "wood"), new Material(4, 2.5, 0, 300, 200, 0, 0, new Color(89, 42, 15)));
    public static final Material STONE = register(new Identifier("soulforged", "stone"), new ToolMaterial(5, 3.9, 0, 700, 250, 0, new Color(63, 63, 63)));
    public static final Material LEATHER = register(new Identifier("soulforged", "leather"), new ArmorMaterial(0.5, 0, 150, 250, 0, 20, new Color(211, 76, 19)));
    public static final Material FLINT = register(new Identifier("soulforged", "flint"), new ToolMaterial(4.9, 4.5, 0, 660, 300, 0, new Color(22, 22, 22)));
    public static final Material BONE = register(new Identifier("soulforged", "bone"), new Material(5, 3.8f, 0, 200, 100, 0, 0, new Color(252, 252, 252)));
    public static final Material WOOL = register(new Identifier("soulforged", "wool"), new ArmorMaterial(0.2, 0, 80, 100, 0, 20, new Color(255, 255, 255)));
    public static final Material SCUTE = register(new Identifier("soulforged", "scute"), new ArmorMaterial(7, 1, 550, 850, 0, 10, new Color(0, 122, 18)));
    public static final Material SPIDER_WEAVE = register(new Identifier("soulforged", "spider_weave"), new ArmorMaterial(0.6, 1, 180, 1100, 0, 12, new Color(255, 255, 255)));
    public static final Material SLIME_WEAVE = register(new Identifier("soulforged", "slime_weave"), new ArmorMaterial(0.1, 1, 300, 300, 0, 40, new Color(156, 252, 170)));
    // Earlygame Metals
    public static final Material IRON = register(new Identifier("soulforged", "iron"), new Material(7, 5.8, 1, 600, 600, 3, 0, new Color(104, 98, 94)));
    public static final Material COPPER = register(new Identifier("soulforged", "copper"), new Material(6.5, 5, 1, 570, 700, 3, 0, new Color(232, 124, 23)));
    public static final Material GOLD = register(new Identifier("soulforged", "gold"), new Material(2.5, 2, 2, 1150, 150, 2, 3, new Color(239, 177, 31)));
    public static final Material SILVER = register(new Identifier("soulforged", "silver"), new Material(5, 5, 2, 800, 670, 3, 0, new Color(151, 175, 175)));
    public static final Material LEAD = register(new Identifier("soulforged", "lead"), new Material(4.5, 2.6, 2, 950, 300, 2, 1, new Color(42, 55, 63)));
    public static final Material STEEL = register(new Identifier("soulforged", "steel"), new Material(7.5, 7, 3, 590, 850, 4, 0, new Color(119, 119, 119)));
    // Earthly Crystals
    public static final Material AMETHYST = register(new Identifier("soulforged", "amethyst"), new CrystalMaterial(9, 12, 3, 400, 200, new Color(103, 31, 158)));
    public static final Material EMERALD = register(new Identifier("soulforged", "emerald"), new CrystalMaterial(6.5, 7, 3, 440, 770, new Color(42, 211, 47)));
    public static final Material DIAMOND = register(new Identifier("soulforged", "diamond"), new CrystalMaterial(7, 8, 4, 460, 1300, new Color(32, 247, 233)));
    public static final Material OBSIDIAN = register(new Identifier("soulforged", "obsidian"), new CrystalMaterial(11, 5.7, 4, 1000, 1000, new Color(12, 4, 25)));
    //Lategame Materials
    public static final Material WITHERBONE = register(new Identifier("soulforged", "witherbone"), new Material(7, 4.5, 3, 150, 980, 0, 0, new Color(12, 12, 12)));
    public static final Material ENDICITE = register(new Identifier("soulforged", "endicite"), new CrystalMaterial(4, 3.1, 3, 5, 150, new Color(12, 30, 61)));
    public static final Material MONSTER_LEATHER = register(new Identifier("soulforged", "monster_leather"), new ArmorMaterial(0.8, 2, 200, 800, 0, 30, new Color(61, 39, 12)));
    public static final Material SCALES = register(new Identifier("soulforged", "scales"), new ArmorMaterial(6.5, 2, 400, 690, 0, 15, new Color(42, 66, 25)));
    // Corundum
    public static final Material SAPPHIRE = register(new Identifier("soulforged", "sapphire"), new CorundumMaterial(CorundumTypes.SAPPHIRE, new Color(0, 0, 255)));
    public static final Material RUBY = register(new Identifier("soulforged", "ruby"), new CorundumMaterial(CorundumTypes.RUBY, new Color(255, 0, 0)));
    public static final Material LOTUS_GEM = register(new Identifier("soulforged", "lotus_gem"), new CorundumMaterial(CorundumTypes.LOTUS, new Color(252, 138, 242)));
    public static final Material STAR_GEM = register(new Identifier("soulforged", "star_gem"), new CorundumMaterial(CorundumTypes.STAR, new Color(252, 248, 171)));
    // Lategame Metals
    public static final Material OSMIUM = register(new Identifier("soulforged", "osmium"), new Material(5, 9, 4, 1400, 1100, 4, 0, new Color(169, 237, 252)));
    public static final Material NETHERITE = register(new Identifier("soulforged", "netherite"), new Material(9.5, 9, 3, 630, 1500, 5, 0, new Color(25, 22, 22)));
    public static final Material ALUMINIUM = register(new Identifier("soulforged", "aluminium"), new Material(5.5, 6.8, 2, 320, 400, 3, 0, new Color(226, 220, 220)));
    public static final Material TITANIUM = register(new Identifier("soulforged", "titanium"), new Material(7.7, 7.5, 4, 450, 900, 4, 0, new Color(22, 25, 30)));
    // Endgame Alloys
    public static final Material VOID_METAL = register(new Identifier("soulforged", "void_metal"), new Material(8.9, 9, 4, 90, 1000, 4, 0, new Color(0, 0, 0)));
    public static final Material NETHERIUM = register(new Identifier("soulforged", "netherium"), new Material(12, 11, 5, 500, 2000, 5, 0, new Color(5, 0, 0)));
    public static final Material NEUTRONIUM = register(new Identifier("soulforged", "neutronium"), new Material(13, 13, 5, 2000, 1500, 5, 0, new Color(255, 255, 255)));
    public static final Material DIVINIUM = register(new Identifier("soulforged", "divinium"), new Material(7, 4, 5, 660, 900, 5, 0, new Color(252, 251, 232)));
    public static final Material STAR_METAL = register(new Identifier("soulforged", "star_metal"), new Material(9, 7, 4, 590, 1300, 4, 0, new Color(255, 255, 0)));
    public static final Material MERCURIUM = register(new Identifier("soulforged", "mercurium"), new Material(6.9 /*nice*/, 6, 3, 690, 800, 4, 0, new Color(127, 127, 127)));
    public static final Material ETERNICITE = register(new Identifier("soulforged", "eternicite"), new CrystalMaterial(8.5, 6, 5, 650, 3000, new Color(14, 114, 83)));


    private static Material register(Identifier id, Material mat) {
        Soulforged.LOGGER.debug("Try register: " + mat.getHardness());
        return Registry.register(TOOL_MATERIALS_REGISTRY, id, mat);
    }
}
