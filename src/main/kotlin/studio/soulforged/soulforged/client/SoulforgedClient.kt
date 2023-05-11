package studio.soulforged.soulforged.client

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents
import studio.soulforged.soulforged.client.debugger.ImGuiRenderer
import studio.soulforged.soulforged.client.gui.SoulforgedHandledScreens

@ClientOnly
class SoulforgedClient : ClientModInitializer {

    override fun onInitializeClient(mod: ModContainer?) {
        LOGGER.info("Initializing Soulforged Client!")
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { SoulforgedModelProvider() }
        SoulforgedKeybinds.init()
        SoulforgedHandledScreens.init()
        ClientTickEvents.END.register {
            while(SoulforgedKeybinds.toggleCombatDebugger()) {
                ImGuiRenderer.SCD_ENABLED = !ImGuiRenderer.SCD_ENABLED
            }
        }
    }
    companion object {
        @JvmField
        val LOGGER: Logger = LogManager.getLogger("SoulforgedClient")
    }
}