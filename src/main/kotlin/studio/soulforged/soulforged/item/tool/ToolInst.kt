package studio.soulforged.soulforged.item.tool

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.random.RandomGenerator
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.combat.AttackTypes
import studio.soulforged.soulforged.item.tool.part.PartPosition
import studio.soulforged.soulforged.item.tool.part.ToolPartInst
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.util.NbtSerializer

/**
 * Represents an instance of a specific tool.
 * @author Lilly Rosaline
 * @see ToolType
 * @see studio.soulforged.soulforged.item.tool.part.ToolPart
 * @see studio.soulforged.soulforged.material.Materials.Material
 */
class ToolInst(val stack: ItemStack, val type: ToolType, val head: ToolPartInst, val binding: ToolPartInst, val handle: ToolPartInst, val pattern: Materials.Material) {
    constructor(type: Identifier, head: Identifier, binding: Identifier, handle: Identifier, pattern: Identifier) : this(
        SoulforgedItems.TOOL.defaultStack,
        ToolTypes.TOOL_TYPES[type] ?: throw IllegalArgumentException(),
        ToolPartInst(ToolTypes.TOOL_TYPES[type]?.parts?.get(0) ?: throw IllegalArgumentException(), Materials.MATERIALS[head] ?: throw IllegalArgumentException()),
        ToolPartInst(ToolTypes.TOOL_TYPES[type]?.parts?.get(1) ?: throw IllegalArgumentException(), Materials.MATERIALS[binding] ?: throw IllegalArgumentException()),
        ToolPartInst(ToolTypes.TOOL_TYPES[type]?.parts?.get(2) ?: throw IllegalArgumentException(), Materials.MATERIALS[handle] ?: throw IllegalArgumentException()),
        Materials.MATERIALS[pattern] ?: throw IllegalArgumentException()
    )
    constructor() : this(Identifier("soulforged:none"), Identifier("soulforged:none"), Identifier("soulforged:none"), Identifier("soulforged:none"), Identifier("soulforged:none"))
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
    fun damage(random: RandomGenerator) {
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
        return if(!shouldBreak()) totalPiercingDamage + totalBluntDamage else 0.0
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
        return (headWeight + bindingWeight + 0.25 * handleWeight) //attributes.get(AttributeIdentifiers.WEIGHT, (headWeight + bindingWeight + 0.25 * handleWeight).toFloat()).toDouble()
    }

    /**
     * Get an instance of attack properties, based on the number of click packets.
     * @param attack Number of attacks.
     * @see studio.soulforged.soulforged.item.tool.combat.AttackHandler
     * @return The attack properties, if it can be found.
     * @author Lilly Rosaline
     */
    fun attackProperties(attack: AttackTypes): AttackProperties {
        return when(attack) {
            AttackTypes.HC -> type.hcAttack ?: type.defaultAttack
            AttackTypes.NORMAL-> type.defaultAttack
            AttackTypes.DC -> type.dcAttack ?: type.defaultAttack
        }
    }
    object ToolInstSerializer : NbtSerializer<ToolInst> {
        override fun serialize(target: ToolInst): NbtCompound {
            val nbt = NbtCompound()
            nbt.putString("sf_tool_type", target.type.id.toString())
            nbt.put("sf_head", ToolPartInst.ToolPartInstSerializer.serialize(target.head))
            nbt.put("sf_binding", ToolPartInst.ToolPartInstSerializer.serialize(target.binding))
            nbt.put("sf_handle", ToolPartInst.ToolPartInstSerializer.serialize(target.handle))
            nbt.putString("sf_pattern", target.pattern.id.toString())
            return nbt
        }

        @Throws(IllegalArgumentException::class)
        override fun deserialize(nbt: NbtCompound): ToolInst? {
            val type = ToolTypes.TOOL_TYPES[Identifier(nbt.getString("sf_tool_type"))] ?: return null
            val head = ToolPartInst.ToolPartInstSerializer.deserialize(nbt.getCompound("sf_head")) ?: return null
            val binding = ToolPartInst.ToolPartInstSerializer.deserialize(nbt.getCompound("sf_binding")) ?: return null
            val handle = ToolPartInst.ToolPartInstSerializer.deserialize(nbt.getCompound("sf_handle")) ?: return null
            val pattern = Materials.MATERIALS[Identifier(nbt.getString("sf_pattern"))] ?: return null
            return ToolInst(SoulforgedItems.TOOL.defaultStack, type, head, binding, handle, pattern)
        }

    }
}
