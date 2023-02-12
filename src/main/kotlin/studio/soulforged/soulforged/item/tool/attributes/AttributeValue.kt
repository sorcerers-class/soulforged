package studio.soulforged.soulforged.item.tool.attributes

class AttributeValue(var value: Float, var op: Operation) {
    override fun toString(): String {
        return "AttributeValue[$value]"
    }
    enum class Operation {
        MUTATE, ADD, MULTIPLY
    }
}
