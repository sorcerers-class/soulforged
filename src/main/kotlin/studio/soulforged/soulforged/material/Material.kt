package studio.soulforged.soulforged.material

import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.util.Identifier
import org.quiltmc.loader.api.minecraft.ClientOnly

data class Material(
    val id: Identifier,
    val name: String,
    val hardness: Double,
    val edgeholding: Double,
    val workability: Int,
    val density: Int,
    val durability: Int,
    val heat: Int,
    val padding: Int,
    val miningLevel: Int,
    val miningSpeed: Int,
    val canIntoTool: Boolean,
    val canIntoArmor: Boolean,
    val classifier: Classifiers?,
    val xpCapacity: Int,
    val xpAbsorption: Int,
    @ClientOnly val transform: RenderContext.QuadTransform
) {
    companion object {
        @ClientOnly fun color(view: MutableQuadView, color: Long): Boolean {
            val color = color.toInt()
            view.spriteColor(0, color, color, color, color)
            return true
        }
    }
}