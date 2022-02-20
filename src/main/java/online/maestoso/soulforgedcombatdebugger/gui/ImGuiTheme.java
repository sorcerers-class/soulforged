package online.maestoso.soulforgedcombatdebugger.gui;

import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import online.maestoso.soulforgedcombatdebugger.CombatDebuggerClient;

import java.io.IOException;
import java.io.InputStream;

public class ImGuiTheme {

    public static void applyImGuiTheme() throws IOException {
        ImGuiStyle style = ImGui.getStyle();
        style.setWindowPadding(10f, 10f);
        style.setPopupRounding(0f);
        style.setFramePadding(8f, 4f);
        style.setItemSpacing(10f, 8f);
        style.setItemInnerSpacing(6f, 6f);
        style.setTouchExtraPadding(0f, 0f);
        style.setIndentSpacing(21f);
        style.setScrollbarSize(15f);
        style.setGrabMinSize(8f);
        style.setWindowBorderSize(1f);
        style.setChildBorderSize(0f);
        style.setPopupBorderSize(1f);
        style.setFrameBorderSize(0f);
        style.setTabBorderSize(0f);
        style.setWindowRounding(1f);
        style.setChildRounding(0f);
        style.setFrameRounding(0f);
        style.setScrollbarRounding(0.5f);
        style.setGrabRounding(0f);
        style.setTabRounding(0f);
        style.setWindowTitleAlign(0.5f, 0.5f);
        style.setButtonTextAlign(0.5f, 0.5f);
        style.setDisplaySafeAreaPadding(3f, 3f);

        style.setColor(ImGuiCol.Text, 1.0f, 1.0f, 1.0f, 1.0f);
        style.setColor(ImGuiCol.WindowBg, 0.2f, 0.2f, 0.2f, 0.2f);
        style.setColor(ImGuiCol.Border, 0.2f, 0.2f, 0.2f, 0.2f);

        style.setColor(ImGuiCol.TitleBgActive, 0.5f, 0.0f, 1.0f, 1.0f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.25f, 0.0f, 0.5f, 1.0f);
        style.setColor(ImGuiCol.TitleBg, 0.5f, 0.0f, 1.0f, 1.0f);

        ImFontAtlas font = ImGui.getIO().getFonts();
        InputStream comicMono = CombatDebuggerClient.class.getResourceAsStream("/assets/soulforgedcombatdebugger/ComicMono.ttf");
        if(comicMono == null) {
            throw new IllegalStateException("ComicMono.ttf missing!");
        }
        ImFontConfig cfg = new ImFontConfig();
        cfg.setName("Comic Mono");
        font.addFontFromMemoryTTF(comicMono.readAllBytes(), 20f, cfg);
        ImFontConfig defaultCfg = new ImFontConfig();
        defaultCfg.setName("Monospace");
        font.addFontDefault(defaultCfg);
        font.build();
    }

}
