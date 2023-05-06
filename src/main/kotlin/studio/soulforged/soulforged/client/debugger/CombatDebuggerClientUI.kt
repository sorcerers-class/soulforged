package studio.soulforged.soulforged.client.debugger

import imgui.ImGui
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import org.quiltmc.loader.api.minecraft.ClientOnly
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.item.tool.combat.AttackQueue
import studio.soulforged.soulforged.item.tool.combat.AttackQueueHolder
import studio.soulforged.soulforged.item.tool.combat.AttackTypes
import studio.soulforged.soulforged.item.tool.combat.CritDirections
import kotlin.math.abs

@ClientOnly
object CombatDebuggerClientUI {
    fun render() {
        if(ImGuiRenderer.SCD_ENABLED) {
            val player = MinecraftClient.getInstance().player
            if (player != null) {
                val stack = player.mainHandStack
                if (stack.item == SoulforgedItems.TOOL) {
                    val tool = ToolInst.ToolInstSerializer.deserialize(stack.nbt!!)
                    if(tool == null) {
                        ImGui.begin("SCD v2")
                        ImGui.text("Invalid tool!")
                        ImGui.end()
                        return
                    }
                    drawCritsWindow(player, tool)
                    drawAttackPacketsWindow(player, tool)
                    drawToolProperties(player, tool)

                }
            }
        }
    }
    fun drawCritsWindow(player: ClientPlayerEntity, tool: ToolInst) {
        ImGui.begin("SCD v2 | Crits")
        val velocity = player.velocity.rotateY(Math.toRadians((player.yaw % 360.0f).toDouble()).toFloat())
            .multiply(-1.0, 1.0, 1.0)
        ImGui.text(
            """
                Cooldown: ${player.getAttackCooldownProgress(0.0f)}
                Facing: ${player.yaw % 360.0f}"
                Moving: ${player.velocity}
                Adjusted Velocity: $velocity"
                Movement: x=${abs(velocity.x) > 0.01} y=${abs(velocity.y) > 0.1} z=${abs(velocity.z) > 0.01}"
                Resultant Crit: ${CritDirections.getCritDirection(player)}
            """.trimIndent()
        )
        ImGui.end()
    }
    private var latestPacket: AttackQueue.Entry? = null
    fun drawAttackPacketsWindow(player: ClientPlayerEntity, tool: ToolInst) {
        ImGui.begin("SCD v2 | AttackPackets")
        val queue = (player as AttackQueueHolder).queue.getQueue()
        val top = queue.peek()
        if (latestPacket != top && top != null) latestPacket = top
        ImGui.text(
            """
                Queue size: ${queue.size}
                Latest Packet Clicks: ${latestPacket?.clicks}
                Time remaining: ${latestPacket?.timer}
                Latest Packet Target: ${latestPacket?.target?.uuidAsString}
                Last Damage Dealt: $damage
                Last Crit Type: $crit
                Last Attack Type: $attack
                Last Multiplier: $multiplier
            """.trimIndent()
        )
        if(ImGui.button("Flush")) {
            latestPacket = null
            damage = null
            crit = null
            attack = null
            multiplier = null
        }
        ImGui.end()
    }
    fun drawToolProperties(player: ClientPlayerEntity, tool: ToolInst) {
        ImGui.begin("SCD v2 | Tool Properties")
        val sc = tool.baseAttackDamage(tool.type.defaultAttack)
        val scm = sc * tool.type.defaultAttack.critDirection.multiplier
        val dc = tool.baseAttackDamage(tool.type.dcAttack ?: tool.type.defaultAttack)
        val dcm = dc * (tool.type.hcAttack ?: tool.type.defaultAttack).critDirection.multiplier
        val hc = tool.baseAttackDamage(tool.type.dcAttack ?: tool.type.defaultAttack)
        val hcm = hc * (tool.type.hcAttack ?: tool.type.defaultAttack).critDirection.multiplier
        ImGui.text(
            """
                SC: $sc:$scm
                DC: $dc:$dcm
                HC: $hc:$hcm
            """.trimIndent()
        )
        ImGui.end()
    }
    private var damage: Float? = null
    private var crit: CritDirections? = null
    private var attack: AttackTypes? = null
    private var multiplier: Float? = null
    fun debuggerAttackCallback(damage: Float, crit: CritDirections?, attack: AttackTypes?, multiplier: Float) {
        CombatDebuggerClientUI.damage = damage
        CombatDebuggerClientUI.crit = crit
        CombatDebuggerClientUI.attack = attack
        CombatDebuggerClientUI.multiplier = multiplier
    }
}