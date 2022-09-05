package studio.soulforged.soulforged.item.tool

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.client.gui.CombatDebuggerClientUI
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.part.PartPosition
import studio.soulforged.soulforged.item.tool.part.ToolPartInst
import studio.soulforged.soulforged.item.tool.part.ToolParts
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.recipe.ToolRecipes

/**
 * Represents an instance of a specific tool.
 * @author Lilly Rosaline
 * @see ToolType
 * @see studio.soulforged.soulforged.item.tool.part.ToolPart
 * @see studio.soulforged.soulforged.material.Material
 */
class ToolInst(val stack: ItemStack, val type: ToolType, val head: ToolPartInst, val binding: ToolPartInst, val handle: ToolPartInst) {
    /**
     * Gets the durability of the tool to display.
     * @return The durability, normalized from 0 to 255.
     * @author Lilly Rosaline
     */
    fun getDurability(): UInt {
        val headDamage = (256 * head.durability.toFloat() / head.maxDurability.toFloat()).toUInt()
        val bindingDamage = (256 * binding.durability.toFloat() / head.maxDurability.toFloat()).toUInt()
        val handleDamage = (256 * binding.durability.toFloat() / head.maxDurability.toFloat()).toUInt()
        return 256u - handleDamage.coerceAtMost(bindingDamage).coerceAtMost(headDamage)
    }
    fun setDurability(head_durability: UInt, binding_durability: UInt, handle_durability: UInt) {
        head.durability = head_durability
        binding.durability = binding_durability
        handle.durability = handle_durability
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
        val type = head.type

        CombatDebuggerClientUI.critType = ap.type
        val totalPiercingDamage = rawPiercingDamage() * ap.piercingDamage
        val totalBluntDamage = rawBluntDamage() * ap.bluntDamage * 0.8
        return if(getDurability() <= 0u)
            (totalBluntDamage + totalPiercingDamage)
        else 0.0
    }

    /**
     * Get the raw piercing damage (not multiplied by the piercing multiplier).
     * @return The piercing damage, not multiplied by the piercing multiplier.
     * @author Lilly Rosaline
     */
    fun rawPiercingDamage(): Double {
        return (head.mat.edgeholding + head.mat.hardness * 0.75) / 2
    }

    /**
     * Get the raw blunt damage (not multiplied by blunt damage multiplier).
     * @return The blunt damage, not multiplied by the blunt damage multiplier and not multiplied by the blunt damage constant.
     * @author Lilly Rosaline
     */
    fun rawBluntDamage(): Double {
        return (weight() / 100 + head.mat.hardness * 0.25)
    }

    /**
     * Get the effective weight of the tool.
     * @return The effective weight.
     * @author Lilly Rosaline
     */
    fun weight(): Double {
        val headWeight: Double = head.part.weight * head.mat.density
        val bindingWeight: Double = binding.part.weight * binding.mat.density
        val handleWeight: Double = handle.part.weight * handle.mat.density
        return headWeight + bindingWeight + 0.25 * handleWeight
    }

    /**
     * Get an instance of attack properties, based on the number of click packets.
     * @param attack Number of attacks.
     * @see studio.soulforged.soulforged.item.tool.combat.AttackHandler
     * @return The attack properties, if it can be found.
     * @author Lilly Rosaline
     */
    fun attackProperties(attack: Int): AttackProperties {
        return if(attack == 1) {
            head.type.hcAttack ?: head.type.defaultAttack
        } else if(attack == 2 || attack == 0) {
            head.type.defaultAttack
        } else {
            head.type.dcAttack ?: head.type.defaultAttack
        }
    }

    /**
     * Writes the contents of the tool to a stack's NBT.
     */
    fun write(stack: ItemStack) {
        val nbt = stack.nbt!!
        nbt.putString("sf_tool_type", type.id.toString())
        head.write(nbt)
        binding.write(nbt)
        handle.write(nbt)
    }
    companion object {
        fun fromRaw(stack: ItemStack, type: Identifier, headMaterial: Identifier, bindingMaterial: Identifier, handleMaterial: Identifier): ToolInst {
            val toolType = ToolTypes.TOOL_TYPES_REGISTRY.get(type)
            val hm = Materials.MATERIAL_REGISTRY.get(headMaterial)
            val bm = Materials.MATERIAL_REGISTRY.get(bindingMaterial)
            val ham = Materials.MATERIAL_REGISTRY.get(handleMaterial)

            val recipe = ToolRecipes.TOOL_RECIPES_REGISTRY.get(type)
            val headPart = ToolParts.TOOL_PARTS_REGISTRY.get(recipe?.head)
            val bindingPart = ToolParts.TOOL_PARTS_REGISTRY.get(recipe?.binding)
            val handlePart = ToolParts.TOOL_PARTS_REGISTRY.get(recipe?.handle)

            toolType!!
            val headInst = ToolPartInst(PartPosition.HEAD, headPart!!, toolType, hm!!, 255u, 255)
            val bindingInst = ToolPartInst(PartPosition.BINDING, bindingPart!!, toolType, bm!!, 255u, 255)
            val handleInst = ToolPartInst(PartPosition.HANDLE, handlePart!!, toolType, ham!!, 255u, 255)

            return ToolInst(stack, toolType, headInst, bindingInst, handleInst)
        }
        fun fromNbt(stack: ItemStack): ToolInst {
            return fromRaw(
                stack,
                Identifier(stack.nbt?.getString("sf_tool_type")),
                Identifier(stack.nbt?.getCompound("sf_head")?.getString("material")),
                Identifier(stack.nbt?.getCompound("sf_binding")?.getString("material")),
                Identifier(stack.nbt?.getCompound("sf_handle")?.getString("material"))
            )
        }
    }
}