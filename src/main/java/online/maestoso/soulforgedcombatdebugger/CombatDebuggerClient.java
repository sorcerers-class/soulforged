package online.maestoso.soulforgedcombatdebugger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import online.maestoso.soulforgedcombatdebugger.gui.ImGuiRenderer;

@Environment(EnvType.CLIENT)
public class CombatDebuggerClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ImGuiRenderer.INSTANCE = new ImGuiRenderer();
    }
}
