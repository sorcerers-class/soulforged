package studio.soulforged.soulforged.item.tool

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.combat.WeaponCategories
import studio.soulforged.soulforged.item.tool.part.ToolParts
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.sound.SoulforgedSoundEvents
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI

object ToolCalculations {
    fun calcAttackSpeed(stack: ItemStack): Double {
        assert(stack.nbt != null)
        return 1 / (ToolItem.getWeight(stack) / 800 /
                ToolTypes.TOOL_TYPES_REGISTRY[Identifier(
                    stack.nbt!!.getString("sf_tool_type")
                )]?.defaultAttack?.speed!!)
    }
    fun calcAttackDamage(
        stack: ItemStack,
        attackType: Int,
        attacker: PlayerEntity?,
        target: Entity?,
        crit: Float
    ): Double {
        val nbt = stack.nbt
        return if (nbt != null) {
            if (ToolItem.getDurabilities(stack).any {  i: Int -> i <= 0 }) return 0.0
            val head =
                ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))]
            val binding =
                ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_binding").getString("type"))]
            val handle =
                ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_handle").getString("type"))]
            val mhead =
                Materials.MATERIAL_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_head").getString("material"))]
            val mbinding = Materials.MATERIAL_REGISTRY[Identifier.tryParse(
                nbt.getCompound("sf_binding").getString("material")
            )]
            val mhandle =
                Materials.MATERIAL_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_handle").getString("material"))]
            assert(mhead != null)
            val headEdgeholding = mhead!!.edgeholding
            val headHardness = mhead.hardness
            val type = ToolTypes.TOOL_TYPES_REGISTRY[Identifier.tryParse(nbt.getString("sf_tool_type"))]!!
            var ap = type.defaultAttack
            if (attackType == 1) {
                ap = type.hcAttack ?: type.defaultAttack
            }
            if (attackType >= 3) {
                ap = type.dcAttack ?: type.defaultAttack
            }
            var velocity = Vec3d.ZERO
            if (attacker != null) velocity =
                attacker.velocity.rotateY(Math.toRadians((attacker.yaw % 360.0f).toDouble()).toFloat())
                    .multiply(-1.0, 1.0, 1.0)
            CombatDebuggerClientUI.critType = ap.type
            if (velocity != null && target != null) {
                when (ap.category) {
                    WeaponCategories.SLASHING -> target.getWorld().playSound(
                        null,
                        target.blockPos,
                        SoulforgedSoundEvents.CRIT_SLASHING,
                        SoundCategory.PLAYERS,
                        1.0f,
                        1.0f
                    )
                    WeaponCategories.THRUSTING -> target.getWorld().playSound(
                        null,
                        target.blockPos,
                        SoulforgedSoundEvents.CRIT_THRUSTING,
                        SoundCategory.PLAYERS,
                        1.0f,
                        1.0f
                    )
                    WeaponCategories.CRUSHING -> target.getWorld().playSound(
                        null,
                        target.blockPos,
                        SoulforgedSoundEvents.CRIT_CRUSHING,
                        SoundCategory.PLAYERS,
                        1.0f,
                        1.0f
                    )
                }
            }
            val piercingDamage: Double = ap.piercingDamage
            val totalPiercingDamage = (headEdgeholding + headHardness * 0.75) / 2 * piercingDamage
            assert(head != null)
            val headWeight: Double = head?.weight!! * mhead.density
            val bindingWeight: Double =
                binding?.weight!! * mbinding?.density!!
            val handleWeight: Double =
                handle?.weight!! * mhandle?.density!!
            val effectiveWeight = headWeight + bindingWeight + 0.25 * handleWeight
            val totalBluntDamage: Double =
                (effectiveWeight / 100 + headHardness * 0.25) * ap.bluntDamage * 0.8
            return (totalBluntDamage + totalPiercingDamage) * crit
        } else 1.0
    }

}