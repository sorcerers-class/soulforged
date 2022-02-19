package online.maestoso.soulforged.client;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import online.maestoso.soulforged.item.tool.combat.debug.gui.ImGuiScreen;
import org.lwjgl.glfw.GLFW;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class SoulforgedClient implements ClientModInitializer {
    private static KeyBinding openDebugger;
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new SoulforgedModelProvider());
        openDebugger = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.soulforged.open_debugger",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_CONTROL,
                "category.soulforged"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> MinecraftClient.getInstance().setScreen(new ImGuiScreen()));
    }
}
