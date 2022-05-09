package studio.soulforged.soulforged

import studio.soulforged.soulforged.recipe.RecipeTables.initRecipeTables
import studio.soulforged.soulforged.command.Commands
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.network.NetworkReceivers
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import net.fabricmc.loader.api.ModContainer
import net.fabricmc.api.ModInitializer
import studio.soulforged.soulforged.sound.SoulforgedSoundEvents

class Soulforged : ModInitializer {
    override fun onInitialize() {
        LOGGER.info("Soulforged version 0.3.0 start!")
        SoulforgedItems.TOOL.asItem()
        initRecipeTables()
        NetworkReceivers.register()
        Commands.register()
        SoulforgedSoundEvents.register()
    }

    companion object {
        val LOGGER: Logger = LogManager.getLogger("Soulforged")
    }
}