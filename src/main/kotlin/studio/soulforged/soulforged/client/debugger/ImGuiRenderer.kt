/*
* ADAPTED FROM: https://git.lavender.software/hibiscus-client/hibiscus/src/branch/main/src/main/kotlin/codes/som/hibiscus/gui/ImGuiRenderer.kt
 */
package studio.soulforged.soulforged.client.debugger

import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW
import org.quiltmc.loader.api.minecraft.ClientOnly
import java.io.IOException

@ClientOnly
class ImGuiRenderer {
    private val imGuiGlfw = ImGuiImplGlfw()
    private val imGuiGl3 = ImGuiImplGl3()
    /**
     * Create ImGUI context, configure, apply theme, and init GLFW/GL
     */
    fun setup() {
        ImGui.createContext()
        ImGui.getIO().iniFilename = null
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard)
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.ViewportsEnable)
        ImGui.getIO().configViewportsNoTaskBarIcon = true
        ImGui.getIO().configDockingWithShift = true
        try {
            ImGuiTheme.applyImGuiTheme()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imGuiGlfw.init(MinecraftClient.getInstance().window.handle, true)
        imGuiGl3.init()
    }

    /**
     * Begin a new frame.
     */
    fun beginFrame() {
        if(SCD_ENABLED) {
            imGuiGlfw.newFrame()
            ImGui.newFrame()
        }
    }

    /**
     * End a frame and update the windows.
     */
    fun finishFrame() {
        if(SCD_ENABLED) {
            ImGui.render()
            imGuiGl3.renderDrawData(ImGui.getDrawData())
            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                val backupWindowPointer = GLFW.glfwGetCurrentContext()
                ImGui.updatePlatformWindows()
                ImGui.renderPlatformWindowsDefault()
                GLFW.glfwMakeContextCurrent(backupWindowPointer)
            }
        }
    }

    companion object {
        var SCD_ENABLED: Boolean = false
        var INSTANCE: ImGuiRenderer? = null
    }
}