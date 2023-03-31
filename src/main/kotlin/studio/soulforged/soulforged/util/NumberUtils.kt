package studio.soulforged.soulforged.util

object NumberUtils {
    fun RGBAorRGBtoARGB(input: String): UInt {
        when(input.length) {
            6 -> {
                return input.toUInt(16) or 0xFF000000.toUInt()
            }
            8 -> {
                return input.toUInt(16).rotateRight(8)
            }
            else -> throw IllegalArgumentException()
        }
    }
}