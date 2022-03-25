package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.entity.Entity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.entity.damage.DamageSource
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI
import net.minecraft.entity.LivingEntity
import java.util.concurrent.ConcurrentHashMap
import net.minecraft.network.PacketByteBuf
import java.util.*

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
            target!!.damage(
                DamageSource.player(client), ToolCalculations.calcAttackDamage(
                    client.mainHandStack, packets.size, client, target, attackCooldown
                ).toFloat()
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