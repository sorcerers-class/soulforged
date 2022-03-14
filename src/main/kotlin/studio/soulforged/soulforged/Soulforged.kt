package studio.soulforged.soulforged

import studio.soulforged.soulforged.recipe.RecipeTables.initRecipeTables
import studio.soulforged.soulforged.network.NetworkReceivers.register
import studio.soulforged.soulforged.command.Commands.register
import net.fabricmc.api.ModInitializer
import studio.soulforged.soulforged.command.Commands
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.recipe.RecipeTables
import studio.soulforged.soulforged.network.NetworkReceivers
import org.apache.logging.log4j.LogManager

class Soulforged : ModInitializer {
    override fun onInitialize() {
        SoulforgedItems.TOOL.asItem()
        initRecipeTables()
        NetworkReceivers.register()
        Commands.register()
    }

    companion object {
        val LOGGER = LogManager.getLogger("Soulforged")
    }
}