package studio.soulforged.soulforged.world

import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.PlacedFeature
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors
import java.util.function.Predicate

@Suppress("unused")
object SoulforgedOres {
    val ORE_ALUMINUM = register("ore_aluminum", BiomeSelectors.foundInOverworld())
    val ORE_ALUMINUM_END = register("ore_aluminum_end", BiomeSelectors.foundInTheEnd())
    val ORE_CINNABAR = register("ore_cinnabar", BiomeSelectors.foundInOverworld())
    val ORE_ENDICITE = register("ore_endicite", BiomeSelectors.foundInTheEnd())
    val ORE_LEAD = register("ore_lead", BiomeSelectors.foundInOverworld())
    val ORE_OSMIUM = register("ore_osmium", BiomeSelectors.foundInTheNether())
    val ORE_PADPARADSCHA = register("ore_padparadscha", BiomeSelectors.foundInTheNether())
    val ORE_RUBY = register("ore_ruby", BiomeSelectors.foundInTheNether())
    val ORE_SAPPHIRE = register("ore_sapphire", BiomeSelectors.foundInTheNether())
    val ORE_STAR_GEM = register("ore_star_gem", BiomeSelectors.foundInTheNether())
    val ORE_TITANIUM = register("ore_titanium", BiomeSelectors.foundInTheNether())
    val ORE_TITANIUM_END = register("ore_titanium_end", BiomeSelectors.foundInTheEnd())
    fun register(id: String, sel: Predicate<BiomeSelectionContext>): RegistryKey<PlacedFeature> {
        val key = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier("soulforged", id))
        BiomeModifications.addFeature(sel, GenerationStep.Feature.UNDERGROUND_ORES, key)
        return key
    }
    fun init() {}
}