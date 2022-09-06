package studio.soulforged.soulforged.item.tool.part

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.tool.ToolType
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.material.Material
import studio.soulforged.soulforged.material.Materials

class ToolPartInst(val pos: PartPosition, val part: ToolPart, val type: ToolType, val mat: Material, var durability: UInt, val maxDurability: Int) {
    fun write(): NbtCompound {
        val nbt = NbtCompound()
        nbt.putInt("max_durability", maxDurability)
        nbt.putString("material", mat.id.toString())
        nbt.putString("type", part.id.toString())
        nbt.putInt("durability", durability.toInt())
        return nbt
    }
    fun decDurability() {
        durability -= 1u
    }
    companion object {
        fun fromNbt(pos: PartPosition, nbt: NbtCompound): ToolPartInst? {
            val type = ToolTypes.TOOL_TYPES_REGISTRY[Identifier.tryParse(nbt.getString("sf_tool_type"))]
            val partNbt = nbt.getCompound(String.format("sf_%s", pos.toString().lowercase()))
            val ptype = ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(partNbt.getString("type"))]
            val material = Materials.MATERIAL_REGISTRY[Identifier.tryParse(partNbt.getString("material"))]
            val durability = partNbt.getInt("damage").toUInt()
            val maxDurability = partNbt.getInt("max_damage")
            return if (ptype != null && type != null && material != null) {
                ToolPartInst(pos, ptype, type, material, durability, maxDurability)
            } else {
                return null
            }
        }
    }
}