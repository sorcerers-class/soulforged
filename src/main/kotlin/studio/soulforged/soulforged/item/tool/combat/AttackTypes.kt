package studio.soulforged.soulforged.item.tool.combat

enum class AttackTypes {
    HC,
    NORMAL,
    DC;
    companion object {
       fun getAttackType(clicks: Int): AttackTypes {
           return when(clicks) {
               0 -> HC
               1 -> NORMAL
               else -> DC
           }
       }

    }
}