package online.maestoso.soulforged.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import online.maestoso.soulforged.Soulforged;
import online.maestoso.soulforged.item.SoulforgedItems;
import online.maestoso.soulforged.recipe.RecipeTables;
import org.apache.commons.lang3.tuple.Triple;
import static net.minecraft.server.command.CommandManager.*;
import java.util.Collection;

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
    public static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Identifier type, Identifier head, Identifier binding, Identifier handle) throws CommandSyntaxException {
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
            ItemEntity drop;
            NbtCompound nbt = new NbtCompound();
            ItemStack stack = new ItemStack(SoulforgedItems.TOOL);
            stack.getOrCreateSubNbt("sf_head");
            stack.getOrCreateSubNbt("sf_binding");
            stack.getOrCreateSubNbt("sf_handle");

            nbt.putString("sf_tool_type", tool_type);

            NbtCompound head_nbt = nbt.getCompound("sf_head");
            head_nbt.putString("material", head_material);
            head_nbt.putString("type", head_part);

            NbtCompound binding_nbt = nbt.getCompound("sf_binding");
            binding_nbt.putString("material", binding_material);
            binding_nbt.putString("type", binding_part);

            NbtCompound handle_nbt = nbt.getCompound("sf_handle");
            handle_nbt.putString("material", handle_material);
            handle_nbt.putString("type", handle_part);

            Soulforged.LOGGER.info("NBT: {}", nbt);
            stack.setNbt(nbt);

            drop = target.dropItem(stack, false);
            assert drop != null;
            drop.resetPickupDelay();
            drop.setOwner(target.getUuid());
            drop.setDespawnImmediately();
            source.sendFeedback(new TranslatableText("commands.give.success.single", 1, stack.toHoverableText(), targets.iterator().next().getDisplayName()), true);
        }
        return 1;
    }
}
