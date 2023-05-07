package studio.soulforged.soulforged.client.attack

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import org.quiltmc.loader.api.minecraft.ClientOnly
import studio.soulforged.soulforged.item.tool.combat.AttackHandler
import studio.soulforged.soulforged.item.tool.combat.AttackHandlers
import studio.soulforged.soulforged.item.tool.combat.AttackTypes
import studio.soulforged.soulforged.item.tool.combat.CritDirections
import java.util.concurrent.ConcurrentLinkedQueue

@ClientOnly
class AttackQueue(private val attacker: PlayerEntity){
    private val queue: ConcurrentLinkedQueue<Entry> = ConcurrentLinkedQueue()
    fun tick() {
        for(entry in queue) {
            entry.timer--
            if(entry.timer == 0u) {
                entry.finish()
                queue.remove(entry)
            }
        }
    }
    fun addClickPacket() {
        for(entry in queue) {
            entry.clicks++
        }
    }
    data class Entry(val attacker: PlayerEntity, val target: Entity, var timer: UInt, var clicks: Int, val critDirection: CritDirections?, val timestamp: ULong) {

        fun finish() {
            val nbt: NbtCompound
            if(attacker.mainHandStack.nbt != null) {
               nbt = attacker.mainHandStack.nbt!!
            } else {
                AttackHandler.sendAttack(
                    AttackHandler.Handler(
                        attacker,
                        target,
                        AttackTypes.getAttackType(clicks),
                        AttackHandlers.ATTACK_HANDLERS_REGISTRY.getId(AttackHandlers.DEFAULT)
                            ?: Identifier("soulforged:default"),
                        critDirection
                    )
                )
                return
            }
            AttackHandler.sendAttack(
                AttackHandler.Handler(
                    attacker, target, AttackTypes.getAttackType(clicks),
                    Identifier("soulforged:with_crits"), critDirection
                )
            )
        }
    }
    fun add(target: Entity) {
        queue.add(Entry(this.attacker, target, 4u, 0, CritDirections.getCritDirection(attacker), System.currentTimeMillis().toULong()))
    }
    internal fun getQueue(): ConcurrentLinkedQueue<Entry> {
        return queue
    }
}
