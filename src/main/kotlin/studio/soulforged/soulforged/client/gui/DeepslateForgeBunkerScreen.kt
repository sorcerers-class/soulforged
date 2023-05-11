package studio.soulforged.soulforged.client.gui

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.quiltmc.loader.api.minecraft.ClientOnly

@ClientOnly
class DeepslateForgeBunkerScreen(handler: DeepslateForgeBunkerScreenHandler, inventory: PlayerInventory)
    : HandledScreen<DeepslateForgeBunkerScreenHandler>(handler, inventory, Text.literal("Deepslate Forge Bunker")) {
    companion object {
        private val TEXTURE = Identifier("soulforged:textures/gui/container/deepslate_forge_bunker.png")
    }
    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
    }
    override fun render(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }
    override fun drawBackground(matrices: MatrixStack?, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShaderTexture(0, TEXTURE)
        val i = (width - backgroundWidth) / 2
        val j = (height - backgroundHeight) / 2
        drawTexture(matrices, i, j, 0, 0, backgroundWidth, backgroundHeight)
    }

}