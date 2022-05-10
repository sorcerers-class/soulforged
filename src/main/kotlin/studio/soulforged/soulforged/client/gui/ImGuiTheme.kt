package studio.soulforged.soulforged.client.gui

import imgui.ImFontConfig
import imgui.ImGui
import imgui.flag.ImGuiCol
import studio.soulforged.soulforged.client.SoulforgedClient
import java.io.IOException

object ImGuiTheme {
    /**
     * Creates the ImGUI theme.
     * @throws IOException if the font file cannot be read.
     */
    @Throws(IOException::class)
    fun applyImGuiTheme() {
        val style = ImGui.getStyle()
        // i don't feel like commenting all of this, so look at the documentation for ImGuiStyle
        with(style) {
            setWindowPadding(10f, 10f)
            popupRounding = 0f
            setFramePadding(8f, 4f)
            setItemSpacing(10f, 8f)
            setItemInnerSpacing(6f, 6f)
            setTouchExtraPadding(0f, 0f)
            indentSpacing = 21f
            scrollbarSize = 15f
            grabMinSize = 8f
            windowBorderSize = 1f
            childBorderSize = 0f
            popupBorderSize = 1f
            frameBorderSize = 0f
            tabBorderSize = 0f
            windowRounding = 1f
            childRounding = 0f
            frameRounding = 0f
            scrollbarRounding = 0.5f
            grabRounding = 0f
            tabRounding = 0f
            setWindowTitleAlign(0.5f, 0.5f)
            setButtonTextAlign(0.5f, 0.5f)
            setDisplaySafeAreaPadding(3f, 3f)
            setColor(ImGuiCol.Text, 1.0f, 1.0f, 1.0f, 1.0f)
            setColor(ImGuiCol.WindowBg, 0.1875f, 0.1875f, 0.1875f, 0.1875f)
            setColor(ImGuiCol.Border, 0.1875f, 0.1875f, 0.1875f, 0.1875f)
            setColor(ImGuiCol.TitleBgActive, 0.5f, 0.0f, 1.0f, 1.0f)
            setColor(ImGuiCol.TitleBgCollapsed, 0.25f, 0.0f, 0.5f, 1.0f)
            setColor(ImGuiCol.TitleBg, 0.5f, 0.0f, 1.0f, 1.0f)
        }
        // Read ComicMono.ttf
        val font = ImGui.getIO().fonts
        val comicMono =
            SoulforgedClient::class.java.getResourceAsStream("/assets/soulforged/ComicMono.ttf")
                ?: throw IOException("ComicMono.ttf missing!")
        // Configure font to be size 20
        val cfg = ImFontConfig()
        cfg.setName("Comic Mono")
        font.addFontFromMemoryTTF(comicMono.readAllBytes(), 14f, cfg)
        // Fallback font
        val defaultCfg = ImFontConfig()
        defaultCfg.setName("Monospace")
        font.addFontDefault(defaultCfg)
        // Create theme
        font.build()
    }
}