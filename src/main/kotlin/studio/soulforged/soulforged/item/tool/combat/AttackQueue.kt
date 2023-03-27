package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.tool.ToolInst
import java.util.concurrent.ConcurrentLinkedQueue

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
    data class Entry(val attacker: PlayerEntity, val target: Entity, var timer: UInt, var clicks: Int) {

        fun finish() {
            val nbt: NbtCompound
            if(attacker.mainHandStack.nbt != null) {
               nbt = attacker.mainHandStack.nbt!!
            } else {
                AttackHandler.sendAttack(AttackHandler.Handler(attacker, target, AttackTypes.getAttackType(clicks), AttackHandlers.ATTACK_HANDLERS_REGISTRY.getId(AttackHandlers.DEFAULT) ?: Identifier("soulforged:default")))
                return
            }
            AttackHandler.sendAttack(AttackHandler.Handler(attacker, target, AttackTypes.getAttackType(clicks), AttackHandlers.ATTACK_HANDLERS_REGISTRY.getId(ToolInst.ToolInstSerializer.deserialize(nbt).type.attackHandler) ?: Identifier("soulforged:default")))
        }
    }
    fun add(target: Entity) {
        queue.add(Entry(this.attacker, target, 4u, 0))
    }
}
