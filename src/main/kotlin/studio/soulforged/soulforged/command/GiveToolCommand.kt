package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolInst

object GiveToolCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            CommandManager.literal("givetool")
                .requires { source: ServerCommandSource -> source.hasPermissionLevel(2) }
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                    .then(CommandManager.argument("type", IdentifierArgumentType.identifier())
                        .then(CommandManager.argument("head", IdentifierArgumentType.identifier())
                            .then(CommandManager.argument("binding", IdentifierArgumentType.identifier())
                                .then(CommandManager.argument("handle", IdentifierArgumentType.identifier())
                                    .then(CommandManager.argument("pattern", IdentifierArgumentType.identifier())
                                        .executes { context: CommandContext<ServerCommandSource> ->
                                            execute(
                                                context.source,
                                                EntityArgumentType.getPlayers(context, "targets"),
                                                IdentifierArgumentType.getIdentifier(context, "type"),
                                                IdentifierArgumentType.getIdentifier(context, "head"),
                                                IdentifierArgumentType.getIdentifier(context, "binding"),
                                                IdentifierArgumentType.getIdentifier(context, "handle"),
                                                IdentifierArgumentType.getIdentifier(context, "pattern")
                                            )
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
        type: Identifier,
        head: Identifier,
        binding: Identifier,
        handle: Identifier,
        pattern: Identifier
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
