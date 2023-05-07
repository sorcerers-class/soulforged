package studio.soulforged.soulforged.item.tool.combat

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityGroup
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.registry.Registry
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.registry.invoke
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.Soulforged.sid
import studio.soulforged.soulforged.client.debugger.CombatDebuggerClientUI
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.util.RegistryUtil

object AttackHandlers {
    val NONE = AttackHandler { _: PlayerEntity?, _: Entity?, _: AttackTypes, _: CritDirections? ->
        return@AttackHandler ActionResult.FAIL
    }
    val ATTACK_HANDLERS_REGISTRY: Registry<AttackHandler> = RegistryUtil.createRegistry("attack_handlers".sid(), NONE)
    val WITH_CRITS = AttackHandler { attacker: PlayerEntity?, target: Entity?, attackType: AttackTypes, critDirection: CritDirections? ->
        if(attacker == null || target == null) return@AttackHandler ActionResult.FAIL
        if(attacker.mainHandStack.item != SoulforgedItems.TOOL) return@AttackHandler ActionResult.FAIL
        if(attacker.mainHandStack.nbt == null) return@AttackHandler ActionResult.FAIL
            val tool = ToolInst.ToolInstSerializer.deserialize(attacker.mainHandStack.nbt!!) ?: return@AttackHandler ActionResult.FAIL
            if (target.isAttackable) {
                if (!target.handleAttack(attacker)) {
                    val attackProperties = tool.attackProperties(attackType)
                    if(attacker.pos.isInRange(target.pos, attackProperties.range)) {
                        val multiplier = if(critDirection == attackProperties.critDirection) critDirection.multiplier else 1.0f
                        val damage = tool.baseAttackDamage(attackProperties).toFloat() * multiplier
                        target.damage(target.damageSources.playerAttack(attacker), damage)
                        CombatDebuggerClientUI.debuggerAttackCallback(damage, critDirection, attackType, multiplier)
                    }
                }
            }
            return@AttackHandler ActionResult.SUCCESS

    }
    val DEFAULT = AttackHandler { attacker: PlayerEntity?, target: Entity?, type: AttackTypes, _: CritDirections? ->
        if(attacker == null || target == null) return@AttackHandler ActionResult.FAIL
        if (target.isAttackable) {
            if (!target.handleAttack(attacker)) {
                val damage = attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE).toFloat()+ EnchantmentHelper.getAttackDamage(attacker.mainHandStack,
                    if(target is LivingEntity) target.group else EntityGroup.DEFAULT
                )
                target.damage(target.damageSources.playerAttack(attacker), damage)
                CombatDebuggerClientUI.debuggerAttackCallback(damage, null, null, -1.0f)
            }
        }
        return@AttackHandler ActionResult.SUCCESS
    }
    fun register(id: Identifier, handler: AttackHandler): AttackHandler {
        return Registry.register(ATTACK_HANDLERS_REGISTRY, id, handler)
    }
    internal fun init() {
        ATTACK_HANDLERS_REGISTRY(Soulforged.NAME) {
            NONE withId "none"
            WITH_CRITS withId "with_crits"
            DEFAULT withId "default"
        }
    }

}