package studio.soulforged.soulforged.command

import org.quiltmc.qsl.command.api.CommandRegistrationCallback

object Commands {
    fun register() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
            GiveToolCommand.register(dispatcher)
        })
    }
}