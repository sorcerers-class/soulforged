package studio.soulforged.soulforgedcombatdebugger

import net.fabricmc.api.EnvType
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW
import studio.soulforged.soulforgedcombatdebugger.gui.ImGuiRenderer

@Environment(EnvType.CLIENT)
class CombatDebuggerClient : ClientModInitializer {
    var keyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding("key.soulforgedcombatdebugger.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "")
    )
    override fun onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register {
            while(keyBinding.wasPressed()) {
                ImGuiRenderer.SCD_ENABLED = !ImGuiRenderer.SCD_ENABLED
            }
        }
        ImGuiRenderer() // Create the ImGUI renderer instance.
    }
}