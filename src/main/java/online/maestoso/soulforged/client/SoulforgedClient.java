package online.maestoso.soulforged.client;

import net.fabricmc.api.ClientModInitializer;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class SoulforgedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SoulforgedModelPredicates.register();
    }
}
