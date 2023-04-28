package studio.soulforged.soulforged.command

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.brigadier.*
import studio.soulforged.soulforged.material.Materials
import java.util.concurrent.CompletableFuture

class MaterialArgumentType : ArgumentType<Materials.Material> {
    override fun parse(reader: StringReader?): Materials.Material {
        return Materials.MATERIALS[Identifier.fromCommandInput(reader)] ?: throw CommandSyntaxException(
            CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect(), {""})
    }

    override fun <S> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        for(material in Materials.MATERIALS.keys) {
            builder.suggest(material.toString())
        }
        return builder.buildFuture()
    }

}
object MaterialArgumentDescriptor : ArgumentDescriptor<MaterialArgumentType>
@BrigadierDsl
public fun<S> toolMaterial(name: String) : RequiredArgumentConstructor<S, MaterialArgumentDescriptor> {
    return argument(name, MaterialArgumentType(), MaterialArgumentDescriptor)
}
@JvmName("toolMaterialArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, MaterialArgumentDescriptor>.value(): Materials.Material {
    return Materials.MATERIALS[IdentifierArgumentType.getIdentifier(context, name)] ?: throw CommandSyntaxException(
        CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect(), {""})
}