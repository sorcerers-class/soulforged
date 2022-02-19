package online.maestoso.soulforgedcombatdebugger.debug.gui;

import imgui.ImGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class CombatDebuggerClientUI {
    public static void render(float delta) {
        if(ImGui.button("Exit")) {
            MinecraftClient.getInstance().setScreen(null);
        }
    }
}
