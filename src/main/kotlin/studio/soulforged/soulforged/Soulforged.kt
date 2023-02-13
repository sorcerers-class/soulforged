package studio.soulforged.soulforged

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import studio.soulforged.soulforged.block.SoulforgedBlocks
import studio.soulforged.soulforged.block.entity.SoulforgedBlockEntityTypes
import studio.soulforged.soulforged.command.Commands
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.item.tool.part.ToolParts
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.network.NetworkReceivers
import studio.soulforged.soulforged.sound.SoulforgedSoundEvents

class Soulforged : ModInitializer {
    override fun onInitialize(mod: ModContainer) {
        LOGGER.info("Soulforged version {} start!", mod.metadata().version().raw())
        Materials.MATERIAL_REGISTRY
        ToolTypes.TOOL_TYPES_REGISTRY
        ToolParts.TOOL_PARTS_REGISTRY
        SoulforgedBlocks.init()
        SoulforgedBlockEntityTypes.init()
        SoulforgedItems.init()
        NetworkReceivers.register()
        Commands.register()
        SoulforgedSoundEvents.register()
    }

    companion object {
        val LOGGER: Logger = LogManager.getLogger("Soulforged")
    }
}