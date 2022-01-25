package online.maestoso.soulforged.client.render;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;

import net.minecraft.block.BlockState;

import net.minecraft.client.MinecraftClient;

import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;

import net.minecraft.client.texture.Sprite;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NbtCompound;

import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.minecraft.world.BlockRenderView;

import online.maestoso.soulforged.Soulforged;

import online.maestoso.soulforged.item.tool.ForgedToolType;
import online.maestoso.soulforged.item.tool.ForgedToolTypes;
import online.maestoso.soulforged.material.SmithingMaterial;
import online.maestoso.soulforged.material.SmithingMaterials;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgedToolItemModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private static final Identifier ITEM_HANDHELD_MODEL = new Identifier("minecraft:item/handheld");
    private static final HashMap<String, BakedModel> PART_MODELS = new HashMap<>();
    private ModelTransformation transformation;
    private enum ModelToolParts {
        HEAD,
        BINDING,
        HANDLE
    }
    public ForgedToolItemModel() {
    }
    @Environment(EnvType.CLIENT)
    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        NbtCompound nbt = stack.getNbt();

        assert nbt != null;
        String type = new Identifier(nbt.getString("sf_tool_type")).getPath();

        String head_mat = new Identifier(nbt.getCompound("sf_head").getString("material")).getPath();
        String binding_mat = new Identifier(nbt.getCompound("sf_binding").getString("material")).getPath();
        String handle_mat = new Identifier(nbt.getCompound("sf_handle").getString("material")).getPath();

        String head = modelNameConcatenation(head_mat, type, ModelToolParts.HEAD);
        String binding = modelNameConcatenation(binding_mat, type, ModelToolParts.BINDING);
        String handle = modelNameConcatenation(handle_mat, type, ModelToolParts.HANDLE);

        context.fallbackConsumer().accept(PART_MODELS.get(head));
        context.fallbackConsumer().accept(PART_MODELS.get(binding));
        context.fallbackConsumer().accept(PART_MODELS.get(handle));
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        JsonUnbakedModel defaultItemModel = (JsonUnbakedModel) loader.getOrLoadModel(ITEM_HANDHELD_MODEL);
        transformation = defaultItemModel.getTransformations();
        defaultItemModel.bake(loader, textureGetter, rotationContainer, modelId);
        if(PART_MODELS.isEmpty()) {
            ForgedToolTypes.TOOL_TYPES_REGISTRY.forEach(type ->
                    SmithingMaterials.SMITHING_MATERIALS_REGISTRY.forEach(material -> {
                        if(material.canIntoTool()) {
                            PART_MODELS.put(getModelName(material, type, ModelToolParts.HEAD), loader.bake(new ModelIdentifier(new Identifier("soulforged", getModelName(material, type, ModelToolParts.HEAD)), "inventory"), ModelRotation.X0_Y0));
                            PART_MODELS.put(getModelName(material, type, ModelToolParts.BINDING), loader.bake(new ModelIdentifier(new Identifier("soulforged", getModelName(material, type, ModelToolParts.BINDING)), "inventory"), ModelRotation.X0_Y0));
                            PART_MODELS.put(getModelName(material, type, ModelToolParts.HANDLE), loader.bake(new ModelIdentifier(new Identifier("soulforged", getModelName(material, type, ModelToolParts.HANDLE)), "inventory"), ModelRotation.X0_Y0));
                        }
                    })
            );
        }
        return this;
    }

    public static String getModelName(SmithingMaterial material, ForgedToolType type, ModelToolParts part) {
        String stype = Objects.requireNonNull(ForgedToolTypes.TOOL_TYPES_REGISTRY.getId(type)).getPath();
        String smaterial = Objects.requireNonNull(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.getId(material)).getPath();
        return modelNameConcatenation(smaterial, stype, part);
    }
    public static String modelNameConcatenation(String material, String type, ModelToolParts part) {
        return material + "_" + type + "_" + part.toString().toLowerCase();
    }

    // Below is all the stuff I don't care about. It's down here because I don't care about it.
    @Override public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }
    @Override public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }
    @Override public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        Soulforged.LOGGER.fatal("Call to ForgedToolItemModel.getQuads");
        return List.of();
    }
    @Override public boolean useAmbientOcclusion() {
        return false;
    }
    @Override public boolean hasDepth() {
        return false;
    }
    @Override public boolean isSideLit() {
        return false;
    }
    @Override public boolean isBuiltin() {
        return false;
    }
    @Override public Sprite getParticleSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(new Identifier("block/cobblestone"));
    }
    @Override public ModelTransformation getTransformation() {
        return transformation;
    }
    @Override public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
    @Override public boolean isVanillaAdapter() {
        return false;
    }
    @Override public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {

    }
}
