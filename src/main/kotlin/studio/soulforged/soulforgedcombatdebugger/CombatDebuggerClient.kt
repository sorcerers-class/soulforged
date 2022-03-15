package studio.soulforged.soulforgedcombatdebugger

import net.fabricmc.api.EnvType
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.Environment
import studio.soulforged.soulforgedcombatdebugger.gui.ImGuiRenderer

@Environment(EnvType.CLIENT)
class CombatDebuggerClient : ClientModInitializer {
    override fun onInitializeClient() {
        ImGuiRenderer() // Create the ImGUI renderer instance.
    }
}