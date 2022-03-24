package studio.soulforged.soulforged.item.tool

import net.minecraft.nbt.NbtCompound
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.part.PartPosition
import studio.soulforged.soulforged.item.tool.part.ToolPartInst
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI

class ToolType(
    val defaultAttack: AttackProperties,
    val hcAttack: AttackProperties?,
    val dcAttack: AttackProperties?,
    val miningSpeedProcessor: Any?,
    val rightClickEventProcessor: Any?
)
class ToolInst(val head: ToolPartInst, val binding: ToolPartInst, val handle: ToolPartInst) {
    /**
     * Get the base attack damage, without any crits.
     * @param ap The attack properties to calculate the attack with.
     * @return The total base attack damage in HP.
     */
    fun baseAttackDamage(ap: AttackProperties): Double {
        val type = this.head.type

        CombatDebuggerClientUI.critType = ap.type
        val totalPiercingDamage = rawPiercingDamage() * ap.piercingDamage
        val totalBluntDamage = rawBluntDamage() * ap.bluntDamage * 0.8
        return (totalBluntDamage + totalPiercingDamage)
    }

    /**
     * Get the raw piercing damage (not multiplied by the piercing multiplier).
     * @return The piercing damage, not multiplied by the piercing multiplier.
     */
    fun rawPiercingDamage(): Double {
        return (this.head.mat.edgeholding + this.head.mat.hardness * 0.75) / 2
    }

    /**
     * Get the raw blunt damage (not multiplied by blunt damage multiplier).
     * @return The blunt damage, not multiplied by the blunt damage multiplier and not multiplied by the blunt damage constant.
     */
    fun rawBluntDamage(): Double {
        return (effectiveWeight() / 100 + this.head.mat.hardness * 0.25)
    }

    /**
     * Get the effective weight of the tool.
     * @return The effective weight.
     */
    fun effectiveWeight(): Double {
        val headWeight: Double = this.head.part.weight * this.head.mat.density
        val bindingWeight: Double = this.binding.part.weight * this.binding.mat.density
        val handleWeight: Double = this.handle.part.weight * this.handle.mat.density
        return headWeight + bindingWeight + 0.25 * handleWeight
    }

    /**
     * Get an instance of attack properties, based on the number of click packets.
     * @param attack Number of attacks.
     * @see studio.soulforged.soulforged.item.tool.combat.AttackHandler
     * @return The attack properties, if it can be found.
     */
    fun attackProperties(attack: Int): AttackProperties? {
        return if(attack == 1) {
            this.head.type.hcAttack
        } else if(attack == 2 || attack == 0) {
            this.head.type.defaultAttack
        } else {
            this.head.type.dcAttack
        }
    }
    companion object {

        /**
         * Get an instance of a tool from an item's NBT
         * @param nbt The NBT of a tool item.
         * @return The instance of a tool, if one can be made from the given NBT.
         * @author mae
         */
        fun fromNbt(nbt: NbtCompound): ToolInst? {
            val head = ToolPartInst.fromNbt(PartPosition.HEAD, nbt)
            val binding = ToolPartInst.fromNbt(PartPosition.BINDING, nbt)
            val handle = ToolPartInst.fromNbt(PartPosition.HANDLE, nbt)
            return if (head != null && binding != null && handle != null) {
                ToolInst(head, binding, handle)
            } else {
                return null
            }
        }
    }
}