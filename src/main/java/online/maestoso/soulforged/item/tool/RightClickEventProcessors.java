package online.maestoso.soulforged.item.tool;

import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;

import net.minecraft.advancement.criterion.Criteria;

import net.minecraft.block.*;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;

import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import net.minecraft.tag.BlockTags;

import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.Optional;

public class RightClickEventProcessors {

    public static final RightClickEventProcessor AXE_INTERACTIONS = (ItemUsageContext ctx) -> {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        PlayerEntity playerEntity = ctx.getPlayer();
        BlockState blockState = world.getBlockState(blockPos);

        Optional<BlockState> tryStripLog = Optional.of(AxeItemAccessor.getStrippedBlocks().get(blockState.getBlock()).getDefaultState());
        Optional<BlockState> tryDecreaseOxidation = Oxidizable.getDecreasedOxidationState(blockState);
        Optional<BlockState> tryUnwax = Optional.ofNullable(HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().get(blockState.getBlock())).map(block -> block.getStateWithProperties(blockState));
        ItemStack itemStack = ctx.getStack();
        Optional<BlockState> actionExecuted = Optional.empty();

        if (tryStripLog.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);

            actionExecuted = tryStripLog;
        } else if (tryDecreaseOxidation.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f);

            world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_SCRAPED, blockPos, 0);
            actionExecuted = tryDecreaseOxidation;
        } else if (tryUnwax.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0f, 1.0f);

            world.syncWorldEvent(playerEntity, WorldEvents.WAX_REMOVED, blockPos, 0);
            actionExecuted = tryUnwax;
        }

        if (actionExecuted.isPresent()) {
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
            }
            world.setBlockState(blockPos, actionExecuted.get(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    };
    public static final RightClickEventProcessor HOE_INTERACTIONS = (ItemUsageContext ctx) -> {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        PlayerEntity player = ctx.getPlayer();

        BlockState state = world.getBlockState(pos);
        if(BlockTags.DIRT.contains(state.getBlock())) {
            world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            state = world.getBlockState(pos);

            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, pos, ctx.getStack());
            }
            world.setBlockState(pos, state, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    };
    public static final RightClickEventProcessor SHOVEL_INTERACTIONS = (ItemUsageContext ctx) -> {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        PlayerEntity playerEntity = ctx.getPlayer();

        if(world.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK) {
            world.setBlockState(pos, Blocks.DIRT_PATH.getDefaultState());
            world.playSound(playerEntity, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);

            BlockState state = world.getBlockState(pos);

            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, pos, ctx.getStack());
            }
            world.setBlockState(pos, state, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    };
    public static final RightClickEventProcessor NONE = (ItemUsageContext ctx) -> ActionResult.PASS;
}
