package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.entity.player.PlayerEntity
import kotlin.math.abs

enum class CritTypes {
    FORWARD, SIDE, DOWN;
    companion object {
        fun getCritType(attacker: PlayerEntity): CritTypes? {
            val velocity = attacker.velocity.rotateY(Math.toRadians((attacker.yaw % 360.0f).toDouble()).toFloat())
                .multiply(-1.0, 1.0, 1.0)
            if (abs(velocity.z) > 0.1) {
                return CritTypes.FORWARD
            }
            if (abs(velocity.y) > 0.1) {
                return CritTypes.DOWN
            }
            if (abs(velocity.x) > 0.1) {
                return CritTypes.SIDE
            }
            return null
        }
    }
}