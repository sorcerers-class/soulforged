package studio.soulforged.soulforged.material

import net.minecraft.util.Identifier

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
    val classifier: Classifiers?
)