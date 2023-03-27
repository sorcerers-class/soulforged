package studio.soulforged.soulforged.resource.callback

import net.minecraft.advancement.criterion.Criteria
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.Oxidizable
import net.minecraft.item.AxeItem
import net.minecraft.item.HoneycombItem
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.BlockTags
import net.minecraft.util.ActionResult
import net.minecraft.util.registry.Registry
import net.minecraft.world.WorldEvents
import studio.soulforged.soulforged.resource.callback.OnRightClickCallbacks.OnRightClickCallback
import java.util.*

object OnRightClickCallbacks {

    val AXE_INTERACTIONS: OnRightClickCallback = OnRightClickCallback { ctx ->
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
            ActionResult.success(world!!.isClient)
        }
        ActionResult.PASS
    }
    val HOE_INTERACTIONS: OnRightClickCallback = OnRightClickCallback { ctx ->
        val world = ctx?.world
        val pos = ctx?.blockPos
        val player = ctx?.player
        var state = world?.getBlockState(pos)
        if (Registry.BLOCK.get(Registry.BLOCK.getRawId(state?.block)).defaultState.isIn(BlockTags.DIRT)) {
            world?.setBlockState(pos, Blocks.FARMLAND.defaultState)
            world?.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f)
            state = world?.getBlockState(pos)
            if (player is ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(player as ServerPlayerEntity?, pos, ctx.stack)
            }
            world?.setBlockState(pos, state, Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
            ActionResult.success(world!!.isClient)
        }
        ActionResult.PASS
    }
    val SHOVEL_INTERACTIONS: OnRightClickCallback = OnRightClickCallback { ctx ->
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
            ActionResult.success(world!!.isClient)
        }
        ActionResult.PASS
    }
    val HAMMER_TO_CREATE_WORKSTATION: OnRightClickCallback = OnRightClickCallback { ctx ->
        val world = ctx?.world
        val pos = ctx?.blockPos
        val playerEntity = ctx?.player
        for(i in -3..3) {
            for(j in -3..3) {
                val block = world?.getBlockState(pos?.add(i, 0, j))?.block ?: Blocks.AIR
            }
        }
        ActionResult.PASS
    }
    val NONE: OnRightClickCallback = OnRightClickCallback { ActionResult.PASS }
    fun interface OnRightClickCallback {
        fun onRightClick(ctx: ItemUsageContext?): ActionResult?
    }
}
