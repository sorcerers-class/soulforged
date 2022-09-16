package studio.soulforged.soulforged.util

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import studio.soulforged.soulforged.item.tool.ToolInst

fun interface SpecialEffectProcessor {
    fun effect(tool: ToolInst, attacker: PlayerEntity, target: Entity)
}