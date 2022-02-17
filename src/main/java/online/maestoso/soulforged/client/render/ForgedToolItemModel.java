package online.maestoso.soulforged.client.render;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;

import net.minecraft.block.BlockState;

import net.minecraft.client.MinecraftClient;

import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;

import net.minecraft.client.texture.Sprite;

import net.minecraft.client.util.SpriteIdentifier;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NbtCompound;

import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.minecraft.world.BlockRenderView;

import online.maestoso.soulforged.Soulforged;

import online.maestoso.soulforged.item.tool.ToolType;
import online.maestoso.soulforged.item.tool.ForgedToolTypes;
import online.maestoso.soulforged.material.Material;
import online.maestoso.soulforged.material.Materials;

import online.maestoso.soulforged.mixin.client.ItemModelGeneratorInvoker;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("deprecated")
public class ForgedToolItemModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private static final Identifier ITEM_HANDHELD_MODEL = new Identifier("minecraft:item/handheld");
    private static final HashMap<String, BakedModel> PART_MODELS = new HashMap<>();
    private ModelTransformation transformation;


    public enum ModelToolParts {
        HEAD,
        BINDING,
        HANDLE
    }
    public ForgedToolItemModel() {}
    @Environment(EnvType.CLIENT)
    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        NbtCompound nbt = stack.getNbt();

        assert nbt != null;
        String type = new Identifier(nbt.getString("sf_tool_type")).getPath();

        String head_mat = new Identifier(nbt.getCompound("sf_head").getString("material")).getPath();
        String binding_mat = new Identifier(nbt.getCompound("sf_binding").getString("material")).getPath();
        String handle_mat = new Identifier(nbt.getCompound("sf_handle").getString("material")).getPath();

        String head_name = modelNameConcatenation(head_mat, type, ModelToolParts.HEAD);
        String binding_name = modelNameConcatenation(binding_mat, type, ModelToolParts.BINDING);
        String handle_name = modelNameConcatenation(handle_mat, type, ModelToolParts.HANDLE);
        BakedModel missingno = MinecraftClient.getInstance().getBakedModelManager().getMissingModel();
        ((FabricBakedModel)PART_MODELS.getOrDefault(head_name, missingno)).emitItemQuads(stack, randomSupplier, context);
        ((FabricBakedModel)PART_MODELS.getOrDefault(binding_name, missingno)).emitItemQuads(stack, randomSupplier, context);
        ((FabricBakedModel)PART_MODELS.getOrDefault(handle_name, missingno)).emitItemQuads(stack, randomSupplier, context);
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        JsonUnbakedModel defaultItemModel = (JsonUnbakedModel) loader.getOrLoadModel(ITEM_HANDHELD_MODEL);
        transformation = defaultItemModel.getTransformations();
        for (String mat : Materials.MATERIAL_REGISTRY.getIds().stream().map(Identifier::getPath).toList()) {
            for(String type : ForgedToolTypes.TOOL_TYPES_REGISTRY.getIds().stream().map(Identifier::getPath).toList()) {
                for(ModelToolParts part : ModelToolParts.values()) {
                    String id = modelNameConcatenation(mat, type, part);
                    SpriteIdentifier sprite_id = new SpriteIdentifier(new Identifier("textures/atlas/blocks.png"), new Identifier("soulforged:item/tools/" + type + "/" + mat + "/" + part.toString().toLowerCase()));
                    Sprite sprite = textureGetter.apply(sprite_id);
                    PART_MODELS.put(id, new JsonUnbakedModel(new Identifier("item/handheld"),  ((ItemModelGeneratorInvoker)new ItemModelGenerator()).callAddLayerElements(0, "layer0", sprite), Map.of("layer0", Either.left(sprite_id)), false, null, transformation, List.of()).bake(loader, textureGetter, rotationContainer, modelId));
                }
            }
        }
        return this;
    }

    public static String modelNameConcatenation(String material, String type, ModelToolParts part) {
        return material + "_" + type + "_" + part.toString().toLowerCase();
    }
    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        Vector<SpriteIdentifier> ids = new Vector<>();
        for (Material mat : Materials.MATERIAL_REGISTRY.stream().toList()) {
            for(ToolType type : ForgedToolTypes.TOOL_TYPES_REGISTRY.stream().toList()) {
                String mat_name = Objects.requireNonNull(Materials.MATERIAL_REGISTRY.getId(mat)).getPath();
                String type_name = Objects.requireNonNull(ForgedToolTypes.TOOL_TYPES_REGISTRY.getId(type)).getPath();
                SpriteIdentifier head_id = new SpriteIdentifier(new Identifier("textures/atlas/blocks.png"), new Identifier("soulforged:item/tools/" + type_name + "/" + mat_name + "/head"));
                SpriteIdentifier binding_id = new SpriteIdentifier(new Identifier("textures/atlas/blocks.png"), new Identifier("soulforged:item/tools/" + type_name + "/" + mat_name + "/binding"));
                SpriteIdentifier handle_id = new SpriteIdentifier(new Identifier("textures/atlas/blocks.png"), new Identifier("soulforged:item/tools/" + type_name + "/" + mat_name + "/handle"));
                ids.add(head_id);
                ids.add(binding_id);
                ids.add(handle_id);
            }
        }
        return ids;
    }
    // Below is all the stuff I don't care about. It's down here because I don't care about it.
    @Override public Collection<Identifier> getModelDependencies() {
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
