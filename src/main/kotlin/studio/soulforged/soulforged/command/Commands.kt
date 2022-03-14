package studio.soulforged.soulforged.command

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource
import studio.soulforged.soulforged.command.GiveToolCommand

object Commands {
    @JvmStatic
    fun register() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>?, dedicated: Boolean ->
            if (dispatcher != null) {
                GiveToolCommand.register(
                    dispatcher
                )
            }
        })
    }
}