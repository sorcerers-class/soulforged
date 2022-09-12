package studio.soulforged.soulforged.material

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import studio.soulforged.soulforged.Soulforged

@Suppress("unused")
object Materials {

    private val MATERIAL_REGISTRY_KEY: RegistryKey<Registry<Material>> =
        RegistryKey.ofRegistry(Identifier("soulforged", "materials"))
    val MATERIAL_REGISTRY: Registry<Material> =
        Registry.registerSimple(MATERIAL_REGISTRY_KEY) { WOOD } // This is possibly the worst way of doing this but i don't care
    // Earlygame Materials
    val WOOD = register(
        Material(
            Identifier("soulforged", "wood"),
            "item.soulforged.tool.material.wood",
            4.0,
            2.5,
            0,
            300,
            200,
            0,
            0,
            0,
            2,
            true,
            true,
            null
        )
    )
    val STONE = register(
        Material(
            Identifier("soulforged", "stone"),
            "item.soulforged.tool.material.stone",
            5.0,
            3.9,
            0,
            700,
            250,
            0,
            0,
            1,
            4,
            true,
            false,
            null
        )
    )
    val LEATHER = register(
        Material(
            Identifier("soulforged", "leather"),
            "item.soulforged.tool.material.leather",
            0.5,
            0.0,
            0,
            250,
            150,
            0,
            20,
            0,
            0,
            false,
            true,
            null
        )
    )
    val FLINT = register(
        Material(
            Identifier("soulforged", "flint"),
            "item.soulforged.tool.material.flint",
            4.9,
            4.5,
            0,
            0,
            660,
            300,
            0,
            1,
            3,
            true,
            false,
            null
        )
    )
    val BONE = register(
        Material(
            Identifier("soulforged", "bone"),
            "item.soulforged.tool.material.bone",
            5.0,
            3.8,
            0,
            200,
            100,
            0,
            0,
            0,
            1,
            true,
            true,
            null
        )
    )
    val WOOL = register(
        Material(
            Identifier("soulforged", "wool"),
            "item.soulforged.tool.material.wool",
            0.2,
            0.0,
            0,
            80,
            100,
            0,
            20,
            0,
            0,
            false,
            true,
            null
        )
    )
    val SCUTE = register(
        Material(
            Identifier("soulforged", "scute"),
            "item.soulforged.tool.material.scute",
            7.0,
            0.0,
            1,
            550,
            850,
            0,
            10,
            0,
            0,
            false,
            true,
            null
        )
    )
    val SPIDER_WEAVE = register(
        Material(
            Identifier("soulforged", "spider_weave"),
            "item.soulforged.tool.material.spider_weave",
            0.6,
            0.0,
            1,
            180,
            1100,
            0,
            12,
            0,
            0,
            false,
            true,
            null
        )
    )
    val SLIME_WEAVE = register(
        Material(
            Identifier("soulforged", "slime_weave"),
            "item.soulforged.tool.material.slime_weave",
            0.1,
            0.0,
            1,
            300,
            300,
            0,
            40,
            0,
            0,
            false,
            true,
            null
        )
    )

    // Earlygame Metals
    val IRON = register(
        Material(
            Identifier("soulforged", "iron"),
            "item.soulforged.tool.material.iron",
            7.0,
            5.8,
            1,
            600,
            600,
            3,
            0,
            2,
            6,
            true,
            true,
            null
        )
    )
    val COPPER = register(
        Material(
            Identifier("soulforged", "copper"),
            "item.soulforged.tool.material.copper",
            6.5,
            5.0,
            1,
            570,
            700,
            3,
            0,
            2,
            5,
            true,
            true,
            null
        )
    )
    val GOLD = register(
        Material(
            Identifier("soulforged", "gold"),
            "item.soulforged.tool.material.gold",
            2.5,
            2.0,
            2,
            1150,
            150,
            2,
            3,
            0,
            12,
            true,
            true,
            null
        )
    )
    val SILVER = register(
        Material(
            Identifier("soulforged", "silver"),
            "item.soulforged.tool.material.silver",
            5.0,
            5.0,
            2,
            800,
            670,
            3,
            0,
            1,
            10,
            true,
            true,
            null
        )
    )
    val LEAD = register(
        Material(
            Identifier("soulforged", "lead"),
            "item.soulforged.tool.material.lead",
            4.5,
            2.6,
            2,
            950,
            300,
            2,
            1,
            2,
            6,
            true,
            true,
            null
        )
    )
    val STEEL = register(
        Material(
            Identifier("soulforged", "steel"),
            "item.soulforged.tool.material.steel",
            7.5,
            7.0,
            3,
            590,
            850,
            4,
            0,
            3,
            7,
            true,
            true,
            null
        )
    )

