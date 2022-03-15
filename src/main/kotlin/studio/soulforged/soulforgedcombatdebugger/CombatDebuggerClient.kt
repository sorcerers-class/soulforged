package studio.soulforged.soulforgedcombatdebugger;

import imgui.ImGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import studio.soulforged.soulforgedcombatdebugger.gui.ImGuiRenderer;

@Environment(EnvType.CLIENT)
public class CombatDebuggerClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new ImGuiRenderer(); // Create the ImGUI renderer instance.
    }
}
