package studio.soulforged.soulforged.client

import net.fabricmc.api.EnvType
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.resource.ResourceManager
import studio.soulforged.soulforged.client.SoulforgedModelProvider

@Environment(EnvType.CLIENT)
class SoulforgedClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { rm: ResourceManager? -> SoulforgedModelProvider() }
    }
}