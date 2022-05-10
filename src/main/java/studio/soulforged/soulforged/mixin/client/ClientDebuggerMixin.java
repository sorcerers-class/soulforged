package studio.soulforged.soulforged.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.soulforged.soulforged.Soulforged;
import studio.soulforged.soulforged.client.gui.CombatDebuggerClientUI;
import studio.soulforged.soulforged.client.gui.ImGuiRenderer;

import java.util.Objects;

@Mixin(MinecraftClient.class)
public class ClientDebuggerMixin {

    /**
     * Injects ImGuiRenderer setup code into client setup.
     */
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SplashOverlay;init(Lnet/minecraft/client/MinecraftClient;)V", shift = At.Shift.BEFORE))
    private void onFinishingStartup(RunArgs args, CallbackInfo ci) {
        Soulforged.Companion.getLOGGER().info("Setting up ImGuiRenderer...");
        ImGuiRenderer.Companion.setINSTANCE(new ImGuiRenderer());
        Objects.requireNonNull(ImGuiRenderer.Companion.getINSTANCE()).setup();
        Soulforged.Companion.getLOGGER().info("Successfully set up ImGuiRenderer!");
    }

    /**
     * Adds beginning of ImGUI frame to beginning of frame renderer.
     */
    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;skipGameRender:Z", shift = At.Shift.BEFORE))
    private void onPreRenderEverything(boolean tick, CallbackInfo ci) {
        Objects.requireNonNull(ImGuiRenderer.Companion.getINSTANCE()).beginFrame();
    }

    /**
     * Adds end of ImGUI frame to end of frame renderer.
     */
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;endWrite()V", shift = At.Shift.BEFORE))
    private void onPostRenderEverything(boolean tick, CallbackInfo ci) {
        CombatDebuggerClientUI.INSTANCE.render();
        Objects.requireNonNull(ImGuiRenderer.Companion.getINSTANCE()).finishFrame();
    }

}
