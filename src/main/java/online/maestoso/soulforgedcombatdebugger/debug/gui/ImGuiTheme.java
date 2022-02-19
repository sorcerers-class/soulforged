package online.maestoso.soulforgedcombatdebugger.debug.gui;

import imgui.ImGui;
import imgui.ImGuiStyle;

public class ImGuiTheme {
    public static void applyImGuiTheme() {
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
        style.setWindowRounding(0f);
        style.setChildRounding(0f);
        style.setFrameRounding(0f);
        style.setScrollbarRounding(0f);
        style.setGrabRounding(0f);
        style.setTabRounding(0f);
        style.setWindowTitleAlign(0.5f, 0.5f);
        style.setButtonTextAlign(0.5f, 0.5f);
        style.setDisplaySafeAreaPadding(3f, 3f);
    }
}