    // Earthly Crystals
    val AMETHYST = register(
        Material(
            Identifier("soulforged", "amethyst"),
            "item.soulforged.tool.material.amethyst",
            9.0,
            12.0,
            3,
            400,
            200,
            0,
            0,
            2,
            5,
            true,
            false,
            null
        )
    )
    val EMERALD = register(
        Material(
            Identifier("soulforged", "emerald"),
            "item.soulforged.tool.material.emerald",
            6.5,
            7.0,
            3,
            440,
            770,
            0,
            0,
            1,
            4,
            true,
            false,
            null
        )
    )
    val DIAMOND = register(
        Material(
            Identifier("soulforged", "diamond"),
            "item.soulforged.tool.material.diamond",
            7.0,
            8.0,
            4,
            460,
            1300,
            0,
            0,
            3,
            8,
            true,
            false,
            null
        )
    )
    val OBSIDIAN = register(
        Material(
            Identifier("soulforged", "obsidian"),
            "item.soulforged.tool.material.obsidian",
            11.0,
            5.7,
            4,
            1000,
            1000,
            0,
            0,
            3,
            10,
            true,
            false,
            null
        )
    )

    //Lategame Materials
    val WITHERBONE = register(
        Material(
            Identifier("soulforged", "witherbone"),
            "item.soulforged.tool.material.witherbone",
            7.0,
            4.5,
            3,
            150,
            980,
            0,
            0,
            1,
            1,
            true,
            true,
            null
        )
    )
    val ENDICITE = register(
        Material(
            Identifier("soulforged", "endicite"),
            "item.soulforged.tool.material.endicite",
            4.0,
            3.1,
            3,
            5,
            150,
            0,
            0,
            3,
            7,
            true,
            false,
            null
        )
    )
    val MONSTER_LEATHER = register(
        Material(
            Identifier("soulforged", "monster_leather"),
            "item.soulforged.tool.material.monster_leather",
            0.8,
            0.0,
            2,
            200,
            800,
            0,
            30,
            0,
            0,
            false,
            true,
            null
        )
    )
    val SCALES = register(
        Material(
            Identifier("soulforged", "scales"),
            "item.soulforged.tool.material.scales",
            6.5,
            0.0,
            2,
            400,
            690,
            15,
            0,
            0,
            0,
            false,
            true,
            null
        )
    )

    // Corundum
    val SAPPHIRE = register(
        Material(
            Identifier("soulforged", "sapphire"),
            "item.soulforged.tool.material.sapphire",
            8.0,
            8.0,
            4,
            390,
            680,
            0,
            0,
            3,
            7,
            true,
            false,
            Classifiers.SAPPHIRE
        )
    )
    val RUBY = register(
        Material(
            Identifier("soulforged", "ruby"),
            "item.soulforged.tool.material.ruby",
            8.0,
            8.0,
            4,
            390,
            680,
            0,
            0,
            3,
            7,
            true,
            false,
            Classifiers.RUBY
        )
    )
    val LOTUS_GEM = register(
        Material(
            Identifier("soulforged", "lotus_gem"),
            "item.soulforged.tool.material.lotus_gem",
            8.0,
            8.0,
            4,
            390,
            680,
            0,
            0,
            3,
            7,
            true,
            false,
            Classifiers.LOTUS
        )
    )
    val STAR_GEM = register(
        Material(
            Identifier("soulforged", "star_gem"),
            "item.soulforged.tool.material.star_gem",
            8.0,
            8.0,
            4,
            390,
            680,
            0,
            0,
            3,
            7,
            true,
            false,
            Classifiers.STAR
        )
    )

