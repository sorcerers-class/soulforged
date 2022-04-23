package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.command.CommandManager
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.IdentifierArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.item.ItemStack
import studio.soulforged.soulforged.item.SoulforgedItems
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
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
                                .then(CommandManager.argument(
                                    "handle",
                                    IdentifierArgumentType.identifier()
                                )
                                    .executes { context: CommandContext<ServerCommandSource> ->
                                        execute(
                                            context.source,
                                            EntityArgumentType.getPlayers(context, "targets"),
                                            IdentifierArgumentType.getIdentifier(context, "type"),
                                            IdentifierArgumentType.getIdentifier(context, "head"),
                                            IdentifierArgumentType.getIdentifier(
                                                context,
                                                "binding"
                                            ),
                                            IdentifierArgumentType.getIdentifier(context, "handle")
                                        )
                                    }
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
        handle: Identifier
    ): Int {
        for (target in targets) {
            val stack: ItemStack = SoulforgedItems.TOOL.defaultStack
            val tool: ToolInst = ToolInst.fromRaw(stack, type, head, binding, handle)
            tool.sync(target)
            target.giveItemStack(stack)
            source.sendFeedback(
                TranslatableText(
                    "commands.give.success.single",
                    1,
                    stack.name,
                    targets.iterator().next().displayName
                ), true
            )
        }
        return targets.size
    }
}