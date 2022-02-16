package online.maestoso.soulforged.client.render;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
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

import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureStitcher;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;

import net.minecraft.item.ItemStack;

import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.minecraft.world.BlockRenderView;

import online.maestoso.soulforged.Soulforged;

import online.maestoso.soulforged.item.SoulforgedItems;
import online.maestoso.soulforged.item.tool.ForgedToolType;
import online.maestoso.soulforged.item.tool.ForgedToolTypes;
import online.maestoso.soulforged.material.SmithingMaterial;
import online.maestoso.soulforged.material.SmithingMaterials;

import online.maestoso.soulforged.mixin.client.ItemModelGeneratorInvoker;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgedToolItemModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private static final Identifier ITEM_HANDHELD_MODEL = new Identifier("minecraft:item/handheld");
    private static final HashMap<String, BakedModel> PART_MODELS = new HashMap<>();
    private static final HashMap<String, Sprite> SPRITES = new HashMap<>();
    private final HashMap<String, Mesh> meshes = new HashMap<>();
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

        //if(meshes.containsKey(head_name)) context.meshConsumer().accept(meshes.get(head_name));
        //if(meshes.containsKey(binding_name))context.meshConsumer().accept(meshes.get(binding_name));
       // if(meshes.containsKey(handle_name))context.meshConsumer().accept(meshes.get(handle_name));
        context.fallbackConsumer().accept(PART_MODELS.get(head_name));
        context.fallbackConsumer().accept(PART_MODELS.get(binding_name));
        context.fallbackConsumer().accept(PART_MODELS.get(handle_name));
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        JsonUnbakedModel defaultItemModel = (JsonUnbakedModel) loader.getOrLoadModel(ITEM_HANDHELD_MODEL);
        transformation = defaultItemModel.getTransformations();
        for (String mat : SmithingMaterials.SMITHING_MATERIALS_REGISTRY.getIds().stream().map(Identifier::getPath).toList()) {
            for(String type : ForgedToolTypes.TOOL_TYPES_REGISTRY.getIds().stream().map(Identifier::getPath).toList()) {
                for(ModelToolParts part : ModelToolParts.values()) {
                    String id = modelNameConcatenation(mat, type, part);

                    SpriteIdentifier sprite_id = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("soulforged:item/" + id));
                    Sprite sprite = textureGetter.apply(sprite_id);

                    SPRITES.put(id, sprite);


                    PART_MODELS.put(id, new JsonUnbakedModel(new Identifier("item/generated"),  ((ItemModelGeneratorInvoker)new ItemModelGenerator()).callAddLayerElements(0, "layer0", sprite), Map.of("layer0", Either.left(sprite_id)), false, null, transformation, List.of()).bake(loader, textureGetter, rotationContainer, modelId));
                    /*Renderer renderer = RendererAccess.INSTANCE.getRenderer();
                    assert renderer != null;
                    MeshBuilder builder = renderer.meshBuilder();
                    QuadEmitter emitter = builder.getEmitter();
                    
                    emitter.square(Direction.SOUTH, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
                    emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
                    emitter.spriteColor(0, -1, -1, -1, -1);
                    emitter.emit();
                    meshes.put(id, builder.build());*/
                }
            }
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
        Vector<SpriteIdentifier> ids = new Vector<>();
        for (SmithingMaterial mat : SmithingMaterials.SMITHING_MATERIALS_REGISTRY.stream().toList()) {
            for(ForgedToolType type : ForgedToolTypes.TOOL_TYPES_REGISTRY.stream().toList()) {
                String mat_name = SmithingMaterials.SMITHING_MATERIALS_REGISTRY.getId(mat).getPath();
                String type_name = ForgedToolTypes.TOOL_TYPES_REGISTRY.getId(type).getPath();
                SpriteIdentifier head_id = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("soulforged:item/" + mat_name + "_" + type_name + "_head"));
                SpriteIdentifier binding_id = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("soulforged:item/" + mat_name + "_" + type_name + "_binding"));
                SpriteIdentifier handle_id = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("soulforged:item/" + mat_name + "_" + type_name + "_handle"));
                ids.add(head_id);
                ids.add(binding_id);
                ids.add(handle_id);
            }
        }
        return ids;
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
