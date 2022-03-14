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
import studio.soulforged.soulforged.item.tool.part.ToolParts
import net.minecraft.util.math.Vec3d
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI
import studio.soulforged.soulforged.sound.SoulforgedSoundEvents
import net.minecraft.sound.SoundCategory
import net.minecraft.text.Text
import net.minecraft.util.*
import org.jetbrains.annotations.Contract
import studio.soulforged.soulforged.item.tool.combat.WeaponCategories
import java.util.*

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

    fun breakTool(stack: ItemStack?, user: PlayerEntity) {
        user.sendToolBreakStatus(Hand.MAIN_HAND)
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        if (Arrays.stream(getDurabilities(stack)).anyMatch { i: Int -> i < 0 }) breakTool(
            stack,
            attacker as PlayerEntity
        )
        if (!stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .containsKey(EntityAttributes.GENERIC_ATTACK_SPEED)
        ) stack.addAttributeModifier(
            EntityAttributes.GENERIC_ATTACK_SPEED,
            EntityAttributeModifier(
                "f106b032-3216-4ff6-9919-36cf09d350f5",
                calcAttackSpeed(stack) - 4,
                EntityAttributeModifier.Operation.ADDITION
            ),
            EquipmentSlot.MAINHAND
        )
        stack.damage = calcDurability(stack)
        return true
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        assert(stack.nbt != null)
        if (Arrays.stream(getDurabilities(stack)).anyMatch { i: Int -> i == 0 }) return 0.0f
        val msp: MiningSpeedProcessor = (ToolTypes.TOOL_TYPES_REGISTRY[Identifier(
            stack.nbt!!.getString("sf_tool_type")
        )]?.miningSpeedProcessor as MiningSpeedProcessor?)!!
        return msp.getMiningSpeed(
            state,
            Materials.MATERIAL_REGISTRY[Identifier(stack.nbt!!.getCompound("sf_head").getString("material"))]
        ) as Float
    }

    override fun postMine(
        stack: ItemStack,
        world: World,
        state: BlockState,
        pos: BlockPos,
        miner: LivingEntity
    ): Boolean {
        if (Arrays.stream(getDurabilities(stack)).anyMatch { i: Int -> i < 0 }) breakTool(stack, miner as PlayerEntity)
        stack.damage = calcDurability(stack)
        return true
    }

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity): Boolean {
        return true
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
        val head_material = Identifier(nbt.getCompound("sf_head").getString("material")).path
        val head_type = Identifier(nbt.getCompound("sf_head").getString("type")).path
        val binding_material = Identifier(nbt.getCompound("sf_binding").getString("material")).path
        val binding_type = Identifier(nbt.getCompound("sf_binding").getString("type")).path
        val handle_material = Identifier(nbt.getCompound("sf_handle").getString("material")).path
        val handle_type = Identifier(nbt.getCompound("sf_handle").getString("type")).path
        val tool_type = ToolTypes.TOOL_TYPES_REGISTRY[Identifier(nbt.getString("sf_tool_type"))]!!
        val hc = tool_type.hcAttack != null
        val dc = tool_type.dcAttack != null

        //tooltip.add(new TranslatableText("item.soulforged.tool.type." + type + ".desc").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(
            TranslatableText(
                "item.soulforged.part.$head_type",
                TranslatableText("item.soulforged.tool.material.$head_material")
            )
                .append(" + ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.part.$binding_type",
                        TranslatableText("item.soulforged.tool.material.$binding_material")
                    )
                )
                .append(" + ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.part.$handle_type",
                        TranslatableText("item.soulforged.tool.material.$handle_material")
                    )
                )
                .formatted(Formatting.GOLD)
        )
        tooltip.add(
            LiteralText("")
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.weight",
                        Math.round(getWeight(stack) * 100.0) / 100.0
                    ).formatted(
                        Formatting.BLUE, Formatting.BOLD
                    )
                )
                .append(" / ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.speed",
                        Math.round(1 / calcAttackSpeed(stack) * 100.0) / 100.0
                    ).formatted(
                        Formatting.GREEN, Formatting.BOLD
                    )
                )
                .append(" / ").formatted(Formatting.RESET)
                .append(
                    TranslatableText(
                        "item.soulforged.tool.tooltip.attack",
                        Math.round(calcDamage(stack, 0, null, null, null) * 100.0) / 100.0
                    ).formatted(
                        Formatting.RED, Formatting.BOLD
                    )
                )
        )
        tooltip.add(
            TranslatableText(
                "item.soulforged.tool.tooltip.defaultattack",
                TranslatableText(
                    "item.soulforged.tool.tooltip.attacktype." + tool_type.defaultAttack.category.name
                        .lowercase(Locale.getDefault())
                ),
                TranslatableText(
                    "item.soulforged.tool.tooltip.attackdirection." + tool_type.defaultAttack.type.name
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
                            "item.soulforged.tool.tooltip.attacktype." + tool_type.hcAttack?.category?.name
                                ?.lowercase(Locale.getDefault())
                        ),
                        TranslatableText(
                            "item.soulforged.tool.tooltip.attackdirection." + (tool_type.hcAttack?.type?.name
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
                            "item.soulforged.tool.tooltip.attacktype." + tool_type.dcAttack?.category?.name
                                ?.lowercase(Locale.getDefault())
                        ),
                        TranslatableText(
                            "item.soulforged.tool.tooltip.attackdirection." + tool_type.dcAttack?.type?.name
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

    companion object {
        const val head = 0
        const val binding = 1
        const val handle = 2
        fun calcAttackSpeed(stack: ItemStack): Double {
            assert(stack.nbt != null)
            return 1 / (getWeight(stack) / 800 /
                ToolTypes.TOOL_TYPES_REGISTRY[Identifier(
                    stack.nbt!!.getString("sf_tool_type")
                )]?.defaultAttack?.speed!!)
        }

        fun calcDamage(
            stack: ItemStack,
            attackType: Int,
            attacker: PlayerEntity?,
            target: Entity?,
            crit: Float?
        ): Double {
            var crit = crit
            val nbt = stack.nbt
            return if (nbt != null) {
                if (Arrays.stream(getDurabilities(stack)).anyMatch { i: Int -> i == 0 }) return 0.0
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
                val head_edgeholding = mhead!!.edgeholding
                val head_hardness = mhead.hardness
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
                val piercing_damage: Double = ap.piercingDamage
                val total_piercing_damage = (head_edgeholding + head_hardness * 0.75) / 2 * piercing_damage
                assert(head != null)
                val head_weight: Double = head?.weight!! * mhead.density
                val binding_weight: Double =
                    binding?.weight!! * mbinding?.density!!
                val handle_weight: Double =
                    handle?.weight!! * mhandle?.density!!
                val effective_weight = head_weight + binding_weight + 0.25 * handle_weight
                val total_blunt_damage: Double =
                    (effective_weight / 100 + head_hardness * 0.25) * ap.bluntDamage * 0.8
                if (crit == null) crit = 0.0f
                (total_blunt_damage + total_piercing_damage) * crit
            } else 1.0
        }

        fun calcDurability(stack: ItemStack): Int {
            val nbt = stack.nbt!!
            if (!nbt.contains("sf_damage")) {
                val head_dura = (Objects.requireNonNull(
                    ToolParts.TOOL_PARTS_REGISTRY[Identifier(
                        nbt.getCompound("sf_head").getString("type")
                    )]
                )?.durability!! *
                        Materials.MATERIAL_REGISTRY[Identifier(
                            nbt.getCompound("sf_head").getString("material")
                        )]?.durability!!)
                val binding_dura = (
                    ToolParts.TOOL_PARTS_REGISTRY[Identifier(
                        nbt.getCompound("sf_binding").getString("type")
                    )]
                ?.durability!! *
                    Materials.MATERIAL_REGISTRY[Identifier(
                        nbt.getCompound("sf_binding").getString("material")
                    )]?.durability!!)
                val handle_dura = (
                    ToolParts.TOOL_PARTS_REGISTRY[Identifier(
                        nbt.getCompound("sf_handle").getString("type")
                    )]?.durability!! *
                    Materials.MATERIAL_REGISTRY[Identifier(
                        nbt.getCompound("sf_handle").getString("material")
                    )]?.durability!!)
                nbt.getCompound("sf_head").putInt("max_damage", head_dura.toInt())
                nbt.getCompound("sf_binding").putInt("max_damage", binding_dura.toInt())
                nbt.getCompound("sf_handle").putInt("max_damage", handle_dura.toInt())
                nbt.getCompound("sf_head").putInt("damage", head_dura.toInt())
                nbt.getCompound("sf_binding").putInt("damage", binding_dura.toInt())
                nbt.getCompound("sf_handle").putInt("damage", handle_dura.toInt())
                nbt.putBoolean("sf_damage", true)
            }
            val durability = getDurabilities(stack)
            val max_durability = getMaxDurabilities(stack)
            nbt.getCompound("sf_head").putInt("damage", durability[head] - 1)
            val head_damage = (durability[head].toFloat() / max_durability[head].toFloat() * 256).toInt()
            nbt.getCompound("sf_binding").putInt("damage", durability[binding] - 1)
            val binding_damage = (durability[binding].toFloat() / max_durability[binding].toFloat() * 256).toInt()
            nbt.getCompound("sf_handle").putInt("damage", durability[handle] - 1)
            val handle_damage = (durability[handle].toFloat() / max_durability[handle].toFloat() * 256).toInt()
            return 256 - Math.min(Math.min(handle_damage, binding_damage), head_damage)
        }

        @JvmStatic
        @Contract("_ -> new")
        fun getDurabilities(stack: ItemStack): IntArray {
            assert(stack.nbt != null)
            return intArrayOf(
                stack.nbt!!.getCompound("sf_head").getInt("damage"),
                stack.nbt!!.getCompound("sf_binding").getInt("damage"),
                stack.nbt!!
                    .getCompound("sf_handle").getInt("damage")
            )
        }

        @Contract("_ -> new")
        fun getMaxDurabilities(stack: ItemStack): IntArray {
            assert(stack.nbt != null)
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
    }
}