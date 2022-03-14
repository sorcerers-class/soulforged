package studio.soulforged.soulforgedcombatdebugger.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.render.RenderTickCounter;
import studio.soulforged.soulforged.Soulforged;
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI;
import studio.soulforged.soulforgedcombatdebugger.gui.ImGuiRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.soulforged.soulforged.Soulforged;
import studio.soulforged.soulforgedcombatdebugger.gui.CombatDebuggerClientUI;
import studio.soulforged.soulforgedcombatdebugger.gui.ImGuiRenderer;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow private volatile boolean paused;
    @Shadow private float pausedTickDelta;
    @Shadow @Final private RenderTickCounter renderTickCounter;

    /**
     * Injects ImGuiRenderer setup code into client setup.
     */
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SplashOverlay;init(Lnet/minecraft/client/MinecraftClient;)V", shift = At.Shift.BEFORE))
    private void onFinishingStartup(RunArgs args, CallbackInfo ci) {
        Soulforged.Companion.getLOGGER().info("Setting up ImGuiRenderer...");
        ImGuiRenderer.Companion.getINSTANCE().setup();
        Soulforged.Companion.getLOGGER().info("Successfully set up ImGuiRenderer!");
    }

    /**
     * Adds beginning of ImGUI frame to beginning of frame renderer.
     */
    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;skipGameRender:Z", shift = At.Shift.BEFORE))
    private void onPreRenderEverything(boolean tick, CallbackInfo ci) {
        float delta = this.paused ? this.pausedTickDelta : this.renderTickCounter.tickDelta;
        ImGuiRenderer.Companion.getINSTANCE().beginFrame(delta);
    }

    /**
     * Adds end of ImGUI frame to end of frame renderer.
     */
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;endWrite()V", shift = At.Shift.BEFORE))
    private void onPostRenderEverything(boolean tick, CallbackInfo ci) {
        float delta = this.paused ? this.pausedTickDelta : this.renderTickCounter.tickDelta;
        CombatDebuggerClientUI.INSTANCE.render(delta);
        ImGuiRenderer.Companion.getINSTANCE().finishFrame(delta);
    }

}
