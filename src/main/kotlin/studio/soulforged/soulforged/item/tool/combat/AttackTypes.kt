package studio.soulforged.soulforged.item.tool.combat

enum class AttackTypes constructor(i: IntRange){
    HC(0..0),
    NORMAL(1..1),
    DC(2..Int.MAX_VALUE)
}