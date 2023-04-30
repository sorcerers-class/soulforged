package studio.soulforged.soulforged.command

import net.minecraft.command.argument.SingletonArgumentInfo
import net.minecraft.util.Identifier
import org.quiltmc.qsl.command.api.CommandRegistrationCallback
import org.quiltmc.qsl.command.api.ServerArgumentType

object Commands {
    fun register() {
        ServerArgumentType.register(Identifier("soulforged:tool_type"), ToolTypeArgumentType::class.java,
            SingletonArgumentInfo.contextFree { ToolTypeArgumentType() }) { ToolTypeArgumentType() }
        ServerArgumentType.register(Identifier("soulforged:material"), MaterialArgumentType::class.java,
            SingletonArgumentInfo.contextFree { MaterialArgumentType() }) { MaterialArgumentType() }
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
            GiveToolCommand.register(dispatcher)
        })
    }
}