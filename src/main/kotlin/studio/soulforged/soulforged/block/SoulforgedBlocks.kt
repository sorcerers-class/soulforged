package studio.soulforged.soulforged.block

import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.registry.Registries
import org.quiltmc.qkl.library.registry.*
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.Soulforged
import java.util.*

@Suppress("unused")
object SoulforgedBlocks {
    // Crafting-related stuff. TODO
    val WORKSTATION = WorkstationBlock(QuiltBlockSettings.of(Material.WOOD))

    val DEEPSLATE_FORGE_CONTROLLER = DeepslateForgeBlocks.DeepslateForgeController()
    val DEEPSLATE_FORGE_BURNER = DeepslateForgeBlocks.DeepslateForgeBurner()
    val DEEPSLATE_FORGE_BUNKER = DeepslateForgeBlocks.DeepslateForgeBunker()
    // Ores
    val LEAD_ORE = Block(QuiltBlockSettings.of(Material.STONE))
    val CINNABAR_ORE = Block(QuiltBlockSettings.of(Material.STONE))
    val OSMIUM_ORE = Block(QuiltBlockSettings.of(Material.STONE))
    val CORUNDUM_ORE = Block(QuiltBlockSettings.of(Material.AMETHYST))
    val ALUMINUM_ORE = Block(QuiltBlockSettings.of(Material.STONE))
    val END_ALUMINUM_ORE = Block(QuiltBlockSettings.of(Material.STONE))
    val TITANIUM_ORE = Block(QuiltBlockSettings.of(Material.STONE))
    val END_TITANIUM_ORE = Block(QuiltBlockSettings.of(Material.STONE))
    val ENDICITE_ORE = Block(QuiltBlockSettings.of(Material.STONE))
    internal fun init() {
        Registries.BLOCK(Soulforged.NAME) {
            WORKSTATION withId "workstation"
            DEEPSLATE_FORGE_CONTROLLER withId "deepslate_forge_controller"
            DEEPSLATE_FORGE_BURNER withId "deepslate_forge_burner"
            DEEPSLATE_FORGE_BUNKER withId "deepslate_forge_bunker"
            LEAD_ORE withId "lead_ore"
            CINNABAR_ORE withId "cinnabar_ore"
            OSMIUM_ORE withId "osmium_ore"
            CORUNDUM_ORE withId "corundum_ore"
            ALUMINUM_ORE withId "aluminum_ore"
            END_ALUMINUM_ORE withId "end_aluminum_ore"
            TITANIUM_ORE withId "titanium_ore"
            END_TITANIUM_ORE withId "end_titanium_ore"
            ENDICITE_ORE withId "endicite_ore"
        }
    }
}