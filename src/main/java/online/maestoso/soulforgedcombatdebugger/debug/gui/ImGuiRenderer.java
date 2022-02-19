/*
* ADAPTED FROM: https://git.lavender.software/hibiscus-client/hibiscus/src/branch/main/src/main/kotlin/codes/som/hibiscus/gui/ImGuiRenderer.kt
 */
package online.maestoso.soulforgedcombatdebugger.debug.gui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ImGuiRenderer {
    private ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    public static ImGuiRenderer INSTANCE;

    public void setup() {
        ImGui.createContext();

        ImGui.getIO().setIniFilename(null);
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        ImGui.getIO().setConfigViewportsNoTaskBarIcon(true);
        ImGui.getIO().setConfigDockingWithShift(true);

        ImGuiTheme.applyImGuiTheme();

        imGuiGlfw.init(MinecraftClient.getInstance().getWindow().getHandle(), true);
        imGuiGl3.init();
    }
    public void beginFrame(float delta) {
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }
    public void finishFrame(float delta) {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            long backupWindowPointer = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPointer);
        }
    }
}
