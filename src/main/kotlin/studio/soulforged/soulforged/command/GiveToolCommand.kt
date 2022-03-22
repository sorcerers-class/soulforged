package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.command.CommandManager
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.IdentifierArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.network.ServerPlayerEntity
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.recipe.RecipeTables
import net.minecraft.item.ItemStack
import studio.soulforged.soulforged.item.SoulforgedItems
import net.minecraft.text.TranslatableText
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.tool.ToolCalculations
import studio.soulforged.soulforged.item.tool.ToolItem

object GiveToolCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>) {
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
            val toolType = type.toString()
            val headMaterial = head.toString()
            val bindingMaterial = binding.toString()
            val handleMaterial = handle.toString()
            Soulforged.LOGGER.info(
                "Tool type: {}, head material: {}, binding material: {}, handle material: {}",
                toolType,
                headMaterial,
                bindingMaterial,
                handleMaterial
            )
            val toolRecipes = RecipeTables.TOOL_RECIPES[toolType]!!
            val headPart = toolRecipes.left
            val bindingPart = toolRecipes.middle
            val handlePart = toolRecipes.right
            Soulforged.LOGGER.info(
                "Head part: {}, Binding part: {}, Handle part: {}",
                headPart,
                bindingPart,
                handlePart
            )
            val stack = ItemStack(SoulforgedItems.TOOL)
            assert(stack.nbt != null)
            stack.nbt!!.putString("sf_tool_type", toolType)
            stack.getOrCreateSubNbt("sf_head").putString("material", headMaterial)
            stack.getSubNbt("sf_head")?.putString("type", headPart)
            stack.getOrCreateSubNbt("sf_binding").putString("material", bindingMaterial)
            stack.getSubNbt("sf_binding")?.putString("type", bindingPart)
            stack.getOrCreateSubNbt("sf_handle").putString("material", handleMaterial)
            stack.getSubNbt("sf_handle")?.putString("type", handlePart)
            ToolCalculations.calcAttackSpeed(stack)
            ToolCalculations.calcAttackDamage(stack, 0, null, null, 1.0f)
            ToolItem.calcDurability(stack)
            target.giveItemStack(stack)
            source.sendFeedback(
                TranslatableText(
                    "commands.give.success.single",
                    1,
                    if (stack.hasCustomName()) stack.name else LiteralText("Forged Tool"),
                    targets.iterator().next().displayName
                ), true
            )
        }
        return targets.size
    }
}