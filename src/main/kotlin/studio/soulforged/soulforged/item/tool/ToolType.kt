package studio.soulforged.soulforged.item.tool

import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.tool.combat.AttackProperties

/**
 * Represents a generic tool type.
 * @author Lilly Rosaline
 */
data class ToolType(
    val id: Identifier,
    val name: String,
    val defaultAttack: AttackProperties,
    val hcAttack: AttackProperties?,
    val dcAttack: AttackProperties?,
    val miningSpeedProcessor: Any?,
    val rightClickEventProcessor: Any?
)