    // Lategame Metals
    val OSMIUM = register(
        Material(
            Identifier("soulforged", "osmium"),
            "item.soulforged.tool.material.osmium",
            5.0,
            9.0,
            4,
            1400,
            1100,
            4,
            0,
            3,
            7,
            true,
            true,
            null
        )
    )
    val NETHERITE = register(
        Material(
            Identifier("soulforged", "netherite"),
            "item.soulforged.tool.material.netherite",
            9.5,
            9.0,
            3,
            630,
            1500,
            5,
            0,
            4,
            9,
            true,
            true,
            null
        )
    )
    val ALUMINIUM = register(
        Material(
            Identifier("soulforged", "aluminium"),
            "item.soulforged.tool.material.aluminium",
            5.5,
            6.8,
            2,
            320,
            400,
            3,
            0,
            1,
            5,
            true,
            true,
            null
        )
    )
    val TITANIUM = register(
        Material(
            Identifier("soulforged", "titanium"),
            "item.soulforged.tool.material.titanium",
            7.7,
            7.5,
            4,
            450,
            900,
            4,
            0,
            4,
            11,
            true,
            true,
            null
        )
    )

    // Endgame Alloys
    val VOID_METAL = register(
        Material(
            Identifier("soulforged", "void_metal"),
            "item.soulforged.tool.material.void_metal",
            8.9,
            9.0,
            4,
            90,
            1000,
            4,
            0,
            4,
            9,
            true,
            true,
            null
        )
    )
    val NETHERIUM = register(
        Material(
            Identifier("soulforged", "netherium"),
            "item.soulforged.tool.material.netherium",
            12.0,
            11.0,
            5,
            500,
            2000,
            5,
            0,
            5,
            12,
            true,
            true,
            null
        )
    )
    val NEUTRONIUM = register(
        Material(
            Identifier("soulforged", "neutronium"),
            "item.soulforged.tool.material.neutronium",
            13.0,
            13.0,
            5,
            2000,
            1500,
            5,
            0,
            5,
            14,
            true,
            true,
            null
        )
    )
    val DIVINIUM = register(
        Material(
            Identifier("soulforged", "divinium"),
            "item.soulforged.tool.material.divinium",
            7.0,
            4.0,
            5,
            660,
            900,
            5,
            0,
            5,
            12,
            true,
            true,
            null
        )
    )
    val STAR_METAL = register(
        Material(
            Identifier("soulforged", "star_metal"),
            "item.soulforged.tool.material.star_metal",
            9.0,
            7.0,
            4,
            590,
            1300,
            4,
            0,
            4,
            10,
            true,
            true,
            null
        )
    )
    val MERCURIUM = register(
        Material(
            Identifier("soulforged", "mercurium"),
            "item.soulforged.tool.material.mercurium",
            6.9 /*nice*/,
            6.0,
            3,
            690,
            800,
            4,
            0,
            4,
            16,
            true,
            true,
            null
        )
    )
    val ETERNICITE = register(
        Material(
            Identifier("soulforged", "eternicite"),
            "item.soulforged.tool.material.eternicite",
            8.5,
            6.0,
            5,
            650,
            3000,
            0,
            0,
            4,
            8,
            true,
            false,
            null
        )
    )

    private fun register(mat: Material): Material {
        Soulforged.LOGGER.debug("Try register: $mat.id")
        return Registry.register(MATERIAL_REGISTRY, mat.id, mat)
    }
}