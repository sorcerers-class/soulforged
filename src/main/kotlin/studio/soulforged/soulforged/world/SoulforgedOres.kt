package studio.soulforged.soulforged.world

import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.PlacedFeature
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors
import studio.soulforged.soulforged.Soulforged.id
import java.util.function.Predicate

@Suppress("unused")
object SoulforgedOres {
    val ORE_ALUMINUM = register("ore_aluminum", BiomeSelectors.foundInOverworld())
    val ORE_ALUMINUM_END = register("ore_aluminum_end", BiomeSelectors.foundInTheEnd())
    val ORE_CINNABAR = register("ore_cinnabar", BiomeSelectors.foundInOverworld())
    val ORE_ENDICITE = register("ore_endicite", BiomeSelectors.foundInTheEnd())
    val ORE_LEAD = register("ore_lead", BiomeSelectors.foundInOverworld())
    val ORE_OSMIUM = register("ore_osmium", BiomeSelectors.foundInTheNether())
    val ORE_CORUNDUM = register("ore_corundum", BiomeSelectors.foundInTheNether())
    val ORE_TITANIUM = register("ore_titanium", BiomeSelectors.foundInTheNether())
    val ORE_TITANIUM_END = register("ore_titanium_end", BiomeSelectors.foundInTheEnd())
    private fun register(id: String, sel: Predicate<BiomeSelectionContext>): RegistryKey<PlacedFeature> {
        val key = RegistryKey.of(RegistryKeys.PLACED_FEATURE, id.id())
        BiomeModifications.addFeature(sel, GenerationStep.Feature.UNDERGROUND_ORES, key)
        return key
    }
    internal fun init() {}
}