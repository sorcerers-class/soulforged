package studio.soulforged.soulforged.client

import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.util.Identifier
import studio.soulforged.soulforged.Soulforged.id
import studio.soulforged.soulforged.client.render.ForgedToolItemModel

class SoulforgedModelProvider : ModelResourceProvider {
    override fun loadModelResource(resourceId: Identifier, context: ModelProviderContext): UnbakedModel? {
        return if (resourceId == FORGED_TOOL_MODEL || resourceId == BROKEN_FORGED_TOOL_MODEL) {
            SoulforgedClient.LOGGER.info("Attempting to load model for soulforged:item/tool")
            ForgedToolItemModel()
        } else null
    }

    companion object {
        val FORGED_TOOL_MODEL = "item/tool".id()
        val BROKEN_FORGED_TOOL_MODEL = "item/broken_tool".id()
    }
}