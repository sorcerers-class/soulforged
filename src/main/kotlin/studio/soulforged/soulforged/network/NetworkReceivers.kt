package studio.soulforged.soulforged.network

import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import org.quiltmc.qsl.networking.api.PacketSender
import org.quiltmc.qsl.networking.api.ServerPlayNetworking

object NetworkReceivers {
    fun register() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkIdentifiers.MOUSE_PACKET) { _: MinecraftServer?, client: ServerPlayerEntity?, _: ServerPlayNetworkHandler?, buf: PacketByteBuf?, _: PacketSender? ->

        }
    }
}