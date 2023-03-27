package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityGroup
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.util.RegistryUtil

object AttackHandlers {
    val NONE = AttackHandler { _: PlayerEntity?, _: Entity?, _: AttackTypes ->
        return@AttackHandler ActionResult.FAIL
    }
    val ATTACK_HANDLERS_REGISTRY: Registry<AttackHandler> = RegistryUtil.createRegistry("soulforged:attack_handlers", NONE)
    val WITH_CRITS = register(Identifier("soulforged:with_crits"), AttackHandler { attacker: PlayerEntity?, target: Entity?, attackType: AttackTypes ->
        if(attacker == null || target == null) return@AttackHandler ActionResult.FAIL
        if(attacker.mainHandStack.item != SoulforgedItems.TOOL) return@AttackHandler ActionResult.FAIL
        if(attacker.mainHandStack.nbt == null) return@AttackHandler ActionResult.FAIL
        val tool = ToolInst.ToolInstSerializer.deserialize(attacker.mainHandStack.nbt!!)

        if (target.isAttackable) {
            if (!target.handleAttack(attacker)) {
                target.damage(target.damageSources.playerAttack(attacker),
                tool.baseAttackDamage(tool.attackProperties(attackType)).toFloat())
            }
        }
        return@AttackHandler ActionResult.SUCCESS
    })
    val DEFAULT = register(Identifier("soulforged:default"), AttackHandler { attacker: PlayerEntity?, target: Entity?, _: AttackTypes ->
        if(attacker == null || target == null) return@AttackHandler ActionResult.FAIL
        if (target.isAttackable) {
            if (!target.handleAttack(attacker)) {
                target.damage(
                    target.damageSources.playerAttack(attacker),
                    attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE).toFloat()
                            + EnchantmentHelper.getAttackDamage(attacker.mainHandStack,
                        if(target is LivingEntity) target.group else EntityGroup.DEFAULT
                    )
                )
            }
        }
        return@AttackHandler ActionResult.SUCCESS
    })
    fun register(id: Identifier, handler: AttackHandler): AttackHandler {
        return Registry.register(ATTACK_HANDLERS_REGISTRY, id, handler)
    }
    fun init() {
        register(Identifier("soulforged:none"), NONE)
    }

}