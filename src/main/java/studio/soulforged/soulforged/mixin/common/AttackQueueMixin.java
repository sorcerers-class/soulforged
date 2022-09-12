package studio.soulforged.soulforged.mixin.common;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.soulforged.soulforged.item.tool.combat.AttackQueue;
import studio.soulforged.soulforged.item.tool.combat.AttackQueueHolder;

@Mixin(ServerPlayerEntity.class)
public abstract class AttackQueueMixin extends PlayerEntity implements AttackQueueHolder {
    @Unique public final AttackQueue queue = new AttackQueue(this);

    @Override
    public AttackQueue getQueue() {
        return queue;
    }

    public AttackQueueMixin(World world, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(world, blockPos, f, gameProfile);
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void attack(Entity target, CallbackInfo ci) {
        queue.add(target);
        ci.cancel();
    }
    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        queue.tick();
    }
}
