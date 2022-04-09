package studio.soulforged.soulforged.item.tool

import net.minecraft.nbt.NbtCompound
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.part.PartPosition
import studio.soulforged.soulforged.item.tool.part.ToolPartInst
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI

/**
 * Represents a generic tool type.
 * @author Lilly Rosaline
 */
class ToolType(
    val defaultAttack: AttackProperties,
    val hcAttack: AttackProperties?,
    val dcAttack: AttackProperties?,
    val miningSpeedProcessor: Any?,
    val rightClickEventProcessor: Any?
)

