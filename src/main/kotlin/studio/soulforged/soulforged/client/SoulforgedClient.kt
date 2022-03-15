package studio.soulforged.soulforged.client

import net.fabricmc.api.EnvType
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry

@Environment(EnvType.CLIENT)
class SoulforgedClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { SoulforgedModelProvider() }
    }
}