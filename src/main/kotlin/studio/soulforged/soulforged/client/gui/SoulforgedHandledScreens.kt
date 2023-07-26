package studio.soulforged.soulforged.client.gui

import net.minecraft.client.gui.screen.ingame.HandledScreens
import org.quiltmc.loader.api.minecraft.ClientOnly
import studio.soulforged.soulforged.block.multiblock.DeepslateForgeBunkerScreen
import studio.soulforged.soulforged.block.multiblock.DeepslateForgeControllerScreen

@ClientOnly
object SoulforgedHandledScreens {
    fun init() {
        HandledScreens.register(SoulforgedScreenHandlerTypes.DEEPSLATE_FORGE_BUNKER) { handler, playerInventory, _ ->
            DeepslateForgeBunkerScreen(handler, playerInventory)
        }
        HandledScreens.register(SoulforgedScreenHandlerTypes.DEEPSLATE_FORGE_CONTROLLER) { handler, playerInventory, _ ->
            DeepslateForgeControllerScreen(handler, playerInventory)
        }
    }
}