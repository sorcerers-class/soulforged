package studio.soulforged.soulforged.resource.callback

import net.minecraft.block.BlockState
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import org.lwjgl.glfw.Callbacks
import studio.soulforged.soulforged.material.Materials
import java.util.*

class CallbackHolder(raw: HashMap<String, String>) {
    val callbacks: EnumMap<Callbacks, Identifier> = EnumMap(Callbacks::class.java)
    init {
        for((k, v) in raw) {
            callbacks[when(k) {
                "getMiningSpeed" -> Callbacks.GET_MINING_SPEED
                "onUse" -> Callbacks.ON_RIGHT_CLICK
                else -> throw IllegalArgumentException()
            }] = Identifier(v)
        }
    }
    private val miningSpeedProcessor: MiningSpeedProcessors.MiningSpeedProcessor? = MiningSpeedProcessors.MINING_SPEED_REGISTRY.get(Identifier(raw["getMiningSpeed"] ?: "soulforged:hand"))
    private val onRightClickCallback: OnRightClickCallbacks.OnRightClickCallback? = OnRightClickCallbacks.RIGHT_CLICK_CALLBACK_REGISTRY.get(Identifier(raw["onUse"] ?: "soulforged:none"))
    fun getMiningSpeed(blockState: BlockState, material: Materials.Material): Float {
        return miningSpeedProcessor?.getMiningSpeed(blockState, material) ?: MiningSpeedProcessors.HAND.getMiningSpeed(blockState, material)
    }
    fun onRightClick(context: ItemUsageContext): ActionResult {
        return onRightClickCallback?.onRightClick(context) ?: OnRightClickCallbacks.NONE.onRightClick(context) ?: ActionResult.PASS
    }
    enum class Callbacks {
        GET_MINING_SPEED,
        ON_RIGHT_CLICK
    }
}