package studio.soulforged.soulforged.client.gui
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.feature_flags.FeatureFlags
import net.minecraft.registry.Registries
import net.minecraft.screen.ScreenHandlerType
import org.quiltmc.qkl.library.registry.invoke
import studio.soulforged.soulforged.Soulforged

object SoulforgedScreenHandlerTypes {
    val DEEPSLATE_FORGE_BUNKER: ScreenHandlerType<DeepslateForgeBunkerScreenHandler> = ScreenHandlerType({ syncId: Int, playerInventory: PlayerInventory ->
        DeepslateForgeBunkerScreenHandler(syncId, playerInventory)
    }, FeatureFlags.DEFAULT_SET)
    fun init() {
        Registries.SCREEN_HANDLER_TYPE(Soulforged.NAME) {
            DEEPSLATE_FORGE_BUNKER withId "deepslate_forge_bunker"
        }
    }
}