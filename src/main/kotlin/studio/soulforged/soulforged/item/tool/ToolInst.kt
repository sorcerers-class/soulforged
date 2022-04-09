package studio.soulforged.soulforged.item.tool

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.part.PartPosition
import studio.soulforged.soulforged.item.tool.part.ToolPartInst
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI

/**
 * Represents an instance of a specific tool.
 * @author Lilly Rosaline
 * @see ToolType
 * @see studio.soulforged.soulforged.item.tool.part.ToolPart
 * @see studio.soulforged.soulforged.material.Material
 */
class ToolInst(stack: ItemStack, head: ToolPartInst, binding: ToolPartInst, handle: ToolPartInst) {

    /**
     * Gets the durability of the tool to display.
     * @return The durability, normalized from 0 to 255.
     * @author Lilly Rosaline
     */
    fun durability(): UInt {
        val headDamage = (256 * head.durability.toFloat() / head.maxDurability.toFloat()).toUInt()
        val bindingDamage = (256 * binding.durability.toFloat() / head.maxDurability.toFloat()).toUInt()
        val handleDamage = (256 * binding.durability.toFloat() / head.maxDurability.toFloat()).toUInt()
        return 256u - handleDamage.coerceAtMost(bindingDamage).coerceAtMost(headDamage)
    }
    /**
     * Get the base attack speed without any modifiers.
     * @param ap The attack properties to get the attack speed from. This can probably be set to the default attack?
     * @return The attack speed in whatever unit the game uses for attack speed, I think it might be attacks/second
     * @author Lilly Rosaline
     */
    fun baseAttackSpeed(ap: AttackProperties): Double {
        return 1 / weight() / 800 / ap.speed
    }
    /**
     * Get the base attack damage, without any crits.
     * @param ap The attack properties to calculate the attack with.
     * @return The total base attack damage in HP.
     * @author Lilly Rosaline
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
     * @author Lilly Rosaline
     */
    fun rawPiercingDamage(): Double {
        return (this.head.mat.edgeholding + this.head.mat.hardness * 0.75) / 2
    }

    /**
     * Get the raw blunt damage (not multiplied by blunt damage multiplier).
     * @return The blunt damage, not multiplied by the blunt damage multiplier and not multiplied by the blunt damage constant.
     * @author Lilly Rosaline
     */
    fun rawBluntDamage(): Double {
        return (weight() / 100 + this.head.mat.hardness * 0.25)
    }

    /**
     * Get the effective weight of the tool.
     * @return The effective weight.
     * @author Lilly Rosaline
     */
    fun weight(): Double {
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
     * @author Lilly Rosaline
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
}