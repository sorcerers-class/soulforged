package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import studio.soulforged.soulforged.client.gui.CombatDebuggerClientUI
import studio.soulforged.soulforged.item.tool.ToolInstSerializer
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class AttackHandler(private val client: ServerPlayerEntity) {
    private var tickCounter = 0
    var finished = false
        private set
    private val attackCooldown: Float
    private var target: Entity? = null
    private val packets = Vector<Int>()

    init {
        addPacket(1)
        attackCooldown = client.getAttackCooldownProgress(0.0f)
    }

    fun tick() {
        tickCounter += 1
        if (tickCounter == 10) {
            onFinish()
            finished = true
        }
    }

    fun addPacket(action: Int) {
        packets.add(action)
    }

    private fun onFinish() {
        if (target != null) {
            val tool = ToolInstSerializer.deserialize(client.mainHandStack.nbt!!)
            target!!.damage(
                DamageSource.player(client), tool.baseAttackDamage(tool.attackProperties(packets.size)).toFloat()
            )
            CombatDebuggerClientUI.attackType = packets.size
            client.mainHandStack.postHit(target as LivingEntity?, client)
        }
    }

    fun setTarget(target: Entity?) {
        this.target = target
    }

    companion object {
        @JvmField
        var attackHandlers = ConcurrentHashMap<UUID, AttackHandler>()
        @JvmStatic
        fun addOrModifyAttackHandler(client: ServerPlayerEntity, buf: PacketByteBuf) {
            if (attackHandlers.containsKey(client.uuid) && !attackHandlers[client.uuid]!!.finished) {
                val attackHandler = attackHandlers[client.uuid]
                val button = buf.readInt()
                if (button == 0) {
                    val action = buf.readInt()
                    attackHandler!!.addPacket(action)
                }
            } else {
                val button = buf.readInt()
                if (button == 0) attackHandlers[client.uuid] = AttackHandler(client)
            }
        }
    }
}