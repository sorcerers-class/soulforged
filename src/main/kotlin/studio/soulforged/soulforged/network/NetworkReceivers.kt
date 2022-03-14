package studio.soulforged.soulforged.network

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import studio.soulforged.soulforged.network.NetworkIdentifiers
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.network.PacketByteBuf
import net.fabricmc.fabric.api.networking.v1.PacketSender
import studio.soulforged.soulforged.item.tool.combat.AttackHandler
import studio.soulforged.soulforged.item.tool.combat.AttackHandler.Companion.addOrModifyAttackHandler

object NetworkReceivers {
    fun register() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkIdentifiers.MOUSE_PACKET) { server: MinecraftServer?, client: ServerPlayerEntity?, handler: ServerPlayNetworkHandler?, buf: PacketByteBuf?, responseSender: PacketSender? ->
            addOrModifyAttackHandler(
                client!!, buf!!
            )
        }
    }
}