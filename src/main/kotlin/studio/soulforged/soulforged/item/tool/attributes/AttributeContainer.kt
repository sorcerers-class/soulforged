package studio.soulforged.soulforged.item.tool.attributes

import net.minecraft.nbt.NbtCompound
import studio.soulforged.soulforged.util.NbtSerializer

class AttributeContainer {
    private val attributes: MutableMap<AttributeIdentifiers, AttributeValue> = mutableMapOf()
    object AttributeContainerSerializer : NbtSerializer<AttributeContainer> {
        override fun serialize(target: AttributeContainer): NbtCompound {
            val nbt = NbtCompound()
            //target.attributes.put("test", AttributeValue(420f, AttributeValue.Operation.MUTATE))
            target.attributes[AttributeIdentifiers.WEIGHT] = AttributeValue(100f, AttributeValue.Operation.MULTIPLY)
            target.attributes.forEach { (k, v) ->
                nbt.putByteArray(k.name, AttributeValueSerializer.serialize(v))
            }
            return nbt
        }

        override fun deserialize(nbt: NbtCompound): AttributeContainer {
            val container = AttributeContainer()
            nbt.keys.forEach { container.attributes[AttributeIdentifiers.valueOf(it)] = AttributeValueSerializer.deserialize(nbt.getByteArray(it).copyOfRange(0, 4)) }
            return container
        }
    }
    fun contains(key: AttributeIdentifiers) = attributes.contains(key)
    fun get(key: AttributeIdentifiers, baseValue: Float): Float {
        val attrib = attributes[key]
        val result = when(attrib?.op) {
            AttributeValue.Operation.MUTATE -> attrib.value
            AttributeValue.Operation.ADD -> baseValue + attrib.value
            AttributeValue.Operation.MULTIPLY -> baseValue * attrib.value
            else -> baseValue
        }
        return result
    }
}
