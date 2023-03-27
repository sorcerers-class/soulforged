package studio.soulforged.soulforged.mixin.common;

import net.minecraft.network.packet.c2s.play.PlayerInteractionWithEntityC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import studio.soulforged.soulforged.item.SoulforgedItems;

@Mixin(ServerPlayNetworkHandler.class)
public class InteractPacketMixin {
    @Shadow
    public ServerPlayerEntity player;
    @Inject(method = "onPlayerInteractionWithEntity(Lnet/minecraft/network/packet/c2s/play/PlayerInteractionWithEntityC2SPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/c2s/play/PlayerInteractionWithEntityC2SPacket;getEntity(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void soulforged$injectOnPlayerInteractionWithEntity(PlayerInteractionWithEntityC2SPacket packet, CallbackInfo ci) {
        if(this.player != null) {
            if(this.player.getMainHandStack() != null) {
                if(this.player.getMainHandStack().isOf(SoulforgedItems.INSTANCE.getTOOL())) {
                    ci.cancel();
                }
            }
        }
    }
}
