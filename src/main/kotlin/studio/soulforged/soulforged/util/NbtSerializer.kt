package studio.soulforged.soulforged.util

import net.minecraft.nbt.NbtCompound

interface NbtSerializer<T> {
    fun serialize(target: T): NbtCompound
    fun deserialize(nbt: NbtCompound): T
}
