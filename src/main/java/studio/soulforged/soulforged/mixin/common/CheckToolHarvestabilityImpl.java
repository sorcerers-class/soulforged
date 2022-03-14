package studio.soulforged.soulforged.mixin.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.ItemStack;

import net.minecraft.server.world.ServerWorld;

import net.minecraft.util.math.BlockPos;

import studio.soulforged.soulforged.item.SoulforgedItems;

import studio.soulforged.soulforged.util.BlockUtil;

import org.jetbrains.annotations.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public class CheckToolHarvestabilityImpl {
    @Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At("HEAD"), cancellable = true)
    private static void injectGetDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, CallbackInfoReturnable<List<Object>> info) {
        if(entity instanceof PlayerEntity) {
            ItemStack tool = ((PlayerEntity)entity).getMainHandStack();
            if(tool.getItem() == SoulforgedItems.INSTANCE.getTOOL() && !BlockUtil.INSTANCE.isMineable(state, tool)) {
                    info.setReturnValue(List.of(ItemStack.EMPTY));
            }
        }
    }
}
