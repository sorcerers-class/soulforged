package studio.soulforged.soulforged.resource.callback

import net.minecraft.advancement.criterion.Criteria
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.Oxidizable
import net.minecraft.item.AxeItem
import net.minecraft.item.HoneycombItem
import net.minecraft.item.ItemUsageContext
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.tag.BlockTags
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldEvents
import org.quiltmc.qkl.library.registry.invoke
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.Soulforged.sid
import studio.soulforged.soulforged.block.multiblock.DeepslateForgeController
import studio.soulforged.soulforged.block.multiblock.DeepslateForgeControllerBlockEntity
import studio.soulforged.soulforged.block.multiblock.TIER
import studio.soulforged.soulforged.resource.callback.OnRightClickCallbacks.OnRightClickCallback
import studio.soulforged.soulforged.util.RegistryUtil
import java.util.*

object OnRightClickCallbacks {
    val DEFAULT: OnRightClickCallback = OnRightClickCallback { return@OnRightClickCallback ActionResult.FAIL }
    val RIGHT_CLICK_CALLBACK_REGISTRY: Registry<OnRightClickCallback> = RegistryUtil.createRegistry("right_click_callbacks".sid(), DEFAULT)
    val AXE = OnRightClickCallback { ctx ->
        val world = ctx?.world
        val blockPos = ctx?.blockPos
        val playerEntity = ctx?.player
        val blockState = world?.getBlockState(blockPos)
        val tryStripLog = AxeItem.STRIPPED_BLOCKS[blockState?.block]?.defaultState
        val tryDecreaseOxidation = Oxidizable.getDecreasedOxidationState(blockState)
        val tryUnwax = Optional.ofNullable(HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()[blockState?.block])
            .map { block: Block -> block.getStateWithProperties(blockState) }
        val itemStack = ctx?.stack
        var actionExecuted = Optional.empty<BlockState>()
        if (tryStripLog != null) {
            world?.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f)
            actionExecuted = Optional.of(tryStripLog)
        } else if (tryDecreaseOxidation.isPresent) {
            world?.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f)
            world?.syncWorldEvent(playerEntity, WorldEvents.BLOCK_SCRAPED, blockPos, 0)
            actionExecuted = tryDecreaseOxidation
        } else if (tryUnwax.isPresent) {
            world?.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0f, 1.0f)
            world?.syncWorldEvent(playerEntity, WorldEvents.WAX_REMOVED, blockPos, 0)
            actionExecuted = tryUnwax
        }
        if (actionExecuted.isPresent) {
            if (playerEntity is ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(playerEntity as ServerPlayerEntity?, blockPos, itemStack)
            }
            world?.setBlockState(blockPos, actionExecuted.get(), Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
            return@OnRightClickCallback ActionResult.success(world!!.isClient)
        }
        return@OnRightClickCallback ActionResult.PASS
    }
    val HOE = OnRightClickCallback { ctx ->
        val world = ctx?.world
        val pos = ctx?.blockPos
        val player = ctx?.player
        var state = world?.getBlockState(pos)
        if (Registries.BLOCK.get(Registries.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.DIRT)) {
            world?.setBlockState(pos, Blocks.FARMLAND.defaultState)
            world?.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f)
            state = world?.getBlockState(pos)
            if (player is ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(player as ServerPlayerEntity?, pos, ctx.stack)
            }
            world?.setBlockState(pos, state, Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
            return@OnRightClickCallback ActionResult.success(world!!.isClient)
        }
        return@OnRightClickCallback ActionResult.PASS
    }
    val SHOVEL = OnRightClickCallback { ctx ->
        val world = ctx?.world
        val pos = ctx?.blockPos
        val playerEntity = ctx?.player
        if (world?.getBlockState(pos)?.block === Blocks.GRASS_BLOCK) {
            world?.setBlockState(pos, Blocks.DIRT_PATH.defaultState)
            world?.playSound(playerEntity, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f)
            val state = world?.getBlockState(pos)
            if (playerEntity is ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(playerEntity as ServerPlayerEntity?, pos, ctx.stack)
            }
            world?.setBlockState(pos, state, Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
            return@OnRightClickCallback ActionResult.success(world!!.isClient)
        }
        return@OnRightClickCallback ActionResult.PASS
    }
    val HAMMER = OnRightClickCallback { ctx ->
        val world = ctx?.world!!
        val pos = ctx.blockPos
        if(world.getBlockState(pos).block == DeepslateForgeController && world.getBlockState(pos.offset(Direction.UP)).get(TIER) == 1) {
            (world.getBlockEntity(pos) as DeepslateForgeControllerBlockEntity).createMultiblock(world)
            return@OnRightClickCallback ActionResult.success(world.isClient)
        }
        return@OnRightClickCallback ActionResult.PASS
    }
    val NONE = OnRightClickCallback{ ActionResult.PASS }
    internal fun init() {
        RIGHT_CLICK_CALLBACK_REGISTRY(Soulforged.NAME) {
            AXE withId "axe"
            HOE withId "hoe"
            SHOVEL withId "shovel"
            HAMMER withId "hammer"
            NONE withId "none"
        }
    }
    fun interface OnRightClickCallback {
        fun onRightClick(ctx: ItemUsageContext?): ActionResult?
    }
}
