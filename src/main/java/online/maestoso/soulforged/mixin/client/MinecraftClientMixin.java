package online.maestoso.soulforged.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.render.RenderTickCounter;
import online.maestoso.soulforgedcombatdebugger.debug.gui.ImGuiRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    private volatile boolean paused;

    @Shadow
    private float pausedTickDelta;

    @Shadow
    @Final
    private RenderTickCounter renderTickCounter;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SplashOverlay;init(Lnet/minecraft/client/MinecraftClient;)V", shift = At.Shift.BEFORE))
    private void onFinishingStartup(RunArgs args, CallbackInfo ci) {
        ImGuiRenderer.INSTANCE.setup();
    }
    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;skipGameRender:Z", shift = At.Shift.BEFORE))
    private void onPreRenderEverything(boolean tick, CallbackInfo ci) {
        float delta = this.paused ? this.pausedTickDelta : this.renderTickCounter.tickDelta;
        ImGuiRenderer.INSTANCE.beginFrame(delta);
    }
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;endWrite()V", shift = At.Shift.BEFORE))
    private void onPostRenderEverything(boolean tick, CallbackInfo ci) {
        float delta = this.paused ? this.pausedTickDelta : this.renderTickCounter.tickDelta;
        ImGuiRenderer.INSTANCE.finishFrame(delta);
    }

}
