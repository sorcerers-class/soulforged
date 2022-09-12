package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult

fun interface AttackHandler {
    fun attack(attacker: PlayerEntity, target: Entity, clicks: Int): ActionResult
}
