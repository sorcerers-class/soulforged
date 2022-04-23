package studio.soulforged.soulforged.item.tool

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import studio.soulforged.soulforged.item.tool.combat.AttackHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.block.BlockState
import studio.soulforged.soulforged.material.Materials
import net.minecraft.util.math.BlockPos
import net.minecraft.item.ItemUsageContext
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.text.TranslatableText
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.*
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings
import java.util.*
import kotlin.math.roundToInt

class ToolItem : Item(
    QuiltItemSettings()
        .rarity(Rarity.RARE)
        .fireproof()
        .maxCount(1)
        .maxDamage(256)
) {
    override fun getEnchantability(): Int {
        return -1
    }

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        for (ah in AttackHandler.attackHandlers.values) {
            ah.tick()
        }
    }

    private fun breakTool(user: PlayerEntity) {
        user.sendToolBreakStatus(Hand.MAIN_HAND)
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        val tool = ToolInst.fromNbt(stack)
        tool.sync(attacker as PlayerEntity)
        if (tool.durability() <= 0u) breakTool(attacker)
        if (!stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .containsKey(EntityAttributes.GENERIC_ATTACK_SPEED)
        ) stack.addAttributeModifier(
            EntityAttributes.GENERIC_ATTACK_SPEED,
            EntityAttributeModifier(
                "f106b032-3216-4ff6-9919-36cf09d350f5",
                tool.baseAttackSpeed(tool.attackProperties(2)!!),
                EntityAttributeModifier.Operation.ADDITION
            ),
            EquipmentSlot.MAINHAND
        )
        stack.damage = tool.durability().toInt()
        return true
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        val tool = ToolInst.fromNbt(stack)
        if (tool.durability() <= 0u) return 0.0f
        val msp: MiningSpeedProcessor = tool.type.miningSpeedProcessor as MiningSpeedProcessor
        return msp.getMiningSpeed(state, tool.head.mat)
    }

    override fun postMine(stack: ItemStack, world: World, state: BlockState, pos: BlockPos, miner: LivingEntity): Boolean {
        val tool = ToolInst.fromNbt(stack)
        if (tool.durability() <= 0u) breakTool(miner as PlayerEntity)
        stack.damage = tool.durability().toInt()
        return true
    }

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity): Boolean {
        val tool = ToolInst.fromNbt(miner.mainHandStack)
        return tool.durability() <= 0u
    }

    override fun isSuitableFor(state: BlockState): Boolean {
        return true
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val tool = ToolInst.fromNbt(context.stack)
        if (tool.durability() <= 0u) return ActionResult.FAIL
        val rcep: RightClickEventProcessor = tool.type.rightClickEventProcessor as RightClickEventProcessor
        return rcep.onRightClick(context)!!
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val tool = ToolInst.fromNbt(stack)
        val headMaterial = TranslatableText(tool.head.mat.name)
        val headType = tool.head.part.name
        val bindingMaterial = TranslatableText(tool.binding.mat.name)
        val bindingType = tool.binding.part.name
        val handleMaterial = TranslatableText(tool.handle.mat.name)
        val handleType = tool.handle.part.name
        val toolType = tool.type
        val hc = toolType.hcAttack != null
        val dc = toolType.dcAttack != null

        //tooltip.add(new TranslatableText("item.soulforged.tool.type." + type + ".desc").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(
            TranslatableText(headType, headMaterial)
                .append(" + ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(bindingType, bindingMaterial)
                )
                .append(" + ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(handleType, handleMaterial)
                )
                .formatted(Formatting.GOLD)
        )
        tooltip.add(
            LiteralText("")
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.weight",
                        (tool.weight() * 100.0).roundToInt() / 100.0
                    ).formatted(
                        Formatting.BLUE, Formatting.BOLD
                    )
                )
                .append(" / ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.speed",
                        (tool.baseAttackSpeed(tool.attackProperties(2)!!) * 100.0).roundToInt() / 100.0
                    ).formatted(
                        Formatting.GREEN, Formatting.BOLD
                    )
                )
                .append(" / ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.attack",
                        (tool.baseAttackDamage(tool.attackProperties(2)!!) * 100.0).roundToInt() / 100.0
                    ).formatted(
                        Formatting.RED, Formatting.BOLD
                    )
                )
        )
        tooltip.add(
            TranslatableText(
                "item.soulforged.tool.tooltip.defaultattack",
                TranslatableText(
                    "item.soulforged.tool.tooltip.attacktype." + toolType.defaultAttack.category.name
                        .lowercase(Locale.getDefault())
                ),
                TranslatableText(
                    "item.soulforged.tool.tooltip.attackdirection." + toolType.defaultAttack.type.name
                        .lowercase(Locale.getDefault())
                )
            ).formatted(
                Formatting.DARK_PURPLE
            )
        )
        tooltip.add(
            LiteralText("")
                .append(
                    if (hc) TranslatableText(
                        "item.soulforged.tool.tooltip.hc.true",
                        TranslatableText(
                            "item.soulforged.tool.tooltip.attacktype." + toolType.hcAttack?.category?.name
                                ?.lowercase(Locale.getDefault())
                        ),
                        TranslatableText(
                            "item.soulforged.tool.tooltip.attackdirection." + (toolType.hcAttack?.type?.name
                                ?.lowercase(Locale.getDefault())
                        )
                    ).formatted(
                        Formatting.DARK_GREEN
                    )) else TranslatableText("item.soulforged.tool.tooltip.hc.false").formatted(
                        Formatting.DARK_RED,
                        Formatting.BOLD
                    )
                )
                .append("; ")
                .append(
                    if (dc) TranslatableText(
                        "item.soulforged.tool.tooltip.dc.true",
                        TranslatableText(
                            "item.soulforged.tool.tooltip.attacktype." + toolType.dcAttack?.category?.name
                                ?.lowercase(Locale.getDefault())
                        ),
                        TranslatableText(
                            "item.soulforged.tool.tooltip.attackdirection." + toolType.dcAttack?.type?.name
                                ?.lowercase(Locale.getDefault())
                        )
                    ).formatted(
                        Formatting.DARK_GREEN
                    ) else TranslatableText("item.soulforged.tool.tooltip.dc.false").formatted(
                        Formatting.DARK_RED,
                        Formatting.BOLD
                    )
                )
        )
    }

    @Environment(EnvType.CLIENT)
    override fun getName(stack: ItemStack): Text {
        val tool = ToolInst.fromNbt(stack)
        return TranslatableText(
            tool.type.name, TranslatableText(tool.head.mat.name)
        )
    }

    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean {
        return false
    }
}