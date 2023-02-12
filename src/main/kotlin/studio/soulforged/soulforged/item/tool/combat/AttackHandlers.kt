package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityGroup
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst

object AttackHandlers {
    val NO_CRIT = AttackHandler { _: PlayerEntity, _: Entity, _: Int ->
        return@AttackHandler ActionResult.FAIL
    }
    val WITH_CRITS = AttackHandler { attacker: PlayerEntity, target: Entity, clicks: Int ->
        if(attacker.mainHandStack.item != SoulforgedItems.TOOL) return@AttackHandler ActionResult.FAIL
        if(attacker.mainHandStack.nbt == null) return@AttackHandler ActionResult.FAIL
        val tool = ToolInst.ToolInstSerializer.deserialize(attacker.mainHandStack.nbt!!)
        if (target.isAttackable) {
            if (!target.handleAttack(attacker)) {
                target.damage(DamageSource.player(attacker),
                tool.baseAttackDamage(tool.attackProperties(clicks)).toFloat())
            }
        }
        return@AttackHandler ActionResult.SUCCESS
    }
    val DEFAULT = AttackHandler { attacker: PlayerEntity, target: Entity, _: Int ->
        if (target.isAttackable) {
            if (!target.handleAttack(attacker)) {
                target.damage(DamageSource.player(attacker),
                    attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE).toFloat()
                            + EnchantmentHelper.getAttackDamage(attacker.mainHandStack,
                        if(target is LivingEntity) target.group else EntityGroup.DEFAULT
                    )
                )
            }
        }
        return@AttackHandler ActionResult.SUCCESS
    }
}