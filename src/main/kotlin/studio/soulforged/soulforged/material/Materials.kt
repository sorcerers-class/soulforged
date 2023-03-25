package studio.soulforged.soulforged.material

import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.util.RegistryUtil

@Suppress("unused")
object Materials {
    val DEFAULT = Material(Identifier("soulforged:none"), "missingno", 0.0, 0.0, 0, 0, 0, 0, 0, 0, 0, false, false, null, 0, 0, transform = {return@Material true})
    val MATERIAL_REGISTRY: Registry<Material> = RegistryUtil.createRegistry("soulforged:materials", DEFAULT)
    // Earlygame Materials
    val WOOD =
        register(Material(
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
            null,
            20,
            20,
            transform = {Material.color(it, 0xff56481a)}
        ))
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
            null,
            10,
            5,
            transform = {return@Material true}
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
            null,
            25,
            10,
            transform = {Material.color(it, 0xffaa6c1b)}
        )
    )
    val FLINT = register(
        Material(
            Identifier("soulforged", "flint"),
            "item.soulforged.tool.material.flint",
            4.9,
            4.5,
            0,
            660,
            300,
            0,
            0,
            1,
            3,
            true,
            false,
            null,
            10,
            10,
            transform = {Material.color(it, 0xff474747)}
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
            null,
            25,
            25,
            transform = {Material.color(it, 0xffeae9de)}
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
            null,
            25,
            10,
            transform = {Material.color(it, 0xffe5e5e5)}
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
            null,
            30,
            30,
            transform = {Material.color(it, 0xff2d8c1e)}
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
            null,
            20,
            40,
            transform = {Material.color(it, 0xffeaeaea)}
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
            null,
            10,
            15,
            transform = {Material.color(it, 0xffd1fccc)}
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
            null,
            30,
            25,
            transform = {Material.color(it, 0xffeae9e3)}
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
            null,
            30,
            40,
            transform = {Material.color(it, 0xfffc800c)}
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
            null,
            100,
            100,
            transform = {Material.color(it, 0xfff9e71d)}
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
            null,
            45,
            30,
            transform = {Material.color(it, 0xffc9e0e5)}
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
            null,
            4,
            4,
            transform = {Material.color(it, 0xffc9e0e5)}
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
            null,
            10,
            15,
            transform = {Material.color(it, 0xff626263)}
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
            null,
            30,
            40,
            transform = {Material.color(it, 0xee8014fc)}
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
            null,
            35,
            20,
            transform = {Material.color(it, 0xee32fc05)}
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
            null,
            35,
            45,
            transform = {Material.color(it, 0xee05fcfc)}
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
            null,
            15,
            10,
            transform = {Material.color(it, 0xff1c0038)}
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
            null,
            0,
            0,
            transform = {Material.color(it, 0xff190000)}
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
            null,
            35,
            30,
            transform = {Material.color(it, 0xccd874fc)}
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
            null,
            26,
            10,
            transform = {Material.color(it, 0xffa8410a)}
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
            null,
            10,
            25,
            transform = {Material.color(it, 0xff255136)}
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
            Classifiers.SAPPHIRE,
            15,
            15,
            transform = {Material.color(it, 0xdd0117bc)}
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
            Classifiers.RUBY,
            15,
            15,
            transform = {Material.color(it, 0xddbc0101)}
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
            Classifiers.LOTUS,
            15,
            15,
            transform = {Material.color(it, 0xddf4a790)}
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
            Classifiers.STAR,
            15,
            15,
            transform = {Material.color(it, 0xddf4a790)}
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
            null,
            30,
            20,
            transform = {Material.color(it, 0xff90bbf4)}
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
            null,
            40,
            40,
            transform = {Material.color(it, 0xff2d2727)}
        )
    )
    val ALUMINUM = register(
        Material(
            Identifier("soulforged", "aluminum"),
            "item.soulforged.tool.material.aluminum",
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
            null,
            32,
            12,
            transform = {Material.color(it, 0xffe6f1f4)}
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
            null,
            35,
            15,
            transform = {Material.color(it, 0xffaeafaf)}
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
            null,
            5,
            10,
            transform = {Material.color(it, 0xff000000)}
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
            null,
            20,
            10,
            transform = {Material.color(it, 0xff605344)}
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
            null,
            45,
            24,
            transform = {Material.color(it, 0xffffff00)}
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
            null,
            55,
            45,
            transform = {Material.color(it, 0xffffffff)}
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
            null,
            30,
            60,
            transform = {Material.color(it, 0xffff7f00)}
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
            null,
            20,
            30,
            transform = {Material.color(it, 0xff757575)}
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
            null,
            40,
            35,
            transform = {Material.color(it, 0xff7d50d8)}
        )
    )

    private fun register(mat: Material): Material {
        Soulforged.LOGGER.debug("Try register: $mat.id")
        return Registry.register(MATERIAL_REGISTRY, mat.id, mat)
    }
    fun init() {
        register(DEFAULT)
    }
}


