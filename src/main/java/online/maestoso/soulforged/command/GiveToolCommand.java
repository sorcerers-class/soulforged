package online.maestoso.soulforged.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;

import net.minecraft.item.ItemStack;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import net.minecraft.util.Identifier;

import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.item.SoulforgedItems;
import online.maestoso.soulforged.item.tool.ToolItem;
import online.maestoso.soulforged.recipe.RecipeTables;

import org.apache.commons.lang3.tuple.Triple;

import static net.minecraft.server.command.CommandManager.*;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class GiveToolCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("givetool")
                    .requires(source -> source.hasPermissionLevel(2))
                .then(argument("targets", EntityArgumentType.players())
                        .then(argument("type", IdentifierArgumentType.identifier())
                                .then(argument("head", IdentifierArgumentType.identifier())
                                        .then(argument("binding", IdentifierArgumentType.identifier())
                                                .then(argument("handle", IdentifierArgumentType.identifier())
                                                        .executes(context -> GiveToolCommand.execute(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, "targets"),
                                                                IdentifierArgumentType.getIdentifier(context, "type"),
                                                                IdentifierArgumentType.getIdentifier(context, "head"),
                                                                IdentifierArgumentType.getIdentifier(context, "binding"),
                                                                IdentifierArgumentType.getIdentifier(context, "handle")
                                                        ))
                                                )
                                        )
                                )
                        )
                )
        );
    }
    public static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Identifier type, Identifier head, Identifier binding, Identifier handle) {
        for(ServerPlayerEntity target : targets) {
            String tool_type = type.toString(),
                    head_material = head.toString(),
                    binding_material = binding.toString(),
                    handle_material = handle.toString();
            Soulforged.LOGGER.info("Tool type: {}, head material: {}, binding material: {}, handle material: {}", tool_type, head_material, binding_material, handle_material);
            Triple<String, String, String> toolRecipes = RecipeTables.TOOL_RECIPES.get(tool_type);
            String head_part = toolRecipes.getLeft(),
                    binding_part = toolRecipes.getMiddle(),
                    handle_part = toolRecipes.getRight();
            Soulforged.LOGGER.info("Head part: {}, Binding part: {}, Handle part: {}", head_part, binding_part, handle_part);

            ItemStack stack = new ItemStack(SoulforgedItems.TOOL);
            assert stack.getNbt() != null;
            stack.getNbt().putString("sf_tool_type", tool_type);

            stack.getOrCreateSubNbt("sf_head").putString("material", head_material);
            Objects.requireNonNull(stack.getSubNbt("sf_head")).putString("type", head_part);
            stack.getOrCreateSubNbt("sf_binding").putString("material", binding_material);
            Objects.requireNonNull(stack.getSubNbt("sf_binding")).putString("type", binding_part);
            stack.getOrCreateSubNbt("sf_handle").putString("material", handle_material);
            Objects.requireNonNull(stack.getSubNbt("sf_handle")).putString("type", handle_part);

            ToolItem.calcAttackSpeed(stack);
            ToolItem.calcDamage(stack, 0, null, null, null);
            ToolItem.calcDurability(stack);

            target.giveItemStack(stack);
            source.sendFeedback(new TranslatableText("commands.give.success.single", 1, stack.hasCustomName() ? stack.getName() : new LiteralText("Forged Tool"), targets.iterator().next().getDisplayName()), true);
        }
        return targets.size();
    }
}
