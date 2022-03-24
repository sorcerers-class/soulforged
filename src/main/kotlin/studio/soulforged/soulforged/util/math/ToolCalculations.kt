package studio.soulforged.soulforged.util.math

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.item.tool.combat.WeaponCategories
import studio.soulforged.soulforged.item.tool.part.ToolParts
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.sound.SoulforgedSoundEvents
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI
import java.util.*

@Suppress("private")
object ToolCalculations {
    const val head = 0
    const val binding = 1
    const val handle = 2


    fun calcDurability(stack: ItemStack): Int {
        val nbt = stack.nbt!!
        if (!nbt.contains("sf_damage")) {
            val headDura = (Objects.requireNonNull(
                ToolParts.TOOL_PARTS_REGISTRY[Identifier(
                    nbt.getCompound("sf_head").getString("type")
                )]
            )?.durability!! *
                    Materials.MATERIAL_REGISTRY[Identifier(
                        nbt.getCompound("sf_head").getString("material")
                    )]?.durability!!)
            val bindingDura = (
                    ToolParts.TOOL_PARTS_REGISTRY[Identifier(
                        nbt.getCompound("sf_binding").getString("type")
                    )]
                        ?.durability!! *
                            Materials.MATERIAL_REGISTRY[Identifier(
                                nbt.getCompound("sf_binding").getString("material")
                            )]?.durability!!)
            val handleDura = (
                    ToolParts.TOOL_PARTS_REGISTRY[Identifier(
                        nbt.getCompound("sf_handle").getString("type")
                    )]?.durability!! *
                            Materials.MATERIAL_REGISTRY[Identifier(
                                nbt.getCompound("sf_handle").getString("material")
                            )]?.durability!!)
            nbt.getCompound("sf_head").putInt("max_damage", headDura.toInt())
            nbt.getCompound("sf_binding").putInt("max_damage", bindingDura.toInt())
            nbt.getCompound("sf_handle").putInt("max_damage", handleDura.toInt())
            nbt.getCompound("sf_head").putInt("damage", headDura.toInt())
            nbt.getCompound("sf_binding").putInt("damage", bindingDura.toInt())
            nbt.getCompound("sf_handle").putInt("damage", handleDura.toInt())
            nbt.putBoolean("sf_damage", true)
        }
        val durability = getDurabilities(stack)
        val maxDurability = getMaxDurabilities(stack)
        nbt.getCompound("sf_head").putInt("damage", durability[head] - 1)
        val headDamage = (durability[head].toFloat() / maxDurability[head].toFloat() * 256).toInt()
        nbt.getCompound("sf_binding").putInt("damage", durability[binding] - 1)
        val bindingDamage = (durability[binding].toFloat() / maxDurability[binding].toFloat() * 256).toInt()
        nbt.getCompound("sf_handle").putInt("damage", durability[handle] - 1)
        val handleDamage = (durability[handle].toFloat() / maxDurability[handle].toFloat() * 256).toInt()
        return 256 - handleDamage.coerceAtMost(bindingDamage).coerceAtMost(headDamage)
    }

    fun getDurabilities(stack: ItemStack): IntArray {
        assert(stack.nbt != null)
        return intArrayOf(
            stack.nbt!!.getCompound("sf_head").getInt("damage"),
            stack.nbt!!.getCompound("sf_binding").getInt("damage"),
            stack.nbt!!
                .getCompound("sf_handle").getInt("damage")
        )
    }

    fun getMaxDurabilities(stack: ItemStack): IntArray {
        return intArrayOf(
            stack.nbt!!.getCompound("sf_head").getInt("max_damage"),
            stack.nbt!!.getCompound("sf_binding").getInt("max_damage"),
            stack.nbt!!
                .getCompound("sf_handle").getInt("max_damage")
        )
    }

    fun getWeight(stack: ItemStack): Double {
        val nbt = stack.nbt!!
        val head = ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))]
        val binding =
            ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_binding").getString("type"))]
        val handle =
            ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_handle").getString("type"))]
        val mhead = Materials.MATERIAL_REGISTRY[Identifier.tryParse(
            nbt.getCompound("sf_head").getString("material")
        )]
        val mbinding = Materials.MATERIAL_REGISTRY[Identifier.tryParse(
            nbt.getCompound("sf_binding").getString("material")
        )]
        val mhandle = Materials.MATERIAL_REGISTRY[Identifier.tryParse(
            nbt.getCompound("sf_handle").getString("material")
        )]
        assert(head != null && mhead != null && binding != null && mbinding != null && handle != null && mhandle != null)
        return head?.weight!! * mhead!!.density + binding!!.weight * mbinding!!.density + 0.25 * handle!!.weight * mhandle!!.density
    }
    fun calcAttackSpeed(stack: ItemStack): Double {
        assert(stack.nbt != null)
        return 1 / (getWeight(stack) / 800 /
                ToolTypes.TOOL_TYPES_REGISTRY[Identifier(
                    stack.nbt!!.getString("sf_tool_type")
                )]?.defaultAttack?.speed!!)
    }

}