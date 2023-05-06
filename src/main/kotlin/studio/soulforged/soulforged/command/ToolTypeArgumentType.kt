package studio.soulforged.soulforged.command

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.server.command.ServerCommandSource
import org.quiltmc.qkl.library.brigadier.*
import studio.soulforged.soulforged.item.tool.ToolType
import studio.soulforged.soulforged.item.tool.ToolTypes
import java.util.concurrent.CompletableFuture

class ToolTypeArgumentType : IdentifierArgumentType() {

    override fun <S> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        for(type in ToolTypes.TOOL_TYPES.keys) {
            builder.suggest(type.toString())
        }
        return builder.buildFuture()
    }

}
object ToolTypeArgumentDescriptor : ArgumentDescriptor<ToolTypeArgumentType>
@BrigadierDsl
fun<S> toolType(name: String) : RequiredArgumentConstructor<S, ToolTypeArgumentDescriptor> {
    return argument(name, ToolTypeArgumentType(), ToolTypeArgumentDescriptor)
}
@JvmName("toolTypeArg")
@BrigadierDsl
fun ArgumentReader<ServerCommandSource, ToolTypeArgumentDescriptor>.value(): ToolType {
    return ToolTypes.TOOL_TYPES[IdentifierArgumentType.getIdentifier(context, name)] ?: throw CommandSyntaxException(
        CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect(), {""})
}