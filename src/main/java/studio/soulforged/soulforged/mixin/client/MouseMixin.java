package studio.soulforged.soulforged.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.network.PacketByteBuf;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.soulforged.soulforged.client.gui.CombatDebuggerClientUI;
import studio.soulforged.soulforged.item.SoulforgedItems;
import studio.soulforged.soulforged.network.NetworkIdentifiers;

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
                if(client.player.getMainHandStack().isOf(SoulforgedItems.INSTANCE.getTOOL())) {
                    PacketByteBuf mousePacket = PacketByteBufs.create();
                    mousePacket.writeInt(button);
                    mousePacket.writeInt(action);
                    ClientPlayNetworking.send(NetworkIdentifiers.INSTANCE.getMOUSE_PACKET(), mousePacket);
                    CombatDebuggerClientUI.INSTANCE.setLastPacket(action);
                    CombatDebuggerClientUI.INSTANCE.setPacketCounter(CombatDebuggerClientUI.INSTANCE.getPacketCounter() + 1);
                }
            }
        }
    }
}
