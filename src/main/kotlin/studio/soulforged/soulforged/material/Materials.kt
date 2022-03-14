package studio.soulforged.soulforged.material

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import studio.soulforged.soulforged.mixin.common.RegistryAccessor
import studio.soulforged.soulforged.Soulforged
import java.util.*

object Materials {
    val MATERIAL_REGISTRY_KEY = RegistryKey.ofRegistry<Material>(Identifier("soulforged", "materials"))
    val MATERIAL_REGISTRY =
        RegistryAccessor.create(MATERIAL_REGISTRY_KEY) { IRON } // This is possibly the worst way of doing this but i don't care

    // Earlygame Materials
    val WOOD = register(
        Identifier("soulforged", "wood"),
        Material(4.0, 2.5, 0, 300, 200, 0, 0, 0, 2, true, true, Optional.empty<Any>())
    )
    val STONE = register(
        Identifier("soulforged", "stone"),
        Material(5.0, 3.9, 0, 700, 250, 0, 0, 1, 4, true, false, Optional.empty<Any>())
    )
    val LEATHER = register(
        Identifier("soulforged", "leather"),
        Material(0.5, 0.0, 0, 250, 150, 0, 20, 0, 0, false, true, Optional.empty<Any>())
    )
    val FLINT = register(
        Identifier("soulforged", "flint"),
        Material(4.9, 4.5, 0, 0, 660, 300, 0, 1, 3, true, false, Optional.empty<Any>())
    )
    val BONE = register(
        Identifier("soulforged", "bone"),
        Material(5.0, 3.8, 0, 200, 100, 0, 0, 0, 1, true, true, Optional.empty<Any>())
    )
    val WOOL = register(
        Identifier("soulforged", "wool"),
        Material(0.2, 0.0, 0, 80, 100, 0, 20, 0, 0, false, true, Optional.empty<Any>())
    )
    val SCUTE = register(
        Identifier("soulforged", "scute"),
        Material(7.0, 0.0, 1, 550, 850, 0, 10, 0, 0, false, true, Optional.empty<Any>())
    )
    val SPIDER_WEAVE = register(
        Identifier("soulforged", "spider_weave"),
        Material(0.6, 0.0, 1, 180, 1100, 0, 12, 0, 0, false, true, Optional.empty<Any>())
    )
    val SLIME_WEAVE = register(
        Identifier("soulforged", "slime_weave"),
        Material(0.1, 0.0, 1, 300, 300, 0, 40, 0, 0, false, true, Optional.empty<Any>())
    )

    // Earlygame Metals
    val IRON = register(
        Identifier("soulforged", "iron"),
        Material(7.0, 5.8, 1, 600, 600, 3, 0, 2, 6, true, true, Optional.empty<Any>())
    )
    val COPPER = register(
        Identifier("soulforged", "copper"),
        Material(6.5, 5.0, 1, 570, 700, 3, 0, 2, 5, true, true, Optional.empty<Any>())
    )
    val GOLD = register(
        Identifier("soulforged", "gold"),
        Material(2.5, 2.0, 2, 1150, 150, 2, 3, 0, 12, true, true, Optional.empty<Any>())
    )
    val SILVER = register(
        Identifier("soulforged", "silver"),
        Material(5.0, 5.0, 2, 800, 670, 3, 0, 1, 10, true, true, Optional.empty<Any>())
    )
    val LEAD = register(
        Identifier("soulforged", "lead"),
        Material(4.5, 2.6, 2, 950, 300, 2, 1, 2, 6, true, true, Optional.empty<Any>())
    )
    val STEEL = register(
        Identifier("soulforged", "steel"),
        Material(7.5, 7.0, 3, 590, 850, 4, 0, 3, 7, true, true, Optional.empty<Any>())
    )

    // Earthly Crystals
    val AMETHYST = register(
        Identifier("soulforged", "amethyst"),
        Material(9.0, 12.0, 3, 400, 200, 0, 0, 2, 5, true, false, Optional.empty<Any>())
    )
    val EMERALD = register(
        Identifier("soulforged", "emerald"),
        Material(6.5, 7.0, 3, 440, 770, 0, 0, 1, 4, true, false, Optional.empty<Any>())
    )
    val DIAMOND = register(
        Identifier("soulforged", "diamond"),
        Material(7.0, 8.0, 4, 460, 1300, 0, 0, 3, 8, true, false, Optional.empty<Any>())
    )
    val OBSIDIAN = register(
        Identifier("soulforged", "obsidian"),
        Material(11.0, 5.7, 4, 1000, 1000, 0, 0, 3, 10, true, false, Optional.empty<Any>())
    )

