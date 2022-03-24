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
import studio.soulforged.soulforged.util.math.ToolCalculations
import java.util.*
import kotlin.math.roundToInt

class ToolItem : Item(
    FabricItemSettings()
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
        if (ToolCalculations.getDurabilities(stack).any { i: Int -> i <= 0 }) breakTool(
            attacker as PlayerEntity
        )
        if (!stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .containsKey(EntityAttributes.GENERIC_ATTACK_SPEED)
        ) stack.addAttributeModifier(
            EntityAttributes.GENERIC_ATTACK_SPEED,
            EntityAttributeModifier(
                "f106b032-3216-4ff6-9919-36cf09d350f5",
                ToolCalculations.calcAttackSpeed(stack) - 4,
                EntityAttributeModifier.Operation.ADDITION
            ),
            EquipmentSlot.MAINHAND
        )
        stack.damage = ToolCalculations.calcDurability(stack)
        return true
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        assert(stack.nbt != null)
        if (ToolCalculations.getDurabilities(stack).any {  i: Int -> i <= 0 }) return 0.0f
        val msp: MiningSpeedProcessor = (ToolTypes.TOOL_TYPES_REGISTRY[Identifier(
            stack.nbt!!.getString("sf_tool_type")
        )]?.miningSpeedProcessor as MiningSpeedProcessor?)!!
        return msp.getMiningSpeed(
            state,
            Materials.MATERIAL_REGISTRY[Identifier(stack.nbt!!.getCompound("sf_head").getString("material"))]
        )
    }

    override fun postMine(stack: ItemStack, world: World, state: BlockState, pos: BlockPos, miner: LivingEntity): Boolean {
        if (Arrays.stream(ToolCalculations.getDurabilities(stack)).anyMatch { i: Int -> i < 0 }) breakTool(miner as PlayerEntity)
        stack.damage = ToolCalculations.calcDurability(stack)
        return true
    }

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity): Boolean {
        return ToolCalculations.getDurabilities(miner.mainHandStack).any {  i: Int -> i <= 0 }
    }

    override fun isSuitableFor(state: BlockState): Boolean {
        return true
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        return (
                ToolTypes.TOOL_TYPES_REGISTRY[Identifier.tryParse(
                    Objects.requireNonNull(
                        Objects.requireNonNull(
                            context.player
                        )?.mainHandStack?.nbt
                    )?.getString("sf_tool_type")
                )]
                    ?.rightClickEventProcessor as RightClickEventProcessor).onRightClick(context)!!
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val nbt = stack.nbt!!
        val headMaterial = Identifier(nbt.getCompound("sf_head").getString("material")).path
        val headType = Identifier(nbt.getCompound("sf_head").getString("type")).path
        val bindingMaterial = Identifier(nbt.getCompound("sf_binding").getString("material")).path
        val bindingType = Identifier(nbt.getCompound("sf_binding").getString("type")).path
        val handleMaterial = Identifier(nbt.getCompound("sf_handle").getString("material")).path
        val handleType = Identifier(nbt.getCompound("sf_handle").getString("type")).path
        val toolType = ToolTypes.TOOL_TYPES_REGISTRY[Identifier(nbt.getString("sf_tool_type"))]!!
        val hc = toolType.hcAttack != null
        val dc = toolType.dcAttack != null

        //tooltip.add(new TranslatableText("item.soulforged.tool.type." + type + ".desc").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(
            TranslatableText(
                "item.soulforged.part.$headType",
                TranslatableText("item.soulforged.tool.material.$headMaterial")
            )
                .append(" + ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.part.$bindingType",
                        TranslatableText("item.soulforged.tool.material.$bindingMaterial")
                    )
                )
                .append(" + ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.part.$handleType",
                        TranslatableText("item.soulforged.tool.material.$handleMaterial")
                    )
                )
                .formatted(Formatting.GOLD)
        )
        tooltip.add(
            LiteralText("")
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.weight",
                        (ToolCalculations.getWeight(stack) * 100.0).roundToInt() / 100.0
                    ).formatted(
                        Formatting.BLUE, Formatting.BOLD
                    )
                )
                .append(" / ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.speed",
                        (1 / ToolCalculations.calcAttackSpeed(stack) * 100.0).roundToInt() / 100.0
                    ).formatted(
                        Formatting.GREEN, Formatting.BOLD
                    )
                )
                .append(" / ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.attack",
                        (ToolCalculations.calcAttackDamage(stack, 0, null, null, 1.0f) * 100.0).roundToInt() / 100.0
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
        val nbt = stack.nbt!!
        val type = ToolTypes.TOOL_TYPES_REGISTRY[Identifier(nbt.getString("sf_tool_type"))]
        val mat = Materials.MATERIAL_REGISTRY[Identifier(
            nbt.getCompound("sf_head").getString("material")
        )]
        val matLocalized = TranslatableText(
            "item.soulforged.tool.material." + Materials.MATERIAL_REGISTRY.getId(mat)?.path
        )
        return TranslatableText(
            "item.soulforged.tool.type." +
                ToolTypes.TOOL_TYPES_REGISTRY.getId(
                    type)?.path, matLocalized
        )
    }

    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean {
        return false
    }
}