package studio.soulforged.soulforged.item.tool

import studio.soulforged.soulforged.item.tool.combat.AttackProperties

class ToolType(
    val defaultAttack: AttackProperties,
    val hcAttack: AttackProperties?,
    val dcAttack: AttackProperties?,
    val miningSpeedProcessor: Any?,
    val rightClickEventProcessor: Any?
)