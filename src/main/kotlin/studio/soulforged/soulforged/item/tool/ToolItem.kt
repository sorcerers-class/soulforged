package studio.soulforged.soulforged.item.tool

import net.minecraft.block.BlockState
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.Rarity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings
import studio.soulforged.soulforged.item.tool.combat.AttackTypes
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

    private fun breakTool(user: PlayerEntity) {
        user.sendToolBreakStatus(Hand.MAIN_HAND)
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        val nbt = attacker.mainHandStack.nbt ?: return false
        val tool = ToolInst.ToolInstSerializer.deserialize(nbt)
        tool.damage(target.world.random)
        if (tool.shouldBreak()) breakTool(attacker as PlayerEntity)
        stack.nbt = ToolInst.ToolInstSerializer.serialize(tool)
        stack.damage = tool.getDisplayDurability().toInt()
        return true
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        val nbt = stack.nbt ?: return 1.0f
        val tool = ToolInst.ToolInstSerializer.deserialize(nbt)
        return tool.type.callbacks.getMiningSpeed(state, tool.head.mat)
    }

    override fun postMine(stack: ItemStack, world: World, state: BlockState, pos: BlockPos, miner: LivingEntity): Boolean {
        val nbt = miner.mainHandStack.nbt ?: return false
        val tool = ToolInst.ToolInstSerializer.deserialize(nbt)
        tool.damage(world.random)
        if(tool.shouldBreak()) breakTool(miner as PlayerEntity)
        stack.nbt = ToolInst.ToolInstSerializer.serialize(tool)
        stack.damage = tool.getDisplayDurability().toInt()
        return true
    }

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity): Boolean {
        val nbt = miner.mainHandStack.nbt ?: return false
        val tool = ToolInst.ToolInstSerializer.deserialize(nbt)
        return !tool.shouldBreak()
    }

    override fun isSuitableFor(state: BlockState): Boolean {
        return true
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val nbt = context.stack.nbt ?: return ActionResult.FAIL
        val tool = ToolInst.ToolInstSerializer.deserialize(nbt)
        if (tool.shouldBreak()) return ActionResult.FAIL
        val result = tool.type.callbacks.onRightClick(context)
        if(result == ActionResult.SUCCESS) {
            tool.damage(context.world.random)
            context.stack.nbt = ToolInst.ToolInstSerializer.serialize(tool)
            context.stack.damage = tool.getDisplayDurability().toInt()
        }
        return result
    }

    @ClientOnly
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val nbt = stack.nbt ?: return
        val tool = ToolInst.ToolInstSerializer.deserialize(nbt)
        val headMaterial = Text.translatable(tool.head.mat.name)
        val headType = tool.head.part.name
        val bindingMaterial = Text.translatable(tool.binding.mat.name)
        val bindingType = tool.binding.part.name
        val handleMaterial = Text.translatable(tool.handle.mat.name)
        val handleType = tool.handle.part.name
        val toolType = tool.type
        val hc = toolType.hcAttack != null
        val dc = toolType.dcAttack != null
        if(
            headMaterial == Text.literal("missingno")
            || headType == "missingno"
            || bindingMaterial == Text.literal("missingno")
            || bindingType == "missingno"
            || handleMaterial == Text.literal("missingno")
            || handleType == "missingno"
            || toolType.name == "missingno"
            ) return
        //tooltip.add(new TranslatableText("item.soulforged.tool.type." + type + ".desc").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(
            Text.translatable(headType, headMaterial)
                .append(" + ").formatted(Formatting.RESET)
                .append(
                    Text.translatable(bindingType, bindingMaterial)
                )
                .append(" + ").formatted(Formatting.RESET)
                .append(
                    Text.translatable(handleType, handleMaterial)
                )
                .formatted(Formatting.GOLD)
        )
        tooltip.add(
            Text.literal("")
                .append(
                    Text.translatable(
                        "item.soulforged.tool.tooltip.weight",
                        (tool.weight() * 100.0).roundToInt() / 100.0
                    ).formatted(
                        Formatting.BLUE, Formatting.BOLD
                    )
                )
                .append(" / ").formatted(Formatting.RESET)
                .append(
                    Text.translatable(
                        "item.soulforged.tool.tooltip.speed",
                        (tool.baseAttackSpeed(tool.attackProperties(AttackTypes.NORMAL)) * 100.0).roundToInt() / 100.0
                    ).formatted(
                        Formatting.GREEN, Formatting.BOLD
                    )
                )
                .append(" / ").formatted(Formatting.RESET)
                .append(
                    Text.translatable(
                        "item.soulforged.tool.tooltip.attack",
                        (tool.baseAttackDamage(tool.attackProperties(AttackTypes.NORMAL)) * 100.0).roundToInt() / 100.0
                    ).formatted(
                        Formatting.RED, Formatting.BOLD
                    )
                )
                .append(Text.of("⛤ \uD83D\uDD25️ ❄ \uD83C\uDF29️️ ☣️ "))
        )
        tooltip.add(
            Text.translatable(
                "item.soulforged.tool.tooltip.defaultattack",
                Text.translatable(
                    "item.soulforged.tool.tooltip.attacktype." + toolType.defaultAttack.category.name
                        .lowercase(Locale.getDefault())
                ),
                Text.translatable(
                    "item.soulforged.tool.tooltip.attackdirection." + toolType.defaultAttack.critDirection.name
                        .lowercase(Locale.getDefault())
                )
            ).formatted(
                Formatting.DARK_PURPLE
            )
        )
        tooltip.add(
            Text.literal("")
                .append(
                    if (hc) Text.translatable(
                        "item.soulforged.tool.tooltip.hc.true",
                        Text.translatable(
                            "item.soulforged.tool.tooltip.attacktype." + toolType.hcAttack?.category?.name
                                ?.lowercase(Locale.getDefault())
                        ),
                        Text.translatable(
                            "item.soulforged.tool.tooltip.attackdirection." + (toolType.hcAttack?.critDirection?.name
                                ?.lowercase(Locale.getDefault())
                                    )
                        ).formatted(
                            Formatting.DARK_GREEN
                        )) else Text.translatable("item.soulforged.tool.tooltip.hc.false").formatted(
                        Formatting.DARK_RED,
                        Formatting.BOLD
                    )
                )
                .append("; ")
                .append(
                    if (dc) Text.translatable(
                        "item.soulforged.tool.tooltip.dc.true",
                        Text.translatable(
                            "item.soulforged.tool.tooltip.attacktype." + toolType.dcAttack?.category?.name
                                ?.lowercase(Locale.getDefault())
                        ),
                        Text.translatable(
                            "item.soulforged.tool.tooltip.attackdirection." + toolType.dcAttack?.critDirection?.name
                                ?.lowercase(Locale.getDefault())
                        )
                    ).formatted(
                        Formatting.DARK_GREEN
                    ) else Text.translatable("item.soulforged.tool.tooltip.dc.false").formatted(
                        Formatting.DARK_RED,
                        Formatting.BOLD
                    )
                )
        )
    }

    @ClientOnly
    override fun getName(stack: ItemStack): Text {
        val nbt = stack.nbt ?: return Text.literal("missingno")
        val tool = ToolInst.ToolInstSerializer.deserialize(nbt)
        return Text.translatable(
            tool.type.name, Text.translatable(tool.head.mat.name)
        )
    }
    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean {
        return false
    }
}