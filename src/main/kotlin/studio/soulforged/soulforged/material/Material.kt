package studio.soulforged.soulforged.material

import java.util.*

data class Material(
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
    val classifier: Optional<Any>
)