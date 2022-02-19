package online.maestoso.soulforged.item.tool.combat.debug.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ImGuiScreen extends Screen {
    public ImGuiScreen() {
        super(Text.of("Soulforged Combat Debugger v1"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        CombatDebuggerClientUI.render(delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
