package studio.soulforged.soulforged

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import studio.soulforged.soulforged.command.Commands
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.network.NetworkReceivers
import studio.soulforged.soulforged.recipe.RecipeTables.initRecipeTables
import studio.soulforged.soulforged.sound.SoulforgedSoundEvents

class Soulforged : ModInitializer {
    override fun onInitialize(mod: ModContainer) {
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