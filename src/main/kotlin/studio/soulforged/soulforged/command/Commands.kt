package studio.soulforged.soulforged.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource
import org.quiltmc.qsl.command.api.CommandRegistrationCallback

object Commands {
    fun register() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, _: Boolean, _: Boolean ->
            GiveToolCommand.register(dispatcher)
        })
    }
}