    //Lategame Materials
    val WITHERBONE = register(
        Identifier("soulforged", "witherbone"),
        Material(7.0, 4.5, 3, 150, 980, 0, 0, 1, 1, true, true, Optional.empty<Any>())
    )
    val ENDICITE = register(
        Identifier("soulforged", "endicite"),
        Material(4.0, 3.1, 3, 5, 150, 0, 0, 3, 7, true, false, Optional.empty<Any>())
    )
    val MONSTER_LEATHER = register(
        Identifier("soulforged", "monster_leather"),
        Material(0.8, 0.0, 2, 200, 800, 0, 30, 0, 0, false, true, Optional.empty<Any>())
    )
    val SCALES = register(
        Identifier("soulforged", "scales"),
        Material(6.5, 0.0, 2, 400, 690, 15, 0, 0, 0, false, true, Optional.empty<Any>())
    )

    // Corundum
    val SAPPHIRE = register(
        Identifier("soulforged", "sapphire"),
        Material(8.0, 8.0, 4, 390, 680, 0, 0, 3, 7, true, false, Optional.of(Classifiers.SAPPHIRE))
    )
    val RUBY = register(
        Identifier("soulforged", "ruby"),
        Material(8.0, 8.0, 4, 390, 680, 0, 0, 3, 7, true, false, Optional.of(Classifiers.RUBY))
    )
    val LOTUS_GEM = register(
        Identifier("soulforged", "lotus_gem"),
        Material(8.0, 8.0, 4, 390, 680, 0, 0, 3, 7, true, false, Optional.of(Classifiers.LOTUS))
    )
    val STAR_GEM = register(
        Identifier("soulforged", "star_gem"),
        Material(8.0, 8.0, 4, 390, 680, 0, 0, 3, 7, true, false, Optional.of(Classifiers.STAR))
    )

    // Lategame Metals
    val OSMIUM = register(
        Identifier("soulforged", "osmium"),
        Material(5.0, 9.0, 4, 1400, 1100, 4, 0, 3, 7, true, true, Optional.empty<Any>())
    )
    val NETHERITE = register(
        Identifier("soulforged", "netherite"),
        Material(9.5, 9.0, 3, 630, 1500, 5, 0, 4, 9, true, true, Optional.empty<Any>())
    )
    val ALUMINIUM = register(
        Identifier("soulforged", "aluminium"),
        Material(5.5, 6.8, 2, 320, 400, 3, 0, 1, 5, true, true, Optional.empty<Any>())
    )
    val TITANIUM = register(
        Identifier("soulforged", "titanium"),
        Material(7.7, 7.5, 4, 450, 900, 4, 0, 4, 11, true, true, Optional.empty<Any>())
    )

    // Endgame Alloys
    val VOID_METAL = register(
        Identifier("soulforged", "void_metal"),
        Material(8.9, 9.0, 4, 90, 1000, 4, 0, 4, 9, true, true, Optional.empty<Any>())
    )
    val NETHERIUM = register(
        Identifier("soulforged", "netherium"),
        Material(12.0, 11.0, 5, 500, 2000, 5, 0, 5, 12, true, true, Optional.empty<Any>())
    )
    val NEUTRONIUM = register(
        Identifier("soulforged", "neutronium"),
        Material(13.0, 13.0, 5, 2000, 1500, 5, 0, 5, 14, true, true, Optional.empty<Any>())
    )
    val DIVINIUM = register(
        Identifier("soulforged", "divinium"),
        Material(7.0, 4.0, 5, 660, 900, 5, 0, 5, 12, true, true, Optional.empty<Any>())
    )
    val STAR_METAL = register(
        Identifier("soulforged", "star_metal"),
        Material(9.0, 7.0, 4, 590, 1300, 4, 0, 4, 10, true, true, Optional.empty<Any>())
    )
    val MERCURIUM = register(
        Identifier("soulforged", "mercurium"),
        Material(6.9 /*nice*/, 6.0, 3, 690, 800, 4, 0, 4, 16, true, true, Optional.empty<Any>())
    )
    val ETERNICITE = register(
        Identifier("soulforged", "eternicite"),
        Material(8.5, 6.0, 5, 650, 3000, 0, 0, 4, 8, true, false, Optional.empty<Any>())
    )

    private fun register(id: Identifier, mat: Material): Material {
        Soulforged.LOGGER.debug("Try register: $id")
        return Registry.register(MATERIAL_REGISTRY, id, mat)
    }
}