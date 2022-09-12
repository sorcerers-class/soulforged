package studio.soulforged.soulforged.item.tool

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.part.PartPosition
import studio.soulforged.soulforged.item.tool.part.ToolPartInst
import studio.soulforged.soulforged.item.tool.part.ToolParts
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.util.NbtSerializer
import java.util.*

/**
 * Represents an instance of a specific tool.
 * @author Lilly Rosaline
 * @see ToolType
 * @see studio.soulforged.soulforged.item.tool.part.ToolPart
 * @see studio.soulforged.soulforged.material.Material
 */
class ToolInst(val stack: ItemStack, val type: ToolType, val head: ToolPartInst, val binding: ToolPartInst, val handle: ToolPartInst) {
    constructor(type: Identifier, head: Identifier, binding: Identifier, handle: Identifier) : this(
        SoulforgedItems.TOOL.defaultStack,
        ToolTypes.TOOL_TYPES_REGISTRY.get(type) ?: ToolTypes.SHORTSWORD,
        ToolPartInst(ToolTypes.TOOL_TYPES_REGISTRY.get(type)?.parts?.get(0) ?: ToolParts.SHORTSWORD_BLADE, Materials.MATERIAL_REGISTRY.get(head) ?: Materials.WOOD),
        ToolPartInst(ToolTypes.TOOL_TYPES_REGISTRY.get(type)?.parts?.get(1) ?: ToolParts.HILT, Materials.MATERIAL_REGISTRY.get(binding) ?: Materials.WOOD),
        ToolPartInst(ToolTypes.TOOL_TYPES_REGISTRY.get(type)?.parts?.get(2) ?: ToolParts.HANDLE, Materials.MATERIAL_REGISTRY.get(handle) ?: Materials.WOOD)
    )
    /**
     * Gets the durability of the tool to display.
     * @return The durability, normalized from 0 to 255.
     * @author Lilly Rosaline
     */
    fun getDisplayDurability(): UInt {
        val headDurability = (256 * head.durability.toFloat() / head.maxDurability.toFloat()).toUInt()
        val bindingDurability = (256 * binding.durability.toFloat() / binding.maxDurability.toFloat()).toUInt()
        val handleDurability = (256 * handle.durability.toFloat() / handle.maxDurability.toFloat()).toUInt()
        return 256u - headDurability.coerceAtMost(bindingDurability.coerceAtMost(handleDurability))
    }
    fun setDurability(hd: Int, bd: Int, had: Int) {
        head.durability = hd
        binding.durability = bd
        handle.durability = had
    }
    fun modifyDurability(hd: Int, bd: Int, had: Int, delta: Boolean) {
        if(delta) {
            head.durability += hd
            binding.durability += bd
            handle.durability += had
        } else {
            head.durability = hd
            binding.durability = bd
            handle.durability = had
        }
    }
    fun damage(random: Random) {
        modifyDurability(-1, if(random.nextBoolean()) -1 else 0, if(random.nextBoolean() && random.nextBoolean()) -1 else 0, true)
    }
    fun getDurability(part: PartPosition): Int {
        return when (part) {
            PartPosition.HEAD -> head.durability
            PartPosition.BINDING -> binding.durability
            PartPosition.HANDLE -> handle.durability
        }
    }
    fun shouldBreak(): Boolean {
        return head.durability <= 0 || binding.durability <= 0 || handle.durability <= 0
    }
    /**
     * Get the base attack speed without any modifiers.
     * @param ap The attack properties to get the attack speed from. This can probably be set to the default attack?
     * @return The attack speed in whatever unit the game uses for attack speed, I think it might be attacks/second
     * @author Lilly Rosaline
     */
    fun baseAttackSpeed(ap: AttackProperties): Double {
        return 1.0 / (weight() / 800.0 / ap.speed)
    }
    /**
     * Get the base attack damage, without any crits.
     * @param ap The attack properties to calculate the attack with.
     * @return The total base attack damage in HP.
     * @author Lilly Rosaline
     */
    fun baseAttackDamage(ap: AttackProperties): Double {
        //CombatDebuggerClientUI.critType = ap.type
        val totalPiercingDamage = rawPiercingDamage() * ap.piercingDamage
        val totalBluntDamage = rawBluntDamage() * ap.bluntDamage * 0.8
        return if(!shouldBreak())
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
        return when(attack) {
            0 -> type.hcAttack ?: type.defaultAttack
            1 -> type.defaultAttack
            else -> type.dcAttack ?: type.defaultAttack
        }
    }
    object ToolInstSerializer : NbtSerializer<ToolInst> {
        override fun serialize(target: ToolInst): NbtCompound {
            val nbt = NbtCompound()
            nbt.putString("sf_tool_type", target.type.id.toString())
            nbt.put("sf_head", ToolPartInst.ToolPartInstSerializer.serialize(target.head))
            nbt.put("sf_binding", ToolPartInst.ToolPartInstSerializer.serialize(target.binding))
            nbt.put("sf_handle", ToolPartInst.ToolPartInstSerializer.serialize(target.handle))
            return nbt
        }

        override fun deserialize(nbt: NbtCompound): ToolInst {
            val type = ToolTypes.TOOL_TYPES_REGISTRY.get(Identifier(nbt.getString("sf_tool_type"))) ?: ToolTypes.SHORTSWORD
            val head = ToolPartInst.ToolPartInstSerializer.deserialize(nbt.getCompound("sf_head"))
            val binding = ToolPartInst.ToolPartInstSerializer.deserialize(nbt.getCompound("sf_binding"))
            val handle = ToolPartInst.ToolPartInstSerializer.deserialize(nbt.getCompound("sf_handle"))
            return ToolInst(SoulforgedItems.TOOL.defaultStack, type, head, binding, handle)
        }

    }
}
