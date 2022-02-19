package online.maestoso.soulforged.item.tool.combat.debug.gui;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CombatDebuggerClientUI {
    public static void render(float delta) {
        ImGui.begin("test window");
        ImGui.setNextWindowSize(300, 2);
    }
}
