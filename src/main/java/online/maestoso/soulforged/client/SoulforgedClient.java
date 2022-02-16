package online.maestoso.soulforged.client;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.MinecraftClient;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class SoulforgedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new SoulforgedModelProvider());
        MinecraftClient.getInstance().wireFrame = true;
    }
}
