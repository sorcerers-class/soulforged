package studio.soulforged.soulforged.util

import com.mojang.serialization.Lifecycle
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.util.registry.SimpleRegistry

object RegistryUtil {
    fun<T> createRegistry(id: Identifier, default: T): Registry<T> {
        val key: RegistryKey<Registry<T>> = RegistryKey.ofRegistry(id)
        return SimpleRegistry(key, Lifecycle.stable(), null)
    }
    fun<T> createRegistry(id: String, default: T): Registry<T> {
        return createRegistry(Identifier(id), default)
    }
}