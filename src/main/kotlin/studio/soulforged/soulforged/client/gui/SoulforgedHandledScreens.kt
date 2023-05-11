package studio.soulforged.soulforged.client.gui

import net.minecraft.client.gui.screen.ingame.HandledScreens

object SoulforgedHandledScreens {
    fun init() {
        HandledScreens.register(SoulforgedScreenHandlerTypes.DEEPSLATE_FORGE_BUNKER) { handler, playerInventory, _ ->
            DeepslateForgeBunkerScreen(handler, playerInventory)
        }
    }
}