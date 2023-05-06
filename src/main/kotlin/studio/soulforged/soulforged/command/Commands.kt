package studio.soulforged.soulforged.command

import net.minecraft.command.argument.SingletonArgumentInfo
import org.quiltmc.qsl.command.api.CommandRegistrationCallback
import org.quiltmc.qsl.command.api.ServerArgumentType
import studio.soulforged.soulforged.Soulforged.id

object Commands {
    internal fun register() {
        ServerArgumentType.register("tool_type".id(), ToolTypeArgumentType::class.java,
            SingletonArgumentInfo.contextFree { ToolTypeArgumentType() }) { ToolTypeArgumentType() }
        ServerArgumentType.register("material".id(), MaterialArgumentType::class.java,
            SingletonArgumentInfo.contextFree { MaterialArgumentType() }) { MaterialArgumentType() }
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
            GiveToolCommand.register(dispatcher)
        })
    }
}