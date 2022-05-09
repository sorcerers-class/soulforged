package studio.soulforged.soulforgedcombatdebugger.gui

import imgui.ImGui
import net.fabricmc.api.EnvType
import studio.soulforged.soulforged.item.tool.combat.CritTypes
import studio.soulforged.soulforged.item.SoulforgedItems
import imgui.flag.ImGuiWindowFlags
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.item.tool.combat.AttackProperties
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.item.tool.part.ToolParts
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
                ImGui.setNextWindowSizeConstraints(-1f, -1f, 500f, 500f)
                val stack = player.mainHandStack
                if (stack.item == SoulforgedItems.TOOL) {
                    ImGui.begin("SCD v1 | Current Tool", ImGuiWindowFlags.AlwaysVerticalScrollbar)
                    ImGui.text(stack.name.string)
                    assert(stack.nbt != null)
                    val nbt = stack.nbt
                    val head = nbt!!.getCompound("sf_head")
                    val binding = nbt.getCompound("sf_binding")
                    val handle = nbt.getCompound("sf_handle")
                    if (ImGui.button(String.format("Type: %s", nbt.getString("sf_tool_type")))) showToolTypeDropdown =
                        showToolTypeDropdown xor true
                    if (showToolTypeDropdown) {
                        val type = ToolTypes.TOOL_TYPES_REGISTRY[Identifier(nbt.getString("sf_tool_type"))]!!
                        val apDefault: AttackProperties = type.defaultAttack
                        ImGui.text(
                            String.format(
                                """
                                        Default Attack:
                                        \tPiercing Damage: %f
                                        \tBlunt Damage: %f
                                        \tFinesse: %f
                                        \tSpeed: %f
                                        \tPiercing: %f
                                        \tCategory: %s
                                        \tCrit Type: %s
                                        """,
                                apDefault.piercingDamage,
                                apDefault.bluntDamage,
                                apDefault.finesse,
                                apDefault.speed,
                                apDefault.piercing,
                                apDefault.category.toString().lowercase(Locale.getDefault()),
                                apDefault.type.toString().lowercase(Locale.getDefault())
                            )
                        )
                        val apDc: AttackProperties = type.dcAttack!!
                        ImGui.text(
                            java.lang.String.format(
                                """
                                        DC Attack:
                                        \tPiercing Damage: %f
                                        \tBlunt Damage: %f
                                        \tFinesse: %f
                                        \tSpeed: %f
                                        \tPiercing: %f
                                        \tCategory: %s
                                        \tCrit Type: %s
                                        """,
                                apDc.piercingDamage,
                                apDc.bluntDamage,
                                apDc.finesse, apDc.speed,
                                apDc.piercing,
                                apDc.category.toString().lowercase(Locale.getDefault()),
                                apDc.type.toString().lowercase(Locale.getDefault())
                            )
                        )
                        val apHc: AttackProperties = type.hcAttack!!
                        ImGui.text(
                            java.lang.String.format(
                                """
                                        DC Attack:
                                        \tPiercing Damage: %f
                                        \tBlunt Damage: %f
                                        \tFinesse: %f
                                        \tSpeed: %f
                                        \tPiercing: %f
                                        \tCategory: %s
                                        \tCrit Type: %s
                                        """,
                                apHc.piercingDamage,
                                apHc.bluntDamage,
                                apHc.finesse,
                                apHc.speed,
                                apHc.piercing,
                                apHc.category.toString().lowercase(Locale.getDefault()),
                                apHc.type.toString().lowercase(Locale.getDefault())
                            )
                        )
                    }
                    ImGui.text("Materials:")
                    if (ImGui.button(String.format("Head: %s", head.getString("material")))) showHeadMaterialDropdown =
                        showHeadMaterialDropdown xor true
                    if (showHeadMaterialDropdown) {
                        val headMat = Materials.MATERIAL_REGISTRY[Identifier(head.getString("material"))]!!
                        ImGui.text(
                            java.lang.String.format(
                                """
                                        \tHardness: %f
                                        \tEdgeholding: %f
                                        \tWorkability: %d
                                        \tDensity: %d
                                        \tHeat: %d
                                        \tPadding: %d
                                        \tMining Level: %d
                                        \tMining Speed: %f
                                        \tTool Material: %b
                                        \tArmor Material: %b
                                        \tCorundum Classifier: %s
                                        """,
                                headMat.hardness,
                                headMat.edgeholding,
                                headMat.workability,
                                headMat.density, headMat.heat,
                                headMat.padding,
                                headMat.miningLevel,
                                headMat.miningSpeed,
                                headMat.canIntoTool,
                                headMat.canIntoArmor,
                                headMat.classifier
                            )
                        )
                    }
                    if (ImGui.button(
                            String.format(
                                "Binding: %s",
                                binding.getString("material")
                            )
                        )
                    ) showBindingMaterialDropdown = showBindingMaterialDropdown xor true
                    if (showBindingMaterialDropdown) {
                        val bindingMat = Materials.MATERIAL_REGISTRY[Identifier(binding.getString("material"))]!!
                        ImGui.text(
                            java.lang.String.format(
                                """
                                        \tHardness: %f
                                        \tEdgeholding: %f
                                        \tWorkability: %d
                                        \tDensity: %d
                                        \tHeat: %d
                                        \tPadding: %d
                                        \tMining Level: %d
                                        \tMining Speed: %f
                                        \tTool Material: %b
                                        \tArmor Material: %b
                                        \tCorundum Classifier: %s
                                        """,
                                bindingMat.hardness,
                                bindingMat.edgeholding,
                                bindingMat.workability,
                                bindingMat.density,
                                bindingMat.heat,
                                bindingMat.padding,
                                bindingMat.miningLevel,
                                bindingMat.miningSpeed,
                                bindingMat.canIntoTool,
                                bindingMat.canIntoArmor,
                                bindingMat.classifier
                            )
                        )
                    }
                    if (ImGui.button(
                            String.format(
                                "Handle: %s",
                                handle.getString("material")
                            )
                        )
                    ) showHandleMaterialDropdown = showHandleMaterialDropdown xor true
                    if (showHandleMaterialDropdown) {
                        val handleMat = Materials.MATERIAL_REGISTRY[Identifier(handle.getString("material"))]!!
                        ImGui.text(
                            java.lang.String.format(
                                """
                                        \tHardness: %f
                                        \tEdgeholding: %f
                                        \tWorkability: %d
                                        \tDensity: %d
                                        \tHeat: %d
                                        \tPadding: %d
                                        \tMining Level: %d
                                        \tMining Speed: %f
                                        \tTool Material: %b
                                        \tArmor Material: %b
                                        \tCorundum Classifier: %s
                                        """,
                                handleMat.hardness,
                                handleMat.edgeholding,
                                handleMat.workability,
                                handleMat.density,
                                handleMat.heat,
                                handleMat.padding,
                                handleMat.miningLevel,
                                handleMat.miningSpeed,
                                handleMat.canIntoTool,
                                handleMat.canIntoArmor,
                                handleMat.classifier
                            )
                        )
                    }
                    ImGui.text("Part Types:")
                    if (ImGui.button(String.format("Head: %s", head.getString("type")))) showHeadTypeDropdown =
                        showHeadTypeDropdown xor true
                    if (showHeadTypeDropdown) {
                        val headPart = ToolParts.TOOL_PARTS_REGISTRY[Identifier(head.getString("type"))]!!
                        ImGui.text(
                            java.lang.String.format(
                                """
                                        \tWeight: %f
                                        \tDurability: %f
                                        """,
                                headPart.weight,
                                headPart.durability
                            )
                        )
                    }
                    if (ImGui.button(String.format("Binding: %s", binding.getString("type")))) showBindingTypeDropdown =
                        showBindingTypeDropdown xor true
                    if (showBindingTypeDropdown) {
                        val bindingPart = ToolParts.TOOL_PARTS_REGISTRY[Identifier(binding.getString("type"))]!!
                        ImGui.text(
                            java.lang.String.format(
                                """
                                        \tWeight: %f
                                        \tDurability: %f
                                        """,
                                bindingPart.weight,
                                bindingPart.durability
                            )
                        )
                    }
                    if (ImGui.button(String.format("Handle: %s", handle.getString("type")))) showHandleTypeDropdown =
                        showHandleTypeDropdown xor true
                    if (showHandleTypeDropdown) {
                        val part = ToolParts.TOOL_PARTS_REGISTRY[Identifier(handle.getString("type"))]!!
                        ImGui.text(
                            java.lang.String.format(
                                """
                                        \tWeight: %f
                                        \tDurability: %f
                                        """,
                                part.weight,
                                part.durability
                            )
                        )
                    }
                    ImGui.end()
                    ImGui.setNextWindowSizeConstraints(-1.0f, -1.0f, 500.0f, 500f)
                    ImGui.begin("SCD v1 | Melee", ImGuiWindowFlags.AlwaysHorizontalScrollbar)
                    ImGui.text(String.format("Attack cooldown: %f", player.getAttackCooldownProgress(0.0f)))
                    ImGui.text(
                        String.format(
                            "Cooldown ratio: %f",
                            player.getAttackCooldownProgress(0.0f).toDouble().pow(4.0)
                        )
                    )
                    if (ImGui.button("Damage calc: ")) showDamageCalcDropdown = showDamageCalcDropdown xor true
                //    if (Arrays.stream(getDurabilities(stack))
                //            .anyMatch { i: Int -> i == 0 }
                //    ) ImGui.text("Broken tool will have 0 damage!")
                    val phead =
                        ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(nbt.getCompound("sf_head").getString("type"))]
                    val pbinding = ToolParts.TOOL_PARTS_REGISTRY[Identifier.tryParse(
                        nbt.getCompound("sf_binding").getString("type")
                    )]
                    val phandle =
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
                    assert(mhead != null)
                    val headEdgeholding: Double = mhead?.edgeholding!!
                    val headHardness: Double = mhead.hardness
                    val type = ToolTypes.TOOL_TYPES_REGISTRY[Identifier.tryParse(nbt.getString("sf_tool_type"))]!!
                    var ap: AttackProperties = type.defaultAttack
                    if (showDcAttackCalc) ap = type.dcAttack ?: type.defaultAttack else if (showHcAttackCalc) ap =
                        type.hcAttack ?: type.defaultAttack
                    val piercingDamage: Double = ap.piercingDamage
                    val totalPiercingDamage = (headEdgeholding + headHardness * 0.75) / 2 * piercingDamage
                    assert(phead != null)
                    val headWeight: Double = phead?.weight!! * mhead.density
                    val bindingWeight: Double =
                        pbinding?.weight!! * mbinding?.density!!
                    val handleWeight: Double =
                        phandle?.weight!! * mhandle?.density!!
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
                            java.lang.String.format("""
                                        Total Piercing Damage:
                                        \t((Edgeholding + (Head Hardness * 0.75)) / 2) * Piercing Damage
                                        \t((%f + (%f * 0.75)) / 2) * %f = %f
                                        Total Blunt Damage:
                                        \t((((Head Weight + Binding Weight + (0.25 * Handle Weight))/100) + (Head Hardness * 0.25))*Blunt Damage Multiplier) * 0.8
                                        \t((((%f + %f + (0.25 * %f))/100) + (%f * 0.25)) * %f) * 0.8 = %f
                                        Total Damage: %f
                                        With cooldown: Total Damage * Cooldown ^ 4
                                        %f * %f^4 = %f
                                        """,
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
                            String.format("""
                                        Total Damage: %f
                                        With Cooldown: %f
                                        """,
                                totalDamage,
                                totalDamage * player.getAttackCooldownProgress(0.0f).toDouble().pow(4.0)
                            )
                        )
                    }
                    ImGui.text(String.format("Attack type: %d", attackType))
                    ImGui.end()
                    ImGui.begin("SCD v1 | Mouse")
                    ImGui.text(
                        String.format("""
                            Last Packet Action: %d
                            Packet Count: %d
                            """,
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
                        String.format("""
                            Cooldown = 1.0: %b
                            Facing: %s
                            Moving: %s
                            Adjusted Velocity: %s
                            Moving: %b %b %b
                            Resultant Crit: %s
                            Crit Direction: %s
                            """,
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