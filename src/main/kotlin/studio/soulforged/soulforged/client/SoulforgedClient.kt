package studio.soulforged.soulforged.client

import com.mojang.blaze3d.platform.InputUtil
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.minecraft.client.option.KeyBind
import org.lwjgl.glfw.GLFW
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.client.gui.ImGuiRenderer

@Environment(EnvType.CLIENT)
class SoulforgedClient : ClientModInitializer {
    private var keyBinding: KeyBind = KeyBindingHelper.registerKeyBinding(
        KeyBind("key.soulforged.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "soulforged.name")
    )
    override fun onInitializeClient(mod: ModContainer?) {
        Soulforged.LOGGER.info("Initializing Soulforged Client!")
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { SoulforgedModelProvider() }

        ClientTickEvents.END.register {
            while(keyBinding.wasPressed()) {
                ImGuiRenderer.SCD_ENABLED = !ImGuiRenderer.SCD_ENABLED
            }
        }
        ImGuiRenderer() // Create the ImGUI renderer instance.
    }
}