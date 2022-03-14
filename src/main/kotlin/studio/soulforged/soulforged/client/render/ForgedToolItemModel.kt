package studio.soulforged.soulforged.client.render

import com.mojang.datafixers.util.Either
import com.mojang.datafixers.util.Pair
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.client.render.model.BakedModel
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.item.ItemStack
import net.minecraft.client.render.model.ModelLoader
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.client.render.model.ModelBakeSettings
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.item.tool.ToolTypes
import studio.soulforged.soulforged.mixin.client.ItemModelGeneratorInvoker
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.model.BakedQuad
import net.minecraft.client.render.model.json.*
import studio.soulforged.soulforged.Soulforged
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.client.texture.Sprite
import net.minecraft.util.Identifier
import net.minecraft.world.BlockRenderView
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import java.util.*
import java.util.Map
import java.util.function.Function
import java.util.function.Supplier

class ForgedToolItemModel : UnbakedModel, BakedModel, FabricBakedModel {
    private var transformation: ModelTransformation? = null

    enum class ModelToolParts {
        HEAD, BINDING, HANDLE
    }

    @Environment(EnvType.CLIENT)
    override fun emitItemQuads(stack: ItemStack, randomSupplier: Supplier<Random>, context: RenderContext) {
        val nbt = stack.nbt!!
        val type = Identifier(nbt.getString("sf_tool_type")).path
        val head_mat = Identifier(nbt.getCompound("sf_head").getString("material")).path
        val binding_mat = Identifier(nbt.getCompound("sf_binding").getString("material")).path
        val handle_mat = Identifier(nbt.getCompound("sf_handle").getString("material")).path
        val head_name = modelNameConcatenation(head_mat, type, ModelToolParts.HEAD)
        val binding_name = modelNameConcatenation(binding_mat, type, ModelToolParts.BINDING)
        val handle_name = modelNameConcatenation(handle_mat, type, ModelToolParts.HANDLE)
        val missingno = MinecraftClient.getInstance().bakedModelManager.missingModel
        (PART_MODELS.getOrDefault(head_name, missingno) as FabricBakedModel?)!!.emitItemQuads(
            stack,
            randomSupplier,
            context
        )
        (PART_MODELS.getOrDefault(binding_name, missingno) as FabricBakedModel?)!!.emitItemQuads(
            stack,
            randomSupplier,
            context
        )
        (PART_MODELS.getOrDefault(handle_name, missingno) as FabricBakedModel?)!!.emitItemQuads(
            stack,
            randomSupplier,
            context
        )
    }

    override fun bake(
        loader: ModelLoader,
        textureGetter: Function<SpriteIdentifier, Sprite>,
        rotationContainer: ModelBakeSettings,
        modelId: Identifier
    ): BakedModel? {
        val defaultItemModel = loader.getOrLoadModel(ITEM_HANDHELD_MODEL) as JsonUnbakedModel
        transformation = defaultItemModel.transformations
        for (mat in Materials.MATERIAL_REGISTRY.ids.stream().map { obj: Identifier -> obj.path }
            .toList()) {
            for (type in ToolTypes.TOOL_TYPES_REGISTRY.ids.stream().map { obj: Identifier -> obj.path }
                .toList()) {
                for (part in ModelToolParts.values()) {
                    val id = modelNameConcatenation(mat, type, part)
                    val sprite_id = SpriteIdentifier(
                        Identifier("textures/atlas/blocks.png"),
                        Identifier(
                            "soulforged:item/tools/" + type + "/" + mat + "/" + part.toString()
                                .lowercase(Locale.getDefault())
                        )
                    )
                    val sprite = textureGetter.apply(sprite_id)
                    PART_MODELS[id] = JsonUnbakedModel(
                        Identifier("item/handheld"),
                        (ItemModelGenerator() as ItemModelGeneratorInvoker).callAddLayerElements(0, "layer0", sprite),
                        Map.of("layer0", Either.left(sprite_id)),
                        false,
                        null,
                        transformation,
                        java.util.List.of()
                    ).bake(loader, textureGetter, rotationContainer, modelId)
                }
            }
        }
        return this
    }

    override fun getTextureDependencies(
        unbakedModelGetter: Function<Identifier, UnbakedModel>,
        unresolvedTextureReferences: Set<Pair<String, String>>
    ): Collection<SpriteIdentifier> {
        val ids = Vector<SpriteIdentifier>()
        for (mat in Materials.MATERIAL_REGISTRY.stream().toList()) {
            for (type in ToolTypes.TOOL_TYPES_REGISTRY.stream().toList()) {
                val mat_name = Objects.requireNonNull(Materials.MATERIAL_REGISTRY.getId(mat))?.path
                val type_name = Objects.requireNonNull(ToolTypes.TOOL_TYPES_REGISTRY.getId(type))?.path
                val head_id = SpriteIdentifier(
                    Identifier("textures/atlas/blocks.png"), Identifier(
                        "soulforged:item/tools/$type_name/$mat_name/head"
                    )
                )
                val binding_id = SpriteIdentifier(
                    Identifier("textures/atlas/blocks.png"), Identifier(
                        "soulforged:item/tools/$type_name/$mat_name/binding"
                    )
                )
                val handle_id = SpriteIdentifier(
                    Identifier("textures/atlas/blocks.png"), Identifier(
                        "soulforged:item/tools/$type_name/$mat_name/handle"
                    )
                )
                ids.add(head_id)
                ids.add(binding_id)
                ids.add(handle_id)
            }
        }
        return ids
    }

    // Below is all the stuff I don't care about. It's down here because I don't care about it.
    override fun getModelDependencies(): Collection<Identifier> {
        return emptyList()
    }

    override fun getQuads(state: BlockState?, face: Direction?, random: Random): List<BakedQuad> {
        Soulforged.LOGGER.fatal("Call to ForgedToolItemModel.getQuads")
        return java.util.List.of()
    }

    override fun useAmbientOcclusion(): Boolean {
        return false
    }

    override fun hasDepth(): Boolean {
        return false
    }

    override fun isSideLit(): Boolean {
        return false
    }

    override fun isBuiltin(): Boolean {
        return false
    }

    override fun getParticleSprite(): Sprite {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)
            .apply(Identifier("block/cobblestone"))
    }

    override fun getTransformation(): ModelTransformation {
        return transformation!!
    }

    override fun getOverrides(): ModelOverrideList {
        return ModelOverrideList.EMPTY
    }

    override fun isVanillaAdapter(): Boolean {
        return false
    }

    override fun emitBlockQuads(
        blockView: BlockRenderView,
        state: BlockState,
        pos: BlockPos,
        randomSupplier: Supplier<Random>,
        context: RenderContext
    ) {
    }

    companion object {
        private val ITEM_HANDHELD_MODEL = Identifier("minecraft:item/handheld")
        private val PART_MODELS = HashMap<String, BakedModel?>()
        fun modelNameConcatenation(material: String, type: String, part: ModelToolParts): String {
            return material + "_" + type + "_" + part.toString().lowercase(Locale.getDefault())
        }
    }
}