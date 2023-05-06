package studio.soulforged.soulforged.item.tool

import com.google.gson.Gson
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import org.quiltmc.qsl.resource.loader.api.ResourceLoader
import org.quiltmc.qsl.resource.loader.api.reloader.SimpleSynchronousResourceReloader
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.item.tool.combat.*
import studio.soulforged.soulforged.item.tool.part.ToolPart
import studio.soulforged.soulforged.item.tool.part.ToolParts
import studio.soulforged.soulforged.material.Materials
import studio.soulforged.soulforged.resource.callback.CallbackHolder
import studio.soulforged.soulforged.util.RegistryUtil
import java.io.BufferedReader
import java.io.InputStreamReader

@Suppress("unused")
object ToolTypes {
    val RAW_TOOL_TYPES: HashMap<Identifier, RawToolType> = hashMapOf()
    val TOOL_TYPES: HashMap<Identifier, ToolType> = hashMapOf()
    internal fun init() {
        ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(object : SimpleSynchronousResourceReloader {
            override fun reload(manager: ResourceManager) {
                TOOL_TYPES.clear()
                for(id in manager.findResources("tool_types") { path ->
                    return@findResources path.path.endsWith(".json")
                }) {
                    try {
                        val rid = RegistryUtil.stripJson(id.key, "tool_types/")
                        RAW_TOOL_TYPES[rid] = Gson().fromJson(BufferedReader(InputStreamReader(manager.open(id.key), "UTF-8")).use { it.readText() }, RawToolType::class.java)
                    } catch (e: Exception) {
                        Soulforged.LOGGER.error("Error occurred while loading resource json $id", e)
                    }
                }
                for(rm in RAW_TOOL_TYPES) {
                    TOOL_TYPES[rm.key] = rm.value.intoToolType(rm.key)
                }
                RAW_TOOL_TYPES.clear()
                Soulforged.LOGGER.info("Registered ${Materials.MATERIALS.size} tool types!")
            }

            override fun getQuiltId(): Identifier {
                return Identifier("soulforged", "tool_types")
            }

        })
    }
    data class RawToolType(val default_attack: RawAttackProperties, val hc_attack: RawAttackProperties?, val dc_attack: RawAttackProperties?, val callbacks: HashMap<String, String>, val recipe: HashMap<String, String>) {
        fun intoToolType(id: Identifier): ToolType {
            return ToolType(id,
                RegistryUtil.resolveTranslationKey(id, "item", "tool.type"),
                default_attack.intoAttackProperties(),
                hc_attack?.intoAttackProperties(),
                dc_attack?.intoAttackProperties(),
                CallbackHolder(callbacks),
                listOf(
                    ToolParts.TOOL_PARTS[Identifier(recipe["head"])],
                    ToolParts.TOOL_PARTS[Identifier(recipe["binding"])],
                    ToolParts.TOOL_PARTS[Identifier(recipe["handle"])]
                )
            )
        }
    }
    data class RawAttackProperties(val piercing_damage: Double, val blunt_damage: Double, val finesse: Double, val speed: Double, val piercing: Double, val category: String, val crit_type: String) {
        fun intoAttackProperties(): AttackProperties {
            return AttackProperties(piercing_damage,
                blunt_damage,
                finesse,
                speed,
                piercing,
                when(category) {
                    "slashing" -> WeaponCategories.SLASHING
                    "crushing" -> WeaponCategories.CRUSHING
                    "thrusting" -> WeaponCategories.THRUSTING
                    else -> throw IllegalArgumentException()
                },
                when(crit_type) {
                    "forward" -> CritDirections.FORWARD
                    "side" -> CritDirections.SIDE
                    "down" -> CritDirections.DOWN
                    else -> throw IllegalArgumentException()
                })
        }
    }
}
data class ToolType(
    val id: Identifier,
    val name: String,
    val defaultAttack: AttackProperties,
    val hcAttack: AttackProperties?,
    val dcAttack: AttackProperties?,
    val callbacks: CallbackHolder,
    val parts: List<ToolPart?>
)


