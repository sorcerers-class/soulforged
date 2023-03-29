package studio.soulforged.soulforged.item.tool.combat

data class AttackProperties(
    val piercingDamage: Double,
    val bluntDamage: Double,
    val finesse: Double,
    val speed: Double,
    val piercing: Double,
    val category: WeaponCategories,
    val critDirection: CritDirections
)