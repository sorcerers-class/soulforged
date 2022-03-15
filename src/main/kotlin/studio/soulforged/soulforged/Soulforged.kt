package studio.soulforged.soulforged

import studio.soulforged.soulforged.recipe.RecipeTables.initRecipeTables
import net.fabricmc.api.ModInitializer
import studio.soulforged.soulforged.command.Commands
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.network.NetworkReceivers
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Soulforged : ModInitializer {
    override fun onInitialize() {
        SoulforgedItems.TOOL.asItem()
        initRecipeTables()
        NetworkReceivers.register()
        Commands.register()
    }

    companion object {
        val LOGGER: Logger = LogManager.getLogger("Soulforged")
    }
}