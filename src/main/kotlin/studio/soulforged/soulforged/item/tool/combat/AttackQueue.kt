package studio.soulforged.soulforged.item.tool.combat
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity

class AttackQueue {
    private val queue: ArrayDeque<Entry> = ArrayDeque()

    fun tick() {
        for(entry in queue) {
            entry.timer -= 1/60f
            if(entry.timer <= 0f) {
                entry.finish()
            }
        }
    }
    data class Entry(val attacker: PlayerEntity, val target: Entity, var timer: Float) {
        fun finish() {

        }
    }
}
