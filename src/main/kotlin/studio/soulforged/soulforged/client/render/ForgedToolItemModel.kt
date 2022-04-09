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
        val headMat = Identifier(nbt.getCompound("sf_head").getString("material")).path
        val bindingMat = Identifier(nbt.getCompound("sf_binding").getString("material")).path
        val handleMat = Identifier(nbt.getCompound("sf_handle").getString("material")).path
        val headName = modelNameConcatenation(headMat, type, ModelToolParts.HEAD)
        val bindingName = modelNameConcatenation(bindingMat, type, ModelToolParts.BINDING)
        val handleName = modelNameConcatenation(handleMat, type, ModelToolParts.HANDLE)
        val missingno = MinecraftClient.getInstance().bakedModelManager.missingModel
        (PART_MODELS.getOrDefault(headName, missingno) as FabricBakedModel?)!!.emitItemQuads(
            stack,
            randomSupplier,
            context
        )
        (PART_MODELS.getOrDefault(bindingName, missingno) as FabricBakedModel?)!!.emitItemQuads(
            stack,
            randomSupplier,
            context
        )
        (PART_MODELS.getOrDefault(handleName, missingno) as FabricBakedModel?)!!.emitItemQuads(
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
    ): BakedModel {
        val defaultItemModel = loader.getOrLoadModel(ITEM_HANDHELD_MODEL) as JsonUnbakedModel
        transformation = defaultItemModel.transformations
        for (mat in Materials.MATERIAL_REGISTRY.ids.stream().map { obj: Identifier -> obj.path }
            .toList()) {
            for (type in ToolTypes.TOOL_TYPES_REGISTRY.ids.stream().map { obj: Identifier -> obj.path }
                .toList()) {
                for (part in ModelToolParts.values()) {
                    val id = modelNameConcatenation(mat, type, part)
                    val spriteId = SpriteIdentifier(
                        PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                        Identifier(
                            "soulforged:item/tools/$type/$mat/" + part.toString()
                                .lowercase(Locale.getDefault())
                        )
                    )
                    val sprite = textureGetter.apply(spriteId)
                    PART_MODELS[id] = JsonUnbakedModel(
                        ITEM_HANDHELD_MODEL,
                        (ItemModelGenerator() as ItemModelGeneratorInvoker).callAddLayerElements(0, "layer0", sprite),
                        mapOf("layer0" to Either.left(spriteId)),
                        false,
                        null,
                        transformation,
                        listOf()
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
                val matName = Objects.requireNonNull(Materials.MATERIAL_REGISTRY.getId(mat))?.path
                val typeName = Objects.requireNonNull(ToolTypes.TOOL_TYPES_REGISTRY.getId(type))?.path
                val headId = SpriteIdentifier(
                    PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                    Identifier("soulforged:item/tools/$typeName/$matName/head")
                )
                val bindingId = SpriteIdentifier(
                    PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                    Identifier("soulforged:item/tools/$typeName/$matName/binding")
                )
                val handleId = SpriteIdentifier(
                    PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                    Identifier("soulforged:item/tools/$typeName/$matName/handle")
                )
                ids.add(headId)
                ids.add(bindingId)
                ids.add(handleId)
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
        return listOf()
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

    override fun emitBlockQuads(blockView: BlockRenderView, state: BlockState, pos: BlockPos, randomSupplier: Supplier<Random>, context: RenderContext) {}

    companion object {
        private val ITEM_HANDHELD_MODEL = Identifier("minecraft:item/handheld")
        private val PART_MODELS = HashMap<String, BakedModel?>()
        fun modelNameConcatenation(material: String, type: String, part: ModelToolParts): String {
            return material + "_" + type + "_" + part.toString().lowercase(Locale.getDefault())
        }
    }
}