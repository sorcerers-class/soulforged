package online.maestoso.soulforged.client.render;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;

import net.fabricmc.fabric.mixin.client.texture.MixinSpriteAtlasTexture;
import net.minecraft.block.BlockState;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
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

import online.maestoso.soulforged.item.tool.ForgedToolTypes;
import online.maestoso.soulforged.material.SmithingMaterials;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgedToolItemModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private static final Identifier ITEM_HANDHELD_MODEL = new Identifier("minecraft:item/handheld");
    private final ModelProviderContext context;
    private ModelTransformation transformation;

    public ForgedToolItemModel(ModelProviderContext context) {
        this.context = context;
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

        MinecraftClient inst = MinecraftClient.getInstance();
        FabricBakedModel head = (FabricBakedModel) inst.getBakedModelManager().getModel(new ModelIdentifier(String.format("soulforged:item/part/%s/head/%s", type, head_mat)));
        FabricBakedModel binding = (FabricBakedModel) inst.getBakedModelManager().getModel(new ModelIdentifier(String.format("soulforged:item/part/%s/head/%s", type, binding_mat)));
        FabricBakedModel handle = (FabricBakedModel) inst.getBakedModelManager().getModel(new ModelIdentifier(String.format("soulforged:item/part/%s/head/%s", type, handle_mat)));

        head.emitItemQuads(stack, randomSupplier, context);
        binding.emitItemQuads(stack, randomSupplier, context);
        handle.emitItemQuads(stack, randomSupplier, context);
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        ArrayList<SpriteIdentifier> parts = new ArrayList<>();
        ForgedToolTypes.TOOL_TYPES_REGISTRY.getIds().forEach((Identifier type) ->
                SmithingMaterials.SMITHING_MATERIALS_REGISTRY.getIds().forEach((Identifier material) -> {
                            if(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(material).canIntoTool()) {
                                //These 100% aren't block textures, I really have no clue what's going on here
                                parts.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(String.format("soulforged:item/part/%s/head/%s", type.getPath(), material.getPath()))));
                                parts.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(String.format("soulforged:item/part/%s/binding/%s", type.getPath(), material.getPath()))));
                                parts.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(String.format("soulforged:item/part/%s/handle/%s", type.getPath(), material.getPath()))));
                            }
                        }
                )
        );

        return parts;
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        Soulforged.LOGGER.info("Attempting to bake ForgedToolItemModel");
        JsonUnbakedModel defaultItemModel = (JsonUnbakedModel) loader.getOrLoadModel(ITEM_HANDHELD_MODEL);
        transformation = defaultItemModel.getTransformations();

        return this;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        ArrayList<Identifier> parts = new ArrayList<>();
        ForgedToolTypes.TOOL_TYPES_REGISTRY.getIds().forEach((Identifier type) ->
                SmithingMaterials.SMITHING_MATERIALS_REGISTRY.getIds().forEach((Identifier material) -> {
                    if(SmithingMaterials.SMITHING_MATERIALS_REGISTRY.get(material).canIntoTool()) {
                        parts.add(new Identifier(String.format("soulforged:item/part/%s/head/%s", type.getPath(), material.getPath())));
                        parts.add(new Identifier(String.format("soulforged:item/part/%s/binding/%s", type.getPath(), material.getPath())));
                        parts.add(new Identifier(String.format("soulforged:item/part/%s/handle/%s", type.getPath(), material.getPath())));
                    }
                }
                )
        );
        parts.add(ITEM_HANDHELD_MODEL);

        return parts;
    }
    // Below is all the stuff I don't care about. It's down here because I don't care about it.
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
        return null;
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
