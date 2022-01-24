package online.maestoso.soulforged.client;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;

import net.minecraft.client.render.model.UnbakedModel;

import net.minecraft.util.Identifier;

import online.maestoso.soulforged.Soulforged;

import online.maestoso.soulforged.client.render.ForgedToolItemModel;

import org.jetbrains.annotations.Nullable;

public class SoulforgedModelProvider implements ModelResourceProvider {
    public static final Identifier FORGED_TOOL_MODEL = new Identifier("soulforged:item/tool");
    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) {
        if(resourceId.equals(FORGED_TOOL_MODEL)) {
            Soulforged.LOGGER.info("Attempting to load model for soulforged:item/tool");
            return new ForgedToolItemModel();
        } else return null;
    }
}
