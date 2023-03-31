package studio.soulforged.soulforged.item.tool.part

import com.google.gson.Gson
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import org.quiltmc.qsl.resource.loader.api.ResourceLoader
import org.quiltmc.qsl.resource.loader.api.reloader.SimpleSynchronousResourceReloader
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.util.RegistryUtil
import java.io.BufferedReader
import java.io.InputStreamReader

@Suppress("unused")
object ToolParts {
    val RAW_TOOL_PARTS: HashMap<Identifier, RawToolPart> = hashMapOf()
    val TOOL_PARTS: HashMap<Identifier, ToolPart> = hashMapOf()
    fun init() {
        ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(object : SimpleSynchronousResourceReloader {
            override fun reload(manager: ResourceManager) {
                TOOL_PARTS.clear()
                for(id in manager.findResources("tool_parts") { path ->
                    return@findResources path.path.endsWith(".json")
                }) {
                    try {
                        val rid = RegistryUtil.stripJson(id.key, "tool_parts/")
                        RAW_TOOL_PARTS[rid] = Gson().fromJson(BufferedReader(InputStreamReader(manager.open(id.key), "UTF-8")).use { it.readText() }, RawToolPart::class.java)
                    } catch (e: Exception) {
                        Soulforged.LOGGER.error("Error occurred while loading resource json $id", e)
                    }
                }
                for(rm in RAW_TOOL_PARTS) {
                    TOOL_PARTS[rm.key] = ToolPart(rm.key, RegistryUtil.resolveTranslationKey(rm.key, "item", "part"), rm.value.weight, rm.value.durability)
                }
                RAW_TOOL_PARTS.clear()
                Soulforged.LOGGER.info("Registered ${TOOL_PARTS.size} tool parts!")
            }

            override fun getQuiltId(): Identifier {
                return Identifier("soulforged", "tool_parts")
            }

        })
    }
}
data class RawToolPart(val weight: Double, val durability: Double)
data class ToolPart(val id: Identifier, val name: String, val weight: Double, val durability: Double)