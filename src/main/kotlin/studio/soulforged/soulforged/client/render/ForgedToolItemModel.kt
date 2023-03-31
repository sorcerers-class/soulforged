package studio.soulforged.soulforged.client.render

import com.mojang.datafixers.util.Either
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.model.*
import net.minecraft.client.render.model.json.ItemModelGenerator
import net.minecraft.client.render.model.json.JsonUnbakedModel
import net.minecraft.client.render.model.json.ModelOverrideList
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.Sprite
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.item.ItemStack
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.random.RandomGenerator
import net.minecraft.world.BlockRenderView
import org.quiltmc.loader.api.minecraft.ClientOnly
import studio.soulforged.soulforged.client.SoulforgedClient
import studio.soulforged.soulforged.item.tool.ToolInst
import studio.soulforged.soulforged.mixin.client.ItemModelGeneratorInvoker
import java.util.function.Function
import java.util.function.Supplier

class ForgedToolItemModel : UnbakedModel, BakedModel, FabricBakedModel {
    private var transformation: ModelTransformation? = null

    enum class ModelToolParts {
        HEAD, BINDING, HANDLE
    }

    @ClientOnly
    override fun emitItemQuads(stack: ItemStack, randomSupplier: Supplier<RandomGenerator>, context: RenderContext) {
        val tool = ToolInst.ToolInstSerializer.deserialize(stack.nbt!!)
        val type = tool.type.id
        val headName = Identifier(type.namespace, "item/tools/" + type.path.toString() + "_" + ModelToolParts.HEAD.toString().lowercase())
        val bindingName = Identifier(type.namespace, "item/tools/" + type.path.toString() + "_" + ModelToolParts.BINDING.toString().lowercase())
        val handleName = Identifier(type.namespace, "item/tools/" +  type.path.toString() + "_" + ModelToolParts.HANDLE.toString().lowercase())
        val missingno = MinecraftClient.getInstance().bakedModelManager.missingModel
        //TODO recolor instead of loading model from file for all 3 of these
        context.pushTransform(tool.head.mat.transform)
        (PART_MODELS[headName] as FabricBakedModel?)!!.emitItemQuads(
            stack,
            randomSupplier,
            context
        )
        context.popTransform()
        context.pushTransform(tool.binding.mat.transform)
        (PART_MODELS[bindingName] as FabricBakedModel?)!!.emitItemQuads(
            stack,
            randomSupplier,
            context
        )
        context.popTransform()
        context.pushTransform(tool.handle.mat.transform)
        (PART_MODELS[handleName] as FabricBakedModel?)!!.emitItemQuads(
            stack,
            randomSupplier,
            context
        )
        context.popTransform()
    }

    override fun bake(
        modelBaker: ModelBaker?,
        textureGetter: Function<SpriteIdentifier, Sprite>,
        rotationContainer: ModelBakeSettings?,
        modelId: Identifier?
    ): BakedModel {
        val defaultItemModel = modelBaker?.getModel(ITEM_HANDHELD_MODEL) as JsonUnbakedModel
        transformation = defaultItemModel.transformations
        val resources = MinecraftClient.getInstance().resourceManager.findResources("textures/item/tools") { path ->
            return@findResources path.path.endsWith(".png")
        }
        SoulforgedClient.LOGGER.info("Client known tool type parts: ${resources.size}")
        for(id in resources.keys.map {id ->
            Identifier(id.namespace, id.path.substring(9 until id.path.length - 4))
        }) {
            SoulforgedClient.LOGGER.info("Adding model part $id")
                val spriteId = SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, id)
                val sprite = textureGetter.apply(spriteId)
                PART_MODELS[id] = JsonUnbakedModel(
                    ITEM_HANDHELD_MODEL,
                    (ItemModelGenerator() as ItemModelGeneratorInvoker).callAddLayerElements(0, "layer0", sprite.contents),
                    mapOf("layer0" to Either.left(spriteId)),
                    false,
                    null,
                    transformation,
                    listOf()
                ).bake(modelBaker, textureGetter, rotationContainer, modelId)
        }
        return this
    }

    override fun resolveParents(models: Function<Identifier, UnbakedModel>?) {
        models?.apply(ITEM_HANDHELD_MODEL)
    }

    // Below is all the stuff I don't care about. It's down here because I don't care about it.
    override fun getModelDependencies(): Collection<Identifier> {
        return emptyList()
    }

    override fun getQuads(state: BlockState?, face: Direction?, random: RandomGenerator?): MutableList<BakedQuad> {
        SoulforgedClient.LOGGER.fatal("Call to ForgedToolItemModel#getQuads")
        return mutableListOf()
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
        blockView: BlockRenderView?,
        state: BlockState?,
        pos: BlockPos?,
        randomSupplier: Supplier<RandomGenerator>?,
        context: RenderContext?
    ) {
        TODO("Not yet implemented")
    }

    companion object {
        private val ITEM_HANDHELD_MODEL = Identifier("minecraft:item/handheld")
        val PART_MODELS = HashMap<Identifier, BakedModel?>()
    }
}