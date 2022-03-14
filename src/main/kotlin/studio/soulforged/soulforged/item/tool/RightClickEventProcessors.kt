package studio.soulforged.soulforged.item.tool

import studio.soulforged.soulforged.item.tool.RightClickEventProcessor
import net.minecraft.item.ItemUsageContext
import net.minecraft.world.World
import net.minecraft.util.math.BlockPos
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.block.BlockState
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor
import net.minecraft.block.Oxidizable
import net.minecraft.item.HoneycombItem
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.sound.SoundCategory
import net.minecraft.world.WorldEvents
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.block.Block
import net.minecraft.util.ActionResult
import net.minecraft.tag.BlockTags
import net.minecraft.block.Blocks
import java.util.*

object RightClickEventProcessors {
    val AXE_INTERACTIONS: RightClickEventProcessor = label@ object : RightClickEventProcessor {
        override fun onRightClick(ctx: ItemUsageContext?): ActionResult? {
            val world = ctx?.world
            val blockPos = ctx?.blockPos
            val playerEntity = ctx?.player
            val blockState = world?.getBlockState(blockPos)
            val tryStripLog = Optional.of(
                AxeItemAccessor.getStrippedBlocks()[blockState?.block]!!.defaultState
            )
            val tryDecreaseOxidation = Oxidizable.getDecreasedOxidationState(blockState)
            val tryUnwax = Optional.ofNullable(HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()[blockState?.block])
                .map { block: Block -> block.getStateWithProperties(blockState) }
            val itemStack = ctx?.stack
            var actionExecuted = Optional.empty<BlockState>()
            if (tryStripLog.isPresent) {
                world?.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f)
                actionExecuted = tryStripLog
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
                return ActionResult.success(world!!.isClient)
            }
            return ActionResult.PASS
        }
    }
    val HOE_INTERACTIONS: RightClickEventProcessor = object : RightClickEventProcessor {
        override fun onRightClick(ctx: ItemUsageContext?): ActionResult? {
            val world = ctx?.world
            val pos = ctx?.blockPos
            val player = ctx?.player
            var state = world?.getBlockState(pos)
            if (BlockTags.DIRT.contains(state?.block)) {
                world?.setBlockState(pos, Blocks.FARMLAND.defaultState)
                world?.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f)
                state = world?.getBlockState(pos)
                if (player is ServerPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger(player as ServerPlayerEntity?, pos, ctx.stack)
                }
                world?.setBlockState(pos, state, Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
                return ActionResult.success(world!!.isClient)
            }
            return ActionResult.PASS
        }
    }
    val SHOVEL_INTERACTIONS: RightClickEventProcessor = object : RightClickEventProcessor {
        override fun onRightClick(ctx: ItemUsageContext?): ActionResult? {
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
                return ActionResult.success(world!!.isClient)
            }
            return ActionResult.PASS
        }
    }
    val NONE: RightClickEventProcessor = object : RightClickEventProcessor {
        override fun onRightClick(ctx: ItemUsageContext?): ActionResult {
            return ActionResult.PASS
        }
    }
}