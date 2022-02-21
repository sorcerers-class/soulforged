package online.maestoso.soulforged.command;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class Commands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
            GiveToolCommand.register(dispatcher);
        }));
    }
}
