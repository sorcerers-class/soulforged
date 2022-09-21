package studio.soulforged.soulforged.item.tool

import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.tool.combat.AttackHandler
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.part.ToolPart

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
    val miningSpeedProcessor: MiningSpeedProcessor,
    val rightClickEventProcessor: RightClickEventProcessor,
    val attackHandler: AttackHandler,
    val parts: Array<ToolPart>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToolType

        if (id != other.id) return false
        if (name != other.name) return false
        if (defaultAttack != other.defaultAttack) return false
        if (hcAttack != other.hcAttack) return false
        if (dcAttack != other.dcAttack) return false
        if (miningSpeedProcessor != other.miningSpeedProcessor) return false
        if (rightClickEventProcessor != other.rightClickEventProcessor) return false
        if (!parts.contentEquals(other.parts)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + defaultAttack.hashCode()
        result = 31 * result + (hcAttack?.hashCode() ?: 0)
        result = 31 * result + (dcAttack?.hashCode() ?: 0)
        result = 31 * result + miningSpeedProcessor.hashCode()
        result = 31 * result + rightClickEventProcessor.hashCode()
        result = 31 * result + parts.contentHashCode()
        return result
    }
}