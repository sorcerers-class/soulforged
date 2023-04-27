package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.item.tool.ToolType
import studio.soulforged.soulforged.material.Materials
import org.quiltmc.qkl.library.brigadier.*
import org.quiltmc.qkl.library.brigadier.argument.*

object GiveToolCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register("givetool") {
            requires { source -> source.hasPermissionLevel(2) }
            required(
                players("targets"),
                toolType("type"),
                toolMaterial("head"),
                toolMaterial("binding"),
                toolMaterial("handle"),
                toolMaterial("pattern")) {players, type, head, binding, handle, pattern ->
                    execute {
                        GiveToolCommand.execute(source, players, type, head, binding, handle, pattern)
                    }
            }
        }
            CommandManager.literal("givetool")
                .requires { source: ServerCommandSource -> source.hasPermissionLevel(2) }
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                    .then(CommandManager.argument("type", ToolTypeArgumentType.toolType())
                        .then(CommandManager.argument("head", MaterialArgumentType.toolMaterial())
                            .then(CommandManager.argument("binding", MaterialArgumentType.toolMaterial())
                                .then(CommandManager.argument("handle", MaterialArgumentType.toolMaterial())
                                    .then(CommandManager.argument("pattern", MaterialArgumentType.toolMaterial())
                                        .executes { context: CommandContext<ServerCommandSource> -> 0
                                            /*execute(
                                                context.source,
                                                EntityArgumentType.getPlayers(context, "targets"),
                                                ToolTypeArgumentType.getToolType(context, "type"),
                                                IdentifierArgumentType.getIdentifier(context, "head"),
                                                IdentifierArgumentType.getIdentifier(context, "binding"),
                                                IdentifierArgumentType.getIdentifier(context, "handle"),
                                                IdentifierArgumentType.getIdentifier(context, "pattern")
                                            )*/
                                        }
                                    )
                                )
                            )
                        )
                    )
                )
        )
    }

    private fun execute(
        source: ServerCommandSource,
        targets: Collection<ServerPlayerEntity>,
        type: ToolType,
        head: Materials.Material,
        binding: Materials.Material,
        handle: Materials.Material,
        pattern: Materials.Material
    ): Int {
        for (target in targets) {
            try {
                val stack = SoulforgedItems.TOOL.defaultStack
                val tool = ToolInst(type, head, binding, handle, pattern)
                tool.setDurability(tool.head.maxDurability, tool.binding.maxDurability, tool.handle.maxDurability)
                stack.nbt = ToolInst.ToolInstSerializer.serialize(tool)
                target.giveItemStack(stack)
                source.sendFeedback(
                    Text.translatable(
                        "commands.give.success.single",
                        1,
                        SoulforgedItems.TOOL.getName(stack),
                        targets.iterator().next().displayName
                    ), true
                )
            } catch(e: Throwable) {
                Soulforged.LOGGER.error(e.stackTraceToString())
            }
        }
        return targets.size
    }
}
