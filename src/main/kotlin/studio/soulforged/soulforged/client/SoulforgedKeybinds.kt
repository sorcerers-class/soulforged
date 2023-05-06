package studio.soulforged.soulforged.client

import com.mojang.blaze3d.platform.InputUtil
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBind
import org.lwjgl.glfw.GLFW

object SoulforgedKeybinds {
    internal val TOGGLE_COMBAT_DEBUGGER: KeyBind = GLFW.GLFW_KEY_RIGHT_SHIFT.registerKeycode("key.soulforged.toggle_combat_debugger", "soulforged.name")
    fun toggleCombatDebugger() = TOGGLE_COMBAT_DEBUGGER.wasPressed()
    private fun Int.registerKeycode(key: String, category: String): KeyBind = KeyBindingHelper.registerKeyBinding(KeyBind(key, InputUtil.Type.KEYSYM, this, category))
    internal fun init() {

    }
}