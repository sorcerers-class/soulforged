/*
* ADAPTED FROM: https://git.lavender.software/hibiscus-client/hibiscus/src/branch/main/src/main/kotlin/codes/som/hibiscus/gui/ImGuiRenderer.kt
 */
package online.maestoso.soulforgedcombatdebugger.gui;

import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class ImGuiRenderer {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    public static ImGuiRenderer INSTANCE;

    /**
     * Create ImGUI context, configure, apply theme, and init GLFW/GL
     */
    public void setup() {
        ImGui.createContext();

        ImGui.getIO().setIniFilename(null);
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        ImGui.getIO().setConfigViewportsNoTaskBarIcon(true);
        ImGui.getIO().setConfigDockingWithShift(true);
        try {
            ImGuiTheme.applyImGuiTheme();
        } catch(IOException e) {
            e.printStackTrace();
        }
        imGuiGlfw.init(MinecraftClient.getInstance().getWindow().getHandle(), true);
        imGuiGl3.init();
    }

    /**
     * Begin a new frame.
     */
    public void beginFrame(float delta) {
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    /**
     * End a frame and update the windows.
     */
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
