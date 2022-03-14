/*
* ADAPTED FROM: https://git.lavender.software/hibiscus-client/hibiscus/src/branch/main/src/main/kotlin/codes/som/hibiscus/gui/ImGuiRenderer.kt
 */
package studio.soulforged.soulforgedcombatdebugger.gui

import imgui.ImGui
import net.fabricmc.api.EnvType
import imgui.glfw.ImGuiImplGlfw
import imgui.gl3.ImGuiImplGl3
import imgui.flag.ImGuiConfigFlags
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import studio.soulforged.soulforgedcombatdebugger.gui.ImGuiTheme
import java.io.IOException
import org.lwjgl.glfw.GLFW
import studio.soulforged.soulforgedcombatdebugger.gui.ImGuiRenderer

@Environment(EnvType.CLIENT)
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
    fun beginFrame(delta: Float) {
        imGuiGlfw.newFrame()
        ImGui.newFrame()
    }

    /**
     * End a frame and update the windows.
     */
    fun finishFrame(delta: Float) {
        ImGui.render()
        imGuiGl3.renderDrawData(ImGui.getDrawData())
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            val backupWindowPointer = GLFW.glfwGetCurrentContext()
            ImGui.updatePlatformWindows()
            ImGui.renderPlatformWindowsDefault()
            GLFW.glfwMakeContextCurrent(backupWindowPointer)
        }
    }

    companion object {
        var INSTANCE: ImGuiRenderer? = null
    }
}