package studio.soulforged.soulforged.network

import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import org.quiltmc.qsl.networking.api.PacketSender
import org.quiltmc.qsl.networking.api.ServerPlayNetworking
import studio.soulforged.soulforged.item.tool.combat.AttackHandler
import studio.soulforged.soulforged.item.tool.combat.AttackHandlers

object NetworkReceivers {
    fun register() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkIdentifiers.ATTACK_PACKET) { _: MinecraftServer, client: ServerPlayerEntity, _: ServerPlayNetworkHandler, buf: PacketByteBuf, _: PacketSender ->
            val handler = AttackHandler.deserialize(buf, client)
            val ah = AttackHandlers.ATTACK_HANDLERS_REGISTRY.get(handler.handler) ?: AttackHandlers.DEFAULT
            ah.attack(handler.attacker, handler.target, handler.attackType, handler.critDirection)
        }
    }
}