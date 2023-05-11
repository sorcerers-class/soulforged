package studio.soulforged.soulforged

import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import studio.soulforged.soulforged.block.SoulforgedBlockTags
import studio.soulforged.soulforged.block.SoulforgedBlocks
import studio.soulforged.soulforged.block.entity.SoulforgedBlockEntityTypes
import studio.soulforged.soulforged.client.gui.SoulforgedScreenHandlerTypes
import studio.soulforged.soulforged.command.Commands
import studio.soulforged.soulforged.item.SoulforgedItemGroups
import studio.soulforged.soulforged.item.SoulforgedItems
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.item.tool.combat.AttackHandlers
import studio.soulforged.soulforged.item.tool.part.ToolParts
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.network.NetworkReceivers
import studio.soulforged.soulforged.resource.callback.MiningSpeedProcessors
import studio.soulforged.soulforged.resource.callback.OnRightClickCallbacks
import studio.soulforged.soulforged.sound.SoulforgedSoundEvents
import studio.soulforged.soulforged.world.SoulforgedOres

public object Soulforged : ModInitializer {
    @get:JvmName("getLogger")
    val LOGGER: Logger = LogManager.getLogger("Soulforged")

    @JvmName("getSoulforgedId")
    internal fun String.id(): Identifier = Identifier(Soulforged.NAME, this)
    internal fun String.sid(): String = Soulforged.NAME + ":" + this
    internal val NAME = "soulforged"
    override fun onInitialize(mod: ModContainer) {
        LOGGER.info("Soulforged version {} start!", mod.metadata().version().raw())
        MiningSpeedProcessors.init()
        OnRightClickCallbacks.init()
        Materials.init()
        ToolTypes.init()
        ToolParts.init()
        AttackHandlers.init()
        SoulforgedBlocks.init()
        SoulforgedScreenHandlerTypes.init()
        SoulforgedBlockEntityTypes.init()
        SoulforgedItems.init()
        SoulforgedItemGroups.init()
        SoulforgedBlockTags.init()
        SoulforgedOres.init()
        NetworkReceivers.register()
        Commands.register()
        SoulforgedSoundEvents.init()
    }

}