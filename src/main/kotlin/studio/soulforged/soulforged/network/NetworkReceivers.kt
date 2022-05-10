package studio.soulforged.soulforged.network

import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import studio.soulforged.soulforged.item.tool.combat.AttackHandler.Companion.addOrModifyAttackHandler

object NetworkReceivers {
    fun register() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkIdentifiers.MOUSE_PACKET) { _: MinecraftServer?, client: ServerPlayerEntity?, _: ServerPlayNetworkHandler?, buf: PacketByteBuf?, _: PacketSender? ->
            addOrModifyAttackHandler(
                client!!, buf!!
            )
        }
    }
}