package online.maestoso.soulforged;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import online.maestoso.soulforged.command.GiveToolCommand;
import online.maestoso.soulforged.item.SoulforgedItems;

import online.maestoso.soulforged.recipe.RecipeTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Soulforged implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Soulforged");
    @Override
    public void onInitialize() {
        SoulforgedItems.TOOL.asItem();
        RecipeTables.initRecipeTables();

        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
            GiveToolCommand.register(dispatcher);
        }));
    }
}
