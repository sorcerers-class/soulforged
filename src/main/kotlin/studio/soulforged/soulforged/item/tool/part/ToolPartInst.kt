package studio.soulforged.soulforged.item.tool.part

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.util.NbtSerializer

class ToolPartInst(val part: ToolPart, val mat: Materials.Material, var durability: Int, val maxDurability: Int) {
    constructor(toolPart: ToolPart, mat: Materials.Material) : this(toolPart, mat, mat.durability, mat.durability)
    object ToolPartInstSerializer : NbtSerializer<ToolPartInst> {
        override fun serialize(target: ToolPartInst): NbtCompound {
            val nbt = NbtCompound()
            nbt.putString("part", target.part.id.toString())
            nbt.putString("material", target.mat.id.toString())
            nbt.putInt("durability", target.durability)
            nbt.putInt("max_durability", target.maxDurability)
            return nbt
        }

        override fun deserialize(nbt: NbtCompound): ToolPartInst {
            val part = ToolParts.TOOL_PARTS[Identifier(nbt.getString("part"))] ?: throw IllegalArgumentException()
            val mat = Materials.MATERIALS[Identifier(nbt.getString("material"))] ?: throw IllegalArgumentException()
            val durability = nbt.getInt("durability")
            val maxDurability = nbt.getInt("max_durability")
            return ToolPartInst(part, mat, durability, maxDurability)
        }

    }
}
