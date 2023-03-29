package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import org.quiltmc.qsl.networking.api.PacketByteBufs
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking
import studio.soulforged.soulforged.network.NetworkIdentifiers

fun interface AttackHandler {
    data class Handler(val attacker: PlayerEntity?, val target: Entity?, val attackType: AttackTypes, val handler: Identifier, val critDirection: CritDirections?)
    fun attack(attacker: PlayerEntity?, target: Entity?, critType: AttackTypes, critDirection: CritDirections?): ActionResult
    companion object {
        fun serialize(handler: Handler): PacketByteBuf {
            val packet = PacketByteBufs.create()
            packet.writeInt(handler.attacker?.id ?: -1)
            packet.writeInt(handler.target?.id ?: -1)
            packet.writeInt(handler.attackType.ordinal)
            packet.writeIdentifier(handler.handler)
            packet.writeInt(handler.critDirection?.ordinal ?: -1)
            return packet
        }
        fun deserialize(packet: PacketByteBuf, player: ServerPlayerEntity): Handler {
            val attacker = player.world.getEntityById(packet.readInt()) as? PlayerEntity?
            val target = player.world.getEntityById(packet.readInt())
            val attackType = AttackTypes.values()[packet.readInt()]
            val handler = packet.readIdentifier()
            val critDirection = CritDirections[packet.readInt()]
            return Handler(attacker, target, attackType, handler, critDirection)
        }
        fun sendAttack(handler: Handler) {
            ClientPlayNetworking.send(NetworkIdentifiers.ATTACK_PACKET, serialize(handler))
        }
    }
}
