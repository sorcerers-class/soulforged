package studio.soulforged.soulforged.client.gui

import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.item.tool.combat.CritTypes
import java.util.*
import kotlin.math.abs
import kotlin.math.pow

@Environment(EnvType.CLIENT)
object CombatDebuggerClientUI {
    private var showToolTypeDropdown = false
    private var showHeadMaterialDropdown = false
    private var showBindingMaterialDropdown = false
    private var showHandleMaterialDropdown = false
    private var showHeadTypeDropdown = false
    private var showBindingTypeDropdown = false
    private var showHandleTypeDropdown = false
    private var showDamageCalcDropdown = false
    private var showDcAttackCalc = false
    private var showHcAttackCalc = false
    var lastPacket = -1
    var packetCounter = 0
    var attackType = 0
    var critType: CritTypes? = null
    fun render() {
        if(ImGuiRenderer.SCD_ENABLED) {
            val player = MinecraftClient.getInstance().player
            if (player != null) {
                val stack = player.mainHandStack
                if (stack.item == SoulforgedItems.TOOL) {
                    ImGui.begin("SCD v1 | Current Tool", ImGuiWindowFlags.AlwaysVerticalScrollbar)
                    ImGui.text(stack.name.string)
                    assert(stack.nbt != null)
                    val nbt = stack.nbt!!
                    val tool: ToolInst
                    try {
                        tool = ToolInst.ToolInstSerializer.deserialize(player.mainHandStack.nbt!!)
                    } catch(_: Exception)  {
                        ImGui.text("Malformed NBT")
                        ImGui.end()
                        return
                    }

                    if (ImGui.button(String.format("Type: %s", tool.type.name))) showToolTypeDropdown =
                        showToolTypeDropdown xor true
                    if (showToolTypeDropdown) {
                        val apDefault: AttackProperties = tool.type.defaultAttack
                        ImGui.text("Default Attack:" +
                                    "\n\tPiercing Damage:" + apDefault.piercingDamage +
                                    "\n\tBlunt Damage:" + apDefault.bluntDamage +
                                    "\n\tFinesse:" + apDefault.finesse +
                                    "\n\tSpeed:" + apDefault.speed +
                                    "\n\tPiercing:" + apDefault.piercing +
                                    "\n\tCategory:" + apDefault.category.toString().lowercase(Locale.getDefault()) +
                                    "\n\tCrit Type:" + apDefault.type.toString().lowercase(Locale.getDefault()),
                        )
                        val apDc: AttackProperties? = tool.type.dcAttack
                        if(apDc != null) {
                            ImGui.text(
                                "DC Attack:" +
                                        "\n\tPiercing Damage:" + apDc.piercingDamage +
                                        "\n\tBlunt Damage:" + apDc.bluntDamage +
                                        "\n\tFinesse:" + apDc.finesse +
                                        "\n\tSpeed:" + apDc.speed +
                                        "\n\tPiercing:" + apDc.piercing +
                                        "\n\tCategory:" + apDc.category.toString().lowercase(Locale.getDefault()) +
                                        "\n\tCrit Type:" + apDc.type.toString().lowercase(Locale.getDefault()),
                            )
                        }
                        val apHc: AttackProperties? = tool.type.hcAttack
                        if(apHc != null) {
                            ImGui.text(
                                "HC Attack:" +
                                        "\n\tPiercing Damage:" + apHc.piercingDamage +
                                        "\n\tBlunt Damage:" + apHc.bluntDamage +
                                        "\n\tFinesse:" + apHc.finesse +
                                        "\n\tSpeed:" + apHc.speed +
                                        "\n\tPiercing:" + apHc.piercing +
                                        "\n\tCategory:" + apHc.category.toString().lowercase(Locale.getDefault()) +
                                        "\n\tCrit Type:" + apHc.type.toString().lowercase(Locale.getDefault()),
                            )
                        }
                    }
                    ImGui.text("Materials:")
                    if (ImGui.button(String.format("Head: %s", tool.head.mat.name))) showHeadMaterialDropdown =
                        showHeadMaterialDropdown xor true
                    if (showHeadMaterialDropdown) {
                        val headMat = tool.head.mat
                        ImGui.text(
                                "\tHardness:" + headMat.hardness +
                                "\n\tEdgeholding:" + headMat.edgeholding +
                                "\n\tWorkability:" + headMat.workability +
                                "\n\tDensity:" + headMat.density +
                                "\n\tHeat:" + headMat.heat +
                                "\n\tPadding:" + headMat.padding +
                                "\n\tMining Level:" + headMat.miningLevel +
                                "\n\tMining Speed:" + headMat.miningSpeed +
                                "\n\tTool Material:" + headMat.canIntoTool +
                                "\n\tArmor Material:" + headMat.canIntoArmor +
                                "\n\tCorundum Classifier:" + (headMat.classifier ?: "None")
                        )
                    }
                    if (ImGui.button(
                                "Binding:" + tool.binding.mat.name
                        )
                    ) showBindingMaterialDropdown = showBindingMaterialDropdown xor true
                    if (showBindingMaterialDropdown) {
                        val bindingMat = tool.binding.mat
                        ImGui.text(
                            "\tHardness:" + bindingMat.hardness +
                                    "\n\tEdgeholding:" + bindingMat.edgeholding +
                                    "\n\tWorkability:" + bindingMat.workability +
                                    "\n\tDensity:" + bindingMat.density +
                                    "\n\tHeat:" + bindingMat.heat +
                                    "\n\tPadding:" + bindingMat.padding +
                                    "\n\tMining Level:" + bindingMat.miningLevel +
                                    "\n\tMining Speed:" + bindingMat.miningSpeed +
                                    "\n\tTool Material:" + bindingMat.canIntoTool +
                                    "\n\tArmor Material:" + bindingMat.canIntoArmor +
                                    "\n\tCorundum Classifier:" + (bindingMat.classifier ?: "None")
                        )
                    }
                    if (ImGui.button(
                            String.format(
                                "Handle: %s",
                                tool.handle.mat.name
                            )
                        )
                    ) showHandleMaterialDropdown = showHandleMaterialDropdown xor true
                    if (showHandleMaterialDropdown) {
                        val handleMat = tool.handle.mat
                        ImGui.text(
                            "\tHardness:" + handleMat.hardness +
                                    "\n\tEdgeholding:" + handleMat.edgeholding +
                                    "\n\tWorkability:" + handleMat.workability +
                                    "\n\tDensity:" + handleMat.density +
                                    "\n\tHeat:" + handleMat.heat +
                                    "\n\tPadding:" + handleMat.padding +
                                    "\n\tMining Level:" + handleMat.miningLevel +
                                    "\n\tMining Speed:" + handleMat.miningSpeed +
                                    "\n\tTool Material:" + handleMat.canIntoTool +
                                    "\n\tArmor Material:" + handleMat.canIntoArmor +
                                    "\n\tCorundum Classifier:" + (handleMat.classifier ?: "None")
                        )
                    }
                    ImGui.text("Part Types:")
                    if (ImGui.button(String.format("Head: %s", tool.head.part.name))) showHeadTypeDropdown =
                        showHeadTypeDropdown xor true
                    if (showHeadTypeDropdown) {
                        val headPart = tool.head.part
                        ImGui.text(
                            "\n\tWeight:" + headPart.weight +
                            "\n\tDurability:" + headPart.durability
                        )
                    }
                    if (ImGui.button(String.format("Binding: %s", tool.binding.part.name))) showBindingTypeDropdown =
                        showBindingTypeDropdown xor true
                    if (showBindingTypeDropdown) {
                        val bindingPart = tool.binding.part
                        ImGui.text(
                            "\n\tWeight:" + bindingPart.weight +
                                    "\n\tDurability:" + bindingPart.durability
                        )
                    }
                    if (ImGui.button(String.format("Handle: %s", tool.handle.part.name))) showHandleTypeDropdown =
                        showHandleTypeDropdown xor true
                    if (showHandleTypeDropdown) {
                        val handlePart = tool.handle.part
                        ImGui.text(
                            "\n\tWeight:" + handlePart.weight +
                                    "\n\tDurability:" + handlePart.durability
                        )
                    }
                    ImGui.end()
                    ImGui.begin("SCD v1 | Melee", ImGuiWindowFlags.AlwaysHorizontalScrollbar)
                    ImGui.text(String.format("Attack cooldown: %f", player.getAttackCooldownProgress(0.0f)))
                    ImGui.text(
                        String.format(
                            "Cooldown ratio: %f",
                            player.getAttackCooldownProgress(0.0f).toDouble().pow(4.0)
                        )
                    )
                    if (ImGui.button("Damage calc: ")) showDamageCalcDropdown = showDamageCalcDropdown xor true
                    if (tool.shouldBreak()) ImGui.text("Broken tool will have 0 damage!")
                    val headEdgeholding: Double = tool.head.mat.edgeholding
                    val headHardness: Double = tool.head.mat.hardness
                    val type = ToolTypes.TOOL_TYPES_REGISTRY[Identifier.tryParse(nbt.getString("sf_tool_type"))]
                    if(type == null) {
                        ImGui.text("Malformed NBT")
                        ImGui.end()
                        return
                    }
                    var ap: AttackProperties = type.defaultAttack
                    if (showDcAttackCalc) ap = type.dcAttack ?: type.defaultAttack else if (showHcAttackCalc) ap =
                        type.hcAttack ?: type.defaultAttack
                    val piercingDamage: Double = ap.piercingDamage
                    val totalPiercingDamage = (headEdgeholding + headHardness * 0.75) / 2 * piercingDamage
                    val headWeight: Double = tool.head.part.weight * tool.head.mat.density
                    val bindingWeight: Double =
                        tool.binding.part.weight * tool.binding.mat.density
                    val handleWeight: Double =
                        tool.handle.part.weight * tool.handle.mat.density
                    val effectiveWeight = headWeight + bindingWeight + 0.25 * handleWeight
                    val totalBluntDamage: Double =
                        (effectiveWeight / 100 + headHardness * 0.25) * ap.bluntDamage * 0.8
                    val totalDamage = totalPiercingDamage + totalBluntDamage
                    if (showDamageCalcDropdown) {
                        if (ImGui.button(if (showDcAttackCalc) "Hide DC Attack" else "Show DC Attack")) showDcAttackCalc =
                            showDcAttackCalc xor true
                        if (ImGui.button(if (showHcAttackCalc) "Hide HC Attack" else "Show HC Attack")) showHcAttackCalc =
                            showHcAttackCalc xor true
                        ImGui.text(
                            java.lang.String.format(
                                        "Total Piercing Damage:" +
                                        "\n\t((Edgeholding + (Head Hardness * 0.75)) / 2) * Piercing Damage" +
                                        "\n\t((%f + (%f * 0.75)) / 2) * %f = %f" +
                                        "\nTotal Blunt Damage:" +
                                        "\n\t((((Head Weight + Binding Weight + (0.25 * Handle Weight))/100) + (Head Hardness * 0.25))*Blunt Damage Multiplier) * 0.8" +
                                        "\n\t((((%f + %f + (0.25 * %f))/100) + (%f * 0.25)) * %f) * 0.8 = %f" +
                                        "\nTotal Damage: %f" +
                                        "\nWith cooldown: Total Damage * Cooldown ^ 4" +
                                        "\n%f * %f^4 = %f"
                                        ,
                                headEdgeholding,
                                headHardness,
                                piercingDamage,
                                totalPiercingDamage,
                                headWeight,
                                bindingWeight,
                                handleWeight,
                                headHardness,
                                ap.bluntDamage,
                                totalBluntDamage,
                                totalDamage,
                                totalDamage,
                                player.getAttackCooldownProgress(0.0f).toDouble().pow(4.0),
                                totalDamage * player.getAttackCooldownProgress(0.0f).toDouble().pow(4.0)
                            )
                        )
                    } else {
                        ImGui.text(
                            String.format(
                                "\nTotal Damage: %f" +
                                "\nWith Cooldown: %f",
                                totalDamage,
                                totalDamage * player.getAttackCooldownProgress(0.0f).toDouble().pow(4.0)
                            )
                        )
                    }
                    ImGui.text(String.format("Attack type: %d", attackType))
                    ImGui.end()
                    ImGui.begin("SCD v1 | Mouse")
                    ImGui.text(
                        String.format(
                            "\nLast Packet Action: %d" +
                            "\nPacket Count: %d",
                            lastPacket,
                            packetCounter
                        )
                    )
                    ImGui.end()
                    ImGui.begin("SCD v1 | Crits")
                    val velocity = player.velocity.rotateY(Math.toRadians((player.yaw % 360.0f).toDouble()).toFloat())
                        .multiply(-1.0, 1.0, 1.0)
                    var direction: CritTypes? = null
                    if (abs(velocity.z) > 0.1) {
                        direction = CritTypes.FORWARD
                    }
                    if (abs(velocity.y) > 0.1) {
                        direction = CritTypes.DOWN
                    }
                    if (abs(velocity.x) > 0.1) {
                        direction = CritTypes.SIDE
                    }
                    ImGui.text(
                        String.format(
                            "\nCooldown = 1.0: %b" +
                            "\nFacing: %s" +
                            "\nMoving: %s" +
                            "\nAdjusted Velocity: %s" +
                            "\nMoving: %b %b %b" +
                            "\nResultant Crit: %s" +
                            "\nCrit Direction: %s",
                            player.getAttackCooldownProgress(0.0f) == 1.0f,
                            player.yaw % 360.0f,
                            player.velocity,
                            velocity,
                            abs(velocity.x) > 0.01, abs(velocity.y) > 0.1, abs(velocity.z) > 0.01,
                            direction,
                            critType
                        )
                    )
                    ImGui.end()
                }
            }
        }
    }
}