package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.entity.player.PlayerEntity
import kotlin.math.abs

enum class CritDirections(val multiplier: Float) {
    FORWARD(1.2f), SIDE(1.3f), DOWN(1.4f);

    companion object {
        fun getCritDirection(attacker: PlayerEntity): CritDirections? {
            val velocity = attacker.velocity.rotateY(Math.toRadians((attacker.yaw % 360.0f).toDouble()).toFloat())
                .multiply(-1.0, 1.0, 1.0)
            return if (abs(velocity.x) > 0.01) {
                SIDE
            } else if (attacker.fallDistance > 0 || velocity.y > 0) {
                DOWN
            } else if (velocity.z > 0.01) {
                FORWARD
            } else null
        }
        operator fun get(readInt: Int): CritDirections? {
            return when(readInt) {
                0 -> FORWARD
                1 -> SIDE
                2 -> DOWN
                else -> null
            }
        }
    }

}