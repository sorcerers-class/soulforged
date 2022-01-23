package online.maestoso.soulforged.client.render;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;

import net.minecraft.block.BlockState;

import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NbtCompound;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.minecraft.world.BlockRenderView;

import online.maestoso.soulforged.Soulforged;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgedToolItemModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private static final Identifier ITEM_HANDHELD_MODEL = new Identifier("minecraft:item/handheld");
    private final ModelProviderContext context;
    private ModelTransformation transformation;
    private ItemStack stack;

    public ForgedToolItemModel(ModelProviderContext context) {
        this.context = context;
    }
    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        NbtCompound nbt = stack.getNbt();

        assert nbt != null;
        String type = new Identifier(nbt.getString("sf_tool_type")).getPath();

        String head_mat = new Identifier(nbt.getCompound("sf_head").getString("material")).getPath();
        String binding_mat = new Identifier(nbt.getCompound("sf_binding").getString("material")).getPath();
        String handle_mat = new Identifier(nbt.getCompound("sf_handle").getString("material")).getPath();

        FabricBakedModel head = (FabricBakedModel)this.context.loadModel(new Identifier("soulforged", String.format("item/part/%s/head/%s", type, head_mat)));
        FabricBakedModel binding = (FabricBakedModel)this.context.loadModel(new Identifier("soulforged", String.format("item/part/%s/head/%s", type, binding_mat)));
        FabricBakedModel handle = (FabricBakedModel)this.context.loadModel(new Identifier("soulforged", String.format("item/part/%s/head/%s", type, handle_mat)));
        System.out.println("Attempting to emit quads for ForgedToolItemModel");
        head.emitItemQuads(stack, randomSupplier, context);
        binding.emitItemQuads(stack, randomSupplier, context);
        handle.emitItemQuads(stack, randomSupplier, context);
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        Soulforged.LOGGER.info("Attempting to bake ForgedToolItemModel");
        return null;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of(ITEM_HANDHELD_MODEL);
    }
    // Below is all the stuff I don't care about. It's down here because I don't care about it.
    @Override public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
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
