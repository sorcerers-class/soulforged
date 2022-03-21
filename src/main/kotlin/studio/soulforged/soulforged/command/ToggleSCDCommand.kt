package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.LiteralText
import studio.soulforged.soulforgedcombatdebugger.gui.ImGuiRenderer

object ToggleSCDCommand {

    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>) {
        dispatcher.register(
            CommandManager.literal("togglescd")
                .requires { source: ServerCommandSource -> source.hasPermissionLevel(2) }
                .executes{ context: CommandContext<ServerCommandSource> -> execute(context.source)}
        )

    }
    private fun execute(source: ServerCommandSource): Int {
        ImGuiRenderer.SCD_ENABLED = ImGuiRenderer.SCD_ENABLED xor true
        source.sendFeedback(
            LiteralText("SoulforgedCombatDebugger set to ${ImGuiRenderer.SCD_ENABLED}"), true
        )
        return if(ImGuiRenderer.SCD_ENABLED) 1 else 0
    }
}