package studio.soulforged.soulforged.client

import com.mojang.blaze3d.platform.InputUtil
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.minecraft.client.option.KeyBind
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.lwjgl.glfw.GLFW
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents
import studio.soulforged.soulforged.client.gui.ImGuiRenderer

@ClientOnly
class SoulforgedClient : ClientModInitializer {
    private var keyBinding: KeyBind = KeyBindingHelper.registerKeyBinding(
        KeyBind("key.soulforged.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "soulforged.name")
    )
    override fun onInitializeClient(mod: ModContainer?) {
        LOGGER.info("Initializing Soulforged Client!")
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { SoulforgedModelProvider() }

        ClientTickEvents.END.register {
            while(keyBinding.wasPressed()) {
                ImGuiRenderer.SCD_ENABLED = !ImGuiRenderer.SCD_ENABLED
            }
        }
    }
    companion object {
        val LOGGER: Logger = LogManager.getLogger("SoulforgedClient")
    }
}