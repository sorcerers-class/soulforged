package studio.soulforged.soulforged.item.tool.attributes

import java.nio.ByteBuffer
import java.nio.ByteOrder

object AttributeValueSerializer {
    fun serialize(target: AttributeValue): ByteArray {
        val buffer = ByteBuffer.allocate(5)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        buffer.putFloat(target.value)
        buffer.put(4, target.op.ordinal.toByte())
        for (byte in buffer.array()) {
            print("$byte, ")
        }
        println()
        return buffer.array()
    }
    fun deserialize(data: ByteArray): AttributeValue {
        val buffer = ByteBuffer.allocate(5)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        buffer.put(data)
        val op = AttributeValue.Operation.values()[buffer.get(4).toInt()]
        return AttributeValue(buffer.getFloat(0), op)
    }
}