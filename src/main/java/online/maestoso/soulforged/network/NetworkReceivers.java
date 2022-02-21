package online.maestoso.soulforged.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import online.maestoso.soulforged.item.tool.combat.AttackHandler;

public class NetworkReceivers {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkIdentifiers.MOUSE_PACKET, AttackHandler::addOrModifyAttackHandler);
    }

}
