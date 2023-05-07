package studio.soulforged.soulforged.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.soulforged.soulforged.item.SoulforgedItems;
import studio.soulforged.soulforged.client.attack.AttackQueueHolder;

@ClientOnly
@Mixin(Mouse.class)
public class MouseMixin {
    @Final
    @Shadow
    private MinecraftClient client;
    @Inject(method = "onMouseButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBind;setKeyPressed(Lcom/mojang/blaze3d/platform/InputUtil$Key;Z)V", shift = At.Shift.BEFORE))
    private void soulforged$injectOnMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if(client.player != null) {
            if(client.player.getMainHandStack() != null) {
                if(client.player.getMainHandStack().isOf(SoulforgedItems.TOOL)) {
                    ((AttackQueueHolder)client.player).getQueue().addClickPacket();
                }
            }
        }
    }
}
