package studio.soulforged.soulforged.block

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import org.quiltmc.qkl.library.registry.invoke
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.block.multiblock.DeepslateForgeBunker
import studio.soulforged.soulforged.block.multiblock.DeepslateForgeBurner
import studio.soulforged.soulforged.block.multiblock.DeepslateForgeController
import studio.soulforged.soulforged.block.multiblock.DeepslateForgeDeepslateBrickBlock

@Suppress("unused")
object SoulforgedBlocks {
    // Crafting-related stuff. TODO
    val WORKSTATION = WorkstationBlock(QuiltBlockSettings.create())

    // Ores
    val LEAD_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    val CINNABAR_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    val OSMIUM_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    val CORUNDUM_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    val ALUMINUM_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    val END_ALUMINUM_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    val TITANIUM_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    val END_TITANIUM_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    val ENDICITE_ORE = Block(QuiltBlockSettings.copyOf(Blocks.IRON_ORE))
    internal fun init() {
        Registries.BLOCK(Soulforged.NAME) {
            WORKSTATION withId "workstation"
            DeepslateForgeController withId "deepslate_forge_controller"
            DeepslateForgeBurner withId "deepslate_forge_burner"
            DeepslateForgeBunker withId "deepslate_forge_bunker"
            DeepslateForgeDeepslateBrickBlock withId "deepslate_forge_bricks"
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