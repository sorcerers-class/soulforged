package studio.soulforged.soulforged.command

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource

object Commands {
    fun register() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>?, _: Boolean ->
            if (dispatcher != null) {
                GiveToolCommand.register(dispatcher)
            }
        })
    }
}