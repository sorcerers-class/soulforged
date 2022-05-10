package studio.soulforged.soulforged.mixin.common;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import studio.soulforged.soulforged.item.SoulforgedItems;
import studio.soulforged.soulforged.item.tool.combat.AttackHandler;

@Mixin(ServerPlayNetworkHandler.class)
public class InteractPacketMixin {
    @Shadow
    public ServerPlayerEntity player;
    @Inject(method = "onPlayerInteractEntity(Lnet/minecraft/network/packet/c2s/play/PlayerInteractEntityC2SPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/c2s/play/PlayerInteractEntityC2SPacket;getEntity(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void injectOnPlayerInteractEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo ci) {
        if(this.player != null) {
            if(this.player.getMainHandStack() != null) {
                if(this.player.getMainHandStack().getItem() == SoulforgedItems.INSTANCE.getTOOL()) {
                    AttackHandler.attackHandlers.get(this.player.getUuid()).setTarget(packet.getEntity(this.player.getWorld()));
                    ci.cancel();
                }
            }
        }
    }
}
