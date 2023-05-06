package studio.soulforged.soulforged.material

import com.google.gson.Gson
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.registry.Registries
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qsl.resource.loader.api.ResourceLoader
import org.quiltmc.qsl.resource.loader.api.reloader.SimpleSynchronousResourceReloader
import studio.soulforged.soulforged.Soulforged
import studio.soulforged.soulforged.Soulforged.id
import studio.soulforged.soulforged.util.ItemOrTag
import studio.soulforged.soulforged.util.NumberUtils
import studio.soulforged.soulforged.util.RegistryUtil
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

@Suppress("unused")
object Materials {
    val RAW_MATERIALS: HashMap<Identifier, RawMaterial> = hashMapOf(Pair(
        "builtin/material".id(),
        RawMaterial("",
            0.0,
            0.0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            false,
            false,
            0,
            0,
            "",
            hashMapOf()
        )))
    val MATERIALS: HashMap<Identifier, Material> = hashMapOf()

    internal fun init() {
        ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(object : SimpleSynchronousResourceReloader {
            override fun reload(manager: ResourceManager) {
                MATERIALS.clear()
                for(id in manager.findResources("tool_materials") { path ->
                    return@findResources path.path.endsWith(".json")
                }) {
                    try {
                        val rid = RegistryUtil.stripJson(id.key, "tool_materials/")
                        RAW_MATERIALS[rid] = Gson().fromJson(BufferedReader(InputStreamReader(manager.open(id.key), "UTF-8")).use { it.readText() }, RawMaterial::class.java)
                    } catch (e: Exception) {
                        Soulforged.LOGGER.error("Error occurred while loading resource json $id", e)
                    }
                }
                for(rm in RAW_MATERIALS) {
                    MATERIALS[rm.key] = Material(rm.value.resolve(), rm.key)
                }
                Soulforged.LOGGER.info("Registered ${MATERIALS.size} tool materials!")
            }

            override fun getQuiltId(): Identifier {
                return Identifier("soulforged", "materials")
            }

        })
    }
    data class RawMaterial(
        val parent: String?,
        val hardness: Double?,
        val edgeholding: Double?,
        val workability: Int?,
        val density: Int?,
        val durability: Int?,
        val heat: Int?,
        val padding: Int?,
        val mining_level: Int?,
        val mining_speed: Int?,
        val can_tool: Boolean?,
        val can_armor: Boolean?,
        val xp_capacity: Int?,
        val xp_absorption: Int?,
        val color: String?,
        val resources: HashMap<String, Float>?
    ) {
        fun resolve(): RawMaterial {
            if(parent == null || parent == "") return RAW_MATERIALS["builtin/material".id()]!!
            var p = RAW_MATERIALS[Identifier(parent)]
            if(p?.hardness == null
                || p.edgeholding == null
                || p.workability == null
                || p.density == null
                || p.durability == null
                || p.heat == null
                || p.padding == null
                || p.mining_level == null
                || p.mining_speed == null
                || p.can_tool == null
                || p.can_armor == null
                || p.xp_capacity == null
                || p.xp_absorption == null
                || p.color == null
                || p.resources == null)
                p = p?.resolve()
            return RawMaterial(parent,
                hardness ?: p?.hardness,
                edgeholding ?: p?.edgeholding,
                workability ?: p?.workability,
                density ?: p?.density,
                durability ?: p?.durability,
                heat ?: p?.heat,
                padding ?: p?.padding,
                mining_level ?: p?.mining_level,
                mining_speed ?: p?.mining_speed,
                can_tool ?: p?.can_tool,
                can_armor ?: p?.can_armor,
                xp_capacity ?: p?.xp_capacity,
                xp_absorption ?: p?.xp_absorption,
                color ?: p?.color,
                resources ?: p?.resources
            )
        }
    }
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
        val xpCapacity: Int,
        val xpAbsorption: Int,
        @ClientOnly val transform: RenderContext.QuadTransform,
        val resources: Map<ItemOrTag, Float>
    ) {
        constructor(raw: RawMaterial, id: Identifier) : this(
            id,
            RegistryUtil.resolveTranslationKey(id, "item", "tool.material"),
            raw.hardness!!,
            raw.edgeholding!!,
            raw.workability!!,
            raw.density!!,
            raw.durability!!,
            raw.heat!!,
            raw.padding!!,
            raw.mining_level!!,
            raw.mining_speed!!,
            raw.can_tool!!,
            raw.can_armor!!,
            raw.xp_capacity!!,
            raw.xp_absorption!!,
            colorToTransform(raw.color!!),
            raw.resources!!.mapKeys { e: Map.Entry<String, Float> ->
                if(e.key.startsWith("#"))
                    return@mapKeys Pair(null, Identifier(e.key.substring(1 until e.key.length)))
                else return@mapKeys Pair(Registries.ITEM[Identifier(e.key)], null)
            })

        override fun toString(): String {
            return "Material[id=$id,name=$name,hardness=$hardness,edgeholding=$edgeholding,workability=$workability,density=$density,durability=$durability,heat=$heat,padding=$padding,miningLevel=$miningLevel,miningSpeed=$miningSpeed,canIntoTool=$canIntoTool,canIntoArmor=$canIntoArmor,xpCapacity=$xpCapacity,xpAbsorption=$xpAbsorption,resources=$resources]"
        }
        companion object {
            fun colorToTransform(color: String): RenderContext.QuadTransform {
                return RenderContext.QuadTransform { view: MutableQuadView ->
                    val col = NumberUtils.RGBAorRGBtoARGB(color).toInt()
                    view.spriteColor(0, col, col, col, col)
                    return@QuadTransform true
                }
            }
        }
    }
}


