package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.command.CommandManager
import net.minecraft.command.EntitySelector
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.IdentifierArgumentType
import com.mojang.brigadier.context.CommandContext
import studio.soulforged.soulforged.command.GiveToolCommand
import net.minecraft.server.network.ServerPlayerEntity
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.recipe.RecipeTables
import net.minecraft.item.ItemStack
import studio.soulforged.soulforged.item.SoulforgedItems
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.TranslatableText
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.item.tool.ToolItem
import java.util.*

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

    fun execute(
        source: ServerCommandSource,
        targets: Collection<ServerPlayerEntity>,
        type: Identifier,
        head: Identifier,
        binding: Identifier,
        handle: Identifier
    ): Int {
        for (target in targets) {
            val tool_type = type.toString()
            val head_material = head.toString()
            val binding_material = binding.toString()
            val handle_material = handle.toString()
            Soulforged.LOGGER.info(
                "Tool type: {}, head material: {}, binding material: {}, handle material: {}",
                tool_type,
                head_material,
                binding_material,
                handle_material
            )
            val toolRecipes = RecipeTables.TOOL_RECIPES[tool_type]!!
            val head_part = toolRecipes.left
            val binding_part = toolRecipes.middle
            val handle_part = toolRecipes.right
            Soulforged.LOGGER.info(
                "Head part: {}, Binding part: {}, Handle part: {}",
                head_part,
                binding_part,
                handle_part
            )
            val stack = ItemStack(SoulforgedItems.TOOL)
            assert(stack.nbt != null)
            stack.nbt!!.putString("sf_tool_type", tool_type)
            stack.getOrCreateSubNbt("sf_head").putString("material", head_material)
            stack.getSubNbt("sf_head")?.putString("type", head_part)
            stack.getOrCreateSubNbt("sf_binding").putString("material", binding_material)
            stack.getSubNbt("sf_binding")?.putString("type", binding_part)
            stack.getOrCreateSubNbt("sf_handle").putString("material", handle_material)
            stack.getSubNbt("sf_handle")?.putString("type", handle_part)
            ToolItem.calcAttackSpeed(stack)
            ToolItem.calcDamage(stack, 0, null, null, null)
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