package studio.soulforged.soulforged.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class PreventUnchargedAttackMixin {
    @Shadow
    public ClientPlayerEntity player;
    @Inject(method = "doAttack", at = @At(value = "HEAD"), cancellable = true)
    public void soulforged$injectToCancelAttack(CallbackInfoReturnable<Boolean> cir) {
        if(this.player.getAttackCooldownProgress(0.5f) != 1.0f) {
            cir.setReturnValue(false);
        }
    }
}
